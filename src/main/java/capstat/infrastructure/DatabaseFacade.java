package capstat.infrastructure;

import capstat.model.ThrowSequence;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Matches;
import org.jooq.generated.db.capstat.tables.Throwsequences;
import org.jooq.generated.db.capstat.tables.Users;

import java.io.File;
import java.io.IOException;
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
	// TODO Fix/Check what happens if the files already exists with content?
    File dbUserQueue = new File("dbuserqueue.txt");
	File dbMatchResultQueue = new File("dbmatchqueue.txt");
	File dbPartialSequenceQueue = new File("dbthrowsqueue.txt");

    DatabaseConnection db = new DatabaseConnection();
    ITaskQueue txtUserQueue, txtMatchResultQueue, txtPartialSequenceQueue = null;

	// START USERS

    @Override
    public void addUser(final UserBlueprint user) {
        // ADDS USER INSERTION TO QUEUE
        try {
            txtUserQueue = new TextFileTaskQueue(dbUserQueue);
            txtUserQueue.add(userBlueprintToQueueEntry(user));

            //Start a new Thread that empties the task queue and inserts users
            // from the queue into the database.
            new Thread(() -> {
                while (txtUserQueue.hasElements()) {
	                addUsersFromQueueToDatabase();
                }
                txtUserQueue.delete();
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUsersFromQueueToDatabase() {
        // INSERTS THE FIRST USER IN THE QUEUE TO THE DATABASE
        String[] parsed = queryStringParser(txtUserQueue.peek());

        db.database.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
                Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
                .values(parsed[0], parsed[1], parsed[2],
                        new java.sql.Date(Integer.parseInt(parsed[3]) - 1900, Integer.parseInt(parsed[4]) - 1, Integer.parseInt(parsed[5])),
                        Integer.parseInt(parsed[6]), Integer.parseInt(parsed[7]), Double.parseDouble(parsed[8])).execute();

        // DELETE USER FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

        UserBlueprint insertedUser = getUserByNickname(parsed[0]);
        if ( userBlueprintToQueueEntry(insertedUser).equals(txtUserQueue.peek()) ) {
            try {
                txtUserQueue.pop();
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
	    Set<UserBlueprint> ub = getUsersFromResult(result);
	    return ub.iterator().hasNext() ? ub.iterator().next() : null;
    }
    @Override
    public UserBlueprint getUserByName(final String name) {
        //TODO A user cannot be identified by name, this could result in more than one user entry. now only returns the first found.
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.equal(name)).fetch();
	    Set<UserBlueprint> ub = getUsersFromResult(result);
	    return ub.iterator().hasNext() ? ub.iterator().next() : null;
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
        String[] date = s[3].split("-");
        return new UserBlueprint(s[0],s[1],s[2],Integer.parseInt(date[0]),Integer.parseInt(date[1]),
                Integer.parseInt(date[2]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Double.parseDouble(s[6]));
    }

	// END USERS

	// START MATCHES



	@Override
	public void addMatch(MatchResultBlueprint match) {
		// ADDS MATCH INSERTION TO QUEUE
		try {
			txtMatchResultQueue = new TextFileTaskQueue(dbMatchResultQueue);
			txtPartialSequenceQueue = new TextFileTaskQueue(dbPartialSequenceQueue);
			txtMatchResultQueue.add(matchResultBlueprintToQueueEntry(match));
			if (!match.sequences.isEmpty()) {
				for (int i = 0 ; match.sequences.size() > i ; i++) {
					txtPartialSequenceQueue.add(i + "," + match.sequences.get(i));
				}
			}

			// Start a new Thread that empties the task queue and inserts users
			// from the queue into the database.
			new Thread(() -> {
				while (txtMatchResultQueue.hasElements()) {
					addMatchesFromQueueToDatabase();
				}
				txtUserQueue.delete();
			}).start();
			new Thread(() -> {
				while (txtPartialSequenceQueue.hasElements()) {
					addPartialSequencesFromQueueToDatabase();
				}
				txtUserQueue.delete();
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addMatchesFromQueueToDatabase() {
		// INSERTS THE FIRST MATCH RESULT IN THE QUEUE TO THE DATABASE
		String[] parsed = queryStringParser(txtMatchResultQueue.peek());


		db.database.insertInto(Matches.MATCHES, Matches.MATCHES.ID, Matches.MATCHES.P1, Matches.MATCHES.P2, Matches.MATCHES.SPECTATOR,
				Matches.MATCHES.P1SCORE, Matches.MATCHES.P2SCORE, Matches.MATCHES.STARTTIME, Matches.MATCHES.ENDTIME)
				.values(parsed[0], parsed[1], parsed[2], parsed[3], Integer.parseInt(parsed[4]),
						Integer.parseInt(parsed[5]), parsed[6], parsed[7]);
		// DELETE MATCH RESULT FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

		MatchResultBlueprint insertedMatchResult = getMatchById(Long.parseLong(parsed[0]));
		if ( matchResultBlueprintToQueueEntry(insertedMatchResult).equals(txtMatchResultQueue.peek()) ) {
			try {
				txtUserQueue.pop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addPartialSequencesFromQueueToDatabase() {
		// INSERTS THE FIRST PARTIAL THROW SEQUENCE IN THE QUEUE TO THE DATABASE
		String[] parsed = queryStringParser(txtPartialSequenceQueue.peek());

		db.database.insertInto(Throwsequences.THROWSEQUENCES, Throwsequences.THROWSEQUENCES.MATCHID,
				Throwsequences.THROWSEQUENCES.SEQUENCEINDEX, Throwsequences.THROWSEQUENCES.GLASSES,
				Throwsequences.THROWSEQUENCES.STARTINGPLAYER, Throwsequences.THROWSEQUENCES.THROWBEFOREWASHIT,
				Throwsequences.THROWSEQUENCES.SEQUENCE)
		.values(parsed[1], Integer.parseInt(parsed[0]), parsed[2], parsed[3], Byte.parseByte(parsed[4]), parsed[5]);


		// DELETE THROW SEQUENCE FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

		PartialSequenceBlueprint insertedPartialSequence = getMatchById(Long.parseLong(parsed[0])).sequences.get(Integer.parseInt(parsed[1]));
		if ( partialSequenceBlueprintToQueueEntry(insertedPartialSequence).equals(txtPartialSequenceQueue.peek()) ) {
			try {
				txtUserQueue.pop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	@Override
	public void addMatchSet(Set<MatchResultBlueprint> matches) {

	}

	@Override
	public void removeMatch(long id) {

	}

	@Override
	public MatchResultBlueprint getMatchById(long id) {
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
	public Set<MatchResultBlueprint> getMatchesInDateRange(long epochFrom, long epochTo) {
		return null;
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForSpectator(String spectator) {
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

	private String matchResultBlueprintToQueueEntry(MatchResultBlueprint mrb) {
		return String.format("%d,%s,%s,%s,%d,%d,%l,%l", mrb.id, mrb.player1Nickname, mrb.player2Nickname,
				mrb.spectatorNickname, mrb.player1score, mrb.player2score, mrb.startTime, mrb.endTime);
	}


	private String partialSequenceBlueprintToQueueEntry(PartialSequenceBlueprint psb) {
		// TODO implement psb to queue entry. Begin with matchID-ish!!!
		return null;
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


