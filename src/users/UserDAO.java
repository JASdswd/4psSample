package users;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.transactionBean;
import beans.Login_beans;

import myconnection.ConnectionDAO;

public class UserDAO extends ConnectionDAO {

	public UserDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArrayList<transactionBean> getmunicipal(boolean flag) //getting municipal names
		throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from municipal_tbl ORDER BY mun_name ASC;");
		
			while (rs.next()) {
		
				bean = new transactionBean(rs.getString("mun_name"),
						rs.getInt("mun_id"));
				list.add(bean);
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
		return list;
	}
	public ArrayList<transactionBean> getAllUserData(String user_id) {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select id, fname,lname,photo,fingerprint, m.mun_id, m.mun_name from user_tbl as u, municipal_tbl as m where user_id = "+user_id+" and m.mun_id = u.mun_id order by m.mun_id";
		System.out.println("sql getAlluserData:"+sql);
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				System.out.println("naay na retrieve");
				bean = new transactionBean(rs.getInt(1),
						rs.getString(2),rs.getString(3),rs.getBytes(4),rs.getBytes(5),rs.getInt(6), rs.getString(7));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public ArrayList<transactionBean> getUser_JSON(int mun_id, int user_role) {
		
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select id,fname,lname,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where u.mun_id = "+mun_id+" and m.mun_id = u.mun_id and user_id = "+user_role+" ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(user_role,rs.getInt(1),
						rs.getString(2),rs.getString(3),rs.getBytes(4),rs.getBytes(5),rs.getInt(6), rs.getString(7));
				list.add(bean);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public ArrayList<transactionBean> getUser_profile(int id,String mun,int user_role) throws SQLException {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql =  "select id,fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where user_id = "+user_role+" and u.id = "+id+"  and u.mun_id = "+mun+" and m.mun_id = u.mun_id ";
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getBytes(8),
						rs.getBytes(9),
						rs.getInt(10),
						rs.getString(11));
				list.add(bean);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return list;
	}
	public ArrayList<transactionBean> getUser_profile123(int id,String mun,int user_role) throws SQLException {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql =  "select id,fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where user_id = "+user_role+" and u.id = "+id+"  and u.mun_id = "+mun+" and m.mun_id = u.mun_id ";
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getBytes(8),
						rs.getBytes(9),
						rs.getInt(10),
						rs.getString(11));
				list.add(bean);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return list;
	}
	public transactionBean getUser_photo(boolean flag, String id, int user_role) throws SQLException {
		transactionBean image = null;
		System.out.println("daosW:"+id);
		try {
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = "+user_role+" and id='"+id+"' ");
			while (rs.next()) {
				image = new transactionBean(rs.getBytes("photo"));
		
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
		return image;
	}
	public void add_user_profile(int user_role,String fname,String lname,String username,String pass,String gender,String email,String contact,String mun,boolean flag) throws SQLException{
		try {
			stmt = con.prepareStatement("INSERT INTO user_tbl(user_id,fname,lname,u_name,p_word,user_gender,user_email,user_contact,photo,fingerprint,mun_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

			stmt.setInt(1, user_role);
			stmt.setString(2, fname);
			stmt.setString(3, lname);
			stmt.setString(4, username);
			stmt.setString(5, pass);
			stmt.setString(6, gender);
			stmt.setString(7, email);
			stmt.setString(8, contact);
			stmt.setBytes(9, null);
			stmt.setBytes(10, null);
			//stmt.setString(11, ID);
			stmt.setString(11, mun);

			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
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
	}
	public void update_user(String fname, String lname,String gender,String email,String contact,String id,String user_role,int omun_id, int nmun_id) throws SQLException  {
		
		String sql = "update user_tbl set fname = ?,lname = ?,user_gender=?,user_email=?,user_contact=?,mun_id = ? where user_id = "+user_role+" and id = '"+id+"' and mun_id= "+omun_id;
		
		try{
			stmt = con.prepareStatement(sql);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, gender);
			stmt.setString(4, email);
			stmt.setString(5, contact);
			stmt.setInt(6, nmun_id);
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to update.");
			System.out.println(sql);
		}
		catch(Exception ex){
			System.out.println(ex);
		}		
	}
	public String getMunValue(String mun_id) {
		// TODO Auto-generated method stub
		String munValue = "";
		String sql = "SELECT mun_name FROM `municipal_tbl` WHERE mun_id = '"+mun_id+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				munValue = rs.getString("mun_name");
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return munValue;
	}
	public void add_logs(boolean flag, String date, String time, String log_message)throws SQLException {
		
		try {
			stmt = con.prepareStatement("INSERT INTO logs_tbl(date,time,log_message) VALUES (?,?,?);");
		
			stmt.setString(1, date);
			stmt.setString(2, time);
			stmt.setString(3, log_message);
		
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add logs.");
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
		
	}
	public boolean upload_user_photo(byte[] imageInByte, int mun, String id, boolean flag,int user_id) throws SQLException{
		
		boolean upload =  false;
			try {
				
				stmt = con.prepareStatement("update user_tbl set photo = ? where  user_id = "+user_id+" and mun_id="+mun+" and id='"+id+"' ");
				stmt.setBytes(1, imageInByte);
				
				
				int ret = stmt.executeUpdate();
				if(ret>0){
					System.out.println("Updated image successfully !");
					upload = true;
				}
				else{
					System.out.println("Upload image Error!");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (flag) {
					close();
				}
			}
			return upload;
		}
	public boolean upload_capture_photo(byte[] imageInByte, boolean flag, int id, int user_role ) throws SQLException{
		boolean upload = false;
		try {
			stmt = con.prepareStatement("update user_tbl set photo = ? where id = '"+id+"' and user_id = "+user_role+"  ");
			stmt.setBytes(1, imageInByte);
			/*stmt.setString(2,id);*/
			/*stmt.setInt(3, user_id);*/
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if(ret>0){
				System.out.println("Uploaded successfully !");
				upload = true;
			}
			else{
				System.out.println("Upload image Error!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
		return upload;
	}	
	public ArrayList<Login_beans> getAccInfo(String id, int mun_id, int user_id) {
		ArrayList<Login_beans> list = new ArrayList<Login_beans>();
		String sql = "select u_name,p_word from user_tbl where user_id = "+user_id+" and id = '"+id+"' and mun_id = "+mun_id;
		System.out.println(sql);
		Login_beans bean = null;
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new Login_beans(rs.getString(1),rs.getString(2));
				list.add(bean);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public int Manage_Acc(boolean flag, String uname, String pword,String id, int user_id, int mun_id) throws SQLException {
		
		int upSuccess = 0;
		String sql = "update user_tbl set u_name=?,p_word = ? where id = '"+id+"' and user_id = "+user_id+" and mun_id = "+mun_id;
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
	public boolean check_password2(String username,String password,int user_id){
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
	public void update_user_fpt(boolean flag, byte[] fingerprint, String id, int mun, int user_id) throws SQLException {
		try {
			stmt = con.prepareStatement("update user_tbl set fingerprint = ? where user_id = "+user_id+" and id = '"+id+"' and mun_id = "+mun+"");
			stmt.setBytes(1, fingerprint);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to update.");
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
	}
	public String getmunName(int mun_id) {
		String munName = null;
		try{
			rs = con.createStatement().executeQuery("select mun_name from municipal_tbl where mun_id = "+mun_id+"");
			while(rs.next()){
				munName = rs.getString(1);
			}	
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return munName;		
	}
	public ArrayList<transactionBean> getUserdataUpdate(String id,String mun) throws SQLException {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		System.out.println("unique_id:"+id+":mun:"+mun);
		transactionBean bean = null;
		String sql =  "select fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where u.id = '"+id+"'  and u.mun_id = "+mun+" and m.mun_id = u.mun_id ";
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getBytes(7),
						rs.getBytes(8),
						rs.getInt(9),
						rs.getString(10));
				list.add(bean);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return list;
	}
	public int getUserId(String uname) {
		int id = 0;
		try{
			rs = con.createStatement().executeQuery("select id from user_tbl where u_name = '"+uname+"'");
			while(rs.next()){
				id = rs.getInt(1);
			}	
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return id;		
	}
	public String getFname(int user_role,int id) {
		String fname = null;
		try{
			rs = con.createStatement().executeQuery("select fname from user_tbl where user_id = "+user_role+" and id = "+id+"");
		while(rs.next()){
			fname = rs.getString(1);
		}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return fname;
	}
	public String getLname(int user_role,int id) {
		String lname = null;
		try{
			rs = con.createStatement().executeQuery("select lname from user_tbl where user_id = "+user_role+" and id = "+id+"");
		while(rs.next()){
			lname = rs.getString(1);
		}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return lname;
	}
//************************Functions for provincial link and financial analyst**************************************
	public ArrayList<transactionBean> View(String user_role){
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		System.out.println("view method UserDAO user_role:"+user_role);
		if(user_role.equals("3") || user_role.equals("1")){
		}
		else{
			user_role = "0";
		}
		String sql = "SELECT fname,lname,u_name,user_gender,user_contact,user_email,photo,fingerprint FROM user_tbl WHERE user_id = "+user_role+"";
		try {
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBytes(7),rs.getBytes(8));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void Update(String fname,String lname,String gender,String contact,String email,int user_role){
		
		String sql = "update user_tbl set fname = ?,lname = ?, user_gender=?, user_email=?, user_contact=? where user_id = "+user_role+"";
		
		try{
			stmt = con.prepareStatement(sql);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, gender);
			stmt.setString(4, email);
			stmt.setString(5, contact);
			int ret = stmt.executeUpdate();
		
			if (ret != 1)
				throw new SQLException("Failed to update.");
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	public String getUser2Fname(int user_role) {
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
	public String getUser2Lname(int user_role) {
		String lname = null;
		try{
			rs = con.createStatement().executeQuery("select lname from user_tbl where user_id = "+user_role+"");
		while(rs.next()){
			lname = rs.getString(1);
		}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return lname;
	}
	public void updateuser2_fpt(boolean flag, byte[] fingerprint,int user_role) throws SQLException {
		try {
			stmt = con
					.prepareStatement("update user_tbl set fingerprint = ? where user_id = "+user_role+"");
			stmt.setBytes(1, fingerprint);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to update.");
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
	}
	public ArrayList<Login_beans> getAccInfo2(int user_role) {
		ArrayList<Login_beans> list = new ArrayList<Login_beans>();
		String sql = "select u_name,p_word from user_tbl where user_id = "+user_role;
		System.out.println(sql);
		Login_beans bean = null;
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new Login_beans(rs.getString(1),rs.getString(2));
				list.add(bean);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public int Manage_Acc2(boolean flag, String uname, String pword, int user_id) throws SQLException {
		
		int upSuccess = 0;
		String sql = "update user_tbl set u_name=?,p_word = ? where user_id = "+user_id;
		
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
			if (flag) {	close();}
		}
		return upSuccess;
	}
	public int updateuserphoto2(byte[] imageInByte,int user_role) {
		int up = 0;
		
		try{
			stmt = con.prepareStatement("update user_tbl set photo = ? where user_id = "+user_role+"");		
			stmt.setBytes(1, imageInByte);
			
			int s = stmt.executeUpdate();
			if(s>0){
				up= 1;
				System.out.println("Uploaded successfully !");
			}
			else{
				up = 0;
				System.out.println("Upload image Error!");
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return up;
	}
	public boolean upload_capture_photo2(byte[] imageInByte, boolean flag, int user_role ) throws SQLException{
		boolean upload = false;
		try {
			stmt = con.prepareStatement("update user_tbl set photo = ? where user_id = "+user_role+"  ");
			stmt.setBytes(1, imageInByte);
			/*stmt.setString(2,id);*/
			/*stmt.setInt(3, user_id);*/
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if(ret>0){
				System.out.println("Uploaded successfully !");
				upload = true;
			}
			else{
				System.out.println("Upload image Error!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
		return upload;
	}
	public transactionBean getphoto_user2(boolean flag,int user_role)throws SQLException {

		transactionBean image = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = "+user_role+" ");
		
			while (rs.next()) {
				image = new transactionBean(rs.getBytes("photo"));
				System.out.println("image = "+image);
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
		return image;
	}
//************************End of Functions for provincial link and financial analyst*******************************
}// end of UserDAO
