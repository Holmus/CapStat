package capstat.application;

import capstat.infrastructure.database.DatabaseHelperFactory;
import capstat.infrastructure.database.UserDatabaseHelper;
import capstat.model.match.Match;
import capstat.model.match.MatchFactory;
import capstat.model.user.User;
import capstat.model.user.UserFactory;
import capstat.model.user.UserLedger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class MatchControllerTest {

    private MatchController controller;
    private Match match;
    private UserDatabaseHelper userDatabase;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        this.match = MatchFactory.createDefaultMatch();
        this.controller = new MatchController(match);
        this.controller.startMatch();
        this.userDatabase = new DatabaseHelperFactory().createUserQueryHelper();
        this.user1 = UserFactory.createDummyUser1();
        this.userDatabase.addUser
                (UserLedger.getInstance().createBlueprint(user1));
        this.user2 = UserFactory.createDummyUser2();
        this.userDatabase.addUser(UserLedger.getInstance().createBlueprint
                (user2));
       Thread.sleep(300);
    }

    @After
    public void tearDown() throws InterruptedException {
        this.userDatabase.removeUser(UserLedger.getInstance().createBlueprint
                (user1));
        this.userDatabase.removeUser(UserLedger.getInstance().createBlueprint
                (user2));
        UserLedger.getInstance().clearCache();
    }

    @Test
    public void testStartMatch() throws Exception {
        //Since in we start the match for convenience in the setup, we must
        // create a different match and controller here to test properly.
        Match otherMatch = MatchFactory.createDefaultMatch();
        MatchController otherController = new MatchController(otherMatch);
        assertFalse(otherMatch.isOngoing());
        otherController.startMatch();
        assertTrue(otherMatch.isOngoing());
    }

    @Test
    public void testRecordMiss() throws Exception {
        this.controller.recordMiss();
        assertFalse(this.match.isDuelling());
    }

    @Test
    public void testRecordHit() throws Exception {
        this.controller.recordHit();
        assertTrue(this.match.isDuelling());
    }

    @Test
    public void testSetPlayer1() throws Exception {
        this.controller.setPlayer1(this.user1.getNickname());
	   Thread.sleep(300);
        assertTrue(this.match.getPlayer(Match.Player.ONE).equals(this.user1));
    }

    @Test
    public void testSetPlayer2() throws Exception {
        this.controller.setPlayer2(this.user2.getNickname());
	   Thread.sleep(300);
        assertTrue(this.match.getPlayer(Match.Player.TWO).equals(this.user2));
    }

    @Test
    public void testSetEndGameStrategyUnranked() throws Exception {
        //Try unranked
        double elo1 = this.user1.getRanking().getPoints();
        double elo2 = this.user2.getRanking().getPoints();
        this.controller.setEndGameStrategy(this.controller.UNRANKED);
        this.match.setPlayer1(user1);
        this.match.setPlayer2(user2);
	    Thread.sleep(300);
        //Play through the game, withut hte controller.
        while (this.match.isOngoing()) {
            this.match.recordHit();
            this.match.recordMiss();
            this.match.recordMiss();
        }
        //In an unranked game, user ranking should not be changed.
        assertTrue(elo1 == user1.getRanking().getPoints());
        assertTrue(elo2 == user2.getRanking().getPoints());
    }

    @Test
    public void testSetEndGameStrategyRanked() throws Exception {
        double elo1 = this.user1.getRanking().getPoints();
        double elo2 = this.user2.getRanking().getPoints();
        this.controller.setEndGameStrategy(this.controller.RANKED);
        this.match.setPlayer1(user1);
        this.match.setPlayer2(user2);
	   Thread.sleep(300);
        while (this.match.isOngoing()) {
            this.match.recordHit();
            this.match.recordMiss();
            this.match.recordMiss();
        }

        assertFalse(elo1 == user1.getRanking().getPoints());
        assertFalse(elo2 == user2.getRanking().getPoints());
    }
}