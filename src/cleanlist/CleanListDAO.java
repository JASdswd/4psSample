package cleanlist;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.reportBean;
import bean.transactionBean;

import myconnection.ConnectionDAO;

public class CleanListDAO extends ConnectionDAO{

	public CleanListDAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}


	public ArrayList<String> getAllGRSCases(String tbl,String mun) {
		
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select distinct h.household_id from "+tbl+" as g, household_tbl as h,municipal_tbl as m where municipality = mun_id and g.household_id = h.household_id and mun_id = "+mun;
		
		try{
		
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString("household_id"));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
	public ArrayList<String> getAllSO(String tbl, String mun) {
		
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select distinct h.household_id from "+tbl+" as s,household_tbl as h,municipal_tbl as m where h.household_id = s.household_id and municipality = mun_id and mun_id = "+mun;
		
		try{
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString("household_id"));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return list;
	}

	public ArrayList<reportBean> getAllReceivedList(String mun_id) {
		ArrayList<reportBean> list = new ArrayList<reportBean>();
		String sql = "select distinct h.household_id,head_name,brgy_name,mun_name,hh_set,set_group from household_tbl as h,received_tbl as r,brgy_tbl as b,municipal_tbl as m where m.mun_id = municipality and m.mun_id = b.mun_id and h.household_id = r.household_id and barangay = brgy_id and m.mun_id = "+mun_id+" order by brgy_name,mun_name,head_name";
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


	public ArrayList<String> getAllCOACases(String tbl, String mun) {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select distinct h.household_id from "+tbl+" as g, household_tbl as h,municipal_tbl as m where municipality = mun_id and g.household_id = h.household_id and mun_id = "+mun;
		
		try{
		
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString("household_id"));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}

}
