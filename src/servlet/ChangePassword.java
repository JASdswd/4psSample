package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.json.JSONObject;

import DAO.BaseDAO;



/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null ChangeView servlet"+session.getAttribute("username"));
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				//System.out.println("username is null ChangeView servlet"+session.getAttribute("username"));
				
				String plpword = null;
				String mlpword = null;
				String fapword = null;
				String bkpword = null;
				String user = (String) session.getAttribute("username");
				
				try{
					BaseDAO dao = new BaseDAO();
					plpword = dao.getPassword("Provincial link");
					mlpword = dao.getPassword("Municipal link");
					fapword = dao.getPassword("Financial Analyst");
					bkpword = dao.getPassword("Book keeper");
				}
				catch(Exception ex){
					System.out.println(ex);
				}
				
				request.setAttribute("user", user);
				request.setAttribute("plpword", plpword);
				request.setAttribute("mlpword", mlpword);
				request.setAttribute("fapword", fapword);
				request.setAttribute("bkpword", bkpword);
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/system_maintenance/sm_changepassword.jsp");
				rd.forward(request, response);
				
				
			}
		}
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
				System.out.println("username is null changePassword servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				String user = request.getParameter("user");
				String npword = request.getParameter("npword");
				int edited = 0;
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				String plpword = null;
				String mlpword = null;
				String fapword = null;
				String bkpword = null;
				Calendar calendar= Calendar.getInstance();
				DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
				SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
				
				String date = format.format(calendar.getTime());
				//System.out.println("date:"+day);
				String time = timeInstance.format(Calendar.getInstance().getTime());
				try{
					
					BaseDAO dao = new BaseDAO();
					System.out.println("Username:"+user);
					System.out.println("Password:"+npword);

					edited = dao.UpdatePassword(user,npword);
					plpword = dao.getPassword("Provincial link");
					mlpword = dao.getPassword("Municipal link");
					fapword = dao.getPassword("Financial Analyst");
					bkpword = dao.getPassword("Book keeper");
					if(edited == 1){
						obj.put("fapword", fapword);
						obj.put("bkpword", bkpword);
						obj.put("plpword", plpword);
						obj.put("mlpword", mlpword);
						if(user.equalsIgnoreCase("Provincial link")){
							dao.add_logs(false, date, time, "Provincial Link change its password.");
						}
						else if(user.equalsIgnoreCase("Municipal link")){
							dao.add_logs(false, date, time, "Provincial Link changed the municipal link's account password.");
						}
						else if(user.equalsIgnoreCase("Financial Analyst")){
							dao.add_logs(false, date, time, "Provincial Link changed the financial analyst's account password.");
						}
						else if(user.equalsIgnoreCase("Book keeper")){
							dao.add_logs(false, date, time, "Provincial Link changed the book keeper's account password.");
						}
							
					}
					
					obj.put("edited", edited);
					out.print(obj);
					out.flush();
					out.close();
					
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
	}

}
