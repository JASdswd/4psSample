package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import bean.transactionBean;

/**
 * Servlet implementation class ViewAllSW
 */
@WebServlet("/ViewAllUser")
public class ViewAllUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAllUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);		
		if(session==null){
			System.out.println("ViewAllUser session is null");
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
				ArrayList<transactionBean> user = new ArrayList<transactionBean>();
				try {
					UserDAO dao = new UserDAO();
					String user_role = "";
					user_role = request.getParameter("id");
					
					String destination = "";
					if(user_role == null || user_role.equals("")){
						destination = "/redirectPage/redirect.jsp";
					}
					else{
						if(user_role.equals("2") || user_role.equals("4") || user_role.equals("5") || user_role.equals("6") || user_role.equals("7")){
							bean = dao.getmunicipal(false);
							user = dao.getAllUserData(user_role);
							
							request.setAttribute("municipal_list", bean);
							request.setAttribute("user_list", user);
							request.setAttribute("user_role", user_role);
							
							destination = "/user/viewAll_user.jsp";
						}
						else{
							destination = "/redirectPage/redirect.jsp";
						}
						
					}
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher(destination);
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				try{
					UserDAO dao = new UserDAO();
					PrintWriter out = response.getWriter();
					JSONObject obj = new JSONObject();
					int mun_id = Integer.parseInt(request.getParameter("mun_id"));
					int user_role = Integer.parseInt(request.getParameter("user_role"));
					ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
					bean = dao.getUser_JSON(mun_id,user_role);
					
					obj.put("data", bean);
					out.print(obj);
					out.flush();
					out.close();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}	
	}

}
