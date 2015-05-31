package capstat.infrastructure.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class should not be called directly to connect to the database,
 * unless very specific queries need to be sent. If this class is used
 * directly, keep in mind that it might be deprecated at any moment without
 * notice.
 *
 * Instead use {@link DatabaseHelperFactory} to create
 * the kind of database helper you need. This method for talking to the
 * database is recommended way, and the only one guaranteed to work with
 * later versions of the program.
 * @Author Johan Andersson
 */
public class DatabaseConnection {

	private Connection conn;
	public DSLContext database;

	private String url;
	private String userName;
	private String password;

	/**
	 * Sets the connection up to both jdbc and jooq.
	 */
	public DatabaseConnection() {
		this.conn = null;
		this.url = "jdbc:mysql://localhost:3306/capstat";
		this.userName = "capstat_user"; // Your username goes here!
		this.password = "1234"; // Your password goes here!
		connect();
		database = DSL.using(conn, SQLDialect.MYSQL);
	}

	/**
	 * Opens a jdbc connection to database.
	 */
	public void connect() {
		try {
			this.conn = DriverManager.getConnection("" + url + "?user=" + userName + "&password=" + password);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	/**
	 * Closes the jdbc connection.
	 */
	public void disconnect() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
