package transaction_Serv;

import java.io.IOException;
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

import beans.BeansAdd;
import beans.Beanstransaction;

import transaction_DAO.Transaction_DAO;

/**
 * Servlet implementation class AddReason
 */
@WebServlet("/AddReason")
public class AddReason extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReason() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			ArrayList<Beanstransaction>lists= new ArrayList<Beanstransaction>();
			Calendar calendar= Calendar.getInstance();
			//SimpleDateFormat format= new SimpleDateFormat("hh:mm:ss");
			SimpleDateFormat format1=new SimpleDateFormat("yyyy");
			//String time=format.format(calendar.getTime());
			String year=format1.format(calendar.getTime());
			Transaction_DAO dao=new Transaction_DAO();
			//int reason_id=dao.reason_id();
			lists=dao.lists(request.getParameter("household_id"));
			//request.setAttribute("reason_id", reason_id);
			request.setAttribute("lists", lists);
		//request.setAttribute("time", time);
			request.setAttribute("year", year);
			request.setAttribute("household_id", request.getParameter("household_id"));
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/transaction/addtransaction.jsp");
			rd.forward(request, response);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*try{
			Transaction_DAO dao = new Transaction_DAO();
			BeansAdd beans =new BeansAdd(Integer.parseInt(request.getParameter("household_id"), request.getParameter("reason"),
					Integer.parseInt(request.getParameter("month")));
			dao.addreason(beans);
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/transaction/addtransaction.jsp");
			rd.forward(request, response);
		}catch (Exception e) {
			// TODO: handle exception
		}*/
	}

}
