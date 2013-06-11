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
import DAO.GrsCasesDAO;

/**
 * Servlet implementation class AddGrsCases
 */
@WebServlet("/AddGrsCases")
public class AddGrsCases extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGrsCases() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hello");
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
				System.out.println("username is null Password_Confirmation servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				PrintWriter out= response.getWriter();
				int server_id = Integer.parseInt((String)request.getParameter("grsServer"));
				String household_id = request.getParameter("household_id");
				System.out.println("household_id = "+household_id);
				int user_id = Integer.parseInt((String)session.getAttribute("user_id"));
				String remarks = request.getParameter("grsRemarks");
				String grscase = request.getParameter("grsCase");
				String idoc = request.getParameter("grsCasesCombo");
				
				System.out.println("user_id:"+user_id);
				System.out.println("remarks:"+remarks);
				System.out.println("grscase:"+grscase);
				System.out.println("idoc:"+idoc);
				
				
				try {
					BaseDAO bDAO = new BaseDAO();
					/*================ Geting date from the server ===================*/
	    			String dateAndTime = bDAO.getDateAndTime();
	    			String regex[] = dateAndTime.split(" ");
	    			String curDate = regex[0];
	    			String regex1[] = regex[1].split("\\."); // naa cjay duha ka slash kung mag split ka with only a dot.
	    			String curTime = regex1[0];
	    			
	    			String regex3[] = curDate.split("-");
	    			String curYear = regex3[0];
	    			String curMonth = regex3[1];
	    			String curDay = regex3[2];
	    			String convertedDate = curMonth+"/"+curDay+"/"+curYear;
	    			
	    			
	    			/*================================================================*/
					GrsCasesDAO dao = new GrsCasesDAO();
					int grs = dao.testIfExist(false, "select * from grscases_tbl where household_id = '"+household_id+"'");
					if(grs>0){
						dao.updateGrsCases(false, convertedDate, server_id, household_id, user_id,grscase,idoc, remarks);
					}
					else{
						dao.addGrsCases(false, convertedDate, server_id, household_id, user_id,grscase,idoc, remarks);
					}
					
					//session.setAttribute("server", server_id);
					out.print(1);
					out.flush();
					out.close();
				} catch (Exception e) {
					out.print(0);
					out.flush();
					out.close();
				}
				/*try {
					Login_DAO dao = new Login_DAO();
					String username = (String)session.getAttribute("username");
					boolean check_password = dao.check_passwordbookKeeper(username, request.getParameter("confirmation_password"),4);
					if(check_password){
						//obj.put("trys", true);
						Date date = new Date();
					    SimpleDateFormat simpDate;

					    simpDate = new SimpleDateFormat("kk:mm:ss");
					    System.out.println("24 hour format:"+simpDate.format(date));
						//if(start_transaction == null || start_transaction.equals(null)){
							System.out.println("starting of transaction");
							//objectall.put("transaction_time", simpDate.format(date));
							session.setAttribute("transactionTime", simpDate.format(date));
						//}
						else{
							System.out.println("nag'start na an transaction");
							objectall.put("has_transaction_time", true);
							objectall.put("transaction_time", session.getAttribute("transactionTime"));
						}
							JSONObject obj=new JSONObject();
							try {
								obj.put("pass_con", 1);
								obj.put("transaction_time",simpDate.format(date));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							out.print(obj);
							out.flush();
							out.close();
					}
					else{
						JSONObject obj=new JSONObject();
						try {
							obj.put("pass_con", 0);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						//obj.put("trys", false);
						out.print(0);
						out.flush();
						out.close();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}*/
				
			}
		}
		//doPost(request, response);
	
	}

}
