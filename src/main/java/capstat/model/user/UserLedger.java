package capstat.model.user;

import capstat.infrastructure.database.DatabaseHelperFactory;
import capstat.infrastructure.database.UserBlueprint;
import capstat.infrastructure.database.UserDatabaseHelper;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A class representing a book or ledger where all users are stored, and from
 * which Users can be retrieved. This class represents a repository in
 * the domain and delegates the issue of persistant sotrage of User objects.
 */
public class UserLedger {

    private static UserLedger instance;
    //This map contains all active users in the system. This saves time, and
    // also guarantees that there are no duplicate objects representing the
    // same user.
    private Map<String, User> users;
    private UserDatabaseHelper dbHelper;

    //Creation

    private UserLedger() {
        this.users = new HashMap<>();
        this.dbHelper = new DatabaseHelperFactory().createUserQueryHelper();
    }

    /**
     * @return the only instance of UserLedger
     */
    public synchronized static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    //Retrieval

    /**
     *
     * @param nickname the nickname of the player to fetch from this repository
     * @return the User requested. If no such user exists, return null
     */
    public User getUserByNickname(String nickname) {
        //First, check if the object is in memory, return it if it is. The
        // structure of the database guarantees that only one user can have a
        // certain nickname.
        if(this.users.containsKey(nickname)) return this.users.get(nickname);
        UserBlueprint blueprint = this.dbHelper.getUserByNickname(nickname);
        User user = blueprint == null ? null : this
                .reconstituteUserFromBlueprint
                (blueprint);
        if (user != null)
            this.overwriteUser(user);
        return user;
    }

    private void overwriteUser(final User user) {
        this.users.remove(user.getNickname());
        this.users.put(user.getNickname(), user);
        this.dbHelper.overwriteUser(this.createBlueprint(user));
    }

    private User reconstituteUserFromBlueprint(final UserBlueprint blueprint) {
         LocalDate birthday = LocalDate.of(blueprint.birthdayYear, blueprint
                .birthdayMonth, blueprint.birthdayDay);
        //The blueprint object gives either reading period 1, 2, 3, or 4. In
        // the Admittance class, this is an Enum, where ONE is represented by
        // 0, TWO by 1, and so on (zero indexing). Thereby the row below.
        Admittance admittance = new Admittance(blueprint.admittanceYear,
                Admittance.Period.values()[blueprint.admittanceReadingPeriod-1]);
        User user = UserFactory.createOldUser(blueprint.nickname, blueprint
                        .name,
                blueprint.hashedPassword, birthday, admittance.getYear(),
                admittance.getReadingPeriod().ordinal()+1, blueprint
                        .ELORanking);
        return user;
    }

    public UserBlueprint createBlueprint(User user) {
        ChalmersAge age = user.getChalmersAge();
        return new UserBlueprint(user.getNickname(), user.getName(), user
                .getHashedPassword(), age.getBirthday().getYear(), age
                .getBirthday().getMonthValue(), age.getBirthday()
                .getDayOfMonth(), age.getAdmittance().getYearValue(), age
                .getAdmittance().getReadingPeridoValue(), user
                .getRanking().getPoints());
    }

    //Registration

    /**
     * Registers a new user in the system.
     * @param nickname the nickname of the new User
     * @param name the name of the new User
     * @param password the plaintext password of the new User
     * @param birthday the birthday of the new User
     * @param admittanceYear the admittance year  of the new User
     * @param readingPeriod the reading period in which this user was admitted.
     *
     * @pre this.isNicknameValid(nickname) == true
     */
    public void registerNewUser(String nickname, String name, String
            password, LocalDate birthday, Year admittanceYear, int
            readingPeriod) {
        //Nickname must be valid
        if (!this.isNicknameValid(nickname)) throw new
                IllegalArgumentException("Invalid nickname");
        User user = UserFactory.createNewUser(nickname, name, password,
                birthday, admittanceYear, readingPeriod);
        this.addUserToLedger(user);
    }

    /**
     * Adds a new User to the database.
     * @param user the user to be added
     *
     * @pre this.isNicknameValid(nickname) == true
     */
    private void addUserToLedger(User user) {
        if (this.users.containsKey(user.getNickname())) {
            this.users.remove(user.getNickname());
        }
        this.users.put(user.getNickname(), user);
        UserBlueprint blueprint = createBlueprint(user);
        this.dbHelper.addUser(blueprint);
    }

    public void removeUserFromLedger(User user) {
        if (this.users.containsKey(user.getNickname())) {
            this.users.remove(user.getNickname());
        }
        this.dbHelper.removeUser(createBlueprint(user));
    }

    public void clearCache() {
        this.users.clear();
    }

    //Utils

    /**
     * Checks to see whether a user with the supplied nickname exists in the
     * ledger.
     * @param nickname
     * @return true if the user exists, otherwise false.
     */
    public boolean doesUserExist(String nickname) {
        if (this.users.containsKey(nickname)) {
            return true;
        }
        return this.dbHelper.getUserByNickname(nickname) != null;
    }

    /**
     * Checks whether a given nickname is a valid nickname. A valid nickname is any nickname that is unique among other nicknames, and contains one or more of the characters A-Z, a-z, ÅÄÖ, åäö, 0-9, (), period, underscore, and space.
     * @return true if the nickname is valid; false otherwise
     */
    public boolean isNicknameValid(String nickname) {
        if (this.doesUserExist(nickname)) {
            return false;
        }
        Stream<Boolean> conflicts = this.users.keySet().stream().map(u -> u
                .equals(nickname));
        Predicate<Boolean> noConflict = conflict -> conflict == false;
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\._\\-]+$") && conflicts.allMatch(noConflict);
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        for (User user : this.users.values()) {
            ret.append("Nickname: " + user.getNickname());
            ret.append("\nName: " + user.getName());
            ret.append("\nPassword (hashed): " + user.getHashedPassword());
            ret.append("\nAge: " + user.getChalmersAge());
            ret.append("\nRanking: " + user.getRanking());
            ret.append("\n");
        }
        return ret.toString();
    }

    public void save() {
        for (User user : this.users.values()) {
            this.dbHelper.overwriteUser(this.createBlueprint(user));
        }
    }
}
