package nz.ac.aut.ense701.gameModel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranking implements Serializable {
	Connection c = null;
	Statement stmt = null;
	
	public Ranking() {
		creatDatabase();
		

	}
	
	public void creatDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:user.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS USER " + "(userName CHAR(20) PRIMARY KEY NOT NULL,"
					+ " password CHAR(12) NOT NULL,"
					+ " score CHAR(10) NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(1);
			//e.printStackTrace();
		}
		System.out.println("Table created successfully");
	}
	

	public boolean addUser(User user) {
		String userName = "";
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:user.db");
			System.out.println("Opened database successfully");
			
			String sql = "SELECT userName FROM USER WHERE userName = '"+user.getUserName()+"'";
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				userName += rs.getString("userName");
			}
			if(userName.equals(user.getUserName())){
				rs.close();
				stmt.close();
				c.close();
				return false;
			}else{
				sql = "INSERT INTO USER (userName, password, score)"+
					"VALUES ('"+user.getUserName()+"', '"+user.getPassword()+"', '0')";
				stmt = c.createStatement();
				stmt.executeUpdate(sql);
				rs.close();
				stmt.close();
				c.close();
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
			//System.exit(1);
			
		}
	}
	

}
