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


import DAO.BaseDAO;
import bean.transactionBean;
import beans.BeansAdd;
import beans.Beanslistson;
import beans.Municpality;

import transaction_DAO.Transaction_DAO;

/**
 * Servlet implementation class Addtransaction
 */
@WebServlet("/Addtransaction")
public class Addtransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Addtransaction() {
        super(); 
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<BeansAdd>transaction_list=new ArrayList<BeansAdd>();
//		ArrayList<Beanslistson>reason_list=new ArrayList<Beanslistson>();
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null ChangeView servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				System.out.println("in sa ADDTRANSACTION");
				try{
					Transaction_DAO dao= new Transaction_DAO();
					ArrayList<transactionBean> list = new ArrayList<transactionBean>();
					
					ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
					municipal_list=dao.municipal_list();
					request.setAttribute("municipal_list",municipal_list);
					
					BaseDAO dao1 = new BaseDAO();
					String id=request.getParameter("household_id");
					list = dao1.gethousehold_info(false, id);
					if(list.isEmpty()){
						System.out.println("list is empty -> Addtransaction.java ");
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/redirectPage/redirect.jsp");
						rd.forward(request, response);
					}
					else{
						//transaction_list=dao.view_transaction(id);
						ArrayList<transactionBean> l1 = new ArrayList<transactionBean>();
						float last_amount = 0;
						l1 = dao.getSubANDDateOfTransaction(id);
						BeansAdd bean1 = null;
						BeansAdd bean2 = null;
						for(transactionBean x:l1){
							
							if(x.getSub() == 0){
								bean1 = dao.getDataByGrantee(x.getDateoftransaction(),id);
								transaction_list.add(bean1);
							}
							else{
								bean2 = dao.getDataByGrantee2(x.getDateoftransaction(),id,x.getSub());
								transaction_list.add(bean2);
							}
							
						}
						
						last_amount = dao.getLastAmount(id);
						if(transaction_list.isEmpty()){
							request.setAttribute("emp", "emp");
							System.out.println("wlay sulod");
						}else{
							request.setAttribute("transaction_list", transaction_list);
						}
						System.out.println("---------------------------------------------------------------------");
						for(BeansAdd l:transaction_list){
							System.out.println(l.getHousehold_id()+","+l.getMonth()+","+l.getReceive()+","+l.getMunLink_name());
						}
						System.out.println("---------------------------------------------------------------------");
						/*reason_list=dao.listreason();*/
						session.setAttribute("household_id", id);
						int ctr = dao1.testIfExist(true, "select * from photo_tbl where household_id = '"+id+"'");
						if(ctr>0){
							request.setAttribute("photohead_exist", true);
						}
						else{
							request.setAttribute("photohead_exist", false);
						}
						request.setAttribute("last_amount",last_amount);
						request.setAttribute("head_name", list.get(0).getHead_name());
						request.setAttribute("brgy", list.get(0).getBarangay());
						request.setAttribute("municipal", list.get(0).getMunicipality());
						request.setAttribute("id", list.get(0).getHousehold_id());
						/*request.setAttribute("reason_list", reason_list);*/
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/transaction/view_transaction.jsp");
						rd.forward(request, response);
					}
				}catch (Exception e) {
					e.printStackTrace();
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
				System.out.println("username is null ChangeView servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
		
		try{
			Transaction_DAO dao=new Transaction_DAO();
			String household_id=request.getParameter("household_id");
			String mon=request.getParameter("months");
			/*int month=0;
			if(mon.equals("JANUARY")){
				month=01;
			}else if(mon.equals("FEBRARY")){
				month=02;
			}else if(mon.equals("MARCH")){
				month=03;
			}else if(mon.equals("APRIL")){
				month=04;
			}else if(mon.equals("MAY")){
				month=05;
			}else if(mon.equals("JUNE")){
				month=06;
			}else if(mon.equals("JULY")){
				month=07;
			}else if(mon.equals("AUGUST")){
				month=8;
			}else if(mon.equals("SEPTEMBER")){
				month=9;
			}else if(mon.equals("OCTOBER")){
				month=10;
			}else if(mon.equals("NOVEMBER")){
				month=11;
			}else if(mon.equals("DECEMBER")){
				month=12;
			}*/
			int year=Integer.parseInt(request.getParameter("years"));
			int bool=Integer.parseInt(request.getParameter("bol"));
			int amount=Integer.parseInt(request.getParameter("amount"));
			String time=request.getParameter("time");
			String day=request.getParameter("day");
			
			//int reason_id=Integer.parseInt(request.getParameter("reason_id"));
			//System.out.println(reason_id+" reason_id");
			try{
				boolean check=dao.check_month_year(household_id,mon);
				if(check==true){
					String mess="Error Duplicate Data!";
					request.setAttribute("mess", mess);
				}else{
					/*boolean check_reason_id=dao.check_reason_id(reason_id);
					if(check_reason_id==true){
						String mess="Error Duplicate Data!";
						request.setAttribute("mess", mess);
					}else{*/
						//if(bool==1){
							//String reason=request.getParameter("reason");
							//if(reason!=null)
							//{
								/*BeansAdd beans=new BeansAdd(household_id,month,day,time,amount,bool);
								dao.addtransaction(beans);*/
								//reason=null;
								//BeansAdd bean=new BeansAdd(reason_id,household_id, reason,month,year);
								//dao.addreason(bean);
							//}
						//}
						//else if(bool==3){
							/*String []reason=request.getParameterValues("reason");
							//if(reason!=null){
								for(int i=0;i<reason.length;i++){
									if(reason.length>=7){
										bool=0;*/
										/*BeansAdd beans=new BeansAdd(household_id,month,day,time,amount,bool);
										dao.addtransaction(beans);*/
										/*BeansAdd bean=new BeansAdd(reason_id,household_id, reason[i],month,year);
										dao.addreason(bean);*/
									/*}else{
										BeansAdd beans=new BeansAdd(household_id,month,day,time,amount,bool);
										dao.addtransaction(beans);
										BeansAdd bean=new BeansAdd(reason_id,household_id, reason[i],month,year);
										dao.addreason(bean);
									}*/
						//}
							//}
					}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			request.setAttribute("head_name", request.getParameter("head_name"));
			request.setAttribute("brgy", request.getParameter("brgy"));
			request.setAttribute("municipal", request.getParameter("municipal"));
			doGet(request, response);
			/*response.sendRedirect("TransactionView");
			return;*/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			}
		}
		
	}

}
