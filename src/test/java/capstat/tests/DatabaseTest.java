package capstat.tests;

import capstat.utils.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jibbs on 09/05/15.
 */
public class DatabaseTest {

	private DatabaseConnection db;

	@Before
	public void initiateConnection() {
		db = new DatabaseConnection();
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


}
