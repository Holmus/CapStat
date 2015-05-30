package capstat.infrastructure.database;

import capstat.infrastructure.taskqueue.ITaskQueue;
import capstat.infrastructure.taskqueue.TextFileTaskQueue;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Matches;
import org.jooq.generated.db.capstat.tables.Throwsequences;
import org.jooq.generated.db.capstat.tables.Users;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A facade to be used for interaction to the database.
 * This is divided into three major parts:
 * * Interaction with the User table.
 * * Interaction with the Match (and ThrowSequence) table.
 * * Common methods, mostly conversions between formats.
 * @Author Johan Andersson
 * @Author Rikard Hjort
 */
class DatabaseFacade implements UserDatabaseHelper, ResultDatabaseHelper {

    // Path and filename as string.
	// TODO Fix/Check what happens if the files already exists with content?
    DatabaseConnection db = new DatabaseConnection();
    private File dbUserQueue = new File("dbuserqueue.txt");
	private File dbMatchResultQueue = new File("dbmatchqueue.txt");
	private File dbPartialSequenceQueue = new File("dbthrowsqueue.txt");
	private ITaskQueue txtUserQueue, txtMatchResultQueue, txtPartialSequenceQueue = null;
	private BackgroundThread backgroundThread;


	// START USERS

    @Override
    public void addUser(final UserBlueprint user) {
        // ADDS USER INSERTION TO QUEUE
        try {
	        if (txtUserQueue == null) {
		        txtUserQueue = new TextFileTaskQueue(dbUserQueue);
	        }
	        txtUserQueue.add(userBlueprintToQueueEntry(user));

            //Start a new Thread that empties the task queue and inserts users
            // from the queue into the database.
	        if (backgroundThread == null ) {
		        backgroundThread = new BackgroundThread();
	        } else if ( !backgroundThread.isAlive() ){
		        backgroundThread.run();
	        }
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
	public void overwriteUser(final UserBlueprint user) {
		removeUser(user);
		addUser(user);
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
    public Set<UserBlueprint> getUsersByName(final String name) {
        //TODO A user cannot be identified by name, this could result in more than one user entry. now only returns the first found.
        Result<Record> result = db.database.select().from(Users.USERS).where(Users.USERS.NAME.equal(name)).fetch();
	    return getUsersFromResult(result);
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

	/*
     * Using CSV standard to store database data as strings
     */
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
			if (txtMatchResultQueue == null) {
				txtMatchResultQueue = new TextFileTaskQueue(dbMatchResultQueue);
			}
			if (txtPartialSequenceQueue == null) {
				txtPartialSequenceQueue = new TextFileTaskQueue(dbPartialSequenceQueue);
			}
			txtMatchResultQueue.add(matchResultBlueprintToQueueEntry(match));
			if (!match.sequences.isEmpty()) {
				for (int i = 0 ; match.sequences.size() > i ; i++) {
					txtPartialSequenceQueue.add(match.id + "," + i + "," + partialSequenceBlueprintToQueueEntry(match.sequences.get(i)));
				}
			}

			// Start a new Thread that empties the task queue and inserts users
			// from the queue into the database.
			if (backgroundThread == null ) {
				backgroundThread = new BackgroundThread();
			} else if ( !backgroundThread.isAlive() ){
				backgroundThread.run();
			}
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
						Integer.parseInt(parsed[5]), parsed[6], parsed[7]).execute();
		// DELETE MATCH RESULT FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

		MatchResultBlueprint insertedMatchResult = getMatchById(Long.parseLong(parsed[0]));
		if ( matchResultBlueprintToQueueEntry(insertedMatchResult).equals(txtMatchResultQueue.peek()) ) {
			try {
				txtMatchResultQueue.pop();
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
		.values(parsed[0], Integer.parseInt(parsed[1]), parsed[2], parsed[3], Byte.parseByte(parsed[4]), parsed[5]).execute();


		// DELETE THROW SEQUENCE FROM QUEUE IF SUCCESSFUL INSERT INTO DATABASE

		PartialSequenceBlueprint insertedPartialSequence = getMatchById(Long.parseLong(parsed[0])).sequences.get(Integer.parseInt(parsed[1]));
		if ( (parsed[0] + "," + parsed[1] + "," + partialSequenceBlueprintToQueueEntry(insertedPartialSequence)).equals(txtPartialSequenceQueue.peek()) ) {
			try {
				txtPartialSequenceQueue.pop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	@Override
	public void addMatchSet(Set<MatchResultBlueprint> matchSet) {
		matchSet.forEach(this::addMatch);
	}

	@Override
	public void removeMatch(long id) {
		db.database.deleteFrom(Throwsequences.THROWSEQUENCES)
				.where(Throwsequences.THROWSEQUENCES.MATCHID.equal(String.valueOf(id)))
				.execute();
		db.database.deleteFrom(Matches.MATCHES).where(Matches.MATCHES.ID.equal(String.valueOf(id)))
				.execute();
	}

	@Override
	public MatchResultBlueprint getMatchById(long id) {
		Result<Record> result = db.database.select().from(Matches.MATCHES)
				.where(Matches.MATCHES.ID.equal(String.valueOf(id)))
				.fetch();
		Set<MatchResultBlueprint> mrbSet = getMatchesFromResult(result);
		return mrbSet.iterator().hasNext() ? mrbSet.iterator().next() : null;
	}

	@Override
	public Set<MatchResultBlueprint> getAllMatches() {
		Result<Record> result = db.database.select().from(Matches.MATCHES)
				.fetch();
		return getMatchesFromResult(result);
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForUser(String player) {
		Result<Record> result = db.database.select().from(Matches.MATCHES)
				.where(Matches.MATCHES.P1.equal(player)
						.or(Matches.MATCHES.P2.equal(player)))
				.fetch();
		return getMatchesFromResult(result);
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForUsers(String p1, String p2) {
			Result<Record> result = db.database.select().from(Matches.MATCHES)
					.where(Matches.MATCHES.P1.equal(p1)
							.and(Matches.MATCHES.P2.equal(p2)))
					.or(Matches.MATCHES.P1.equal(p2)
							.and(Matches.MATCHES.P2.equal(p1)))
					.fetch();
			return getMatchesFromResult(result);
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesInDateRange(long epochFrom, long epochTo) {
		Result<Record> result = db.database.select().from(Matches.MATCHES)
				.where(Matches.MATCHES.ENDTIME.between(String.valueOf(epochFrom)).and(String.valueOf(epochTo)))
						.fetch();
		return getMatchesFromResult(result);
	}

	@Override
	public Set<MatchResultBlueprint> getMatchesForSpectator(String spectator) {
		Result<Record> result = db.database.select().from(Matches.MATCHES)
				.where(Matches.MATCHES.SPECTATOR.equal(spectator))
				.fetch();
		return getMatchesFromResult(result);
	}

	private List<PartialSequenceBlueprint> getPartialSequencesByMatchId(long id) {
		Result<Record> result = db.database.select().from(Throwsequences.THROWSEQUENCES)
				.where(Throwsequences.THROWSEQUENCES.MATCHID.equal(String.valueOf(id))).fetch();
		return getPartialSequencesFromResult(result);
	}

	private List<PartialSequenceBlueprint> getPartialSequencesFromResult(Result<Record> result) {
		String resultString = result.formatCSV();
		String[] rows = resultString.substring(resultString.indexOf(System.getProperty("line.separator")) + 1).split("\n");

		List<PartialSequenceBlueprint> psbList = new ArrayList<>();
		Arrays.sort(rows);
		for (String s : rows) {
			if (s.length() > 0) {
				psbList.add(dbEntryToPartialSequenceBlueprint(queryStringParser(s)));
			}
		}
		return psbList;
	}

	private PartialSequenceBlueprint dbEntryToPartialSequenceBlueprint(String[] s) {
		return new PartialSequenceBlueprint(bitStringToBooleans(s[2]),
				Integer.parseInt(s[3]), s[4].equals("1"), bitStringToBooleans(s[5]));
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

	/*
	 * Using CSV standard to store database data as strings
	 */
	private String matchResultBlueprintToQueueEntry(MatchResultBlueprint mrb) {
		return String.format("%d,%s,%s,%s,%d,%d,%d,%d", mrb.id, mrb.player1Nickname, mrb.player2Nickname,
				mrb.spectatorNickname, mrb.player1score, mrb.player2score, mrb.startTime, mrb.endTime);
	}

	/*
	 * Using CSV standard to store database data as strings
	 */
	private String partialSequenceBlueprintToQueueEntry(PartialSequenceBlueprint psb) {
		return booleansTobitString(psb.glasses) + "," +
				psb.startingPlayer + "," +
				(psb.throwBeforeWasHit ? "1" : "0") + "," +
				booleansTobitString(psb.sequence);
	}



	private MatchResultBlueprint dbEntryToMatchBlueprint(String[] s) {

		//TODO parse the database fetch to a matchBlueprint

		return new MatchResultBlueprint(Long.parseLong(s[0]),s[1],s[2],s[3],Integer.parseInt(s[4]),
				Integer.parseInt(s[5]), Long.parseLong(s[6]), Long.parseLong(s[7]),
				getPartialSequencesByMatchId(Long.parseLong(s[0])));
	}

	// END MATCHES

	// START COMMON HELP METHODS

	private String[] queryStringParser (String s) {
		return s.split(",");
	}

	private String booleansTobitString (boolean[] b) {
		String s = "";
		for (int i = 0 ; b.length > i ; i++) {
			s += b[i] ? 1 : 0;
		}
		return s;
	}

	private boolean[] bitStringToBooleans (String s) {
		boolean[] b = new boolean[s.length()];
		for (int i = 0 ; s.length() > i ; i++) {
			String str = "" + s.charAt(i);
			b[i] = str.equals("1");
		}
		return b;
	}

	// END COMMON HELP METHODS


	private class BackgroundThread extends Thread {

		BackgroundThread() {
			start();
		}

		public void run() {
			boolean chores = true;
			while(chores) {
				chores = false;
				if (txtUserQueue != null) {
					while (txtUserQueue.hasElements()) {
						chores = true;
						addUsersFromQueueToDatabase();
					}
					txtUserQueue.delete();
				}
				if (txtMatchResultQueue != null) {
					while (txtMatchResultQueue.hasElements()) {
						chores = true;
						addMatchesFromQueueToDatabase();
					}
					txtMatchResultQueue.delete();
				}
				if (txtPartialSequenceQueue != null) {
					while (txtPartialSequenceQueue.hasElements()) {
						chores = true;
						addPartialSequencesFromQueueToDatabase();
					}
					txtPartialSequenceQueue.delete();
				}

			}
		}
	}
}


