package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import test.fingerprintBean;


import bean.LogsBean;
import bean.reportBean;
import bean.reportBean2;
import bean.transactionBean;

import myconnection.ConnectionDAO;

public class BaseDAO extends ConnectionDAO {

	public BaseDAO() throws SQLException {
		super();

	}

	public void add_fingerprint(boolean flag, String hh_id, byte[] fingerprint, String date, String time,int server_id,int team_id,String user_id,int mun_id)
			throws SQLException {
		
		try {
			stmt = con
					.prepareStatement("insert into fingerprint_tbl_temp(household_id,fingerprint,date_recorded,time_recorded,server_id,team_id,user_id,mun_id) values(?,?,?,?,?,?,?,?)");
			
			stmt.setString(1, hh_id);
			stmt.setBytes(2, fingerprint);
			stmt.setString(3, date);
			stmt.setString(4, time);
			stmt.setInt(5, server_id);
			stmt.setInt(6, team_id);
			stmt.setString(7, user_id);
			stmt.setInt(8, mun_id);
			
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
	public void add_registration(boolean flag, String hh_id, String date, String time, int user_id)throws SQLException {
		
		try {
			stmt = con
					.prepareStatement("insert into registrations_tbl(date_recorded,time_recorded,household_id,user_id) values(?,?,?,?)");
			
			stmt.setString(1, hh_id);
			stmt.setString(2, date);
			stmt.setString(3, time);
			stmt.setInt(4, user_id);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add registration table.");
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

	public void add_fingerprint2(boolean flag, String hh_id, byte[] fingerprint, String date, String time) throws SQLException {

			try {
				stmt = con.prepareStatement("insert into fingerprint_duplicate_tbl(household_id,fingerprint,date_recorded,time_recorded) values(?,?,?,?)");
				
				stmt.setString(1, hh_id);
				stmt.setBytes(2, fingerprint);
				stmt.setString(3, date);
				stmt.setString(4, time);
				
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
	
	public void update_fingerprint(boolean flag, String hh_id,
			byte[] fingerprint, String date, String time,int server_id,int team_id,String user_id,int mun_id) throws SQLException {
		try {
			stmt = con
					.prepareStatement("update fingerprint_tbl_temp set fingerprint = ?, date_recorded = ?, time_recorded = ?,server_id = ?," +
							" team_id = ?, user_id = ?, mun_id = ?  where household_id = '"
							+ hh_id + "'");
			stmt.setBytes(1, fingerprint);
			stmt.setString(2, date);
			stmt.setString(3, time);
			stmt.setInt(4, server_id);
			stmt.setInt(5, team_id);
			stmt.setString(6, user_id);
			stmt.setInt(7,mun_id);
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

	@SuppressWarnings("unchecked")
	public ArrayList<transactionBean> gethousehold_info(boolean flag,
			String hh_id) throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select household_id, philhealth_id, household_member_id, head_name," +
					" age, birthday, gender, pregnant, attending_school, street, purok, brgy_name, mun_name,status from household_tbl as h ,municipal_tbl as m, brgy_tbl as b" +
					" where h.barangay = b.brgy_id and m.mun_id = h.municipality and household_id = '"
							+ hh_id + "'");

			while (rs.next()) {
				System.out.println("status:"+rs.getInt("status"));
				
				bean = new transactionBean(rs.getString("household_id"),
						rs.getString("philhealth_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"), rs.getInt("age"),
						rs.getString("birthday"),rs.getString("gender"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"),
						rs.getString("street"), rs.getString("purok"),
						rs.getString("brgy_name"), rs.getString("mun_name"),rs.getInt("status"));
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
	
	public ArrayList<LogsBean> getLogs_list(boolean flag) throws SQLException {
		LogsBean bean = null;
		list = new ArrayList<LogsBean>();
		try {
			rs = con.createStatement().executeQuery("select * from logs_tbl");

			while (rs.next()) {

				bean = new LogsBean(rs.getString("date"), rs.getString("time"), rs.getString("log_message"));
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
	public ArrayList<transactionBean> gethhid(boolean flag) throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<LogsBean>();
		try {
			rs = con.createStatement().executeQuery("select household_id from copyphoto_tbl");

			while (rs.next()) {

				bean = new transactionBean(rs.getString("household_id"));
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
	public ArrayList<LogsBean> getLogs_list(boolean flag, String search) throws SQLException {
		LogsBean bean = null;
		list = new ArrayList<LogsBean>();
		try {
			rs = con.createStatement().executeQuery("select * from logs_tbl where log_message like '%"+search+"%'");

			while (rs.next()) {

				bean = new LogsBean(rs.getString("date"), rs.getString("time"), rs.getString("log_message"));
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

	public ArrayList<transactionBean> getmunicipal(boolean flag)
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

	public ArrayList<transactionBean> getbrgy_name(boolean flag, int mun_id)
			throws SQLException {
		transactionBean brgy_name = null;
		try {
			rs = con.createStatement()
					.executeQuery(
							"SELECT * FROM brgy_tbl as b, municipal_tbl as m WHERE m.mun_id = b.mun_id AND m.mun_id = '"
									+ mun_id + "'");

			list = new ArrayList<transactionBean>();
			while (rs.next()) {
				brgy_name = new transactionBean(rs.getString("brgy_name"));

				list.add(brgy_name);
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

	@SuppressWarnings("unchecked")
	public ArrayList<transactionBean> getwife_info(boolean flag, String hh_id)
			throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from wife_tbl where household_id = '" + hh_id
							+ "'");

			while (rs.next()) {

				bean = new transactionBean(rs.getString("household_id"),
						rs.getString("spouse_name"),
						rs.getInt("household_member_id"), rs.getInt("age"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"),rs.getString("f_position"),rs.getInt("status"),rs.getString("gender"));
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

	@SuppressWarnings("unchecked")
	public ArrayList<transactionBean> getchildren_info(boolean flag,
			String hh_id) throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from children_tbl where household_id = '" + hh_id
							+ "'");

			while (rs.next()) {

				bean = new transactionBean(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("child_name"), rs.getInt("age"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"),rs.getString("f_position"),rs.getInt("status"),rs.getString("gender"));
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

	@SuppressWarnings("unchecked")
	public ArrayList<transactionBean> getchildren(boolean flag, int hh_id)
			throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from children_tbl where household_member_id = '"
							+ hh_id + "'");

			while (rs.next()) {

				bean = new transactionBean(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("child_name"), rs.getInt("age"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"));
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

	public ArrayList<transactionBean> getgrandchild(boolean flag, int hh_id)
			throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from grandchild_tbl where household_member_id = '"
							+ hh_id + "'");

			while (rs.next()) {

				bean = new transactionBean(rs.getString("household_id"),
						rs.getInt("household_member_id"), rs.getInt("age"),
						rs.getString("grandchild_name"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"));
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

	@SuppressWarnings("unchecked")
	public ArrayList<transactionBean> getgrandchild_info(boolean flag,
			String hh_id) throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from grandchild_tbl where household_id = '"
							+ hh_id + "'");

			while (rs.next()) {
				bean = new transactionBean(rs.getString("household_id"),
						rs.getInt("household_member_id"), rs.getInt("age"),
						rs.getString("grandchild_name"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"),rs.getInt("status"),rs.getString("gender"));
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

	public int testIfPhotoExist(boolean flag, String hh_id) throws SQLException {

		int count = 0;
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM photo_tbl_temp2 WHERE household_id = '" +hh_id+"'");

			while (rs.next()) {
				count++;
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

	public int testIffingerprintExist(boolean flag, String hh_id)
			throws SQLException {

		int count = 0;
		try {
			rs = con.createStatement().executeQuery(
					"SELECT * FROM fingerprint_tbl_temp WHERE household_id = '"
							+ hh_id + "'");

			while (rs.next()) {
				count++;
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

	public int testIfExist(boolean flag, String sql) throws SQLException {
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(sql);
			
			while (rs.next()) {
				count++;
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

	public ArrayList<transactionBean> testIfsdExist(boolean flag, String sql)
			throws SQLException {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		try {
			rs = con.createStatement().executeQuery(sql);

			while (rs.next()) {
				bean = new transactionBean(rs.getInt("household_member_id"));
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
	public int get(boolean flag, String sql) throws SQLException {
		int entry_id = 0;
		try {

			rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				entry_id = rs.getInt(1);
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
		return entry_id;
	}

	public transactionBean gethead_photo(boolean flag, String hh_id)
			throws SQLException {

		transactionBean image = null;

		try {
			rs = con.createStatement().executeQuery(
					"select photo_head from photo_tbl_temp2 where household_id = '"
							+ hh_id + "'");
			/*rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = '1'");*/

			while (rs.next()) {

				image = new transactionBean(rs.getBytes("photo_head"));

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

	public transactionBean getfingerprint(boolean flag, String hh_id)
			throws SQLException {

		transactionBean fingerprint = null;

		try {
			rs = con.createStatement().executeQuery(
					hh_id);

			while (rs.next()) {
				fingerprint = new transactionBean(rs.getBytes(1),
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
	
	public int getCountAllFPT(boolean flag)
		throws SQLException {
	
	int fingerprint = 0;
	
	try {
		rs = con.createStatement().executeQuery("select count(*) from fingerprint_tbl_temp");
	
		while (rs.next()) {
			fingerprint = rs.getInt(1);
	
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

public ArrayList<transactionBean> getallfingerprint(boolean flag, String municipal, String brgy_id) throws SQLException {
		
		transactionBean fingerprint = null;
		ArrayList<transactionBean> allFingerPrint = new ArrayList<transactionBean>();
		String sql = "";
		try {
			/*	sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h, received_tbl as r" +
				"  where h.household_id = f.household_id and r.household_id = f.household_id and receive = 0 and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
				*/sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h" +
				"  where h.household_id = f.household_id and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
				//sql = "select household_id, fingerprint from fingerprint_tbl_temp ";
			System.out.println(sql);
			rs=con.createStatement().executeQuery(sql);
			int count = 1;
			while (rs.next()) {
				
				fingerprint = new transactionBean(rs.getBytes("fingerprint"), rs.getString("household_id"));
				allFingerPrint.add(fingerprint);
				System.out.println("count:"+count++);
			}

		} catch (SQLException ex) {
			if(flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(flag)
			close();
		}
		return allFingerPrint;
	}
public ArrayList<transactionBean> getallfingerprintByBatch(boolean flag, int limit) throws SQLException {
	
	transactionBean fingerprint = null;
	ArrayList<transactionBean> allFingerPrint = new ArrayList<transactionBean>();
	String sql = "";
	try {
		/*	sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h, received_tbl as r" +
			"  where h.household_id = f.household_id and r.household_id = f.household_id and receive = 0 and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
			sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h" +
			"  where h.household_id = f.household_id and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
		*/
		int range = limit - 10000;
		sql = "select household_id, fingerprint from fingerprint_tbl_temp limit "+range+","+10000;
		System.out.println(sql);
		/*stmtZ = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		//rs.setFetchSize(1);
		rs = stmtZ.executeQuery(sql);*/
		rs = con.createStatement().executeQuery(sql);
		int count = 1;
		while (rs.next()) {
			fingerprint = new transactionBean(rs.getBytes("fingerprint"), rs.getString("household_id"));
			allFingerPrint.add(fingerprint);
		}

	} catch (SQLException ex) {
		if(flag)
			rollback();
		throw new SQLException(ex.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(flag)
		close();
	}
	return allFingerPrint;
}
	
public ArrayList<fingerprintBean> getallfingerprint(boolean flag, int mun) throws SQLException {
		
		String household_id = "";
		fingerprintBean bean = null;	
		ArrayList<fingerprintBean> allFingerPrint = new ArrayList<fingerprintBean>();
		
		try {
			
			rs=con.createStatement().executeQuery("SELECT * FROM `isolated_fingerprint_db` ");
			
			while (rs.next()) {
				
				bean = new fingerprintBean(rs.getString("household_id"),rs.getBytes("fingerprint"),rs.getString("date_recorded"),rs.getString("time_recorded"));
				allFingerPrint.add(bean);
				
			}

		} catch (SQLException ex) {
			if(flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(flag)
			close();
		}
		return allFingerPrint;
	}

	public void parsePDf(boolean flag, String household_id,
			String month_and_year, int amount, int received,int parsing_id)
			throws SQLException {

		try {
			stmt = con
					.prepareStatement("INSERT INTO received_tbl(household_id,month_and_year,date_receive,time,amount,receive,comment,sub, parsing_id) VALUES (?,?,?,?,?,?,?,?,?)");

			stmt.setString(1, household_id);
			stmt.setString(2, month_and_year);
			stmt.setString(3, "");
			stmt.setString(4, "");
			stmt.setInt(5, amount);
			stmt.setInt(6, received);
			stmt.setString(7, "");
			stmt.setInt(8, 0);
			stmt.setInt(9, parsing_id);

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
	public int getLastParsing_id() {
			
			int am = 0;
			
			try{
				
				/*rs = con.createStatement().executeQuery("select `amount` from received_tbl where parsing_id = (select max(parsing_id) from received_tbl where household_id = '"+id+"') and household_id = '"+id+"'");*/
				rs = con.createStatement().executeQuery("select `parsing_id` from received_tbl order by parsing_id desc limit 1");
				while(rs.next()){
					am = rs.getInt("parsing_id");
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
			
			return am;
			
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

	public void addHousehold(boolean flag, reportBean bean) throws SQLException {
		
		try{
			
			stmt = con.prepareStatement("INSERT INTO household_tbl(household_id,philhealth_id,household_member_id,head_name,age, birthday,gender,pregnant,attending_school,street,purok,barangay,municipality,f_position) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("Bean:"+Integer.parseInt(bean.getHmember_id()));
			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, bean.getHousehold_id());
			stmt.setString(2, bean.getPhilhealth_id());
			stmt.setInt(3, Integer.parseInt(bean.getHmember_id()));
			stmt.setString(4, bean.getName());
			stmt.setInt(5, bean.getAge());
			stmt.setString(6, bean.getBday());
			stmt.setString(7, bean.getGender());
			stmt.setInt(8, bean.getPregnant());
			stmt.setInt(9, bean.getAttending_school());
			stmt.setString(10, bean.getStreet());
			stmt.setString(11, bean.getPurok());
			stmt.setInt(12, bean.getBrgy_id());
			stmt.setInt(13, bean.getMun_id());
			stmt.setString(14, bean.getF_position());
			
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

	
	public boolean searchHousehold(boolean flag, String household_id) throws SQLException{
		
		boolean found = false;
		String sql = "select * from household_tbl where household_id = '"+household_id+"'";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
		
	}
	
	public void addSpouse(boolean flag, String household_id, String name, String hmember_id,
			int age, String bday, int pregnant, int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO wife_tbl VALUES (?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setInt(6, pregnant);
			stmt.setInt(7, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}

	public void addChildren(boolean flag, String household_id, String name,
			String hmember_id, int age, String bday, int pregnant,
			int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO children_tbl VALUES (?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setInt(6, pregnant);
			stmt.setInt(7, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}
	public String getfPosition(String household_id) {
		String f_position = null;
		String sql = "select f_position from household_tbl where household_id = '"+household_id+"'";
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				f_position = rs.getString(1);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return f_position;
	}

	public void addGrandChildren(boolean flag, String household_id, String name,
			String hmember_id, int age, String bday, int pregnant,
			int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO grandchild_tbl VALUES (?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setInt(6, pregnant);
			stmt.setInt(7, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}

	public boolean searchWife(boolean flag, String household_id, String hmember_id) throws SQLException {
		boolean found = false;
		String sql = "select * from wife_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"' ";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}
	public boolean searchWife(boolean flag, String household_id, String hmember_id, String name) throws SQLException {
		boolean found = false;
		String sql = "select * from wife_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"' and spouse_name = '"+name+"' ";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}
	public boolean searchChildren(boolean flag, String household_id,
			String hmember_id) throws SQLException {
		boolean found = false;
		String sql = "select * from children_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"'";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}

	public boolean searchChildren(boolean flag, String household_id,String hmember_id, String name) throws SQLException {
		boolean found = false;
		String sql = "select * from children_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"' and child_name = '"+name+"' ";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}

	
	public boolean searchGrandChild(boolean flag, String household_id,
			String hmember_id) throws SQLException {
		boolean found = false;
		String sql = "select * from grandchild_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"'";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}
	public boolean searchGrandChild(boolean flag, String household_id,
			String hmember_id, String name) throws SQLException {
		boolean found = false;
		String sql = "select * from grandchild_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"' and grandchild_name = '"+name+"' ";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}
	public boolean searchHousehold(boolean flag, String household_id, String name) throws SQLException {
		boolean found = false;
		String sql = "select * from household_tbl where household_id = '"+household_id+"' and head_name = '"+name+"' ";
		
		try {
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				found = true;
			}
			
			if (flag) {
				commit();
			}
		} catch (SQLException ex) {
			if (flag)
				rollback();
			throw new SQLException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return found;
	}

	public ArrayList<LogsBean> getSearchLogs(boolean flag, String search) throws SQLException {
		ArrayList<LogsBean> list = new ArrayList<LogsBean>();
		
		LogsBean bean = null;
		try {
			rs = con.createStatement().executeQuery("select * from logs_tbl where log_message like '%"+search+"%'");

			while (rs.next()) {

				bean = new LogsBean(rs.getString("date"), rs.getString("time"), rs.getString("log_message"));
				System.out.println(rs.getString("date")+ rs.getString("time")+ rs.getString("log_message"));
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

	public String gethousehold_id(boolean flag, String sql) throws SQLException {
		String hh_id = "";
		try {

			rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				hh_id = rs.getString(1);
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
		return hh_id;
	}

	public int getMunId(String municipality) {
		int mun_id = 0;
		
		try{
		
			rs = con.createStatement().executeQuery("select mun_id from municipal_tbl where mun_name = '"+municipality+"'");
			while(rs.next()){
				mun_id = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return mun_id;
	}
	public ArrayList<reportBean> getbrgy(int mun_id) {
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select brgy_id,brgy_name from brgy_tbl where mun_id = "+mun_id;
		reportBean bean = null;
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				bean = new reportBean(rs.getInt(1),rs.getString(2));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}	
	public int getbrgyID(String barangay, int mun_id) {
		int brgy_id = 0;
		
		try{
		
			rs = con.createStatement().executeQuery("select brgy_id from brgy_tbl where mun_id = "+mun_id+" and brgy_name ='"+barangay+"'");
			while(rs.next()){
				brgy_id = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return brgy_id;
	}
	
	public String getbrgyID2(String household_id) {
		String brgy_id = "";
		
		try{
		
			rs = con.createStatement().executeQuery("select barangay from household_tbl where household_id = '"+household_id+"'");
			while(rs.next()){
				brgy_id = rs.getString(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return brgy_id;
	}
	
	public int getMunId2(String household_id) {
		int mun_id = 0;
		
		try{
		
			rs = con.createStatement().executeQuery("select municipality from household_tbl where household_id = '"+household_id+"'");
			while(rs.next()){
				mun_id = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return mun_id;
	}
	public int getTeamId() {
		int team_id = 0;
		
		try{
		
			rs = con.createStatement().executeQuery("select team_id from team_tbl");
			while(rs.next()){
				team_id = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return team_id;
	}
	
	public String getPassword(String user) {
		
		String sql = "select password from login_tbl where username = '"+user+"'";
		String pword = null;
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pword = rs.getString(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return pword;
	}

	public int UpdatePassword(String user, String npword) {
		int edited = 0;
		String sql = "update login_tbl set password = '"+npword+"' where username = '"+user+"'";
		String sql2 = "select * from login_tbl where password = '"+npword+"'";
		
		try{
			
			con.createStatement().execute(sql);
			
			rs = con.createStatement().executeQuery(sql2);
			while(rs.next()){
				edited = 1;
			}
			System.out.println("edited: "+edited);
			/*while(rs.next()){
				System.out.println("updated..?");
				edited = 1;
			}*/
			
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return edited;
	}
	
	public void addSpouse(boolean flag, String household_id, String name, String hmember_id,
			int age, String bday,String gender, int pregnant, int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO wife_tbl VALUES (?,?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setString(6, gender);
			stmt.setInt(7, pregnant);
			stmt.setInt(8, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}
	
	public void addChildren(boolean flag, String household_id, String name,
			String hmember_id, int age, String bday, String gender, int pregnant,
			int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO children_tbl VALUES (?,?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setString(6, gender);
			stmt.setInt(7, pregnant);
			stmt.setInt(8, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}
	
	public void addGrandChildren(boolean flag, String household_id, String name,
			String hmember_id, int age, String bday, String gender, int pregnant,
			int attending_school) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO grandchild_tbl VALUES (?,?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, household_id);
			stmt.setString(2, name);
			stmt.setInt(3, Integer.parseInt(hmember_id));
			stmt.setInt(4, age);
			stmt.setString(5, bday);
			stmt.setString(6, gender);
			stmt.setInt(7, pregnant);
			stmt.setInt(8, attending_school);
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}
	public void addGrsCase(boolean flag, String household_id, String grsCase,
			String syscode, String fullName, String municipal, String barangay , String idocp,
			String remarks,String date, String time, int server_id , int team_id,String user_id) throws SQLException {
		try{
			stmt = con.prepareStatement("INSERT INTO grscases2_tbl_temp(date_recorded,time_recorded,household_id," +
					"captured_province,captured_citymunicipality,captured_barangay,captured_fullname,grievance_officer,captured_grscasetype," +
					"captured_grsidoctype,status,remarks,team_id,server_id,user_id,grscasetype_id,grsidoctype_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1,date);
			stmt.setString(2,time);
			stmt.setString(3,household_id);
			stmt.setString(4,"captured_province here!");
			stmt.setString(5,municipal);
			stmt.setString(6,barangay);
			stmt.setString(7,fullName);
			stmt.setString(8,"");
			stmt.setString(9,grsCase);
			stmt.setString(10,"");
			stmt.setString(11,syscode);
			stmt.setString(12,remarks);
			stmt.setInt(13,team_id);
			stmt.setInt(14,server_id);
			stmt.setString(15,user_id);
			stmt.setInt(16,0);
			stmt.setString(17, idocp);
			
			
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				close();
			}
		}
	}
	
	public String getbrgyName(int brgy_id) {
		String brgyName = null;
		
		try{
			
			rs = con.createStatement().executeQuery("select brgy_name from brgy_tbl where brgy_id = "+brgy_id+"");
			
			while(rs.next()){
				brgyName = rs.getString(1);
			}
			
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return brgyName;
	}

	public void updateGrantee(boolean flag, reportBean bean) throws SQLException {
		
		String sql = "update household_tbl set philhealth_id = ?,household_member_id = ?,head_name = ?,age = ?,birthday = ?,gender = ?,pregnant = ?,attending_school = ?,street = ?,purok = ?,barangay = ?,municipality = ?,f_position = ? where household_id = ?  ";
		
		try{
			
			stmt = con.prepareStatement(sql);
			stmt.setString(14, bean.getHousehold_id());
			stmt.setString(1, bean.getPhilhealth_id());
			stmt.setInt(2, Integer.parseInt(bean.getHmember_id()));
			stmt.setString(3, bean.getName());
			stmt.setInt(4, bean.getAge());
			stmt.setString(5, bean.getBday());
			stmt.setString(6, bean.getGender());
			stmt.setInt(7, bean.getPregnant());
			stmt.setInt(8, bean.getAttending_school());
			stmt.setString(9, bean.getStreet());
			stmt.setString(10, bean.getPurok());
			stmt.setInt(11, bean.getBrgy_id());
			stmt.setInt(12, bean.getMun_id());
			stmt.setString(13, bean.getF_position());
			
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
	
	public ArrayList<reportBean> gethousehold(boolean flag, String household_id) throws SQLException {
		reportBean bean = null;
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		try {
			rs = con.createStatement().executeQuery("select * from household_tbl where household_id = '"+household_id+"'");

			while (rs.next()) {

				bean = new reportBean(rs.getString("household_id"),
						rs.getString("philhealth_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"), rs.getInt("age"),
						rs.getString("birthday"),rs.getString("gender"), rs.getInt("pregnant"),
						rs.getInt("attending_school"),
						rs.getString("street"), rs.getString("purok"),
						rs.getInt("barangay"), rs.getInt("municipality")
						,rs.getString("f_position"));
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
	
public boolean isExists(String name, String tbl_name, String col_name, String household_id) {
		
		boolean ex = false;
		String sql = "select * from "+tbl_name+" where "+col_name+" = '"+name+"' and household_id = '"+household_id+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				ex = true;
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return ex;
		
	}

public void updateMember(boolean flag, reportBean bean, String tbl_name, String col_name) throws SQLException {
	//System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------in");
	String sql = "update "+tbl_name+" set "+col_name+" = ?,household_member_id = ?,age = ?,birthday = ?,pregnant = ?,attending_school = ?,f_position = ?,status = ?, gender = ?  where household_id = ? and household_member_id = ?";
	
	try{
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, bean.getName());
		stmt.setInt(2, Integer.parseInt(bean.getHmember_id()));
		stmt.setInt(3, bean.getAge());
		stmt.setString(4, bean.getBday());
		stmt.setInt(5, bean.getPregnant());
		stmt.setInt(6, bean.getAttending_school());
		stmt.setString(7, bean.getF_position());
		stmt.setInt(8, bean.getStatus());
		stmt.setString(9, bean.getGender());
		stmt.setString(10, bean.getHousehold_id());
		stmt.setInt(11, Integer.parseInt(bean.getHmember_id()));
		
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

public void deleteMember(boolean flag, String household_id, String name, String tbl_name, String col_name) throws SQLException {
	
	String sql = "delete from "+tbl_name+" where "+col_name+" = ? and household_id = ?";
	
	try{
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setString(2, household_id);
		
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
	
public void UpdateFPosition(boolean flag,String ff_position, String household_id) throws SQLException {
		
		String sql = "update household_tbl set f_position = ? where household_id ='"+household_id+"' ";
		
		try{
			
			stmt = con
					.prepareStatement(sql);
			stmt.setString(1, ff_position);

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

	public ArrayList<transactionBean> getwife(boolean flag, int hh_mem_id)
			throws SQLException {
		transactionBean bean = null;
		list = new ArrayList<transactionBean>();
		try {
			rs = con.createStatement().executeQuery(
					"select * from wife_tbl where household_member_id = '" + hh_mem_id
							+ "'");
	
			while (rs.next()) {
	
				bean = new transactionBean(rs.getString("household_id"),
						rs.getString("spouse_name"),
						rs.getInt("household_member_id"), rs.getInt("age"),
						rs.getString("birthday"), rs.getBoolean("pregnant"),
						rs.getBoolean("attending_school"),rs.getString("f_position"),rs.getInt("status"),rs.getString("gender"));
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
	public ArrayList<transactionBean> getPlData() {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select fname,lname,u_name,user_gender,user_contact,user_email,photo,fingerprint from user_tbl where user_id = 1";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBytes(7),rs.getBytes(8));
				System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public void updateProvlink(String fname, String lname,String gender,String email,String contact) {
		
		String sql = "update user_tbl set fname = ?,lname = ?,user_gender=?, user_email=?, user_contact=?  where user_id = 1";
		
		try{
			stmt = con.prepareStatement(sql);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, gender);
			stmt.setString(4, email);
			stmt.setString(5, contact);
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to update.");
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
	}
	public transactionBean gethead_photo123(boolean flag)
			throws SQLException {
		
		transactionBean image = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = 1 ");
		
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
	public void update_fingerprint(boolean flag, byte[] fingerprint) throws SQLException {
		try {
			stmt = con
					.prepareStatement("update user_tbl set fingerprint = ? where user_id = 1");
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
	public void update_fingerprint_ML(boolean flag, byte[] fingerprint, String fname, String lname, int mun) throws SQLException {
		try {
			stmt = con
					.prepareStatement("update user_tbl set fingerprint = ? where user_id = 2 and fname = ? and lname = ? and mun_id = ?");
			stmt.setBytes(1, fingerprint);
			stmt.setString(2, fname);
			stmt.setString(3, lname);
			stmt.setInt(4, mun);
			
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
	
	/*Municipal User Accounts functions*/
	public void add_mun_user(String fname,String lname,String username,String pass,String gender,String email,String contact,String mun,boolean flag) throws SQLException{
		try {
			stmt = con
					.prepareStatement("INSERT INTO user_tbl(user_id,fname,lname,u_name,p_word,user_gender,user_email,user_contact,photo,fingerprint,mun_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

			stmt.setInt(1, 2);
			stmt.setString(2, fname);
			stmt.setString(3, lname);
			stmt.setString(4, username);
			stmt.setString(5, pass);
			stmt.setString(6, gender);
			stmt.setString(7, email);
			stmt.setString(8, contact);
			stmt.setBytes(9, null);
			stmt.setBytes(10, null);
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
	public ArrayList<transactionBean> getMunData(String fname,String lname,String username,String gender,String email,String contact, String mun) throws SQLException {
		System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where fname = '"+fname+"' AND lname = '"+lname+"' and u.mun_id = "+mun+" and m.mun_id = u.mun_id ";
		
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
	
	
	public ArrayList<transactionBean> getMunData(String uname, int mun_id) throws SQLException {
		//System.out.println("in sa getMunData:"+fname+" "+lname);
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where user_id = 2 and u_name = '"+uname+"' and m.mun_id = "+mun_id+" and m.mun_id = u.mun_id ";
		
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
		System.out.println("-----------------------------------------------vghbhg");
		return list;
	}
	
	public ArrayList<transactionBean> getMunData2() {
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select fname,lname,photo,fingerprint, m.mun_id, m.mun_name from user_tbl as u, municipal_tbl as m where user_id = 2 and m.mun_id = u.mun_id order by m.mun_id";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5), rs.getString(6));
				//System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public void updateMunUser(String fname, String lname,String gender,String email,String contact,String r_fname,String r_lname,int omun_id, int nmun_id) throws SQLException  {
		
		String sql = "update user_tbl set fname = ?,lname = ?,user_gender=?,user_email=?,user_contact=?,mun_id = ? where user_id = 2 AND fname = '"+r_fname+"' AND lname = '"+r_lname+"' and mun_id= "+omun_id;
		
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
	public boolean checkfname(String fname, String lname, String mun){
		boolean exist = false;
		String sql = "SELECT * FROM user_tbl WHERE fname = '"+fname+"' and lname = '"+lname+"' and mun_id = '"+mun+"' ";
		
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				exist = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return exist;
	}

	public transactionBean getmunlink_photo(boolean flag, String f, String l, int m) throws SQLException {
		transactionBean image = null;
		
		try {
			rs = con.createStatement().executeQuery(
					"select photo from user_tbl where user_id = 2 and mun_id="+m+" and fname='"+f+"' and lname='"+l+"' ");
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

	public int getMunId(String fname, String lname) {
		int mun = 0;
		
		try{
			
			rs = con.createStatement().executeQuery("select mun-id from user_tbl where fname = '"+fname+"' and lname = '"+lname+"'");
			while(rs.next()){
				mun = rs.getInt(1);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return mun;
	}

	public ArrayList<transactionBean> getMunLink(int mun_id) {
		//System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
		ArrayList<transactionBean> list = new ArrayList<transactionBean>();
		transactionBean bean = null;
		String sql = "select fname,lname,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where u.mun_id = "+mun_id+" and m.mun_id = u.mun_id and user_id = 2 ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5),rs.getString(6));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}

	public String getMunValue(String mun_id) {
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
public ArrayList<String> getPassword2(String user) {
		
		String sql = "select password from login_tbl where username = '"+user+"'";
		ArrayList<String> pword = new ArrayList<String>();;
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pword.add(rs.getString(1));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return pword;
	}
public int searchArgie(boolean flag, byte[] search, String household) throws SQLException {
	//ArrayList<LogsBean> list = new ArrayList<LogsBean>();
	
	//LogsBean bean = null;
	int ctr = 0;
	try {
		//rs = con.createStatement().executeQuery("select * from fingerprint_tbl_temp where fingerprint = ? ");
		stmt = con.prepareStatement("select * from fingerprint_tbl_temp where household_id = ?");
		

		 stmt.setString(1, household);
		//stmt.setBytes(1, search);
		rs = stmt.executeQuery();
		System.out.println("rs pa la"+search);
		
		while (rs.next()) {
			boolean re = Arrays.equals(search, rs.getBytes("fingerprint"));
			System.out.println("compared"+re);
			if(re){
				System.out.println("equals an bytes of arrays.");
			}
			
			search = rs.getBytes("fingerprint");
			ctr++;
			/*bean = new LogsBean(rs.getString("date"), rs.getString("time"), rs.getString("log_message"));
			System.out.println(rs.getString("date")+ rs.getString("time")+ rs.getString("log_message"));
			list.add(bean);*/
		}
		stmt = con.prepareStatement("select * from fingerprint_tbl_temp where fingerprint = ? ");
		stmt.setBytes(1, search);
		//stmt.setString(1, "083733002-5275-00050");
		
		rs = stmt.executeQuery();
		//String var = "083733002-5275-00050";
		//rs = con.createStatement().executeQuery("select * from fingerprint_tbl_temp where fingerprint = '%"+search+"%' ");
		
		System.out.println("createStatement na an gamit.");
		int count = 0;
		while(rs.next()){
			System.out.println("count:"+
					++count);
			System.out.println("It's working.");
		}
		System.out.println("Anover Gwapo igsulod ha sako..?(.harsh word..sumat q kaw kan kuya dexon.. :P)");

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
	return ctr;
	//return list;
}


//--------------------------------- Book Keeper functions -----------------------------------------------//


public void updateBookKeeper(String fname, String lname,String gender,String email,String contact,String r_fname,String r_lname,int omun_id, int nmun_id) throws SQLException  {
	
	String sql = "update user_tbl set fname = ?,lname = ?,user_gender=? ,user_email=?,user_contact=? ,mun_id = ? where user_id = 4 AND fname = '"+r_fname+"' AND lname = '"+r_lname+"' and mun_id= "+omun_id;
	
	try{
		stmt = con.prepareStatement(sql);
		stmt.setString(1, fname);
		stmt.setString(2, lname);
		stmt.setString(3, gender);
		stmt.setString(4, email);
		stmt.setString(5, contact);
		stmt.setInt(6, nmun_id);
		int ret = stmt.executeUpdate();
		System.out.println(sql);
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to update.");
		System.out.println(sql);
	}
	catch(Exception ex){
		System.out.println(ex);
	}		
}

public ArrayList<transactionBean> getBookKeeperList() {
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint, m.mun_id, m.mun_name from user_tbl as u, municipal_tbl as m where user_id = 4 and m.mun_id = u.mun_id order by m.mun_id";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5), rs.getString(6));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}

public boolean checkBookKeeper(String fname, String lname, int mun) {
	boolean dup = false;
	String sql = "select * from user_tbl where user_id = 4 and fname='"+fname+"' and lname = '"+lname+"' and mun_id = "+mun;
	
	try{
	
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			dup = true;
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return dup;
}

public void addBookKeeper(boolean flag, String fname, String lname,String username,String password,String gender,String email,String contact, String mun) throws SQLException {
	try {
		stmt = con
				.prepareStatement("INSERT INTO user_tbl(user_id,fname,lname,u_name,p_word,user_gender,user_email,user_contact,photo,fingerprint,mun_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

		stmt.setInt(1, 4);
		stmt.setString(2, fname);
		stmt.setString(3, lname);
		stmt.setString(4, username);
		stmt.setString(5, password);
		stmt.setString(6, gender);
		stmt.setString(7, email);
		stmt.setString(8, contact);
		stmt.setBytes(9, null);
		stmt.setBytes(10, null);
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

public ArrayList<transactionBean> getBookKeeperList(String fname, String lname,String username,String gender,String email,String contact, String mun) {
	System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where user_id=4 and fname = '"+fname+"' AND lname = '"+lname+"' and u.mun_id = "+Integer.parseInt(mun)+" and m.mun_id = u.mun_id ";
	
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
			System.out.println("----"+rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	System.out.println("-----------------------------------------------vghbhg");
	return list;
}

public boolean updateBookKepperPhoto(byte[] imageInByte,String fname, String lname, int mun) {
	boolean updated = false;
	
	try{
		
		stmt = con.prepareStatement("update user_tbl set photo = ? where  user_id = 4 and mun_id="+mun+" and fname='"+fname+"' and lname='"+lname+"' ");		
		stmt.setBytes(1, imageInByte);
		
		int s = stmt.executeUpdate();
		if(s>0){
			System.out.println("Uploaded successfully !");
			updated = true;
		}
		else{
			System.out.println("Upload image Error!");
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return updated;
}

public transactionBean getbk_photo(boolean flag, String f, String l, int m) throws SQLException {
	transactionBean image = null;
	
	try {
		rs = con.createStatement().executeQuery(
				"select photo from user_tbl where user_id = 4 and mun_id="+m+" and fname='"+f+"' and lname='"+l+"' ");
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

public void update_fingerprint_BK(boolean flag, byte[] fingerprint,
		String fname, String lname, int mun) throws SQLException {
	
	try {
		stmt = con
				.prepareStatement("update user_tbl set fingerprint = ? where user_id = 4 and fname = ? and lname = ? and mun_id = ?");
		stmt.setBytes(1, fingerprint);
		stmt.setString(2, fname);
		stmt.setString(3, lname);
		stmt.setInt(4, mun);
		
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
public ArrayList<transactionBean> getMunLink(int mun_id,int user_id) {
	//System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint,m.mun_id,m.mun_name  from user_tbl as u , municipal_tbl as m where u.mun_id = "+mun_id+" and m.mun_id = u.mun_id and user_id = "+user_id;
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5),rs.getString(6));
			System.out.println("----"+rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return list;
}

public int UpdateFAPhoto(byte[] imageInByte) {
	int up = 0;
	
	try{
		stmt = con.prepareStatement("update user_tbl set photo = ? where user_id = 3");		
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
public String getName_ofUser( int user_id) {
	String up = "";
	
	try{
			
		rs= con.createStatement().executeQuery("select lname, fname from user_tbl where user_id = '"+user_id+"'  ");
		
		while(rs.next()){
			up = rs.getString("lname")+", "+rs.getString("fname");
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return up;
}
public String getName_ofmunUser( String username, String password) {
	String up = "";
	
	try{
			
		rs= con.createStatement().executeQuery("select lname, fname from user_tbl where u_name = '"+username+"' and p_word = '"+password+"'  ");
		
		while(rs.next()){
			up = rs.getString("lname")+", "+rs.getString("fname");
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return up;
}
public int getMunID(String uname) {
	int m =0;
	
	try{
		
		rs= con.createStatement().executeQuery("select mun_id from user_tbl where u_name= '"+uname+"'");
		while(rs.next()){
			m = rs.getInt(1);
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return m;
}

public String getUserName(String name) {
	String userId = null;
	
	try{
		
		rs= con.createStatement().executeQuery("select fname,lname,u_name from user_tbl where user_id = 2 ");
		while(rs.next()){
			String uname = rs.getString("fname")+" "+rs.getString("lname");
			if(uname.equalsIgnoreCase(name)){
				userId = rs.getString("u_name");
			}
			
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return userId;
}

public String getFname(String uname, int mun_id) {
String fname = null;
	
	try{
		
		rs= con.createStatement().executeQuery("select fname from user_tbl where u_name = '"+uname+"' and user_id = 2 and mun_id = "+mun_id);
		while(rs.next()){
			
			fname = rs.getString("fname");
			
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return fname;
}
public String getLname(String uname, int mun_id) {
	String lname = null;
		
		try{
			
			rs= con.createStatement().executeQuery("select lname from user_tbl where u_name = '"+uname+"' and user_id = 2 and mun_id = "+mun_id);
			while(rs.next()){
				
				lname = rs.getString("lname");
				
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return lname;
	}

public boolean checkDuplicate(String cellValue) {
	boolean dup = false;
	
	try{
		
		//rs = con.createStatement().executeQuery("select * from isolated_fingerprint_db where household_id = '"+cellValue+"'");
		
		rs = con.createStatement().executeQuery("select * from household_tbl where household_id = '"+cellValue+"'");
		while(rs.next()){
			dup = true;
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return dup;
}

public boolean hasData(String l) {
	
	boolean exists = false;

	String sql2 = "select household_id from  fingerprint_tbl_temp where household_id = '"+l+"'";
	
	try{
		
		rs = con.createStatement().executeQuery(sql2);
		while(rs.next()){
			exists = true;
			System.out.println("dup:"+l);
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return exists;
}
public boolean hasData2(String l) {
	
	boolean exists = false;

	String sql = "select household_id from household_tbl where household_id = '"+l+"'";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			exists = true;
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return exists;
}
public ArrayList<fingerprintBean> getallfingerprint(boolean flag) throws SQLException {
	
	ArrayList<fingerprintBean> list = new ArrayList<fingerprintBean>();
	fingerprintBean bean = null;
	
	try{
		
		rs=con.createStatement().executeQuery("SELECT * FROM `import_fingerprint_tbl` ");
		
		while (rs.next()) {
			
			bean = new fingerprintBean(rs.getString("household_id"),rs.getBytes("fingerprint"),rs.getString("date_recorded"),rs.getString("time_recorded"));
			list.add(bean);
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(flag)
		close();
	}
	
	return list;
}

//--------------------------------- verifier account modification ---------------------------------------------//

/*
public ArrayList<transactionBean> getVerifierList() {
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint, m.mun_id, m.mun_name,b.brgy_id,brgy_name from user_tbl as u, municipal_tbl as m,brgy_tbl as b where user_id = 5 and m.mun_id = b.mun_id and b.mun_id = u.mun_id and b.brgy_id = u.brgy_id and m.mun_id = u.mun_id order by mun_name,brgy_name,lname";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5), rs.getString(6), rs.getInt(7),rs.getString(8));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}
*/


public ArrayList<transactionBean> getVerifierList() {
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname from user_tbl where user_id = 5 order by lname";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}
public ArrayList<transactionBean> getListVerifier(){
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint, m.mun_id, m.mun_name from user_tbl as u, municipal_tbl as m where user_id = 5 and m.mun_id = u.mun_id order by m.mun_id";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5), rs.getString(6));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}

public ArrayList<transactionBean> getVerifierList(String fname, String lname) {
	//System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,u_name,user_gender,user_email,user_contact,photo,fingerprint  from user_tbl where user_id=5 and fname = '"+fname+"' AND lname = '"+lname+"'";
	
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
					rs.getBytes(8));
			System.out.println("----"+rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	System.out.println("-----------------------------------------------vghbhg");
	return list;
}

public boolean checkVerifier(String fname, String lname, int mun) {
	boolean dup = false;
	String sql = "select * from verifier_tbl where user_id = 5 and fname='"+fname+"' and lname = '"+lname+"' and mun_id = "+mun;
	
	try{
	
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			dup = true;
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return dup;
}
public ArrayList<transactionBean> getVerifierList2() {
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint, m.mun_id, m.mun_name from verifier_tbl as u, municipal_tbl as m where user_id = 5 and m.mun_id = u.mun_id order by m.mun_id";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5), rs.getString(6));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}

public void addVerifier(boolean flag, String fname, String lname,String username,String password,String gender,String email,String contact, String mun) throws SQLException {
	try {
		stmt = con.prepareStatement("INSERT INTO user_tbl(user_id,fname,lname,u_name,p_word,user_gender,user_email,user_contact,photo,fingerprint,mun_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

		stmt.setInt(1, 5);
		stmt.setString(2, fname);
		stmt.setString(3, lname);
		stmt.setString(4, username);
		stmt.setString(5, password);
		stmt.setString(6, gender);
		stmt.setString(7, email);
		stmt.setString(8, contact);
		stmt.setBytes(9, null);
		stmt.setBytes(10, null);
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
public ArrayList<transactionBean> getVerifier(int mun_id,int user_id) {
	//System.out.println("in sa getMunData:"+fname+" "+lname+" "+mun);
	ArrayList<transactionBean> list = new ArrayList<transactionBean>();
	transactionBean bean = null;
	String sql = "select fname,lname,photo,fingerprint,m.mun_id,m.mun_name  from verifier_tbl as u , municipal_tbl as m where u.mun_id = "+mun_id+" and m.mun_id = u.mun_id and user_id = "+user_id;
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getString(1),rs.getString(2),rs.getBytes(3),rs.getBytes(4),rs.getInt(5),rs.getString(6));
			System.out.println("----"+rs.getString(1)+rs.getString(2)+rs.getBytes(3)+rs.getBytes(4));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return list;
}

public void updateVerifier(String fname, String lname,String gender,String email,String contact,String r_fname,String r_lname,int omun_id, int nmun_id) throws SQLException  {
	
	String sql = "update verifier_tbl set fname = ?,lname = ?,user_gender=? ,user_email=?,user_contact=? ,mun_id = ? where user_id = 5 AND fname = '"+r_fname+"' AND lname = '"+r_lname+"' and mun_id= "+omun_id;
	
	try{
		stmt = con.prepareStatement(sql);
		stmt.setString(1, fname);
		stmt.setString(2, lname);
		stmt.setString(3, gender);
		stmt.setString(4, email);
		stmt.setString(5, contact);
		stmt.setInt(6, nmun_id);
		int ret = stmt.executeUpdate();
		System.out.println(sql);
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to update.");
		System.out.println(sql);
	}
	catch(Exception ex){
		System.out.println(ex);
	}		
}

public void update_fingerprint_Ver(boolean flag, byte[] fingerprint,
		String fname, String lname, int mun) throws SQLException {
	
	try {
		stmt = con
				.prepareStatement("update verifier_tbl set fingerprint = ? where user_id = 5 and fname = ? and lname = ? and mun_id = ?");
		stmt.setBytes(1, fingerprint);
		stmt.setString(2, fname);
		stmt.setString(3, lname);
		stmt.setInt(4, mun);
		
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
public boolean updateVerifierPhoto(byte[] imageInByte,String fname, String lname) {
	boolean updated = false;
	
	try{
		
		stmt = con.prepareStatement("update user_tbl set photo = ? where  user_id = 5 and fname='"+fname+"' and lname='"+lname+"' ");		
		stmt.setBytes(1, imageInByte);
		
		int s = stmt.executeUpdate();
		if(s>0){
			System.out.println("Uploaded successfully !");
			updated = true;
		}
		else{
			System.out.println("Upload image Error!");
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return updated;
}

public transactionBean getver_photo(boolean flag, String f, String l) throws SQLException {
	transactionBean image = null;
	
	try {
		rs = con.createStatement().executeQuery(
				"select photo from user_tbl where user_id = 5 and fname='"+f+"' and lname='"+l+"' ");
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

//---------------------Sayao Functions ---------------------//

public ArrayList<reportBean> getAllBrgy() {

	ArrayList<reportBean> list = new ArrayList<reportBean>();
	String sql = "select mun_id,brgy_id,brgy_name from brgy_tbl";
	reportBean bean =null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getInt("mun_id"),rs.getInt("brgy_id"),rs.getString("brgy_name"));
			list.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	
	return list;
}

public boolean searchHousehold(String household_id) throws SQLException{
	
	boolean found = false;
	String sql = "select * from household_tbl where household_id = '"+household_id+"'";
	
	try {
		
		rs = con.createStatement().executeQuery(sql);
		
		while(rs.next()){
			found = true;
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return found;
	
}
public void addHousehold(boolean flag, ArrayList<reportBean> glist) throws SQLException {
	try{
		for(reportBean bean:glist){
			
			
			stmt = con.prepareStatement("INSERT INTO household_tbl(household_id,philhealth_id,household_member_id,head_name,age, birthday,gender,pregnant,attending_school,street,purok,barangay,municipality,f_position,hh_set,set_group,status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("Bean:"+bean.getBrgy_id());
			// stmt.setBytes(1, bean.getFingerprint());
			stmt.setString(1, bean.getHousehold_id());
			stmt.setString(2, bean.getPhilhealth_id());
			stmt.setInt(3, Integer.parseInt(bean.getHmember_id()));
			stmt.setString(4, bean.getName());
			stmt.setInt(5, bean.getAge());
			stmt.setString(6, bean.getBday());
			stmt.setString(7, bean.getGender());
			stmt.setInt(8, bean.getPregnant());
			stmt.setInt(9, bean.getAttending_school());
			stmt.setString(10, bean.getStreet());
			stmt.setString(11, bean.getPurok());
			stmt.setInt(12, bean.getBrgy_id());
			stmt.setInt(13, bean.getMun_id());
			stmt.setString(14, bean.getF_position());
			stmt.setString(15, bean.getHh_set());
			stmt.setString(16, bean.getSet_group());
			stmt.setInt(17, bean.getStatus());
			int ret = stmt.executeUpdate();
			// When Insert not completed
			if (ret != 1)
				throw new SQLException("Failed to add.");
			if (flag) {
				commit();
			}
			
		}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	if (flag) {
		close();
	}
}
}

public ArrayList<reportBean2> getHHList(String tbl,String col) {
	
	ArrayList<reportBean2> list = new ArrayList<reportBean2>();
	String sql = "select household_id,"+col+" from "+tbl;
	reportBean2 bean = null;
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean2(rs.getString(1),rs.getString(2));
			list.add(bean);
		}
		
		
	}
	catch(Exception ex){
		
	}
	
	return list;
	
}

public void addSpouse(boolean flag, String household_id, String name, String hmember_id,
		int age, String bday, int pregnant, int attending_school, String f_position, int status, String gender) throws SQLException {
	try{
		stmt = con.prepareStatement("INSERT INTO wife_tbl VALUES (?,?,?,?,?,?,?,?,?,?)");

		// stmt.setBytes(1, bean.getFingerprint());
		stmt.setString(1, household_id);
		stmt.setString(2, name);
		stmt.setInt(3, Integer.parseInt(hmember_id));
		stmt.setInt(4, age);
		stmt.setString(5, bday);
		stmt.setInt(6, pregnant);
		stmt.setInt(7, attending_school);
		stmt.setString(8, f_position);
		stmt.setInt(9, status);
		stmt.setString(10, gender);
		
		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to add.");
		if (flag) {
			commit();
		}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}
}
public void addChildren(boolean flag, String household_id, String name,
		String hmember_id, int age, String bday, int pregnant,
		int attending_school, String f_position, int status, String gender) throws SQLException {
	try{
		stmt = con.prepareStatement("INSERT INTO children_tbl VALUES (?,?,?,?,?,?,?,?,?,?)");

		// stmt.setBytes(1, bean.getFingerprint());
		stmt.setString(1, household_id);
		stmt.setString(2, name);
		stmt.setInt(3, Integer.parseInt(hmember_id));
		stmt.setInt(4, age);
		stmt.setString(5, bday);
		stmt.setInt(6, pregnant);
		stmt.setInt(7, attending_school);
		stmt.setString(8, f_position);
		stmt.setInt(9, status);
		stmt.setString(10, gender);
		
		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to add.");
		if (flag) {
			commit();
		}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}
}
public void addGrandChildren(boolean flag, String household_id, String name,
		String hmember_id, int age, String bday, int pregnant,
		int attending_school, int status, String gender) throws SQLException {
	try{
		stmt = con.prepareStatement("INSERT INTO grandchild_tbl VALUES (?,?,?,?,?,?,?,?,?)");

		// stmt.setBytes(1, bean.getFingerprint());
		stmt.setString(1, household_id);
		stmt.setString(2, name);
		stmt.setInt(3, Integer.parseInt(hmember_id));
		stmt.setInt(4, age);
		stmt.setString(5, bday);
		stmt.setInt(6, pregnant);
		stmt.setInt(7, attending_school);
		stmt.setInt(8, status);
		stmt.setString(9, gender);
		
		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to add.");
		if (flag) {
			commit();
		}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}
}
public void addOtherRealtives(boolean flag, String household_id, String name,
		String hmember_id, int age, String bday, int pregnant,
		int attending_school, String f_position, int status, String gender) throws SQLException {
	try{
		stmt = con.prepareStatement("INSERT INTO other_relatives_tbl VALUES (?,?,?,?,?,?,?,?,?,?)");

		// stmt.setBytes(1, bean.getFingerprint());
		stmt.setString(1, household_id);
		stmt.setString(2, name);
		stmt.setInt(3, Integer.parseInt(hmember_id));
		stmt.setInt(4, age);
		stmt.setString(5, bday);
		stmt.setInt(6, pregnant);
		stmt.setInt(7, attending_school);
		stmt.setString(8, f_position);
		stmt.setInt(9, status);
		stmt.setString(10, gender);
		
		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1)
			throw new SQLException("Failed to add.");
		if (flag) {
			commit();
		}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (flag) {
			close();
		}
	}
}
public boolean searchOtherRelatives(boolean flag, String household_id,
		String hmember_id) throws SQLException {
	boolean found = false;
	String sql = "select * from other_relatives_tbl where household_id = '"+household_id+"' and household_member_id ='"+hmember_id+"'";
	
	try {
		
		rs = con.createStatement().executeQuery(sql);
		
		while(rs.next()){
			found = true;
		}
		
		if (flag) {
			commit();
		}
	} catch (SQLException ex) {
		if (flag)
			rollback();
		throw new SQLException(ex.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {

	}
	
	return found;
}

@SuppressWarnings("unchecked")
public ArrayList<transactionBean> getother_rel_info(boolean flag,
		String hh_id) throws SQLException {
	transactionBean bean = null;
	list = new ArrayList<transactionBean>();
	try {
		rs = con.createStatement().executeQuery(
				"select * from other_relatives_tbl where household_id = '" + hh_id
						+ "'");

		while (rs.next()) {

			bean = new transactionBean(rs.getString("household_id"),
					rs.getInt("household_member_id"),
					rs.getString("fullname"), rs.getInt("age"),
					rs.getString("birthday"), rs.getBoolean("pregnant"),
					rs.getBoolean("attending_school"),rs.getString("f_position"),rs.getInt("status"),rs.getString("gender"));
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


public ArrayList<transactionBean> getHH_SetGroup(boolean b, String household_id) {
	
	ArrayList<transactionBean>  l = new ArrayList<transactionBean> ();
	String sql = "select hh_set,set_group from household_tbl where household_id = '"+household_id+"'";
	transactionBean bean =  null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new transactionBean(rs.getInt("hh_set"),rs.getString("set_group"));
			l.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;
	
	
}

public boolean isExists(String name, String tbl_name, String col_name, String household_id, String hmember) {
	
	boolean ex = false;
	String sql = "select * from "+tbl_name+" where "+col_name+" = '"+name+"' and household_id = '"+household_id+"' and household_member_id = "+hmember;
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		
		while(rs.next()){
			ex = true;
		}
		
	}
	catch(Exception e){
		System.out.println(e);
	}
	
	return ex;
	
}

public void deleteMember(boolean flag, String household_id, String hmember, String name, String tbl_name, String col_name) throws SQLException {
	
	String sql = "delete from "+tbl_name+" where "+col_name+" = ? and household_id = ? and household_member_id = ?";
	
	try{
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setString(2, household_id);
		stmt.setString(3, hmember);
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

public void updateMemberG(boolean flag, reportBean bean, String tbl_name, String col_name) throws SQLException {
	//System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------in");
	String sql = "update "+tbl_name+" set "+col_name+" = ?,household_member_id = ?,age = ?,birthday = ?,pregnant = ?,attending_school = ?,status = ?, gender = ? where household_id = ? and household_member_id = ?";
	
	try{
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, bean.getName());
		stmt.setInt(2, Integer.parseInt(bean.getHmember_id()));
		stmt.setInt(3, bean.getAge());
		stmt.setString(4, bean.getBday());
		stmt.setInt(5, bean.getPregnant());
		stmt.setInt(6, bean.getAttending_school());
		stmt.setInt(7, bean.getStatus());
		stmt.setString(8, bean.getGender());
		stmt.setString(9, bean.getHousehold_id());
		stmt.setInt(10, Integer.parseInt(bean.getHmember_id()));
		
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
public int getServerId(){
	int server_id = 0;
	String sql = "select server_id from s_tbl";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		
		while(rs.next()){
			server_id = rs.getInt("server_id");
		}
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return server_id ;
}

public void UpdateMember(boolean flag, String household_id, int hmember_id,
		String gender, String string) throws SQLException {
	
	try {
		stmt = con
				.prepareStatement("update ? set gender = ? where household_id = ? and household_member_id = ?");
		stmt.setString(1, string);
		stmt.setString(2, gender);
		stmt.setString(3, household_id);
		stmt.setInt(4, hmember_id);
		
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
public String getDateAndTime(){
	String dateAndTime = "";
	try{
		rs = con.createStatement().executeQuery("Select NOW()");
	while(rs.next()){
		dateAndTime = rs.getString(1);
	}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return dateAndTime;
}
public String getTime(){
	String dateAndTime = "";
	try{
		rs = con.createStatement().executeQuery("Select CURTIME()");
	while(rs.next()){
		dateAndTime = rs.getString(1);
	}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return dateAndTime;
}
public void addWebData(String id,String fname,String mname,String lname,String bday,String gender,int lengthofstay,int maritalstat,int occupation){
	
	try {
		stmt = con
				.prepareStatement("insert into mcct_grantee_tbl(FamilyId,fname,mname,lname,birthday,gender,lengthofstay,maritalStat,occupation) values(?,?,?,?,?,?,?,?,?)");
		
		stmt.setString(1, id);
		stmt.setString(2, fname);
		stmt.setString(3, mname);
		stmt.setString(4, lname);
		stmt.setString(5, bday);
		stmt.setString(6, gender);
		stmt.setInt(7, lengthofstay);
		stmt.setInt(8, maritalstat);
		stmt.setInt(9, occupation);
		
		int ret = stmt.executeUpdate();
		// When Insert not completed
		if (ret != 1){
			throw new SQLException("Failed to add.");
		}else{
			System.out.println(id+" named "+fname+" "+lname+" was successfully added to database.");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
	}
}
public void add_fingerprintForFM(boolean flag, String hh_id, byte[] fingerprint)
	throws SQLException {
	
	try {
	stmt = con
			.prepareStatement("insert into fingerprint_tbl_tempForFM(household_id,fingerprintForFM) values(?,?)");
	
	stmt.setString(1, hh_id);
	stmt.setBytes(2, fingerprint);
	
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
public ArrayList<transactionBean> getallfingerprintForFM(boolean flag, String sql) throws SQLException {
	
	transactionBean fingerprint = null;
	ArrayList<transactionBean> allFingerPrint = new ArrayList<transactionBean>();
	try {
		/*	sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h, received_tbl as r" +
			"  where h.household_id = f.household_id and r.household_id = f.household_id and receive = 0 and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
			*//*sql = "SELECT distinct f.household_id, f.fingerprint FROM `fingerprint_tbl_temp` as f , household_tbl as h" +
			"  where h.household_id = f.household_id and h.municipality like '%"+municipal+"%' and barangay like '%"+brgy_id+"%'  order by head_name ";
		System.out.println(sql);*/
		rs=con.createStatement().executeQuery(sql);
		
		while (rs.next()) {
			
			fingerprint = new transactionBean(rs.getBytes(1), rs.getString(2));
			allFingerPrint.add(fingerprint);
		}

	} catch (SQLException ex) {
		if(flag)
			rollback();
		throw new SQLException(ex.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(flag)
		close();
	}
	return allFingerPrint;
}
}
