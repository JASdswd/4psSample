package grsCases;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BaseDAO;

/**
 * Servlet implementation class AddGrsCase
 */
@WebServlet("/AddGrsCase")
public class AddGrsCase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGrsCase() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null TransactionView servlet doPost");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				//System.out.println("AddGrsCase doPost");
				String household_id = request.getParameter("hh_id");
				String grsCase = request.getParameter("grsCase");
				String syscode = request.getParameter("syscode");
				String fullName = request.getParameter("fullName");
				String municipal = request.getParameter("municipal");
				String barangay = request.getParameter("barangay");
				String idocp = request.getParameter("idocp");
				String remarks = request.getParameter("remarks");
				
				PrintWriter out= response.getWriter();
				try {
					BaseDAO dao = new BaseDAO();
					/*================ Geting date from the server ===================*/
	    			String dateAndTime = dao.getDateAndTime();
	    			String regex[] = dateAndTime.split(" ");
	    			String curDate = regex[0];
	    			String regex1[] = regex[1].split("\\."); // naa cjay duha ka slash kung mag split ka with only a dot.
	    			String curTime = regex1[0];
	    			
	    			/*String regex3[] = curDate.split("-");
	    			String curYear = regex3[0];
	    			String curMonth = regex3[1];
	    			String curDay = regex3[2];
	    			System.out.println("curTime:"+curTime);
	    			System.out.println("curDate:"+curDate);
	    			String convertedDate = curMonth+"/"+curDay+"/"+curYear;*/
	    			/*================================================================*/
	    			String user_id = (String)session.getAttribute("user_id");
	    			int team_id = dao.getTeamId();
	    			int server_id = dao.getServerId();
	    			System.out.println("user:id:"+user_id);
	    			System.out.println(team_id);
	    			System.out.println(server_id);
	    			int found = dao.testIfExist(false, "select household_id from household_tbl where household_id = '"+household_id+"' ");
	    			int found2 = dao.testIfExist(false, "select household_id from grscases2_tbl_temp where household_id = '"+household_id+"' ");
	    			if(found == 1){
		    			out.print(2);
						out.flush();
						out.close();
	    			}
	    			else{
	    				dao.addGrsCase(false, household_id, grsCase, syscode, fullName, municipal, barangay, idocp, remarks, curDate, curTime, server_id, team_id, user_id);
		    			out.print(1);
						out.flush();
						out.close();
	    			}
	    			
				} catch (Exception e) {
					e.printStackTrace();
					out.print(0);
					out.flush();
					out.close();
				}
				
			}
			
		}
	}// end sa doPost.
	

}
