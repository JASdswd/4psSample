package transaction_Serv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditTransaction
 */
@WebServlet("/EditTransaction")
public class EditTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTransaction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String month=request.getParameter("month");
		String id=request.getParameter("household_id");
		int year=Integer.parseInt(request.getParameter("year"));
		float amount=Float.parseFloat(request.getParameter("amount"));
		int recieve=Integer.parseInt(request.getParameter("recieve"));
		request.setAttribute("id", id);
		request.setAttribute("month", month);
		request.setAttribute("year", year);
		request.setAttribute("amount", amount);
		request.setAttribute("recieve", recieve);
		ServletContext sc=this.getServletContext();
		RequestDispatcher rd=sc.getRequestDispatcher("/transaction/edittransaction.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	try{
			Transaction_DAO dao=new Transaction_DAO();
			String household_id=request.getParameter("household_id");
			int month=Integer.parseInt(request.getParameter("month"));
			int year=Integer.parseInt(request.getParameter("year"));
			int nwmonth=Integer.parseInt(request.getParameter("nwmonth"));
			int nwyear=Integer.parseInt(request.getParameter("nwyear"));
			float amount=Float.parseFloat(request.getParameter("amount"));
			int recieve=Integer.parseInt(request.getParameter("bol"));
			int reason_id=Integer.parseInt(request.getParameter("reason_id"));
			boolean check_month_year=dao.check_month_year(household_id, month, year);
			System.out.println(check_month_year);
			if(check_month_year==true){
					String mess="Error Duplicate Data!";
					request.setAttribute("mess", mess);
			}else{
			if(month==nwmonth && year==nwyear){
				if(recieve==1){
					String reason=request.getParameter("reason");
					//if(reason!=null){
						reason=null;
						dao.updatetransaction(household_id, month, year, amount, recieve, reason_id,nwmonth,nwyear);
						BeansAdd bean=new BeansAdd(reason_id,household_id, reason,nwmonth,nwyear);
						dao.addreason(bean);
					//}
					request.setAttribute("household_id", household_id);
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/SerPath");
					rd.forward(request, response);
				}else if(recieve==3){
					String[] reason=request.getParameterValues("reason");
					dao.updatetransaction(household_id, month, year, amount, recieve, reason_id,nwmonth,nwyear);
					for(int i=0;i<reason.length;i++){
						BeansAdd bean=new BeansAdd(reason_id,household_id, reason[i],nwmonth,nwyear);
						dao.addreason(bean);
					}
					request.setAttribute("household_id", household_id);
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/SerPath");
					rd.forward(request, response);
				}
			}else{
				boolean check=dao.check_month_year(household_id, nwmonth, nwyear);
				if(check==true){
					String mess="Error Duplicate Data!";
					request.setAttribute("mess", mess);
					request.setAttribute("household_id", household_id);
					ServletContext sc=this.getServletContext();
					RequestDispatcher rd=sc.getRequestDispatcher("/SerPath");
					rd.forward(request, response);
				}else{
					if(recieve==1){
						String reason=request.getParameter("reason");
						//if(reason!=null){
							reason=null;
							dao.updatetransaction(household_id, month, year, amount, recieve, reason_id,nwmonth,nwyear);
							BeansAdd bean=new BeansAdd(reason_id,household_id, reason,nwmonth,nwyear);
							dao.addreason(bean);
						//}
						request.setAttribute("household_id", household_id);
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/SerPath");
						rd.forward(request, response);
					}else if(recieve==3){
						System.out.println("hell3");
						String[] reason=request.getParameterValues("reason");
						dao.updatetransaction(household_id, month, year, amount, recieve, reason_id,nwmonth,nwyear);
						for(int i=0;i<reason.length;i++){
							BeansAdd bean=new BeansAdd(reason_id,household_id, reason[i],nwmonth,nwyear);
							dao.addreason(bean);
						}
						request.setAttribute("household_id", household_id);
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/SerPath");
						rd.forward(request, response);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
