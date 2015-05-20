package capstat.tests;

import capstat.infrastructure.*;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jibbs on 19/05/15.
 * A class to test the match-part in DatabaseFacade.
 */
public class MatchDatabaseFacadeTest {

	private static MatchDatabaseHelper matchdb;
	private MatchResultBlueprint dummyMatchResult1;
	private ThrowSequenceBlueprint dummyThrowSequence1;

	@BeforeClass
	public static void init() {
		DatabaseHelperFactory factory = new DatabaseHelperFactory();
		matchdb = factory.createMatchQueryHelper();
	}

	@Before
	public void addNewMatch() {

	}

	@After
	public void removeNewUser() {
		matchdb.removeMatch(1);
	}

	@Test
	public void testAddMatch() throws Exception {

	}

	@Test
	public void testAddMatchSet() throws Exception {

	}

	@Test
	public void testRemoveMatch() throws Exception {

	}

	@Test
	public void testGetMatch() throws Exception {

	}

	@Test
	public void testGetAllMatches() throws Exception {

	}

	@Test
	public void testGetMatchesForUser() throws Exception {

	}

	@Test
	public void testGetMatchesForUsers() throws Exception {

	}

	@Test
	public void testGetMatchesInDateRange() throws Exception {

	}

	@Test
	public void testGetMatchForSpectator() throws Exception {

	}

	public MatchResultBlueprint getDummyMatchResult(MatchResult mr) {
/*		List<PartialSequenceBlueprint> psbs = new ArrayList<>();
		List<PartialSequenceResult> sequences = mr.getSequences();
		for (PartialSequenceResult psr : sequences) {
			Match.Glass[] glassObjs = psr.getGlasses();
			boolean[] glasses = new boolean[glassObjs.length];
			for (int i = 0; i < glasses.length; i++) {
				glasses[i] = glassObjs[i].isActive();
			}
			int startingPlayer = psr.getStartingPlayer() == Match.Player.ONE ? 1 : 2;
			boolean throwBeforeWasHit = psr.throwBeforeWasHit();

			Match.Throw[] throwObjs = psr.getSequence();
			boolean[] throwArr = new boolean[throwObjs.length];
			for (int i = 0; i < throwArr.length; i++) {
				throwArr[i] = throwObjs[i] == Match.Throw.HIT;
			}

			psbs.add(new PartialSequenceBlueprint(
				glasses,
				startingPlayer,
				throwBeforeWasHit,
				throwArr
			));
		}*/


		boolean[] sequence = new boolean[10];
		sequence[0] = true;
		sequence[1] = false;//1
		sequence[2] = false;
		sequence[3] = true;
		sequence[4] = true;
		sequence[5] = true;
		sequence[6] = false;//1
		sequence[7] = true;
		sequence[8] = false;//2
		sequence[9] = true;//p1

		boolean[] glasses = new boolean[7];
		glasses[0] = true;
		glasses[1] = true;
		glasses[2] = true;
		glasses[3] = true;
		glasses[4] = true;
		glasses[5] = true;
		glasses[6] = true;

		PartialSequenceBlueprint psb = new PartialSequenceBlueprint(glasses, 1, false, sequence);
		List<PartialSequenceBlueprint> psbs = new ArrayList<>();
		psbs.add(psb);

		glasses[0] = false;
		glasses[1] = true;
		glasses[2] = true;
		glasses[3] = true;
		glasses[4] = true;
		glasses[5] = false;
		glasses[6] = false;

		sequence = new boolean[6];
		sequence[0] = true;
		sequence[1] = true;
		sequence[2] = false;
		sequence[3] = true;
		sequence[4] = true;
		sequence[5] = false;

		psb = new PartialSequenceBlueprint(glasses, 2, true, sequence);

		psbs.add(psb);


		return new MatchResultBlueprint(mr.getId(), mr.getPlayer1().getNickname(),
				mr.getPlayer2().getNickname(), mr.getSpectator().getNickname(), mr.getPlayer1score(),
				mr.getPlayer2score(), mr.getStartTime(), mr.getEndTime(), psbs);
	}
}