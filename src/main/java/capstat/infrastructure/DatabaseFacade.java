package capstat.infrastructure;

import capstat.model.User;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Users;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hjorthjort
 *
 * Using CSV standard to store database data as strings
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
            txtQ.add(userBluePrintToQueueEntry(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO the date adds 1900 years when inserted in db, find out why and fix it.
        // INSERTS THE FIRST USER IN THE QUEUE TO THE DATABASE
        String[] parsed = queryStringParser(txtQ.peek());

        db.database.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
                Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
                .values(parsed[0], parsed[1], parsed[2],
                        new java.sql.Date(Integer.parseInt(parsed[3]) - 1900, Integer.parseInt(parsed[4]) - 1, Integer.parseInt(parsed[5])),
                        Integer.parseInt(parsed[6]), Integer.parseInt(parsed[7]), Double.parseDouble(parsed[8])).execute();

        // DELETE USER FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

        UserBlueprint insertedUser = getUserByNickname(parsed[0]);
        if ( userBluePrintToQueueEntry(insertedUser).equals(txtQ.peek()) ) {
            try {
                txtQ.pop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    public void addUserSetToDatabase(final Set<UserBlueprint> userSet) {
        userSet.forEach(this::addUserToDatabase);
    }

    @Override
    public void removeUserFromDatabase(final UserBlueprint user) {
        db.database.deleteFrom(Users.USERS).where(Users.USERS.NICK.equal(user.nickname)).execute();
        //TODO check if this is done or not.
    }

    @Override
    public UserBlueprint getUserByNickname(final String nickname) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NICK.equal(nickname)).fetch();
        String resultString = result.formatCSV();
        String[] parsed = queryStringParser(resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1));
        return dbEntryToUserBluePrint(parsed);
    }

    @Override
    public UserBlueprint getUserByName(final String name) {
        //TODO A user cannot be identified by name, this could result in more than one user entry. now only returns the first found.
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.equal(name)).fetch();
        String resultString = result.formatCSV();
        String[] parsed = queryStringParser(resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1));
        return dbEntryToUserBluePrint(parsed);
    }

    @Override
    public Set<UserBlueprint> getUsersByNicknameMatch(final String regex) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NICK.likeRegex("" + regex)).fetch();
        Set<UserBlueprint> userSet = new HashSet<>();
        String resultString = result.formatCSV();
        String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");

        for (String s : rows) {
            userSet.add(dbEntryToUserBluePrint(queryStringParser(s)));
        }

        //TODO Implement getting a set of users by a matching regex
        // Is this a partly matched string/entry?

        return userSet;
    }

    @Override
    public Set<UserBlueprint> getUsersByNameMatch(final String regex) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.likeRegex(regex)).fetch();
        Set<UserBlueprint> userSet = new HashSet<>();
        String resultString = result.formatCSV();
        String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");
        for (String s : rows) {
            userSet.add(dbEntryToUserBluePrint(queryStringParser(s)));
        }

        //TODO Implement getting a set of users by a matching their name to a regex
        // Is this a partly matched string/entry?

        return userSet;
    }

    @Override
    public Set<UserBlueprint> getUsersInELORankRange(final double minELO,
                                                     final double maxELO) {
        //TODO Implement getting all users that have an ELO ranking in a
        // given, inclusive range.
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.ELORANK.between(minELO).and(maxELO)).fetch();
        Set<UserBlueprint> userSet = new HashSet<>();
        String resultString = result.formatCSV();
        String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");

        for (String s : rows) {
            if ( s.length() > 0 ) {
                userSet.add(dbEntryToUserBluePrint(queryStringParser(s)));
            }
        }

        return userSet;
    }

    private String[] queryStringParser (String s) {
        return s.split(",");
    }

    private String userBluePrintToQueueEntry(UserBlueprint ubp) {
        return ubp.nickname + "," +
                ubp.name + "," +
                ubp.hashedPassword + "," +
                ubp.birthdayYear + "," +
                ubp.birthdayMonth + "," +
                ubp.birthdayDay + "," +
                ubp.admittanceYear + "," +
                ubp.admittanceReadingPeriod + "," +
                ubp.ELORanking;
    }

    private UserBlueprint dbEntryToUserBluePrint(String[] s) {
        //TODO parse the database fetch to a userBluePrint
        //TODO Should this return an object from the UserLedger instead of creating a new UserBluePrint-object?
        String[] date = s[3].split("-");
        return new UserBlueprint(s[0],s[1],s[2],Integer.parseInt(date[0]),Integer.parseInt(date[1]),
                Integer.parseInt(date[2]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Double.parseDouble(s[6]));
    }
}
