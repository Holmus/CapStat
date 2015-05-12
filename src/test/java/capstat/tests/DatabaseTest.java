package capstat.tests;

import static org.junit.Assert.*;
import capstat.utils.DatabaseConnection;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import org.jooq.*;

/**
 * Created by jibbs on 09/05/15.
 */
public class DatabaseTest {

	private DatabaseConnection db;

	@Before
	public void initiateConnection() {
		db = new DatabaseConnection();
		db.connect();
	}

	@Test
	public void useJooq() {
//		DSLContext create = DSL.using(mySql);
	}


	@Test
	public void deleteAllUsers() {
		db.alter("DELETE FROM Users");
	}

	@Test
	public void insertUsers () {
		db.insert("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('lol2kpe', 'Johan Andersson', 'lol', 913, 'Master')");
		db.insert("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('user1', 'Arne Ranta', 'lol2', 123, 'n00b')");
		db.insert("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('user2', 'Ben Dover', 'lol4', 1212, 'n00b')");
	}


	@Test
	public void getUsers() {
		Iterator<String> resultIterator;
		resultIterator = db.read("SELECT * FROM Users").iterator();
		while ( resultIterator.hasNext() ) {
			System.out.println("" + resultIterator.next());
		}
	}


	@Test
	public void insertMatches() {

	}


	@Test
	public void getMatches() {

	}


	@Test
	public void insertThrowSequences() {

	}


	@Test
	public void getAll() {

	}

	@After
	public void closeConn() {
		db.disconnect();
	}

}
