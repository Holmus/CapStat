package capstat.model;

import capstat.infrastructure.DatabaseHelperFactory;
import capstat.infrastructure.MatchDatabaseHelper;
import capstat.infrastructure.MatchResultBlueprint;
import capstat.infrastructure.PartialSequenceBlueprint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private MatchDatabaseHelper dbHelper;

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
//TODO:implemnet
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
