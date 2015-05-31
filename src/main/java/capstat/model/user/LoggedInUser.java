package capstat.model.user;

/**
 * @author Christian Persson
 * rewieved by hjorthjort
 */
public class LoggedInUser {

    private static LoggedInUser instance;
    private UserLedger userLedger;
    private User loggedInUser;
    private final User guestUser;

    private LoggedInUser() {
        this.userLedger = UserLedger.getInstance();
        this.guestUser = UserLedger.getGuestUser();
        this.loggedInUser = guestUser;
    }

    /**
     * Returns the only instance of LoggedInUser.
     * @return the only instance of LoggedInUser
     */
    public synchronized static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    //Setters

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setGuestLoggedIn() {
        this.loggedInUser = this.guestUser;
    }

    //Getters

    /**
     * Checks whether a given nickname is a valid nickname. A valid nickname is any nickname that is unique among other nicknames, and contains one or more of the characters A-Z, a-z, ÅÄÖ, åäö, 0-9, (), period, underscore, and space.
     * @param nickname the nickname to validate
     * @return true if the nickname is valid; false otherwise
     */
    public boolean isNicknameValid(String nickname) {
        return this.userLedger.isNicknameValid(nickname);
    }

    public boolean userIsLoggedIn() {
        return this.loggedInUser != this.guestUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
