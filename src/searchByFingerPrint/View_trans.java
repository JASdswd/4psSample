package searchByFingerPrint;

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
import DAO.BaseDAO;
import bean.transactionBean;
import beans.BeansAdd;
import beans.Beanstransaction;
import beans.Municpality;

/**
 * Servlet implementation class View_trans
 */
@WebServlet("/View_trans")
public class View_trans extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_trans() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArrayList<BeansAdd>transaction_list=new ArrayList<BeansAdd>();
		ArrayList<Beanstransaction> beantransaction = new ArrayList<Beanstransaction>();
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
				System.out.println("username is null View_trans servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				try{
					//MainVerification verify = new MainVerification(request.getParameter("household_id"));
					Transaction_DAO dao = new Transaction_DAO();
					
					ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
					municipal_list=dao.municipal_list();
					request.setAttribute("municipal_list",municipal_list);
					request.setAttribute("mun", session.getAttribute("mun"));
					
					BaseDAO dao1 = new BaseDAO();
					String id = request.getParameter("id");
					float last_amount = 0; 
					
					beantransaction = dao.lists(id);
					ArrayList<transactionBean> l1 = new ArrayList<transactionBean>();
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
					/*Collections.sort(transaction_list, new Comparator() {

						@Override
						public int compare(Object o1, Object o2) {
							
						}
					});*/
					last_amount = dao.getLastAmount(id);
					
					if(beantransaction.isEmpty()){
						System.out.println("list is empty -> View_trans.java ");
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/redirectPage/redirect.jsp");
						rd.forward(request, response);
					}
					else{
						if(transaction_list.isEmpty()){
							request.setAttribute("emp", "emp");
							System.out.println("wlay sulod");
						}else{
							request.setAttribute("transaction_list", transaction_list);
						}
						/*reason_list=dao.listreason();*/
						session.setAttribute("household_id", id);
						int ctr = dao1.testIfExist(true, "select * from photo_tbl_temp2 where household_id = '"+id+"'");
						if(ctr>0){
							request.setAttribute("photohead_exist", true);
						}
						else{
							request.setAttribute("photohead_exist", false);
						}
						request.setAttribute("last_amount",last_amount);
						request.setAttribute("head_name", beantransaction.get(0).getHead_name());
						request.setAttribute("brgy", beantransaction.get(0).getBrgy());
						request.setAttribute("municipal", beantransaction.get(0).getMunicipal());
						request.setAttribute("id", id);
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/transaction/view_transaction.jsp");
						rd.forward(request, response);
					}
					
				
				
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
		doGet(request, response);
	}

}
