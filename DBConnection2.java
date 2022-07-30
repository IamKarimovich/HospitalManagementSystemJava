package Help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection2 {
Connection conn = null; 
	
	public void DbConnection2(){}
	
	public Connection connDB()
	{
		try {
			this.conn = DriverManager.getConnection(
				    "Any Db Connection is here!");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
