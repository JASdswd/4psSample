package DAO;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import test.fingerprintBean;

import bean.reportBean;
import bean.reportBean2;

import myconnection.ConnectionDAO;

public class reportDAO extends ConnectionDAO{

	
	public reportDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<reportBean> getList(String sdate, String edate) {
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,h.barangay,h.municipality,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id,r.sub"+
						" FROM household_tbl as h,received_tbl as r "+
						" WHERE h.household_id = r.household_id and r.receive = 1 and r.date_receive between '"+sdate+"' and '"+edate+"'";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10);
				if(pId == null){
					pId = "";
				}
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(11));
				System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease(String sdate, String edate) {
		
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl "+
					"WHERE  receive = 1 and date_receive between '"+sdate+"' and '"+edate+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
		
	}

	public ArrayList<reportBean> getList(String sdate, String edate, String search, String col) {
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,h.barangay,h.municipality,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id,r.sub"+
						" FROM household_tbl as h,received_tbl as r "+
						" WHERE h.household_id = r.household_id and r.receive = 1 and r.date_receive between '"+sdate+"' and '"+edate+"' and h."+col+" = '"+search+"'";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10);
				if(pId == null){
					pId = "";
				}
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(11));
				System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease(String sdate, String edate, String search, String col) {
		
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl "+
					"WHERE  receive = 1 and date_receive between '"+sdate+"' and '"+edate+"' and "+col+"='"+search+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
		
	}

	public ArrayList<reportBean> getList1(String search, String string, int receive) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h."+string+" = '"+search+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getList1(String search, String string, int receive, String trans) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h."+string+" = "+Integer.parseInt(search)+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				/*pId = rs.getString(10);
				if(pId == null){*/
					pId = rs.getString(10)+rs.getString(11);
				/*}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println(receive+"-------------------------------in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease1(String search, String string, int receive) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl "+
					"WHERE  receive = "+receive+" and "+string+"='"+search+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public float getTotalRelease2(String search, String string, int receive) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and household_tbl."+string+"='"+search+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total releases");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}
	
	public float getTotalRelease2(String search, String string, int receive, String trans) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and municipality="+Integer.parseInt(search)+" and received_tbl.month_and_year = '"+trans+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release:"+total_release);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}
	
	public float getTotalRelease5(String var, int receive) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and month_and_year='"+var+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public float getTotalRelease3(String sdate, String edate, String search, String col) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl  "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = 1 and date_receive between '"+sdate+"' and '"+edate+"' and household_tbl."+col+"='"+search+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public ArrayList<reportBean> getMunList() {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		reportBean bean = null;
		String sql = "SELECT distinct mun_id,mun_name  "+
						"from household_tbl as h,received_tbl as r,municipal_tbl as m "+
						"where h.household_id = r.household_id and m.mun_id = h.municipality and r.receive = 1 ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new reportBean(rs.getInt(1),rs.getString(2));
				System.out.println("----"+rs.getInt(1)+rs.getString(2));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean> getAllMun() {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT mun_id,mun_name from municipal_tbl ";
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
	
	public ArrayList<reportBean> getAllBrgy() {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		reportBean bean = null;
		String sql = "SELECT municipal_tbl.mun_id,brgy_id,brgy_name from brgy_tbl,municipal_tbl where municipal_tbl.mun_id = brgy_tbl.mun_id ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new reportBean(rs.getInt(1),rs.getInt(2),rs.getString(3));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getBrgy(String mun) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		reportBean bean = null;
		String sql = "SELECT distinct brgy_id,brgy_name "+
						"from household_tbl as h,received_tbl as r,brgy_tbl as b "+
						"where h.household_id = r.household_id and r.receive = 1 and brgy_id = barangay and h.municipality='"+mun+"'";
		
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

	public ArrayList<reportBean> getList2(String mun, String brgy, int receive) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality = '"+mun+"' and h.barangay ='"+brgy+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getList2(String mun, String brgy, int receive, String trans) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality = "+mun+" and h.barangay ="+brgy+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease4(String mun, String brgy, int receive) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl  "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and household_tbl.municipality = '"+mun+"' and household_tbl.barangay = '"+brgy+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total releasetotal per brgy");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	

	public float getTotalRelease4(String mun, String brgy, int receive, String trans) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl  "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and household_tbl.municipality = '"+mun+"' and household_tbl.barangay = '"+brgy+"' and received_tbl.month_and_year = '"+trans+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public ArrayList<reportBean> getList6(String sdate, String edate,
			String mun, String brgy) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,h.barangay,h.municipality,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id,r.sub"+
						" FROM household_tbl as h,received_tbl as r "+
						" WHERE h.household_id = r.household_id and r.receive = 1 and h.municipality = '"+mun+"' and h.barangay ='"+brgy+"' and date_receive between '"+sdate+"' and '"+edate+"' ";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10);
				if(pId == null){
					pId = "";
				}
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(11));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	
	public float getTotalRelease5(String var, int receive,String h_ID, String col) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+receive+" and month_and_year='"+var+"' and household_tbl."+col+" = '"+h_ID+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}
	
	public float getTotalRelease5(String sdate, String edate, String mun,
			String brgy) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl  "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = 1 and household_tbl.municipality = '"+mun+"' and household_tbl.barangay = '"+brgy+"' and date_receive between '"+sdate+"' and '"+edate+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}
	

	public ArrayList<reportBean> getList7(String sdate, String edate, String mun) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,h.barangay,h.municipality,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id,r.sub"+
						" FROM household_tbl as h,received_tbl as r "+
						" WHERE h.household_id = r.household_id and r.receive = 1 and h.municipality = '"+mun+"'  and date_receive between '"+sdate+"' and '"+edate+"' ";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10);
				if(pId == null){
					pId = "";
				}
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(11));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease6(String sdate, String edate, String mun) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl  "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = 1 and household_tbl.municipality = '"+mun+"'  and date_receive between '"+sdate+"' and '"+edate+"' ";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total release");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public ArrayList<reportBean> getAllList(int receive) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getAllList(String col, String search, int receive) {
	
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h."+col+" = '"+search+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getAllList(String mun, int receive) {
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality ='"+mun+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean> getAllList1(String mun, String brgy, int receive) {
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality ='"+mun+"' and h.barangay='"+brgy+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getAllList(int receive, String syear, String eyear) {
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and (r.date_receive like '%"+syear+"' or r.date_receive like '%"+eyear+"') ";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean> getTotalMembers() {
		
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select municipality, mun_name, count(*),barangay, brgy_name, count(*) "+
					"from household_tbl, brgy_tbl as b, municipal_tbl as m where barangay = brgy_id and municipality = m.mun_id and b.mun_id = m.mun_id "+
					"group by municipality,barangay";
		reportBean bean = null;
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				bean = new reportBean(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6));
				list.add(bean); 
			}
			
		}
		catch(Exception ex){
			
		}
		
		return list;
		
	}
	
	public ArrayList<reportBean2> getTotalMun() {
		
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		String sql = "select m.mun_id,m.mun_name,count(*) "+
					"from household_tbl as h,municipal_tbl as m where h.municipality = m.mun_id "+
					"group by municipality";
		reportBean2 bean = null;
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				bean = new reportBean2(rs.getInt(1),rs.getString(2),rs.getInt(3));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			
		}
		
		return list;
		
	}
	
	public int getMunCount() {
		
		int total = 0;
		String sql = "select count(*) from municipal_tbl ";
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				total = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			
		}
		
		return total;
		
	}

	public int getHouseholdCount() {
		
		int total = 0;
		String sql = "select count(*) from household_tbl ";
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				total = rs.getInt(1);
			}
			
		}
		catch(Exception ex){
			
		}
		
		return total;
		
	}

	public ArrayList<reportBean2> getMunBrgy() {
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		reportBean2 bean = null;
		String sql = "SELECT distinct m.mun_id,m.mun_name,count(brgy_id) "+
					"from municipal_tbl as m,brgy_tbl as b "+
					"where m.mun_id = b.mun_id "+
					"group by m.mun_id";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new reportBean2(rs.getInt(1),rs.getString(2),rs.getInt(3));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<String> getAllPhilhealthID() {
		
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select philhealth_id from household_tbl where philhealth_id is not null";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				list.add(rs.getString(1));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<String> getAllHouseholdID() {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select household_id from household_tbl where household_id is not null";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			
			while(rs.next()){
				list.add(rs.getString(1));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean> gethouseholdData(String mun_id) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select distinct municipality,barangay from household_tbl where municipality='"+mun_id+"'";
		reportBean bean = null;
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new reportBean(rs.getString(1),rs.getString(2));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public int gettry(String mun_id) {
		int ctr = 0;
		String sql = "select municipality,barangay,fingerprint from household_tbl as h, fingerprint_tbl as f where barangay='"+mun_id+"' and h.household_id = f.household_id";
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				ctr++;
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		System.out.println("barangay:::::"+mun_id+" ctr:"+ctr);
		return ctr;
	}

	public ArrayList<reportBean> getBrgyTBLData(String mun_id) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select brgy_id,brgy_name from brgy_tbl where mun_id = "+mun_id+"";
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

	public void updateBrgy(int brgy_id, String brgy_name, String mun_id) {
		try{
			String sql = "update household_tbl set barangay = '"+brgy_id+"' where barangay = '"+brgy_name+"' and municipality='"+mun_id+"'  ";
			con.createStatement().execute(sql);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}

	public ArrayList<String> getTransaction() {
		
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select distinct month_and_year from received_tbl";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString(1));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean> getDataByTransaction(String var, int receive) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id,r.sub"+
		" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
		" WHERE h.household_id = r.household_id and r.receive = "+receive+" and r.month_and_year='"+var+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;

		try{
			String pId = "";
			rs  = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10);
				if(pId == null){
					pId = "";
				}
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(11));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
				
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	
	public ArrayList<reportBean> getDataByTransaction(String var, int receive,String h_ID, String col) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
		" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
		" WHERE h.household_id = r.household_id and r.receive = "+receive+" and r.month_and_year='"+var+"' and h."+col+" = '"+h_ID+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;

		try{
			String pId = "";
			rs  = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println("in while"+rs.getString(10));
				list.add(bean);
			}
				
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public float getTotalRelease6(String mun, String string, int i,
			String transaction) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+i+" and household_tbl."+string+"="+Integer.parseInt(mun)+" and month_and_year = '"+transaction+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total releases");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}

	public String getbrgy_name(int brgy_id) {
		String brgy ="";
		
		try{
			
			rs = con.createStatement().executeQuery("select brgy_name from brgy_tbl where brgy_id = "+brgy_id);
			while(rs.next()){
				brgy = rs.getString(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return brgy;
	}
	
	public String getmun_name(int brgy_id) {
		String mun ="";
		
		try{
			
			rs = con.createStatement().executeQuery("select mun_name from municipal_tbl where mun_id = "+brgy_id);
			while(rs.next()){
				mun = rs.getString(1);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return mun;
	}

	/*public int checkIfEmp(String bname) {
		int emp = 0;
		String sql="select * from household_tbl where barangay like '%"+bname+"%' ";
		try{
			
		;	rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				emp = 1;
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return emp;
	}*/

	public ArrayList<reportBean> getAllList(int receive, String trans) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id  and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				/*if(pId == null){
					pId = "";
				}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println("in while");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public ArrayList<reportBean> getList1(int receive, String trans) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				/*pId = rs.getString(10);
				if(pId == null){*/
					pId = rs.getString(10)+rs.getString(11);
				/*}*/
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),NumberFormat.getNumberInstance(Locale.US).format(rs.getFloat(9)),pId,rs.getInt(12));
				//System.out.println(receive+"-------------------------------in while"+rs.getString(10));
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public float getTotalRelease6( int i, String transaction) {
		float total_release = 0;
		String sql = "SELECT sum(amount) "+
					"FROM received_tbl,household_tbl "+
					"WHERE received_tbl.household_id = household_tbl.household_id and  receive = "+i+" and received_tbl.month_and_year = '"+transaction+"'";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				total_release = rs.getFloat(1);
				System.out.println("in while total releases");
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return total_release;
	}
