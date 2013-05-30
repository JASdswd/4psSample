package users;

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

import login_DAO.Login_DAO;

import DAO.BaseDAO;
import bean.transactionBean;

/**
 * Servlet implementation class AddSocial
 */
@WebServlet("/Add_user")
public class Add_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_user() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet add user servlet[jm]");
		HttpSession session  = request.getSession(false);
		ArrayList<transactionBean> mlist = new ArrayList<transactionBean>();
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
				
				int id = Integer.parseInt(request.getParameter("id"));
				String mun = request.getParameter("mun");
				String destination = "";
				int user_role = 0;
				if(request.getParameter("user_role") == null || request.getParameter("user_role").equals("")){
					destination = "/redirectPage/redirect.jsp";
				}
				else{
					
					try {
						user_role = Integer.parseInt(request.getParameter("user_role"));
						UserDAO dao = new UserDAO();
						bean  = dao.getUser_profile(id,mun,user_role);
						mlist = dao.getmunicipal(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					if(bean.isEmpty()){
						destination = "/redirectPage/redirect.jsp";
					}
					else{
						String municipality = bean.get(0).getMunicipality();
						String fname = bean.get(0).getPl_fname();
						String lname = bean.get(0).getPl_lname();
						for(transactionBean x: bean){
							if(x.getFingerprint() == null || x.getFingerprint().equals(null)){
								System.out.println("no fingerprint found...");
								request.setAttribute("fingerprint_exist", false);
							}
							else{
								request.setAttribute("fingerprint_exist", true);
							}
							
							if(x.getPhoto_head() == null || x.getPhoto_head().equals(null)){
								request.setAttribute("photohead_exist", false);
							}
							else{
								request.setAttribute("photohead_exist", true);
							}
						}
						/*checking if exist*/
						request.setAttribute("m", mun);
						request.setAttribute("f", fname);
						request.setAttribute("l", lname);
						request.setAttribute("ID",id);
						request.setAttribute("user_role", user_role);
						request.setAttribute("municipal_list", mlist);
						request.setAttribute("user_list", bean);
						destination = "/user/viewprof_user.jsp";
						
					}
				}
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher(destination);
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("add user[jm]");
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
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String uname = request.getParameter("uname");
				String password = request.getParameter("pword");
				String gender = request.getParameter("gender");
				String email = request.getParameter("email");
				String contact = request.getParameter("contact");
				String mun = request.getParameter("mun");
				String user_roleString = request.getParameter("hiddenUser_role");
				
				ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
				ArrayList<transactionBean> user = new ArrayList<transactionBean>();
				ArrayList<transactionBean> mlist = new ArrayList<transactionBean>();
				
				try {
					UserDAO dao = new UserDAO();
					BaseDAO bdao = new BaseDAO();
					String destination = "";
					int count = 0;
					count = bdao.testIfExist(false, "select * from user_tbl where u_name = '"+uname+"' ");
					if(count>0){
						System.out.println("duplicate username:"+uname);
						bean = dao.getmunicipal(false);
						user = dao.getAllUserData(user_roleString);
						request.setAttribute("municipal_list", bean);
						request.setAttribute("user_list", user);
						request.setAttribute("user_role", user_roleString);
						
						request.setAttribute("duplicateUser", true);
						destination = "/user/viewAll_user.jsp";
					}
					else{
						int user_role = Integer.parseInt(request.getParameter("hiddenUser_role"));
						Login_DAO dao1 = new Login_DAO();
						String hashpword = dao1.byteArrayToHexString(dao1.computeHash(password));
						// need to edit this code..
						dao.add_user_profile(user_role,fname,lname,uname,hashpword,gender,email,contact,mun,false);
						int id = dao.getUserId(uname);
						bean  = dao.getUser_profile(id, mun, user_role);
						mlist = dao.getmunicipal(false);
						/*page not found*/
						session.setAttribute("fname",fname );
						session.setAttribute("lname", lname);
						session.setAttribute("mun", mun);
						/*checking if exist*/
						request.setAttribute("f", fname);
						request.setAttribute("l", lname);
						request.setAttribute("u", uname);
						request.setAttribute("m", mun);
						request.setAttribute("municipal_list", mlist);
						request.setAttribute("user_list", bean);
						request.setAttribute("user_role", user_role);
						request.setAttribute("ID",id);
						destination = "/user/viewprof_user.jsp";
					}
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher(destination);
					rd.include(request, response);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
