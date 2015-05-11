package capstat.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jibbs on 07/05/15.
 */
public class DatabaseConnection {

	public Connection conn;

	public DatabaseConnection() {
		this.conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/capstat";
			String userName = "capstat_user"; // Your username goes here!
			String password = "1234"; // Your password goes here!
			this.conn = DriverManager.getConnection("" + url + "?user=" + userName + "&password=" + password);
		} catch (SQLException e) {
			System.err.println(e);
			System.exit(2);
		}
	}

	public void insert(String statement){
		Statement myStmt;
		try {
			myStmt = conn.createStatement();
			myStmt.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alter(String statement) {

		//TODO

		Statement myStmt;
		try {
			myStmt = conn.createStatement();
			myStmt.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> read(String statement) {

		Statement myStmt;
		try {
			myStmt = conn.createStatement();
			ResultSet rs = myStmt.executeQuery(statement);
			List<String> resultList = new ArrayList<>();
			while ( rs.next() ) {
				String nick = rs.getString(1);
				String name = rs.getString(2);
				String pass = rs.getString(3);
				String chalmersAge = rs.getString(4);
				String iloRank = rs.getString(5);
				resultList.add("" + nick + "  " + name + "  " + pass + "  " + chalmersAge + "  " + iloRank);
			}

			myStmt.close(); /* Closes the statement when everything is done */

			return resultList;

		} catch (SQLException se) {
			System.out.println("" + se.getMessage());
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return null;
	}

}
