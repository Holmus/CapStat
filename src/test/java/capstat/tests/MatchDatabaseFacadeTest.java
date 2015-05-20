package capstat.tests;

import capstat.infrastructure.*;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

	public MatchResultBlueprint getDummyMatchResult(MatchResult dmr) {
		return new MatchResultBlueprint(dmr.getId(), dmr.getP1(), dmr.getP2(), dmr.getP1Score(),
				dmr.getP2Score(), dmr.getSpectator(), dmr.getYear(), dmr.getMonth(), dmr.getDay(),
				dmr.getHour(), dmr.getMinute(), dmr.getSecond()
		);
	}

}