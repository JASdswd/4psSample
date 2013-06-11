package myconnection;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

<<<<<<< HEAD
=======
import java.sql.Statement;

>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912

public class ConnectionDAO {
	
	protected Connection con = null;
	protected PreparedStatement stmt = null;
<<<<<<< HEAD
=======
	protected Statement stmtZ = null;
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
	protected ResultSet rs = null;
	protected ArrayList list = null;
	
	public ConnectionDAO() throws SQLException{
		this.con = getConnection();
		
	}

	public Connection getConnection() throws SQLException {
		
		try{
			String dbDriver = "com.mysql.jdbc.Driver";
			String dbServer = "localhost";
			String dbPort = "3306";
<<<<<<< HEAD
			String dbName = "4ps_maguindanao";
			String dbURL = "jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName ;
			String dbUser = "root";
			String dbPassword = "";
=======
			String dbName = "pantawid_db_sample";
			String dbURL = "jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName ;
			String dbUser = "root";
			String dbPassword = "pxzgjfvb";
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
			
			Class.forName(dbDriver);
	    	Connection conn =  DriverManager.getConnection(dbURL , dbUser, dbPassword);
			
		return conn;	
		}catch(ClassNotFoundException e){
			System.out.println("Class not found!");
			e.printStackTrace();
			return null;
		}catch(SQLException e){
			System.out.println("Communication link failure!");
			e.printStackTrace();
			return null;			
		}		
	}
	public void close() throws SQLException {
		if (this.con != null)
			con.close();
		if (this.stmt != null)
			stmt.close();
		if (this.rs != null)
			rs.close();
	}

	public void commit() throws SQLException {
		if (this.con != null)
			con.commit();
	}

	public void rollback() throws SQLException {
		if (this.con != null)
			con.rollback();
	}	
}
