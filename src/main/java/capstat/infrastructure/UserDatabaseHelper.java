package capstat.infrastructure;

import capstat.model.User;

import java.util.List;
import java.util.Set;

/**
 * @author hjorthjort
 * An interface for a Facade used to get and add users from the database.
 */
public interface UserDatabaseHelper {

    void addUserToDatabase(User user);
    void addUserSetToDatabase(Set<User> userSet);
    UserDatabaseRow getUserByNickname(String nickname);
    UserDatabaseRow getUserByName(String name);
    List<UserDatabaseRow> getUsersByAdmittance(int year, int readingPeriod);
    List<UserDatabaseRow> getUsersByNicknameMatch(String regex);
    List<UserDatabaseRow> getUsersByNameMatch(String regex);
    List<UserDatabaseRow> getUsersInELORankRange(int minELO, int maxELO);
}
