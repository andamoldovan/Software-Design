package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOConnectionFactory {
	String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/nationaltheatre";
	String dbUser = "root";
	String dbPwd = "";
	
	private static DBOConnectionFactory connectionFactory = null;
	
	private DBOConnectionFactory() {
		try {
			Class.forName(driverClassName);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		return conn;
	}
	
	public static DBOConnectionFactory getInstance() {
		if(connectionFactory == null) {
			connectionFactory = new DBOConnectionFactory();
		}
		return connectionFactory;
	}
	
	public int executeQuery(String query) throws ClassNotFoundException, SQLException{
		return getConnection().createStatement().executeUpdate(query);
	}
}
