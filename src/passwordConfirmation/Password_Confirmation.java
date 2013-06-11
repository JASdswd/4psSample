package passwordConfirmation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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





/**
 * Servlet implementation class Password_Confirmation
 * Password confirmation in updating user accounts.
 * 
 */
@WebServlet("/Password_Confirmation")
public class Password_Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Password_Confirmation() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doget password_confirmation");
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
				//JSONObject obj=new JSONObject();
				
				try {
					Login_DAO dao = new Login_DAO();
					String username = (String)session.getAttribute("username");
					String hash = dao.byteArrayToHexString(dao.computeHash(request.getParameter("confirmation_password")));
					boolean check_password = dao.check_passwordbookKeeper(username, hash,4);
					System.out.println("check_password:"+check_password);
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
						/*else{
							System.out.println("nag'start na an transaction");
							objectall.put("has_transaction_time", true);
							objectall.put("transaction_time", session.getAttribute("transactionTime"));
						}*/
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		//doPost(request, response);
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
				
				try {
					Login_DAO dao = new Login_DAO();
					String hash = "";
					try {
						hash = dao.byteArrayToHexString(dao.computeHash(request.getParameter("confirmation_password")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					boolean check_password = dao.check_password("JAS", hash);
					System.out.println("checkpassword:"+check_password);
					if(check_password){
						out.print(1);
						out.flush();
						out.close();
					}
					else{
						out.print(0);
						out.flush();
						out.close();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
