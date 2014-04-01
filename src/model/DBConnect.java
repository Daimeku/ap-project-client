/**
 * PR:
 * DBConnect class...
 * Holds constant members related to database connection
 */

package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import com.mysql.jdbc.PreparedStatement;

public final class DBConnect {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB = "jdbc:mysql://localhost/ap-project";
	public static final String USER = "root";
	public static final String PASS = "";
	public Connection conn;
	public ResultSet result;
	//public PreparedStatement state = conn.prepareStatement();
	
	public boolean addDrink(Drink drink){
		boolean conf = false;
		try{
			PreparedStatement prep = conn.prepareStatement("INSERT INTO drinks(name, price, type) VALUES ('" + drink.getName() + "', '" + drink.getPrice() + "', '" + drink.getType() + "')"); 
			int numChanged = prep.executeUpdate();
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			
		}
		catch(Exception ex){
			
		}
		return conf;
	}
	
	public boolean staffLogin(String user, String pass){
		boolean conf = false;
		try{
			java.sql.PreparedStatement prep = conn.prepareStatement("SELECT * FROM staff WHERE name = '"+user+"' AND password = '" + pass + "'");
			result = prep.executeQuery();
			
			int count = 0;
			while(result.next())
				++count;
			if(count == 1){
				
				conf = true;
			}
		}
		
		catch(Exception ex){
			
		}
		return conf;
	}
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName(DBConnect.JDBC_DRIVER); // Access JDBC driver from JAR 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DBConnect.DB, DBConnect.USER, DBConnect.PASS);
	}
	
}
