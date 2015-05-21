package capstat.model;

import capstat.infrastructure.DatabaseHelperFactory;
import capstat.infrastructure.UserBlueprint;
import capstat.infrastructure.UserDatabaseHelper;

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
    private Map<String, User> users;
    private UserDatabaseHelper dbHelper;

    //Creation

    private UserLedger() {
        this.users = new HashMap<>();
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
        return user;
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
        ChalmersAge chalmersAge = user.getChalmersAge();
        LocalDate birthday = chalmersAge.getBirthday();
        Admittance admittance = chalmersAge.getAdmittance();
        UserBlueprint blueprint = new UserBlueprint(
                user.getNickname(),
                user.getName(),
                user.getHashedPassword(),
                birthday.getYear(),
                birthday.getMonthValue(),
                birthday.getDayOfMonth(),
                admittance.getYear().getValue(),
                admittance.getReadingPeriod().ordinal() + 1,
                user.getRanking().getPoints());
        this.dbHelper.addUserToDatabase(blueprint);
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
        String ret = "";
        for (User user : this.users.values()) {
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
