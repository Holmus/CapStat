package capstat.tests;

import capstat.infrastructure.database.*;
import capstat.model.user.Admittance;
import capstat.model.user.ELORanking;
import capstat.model.user.User;
import capstat.model.user.UserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by jibbs on 19/05/15.
 * A class to test the match-part in DatabaseFacade.
 */
public class MatchDatabaseFacadeTest {

	private static MatchDatabaseHelper matchdb;
	private static UserDatabaseHelper userdb;
	private MatchResultBlueprint dummyMatchResult1, dummyMatchResult2;
	private UserBlueprint dummyRow1, dummyRow2, dummyRow3, dummyRow4, guestUser;



	@BeforeClass
	public static void init() {
		DatabaseHelperFactory factory = new DatabaseHelperFactory();
		userdb = factory.createUserQueryHelper();
		matchdb = factory.createMatchQueryHelper();
	}

	@Before
	public void addNewMatch() throws InterruptedException {
		dummyRow1 = getDummyRow(UserFactory.createDummyUser1());
		dummyRow2 = getDummyRow(UserFactory.createDummyUser2());
		dummyRow3 = getDummyRow(UserFactory.createDummyUser3());
		dummyRow4 = getDummyRow(UserFactory.createDummyUser4());
		guestUser = getDummyRow(UserFactory.createGuestUser());

		dummyMatchResult1 = getDummyMatchResultBlueprint1();
		dummyMatchResult2 = getDummyMatchResultBlueprint2();
		userdb.removeUser(dummyRow2);
		userdb.removeUser(dummyRow3);
		userdb.removeUser(dummyRow4);
		userdb.removeUser(dummyRow1);
		userdb.removeUser(guestUser);
		userdb.addUser(dummyRow1);
		userdb.addUser(dummyRow2);
		userdb.addUser(dummyRow3);
		userdb.addUser(dummyRow4);
		userdb.addUser(guestUser);
		Thread.sleep(300);
		matchdb.addMatch(dummyMatchResult1);
		matchdb.addMatch(dummyMatchResult2);
		Thread.sleep(300);
	}

	@After
	public void removeNewMatches() {

		matchdb.removeMatch(1);
		matchdb.removeMatch(2);
		userdb.removeUser(dummyRow1);
		userdb.removeUser(dummyRow2);
		userdb.removeUser(dummyRow3);
		userdb.removeUser(dummyRow4);
		userdb.removeUser(guestUser);
	}


	@Test
	public void testAddMatchSet() throws Exception {
		matchdb.removeMatch(1);
		matchdb.removeMatch(2);
		Set<MatchResultBlueprint> mrbs = new HashSet<>();
		mrbs.add(dummyMatchResult1);
		mrbs.add(dummyMatchResult2);
		matchdb.addMatchSet(mrbs);
		Thread.sleep(300);
	}

	@Test
	public void testGetMatch() throws Exception {
		MatchResultBlueprint mrb = matchdb.getMatchById(1);
		testMatchesAreEqual(dummyMatchResult1, mrb);
		mrb = matchdb.getMatchById(2);
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
		Set<MatchResultBlueprint> mrbSet = matchdb.getMatchesForUsers(dummyMatchResult1.player2Nickname, dummyMatchResult1.player1Nickname);
		assertTrue(mrbSet.contains(dummyMatchResult1));
		assertFalse(mrbSet.contains(dummyMatchResult2));
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
		String standardMessage = "Make sure the stored Matches / ThrowSequences fields and the database " +
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
				"DummyTwo", "Guest", 4,
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


		return new MatchResultBlueprint(2, "DummyThree",
				"DummyFour", "DummyOne", 4,
				1, 1412060742, 1431061982, psbs);
	}

	private UserBlueprint getDummyRow(User dummyUser) {
		LocalDate bd = dummyUser.getChalmersAge().getBirthday();
		Admittance ad = dummyUser.getChalmersAge().getAdmittance();
		ELORanking elo = dummyUser.getRanking();
		return new UserBlueprint(dummyUser.getNickname(), dummyUser.getName(),
				dummyUser.getHashedPassword(),
				bd.getYear(), bd.getMonthValue(), bd.getDayOfMonth(), ad.getYear()
				.getValue(), ad
				.getReadingPeriod().ordinal()+1, elo.getPoints()
		);
	}

}