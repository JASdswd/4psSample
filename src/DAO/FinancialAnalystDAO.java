package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import myconnection.ConnectionDAO;
import bean.transactionBean;

public class FinancialAnalystDAO extends ConnectionDAO{

	public FinancialAnalystDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<transactionBean> View(){
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "SELECT fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint FROM user_tbl WHERE user_id = 3";
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
	
	public void Update(String fname,String lname,String gender,String email,String contact){
		
		String sql = "update user_tbl set fname = ?,lname = ?, user_gender=?, user_email=?, user_contact=? where user_id = 3";
		
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
	
	public transactionBean gethead_photo(boolean flag)throws SQLException {

		transactionBean image = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = 3 ");
		
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

	public String getFinancialAFname() {
		String fname = null;
		
		try{
			
			rs = con.createStatement().executeQuery("select fname from user_tbl where user_id = 3");
		while(rs.next()){
			fname = rs.getString(1);
		}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return fname;
	
	}
	public String getFinancialALname() {
		String lname = null;
		
		try{
			
			rs = con.createStatement().executeQuery("select lname from user_tbl where user_id = 3");
		while(rs.next()){
			lname = rs.getString(1);
		}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return lname;
	
	}
	
}