public ArrayList<reportBean> getAllList(String col, String search, int receive, String trans) {
		
		/*
		SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
		FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
		WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */
	
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
						" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
						" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h."+col+" = '"+search+"' and r.month_and_year = '"+trans+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id order by brgy_name asc,mun_name asc,h.head_name asc";
		reportBean bean = null;
		System.out.println(sql);
		try{
			String pId;
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				pId = rs.getString(10)+rs.getString(11);
				bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
				System.out.println("-----------------in while--------------");
				list.add(bean);
			}
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
		
		return list;
	}
public ArrayList<reportBean> getAllList1(String mun, String brgy, int receive, String trans) {
	
	/*
	SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
	FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
	WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */

	
	ArrayList<reportBean> list = new ArrayList<reportBean>();
	String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
					" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
					" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality ='"+mun+"' and h.barangay='"+brgy+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
	reportBean bean = null;
	
	try{
		String pId;
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			pId = rs.getString(10)+rs.getString(11);
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
			//System.out.println("in while");
			list.add(bean);
		}
		
	}
	catch(SQLException ex){
		System.out.println(ex);
	}
	
	return list;
}
public ArrayList<reportBean> getAllList(String mun, int receive, String trans) {
	
	/*
	SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.philhealth_id
	FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m
	WHERE h.household_id = r.household_id and r.receive = 1 and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and h.household_id like '%0%' */

	
	ArrayList<reportBean> list = new ArrayList<reportBean>();
	String sql = "SELECT h.household_id,h.head_name,h.birthday,brgy_name,mun_name,r.month_and_year,r.date_receive,r.time,r.amount,h.hh_set,h.set_group,r.sub"+
					" FROM household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m "+
					" WHERE h.household_id = r.household_id and r.receive = "+receive+" and h.municipality ='"+mun+"' and m.mun_id = municipality and brgy_id = barangay and m.mun_id = b.mun_id and r.month_and_year = '"+trans+"' order by brgy_name asc,mun_name asc,h.head_name asc";
	reportBean bean = null;
	
	try{
		String pId;
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			pId = rs.getString(10)+rs.getString(11);
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),Float.toString(rs.getFloat(9)),pId,rs.getInt(12));
			//System.out.println("in while");
			list.add(bean);
		}
		
	}
	catch(SQLException ex){
		System.out.println(ex);
	}
	
	return list;
}

