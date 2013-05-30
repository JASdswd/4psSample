package transaction_Serv;

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

import transaction_DAO.Transaction_DAO;
import beans.Beanstransaction;
import beans.Municpality;

/**
 * Servlet implementation class TransactionRedirect
 */
@WebServlet("/TransactionRedirect")
public class TransactionRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionRedirect() {
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
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null TransactionView servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
				ArrayList<Beanstransaction>householdlist=new ArrayList<Beanstransaction>();
				try{
					Transaction_DAO dao=new Transaction_DAO();
					municipal_list=dao.municipal_list();
					householdlist=dao.household_list("",0);
					request.setAttribute("municipal_list",municipal_list);
					request.setAttribute("householdlist", householdlist);
					request.setAttribute("type", "household");
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/transaction/viewtransaction.jsp");
				rd.forward(request, response);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

}
