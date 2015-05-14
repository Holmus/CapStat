package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 */
//TODO Make class implement a MatchDatabaseHelper
class DatabaseFacade implements UserDatabaseHelper {

    @Override
    public void addUserToDatabase(final UserBlueprint user) {
        //TODO Implement adding user to databse
    }

    @Override
    public void addUserSetToDatabase(final Set<UserBlueprint> userSet) {
        //TODO Implement adding several users at once
    }

    @Override
    public void removeUserFromDatabase(final UserBlueprint user) {
        //TODO Implement removing single user from database
    }

    @Override
    public UserBlueprint getUserByNickname(final String nickname) {
        //TODO get user by exact nickname match
        return null;
    }

    @Override
    public UserBlueprint getUserByName(final String name) {
        //TODO get user by exact name match
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersByNicknameMatch(final String regex) {
        //TODO Implement getting a set of users by a matching regex
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersByNameMatch(final String regex) {
        //TODO Implement geting a set of users by matching their names to a
        // regex
        return null;
    }

    @Override
    public Set<UserBlueprint> getUsersInELORankRange(final double minELO,
                                                       final double maxELO) {
        //TODO Implement getting all users that have an ELO ranking in a
        // given, inclusive range.
        return null;
    }
}
