package DAO;

import java.sql.SQLException;


public class GrsCasesDAO extends BaseDAO  {

	public GrsCasesDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	public void addGrsCases(boolean flag,String date_recorded,int server_id, String household_id, int user_id, String grscase, String idoc, String remarks) throws SQLException{
		
		try {
				stmt = con.prepareStatement("insert into grscases_tbl(date_recorded,server_id,household_id,user_id,grscasetype_id,grscase,idoc,remarks) values(?,?,?,?,?,?,?,?)");
				stmt.setString(1, date_recorded);
				stmt.setInt(2,server_id );
				stmt.setString(3, household_id);
				stmt.setInt(4, user_id);
				stmt.setInt(5, 1);
				stmt.setString(6, grscase);
				stmt.setString(7, idoc);
				stmt.setString(8, remarks);
				System.out.println("add grs Cases yahoo.");
			
			
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
public void updateGrsCases(boolean flag,String date_recorded,int server_id, String household_id, int user_id, String grscase, String idoc, String remarks) throws SQLException{
		
		try {
				stmt = con.prepareStatement("update grscases_tbl set date_recorded = ?, server_id = ?, household_id = ? , user_id = ?, grscase = ?, idoc = ?, remarks = ? where household_id = ? ");
				stmt.setString(1, date_recorded);
				stmt.setInt(2,server_id );
				stmt.setString(3, household_id);
				stmt.setInt(4, user_id);
				stmt.setString(5, grscase);
				stmt.setString(6, idoc);
				stmt.setString(7, remarks);
				stmt.setString(8, household_id);
			
			
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
	
/*public Beanstransaction_record getgrsCases(boolean flag, String hh_id)
		throws SQLException {
		
		Beanstransaction_record grs = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select * from grscases_tbl where household_id = '"
							+ hh_id + "'");
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = '1'");
			
			while (rs.next()) {
			
				grs = new Beanstransaction_record(rs.getBytes("photo_head"));
			
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
		return grs;
		}*/
}
