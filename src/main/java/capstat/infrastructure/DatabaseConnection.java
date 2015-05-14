package capstat.infrastructure;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jibbs on 07/05/15.
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
