package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.json.JSONException;
import org.json.JSONObject;

import DAO.BaseDAO;
import beans.Login_beans;

/**
 * Servlet implementation class GetPassUname
 */
@WebServlet("/GetPassUname")
public class GetPassUname extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPassUname() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			String uname = request.getParameter("username");
			String pword = request.getParameter("password");
			Calendar calendar= Calendar.getInstance();
			DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
			SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
			String date = format.format(calendar.getTime());
			String time = timeInstance.format(Calendar.getInstance().getTime());
			int upSuccess = 0;
			BaseDAO d = new BaseDAO();
			UserDAO dao = new UserDAO();
			PrintWriter out = response.getWriter();
			int user_role = Integer.parseInt(request.getParameter("user_role"));
			try{
				
				if(pword.length() >= 2 && pword.length() <= 18){
					if(user_role == 1 || user_role == 3){
						String fname = dao.getUser2Fname(user_role);//for logs
						String lname = dao.getUser2Lname(user_role);//for logs
						upSuccess=dao.Manage_Acc2(false,uname,pword,user_role);
					}
				}
				
				if(upSuccess == 1){
					
					if(user_role == 1){
						d.add_logs(false, date, time, "Provincial Link account updated.");
					}
					else if(user_role == 2){
						d.add_logs(false, date, time, "Provincial Link updated the Financial Analyst account.");
					}
					
					out.print(1);
					out.close();
					out.flush();
				}
				else{
					out.print(0);
					out.close();
					out.flush();
				}
				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("doPost GetPassUname");
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
				JSONObject obj=new JSONObject();
				String pword = request.getParameter("confirmation_password");
				try {
					UserDAO dao = new UserDAO();
					Login_DAO dao1 = new Login_DAO();
					String hashpword = dao1.byteArrayToHexString(dao1.computeHash(pword));
					boolean check_password = dao.check_password2("JAS", hashpword,10);
					System.out.println("check_password:"+check_password);
					int user_role = Integer.parseInt(request.getParameter("user_role"));
					System.out.println("user_role:"+user_role);
					ArrayList<Login_beans> acclist = new ArrayList<Login_beans>(); 
					if(check_password){
						
						if(user_role == 1 || user_role == 3|| user_role == 10){ //for provincial and financial account [jm]
							acclist = dao.getAccInfo2(user_role);
						}
						else if(user_role == 2 || user_role == 4 || user_role == 6 || user_role == 7 || user_role == 5){ //for municipal,social,bookkeeper and grievance account [jm]
							String id = request.getParameter("id");
							int mun_id = Integer.parseInt(request.getParameter("mun_id"));
							acclist = dao.getAccInfo(id,mun_id,user_role);
						}
						
						try {
							obj.put("data", acclist);
							obj.put("c", 1);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.print(obj);
						out.flush();
						out.close();
					}
					else{
						try {
							obj.put("c", 0);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.print(obj);
						out.flush();
						out.close();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}