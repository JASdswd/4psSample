package login;

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

import beans.Loginbeans;

import login_DAO.Login_DAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			ArrayList<Loginbeans>userlist=new ArrayList<Loginbeans>();
			try{
				Login_DAO dao=new Login_DAO();
				userlist=dao.userlist();
				String user=null;
				request.setAttribute("user", user);
				request.setAttribute("userlist", userlist);
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/login/login1.jsp");
				rd.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Loginbeans>userlist=new ArrayList<Loginbeans>();
		HttpSession  session=request.getSession(false);
		
		if(session.getAttribute("username")!=null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/home/home_page.jsp");
			rd.forward(request, response);
		}
		else{
			try{
			
				Login_DAO dao =new Login_DAO();
				String user=request.getParameter("user");
				String password=request.getParameter("password");
				
				userlist=dao.userlist();
				
				String hash = dao.byteArrayToHexString(dao.computeHash(password));
				
				String user_id = dao.getUserID(user);
				String stringcheck_password = dao.check_passwordLogin(user);
				int user_role = dao.getUser_role(user, hash);
				System.out.println("user_role ===="+user_role);
				if(hash.equals(stringcheck_password)){
					int check_password=dao.check_passwordLogin(user, hash,"user_tbl");
						if(check_password==1){
							request.setAttribute("provlink", true);
							session.setAttribute("provlink", true);
							session.setAttribute("provOrMun", true);
						}
						else{
							request.setAttribute("provlink", false);
							session.setAttribute("provlink", false);
							session.setAttribute("provOrMun", false);
						}
						if(check_password==2){
							request.setAttribute("munlink", true);
							session.setAttribute("munlink", true);
							session.setAttribute("provOrMun", true);
						}
						else if(check_password==4){
							request.setAttribute("book_keeper", true);
							session.setAttribute("book_keeper", true);
						}
						else if(check_password==3){
							request.setAttribute("financial_analyst", true);
							session.setAttribute("financial_analyst", true);
						}
						else if(check_password==5){
							request.setAttribute("verifier", true);
							session.setAttribute("verifier", true);
						}
						else if(check_password==6){
							request.setAttribute("grievance_officer", true);
							session.setAttribute("grievance_officer", true);
						}
						else if(check_password==7){
							request.setAttribute("social_worker", true);
							session.setAttribute("grievance_worker", true);
						}
						else if(check_password==10){
							request.setAttribute("admin", true);
							session.setAttribute("admin", true);
						}
						byte[] ctr = null;
						ctr = dao.testIfExist(false, "select photo from user_tbl where id = '"+user_id+"'");
						System.out.println("ctr login.java:"+ctr);
						if(ctr == null ){
							request.setAttribute("photohead_exist", false);
						}
						else{
							request.setAttribute("photohead_exist", true);
						}
						String duser_rolename = dao.getUser_rolename(user_role, user_id);
						System.out.println("username()_sds : "+duser_rolename);
			
						String fname = dao.getFname(user_role, user_id);
						String pop_up = "Ajaw vah,ajaw woiest";
						
						request.setAttribute("popup", pop_up);
						session.setAttribute("user_id", user_id);
						session.setAttribute("Luser_role", user_role);
						session.setAttribute("user", user);
						session.setAttribute("username", user);// ajaw tanggala kai para ni cja sa session..
						request.setAttribute("user", user);
						request.setAttribute("user_role", user_role);
						request.setAttribute("fname", fname);
						session.setAttribute("dfname", fname);
						session.setAttribute("duser_rolename", duser_rolename);
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/home/home_page.jsp");
						rd.forward(request, response);
						
				}
				else{
					String mess="Incorrect Password";
					request.setAttribute("userlist", userlist);
					request.setAttribute("error",mess);
					request.setAttribute("user", user);
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/login/login1.jsp");
					rd.forward(request, response);
					
				}
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
 