package capstat.tests;

import capstat.application.LoginController;
import capstat.model.CapStat;
import capstat.model.UserFactory;
import capstat.model.UserLedger;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author hjorthjort
 */
public class LoginControllerTest {

    LoginController lc = new LoginController();
    CapStat cp = CapStat.getInstance();

    @Test
    public void testGuestLogin() {
        lc.loginAsGuest();
        assertTrue("Guest is logged in", cp.getLoggedInUser().equals
                (UserFactory.createGuestUser()));
    }

}
