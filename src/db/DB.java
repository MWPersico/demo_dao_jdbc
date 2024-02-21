package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Connection conn = null;
	
	public static Connection getConnection(){
		if(conn == null) {
			Properties props = loadProperties();
			String connectionURL = props.getProperty("dburl");
			try {
				conn = DriverManager.getConnection(connectionURL, props);				
			}catch(SQLException ex) {
				throw new DBException(ex.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException ex) {
				throw new DBException(ex.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement statement) {
		if(statement == null)return;
		
		try {
			statement.close();
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet result) {
		if(result == null)return;
		
		try {
			result.close();
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}
	}
	
	private static Properties loadProperties() {
		Properties props = new Properties();
		try(FileInputStream fs = new FileInputStream("./db.properties")){
			props.load(fs);
		}catch(FileNotFoundException ex) {
			System.out.println("Properties file not found!");
		}catch(IOException ex) {
			System.out.println("Error reading file!");
		}
		return props;
	}
}
