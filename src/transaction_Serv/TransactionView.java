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

import org.json.*;

import beans.Municpality;

import beans.Beanstransaction;

import transaction_DAO.Transaction_DAO;
/**
 * Servlet implementation class TransactionView
 */
@WebServlet("/TransactionView")
public class TransactionView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionView() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				ArrayList<Beanstransaction> iDOCP = new ArrayList<Beanstransaction>();
				try{
					Transaction_DAO dao=new Transaction_DAO();
					municipal_list=dao.municipal_list();
					householdlist=dao.household_list("",1);
					iDOCP = dao.getIDOCP();
					request.setAttribute("mun", session.getAttribute("mun"));
					request.setAttribute("municipal_list",municipal_list);
					request.setAttribute("householdlist", householdlist);
					request.setAttribute("type", "household");
					request.setAttribute("iDOCP", iDOCP);
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/transaction/viewtransaction.jsp");
				rd.forward(request, response);
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
		//HttpSession session=request.getSession(false);
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null TransactionView servlet doPost");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<Beanstransaction>householdlist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>granteelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>wifelist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>studentlist=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>municipal_list=new ArrayList<Beanstransaction>();
				ArrayList<Beanstransaction>brgy_list=new ArrayList<Beanstransaction>();
			//ArrayList<BeansAdd>receivelist=new ArrayList<BeansAdd>();
				String name=request.getParameter("name");
				String transaction=request.getParameter("transaction");
				try{
					Transaction_DAO dao=new Transaction_DAO();
					JSONArray array=new JSONArray();
					JSONObject objectall=new JSONObject();
					PrintWriter out=response.getWriter();
					if(transaction.equals("household")){
						householdlist=dao.household_list(name,0);
					
						if(householdlist.isEmpty()){
							JSONObject ob=new JSONObject();
							ob.put("empty", true);
							out.print(ob);
							out.close();
						}else{
							for(Beanstransaction i: householdlist){
								JSONObject obj=new JSONObject();
								obj.put("household_id", i.getHousehold_id());
								obj.put("household_member_id", i.getHouseholdmember_id());
								obj.put("head_name", i.getName());
								obj.put("brgy", i.getBrgy());
								obj.put("municipal", i.getMunicipal());
								array.put(obj);
							}
							objectall.put("data", array);
							out.print(objectall);
							out.flush();
							out.close();
						}
						
					}else if(transaction.equals("grantee")){
							granteelist=dao.granteelist(name);
							if(granteelist.isEmpty()){
								JSONObject ob3=new JSONObject();
								ob3.put("empty", true);
								out.print(ob3);
								out.close();
							}else{
								for(Beanstransaction l: granteelist){
									JSONObject objc=new JSONObject();
									objc.put("household_id", l.getHousehold_id());
									objc.put("household_member_id", l.getHouseholdmember_id());
									objc.put("grantee", l.getName());
									objc.put("brgy", l.getBrgy());
									objc.put("municipal", l.getMunicipal());
									array.put(objc);
								}
								objectall.put("data1", array);
								out.print(objectall);
								out.flush();
								out.close();
							}
						
					}else if(transaction.equals("wife")){
						wifelist=dao.wifelist(name);
						if(wifelist.isEmpty()){
							JSONObject ob2=new JSONObject();
							ob2.put("empty", true);
							out.print(ob2);
							out.close();
						}else{
							for(Beanstransaction j: wifelist){
								JSONObject objcs=new JSONObject();
								objcs.put("household_id", j.getHousehold_id());
								objcs.put("household_member_id", j.getHouseholdmember_id());
								objcs.put("head_name", j.getName());
								objcs.put("wife", j.getHead_name());
								objcs.put("brgy", j.getBrgy());
								objcs.put("municipal", j.getMunicipal());
								array.put(objcs);
							}
							objectall.put("data2", array);
							out.print(objectall);
							out.flush();
							out.close();
						}
					}else if(transaction.equals("student")){
							studentlist=dao.studentlist(name);
							if(studentlist.isEmpty()){
								JSONObject ob2=new JSONObject();
								ob2.put("empty", true);
								out.print(ob2);
								out.close();
							}else{
								for(Beanstransaction k: studentlist){
									JSONObject objcts=new JSONObject();
									objcts.put("household_id", k.getHousehold_id());
									objcts.put("household_member_id", k.getHouseholdmember_id());
									objcts.put("head_name", k.getName());
									objcts.put("student", k.getHead_name());
									objcts.put("brgy", k.getBrgy());
									objcts.put("municipal", k.getMunicipal());
									array.put(objcts);
								}
								objectall.put("data3", array);
								out.print(objectall);
								out.flush();
								out.close();
							}
					}
					else if(transaction.equals("municipal")){
						String municipal=request.getParameter("municipal");
						String brgy=request.getParameter("brgy");
						if(municipal!=null && brgy!=null){
							municipal_list=dao.municipality_list(municipal, brgy, name);
							if(municipal_list.isEmpty()){
								JSONObject ob2=new JSONObject();
								ob2.put("empty", true);
								out.print(ob2);
								out.close();
							}else{
								for(Beanstransaction z: municipal_list){
									JSONObject jsonOb=new JSONObject();
									jsonOb.put("household_id", z.getHousehold_id());
									jsonOb.put("household_member_id", z.getHouseholdmember_id());
									jsonOb.put("head_name", z.getName());
									jsonOb.put("brgy", z.getBrgy());
									jsonOb.put("municipal", z.getMunicipal());
									array.put(jsonOb);
								}
								objectall.put("municipal_list", array);
								out.print(objectall);
								out.flush();
								out.close();
								System.out.println(objectall);
							}
						}else if(municipal!=null && brgy==null){
							municipal_list=dao.municipal_list1(municipal, name);
							if(municipal_list.isEmpty()){
								JSONObject ob2=new JSONObject();
								ob2.put("empty", true);
								out.print(ob2);
								out.close();
							}else{
								for(Beanstransaction x: municipal_list){
									JSONObject jsonObs=new JSONObject();
									jsonObs.put("household_id", x.getHousehold_id());
									jsonObs.put("household_member_id", x.getHouseholdmember_id());
									jsonObs.put("head_name", x.getName());
									jsonObs.put("brgy", x.getBrgy());
									jsonObs.put("municipal", x.getMunicipal());
									array.put(jsonObs);
								}
								objectall.put("municipal_list", array);
								out.print(objectall);
								out.flush();
								out.close();
							}
						}
						
					}else if(transaction.equals("brgy")){
						brgy_list=dao.brgay_list(name);
						if(brgy_list.isEmpty()){
							JSONObject ob2=new JSONObject();
							ob2.put("empty", true);
							out.print(ob2);
							out.close();
						}else{
							for(Beanstransaction y: brgy_list){
								JSONObject jsonObjs=new JSONObject();
								jsonObjs.put("household_id", y.getHousehold_id());
								jsonObjs.put("household_member_id", y.getHouseholdmember_id());
								jsonObjs.put("head_name", y.getName());
								jsonObjs.put("brgy", y.getBrgy());
								jsonObjs.put("municipal", y.getMunicipal());
								array.put(jsonObjs);
							}
							objectall.put("brgay_list", array);
							out.print(objectall);
							out.flush();
							out.close();
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
