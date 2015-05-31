package capstat.infrastructure.database;

import java.util.Set;

/**
 * @author hjorthjort
 * An interface for a Facade used to get and add users from the database.
 */
public interface UserDatabaseHelper {

	/**
	 * Stores a user in the database.
	 * @param user The blueprint of a user to be inserted into the database.
	 */
    void addUser(UserBlueprint user);

	/**
	 * Stores a set of users in the database.
	 * @param userSet The set of blueprints of users to be inserted into the database.
	 */
    void addUserSet(Set<UserBlueprint> userSet);

    /**
     * Removes a user from the database and then adds it again with its new values.
     * @param user The user with its new credentials (nickname must be the same).
     */
    void overwriteUser(UserBlueprint user);

    /**
     * Removes a user from the database.
     * @param user the user to remove.
     */
    void removeUser(UserBlueprint user);

	/**
	 * Returns the user matching the specified nickname.
	 * @param nickname The nickname of the user to be found
	 * @return UserBlueprint User blueprint for the user with the given nickname
	 * if that user exists in the database. Returns null otherwise.
	 */
    UserBlueprint getUserByNickname(String nickname);

    /**
     * Returns a set of users matching a specified name.
     * @param name The name of the users to be found.
     * @return Set The set of users matching the name.
     */
    Set<UserBlueprint> getUsersByName(String name);

	/**
	 * Returns a set of users matching a specified nickname (even partly).
	 * @param regex The string search for in all users nicknames.
	 * @return Set The set of users matching the nickname (even partly).
	 */
    Set<UserBlueprint> getUsersByNicknameMatch(String regex);

	/**
	 * Returns a set of users matching a specified name (even partly).
	 * @param regex The string search for in all users names.
	 * @return Set The set of users matching the name (even partly).
	 */
    Set<UserBlueprint> getUsersByNameMatch(String regex);

    /**
     * Returns a set of users which all has an ELO-rank in the specified range.
     * @param minELO The lower value in the range to search for.
     * @param maxELO The upper value in the range to search for.
     * @return Set The set of users that have an ELO-rank within the given range.
     */
    Set<UserBlueprint> getUsersInELORankRange(double minELO, double maxELO);
}
