package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 */
public class DatabaseFacade implements UserDatabaseHelper {

    @Override
    public void addUserToDatabase(final UserBlueprint user) {

    }

    @Override
    public void addUserSetToDatabase(final Set<UserBlueprint> userSet) {

    }

    @Override
    public void removeUserFromDatabase(final UserBlueprint user) {

    }

    @Override
    public UserBlueprint getUserByNickname(final String nickname) {
        return null;
    }

    @Override
    public UserBlueprint getUserByName(final String name) {
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersByNicknameMatch(final String regex) {
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersByNameMatch(final String regex) {
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersInELORankRange(final double minELO,
                                                       final double maxELO) {
        return null;
    }
}
