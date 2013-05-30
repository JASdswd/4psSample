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
import bean.transactionBean;

/**
 * Servlet implementation class ViewAllUser2
 */
@WebServlet("/ViewAllUser2")
public class ViewAllUser2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAllUser2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		String destination = "";
		String user_role = request.getParameter("id");
		if(session==null){
			destination = "/index.jsp";
		}
		else{
			if(session.getAttribute("username")==null){
				destination = "/index.jsp";
			}
			else{	
				ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
				try {					
					UserDAO dao = new UserDAO();
					System.out.println("user_role ViewAllUser2 servlet:"+user_role);
					if(user_role == null || user_role.equals("")){
						destination = "/redirectPage/redirect.jsp";
					}
					else{
						bean = dao.View(user_role);
						if(bean.isEmpty()){
							destination = "/redirectPage/redirect.jsp";
						}
						else{
							for(transactionBean l: bean){
								if(l.getFingerprint() == null || l.getFingerprint().equals(null)){
									request.setAttribute("fingerprint_exist", false);
								}
								else{
									request.setAttribute("fingerprint_exist", true);
								}
								
								if(l.getPhoto_head() == null || l.getPhoto_head().equals(null)){
									request.setAttribute("photohead_exist", false);
								}
								else{
									request.setAttribute("photohead_exist", true);
								}
							}
							destination = "/user/viewprof_user2.jsp"; // Path to JSP file/ View Profile of Financial Analyst
							session.setAttribute("user_list2", bean);
							session.setAttribute("user_role", user_role);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
			}
			
		}
		ServletContext sc=this.getServletContext();
		RequestDispatcher rd=sc.getRequestDispatcher(destination);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}