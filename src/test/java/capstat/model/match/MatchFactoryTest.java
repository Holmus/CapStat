package capstat.model.match;

import capstat.model.LoggedInUser;
import capstat.model.user.UserFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class MatchFactoryTest {

    @Test
    public void testCreateDefaultMatch() throws Exception {
        Match match = MatchFactory.createDefaultMatch();
        assertTrue(match.getPlayer(Match.Player.ONE)==null);
        assertTrue(match.getPlayer(Match.Player.TWO)==null);
        assertFalse(match.isDuelling());
        assertFalse(match.isOngoing());
        assertTrue(match.getSpectator() == LoggedInUser.getInstance()
                .getLoggedInUser());
        assertTrue(match.checkInvariants());
    }

    @Test
    public void testCreateMatch() throws Exception {
        Match match = MatchFactory.createMatch(19, 4);
        assertTrue(match.getPlayer(Match.Player.ONE)==null);
        assertTrue(match.getPlayer(Match.Player.TWO)==null);
        assertTrue(match.getGlasses().length == 39);
        assertFalse(match.isDuelling());
        assertFalse(match.isOngoing());
        assertTrue(match.getSpectator() == LoggedInUser.getInstance()
                .getLoggedInUser());
        assertTrue(match.checkInvariants());
    }

    @Test
    public void testCreateMatch1() throws Exception {
        Match match = MatchFactory.createMatch(11, 3, UserFactory
                .createDummyUser1(), UserFactory.createDummyUser2());
        assertTrue(match.getPlayer(Match.Player.ONE).equals(UserFactory
                .createDummyUser1()));
        assertTrue(match.getPlayer(Match.Player.TWO).equals(UserFactory
                .createDummyUser2()));
        assertTrue(match.getGlasses().length == 23);
        assertFalse(match.isDuelling());
        assertFalse(match.isOngoing());
        assertTrue(match.getSpectator() == LoggedInUser.getInstance()
                .getLoggedInUser());
        assertTrue(match.checkInvariants());
    }
}