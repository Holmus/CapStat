package capstat.tests;

import capstat.application.LoginController;
import capstat.model.CapStat;
import capstat.model.User;
import capstat.model.UserFactory;
import capstat.model.UserLedger;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author hjorthjort
 */
public class LoginControllerTest {

    LoginController lc = new LoginController();
    CapStat cp = CapStat.getInstance();
    UserLedger ul = UserLedger.getInstance();

    /**
     * Logs in a guest user and assert that this user is indeed logged in
     */
    @Test
    public void testGuestLogin() {
        lc.loginAsGuest();
        assertTrue("Guest is logged in", cp.getLoggedInUser().equals
                (UserFactory.createGuestUser()));
    }

    /**
     * Logs in a dummy user and asserts that this user is then indeed logged in.
     */
    @Test
    public void testSuccessfulLogin() {
        //Test with dummy 1
        User user = UserFactory.createDummyUser1();
        ul.registerNewUser(user.getNickname(), user.getName(), "foobar", user
                .getChalmersAge().getBirthday(), user.getChalmersAge()
                .getAdmittance().getYear(), user.getChalmersAge()
                .getAdmittance().getReadingPeriod().ordinal()+1);
        //The password is hardcoded into the dummy user.
        assertTrue(lc.loginAsUser(user.getNickname(), "foobar"));
        assertTrue("New user is logged in", cp.getLoggedInUser().equals(user));
    }

    @Test
    public void testFailedLogin() {
        //Test with dummy 2
        User user = UserFactory.createDummyUser2();
        ul.registerNewUser(user.getNickname(), user.getName(), "boofaz", user
                .getChalmersAge().getBirthday(), user.getChalmersAge()
                .getAdmittance().getYear(), user.getChalmersAge()
                .getAdmittance().getReadingPeriod().ordinal() + 1);
        //The password is hardcoded into the dummy user.
        assertFalse(lc.loginAsUser(user.getNickname(), "not boofaz"));
        assertFalse("New user has not been logged in", cp.getLoggedInUser()
                .equals(user));
    }


}
