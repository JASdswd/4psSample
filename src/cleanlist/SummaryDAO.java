package cleanlist;

import java.sql.SQLException;
import java.util.ArrayList;

import myconnection.ConnectionDAO;

import bean.reportBean;
import bean.reportBean2;

public class SummaryDAO extends ConnectionDAO{
	
public SummaryDAO() throws SQLException {
		super();
	}

public ArrayList<reportBean2> getAllGRSCases(String tbl,String mun) {
		
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		String sql = "select distinct h.household_id, captured_grscasetype  from "+tbl+" as g, household_tbl as h,municipal_tbl as m where municipality = mun_id and g.household_id = h.household_id and mun_id = "+mun;
		reportBean2 b = null;
		try{
		
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				b = new reportBean2(rs.getString("household_id"),rs.getString("captured_grscasetype"));
				list.add(b);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

	public ArrayList<reportBean2> getAllSO(String tbl, String mun) {
		
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		String sql = "select distinct h.household_id,remarks from "+tbl+" as s,household_tbl as h,municipal_tbl as m where h.household_id = s.household_id and municipality = mun_id and mun_id = "+mun;
		reportBean2 b = null;
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				b = new reportBean2(rs.getString("household_id"),rs.getString("remarks"));
				System.out.println(rs.getString("remarks"));
				list.add(b);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return list;
	}

	public ArrayList<reportBean> getAllReceivedList(String mun_id) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select distinct h.household_id,head_name,brgy_name,mun_name,hh_set,set_group from household_tbl as h,brgy_tbl as b,municipal_tbl as m where m.mun_id = municipality and m.mun_id = b.mun_id and barangay = brgy_id and m.mun_id = "+mun_id+" order by brgy_name,mun_name,head_name";
		reportBean bean = null;
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new reportBean(rs.getString("household_id"),rs.getString("head_name"),rs.getString("brgy_name"),rs.getString("mun_name"),rs.getString("hh_set"),rs.getString("set_group"));
				list.add(bean);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return list;
	}

	public ArrayList<reportBean2> getRegistered(String mun_id,String tbl) {
		
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		String sql = "select distinct household_id,date_recorded from "+tbl+" where mun_id = "+mun_id;
		reportBean2 b = null;
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				b = new reportBean2(rs.getString("household_id"),rs.getString("date_recorded"));
				list.add(b);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
		
	}

	public ArrayList<reportBean2> getAllCOACases(String tbl, String mun) {
		
		ArrayList<reportBean2> list = new ArrayList<reportBean2>();
		String sql = "select distinct h.household_id, code  from "+tbl+" as g, household_tbl as h,municipal_tbl as m where municipality = mun_id and g.household_id = h.household_id and mun_id = "+mun;
		reportBean2 b = null;
		try{
		
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				b = new reportBean2(rs.getString("household_id"),rs.getString("code"));
				list.add(b);
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
}
