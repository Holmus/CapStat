package capstat.utils;

import java.sql.*;

/**
 * Created by jibbs on 07/05/15.
 */
public class DatabaseConnection {
	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://localhost:3306/capstat";
			String userName = "capstat_user"; // Your username goes here!
			String password = "1234"; // Your password goes here!
			Connection conn = DriverManager.getConnection("" + url + "?user=" + userName + "&password=" + password);
			readUsers(conn);
		} catch (SQLException e) {
			System.err.println(e);
			System.exit(2);
		}
	}


	private static void readUsers(Connection conn) {

		Statement myStmt;
		try {
			myStmt = conn.createStatement();
		    /* Checks and tells if the student got unregistered from a course or was already not registered */

			//myStmt.executeUpdate("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('lol2kpe', 'Johan Andersson', 'lol', 912, 'Master')");
			myStmt.executeUpdate("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('user1', 'Arne Ranta', 'lol2', 123, 'n00b')");
			myStmt.executeUpdate("INSERT INTO Users (nick, name, pass, chalmersAge, iloRank) VALUES ('user2', 'Ben Dover', 'lol4', 1212, 'n00b')");
			ResultSet rs = myStmt.executeQuery("SELECT * FROM Users");
			while ( rs.next() ) {
				String nick = rs.getString(1);
				String name = rs.getString(2);
				String pass = rs.getString(3);
				String chalmersAge = rs.getString(4);
				String iloRank = rs.getString(5);
				System.out.println("" + nick + "  " + name + "  " + pass + "  " + chalmersAge + "  " + iloRank);
			}

			myStmt.close(); /* Closes the statement when everything is done */
		} catch (SQLException se) {
			System.out.println("" + se.getMessage());
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}

	}


}
