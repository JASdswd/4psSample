	package transaction;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import transaction_DAO.Transaction_DAO;



import DAO.BaseDAO;
import bean.reportBean;
import bean.transactionBean;

/**
 * Servlet implementation class View_transactions
 */
@WebServlet("/View_transactions")
public class View_transactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_transactions() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
<<<<<<< HEAD
	
=======
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
		if(session==null){
			System.out.println("session is null add municipality servelet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_transactions servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<transactionBean> household = new ArrayList<transactionBean>();
				ArrayList<transactionBean> wife = new ArrayList<transactionBean>();
				ArrayList<transactionBean> children = new ArrayList<transactionBean>();
				ArrayList<transactionBean> grandchild = new ArrayList<transactionBean>();
				ArrayList<transactionBean> municipal = new ArrayList<transactionBean>();
				ArrayList<transactionBean> servers = new ArrayList<transactionBean>();
				ArrayList<reportBean> barangay = new ArrayList<reportBean>();
				ArrayList<transactionBean> other_relatives = new ArrayList<transactionBean>();
				
				try{
					String household_id = (String)session.getAttribute("household_id");
					BaseDAO dao = new BaseDAO();
					ArrayList<transactionBean> hh_setgroup = new ArrayList<transactionBean>();
					String f_position = dao.getfPosition(household_id);	
					household = dao.gethousehold_info(false, household_id);			// Retrieve the household info from database
					int mun = dao.getMunId2(household_id);
					String brgy = dao.getbrgyID2(household_id);
					municipal = dao.getmunicipal(false);
					barangay = dao.getbrgy(mun);
					hh_setgroup = dao.getHH_SetGroup(false,household_id);
<<<<<<< HEAD
					
=======
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
					if(household.isEmpty()){
						ServletContext sc = this.getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/redirectPage/redirect.jsp");
						rd.forward(request, response);
					}
					else{
					
						int wife_ctr = dao.testIfExist(false, "select * from wife_tbl where household_id = '"+household_id+"' ");
						
						if(wife_ctr==0){
							request.setAttribute("ws_null", true);
						}
						else{
							wife = dao.getwife_info(false, household_id);				// Retrieve the wife info from database
							request.setAttribute("wife", wife);	
						}
						
						
						children = dao.getchildren_info(false, household_id);
						other_relatives = dao.getother_rel_info(false, household_id);
						
						grandchild = dao.getgrandchild_info(false, household_id);
<<<<<<< HEAD
						
						int ctr = dao.testIfExist(false, "select * from photo_tbl_temp2 where household_id = '"+household_id+"'");
=======
						System.out.println("before cted");
						int ctr = dao.testIfExist(false, "select household_id from photo_tbl_temp2 where household_id = '"+household_id+"'");
						System.out.println("cterer");
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
						if(ctr>0){
							request.setAttribute("photohead_exist", true);
						}
						else{
							request.setAttribute("photohead_exist", false);
						}
						if((Boolean)session.getAttribute("provOrMun")){
							request.setAttribute("user_Prov", true);
						}
						else{
							request.setAttribute("user_Prov", false);
						}
<<<<<<< HEAD
						int fingerprint_ctr = dao.testIfExist(false, "select * from fingerprint_tbl_temp where household_id = '"+household_id+"'");
=======
						int fingerprint_ctr = dao.testIfExist(false, "select household_id from fingerprint_tbl_temp where household_id = '"+household_id+"'");
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
						if(fingerprint_ctr>0){
							request.setAttribute("fingerprint_exist", true);
						}
						else{
							request.setAttribute("fingerprint_exist", false);
						}
						if(household.get(0).getPhilhealth_id() == null || household.get(0).getPhilhealth_id().equals(null) || household.get(0).getPhilhealth_id() == "" || household.get(0).getPhilhealth_id().equals(null)){
							System.out.println("philhealth id is null. -> View_transactions.java");
							request.setAttribute("ph_null", true);
						}
						if(household.get(0).getH_gender().equalsIgnoreCase("m")){
							request.setAttribute("h_gender", true);
						}
						
						Transaction_DAO dao1 = new Transaction_DAO();
						servers = dao1.getServers();
						request.setAttribute("serv", session.getAttribute("server"));
<<<<<<< HEAD
						
=======
						System.out.println("pompom puyangaw bukhad ilawm");
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
						request.setAttribute("other_relatives", other_relatives);
						request.setAttribute("hh_setgroup", hh_setgroup);
						request.setAttribute("mun", mun);
						request.setAttribute("f_position",f_position);
						request.setAttribute("brgy", brgy);
						request.setAttribute("hh_id", household_id);
						request.setAttribute("household", household);
						request.setAttribute("children", children);
						request.setAttribute("grandchild", grandchild);
						request.setAttribute("munList", municipal);
						request.setAttribute("brgyList", barangay);
						request.setAttribute("servers", servers);
						
						ServletContext sc = this.getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/transactions/view_transactionsProfile.jsp");
						rd.forward(request, response);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
