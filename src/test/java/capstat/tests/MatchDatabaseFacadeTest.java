package capstat.tests;

import capstat.infrastructure.*;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jibbs on 19/05/15.
 * A class to test the match-part in DatabaseFacade.
 */
public class MatchDatabaseFacadeTest {

	private static MatchDatabaseHelper matchdb;
	private MatchResultBlueprint dummyMatchResult1;

	@BeforeClass
	public static void init() {
		DatabaseHelperFactory factory = new DatabaseHelperFactory();
		matchdb = factory.createMatchQueryHelper();
	}

	@Before
	public void addNewMatch() {
		dummyMatchResult1 = getDummyMatchResultBlueprint1();

	}

	@After
	public void removeNewUser() {
		matchdb.removeMatch(1);
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

	public MatchResultBlueprint getDummyMatchResultBlueprint1() {
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

		List<PartialSequenceBlueprint> psbs = new ArrayList<>();

		PartialSequenceBlueprint psb = new PartialSequenceBlueprint(
				new boolean[] { true, true, true, true, true, true, true }, 1, false,
				new boolean[] { true, false, false, true, true, true, false, true, false, true } );
		psbs.add(psb);

		psb = new PartialSequenceBlueprint(
				new boolean[] { false, true, true, true, true, false, false }, 2, true,
				new boolean[] { true, true, false, true, true, false });
		psbs.add(psb);


		return new MatchResultBlueprint(1, "DummyOne",
				"DummmyTwo", "Guest", 4,
				1, 1432060742, 1432061582, psbs);
	}
}