public ArrayList<fingerprintBean> getFingerprintDatas(String mun) {

	ArrayList<fingerprintBean> flist = new ArrayList<fingerprintBean>();
	fingerprintBean bean = null;
	try{
		

		rs=con.createStatement().executeQuery("SELECT distinct f.household_id, f.fingerprint,f.date_recorded,f.time_recorded FROM `fingerprint_tbl` as f , household_tbl as h" +
				"  where h.household_id = f.household_id and h.municipality ="+mun+" order by head_name ");
		
		while (rs.next()) {
			
			bean = new fingerprintBean(rs.getString(1),rs.getBytes(2), rs.getString(3),rs.getString(4));
			flist.add(bean);
		}

		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return flist;
	
	
}

public void insertFingerpritns(boolean flag,String houseold_id, byte[] fingerprint,
		String date_recorded, String time_recorded) throws SQLException {

	try {
		stmt = con
				.prepareStatement("INSERT INTO isolated_fingerprint_db (household_id,fingerprint,date_recorded,time_recorded) VALUES (?,?,?,?)");

		stmt.setString(1, houseold_id);
		stmt.setBytes(2, fingerprint);
		stmt.setString(3, date_recorded);
		stmt.setString(4, time_recorded);
		
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
//----------- Sayao new function -------------//


public ArrayList<reportBean> getSysOReport() {
	ArrayList<reportBean> slist = new ArrayList<reportBean>();
	String sql = "select household_id,remarks from system_onhold_tbl";
	reportBean bean = null;
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2));
			slist.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return slist;
}

public ArrayList<reportBean> getRegistered(String sdate, int team_id) {
	
	ArrayList<reportBean> l = new ArrayList<reportBean>();
	String sql = "select mun_name,brgy_name,h.household_id,head_name,hh_set,set_group from fingerprint_tbl_temp as f,household_tbl as h,municipal_tbl as m,brgy_tbl as b where h.household_id = f.household_id and municipality = m.mun_id and m.mun_id = b.mun_id and f.mun_id = f.mun_id and brgy_id = barangay and server_id = "+team_id+" and date_recorded = '"+sdate+"' order by mun_name,brgy_name,head_name";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;

}

public ArrayList<reportBean> getGRS(String sdate, int team_id) {	ArrayList<reportBean> l = new ArrayList<reportBean>();
	
	String sql = "select captured_citymunicipality,captured_barangay,date_recorded,team_id,household_id, captured_fullname , captured_grscasetype,captured_grsidoctype, remarks , grievance_officer  from grscases2_tbl_temp where date_recorded = '"+sdate+"' and team_id = "+team_id+" order by captured_citymunicipality,captured_barangay,captured_fullname";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}

	return l;
}

