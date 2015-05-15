package capstat.infrastructure;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Users;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Set;

/**
 * @author hjorthjort
 */
//TODO Make class implement a MatchDatabaseHelper
class DatabaseFacade implements UserDatabaseHelper {

    // Path and filename as string.
    File dbQueue = new File("dbqueue.txt");
    DatabaseConnection db = new DatabaseConnection();

    @Override
    public void addUserToDatabase(final UserBlueprint user) {

        // TODO check how this should be implemented properly

        TextFileTaskQueue txtQ = null;

        // ADDS USER INSERTION TO QUEUE
        try {
            txtQ = new TextFileTaskQueue(dbQueue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            txtQ.add(userBluePrintToQueueEntry(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO the date adds 1900 years when inserted in db, find out why and fix it.
        // INSERTS THE FIRST USER IN THE QUEUE TO THE DATABASE
        String[] parsed = queryStringParser(txtQ.peek());
        db.database.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
                Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
                .values(parsed[1], parsed[0], parsed[2],
                        new java.sql.Date(Integer.parseInt(parsed[3]), Integer.parseInt(parsed[4]), Integer.parseInt(parsed[5])),
                        Integer.parseInt(parsed[6]), Integer.parseInt(parsed[7]), parsed[8]).execute();

        // DELETE USER FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

        UserBlueprint insertedUser = getUserByNickname(parsed[0]);

        if ( userBluePrintToQueueEntry(insertedUser).equals(txtQ.peek()) ) {
            try {
                String tmp = txtQ.pop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    public void addUserSetToDatabase(final Set<UserBlueprint> userSet) {


        //TODO Implement adding several users at once
    }

    @Override
    public void removeUserFromDatabase(final UserBlueprint user) {
        db.database.deleteFrom(Users.USERS).where(Users.USERS.NICK.equal(user.nickname)).execute();
        //TODO check if this is done or not.
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

    private String[] queryStringParser (String s) {
        return s.split(":");
    }

    private String userBluePrintToQueueEntry(UserBlueprint ubp) {
        return ubp.name + ":" +
                ubp.nickname + ":" +
                ubp.hashedPassword + ":" +
                ubp.birthdayYear + ":" +
                ubp.birthdayMonth + ":" +
                ubp.birthdayDay + ":" +
                ubp.admittanceYear + ":" +
                ubp.admittanceReadingPeriod + ":" +
                ubp.ELORanking;
    }

    private UserBlueprint dbEntryToUserBluePrint(String s) {
        //TODO parse the database fetch to a userBluePrint
        return null;
    }
}
