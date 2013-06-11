package login_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Login_beans;
import beans.Loginbeans;

import myconnection.ConnectionDAO;

public class Login_DAO extends ConnectionDAO{

	public Login_DAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean check_password(String username,String password){
		boolean check=false;
		try{
			rs=con.createStatement().executeQuery("select * from user_tbl where user_id = 10 and p_word='"+password+"'");
			while(rs.next()){
				check=true;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}

	public int check_passwordLogin(String username,String password, String tbl){
		int check=0;
		try{
			rs=con.createStatement().executeQuery("select user_id from "+tbl+" where u_name='"+username+"' and p_word='"+password+"'");
			while(rs.next()){
				check=rs.getInt("user_id");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}
	public String check_passwordLogin(String username){
		String password=null;
		try{
			rs=con.createStatement().executeQuery("select p_word from user_tbl where u_name='"+username+"'");
			while(rs.next()){
				password=rs.getString("p_word");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return password;
	}
	public String getUserID(String username){
		String id=null;
		try{
			rs=con.createStatement().executeQuery("select id from user_tbl where u_name='"+username+"'");
			while(rs.next()){
				id=rs.getString("id");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}
	public String check_passwordLoginVer(String username){
		String password=null;
		try{
			rs=con.createStatement().executeQuery("select p_word from verifier_tbl where u_name='"+username+"'");
			while(rs.next()){
				password=rs.getString("p_word");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return password;
	}
	public ArrayList<Loginbeans>userlist(){
		ArrayList<Loginbeans>list=new ArrayList<Loginbeans>();
		Loginbeans beans=null;
		try{
			rs=con.createStatement().executeQuery("select * from role_tbl order by username");
			while(rs.next()){
				beans = new Loginbeans(rs.getString("username"), rs.getInt("user_id"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
public int updateAcc(boolean flag, String uname, String pword, int user_id) throws SQLException {
		
		int upSuccess = 0;

		try {
			stmt = con
					.prepareStatement("update user_tbl set u_name=?,p_word = ? where user_id = "+user_id);
			stmt.setString(1, uname);
			stmt.setString(2, pword);

			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1){
				upSuccess = 0;
				throw new SQLException("Failed to update.");
			}
			else{
				upSuccess = 1;
			}
			if (flag) {
				commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	
		return upSuccess;
	}
public boolean check_password2(String password,int user_id){
	boolean check=false;
	try{
		rs=con.createStatement().executeQuery("select * from user_tbl where user_id = "+user_id+" and p_word='"+password+"'");
		while(rs.next()){
			check=true;
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return check;
}
public boolean check_passwordbookKeeper(String username,String password,int user_id){
	boolean check=false;
	try{
		rs=con.createStatement().executeQuery("select * from user_tbl where user_id = "+user_id+" and u_name = '"+username+"' and p_word='"+password+"'");
		while(rs.next()){
			check=true;
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return check;
}
public ArrayList<Login_beans> getAccInfo(String pword,int user_id) {
	
	ArrayList<Login_beans> list = new ArrayList<Login_beans>();
	String sql = "select u_name,p_word from user_tbl where user_id = "+user_id+" and p_word = '"+pword+"'";
	Login_beans bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new Login_beans(rs.getString(1),rs.getString(2));
			System.out.println("u:"+rs.getString(1)+"p:"+rs.getString(2));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
	
}
public ArrayList<Login_beans> getAccInfo(int user_id) {
	
	ArrayList<Login_beans> list = new ArrayList<Login_beans>();
	String sql = "select u_name,p_word from user_tbl where user_id = "+user_id;
	Login_beans bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new Login_beans(rs.getString(1),rs.getString(2));
			System.out.println("u:"+rs.getString(1)+"p:"+rs.getString(2));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
	
}
public ArrayList<Login_beans> getAccInfo(String fname, String lname, int mun_id, int user_id) {
	
	ArrayList<Login_beans> list = new ArrayList<Login_beans>();
	String sql = "select u_name,p_word from user_tbl where user_id = "+user_id+" and fname = '"+fname+"' and lname = '"+lname+"' and mun_id = "+mun_id;
	System.out.println(sql);
	Login_beans bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new Login_beans(rs.getString(1),rs.getString(2));
			System.out.println("u:"+rs.getString(1)+"p:"+rs.getString(2));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
	
}


public int updateAcc(boolean flag, String uname, String pword, int user_id, String fname, String lname, int mun_id, String old_uname, String old_pword) throws SQLException {
	
	int upSuccess = 0;
	String sql = "update user_tbl set u_name=?,p_word = ? where user_id = "+user_id+" and u_name = '"+old_uname+"' and p_word = '"+old_pword+"' and fname = '"+fname+"' and lname = '"+lname+"' and mun_id = "+mun_id;
	System.out.println(sql);
	try {
		stmt = con.prepareStatement(sql);
		stmt.setString(1, uname);
		stmt.setString(2, pword);

		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1){
			upSuccess = 0;
			throw new SQLException("Failed to update.");
		}
		else{
			upSuccess = 1;
		}
		if (flag) {
			commit();
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}

	return upSuccess;
}
 

//---------- Verifier Account ---------------------------------//

public ArrayList<Login_beans> getVerAccInfo(String fname, String lname, int mun_id, int user_id) {
	
	ArrayList<Login_beans> list = new ArrayList<Login_beans>();
	String sql = "select u_name,p_word from verifier_tbl where user_id = "+user_id+" and fname = '"+fname+"' and lname = '"+lname+"' and mun_id = "+mun_id;
	System.out.println(sql);
	Login_beans bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new Login_beans(rs.getString(1),rs.getString(2));
			System.out.println("u:"+rs.getString(1)+"p:"+rs.getString(2));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
	
}

public int updateVerAcc(boolean flag, String uname, String pword, int user_id, String fname, String lname, int mun_id, String old_uname, String old_pword) throws SQLException {
	
	int upSuccess = 0;
	String sql = "update verifier_tbl set u_name=?,p_word = ? where user_id = "+user_id+" and u_name = '"+old_uname+"' and p_word = '"+old_pword+"' and fname = '"+fname+"' and lname = '"+lname+"' and mun_id = "+mun_id;
	System.out.println(sql);
	try {
		stmt = con.prepareStatement(sql);
		stmt.setString(1, uname);
		stmt.setString(2, pword);

		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1){
			upSuccess = 0;
			throw new SQLException("Failed to update.");
		}
		else{
			upSuccess = 1;
		}
		if (flag) {
			commit();
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}

	return upSuccess;
}
public int getUser_role(String username,String pass){
	int user_role = 0;
	try {
		rs = con.createStatement().executeQuery("select user_id from user_tbl where u_name = '"+username+"' and p_word = '"+pass+"'");
		while(rs.next()){
			user_role = rs.getInt(1);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return user_role;
}
public String getFname(int user_role,String id) {
	String fname = null;
	try{
		rs = con.createStatement().executeQuery("select fname from user_tbl where user_id = "+user_role+" and id='"+id+"'");
	while(rs.next()){
		fname = rs.getString(1);
	}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	return fname;
}
public String getFname2(int user_role) {
	String fname = null;
	try{
		rs = con.createStatement().executeQuery("select fname from user_tbl where user_id = "+user_role+"");
	while(rs.next()){
		fname = rs.getString(1);
	}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	return fname;
}
public String getUser_rolename(int user_role,String id) {
	String fname = null;
	try{
		rs = con.createStatement().executeQuery("select l.username from user_tbl as u,login_tbl as l where u.user_id = l.user_id and u.user_id = "+user_role+" and u.id='"+id+"'");
	while(rs.next()){
		fname = rs.getString(1);
	}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	return fname;
}
public byte[] testIfExist(boolean flag, String sql) throws SQLException {
	byte[] count = null;
	try {
		//System.out.println("sql:"+sql);
		rs = con.createStatement().executeQuery(sql);
		
		while (rs.next()) {
			count  = rs.getBytes("photo");
		}

	} catch (SQLException ex) {
		if (flag)
			rollback();
		throw new SQLException(ex.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag)
			close();
	}
	return count;
}
/*         hashing code algorithm             */
public byte[] computeHash(String x)   
throws Exception  
{
   java.security.MessageDigest d =null;
   d = java.security.MessageDigest.getInstance("SHA-256");
   d.reset();
   d.update(x.getBytes());
   return  d.digest();
}

public String byteArrayToHexString(byte[] b){
   StringBuffer sb = new StringBuffer(b.length * 2);
   for (int i = 0; i < b.length; i++){
     int v = b[i] & 0xff;
     if (v < 16) {
       sb.append('0');
     }
     sb.append(Integer.toHexString(v));
   }
   return sb.toString().toUpperCase();
}

}
