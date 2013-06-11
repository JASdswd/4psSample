package reports;

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
 * Servlet implementation class FingerPrintSearch
 */
@WebServlet("/FingerPrintSearch")
public class FingerPrintSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FingerPrintSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("NoFingerPrint");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
				ArrayList<Beanstransaction>fhouseholdlist=new ArrayList<Beanstransaction>();
				ArrayList<String>fingerprintList=new ArrayList<String>();
				ArrayList<Beanstransaction>granteelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fgranteelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>wifelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fwifelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>studentlist=new ArrayList<Beanstransaction>();
				//ArrayList<BeansAdd>receivelist=new ArrayList<BeansAdd>();
				ArrayList<Beanstransaction>municipal_list1=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fmunicipal_list1=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>brgy_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fbrgy_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>birth_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fbirth_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>phil_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>fphil_list=new ArrayList<Beanstransaction>();
				String name=request.getParameter("val");
				String transaction=request.getParameter("transaction");
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				
				try{
					
					Transaction_DAO dao=new Transaction_DAO();
					municipal_list=dao.municipal_list();
					request.setAttribute("municipal_list",municipal_list);
					fingerprintList = dao.getflist();
					if(transaction.equals("household")){
					
						System.out.println("in sa hID:"+name);
						householdlist = dao.gettemplist(name,"household_id");
						
						for(Beanstransaction l:householdlist){
							boolean found = false;
							for(String f : fingerprintList){
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									System.out.println("true found");;
									found = true;
								}
							}
							if(found == false){
								fhouseholdlist.add(l);
								System.out.println("false found no fingerprint");;
							}
						}
						
						//fhouseholdlist = getSearchedData(1,fingerprintList,householdlist);
						if(fhouseholdlist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", fhouseholdlist);
						}
					}else if(transaction.equals("grantee")){
						granteelist=dao.gettemplist(name,"head_name");
						
						for(Beanstransaction l:granteelist){
							boolean found = false;
							for(String f : fingerprintList){
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									found = true;
								}
							}
							if(found == false){
								fgranteelist.add(l);
							}
						}
						
						if(fgranteelist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", fgranteelist);
						}
					}else if(transaction.equals("wife")){
						wifelist=dao.gettempwlist(name,"spouse_name");
						
						for(Beanstransaction l:wifelist){
							boolean found = false;
							for(String f : fingerprintList){
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									found = true;
								}
							}
							if(found == false){
								fwifelist.add(l);
							}
						}
						
						if(fwifelist.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", fwifelist);
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
						System.out.println("brgy:"+brgy);
						if(municipal!=null && !brgy.equals("Barangay")){
							municipal_list1=dao.gettempmunicipality_list(municipal, brgy, name);
							System.out.println("in sa mun is not null and brgy is null");
							for(Beanstransaction l:municipal_list1){
								boolean found = false;
								for(String f : fingerprintList){
									if(l.getHousehold_id().equalsIgnoreCase(f)){
										found = true;
									}
								}
								if(found == false){
									fmunicipal_list1.add(l);
								}
							}
							
							
							if(fmunicipal_list1.isEmpty()){
								String mess="No Record Found";
								obj.put("mess", mess);
							}else{
								obj.put("data", fmunicipal_list1);
							}
						}else if(municipal!=null && brgy.equals("Barangay")){
							municipal_list1=dao.getmunicipal_list1(municipal, name);
							
							for(Beanstransaction l:municipal_list1){
								boolean found = false;
								for(String f : fingerprintList){
									if(l.getHousehold_id().equalsIgnoreCase(f)){
										found = true;
									}
								}
								if(found == false){
									fmunicipal_list1.add(l);
								}
							}
							
							if(fmunicipal_list1.isEmpty()){
								String mess="No Record Found";
								obj.put("mess", mess);
							}else{
								obj.put("data", fmunicipal_list1);
							}
						}else if(municipal==null && brgy.equals("Barangay")){
							String mess="No Record Found";
							obj.put("mess", mess);
						}
					}else if(transaction.equals("brgy")){
						String brgy_id = request.getParameter("brgy_select");
						brgy_list=dao.gettemplist2(brgy_id,"barangay");
						
						for(Beanstransaction l:brgy_list){
							boolean found = false;
							for(String f : fingerprintList){
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									found = true;
								}
							}
							if(found == false){
								fbrgy_list.add(l);
							}
						}
						
						if(fbrgy_list.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", fbrgy_list);
						}
					}else if(transaction.equals("birth")){
						String date=request.getParameter("date");
						birth_list=dao.gettemplist(date,"birthday");
						
						for(Beanstransaction l: birth_list){
							boolean found = false;
							for(String f : fingerprintList){
								
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									found = true;
								}
								
							}
							
							if(found = false){
								fbirth_list.add(l);
							}
							
						}
						
						if(birth_list.isEmpty()){
							obj.put("day", "No Birthdays");
						}else{
							obj.put("data", fbirth_list);
						}
					}else if(transaction.equals("phil")){
						phil_list=dao.gettemplist(name,"philhealth_id");
						
						for(Beanstransaction l: phil_list){
							boolean found = false;
							for(String f : fingerprintList){
								
								if(l.getHousehold_id().equalsIgnoreCase(f)){
									found = true;
								}
								
							}
							
							if(found = false){
								fphil_list.add(l);
							}
							
						}
						
						if(fphil_list.isEmpty()){
							String mess="No Record Found";
							obj.put("mess", mess);
						}else{
							obj.put("data", fphil_list);
						}
					}
					
					out.print(obj);
					out.flush();
					out.close();
					
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	} //ENDof dopost

	String getmun(String mun){
		String []temp = mun.split(" ");
		if(temp.length>=2){
			return temp[0];
		}
		return mun;
		
	}
}