public String getMunServed(String sdate, int i) {
	String sql = "SELECT mun_name FROM `fingerprint_tbl_temp` as f,municipal_tbl as m where  date_recorded ='"+sdate+"' and f.mun_id = m.mun_id group by m.mun_id order by count(household_id) desc limit 1";
	String mun_name = "";

	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			mun_name = rs.getString(1);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	

	return mun_name;
}

public ArrayList<String> getTeamsReg(String tbl) {
	ArrayList<String> list = new  ArrayList<String>();
	String sql = "select distinct server_id from "+tbl+" ";
	
	try{
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			list.add(rs.getString("server_id"));
		}
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	return list;
}

public ArrayList<reportBean> getRegistered(int team_num,String sdate, String tbl) {
	
	ArrayList<reportBean> l = new ArrayList<reportBean>();
	String sql = "select mun_name,brgy_name,h.household_id,head_name,team_id,server_id,hh_set,set_group from "+tbl+" as f,household_tbl as h,municipal_tbl as m,brgy_tbl as b where h.household_id = f.household_id and municipality = m.mun_id and m.mun_id = b.mun_id and f.mun_id = f.mun_id and brgy_id = barangay  and date_recorded = '"+sdate+"' and server_id = "+team_num+" order by mun_name,brgy_name,head_name,team_id,server_id";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7),rs.getString(8));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;

}


