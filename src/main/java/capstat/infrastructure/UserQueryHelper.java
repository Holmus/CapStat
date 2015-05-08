package capstat.infrastructure;

import capstat.model.User;

import java.util.List;

/**
 * @author hjorthjort
 */
public interface UserQueryHelper {

    void addUserToDatabase(User dummy);
    UserDatabaseRow getUserByNickname(String nickname);
    UserDatabaseRow getUserByName(String name);
    List<UserDatabaseRow> getUsersByAdmittance(int year, int readingPeriod);
    List<UserDatabaseRow> getUsersByNicknameMatch(String regex);
    List<UserDatabaseRow> getUsersByNameMatch(String regex);
    List<UserDatabaseRow> getUsersInELORankRange(int minELO, int maxELO);
}
