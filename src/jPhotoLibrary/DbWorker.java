package jPhotoLibrary;

import java.sql.SQLException;

final class DbWorker {
	
	static java.sql.Connection dbConnection;
	
	static void dbConnect() {
		try {
			dbConnection = java.sql.DriverManager.getConnection("jdbc:h2:~/" + DB_Constants.DB_PATH.toString()
			, DB_Constants.DB_USER.toString(), DB_Constants.DB_PASS.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