public ArrayList<reportBean> getGRS(String tbl,String sdate, int team_id) {	ArrayList<reportBean> l = new ArrayList<reportBean>();
	
	String sql = "select captured_citymunicipality,captured_barangay,date_recorded,server_id,household_id, captured_fullname , captured_grscasetype,captured_grsidoctype, remarks , grievance_officer  from "+tbl+" where date_recorded = '"+sdate+"' and server_id = "+team_id+" order by captured_citymunicipality,captured_barangay,captured_fullname";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}

	return l;
}


public ArrayList<reportBean> getRegistered(int mun_id, String tbl) {
	
	ArrayList<reportBean> l = new ArrayList<reportBean>();
	String sql = "select mun_name,brgy_name,h.household_id,head_name,team_id,server_id,hh_set,set_group from "+tbl+" as f,household_tbl as h,municipal_tbl as m,brgy_tbl as b where h.household_id = f.household_id and municipality = m.mun_id and m.mun_id = b.mun_id and f.mun_id = f.mun_id and brgy_id = barangay  and m.mun_id = "+mun_id+" order by mun_name,brgy_name,head_name,team_id,server_id";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7),rs.getString(8));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;

}


public ArrayList<reportBean> getTotalPerMunicipality(String tbl) {
	ArrayList<reportBean> l = new ArrayList<reportBean>();
	String sql = "select mun_name,count(*) from "+tbl+" as f, municipal_tbl as m where m.mun_id = f.mun_id group by m.mun_id";
	reportBean bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean(rs.getString(1),rs.getInt(2));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;
}


public ArrayList<reportBean2> getTotalPerMunAndBrgy(String tbl) {
	ArrayList<reportBean2> l = new ArrayList<reportBean2>();
	String sql = "select mun_name,brgy_name,count(*) from "+tbl+" as f,household_tbl as h,municipal_tbl as m,brgy_tbl as b where municipality = m.mun_id and b.mun_id = m.mun_id and barangay = brgy_id and f.household_id = h.household_id and f.mun_id = m.mun_id group by m.mun_id,brgy_id ";
	reportBean2 bean = null;
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			bean = new reportBean2(rs.getString(1),rs.getString(2),rs.getInt(3));
			//System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			l.add(bean);
		}
		System.out.println(l.size());
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l;
}

public ArrayList<reportBean> getGSRGrouped(int mun_id) {
	
	ArrayList<reportBean> list = new ArrayList<reportBean>();
	reportBean b = null;
	String sql= "SELECT municipality,`captured_grscasetype`,count(*)" +
			" FROM `grscases2_tbl_temp` as g,household_tbl as h " +
			"WHERE h.household_id = g.household_id and " +
			"municipality= "+mun_id+ 
			"group by municipality,`captured_grscasetype`";
	
	try{
		
		rs = con.createStatement().executeQuery(sql);
		while(rs.next()){
			b = new reportBean(rs.getInt(1),rs.getString(2),rs.getInt(3));
			list.add(b);
		}
		
	}catch(Exception ex){
		System.out.println(ex);
	}
	
	return list;
}



}


