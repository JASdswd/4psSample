package reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;


import users.UserDAO;

import bean.transactionBean;

/**
 * Servlet implementation class VerifiersDailyReports
 */
@WebServlet("/VerifiersDailyReports")
public class VerifiersDailyReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifiersDailyReports() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);		
		if(session==null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
				ArrayList<transactionBean> getData = new ArrayList<transactionBean>();
				ArrayList<transactionBean> getNameVerifier = new ArrayList<transactionBean>();
				try {
					UserDAO dao = new UserDAO();
					bean = dao.getmunicipal(false);
					String sql = "select fname,lname,date_recorded,mun_name,count(*) from fingerprint_tbl_temp as f,municipal_tbl as m,user_tbl as u where u.user_id = 5 and f.mun_id = m.mun_id and f.user_id = u.id group by u.id,f.mun_id";
					getData = dao.getVRR(1,sql);
					getNameVerifier = dao.getNameofVerifier();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("municipal_list", bean);
				request.setAttribute("Vlist", getData);
				request.setAttribute("nameV", getNameVerifier);
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/reports/verifierDailyReports.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);		
		if(session==null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				try {
					UserDAO dao = new UserDAO();
					PrintWriter out = response.getWriter();
					JSONObject obj = new JSONObject();
					ArrayList<transactionBean> getVerifiersData = new ArrayList<transactionBean>();
					
					
					
					String transaction = request.getParameter("transaction");
					
					
					
					if(transaction.equals("Vname")){
						String id = request.getParameter("VID");
						String sql = "select fname,lname,date_recorded,mun_name,count(*) from fingerprint_tbl_temp as f,municipal_tbl as m,user_tbl as u where f.mun_id = m.mun_id and f.user_id = u.id and u.id = "+id+" group by u.id,f.mun_id";
						getVerifiersData = dao.getVRR(0, sql);
						if(getVerifiersData.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", getVerifiersData);
						}
					}
					else if(transaction.equals("date")){
						String date = request.getParameter("date");
						String sql = "select fname,lname,date_recorded,mun_name,count(*) from fingerprint_tbl_temp as f,municipal_tbl as m,user_tbl as u where f.mun_id = m.mun_id and f.user_id = u.id and f.date_recorded = '"+date+"' group by u.id,f.mun_id";
						getVerifiersData = dao.getVRR(0, sql);
						if(getVerifiersData.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", getVerifiersData);
						}
					}
					else if(transaction.equals("municipal")){
						int mun_id = Integer.parseInt(request.getParameter("mun_id"));
						String sql = "select fname,lname,date_recorded,mun_name,count(*) from fingerprint_tbl_temp as f,municipal_tbl as m,user_tbl as u where f.mun_id = m.mun_id and f.user_id = u.id and m.mun_id = "+mun_id+" group by u.id,f.mun_id order by f.date_recorded desc";
						getVerifiersData = dao.getVRR(0, sql);
						if(getVerifiersData.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", getVerifiersData);
						}
					}
					out.print(obj);
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
	}

}
