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

import org.apache.catalina.connector.Request;

import transaction_DAO.Transaction_DAO;

import beans.Beanstransaction_record;
import beans.Brgy;
import beans.Month_Year;
import beans.Municpality;

/**
 * Servlet implementation class View_RecordTransaction
 */
@WebServlet("/View_RecordTransaction")
public class View_RecordTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_RecordTransaction() {
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
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_RecordTransaction servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
				ArrayList<Month_Year>month_year_list=new ArrayList<Month_Year>();
				try{
					Transaction_DAO dao=new Transaction_DAO();
					municipal_list=dao.municipal_list();
					month_year_list=dao.m_y_list();
					String brgy="";
					
					request.setAttribute("municipal_list", municipal_list);
					request.setAttribute("month_year_list", month_year_list);
					request.setAttribute("brgy", brgy);
					request.setAttribute("municipal", "");
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/transaction/view_recordtransaction.jsp");
					rd.forward(request, response);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_RecordTransaction servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
				ArrayList<Brgy>brgy_list=new ArrayList<Brgy>();
				ArrayList<Beanstransaction_record>record_list=new ArrayList<Beanstransaction_record>();
				ArrayList<Month_Year>month_year_list=new ArrayList<Month_Year>();
				try{
					Transaction_DAO dao=new Transaction_DAO();
					String municipal=request.getParameter("municipal");
					String barangay=request.getParameter("brgy");
					request.setAttribute("municipal", municipal);
					System.out.println("brgy ="+barangay);
					String month=request.getParameter("month");
					/*int year=Integer.parseInt(request.getParameter("year"));*/
					municipal_list=dao.municipal_list();
					
					TransactionSearch tran_search = new TransactionSearch();
					//municipal = tran_search.getmun(municipal);
					
					System.out.println("municipal:"+municipal);
					
					brgy_list=dao.brgy_list(municipal);
					
					request.setAttribute("municipal_list", municipal_list);
					request.setAttribute("brgy_list", brgy_list);
					request.setAttribute("brgy", barangay);
					request.setAttribute("month", month);
					month_year_list=dao.m_y_list();
					
					record_list=dao.list_record(municipal, barangay, month);
					
					if(record_list.isEmpty()){
						String mess="No Record Found";
						request.setAttribute("mess", mess);
					}else{
						
						request.setAttribute("record_list", record_list);
					}
					request.setAttribute("month_year_list", month_year_list);
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/transaction/view_recordtransaction.jsp");
					rd.forward(request, response);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

}
