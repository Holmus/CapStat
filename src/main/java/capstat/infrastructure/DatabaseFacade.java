package capstat.infrastructure;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Users;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hjorthjort
 *
 * Using CSV standard to store database data as strings
 */
//TODO Make class implement a MatchDatabaseHelper
class DatabaseFacade implements UserDatabaseHelper, MatchDatabaseHelper {

    // Path and filename as string.
    File dbQueue = new File("dbqueue.txt");
    DatabaseConnection db = new DatabaseConnection();


	// START USERS

    @Override
    public void addUser(final UserBlueprint user) {

        // TODO this should call a new thread to handle the txt-to-database process.

        TextFileTaskQueue txtQ = null;

        // ADDS USER INSERTION TO QUEUE
        try {
            txtQ = new TextFileTaskQueue(dbQueue);
            txtQ.add(userBlueprintToQueueEntry(user));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // INSERTS THE FIRST USER IN THE QUEUE TO THE DATABASE
        String[] parsed = queryStringParser(txtQ.peek());

        db.database.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
                Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
                .values(parsed[0], parsed[1], parsed[2],
                        new java.sql.Date(Integer.parseInt(parsed[3]) - 1900, Integer.parseInt(parsed[4]) - 1, Integer.parseInt(parsed[5])),
                        Integer.parseInt(parsed[6]), Integer.parseInt(parsed[7]), Double.parseDouble(parsed[8])).execute();

        // DELETE USER FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

        UserBlueprint insertedUser = getUserByNickname(parsed[0]);
        if ( userBlueprintToQueueEntry(insertedUser).equals(txtQ.peek()) ) {
            try {
                txtQ.pop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addUserSet(final Set<UserBlueprint> userSet) {
        userSet.forEach(this::addUser);
    }

    @Override
    public void removeUser(final UserBlueprint user) {
        db.database.deleteFrom(Users.USERS).where(Users.USERS.NICK.equal(user.nickname)).execute();
        //TODO check if this is done or not.
    }

    @Override
    public UserBlueprint getUserByNickname(final String nickname) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NICK.equal(nickname)).fetch();
        return getUsersFromResult(result).iterator().next();
    }
    @Override
    public UserBlueprint getUserByName(final String name) {
        //TODO A user cannot be identified by name, this could result in more than one user entry. now only returns the first found.
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.equal(name)).fetch();
        return getUsersFromResult(result).iterator().next();
    }

    @Override
    public Set<UserBlueprint> getUsersByNicknameMatch(final String regex) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NICK.likeRegex("" + regex)).fetch();
        return getUsersFromResult(result);
    }

    @Override
    public Set<UserBlueprint> getUsersByNameMatch(final String regex) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.likeRegex(regex)).fetch();
        return getUsersFromResult(result);
    }

    @Override
    public Set<UserBlueprint> getUsersInELORankRange(final double minELO, final double maxELO) {
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.ELORANK.between(minELO).and(maxELO)).fetch();
        return getUsersFromResult(result);
    }

    private Set<UserBlueprint> getUsersFromResult (Result<Record> result) {
        Set<UserBlueprint> userSet = new HashSet<>();
        String resultString = result.formatCSV();
        String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");

        for (String s : rows) {
            if (s.length() > 0) {
                userSet.add(dbEntryToUserBlueprint(queryStringParser(s)));
            }
        }
        return userSet;
    }

    private String userBlueprintToQueueEntry(UserBlueprint ubp) {
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

    private UserBlueprint dbEntryToUserBlueprint(String[] s) {
        //TODO parse the database fetch to a userBlueprint
        //TODO Should this return an object from the UserLedger instead of creating a new UserBlueprint-object?
        String[] date = s[3].split("-");
        return new UserBlueprint(s[0],s[1],s[2],Integer.parseInt(date[0]),Integer.parseInt(date[1]),
                Integer.parseInt(date[2]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Double.parseDouble(s[6]));
    }

	// END USERS

	// START MATCHES



	@Override
	public void addMatch(MatchResultBlueprint match, ThrowSequenceBlueprint throwSequence) {

	}

	@Override
	public void addMatchSet(Set<MatchResultBlueprint> matches, Set<ThrowSequenceBlueprint> throwSequences) {

	}

	@Override
	public void removeMatch(int id) {

	}

	@Override
	public MatchResultBlueprint getMatch(int id) {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getAllMatches() {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForUser(String player) {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForUsers(String p1, String p2) {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesInDateRange(Date from, Date to) {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getMatchForSpectator(String spectator) {
		return null;
	}

	private Set<MatchResultBlueprint> getMatchesFromResult (Result<Record> result) {
		Set<MatchResultBlueprint> matchSet = new HashSet<>();
		String resultString = result.formatCSV();
		String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");

		for (String s : rows) {
			if (s.length() > 0) {
				matchSet.add(dbEntryToMatchBlueprint(queryStringParser(s)));
			}
		}
		return matchSet;
	}

	private String matchBlueprintToQueueEntry(MatchResultBlueprint mbp) {
		return mbp.id + "," +
				mbp.p1 + "," +
				mbp.p2 + "," +
				mbp.p1Score + "," +
				mbp.p2Score + "," +
				mbp.spectator + "," +
				mbp.year + "," +
				mbp.month + "," +
				mbp.day + "," +
				mbp.hour + "," +
				mbp.minute + "," +
				mbp.second;

	}

	private MatchResultBlueprint dbEntryToMatchBlueprint(String[] s) {
		//TODO parse the database fetch to a matchBlueprint
		//TODO Should this return an object from the ResultLedger instead of creating a new MatchResultBlueprint-object?
		return null;
	}

	// END MATCHES

	// START COMMON HELP METHODS

	private String[] queryStringParser (String s) {
		return s.split(",");
	}

	// END COMMON HELP METHODS

}


