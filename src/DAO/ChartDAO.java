package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.ChartBean;

import myconnection.ConnectionDAO;

public class ChartDAO extends ConnectionDAO{

	public ChartDAO() throws SQLException {
		super();

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ChartBean> getChartReport(boolean flag) throws SQLException{
		ChartBean bean = null;
		list = new ArrayList<ChartBean>();
		try {
			rs = con.createStatement().executeQuery("SELECT m.mun_name, count(*),(select count(*) " +
					"from household_tbl as h where m.mun_id = municipality) - count(*) as difference" +
					" from municipal_tbl as m, fingerprint_tbl_temp as f " +
					"where f.mun_id = m.mun_id group by f.mun_id;");

			while (rs.next()) {
				bean = new ChartBean(rs.getString(1), rs.getInt(2), rs.getInt(3));
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
}
