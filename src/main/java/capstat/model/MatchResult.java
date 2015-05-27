package capstat.model;

import capstat.infrastructure.database.MatchResultBlueprint;
import capstat.infrastructure.database.PartialSequenceBlueprint;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 * An immutable value object class that can be queried to figure out how a
 * game turned out.
 *
 * @author hjorthjort
 */
public class MatchResult {
    private final long id;
    private final User player1, player2, spectator;
    private final int player1score, player2score;
    private final Instant startTime, endTime;
    private final List<PartialSequenceResult> sequences;

    public MatchResult(MatchResultBlueprint blueprint) {
        this.id = blueprint.id;
        UserLedger userLedger = UserLedger.getInstance();
        this.player1 = userLedger.getUserByNickname(blueprint.player1Nickname);
        this.player2 = userLedger.getUserByNickname(blueprint.player2Nickname);
        this.spectator = userLedger.getUserByNickname(blueprint.spectatorNickname);
        this.player1score = blueprint.player1score;
        this.player2score = blueprint.player2score;
        this.startTime = Instant.ofEpochSecond(blueprint.startTime);
        this.endTime = Instant.ofEpochSecond(blueprint.endTime);
        this.sequences = convertToPartialSequences(blueprint.sequences);
    }

    private List<PartialSequenceResult> convertToPartialSequences(final
                                           List<PartialSequenceBlueprint> sequences) {
        List<PartialSequenceResult> retList = new LinkedList<>();
        for (PartialSequenceBlueprint sequence : sequences) {
            retList.add(this.convertSingleSequenceFromBlueprint(sequence));
        }
        return retList;
    }

    private PartialSequenceResult convertSingleSequenceFromBlueprint
            (final PartialSequenceBlueprint sequence) {
        return new PartialSequenceResult(sequence);
    }

    public long getId() {
        return this.id;
    }

	public User getPlayer1() {
		return this.player1;
	}
	public User getPlayer2() {
		return this.player2;
	}

	public int getPlayer1score() {
		return this.player1score;
	}

	public int getPlayer2score() {
		return this.player2score;
	}

    public User getPlayer(Match.Player player) {
        if (player == Match.Player.ONE)
            return this.player1;
        else if (player == Match.Player.TWO)
            return this.player2;
        else
            throw new IllegalArgumentException("Must either get Player.ONE or Player.TWO");
    }

    public User getSpectator() {
        return this.spectator;
    }

    public int getPlayerScore(Match.Player player) {
        if (player == Match.Player.ONE)
            return this.player1score;
        else if (player == Match.Player.TWO)
            return this.player2score;
        else
            throw new IllegalArgumentException("Must either get Player.ONE or Player.TWO");
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public List<PartialSequenceResult> getSequences() {
        return new LinkedList<>(this.sequences);
    }
}
