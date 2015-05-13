package capstat.model;

import capstat.infrastructure.DatabaseFacade;
import capstat.infrastructure.DatabaseHelperFactory;
import capstat.infrastructure.UserBlueprint;
import capstat.infrastructure.UserDatabaseHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A class representing a book or ledger where all users are stored, and from
 * which Users can be retrieved. This class represents a repository in
 * the domain and delegates the issue of persistant sotrage of User objects.
 */
public class UserLedger {

    private static UserLedger instance;
    private Collection<User> users;
    private UserDatabaseHelper dbHelper;

    //Creation

    private UserLedger() {
        this.users = new HashSet<>();
        this.dbHelper = new DatabaseHelperFactory().createUserQueryHelper();
    }

    /**
     * Returns the only instance of CapStat.
     * @return the only instance of CapStat
     */
    public synchronized static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    //Retrieval
    public User getUserByNickname(String nickname) {
        UserBlueprint blueprint = this.dbHelper.getUserByNickname(nickname);
        return this.reconstituteUserFromBlueprint(blueprint);
    }

    private User reconstituteUserFromBlueprint(final UserBlueprint blueprint) {
        //TODO
        return null;
    }

    //Registration

    /**
     * Registers a new user in the system.
     * @param nickname the nickname of the new User
     * @param name the name of the new User
     * @param password the plaintext password of the new User
     * @param birthday the birthday of the new User
     * @param admittance the Admittance instance of the new User
     *
     * @pre this.isNicknameValid(nickname) == true
     */
    public void registerNewUser(String nickname, String name, String
            password, LocalDate birthday, Admittance admittance) {
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

    //Utils

    /**
     * Checks whether a given nickname is a valid nickname. A valid nickname is any nickname that is unique among other nicknames, and contains one or more of the characters A-Z, a-z, ÅÄÖ, åäö, 0-9, (), period, underscore, and space.
     * @return true if the nickname is valid; false otherwise
     */
    public boolean isNicknameValid(String nickname) {
        Stream<Boolean> conflicts = this.users.stream().map(u -> u.getNickname().equals(nickname));
        Predicate<Boolean> noConflict = conflict -> conflict == false;
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\._\\-]+$") && conflicts.allMatch(noConflict);
    }

    public String toString() {
        String ret = "";
        for (User user : this.users) {
            ret.concat("Nickname: " + user.getNickname());
            ret.concat("\n\nName: " + user.getName());
            ret.concat("\nPassword (hashed): " + user.getHashedPassword());
            ret.concat("\nAge: " + user.getChalmersAge());
            ret.concat("\nRanking: " + user.getRanking());
            ret.concat("\n");
        }
        return ret;
    }
}
