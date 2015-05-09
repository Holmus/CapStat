package capstat.infrastructure;

import java.util.List;
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
    List<UserDatabaseRow> getUsersByAdmittance(int year, int readingPeriod);
    List<UserDatabaseRow> getUsersByNicknameMatch(String regex);
    List<UserDatabaseRow> getUsersByNameMatch(String regex);
    List<UserDatabaseRow> getUsersInELORankRange(int minELO, int maxELO);
}
