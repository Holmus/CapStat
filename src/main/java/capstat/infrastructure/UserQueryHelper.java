package capstat.infrastructure;

import capstat.model.User;

/**
 * @author hjorthjort
 */
public interface UserQueryHelper {
    void addUserToDatabase(User dummy);

    UserDatabaseRow getUserByNickname(String nickname);
}
