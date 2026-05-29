package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static String DriveClass ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url ="jdbc:sqlserver://DESKTOP-1AQF1D9:1433;databaseName=FoodDTB;encrypt=true;trustServerCertificate=true;";

	private static String username = "sa";

	private static String password ="123456" +
			"";
	public static Connection getConnect() throws SQLException {
		try {
			Class.forName(DriveClass);
			return DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}
}