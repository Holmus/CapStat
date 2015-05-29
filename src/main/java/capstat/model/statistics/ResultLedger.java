package capstat.model.statistics;

import capstat.infrastructure.database.DatabaseHelperFactory;
import capstat.infrastructure.database.ResultDatabaseHelper;
import capstat.infrastructure.database.MatchResultBlueprint;
import capstat.infrastructure.database.PartialSequenceBlueprint;
import capstat.model.match.Match;
import capstat.model.match.ThrowSequence;
import capstat.model.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class representing a book or ledger where all matches are stored, and from
 * which matches can be retrieved. This class represents a repository in
 * the domain and delegates the issue of persistant sotrage of Match objects.
 *
 * This class is a Singleton, and a reference to the singleton object is
 * obtained by calling getInstance.
 */
public class ResultLedger {
	private static ResultLedger instance;
	private Map<String, User> users;
	private ResultDatabaseHelper dbHelper;

	private ResultLedger() {
		this.users = new HashMap<>();
		this.dbHelper = new DatabaseHelperFactory().createMatchQueryHelper();
	}

	public static synchronized ResultLedger getInstance() {
		if (instance == null)
			instance = new ResultLedger();

		return instance;
	}

	public void registerResult(Match match) {
        MatchResultBlueprint blueprint = createBlueprint(match);
        this.dbHelper.addMatch(blueprint);
	}

    public MatchResultBlueprint createBlueprint(Match match) {
        Instant endInstant = match.getEndTime();
        LocalDateTime end = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());
        // Gives a string like YYYYMMDDHHMMSSXXX (second M = minute, X = millisecond)
        String idString = String.format("%d%02d%02d%02d%02d%02d%d", end.getYear(), end.getMonthValue(), end.getDayOfMonth(), end.getHour(), end.getMinute(), end.getSecond(), endInstant.getNano() / 1000000);

        long id = Long.parseLong(idString);
        String player1Nickname = match.getPlayer(Match.Player.ONE).getNickname();
        String player2Nickname = match.getPlayer(Match.Player.TWO).getNickname();
        String spectatorNickname = match.getSpectator().getNickname();
        int player1Score = match.getPlayerRoundsWon(Match.Player.ONE);
        int player2Score = match.getPlayerRoundsWon(Match.Player.TWO);
        long startTime = match.getStartTime().getEpochSecond();
        long endTime = endInstant.getEpochSecond();

        ThrowSequence sequence = match.getThrowSequence();
        List<ThrowSequence.PartialSequence> sequences = sequence.getSequences();
        List<PartialSequenceBlueprint> psbs = new LinkedList<>();
        psbs = sequences
            .stream()
            .map(seq -> createBlueprint(seq))
            .collect(Collectors.toList());

        MatchResultBlueprint mrb = new MatchResultBlueprint(
            id,
            player1Nickname,
            player2Nickname,
            spectatorNickname,
            player1Score,
            player2Score,
            startTime,
            endTime,
            psbs
        );
        return mrb;
    }

    private static PartialSequenceBlueprint createBlueprint(ThrowSequence.PartialSequence ps) {
        boolean[] glasses;
        int startingPlayer;
        boolean throwBeforeWasHit;
        boolean[] sequence;

        Match.Glass[] glassObjs = ps.getGlasses();
        glasses = new boolean[glassObjs.length];
        for (int i = 0; i < glasses.length; i++) {
            glasses[i] = glassObjs[i].isActive();
        }

        startingPlayer = ps.getStartingPlayer() == Match.Player.ONE ? 1 : 2;

        throwBeforeWasHit = ps.throwBeforeWasHit();

        List<Match.Throw> throwSequence = ps.getSequence();
        sequence = new boolean[throwSequence.size()];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = throwSequence.get(i) == Match.Throw.HIT;
        }

        return new PartialSequenceBlueprint(glasses, startingPlayer, throwBeforeWasHit, sequence);
    }

	public Set<MatchResult> getMatchesForUser(User user) {
		Set<MatchResultBlueprint> blueprintSet = this.dbHelper
				.getMatchesForUser(user
				.getNickname());
		Iterator<MatchResultBlueprint> iterator = blueprintSet.stream().
				filter(b -> b.player1Nickname.equals(user.getNickname()) ||
						b.player2Nickname.equals(user.getNickname())).iterator();
		Set<MatchResult> ret = new HashSet<>();
		while(iterator.hasNext()) {
			ret.add(reconstituteFromBlueprint(iterator.next()));
		}
		return ret;
	}

	private MatchResult reconstituteFromBlueprint(MatchResultBlueprint blueprint) {
		return new MatchResult(blueprint);
	}

	public MatchResultBlueprint createBlueprint(MatchResult result) {
		List<PartialSequenceBlueprint> sequenceBlueprintList = new
				LinkedList<>();
		for (PartialSequenceResult sequence : result.getSequences()) {
			sequenceBlueprintList.add(createBlueprint(sequence));
		}
		return new MatchResultBlueprint(result.getId(),
				result.getPlayer1().getNickname(),
				result.getPlayer2().getNickname(),
				result.getSpectator().getNickname(),
				result.getPlayer1score(),
				result.getPlayer2score(),
				result.getStartTime().toEpochMilli(),
				result.getEndTime().toEpochMilli(),
				sequenceBlueprintList);
	}

	public PartialSequenceBlueprint createBlueprint(PartialSequenceResult
															sequence) {
		//Translate glasses array to boolean array
		Match.Glass[] glasses = sequence.getGlasses();
		boolean[] newGlasses = new boolean[glasses.length];
		for (int i = 0; i < glasses.length; i++) {
			newGlasses[i] = glasses[i].isActive();
		}

		//Translate throws array to boolean array.
		Match.Throw[] partialSequence = sequence.getSequence();
		boolean[] newPartialSequence = new boolean[partialSequence.length];
		for (int i = 0; i < partialSequence.length; i++) {
			newPartialSequence[i] = partialSequence[i] == Match.Throw.HIT;
		}

		return new PartialSequenceBlueprint(newGlasses,
				sequence.getStartingPlayer().ordinal() + 1,
				sequence.throwBeforeWasHit(),
				newPartialSequence);
	}
}
