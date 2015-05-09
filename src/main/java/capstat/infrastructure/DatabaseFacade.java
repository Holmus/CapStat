package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 */
public class DatabaseFacade implements UserDatabaseHelper {

    @Override
    public void addUserToDatabase(final UserDatabaseRow user) {

    }

    @Override
    public void addUserSetToDatabase(final Set<UserDatabaseRow> userSet) {

    }

    @Override
    public void removeUserFromDatabase(final UserDatabaseRow user) {

    }

    @Override
    public UserDatabaseRow getUserByNickname(final String nickname) {
        return null;
    }

    @Override
    public UserDatabaseRow getUserByName(final String name) {
        return null;
    }

    @Override
    public Set<UserDatabaseRow> getUsersByNicknameMatch(final String regex) {
        return null;
    }

    @Override
    public Set<UserDatabaseRow> getUsersByNameMatch(final String regex) {
        return null;
    }

    @Override
    public Set<UserDatabaseRow> getUsersInELORankRange(final double minELO,
                                                       final double maxELO) {
        return null;
    }
}
