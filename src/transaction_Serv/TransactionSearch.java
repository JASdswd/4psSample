package transaction_Serv;

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


import transaction_DAO.Transaction_DAO;

import beans.Beanstransaction;
import beans.Municpality;

/**
 * Servlet implementation class TransactionSearch
 */
@WebServlet("/TransactionSearch")
public class TransactionSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("TransactionView");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null add municipality servelet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null TransactionSearch servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				System.out.println("in sa transaction search..");
				
				ArrayList<Municpality>municipal_list=new ArrayList<Municpality>();
				ArrayList<Beanstransaction>householdlist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>granteelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>wifelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>studentlist=new ArrayList<Beanstransaction>();
				//ArrayList<BeansAdd>receivelist=new ArrayList<BeansAdd>();
				ArrayList<Beanstransaction>municipal_list1=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>brgy_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>birth_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>phil_list=new ArrayList<Beanstransaction>();
				String name=request.getParameter("val");
				String transaction=request.getParameter("transaction");
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				
				try{
					Transaction_DAO dao=new Transaction_DAO();
					municipal_list=dao.municipal_list();
					request.setAttribute("municipal_list",municipal_list);
					if(transaction.equals("household")){
						householdlist=dao.household_list(name,0);
						if(householdlist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", householdlist);
						}
					}else if(transaction.equals("grantee")){
						granteelist=dao.granteelist(name);
						if(granteelist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", granteelist);
						}
					}else if(transaction.equals("wife")){
						wifelist=dao.wifelist(name);
						if(wifelist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", wifelist);
						}
						
					}else if(transaction.equals("student")){
						studentlist=dao.studentlist(name);
						if(studentlist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", studentlist);
						}
					}
					else if(transaction.equals("municipal")){
						String municipal=request.getParameter("municipal");
						municipal = getmun(municipal);
						String brgy=request.getParameter("brgy");
						System.out.println("municipal:"+municipal);
						if(municipal!=null && !brgy.equals("Barangay")){
							System.out.println("Abrgy name:"+brgy);
							System.out.println(" brgy name:"+name);
							municipal_list1=dao.municipality_list(municipal, brgy, name);
							if(municipal_list1.isEmpty()){
								String mess="No Record Found";
								obj.put("mess", mess);
							}else{
								obj.put("data", municipal_list1);
							}
						}else if(municipal!=null && brgy.equals("Barangay")){
							System.out.println("sakob ngadi");
							municipal_list1=dao.municipal_list1(municipal, name);
							if(municipal_list1.isEmpty()){
								String mess="No Record Found";
								obj.put("mess", mess);
							}else{
								obj.put("data", municipal_list1);
							}
						}
					}else if(transaction.equals("brgy")){
						String brgy_select= request.getParameter("brgy_select");
						System.out.println(" -----------------------brgy name:"+brgy_select);
						brgy_list=dao.brgay_list(brgy_select);
						if(brgy_list.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", brgy_list);
						}
					}else if(transaction.equals("birth")){
						String date=request.getParameter("date");
						birth_list=dao.birth_list(date);
						if(birth_list.isEmpty()){
							obj.put("day", "No Birthdays");
						}else{
							obj.put("data", birth_list);
						}
					}else if(transaction.equals("phil")){
						phil_list=dao.philhealth_list(name);
						if(phil_list.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", phil_list);
						}
					}
					
					out.print(obj);
					out.flush();
					out.close();
					
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	} //END of dopost
	
	public String getmun(String mun){
		String []temp = mun.split(" ");
		if(temp.length>=2){
			return temp[0];
		}
		return mun;
		
	}

}
