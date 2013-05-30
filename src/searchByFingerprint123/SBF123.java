package searchByFingerprint123;

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
 * Servlet implementation class SBF123
 */
@WebServlet("/SBF123")
public class SBF123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SBF123() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doget SBF123.java");
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null SearchByFingerprint servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				doPost(request, response);
			}
		}
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
				System.out.println("username is null SearchByFingerprint servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				//MainForm_sbf form = new MainForm_sbf(request.getParameter("mun"));
				
				session.setAttribute("mun", request.getParameter("mun"));
				
				ArrayList<BeansAdd>transaction_list=new ArrayList<BeansAdd>();
				ArrayList<Beanstransaction> beantransaction = new ArrayList<Beanstransaction>();
		//		ArrayList<Beanslistson>reason_list=new ArrayList<Beanslistson>();
				
					
				
					try{
						//MainVerification verify = new MainVerification(request.getParameter("household_id"));
						Transaction_DAO dao = new Transaction_DAO();
						
						ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
						municipal_list=dao.municipal_list();
						request.setAttribute("municipal_list",municipal_list);
						request.setAttribute("mun", session.getAttribute("mun"));
						float last_amount = 0;
						BaseDAO dao1 = new BaseDAO();
						String id = request.getParameter("id");
						String brgy_id = dao1.getbrgyID2(id);
						session.setAttribute("brgy_id", brgy_id);
						//transaction_list = dao.view_transaction(id);
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
						
						last_amount = dao.getLastAmount(id);
						
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
						beantransaction = dao.lists(id);
						request.setAttribute("last_amount",last_amount);
						request.setAttribute("head_name", beantransaction.get(0).getHead_name());
						request.setAttribute("brgy", beantransaction.get(0).getBrgy());
						request.setAttribute("municipal", beantransaction.get(0).getMunicipal());
						request.setAttribute("id", id);
						String username = (String)session.getAttribute("username");
						request.setAttribute("username", username);
						/*request.setAttribute("reason_list", reason_list);*/
						ServletContext sc=this.getServletContext();
						RequestDispatcher rd=sc.getRequestDispatcher("/transaction/view_transaction.jsp");
						rd.forward(request, response);
					
					
					}catch (Exception e) {
						// TODO: handle exception
					}
			}
		}
	}

}
