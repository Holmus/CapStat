package capstat.application;

import capstat.model.user.LoggedInUser;
import capstat.model.user.Security;
import capstat.model.user.User;
import capstat.model.user.UserLedger;

/**
 * Created by Jakob on 20/05/15.
 * @author Jakob
 * Class to verify login credentials with the model and login.
 *
 */
public class LoginController {

    private LoggedInUser loggedInUser = LoggedInUser.getInstance();
    UserLedger userLedger = UserLedger.getInstance();

    /**
     * Method to validate user credentials and login
     * @param username the username chosen at registration
     * @param password the password chosen at registration
     * @return true if login was successful with given credentials, otherwise false
     */
    public boolean loginAsUser(String username, String password) {
        User user = this.userLedger.getUserByNickname(username);
        if (user == null)
            return false;
        String hashedPassword = Security.hashPassword(password);
        if(userLedger.doesUserExist(username)) {
            if (hashedPassword.equals(user.getHashedPassword())) {
                this.loggedInUser.setLoggedInUser(user);
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    /**
     * Should set the logged in user to a guest-user
     */
    public void loginAsGuest() {
        this.loggedInUser.setGuestLoggedIn();
    }

    /**
     * Method to logout the currently logged in user
     */
    public void logoutUser() {
        loginAsGuest();
    }
}
