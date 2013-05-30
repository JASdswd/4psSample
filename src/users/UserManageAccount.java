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
 * Servlet implementation class UserManageAccount
 */
@WebServlet("/UserManageAccount")
public class UserManageAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManageAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			String uname = request.getParameter("username");
			String pword = request.getParameter("password");
			
			Login_DAO dao1 = new Login_DAO();
			String hashpword = dao1.byteArrayToHexString(dao1.computeHash(pword));
			
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
				
				if(user_role == 1 || user_role == 3){
					if(pword.length() >= 2 && pword.length() <= 18){
						upSuccess=dao.Manage_Acc2(false,uname,hashpword,user_role);
					}
					if(upSuccess == 1){
						String rfname = dao.getUser2Fname(user_role);
						String rlname = dao.getUser2Lname(user_role);
						/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
						String logs_fname = dao.getUser2Fname(1);
						String logs_lname = dao.getUser2Lname(1);
						if(user_role==1){
							dao.add_logs(false, date, time, logs_fname+" "+logs_lname+" updated the username and password of Provincial Link account named "+rlname.toUpperCase()+", "+rfname.toUpperCase()+".");
						}else if(user_role==3){
							dao.add_logs(false, date, time, logs_fname+" "+logs_lname+" updated the username and password of Financial Analyst account named "+rlname.toUpperCase()+", "+rfname.toUpperCase()+".");
						}
					}
				}
				else if(user_role==2 || user_role==4 || user_role==6 || user_role==7 || user_role == 5){
					String id = request.getParameter("id");
					String fname = request.getParameter("fname");
					String lname = request.getParameter("lname");
					int mun_id = Integer.parseInt(request.getParameter("mun_id"));
					String mun_name = d.getmunName(mun_id);
					if(pword.length() >= 2 && pword.length() <= 18){
						upSuccess=dao.Manage_Acc(false,uname,hashpword,id,user_role,mun_id);
					}
					if(upSuccess == 1){
						String rfname = dao.getUser2Fname(user_role);
						String rlname = dao.getUser2Lname(user_role);
						/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
						String logs_fname = dao.getUser2Fname(1);
						String logs_lname = dao.getUser2Lname(1);
						if(user_role==2){
							dao.add_logs(false, date, time,logs_fname+" "+logs_lname+" updated the username and password of Municipal Link account named "+lname.toUpperCase()+", "+fname.toUpperCase()+" from "+mun_name+".");
						}
						else if(user_role==4){
							dao.add_logs(false, date, time,logs_fname+" "+logs_lname+" updated the username and password of BookKeeper account named "+lname.toUpperCase()+", "+fname.toUpperCase()+" from "+mun_name+".");
						}
						else if(user_role==5){
							dao.add_logs(false, date, time,logs_fname+" "+logs_lname+" updated the username and password of Verifier account named "+lname.toUpperCase()+", "+fname.toUpperCase()+".");
						}
						else if(user_role==6){
							dao.add_logs(false, date, time,logs_fname+" "+logs_lname+" updated the username and password of Grievance Officer account named "+lname.toUpperCase()+", "+fname.toUpperCase()+" from "+mun_name+".");
						}
						else if(user_role==7){
							dao.add_logs(false, date, time,logs_fname+" "+logs_lname+" updated the username and password of Social Worker account named "+lname.toUpperCase()+", "+fname.toUpperCase()+" from "+mun_name+".");
						}						
					}
				}
				if(upSuccess == 1){
					
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
		System.out.println("UserManageAccount do post----------------------------------"+request.getParameter("confirmation_password"));
		HttpSession session = request.getSession(false);
		System.out.println("pass servlet");
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
					Login_DAO dao = new Login_DAO();
					boolean check_password = dao.check_password2(pword,3);
					ArrayList<Login_beans> acclist = new ArrayList<Login_beans>(); 
					if(check_password){
						//obj.put("trys", true);
						acclist = dao.getAccInfo(pword,3);
						System.out.println("arg");
						try {
							obj.put("data", acclist);
							obj.put("c", 1);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
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
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						out.print(obj);
						out.flush();
						out.close();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

}
