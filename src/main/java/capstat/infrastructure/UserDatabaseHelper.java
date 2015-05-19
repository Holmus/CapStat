package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 * An interface for a Facade used to get and add users from the database.
 */
public interface UserDatabaseHelper {

    /**
     * Adds this user to the database. If one already exists, overwrite it.
     * @param user
     */
    void addUser(UserBlueprint user);
    void addUserSet(Set<UserBlueprint> userSet);
    void removeUser(UserBlueprint user);
    UserBlueprint getUserByNickname(String nickname);
    UserBlueprint getUserByName(String name);
    Set<UserBlueprint> getUsersByNicknameMatch(String regex);
    Set<UserBlueprint> getUsersByNameMatch(String regex);
    Set<UserBlueprint> getUsersInELORankRange(double minELO, double maxELO);
}
