package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Beanstransaction;

import myconnection.ConnectionDAO;

public class WithFingerprintDAO extends ConnectionDAO {

	public WithFingerprintDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Beanstransaction>household_listnf(String val ,int condition){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			if(condition==0){
				rs=con.createStatement().executeQuery("SELECT household_id,household_member_id,head_name,barangay,municipality FROM household_tbl where " +
						"household_id not in (select household_id from fingerprint_tbl_temp) " +
						"order by rand(), head_name limit 50");
			}
			
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Beanstransaction>household_list(String val ,int condition){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			if(condition==1){
				rs=con.createStatement().executeQuery("select f.household_id,head_name,date_recorded,time_recorded " +
						"from fingerprint_tbl_temp as f ,household_tbl as h " +
						"where h.household_id = f.household_id order by rand(), head_name limit 50");
			}
			else{
				rs=con.createStatement().executeQuery("select f.household_id,head_name,date_recorded,time_recorded" +
						"from fingerprint_tbl_temp as f ,household_tbl as h " +
						"where h.household_id = f.household_id and household_id like '%"+val+"%' order by head_name");
			}
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getString("head_name"),
						rs.getString("date_recorded"),
						rs.getString("time_recorded"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Beanstransaction>getlist(String search, String col, String municipal, String barangay, int condition){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		String query = "";
		try{
			if(condition==1){
				// query for search  by  birthday.
				/*query = "select f.household_id,head_name,fingerprint,date_recorded,time_recorded " +
				"from fingerprint_tbl as f ,household_tbl as h " +
				"where h.household_id = f.household_id and h."+col+" like '%"+search+"%' "
										+"order by head_name";*/
				// query for search by date of registration.
				query = "SELECT h.household_id , head_name, date_recorded, time_recorded " +
						"FROM `fingerprint_tbl_temp`as f, household_tbl as h " +
						"WHERE h.household_id = f.household_id " +
						"and date_recorded = '"+search+"' " +
						"order by head_name";
			}
			else if(condition == 2){
				query = 
				"select f.household_id,head_name,date_recorded,time_recorded from fingerprint_tbl_temp as f, household_tbl as h,wife_tbl as w  "
				+"where h.household_id = f.household_id and w.spouse_name like '%"+search+"%' and w.household_id = h.household_id "
				+"order by head_name";
			}
			else if(condition == 3){
				query = "select f.household_id,head_name,date_recorded,time_recorded " +
				"from fingerprint_tbl_temp as f ,household_tbl as h " +
					"where h.household_id = f.household_id and municipality like '"+municipal+"%' and barangay='"+barangay+"' order by head_name";
			}
			else if(condition == 4){
				query = "select f.household_id,head_name,date_recorded,time_recorded " +
				"from fingerprint_tbl_temp as f ,household_tbl as h " +
				"where h.household_id = f.household_id and municipality like '"+municipal+"%' order by head_name";
			}
			else if(condition == 5){
				query = "select f.household_id,head_name,date_recorded,time_recorded " +
				"from fingerprint_tbl_temp as f ,household_tbl as h " +
				"where h.household_id = f.household_id and barangay = "+search+" order by head_name";
			}
			else if(condition == 6){
				query = "select f.household_id,head_name,date_recorded,time_recorded " +
				"from fingerprint_tbl_temp as f ,household_tbl as h " +
				"where h.household_id = f.household_id and h.household_id like '%"+search+"%' order by head_name";
			}
			rs=con.createStatement().executeQuery(query); 
			int c = 0;
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getString("head_name"),
						rs.getString("date_recorded"),
						rs.getString("time_recorded"));
				list.add(beans);
				c++;
			}
			System.out.println("c:"+c);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
}
