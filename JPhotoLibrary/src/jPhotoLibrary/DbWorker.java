package jPhotoLibrary;

import java.sql.SQLException;

final class DbWorker {
	
	static java.sql.Connection dbConnection;
	
	static void dbConnect() {
		try {
			dbConnection = java.sql.DriverManager.getConnection("jdbc:h2:~/" + Constants.dbPath, Constants.dbUser, Constants.dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
