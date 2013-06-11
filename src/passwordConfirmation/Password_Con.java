package passwordConfirmation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login_DAO.Login_DAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import transaction_DAO.Transaction_DAO;
import DAO.BaseDAO;
import beans.BeansAdd;

/**
 * Servlet implementation class Password_Con
 * Para ni cja sa kada dyes minutos nga pagpangayo og password inig panghatag og monart.  
 */
@WebServlet("/Password_Con")
public class Password_Con extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Password_Con() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("pass_con doget");
		HttpSession session = request.getSession(false);
		System.out.println("pass servlet");
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null Password_Confirmation servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
		
				PrintWriter out= response.getWriter();
				//JSONObject obj=new JSONObject();
				
				try {
					Login_DAO dao = new Login_DAO();
					String hashpword = dao.byteArrayToHexString(dao.computeHash(request.getParameter("confirmation_password1")));
					boolean check_password = dao.check_password(request.getParameter("user"), hashpword);
					if(check_password){
						
						ArrayList<BeansAdd>list_house=new ArrayList<BeansAdd>();
						
						JSONArray array=new JSONArray();
						//JSONArray array1=new JSONArray();
						JSONObject objectall=new JSONObject();
						Calendar calendar= Calendar.getInstance();
						DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
						SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
						
							String day=format.format(calendar.getTime());
							System.out.println("date="+day);
							String time=timeInstance.format(Calendar.getInstance().getTime());
							System.out.println("time="+time);
							
							String household_id=request.getParameter("household_id");
							String month=request.getParameter("month");
							String id=request.getParameter("id");
							String comment=request.getParameter("comment");
							Transaction_DAO dao1 = new Transaction_DAO();
							dao1.updaterecieve(household_id, month, day,time,1,comment);
							
							/*JSONObject transaction_time=new JSONObject();
							transaction_time.put("transaction_time", time);
							out.print(transaction_time);*/
							
							list_house=dao1.list_house(household_id, month);
							//reason_list=dao.listreason();
							for(BeansAdd i:list_house){
								JSONObject obj=new JSONObject();
								obj.put("household_id", i.getHousehold_id());
								obj.put("month", i.getMonth());
								/*obj.put("year", i.getYear());*/
								obj.put("amount", i.getAmount());
								obj.put("recieve", 1);
								obj.put("date_receive", day);
								obj.put("time", time);
								obj.put("comment", i.getComment());
								obj.put("sub", i.getSub());
								obj.put("munLink_name", i.getMunLink_name());
								array.put(obj);
							}
							objectall.put("data", array );
							
							objectall.put("id", id);
							String start_transaction = (String)session.getAttribute("startTransaction");
							System.out.println("start_transaction:"+start_transaction);
							Date date = new Date();
						    SimpleDateFormat simpDate;

						    simpDate = new SimpleDateFormat("kk:mm:ss");
						    System.out.println("24 hour format:"+simpDate.format(date));
							if(start_transaction == null || start_transaction.equals(null)){
								System.out.println("starting of transaction");
								objectall.put("transaction_time", simpDate.format(date));
								session.setAttribute("transactionTime", simpDate.format(date));
							}
							else{
								System.out.println("nag'start na an transaction");
								objectall.put("has_transaction_time", true);
								objectall.put("transaction_time", session.getAttribute("transactionTime"));
							}
							session.setAttribute("startTransaction", "yes");
							
							System.out.println(objectall);
							out.print(objectall);
							
							out.flush();
							out.close();
						
					}
					else{
						JSONObject obj=new JSONObject();
						obj.put("trys", true);
						out.print(obj);
						out.flush();
						out.close();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// wala na magamit ning doPost.[arg]
		HttpSession session  = request.getSession(false);
		
		if(session==null){
			System.out.println("session is null add user mun servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null add user mun servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				System.out.println("password:"+request.getParameter("confirmation_password"));
			
				try{
					ArrayList<String> plpword=new ArrayList<String>();
					ArrayList<String> mlpword=new ArrayList<String>();
					ArrayList<String> fapword=new ArrayList<String>();
					ArrayList<String> bkpword=new ArrayList<String>();
					
					JSONObject obj = new JSONObject();
					PrintWriter out = response.getWriter();
					BaseDAO dao = new BaseDAO();
					plpword = dao.getPassword2("Provincial link");
					mlpword = dao.getPassword2("Municipal link");
					fapword = dao.getPassword2("Financial Analyst");
					bkpword = dao.getPassword2("Book keeper");
					
					System.out.println("PlPword:"+plpword);
					
					obj.put("plpword", plpword);
					obj.put("mlpword", mlpword);
					obj.put("fapword", fapword);
					obj.put("bkpword", bkpword);
					System.out.println("Objectall="+obj);
					out.print(obj);
					out.flush();
					out.close();
					
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
	
	}

}
