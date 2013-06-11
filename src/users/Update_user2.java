package users;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login_DAO.Login_DAO;

/**
 * Servlet implementation class Update_user2
 */
@WebServlet("/Update_user2")
public class Update_user2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update_user2() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destination = "";
		HttpSession session = request.getSession(false);
		if(session==null){
			destination = "/index.jsp";
		}
		else{
			if(session.getAttribute("username")==null){
				destination = "/index.jsp";
			}
			else{
				destination = "/View_financial";
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher(destination);
				rd.forward(request, response);	
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		String destination = "";
		Calendar calendar= Calendar.getInstance();
		DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
		SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
		
		String date = format.format(calendar.getTime());
		//System.out.println("date:"+day);
		String time = timeInstance.format(Calendar.getInstance().getTime());
		
		if(session==null){
			destination = "/index.jsp";
		}
		else{
			if(session.getAttribute("username")==null){
				destination = "/index.jsp";
			}
			else{	
				String fname = request.getParameter("f_fname");
				String lname = request.getParameter("f_lname");
				String gender = request.getParameter("f_gender");
				String email = request.getParameter("f_email");
				String contact = request.getParameter("f_contact");
				int user_role = Integer.parseInt(request.getParameter("u_user_role"));
				
				try {	
					UserDAO dao = new UserDAO();
					Login_DAO dao2 = new Login_DAO();
					String rfname = dao.getUser2Fname(user_role);
					String rlname = dao.getUser2Lname(user_role);
					dao.Update(fname, lname,gender,contact,email,user_role);
					/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink[jm])*/
					String logs_fname = dao.getUser2Fname(1);
					String logs_lname = dao.getUser2Lname(1);
					if(user_role == 1){
						dao.add_logs(false, date, time, "Provincial link user change its name from "+rfname+" "+rlname+" to "+fname+" "+lname+" by "+logs_fname+" "+logs_lname);
						
					}else if(user_role == 3){
						dao.add_logs(false, date, time, "Financial Analyst user change its name from "+rfname+" "+rlname+" to "+fname+" "+lname+" by "+logs_fname+" "+logs_lname);
						
					}
					else if(user_role == 10){
						dao.add_logs(false, date, time, "Administrator user change its name from "+rfname+" "+rlname+" to "+fname+" "+lname+" by "+logs_fname+" "+logs_lname);
						
					}
					String dfname = dao2.getFname2(user_role);
					int Login_UserRole = (Integer) session.getAttribute("Luser_role");
					if(Login_UserRole==user_role){
						session.setAttribute("dfname", dfname);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				destination = "/ViewAllUser2?id="+user_role;
				//session.setAttribute("id", user_role);
			}
			
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher(destination);
			rd.forward(request, response);
		}
	}

}
