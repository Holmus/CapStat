package capstat.tests;

import capstat.infrastructure.database.DatabaseConnection;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.db.capstat.tables.Users;
import org.jooq.generated.db.capstat.tables.records.UsersRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jibbs on 09/05/15.
 *
 * Performs a number of tests to manipulate the database in several
 * different ways using the jooq library.
 */
public class DatabaseConnectionTest {


	private DatabaseConnection dbConn;
	private DSLContext db;

	/**
	 * Setting up the connection using the DatabaseConnection-class.
	 */
	@Before
	public void initiateConnection() {
		this.dbConn = new DatabaseConnection();
		this.db = dbConn.database;
	}


	/**
	 * Deletes all users.
	 */
	@After
	public void deleteDummyUsers() {
		db.deleteFrom(Users.USERS).
				where(Users.USERS.NICK.equal("lol2kpe"))
				.or(Users.USERS.NICK.equal("user1"))
				.or(Users.USERS.NICK.equal("user2"))
				.execute();
	}

	/**
	 * Inserts three users.
	 */
	@Before
	public void insertUsers () {
		db.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
				Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
				.values("lol2kpe", "Johan Andersson", "lol", new java.sql.Date(1988-1900, 02-1, 29), 2012, 1, 1337.0 ).execute();
		db.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
				Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
				.values("user1", "Arne Ranta", "lol2", new java.sql.Date(1942-1900, 12-1, 30), 1971, 3, 123.4).execute();
		db.insertInto(Users.USERS, Users.USERS.NICK, Users.USERS.NAME, Users.USERS.PASS, Users.USERS.BIRTHDAY,
				Users.USERS.ADMITTANCEYEAR, Users.USERS.ADMITTANCEREADINGPERIOD, Users.USERS.ELORANK)
				.values("user2", "Ben Dover", "lol4", new java.sql.Date(1994-1900, 06-1, 21), 2013, 1, 324.8).execute();
	}

	/**
	 * Prints the users in the database in several different ways.
	 */
	@Test
	public void getUsers() {
		Result<Record> result = db.select().from(Users.USERS).fetch();
		System.out.println("\nLISTING ALL USERS VIA RESULT : \n" + result + "\n");

		int count = 0;
		for ( UsersRecord userRecord : db.selectFrom(Users.USERS).fetch() ) {
			count++;
			System.out.print("\nPRINTING USER " + count + " VIA USERRECORD :\n" + userRecord + "\n");
		}

		System.out.println ( "\nPRINTING SECOND USERS NICK USING RESULT.GETVALUE: " + result.formatCSV() + "\n");
	}

}
