package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 * An interface for a Facade used to get and add users from the database.
 */
public interface UserDatabaseHelper {

    void addUserToDatabase(UserDatabaseRow user);
    void addUserSetToDatabase(Set<UserDatabaseRow> userSet);
    void removeUserFromDatabase(UserDatabaseRow user);
    UserDatabaseRow getUserByNickname(String nickname);
    UserDatabaseRow getUserByName(String name);
    Set<UserDatabaseRow> getUsersByNicknameMatch(String regex);
    Set<UserDatabaseRow> getUsersByNameMatch(String regex);
    Set<UserDatabaseRow> getUsersInELORankRange(double minELO, double maxELO);
}
