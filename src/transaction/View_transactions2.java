package transaction;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class View_transactions2
 */
@WebServlet("/View_transactions2")
public class View_transactions2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_transactions2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		if(session==null){
<<<<<<< HEAD
			System.out.println("session is null add municipality servelet");
=======
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
<<<<<<< HEAD
				System.out.println("username is null View_transactions2 servlet");
=======
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
<<<<<<< HEAD
=======
				System.out.println("View_transactions2 doPost");
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
				System.out.println("hh_id+"+request.getParameter("hh_id"));
				session.setAttribute("household_id", request.getParameter("hh_id"));
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/View_transactions");
				rd.forward(request, response);
			}
		}
	}

}
