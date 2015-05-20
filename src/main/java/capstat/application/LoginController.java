package capstat.application;

import capstat.model.CapStat;

/**
 * Created by Jakob on 20/05/15.
 *
 * Class to verify login credentials with the model and login.
 *
 */
public class LoginController {

    private CapStat capStat = CapStat.getInstance();

    /**
     * Method to validate user credentials and login
     * @param username the username chosen at registration
     * @param password the password chosen at registration
     * @return true if login was successful with given credentials, otherwise false
     */
    public boolean loginAsUser(String username, String password) {
        return true;
    }
    /**
     * Should set the logged in user to a guest-user
     */
    public void loginAsGuest() {
        this.capStat.setGuestLoggedIn();
    }
}
