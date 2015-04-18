package capstat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import capstat.utils.Security;

public class UserLedger {

    private static UserLedger instance;
    private List<User> users;

    private UserLedger() {
        this.users = new ArrayList<>();
    }

    public static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    /**
     * Checks whether a given nickname is a valid nickname. A valid nickname is any nickname that is unique among other nicknames, and contains one or more of the characters A-Z, a-z, ÅÄÖ, åäö, 0-9, (), period, underscore, and space.
     * @return true if the nickname is valid; false otherwise
     */
    public boolean isNicknameValid(String nickname) {
        Stream<Boolean> conflicts = this.users.stream().map(u -> u.getNickname().equals(nickname));
        Predicate<Boolean> noConflict = conflict -> conflict == false;
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\._\\-]+$") && conflicts.allMatch(noConflict);
    }

    /**
     * Registers a new user in the system.
     * @param nickname the nickname of the new User
     * @param name the name of the new User
     * @param password the plaintext password of the new User
     * @param birthday the Birthday instance of the new User
     * @param admittance the Admittance instance of the new User
     *
     * @pre this.isNicknameValid(nickname) == true
     */
    public void registerUser(String nickname, String name, String password, Birthday birthday, Admittance admittance) {
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);
        String hashedPassword = Security.hashPassword(password);
        ELORanking ranking = ELORanking.defaultRanking();
        this.createUserInDatabase(nickname, name, hashedPassword, chalmersAge, ranking);
    }

    /**
     * Adds a new User to the database.
     * @param nickname the nickname of the new User
     * @param name the name of the new User
     * @param hashedPassword the hashed password of the new User
     * @param chalmersAge the ChalmersAge instance of the new User
     * @param ranking the ELORanking instance of the new User
     *
     * @pre this.isNicknameValid(nickname) == true
     */
    private void createUserInDatabase(String nickname, String name, String hashedPassword, ChalmersAge chalmersAge, ELORanking ranking) {
        User user = new User(nickname, name, hashedPassword, chalmersAge, ranking);
        users.add(user);
    }
}
