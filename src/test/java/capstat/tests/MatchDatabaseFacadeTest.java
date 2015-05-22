package capstat.tests;

import capstat.infrastructure.*;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by jibbs on 19/05/15.
 * A class to test the match-part in DatabaseFacade.
 */
public class MatchDatabaseFacadeTest {

	private static MatchDatabaseHelper matchdb;
	private MatchResultBlueprint dummyMatchResult1;
	private MatchResultBlueprint dummyMatchResult2;



	@BeforeClass
	public static void init() {
		DatabaseHelperFactory factory = new DatabaseHelperFactory();
		matchdb = factory.createMatchQueryHelper();
	}

	@Before
	public void addNewMatch() {
		dummyMatchResult1 = getDummyMatchResultBlueprint1();
		dummyMatchResult2 = getDummyMatchResultBlueprint2();
		matchdb.addMatch(dummyMatchResult1);
		matchdb.addMatch(dummyMatchResult2);
	}

	@After
	public void removeNewMatches() {
		matchdb.removeMatch(1);
		matchdb.removeMatch(2);
	}


	@Test
	public void testAddMatchSet() throws Exception {
		removeNewMatches();
		Set<MatchResultBlueprint> mrbs = new HashSet<>();
		mrbs.add(dummyMatchResult1);
		mrbs.add(dummyMatchResult2);
		matchdb.addMatchSet(mrbs);
	}

	@Test
	public void testGetMatch() throws Exception {

		MatchResultBlueprint mrb = matchdb.getMatch(1);
		testMatchesAreEqual(dummyMatchResult1, mrb);
		mrb = matchdb.getMatch(2);
		testMatchesAreEqual(dummyMatchResult2, mrb);

	}

	@Test
	public void testGetAllMatches() throws Exception {
		Set<MatchResultBlueprint> mrbSet = matchdb.getAllMatches();
		assertTrue(mrbSet.contains(dummyMatchResult1));
		assertTrue(mrbSet.contains(dummyMatchResult2));
	}

	@Test
	public void testGetMatchesForUser() throws Exception {
		Set<MatchResultBlueprint> mrbSet = matchdb.getMatchesForUser(dummyMatchResult1.player1Nickname);
		assertTrue(mrbSet.contains(dummyMatchResult1));
		assertFalse(mrbSet.contains(dummyMatchResult2));
	}

	@Test
	public void testGetMatchesForUsers() throws Exception {
		Set<MatchResultBlueprint> mrbSet = matchdb.getMatchesForUsers(dummyMatchResult1.player2Nickname, dummyMatchResult2.player2Nickname);
		assertTrue(mrbSet.contains(dummyMatchResult1));
		assertTrue(mrbSet.contains(dummyMatchResult2));
	}

	@Test
	public void testGetMatchesInDateRange() throws Exception {
		Set<MatchResultBlueprint> mrbSet = matchdb.getMatchesInDateRange(dummyMatchResult2.endTime-100, dummyMatchResult2.endTime+100);
		assertTrue(mrbSet.contains(dummyMatchResult2));
		assertFalse(mrbSet.contains(dummyMatchResult1));
	}

	@Test
	public void testGetMatchForSpectator() throws Exception {
		Set<MatchResultBlueprint> mrbSet = matchdb.getMatchesForSpectator(dummyMatchResult1.spectatorNickname);
		assertTrue(mrbSet.contains(dummyMatchResult1));
		assertFalse(mrbSet.contains(dummyMatchResult2));
	}

	private void testMatchesAreEqual(final MatchResultBlueprint mrbLocal, final MatchResultBlueprint mrbDb) {
		String standardMessage = "Make sure the stored users fields and the database " +
				"fields contain the same information: ";
		assertEquals(standardMessage, mrbLocal.player1Nickname, mrbDb.player1Nickname);
		assertEquals(standardMessage, mrbLocal.player2Nickname, mrbDb.player2Nickname);
		assertEquals(standardMessage, mrbLocal.spectatorNickname, mrbDb.spectatorNickname);
		assertEquals(standardMessage, mrbLocal.player1score, mrbDb.player1score);
		assertEquals(standardMessage, mrbLocal.player2score, mrbDb.player2score);
		assertEquals(standardMessage, mrbLocal.startTime, mrbDb.startTime);
		assertEquals(standardMessage, mrbLocal.endTime, mrbDb.endTime);
		assertEquals(standardMessage, mrbLocal.sequences, mrbDb.sequences);
	}


	private MatchResultBlueprint getDummyMatchResultBlueprint1() {
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

	private MatchResultBlueprint getDummyMatchResultBlueprint2() {

		List<PartialSequenceBlueprint> psbs = new ArrayList<>();

		PartialSequenceBlueprint psb = new PartialSequenceBlueprint(
				new boolean[] { true, true, true, true, true, true, true }, 1, false,
				new boolean[] { true, false, false, true, true, true, false, true, false, true } );
		psbs.add(psb);

		psb = new PartialSequenceBlueprint(
				new boolean[] { false, true, true, true, true, false, false }, 2, true,
				new boolean[] { true, true, false, true, true, false });
		psbs.add(psb);


		return new MatchResultBlueprint(1, "DummyThree",
				"DummmyFour", "DummyFive", 4,
				2, 1412060742, 1431061982, psbs);
	}
}