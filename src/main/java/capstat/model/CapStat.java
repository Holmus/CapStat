package capstat.model;

/**
 * @author Christian Persson
 */
public class CapStat {

    private static CapStat instance;
    private UserLedger userLedger = UserLedger.getInstance();

    private CapStat() {}

    /**
     * Returns the only instance of CapStat.
     * @return the only instance of CapStat
     */
    public static CapStat getInstance() {
        if (instance == null) {
            instance = new CapStat();
        }
        return instance;
    }

    /**
     * Checks whether a given nickname is a valid nickname. A valid nickname is any nickname that is unique among other nicknames, and contains one or more of the characters A-Z, a-z, ÅÄÖ, åäö, 0-9, (), period, underscore, and space.
     * @param nickname the nickname to validate
     * @return true if the nickname is valid; false otherwise
     */
    public boolean isNicknameValid(String nickname) {
        return this.userLedger.isNicknameValid(nickname);
    }

    /**
     * Registers a new user in the system.
     * @param nickname the nickname of the new User
     * @param name the name of the new User
     * @param password the plaintext password of the new User
     * @param birthday the Birthday instance of the new User
     * @param admittance the Admittance instance of the new User
     */
    public void registerUser(String nickname, String name, String password, Birthday birthday, Admittance admittance) {
        this.userLedger.registerUser(nickname, name, password, birthday, admittance);
    }
}
