package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.transactionBean;

import myconnection.ConnectionDAO;
public class Fingerprint_conDAO  extends ConnectionDAO {

	public Fingerprint_conDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	public transactionBean getuser_fingerprint(boolean flag)throws SQLException {
		
		transactionBean fingerprint = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select fingerprint from user_tbl where user_id = '1'");
		
			while (rs.next()) {
		
				fingerprint = new transactionBean(rs.getBytes("fingerprint"),
						"none");
		
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
		return fingerprint;
	}
	public int get_municipality_id(boolean flag, String household_id) throws SQLException{
		int municipality = 0;
		try {
			rs = con.createStatement().executeQuery(
					"select municipality from household_tbl where household_id = '"+household_id+"' ");
		
			while (rs.next()) {
		
				municipality = rs.getInt("municipality");
		
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
		return municipality;
	}
	public transactionBean getFinancialAnalyst_fingerprint(boolean flag, int user_id)throws SQLException {
		
		transactionBean fingerprint = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select fingerprint from user_tbl where user_id = '"+user_id+"' ");
		
			while (rs.next()) {
		
				fingerprint = new transactionBean(rs.getBytes("fingerprint"),
						"none");
		
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
		return fingerprint;
	}
	
	public ArrayList<transactionBean> getmunuser_fingerprint(boolean flag, int mun_id)throws SQLException {
		
		transactionBean fingerprint = null;
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		
		try {
			rs = con.createStatement().executeQuery(
					"select fingerprint,id from user_tbl where user_id = '2' and mun_id = '"+mun_id+"' and fingerprint is not null");
		
			while (rs.next()) {
		
				fingerprint = new transactionBean(rs.getBytes("fingerprint"),
						rs.getInt("id"));
				list.add(fingerprint);
		
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
	public void updatesub(String household_id,int munUserId, String month){
		//con.createStatement().execute("update received_tbl set sub = '"+munUserId+"' where household_id='"+household_id+"' and month_and_year="+month+" ");
		String sql = "update received_tbl set sub = ? where household_id = ? and month_and_year = ?";
		
		try{
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, munUserId);
			stmt.setString(2, household_id);
			stmt.setString(3, month);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to update.");
			System.out.println(sql);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
	}
}
