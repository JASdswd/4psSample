package DAO;

import java.sql.SQLException;

public class UploadPhotoDAO extends BaseDAO  {

	public UploadPhotoDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	public void uploadBeneficiaryPhoto(String household_id, byte[] imageInByte,String date, String time, int server_id, int team_id, int user_id, int mun_id, boolean flag, int ctrl) throws SQLException{
		
		try {
			if(ctrl == 1){
				stmt = con.prepareStatement("insert into photo_tbl_temp2(household_id,photo_head,date_recorded,time_recorded,server_id,team_id,user_id,mun_id) values(?,?,?,?,?,?,?,?)");
				stmt.setString(1, household_id);
				stmt.setBytes(2, imageInByte);
				stmt.setString(3, date);
				stmt.setString(4, time);
				stmt.setInt(5, server_id);
				stmt.setInt(6, team_id);
				stmt.setInt(7, user_id);
				stmt.setInt(8, mun_id);
				System.out.println("add beneficiary photo yahoo.");
			}
			else{
				stmt = con.prepareStatement("update photo_tbl_temp2 set photo_head = ?,date_recorded = ?, time_recorded = ?, server_id = ?, team_id = ?, user_id = ?, mun_id = ? where household_id = ? ");
				stmt.setBytes(1, imageInByte);
				stmt.setString(2, date);
				stmt.setString(3, time);
				stmt.setInt(4, server_id);
				stmt.setInt(5, team_id);
				stmt.setInt(6, user_id);
				stmt.setInt(7, mun_id);
				stmt.setString(8, household_id);
			}
			
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
	
public boolean uploadMunPhoto(byte[] imageInByte, int mun, String fname, String lname, boolean flag) throws SQLException{
		
	boolean upload =  false;
		try {
			
			stmt = con.prepareStatement("update user_tbl set photo = ? where  user_id = 2 and mun_id="+mun+" and fname='"+fname+"' and lname='"+lname+"'");
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

public boolean uploadProvPhoto(byte[] imageInByte, boolean flag) throws SQLException{
	boolean upload = false;
	try {
		
		stmt = con.prepareStatement("update user_tbl set photo = ? where user_id = 1 ");
		stmt.setBytes(1, imageInByte);
		
		
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
	

}
