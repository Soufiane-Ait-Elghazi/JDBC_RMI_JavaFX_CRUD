package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
	private static Connection connection;
	static {
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_etudiants","root","");
		} catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

	public static Connection getConnection() {
		return connection;
	}

}
