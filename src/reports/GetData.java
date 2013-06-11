package reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import DAO.reportDAO;
import bean.reportBean;

/**
 * Servlet implementation class GetData
 */
@WebServlet("/GetData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
				String sdate = request.getParameter("sdate");
				String edate = request.getParameter("edate");
				String val = request.getParameter("val");
				int param_op = Integer.parseInt(request.getParameter("param_op"));
				ArrayList<reportBean> ListAll = new ArrayList<reportBean>();
				ArrayList<reportBean> releaseList = new ArrayList<reportBean>();
				ArrayList<reportBean> releaseList2 = new ArrayList<reportBean>();
				ArrayList<reportBean> notReleaseList = new ArrayList<reportBean>();
				ArrayList<reportBean> notReleaseList2 = new ArrayList<reportBean>();
				System.out.println("param_op:"+param_op);
				float total_cash = 0;
				float total_release = 0;;
				float total_notrelease = 0;
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				boolean emp = false;
				boolean emp1 = false;
				boolean shortcut = false;
				boolean shortcut1 = false;
				boolean shortcut2 = false;
				boolean shortcut3 = false;
				boolean shortcut4 = false;
				boolean shortcut5 = false;
				boolean shortcut6 = false;
				boolean shortcut7 = false;
				String mun_name = "";
				String brgy_name = "";
				String column = "";
				String t = "";
				String search_hh = "";
				if((edate == "" && sdate == "")){
					emp = true;
				}
				
				
				
				try{
					reportDAO dao = new reportDAO();
					String hh_set = request.getParameter("hh_set");
					if(val.equalsIgnoreCase("household")){
						String trans = request.getParameter("transaction");
						String search = request.getParameter("search");
						search_hh = search;
						if(search == null || search == " " || search == ""){
							emp1 = true;
						}
						
						if(hh_set.equalsIgnoreCase("") || hh_set == null){
							if(emp == false && emp1 == true  && trans.equalsIgnoreCase("Transactions")){
								System.out.println("dates is not empty while search is empty");
								System.out.println("sdate: "+sdate+" edate: "+edate+" search: "+search);
								shortcut = true;
							}
							else if(emp == false && emp1 == false  && trans.equalsIgnoreCase("Transactions")){
								System.out.println("both input have values");
								System.out.println("sdate: "+sdate+" edate: "+edate+" search: "+search);
								shortcut1 = true;
								column = "household_id";
							}
							else if(emp == false && emp1 == true  && trans != "Transactions"){
								System.out.println("in sa emp = false && emp1 == true && trans not empty");
								shortcut6 = true;
								t = trans;
							}
							else if(emp == false && emp1 == false  && trans != "Transactions"){
								System.out.println("in sa not empty lahat na fields");
								shortcut7 = true;
								column = "household_id";
								t = trans;
							}
							else if(emp == true && emp1 == true  && trans.equalsIgnoreCase("Transactions")){
								System.out.println("both input dont have values");
								System.out.println("sdate: "+sdate+" edate: "+edate+" search: "+search);
								
							}
							else if(emp == true && emp1 == false  && trans.equalsIgnoreCase("Transactions")){
								System.out.println("dates is empty while serach is not:"+trans);
								System.out.println("sdate: "+sdate+" edate: "+edate+" search: "+search);
								total_cash = dao.getTotalCash(search,"household_id");
								if(param_op == 1){
									releaseList = dao.getList1(search,"household_id",1);
									total_release = dao.getTotalRelease1(search,"household_id",1);
								}else{
									notReleaseList = dao.getList1(search,"household_id",0);
									total_notrelease = dao.getTotalRelease1(search,"household_id",0);
									System.out.println(releaseList.size());
									System.out.println(notReleaseList.size());
								}
							}
							else if(emp == true && emp1 == true && trans != "Transactions"){ 
								System.out.println("//codes for transaction la it may value..");
								total_cash = dao.getTotalCash(trans);
								if(param_op == 1){
									releaseList = dao.getList1(1,trans);
									total_release = dao.getTotalRelease6(1,trans);
								}else{
									notReleaseList = dao.getList1(0,trans);
									total_notrelease = dao.getTotalRelease6(0,trans);
									System.out.println("---finish---");
								}
							}
							else if(emp1 == false && trans != "Transactions" && emp == true){
								System.out.println("in sa naiiba");
								total_cash = dao.getTotalCash(trans,search,"household_id",2);
								if(param_op == 1){
									releaseList = dao.getDataByTransaction(trans,1,search,"household_id");
									total_release = dao.getTotalRelease5(trans,1,search,"household_id");
								}
								else{
									notReleaseList = dao.getDataByTransaction(trans,0,search,"household_id");
									total_notrelease = dao.getTotalRelease5(trans,0,search,"household_id");
								}
								
							}
							
						}
						else{
							String sql = "";
							if(!emp){//date
								String sdateArray[] = sdate.split("/");
								String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
								String edateArray[] = edate.split("/");
								String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
								
								sql += " and r.date_receive between '"+start_date+"' and '"+end_date+"' ";
							}
							if(!emp1){//household id
								sql += " and h.household_id = '"+search+"'";
							}
							if(trans != "Transactions"){
								sql += "  and r.month_and_year='"+trans+"' ";
							}
							
							total_cash = dao.getTotalCash(sql,0,hh_set);
							if(param_op == 1){
								releaseList = dao.getList1(sql,1,hh_set);
								total_release = dao.getTotalRelease2(sql,1,hh_set);
							}
							else{
								notReleaseList = dao.getList1(sql,0,hh_set);
								total_notrelease = dao.getTotalRelease2(sql,0,hh_set);
							}
							
						}
						
					}
					else if(val.equalsIgnoreCase("municipal")){
						System.out.println("in");
						String mun = request.getParameter("mun");
						String brgy = request.getParameter("brgy");
						String transaction = request.getParameter("transaction");
						
						System.out.println("mun:"+mun);
						System.out.println("brgy:"+brgy);
						System.out.println("--------------------"+transaction);
						System.out.println("emp:"+emp);
						if(hh_set.equalsIgnoreCase("") || hh_set == null){

								if(emp == true && mun.equalsIgnoreCase("Municipality") && brgy.equalsIgnoreCase("Barangay")  && transaction != "Transactions"){
									System.out.println("//codes for transaction la it may value..");
									total_cash = dao.getTotalCash(transaction,1);
									if(param_op == 1){
										releaseList = dao.getList1(1,transaction);
										total_release = dao.getTotalRelease6(1,transaction);
									}
									else{
										notReleaseList = dao.getList1(0,transaction);
										total_notrelease = dao.getTotalRelease6(0,transaction);
										System.out.println("---finish---");
									}
								}
								
								else if(emp == true && mun.equalsIgnoreCase("Municipality") && brgy.equalsIgnoreCase("Barangay")  && transaction.equalsIgnoreCase("Transactions")){
									System.out.println("1");
								}
								else if((emp == false && mun.equalsIgnoreCase("Municipality") && brgy.equalsIgnoreCase("Barangay")  && transaction.equalsIgnoreCase("Transactions")) || (emp == false && mun == null && brgy == null  && transaction == null)){
									shortcut = true;
									System.out.println("32");
														
								}
								else if(emp == false && mun != "Municipality" && brgy.equalsIgnoreCase("Barangay")  && transaction.equalsIgnoreCase("Transactions")){
									shortcut3 = true;
									mun_name = mun;
									System.out.println("--------------------------------------6");
								}
								
								else if(emp == true && mun != "Municipality" && brgy.equalsIgnoreCase("Barangay")  && transaction.equalsIgnoreCase("Transactions")){
									total_cash = dao.getTotalCash(mun,2);
									if(param_op == 1){
										releaseList = dao.getList1(mun,"municipality",1);
										total_release = dao.getTotalRelease2(mun,"municipality",1);
									}
									else{
										notReleaseList = dao.getList1(mun,"municipality",0);
										total_notrelease = dao.getTotalRelease2(mun,"municipality",0);
										System.out.println("in here 2");
									}
								}
								else if(emp == false && mun.equalsIgnoreCase("Municipality") && brgy.equalsIgnoreCase("Barangay")  && transaction != "Transactions"){
									//code for reports with datepicker and transaction value
									shortcut6 = true;
									t = transaction;
								}
								
								else if(emp == false && mun != "Municipality" && brgy != "Barangay"  && transaction.equalsIgnoreCase("Transactions")){
									shortcut2 = true;
									mun_name = mun;
									brgy_name = brgy;
									System.out.println("4"+mun_name+" "+brgy_name);
								}
								else if(emp == true && mun != "Municipality" && brgy != "Barangay"  && transaction.equalsIgnoreCase("Transactions")){
									total_cash = dao.getTotalCash(mun,brgy,2);
									if(param_op == 1){
										releaseList = dao.getList2(mun,brgy,1);
										total_release = dao.getTotalRelease4(mun,brgy,1);
									}
									else{
										notReleaseList = dao.getList2(mun,brgy,0);
										total_notrelease = dao.getTotalRelease4(mun,brgy,0);
										System.out.println("5");
									}
								}
								
								
								else if(emp == true && mun != "Municipality" && brgy.equalsIgnoreCase("Barangay") && transaction != "Transactions"){
									System.out.println("--2");
									total_cash = dao.getTotalCash(mun,transaction,1);
									if(param_op == 1){
										releaseList = dao.getList1(mun,"municipality",1,transaction);
										total_release = dao.getTotalRelease6(mun,"municipality",1,transaction);
									}
									else{
										System.out.println("in sa notrelease..");
										notReleaseList = dao.getList1(mun,"municipality",0,transaction);
										total_notrelease = dao.getTotalRelease6(mun,"municipality",0,transaction);
									}
									
								}
								else if(emp == true &&  mun != "Municipality" && brgy != "Barangay" && transaction != "Transactions"){
									total_cash = dao.getTotalCash(mun,brgy,transaction,1);
									if(param_op == 1){
										releaseList = dao.getList2(mun,brgy,1,transaction);
										total_release = dao.getTotalRelease4(mun,brgy,1,transaction);
									}
									else{
										notReleaseList = dao.getList2(mun,brgy,0,transaction);
										total_notrelease = dao.getTotalRelease4(mun,brgy,0,transaction);
										System.out.println("51");
									}
									
								}
								
								
								if(emp == false && mun != "Municipality" && brgy.equalsIgnoreCase("Barangay") && transaction != "Transactions"){
									//-- dito ung merong value ung transaction at date picker at municipality but no brgy
									shortcut5 = true;
									mun_name = mun;
									t = transaction;
								}
								else if(emp == false &&  mun != "Municipality" && brgy != "Barangay" && transaction != "Transactions"){
									System.out.println("//-- dito ung merong value ung lahat..");
									shortcut4 = true;
									mun_name = mun;
									brgy_name = brgy;
									t = transaction;
								}
						
						}
						else{
							String sql = "";
							int in = 0;
							if(!emp){//date
								String sdateArray[] = sdate.split("/");
								String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
								String edateArray[] = edate.split("/");
								String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
								
								sql += " and r.date_receive between '"+start_date+"' and '"+end_date+"' ";
							}
							if(mun != "Municipalitty"){
								sql += " and h.municipality = "+mun+" ";
							}
							if(brgy!="Barangay"){//household id
								sql += "  and h.barangay ="+brgy+"  ";
							}
							if(transaction != "Transactions"){
								sql += "  and r.month_and_year='"+transaction+"' ";
							}
							
							total_cash = dao.getTotalCash(sql,0,hh_set);
							if(param_op == 1){
								releaseList = dao.getList1(sql,1,hh_set);
								total_release = dao.getTotalRelease2(sql,1,hh_set);
							}
							else{
								notReleaseList = dao.getList1(sql,0,hh_set);
								total_notrelease = dao.getTotalRelease2(sql,0,hh_set);
							}
							
						}
					}
					else{
						System.out.println("not in the choices");
					}
					
					
					if(shortcut == true){
						
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(0);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(start_date, end_date);
							total_release = dao.getTotalRelease(start_date, end_date);}
						
					}
					
					if(shortcut6){
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(0,t);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(t,start_date, end_date,1);
							total_release = dao.getTotalRelease(t,start_date, end_date,1);
							//total_cash = dao.getTotalCashDate(t, start_date, end_date); //--need not release amount before generating this sum T_T
						}
					
					}
					
					if(shortcut1 == true){
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(column,search_hh,0);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(column,search_hh,start_date, end_date,1);
							total_release = dao.getTotalRelease(column,search_hh,start_date, end_date,1);
						}
					}
					
					if(shortcut2 == true){
						
						System.out.println("in shortcut2");
						if(param_op == 2){
							notReleaseList2 =  dao.getAllList1(mun_name,brgy_name,0);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(mun_name,brgy_name,start_date, end_date,2);
							total_release = dao.getTotalRelease(mun_name,brgy_name,start_date, end_date,2);
						}
						
					}
		
					if(shortcut3 == true){
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(mun_name,0);
						}
						else{// finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(mun_name,start_date, end_date,2);
							total_release = dao.getTotalRelease(mun_name,start_date, end_date,2);
						}
					}
					
					if(shortcut4){

						if(param_op == 2){
							notReleaseList2 =  dao.getAllList1(mun_name,brgy_name,0,t);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(mun_name,brgy_name,t,start_date, end_date,1);
							total_release = dao.getTotalRelease(mun_name,brgy_name,t,start_date, end_date,1);
						}
					}
					
					if(shortcut5){
						System.out.println("shortcut5:totalrelease:"+total_release+" notrelease:"+total_notrelease);
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(mun_name,0,t);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(mun_name,t,start_date, end_date);
							total_release = dao.getTotalRelease(mun_name,t,start_date, end_date);
						}
					}
					
					if(shortcut7){
						if(param_op == 2){
							notReleaseList2 = dao.getAllList(column,search_hh,0,t);
						}
						else{//finish
							String sdateArray[] = sdate.split("/");
							String start_date = sdateArray[2]+"-"+sdateArray[0]+"-"+sdateArray[1];
							String edateArray[] = edate.split("/");
							String end_date = edateArray[2]+"-"+edateArray[0]+"-"+edateArray[1];
							
							releaseList = dao.getReleaseList(column,search_hh,t,start_date, end_date,2);
							total_release = dao.getTotalRelease(column,search_hh,t,start_date, end_date,2);
						}
					}
					
					
				/*	System.out.println("size: "+trans.size());
					for(String l: trans){
						System.out.println("date coverage:"+l);
					}*/
					
					if(notReleaseList2.size() > 0 && param_op == 2){/*
						
						System.out.println("get: saving data at notRelease list: "+notReleaseList2.size());
					
								ArrayList<String> trans = new ArrayList<String>();
								
								for(reportBean l: releaseList){
									
									System.out.println("1: "+l.getDate_coverage());
									trans.add(l.getDate_coverage());
									
								}
					
								System.out.println("trans size:"+trans.size());
								
								if(trans.size() > 0){
									HashSet<String> set = new  HashSet<String>(trans);
									trans.clear();
									trans.addAll(set);
						
									for(String y:trans){
										for(reportBean x: notReleaseList2){
											reportBean bean = null;
											if(y.equalsIgnoreCase(x.getDate_coverage())){
												bean = new reportBean(x.getHousehold_id(),x.getHead_name(),x.getBday(),x.getBrgy(),x.getMunicipality(),x.getDate_coverage(),x.getDate_receive(),x.getTime_receive(),x.getAmount_receive(),x.getPhilhealth_id(),x.getSub());
												notReleaseList.add(bean);
												total_notrelease = total_notrelease + Float.parseFloat(x.getAmount_receive());
												System.out.println("added");
											}
											
										}
									}
								}
								else{
									
									String sdateArr1[] = sdate.split("/");
									String edateArr1[] = edate.split("/");
									String syear1 = sdateArr1[2];
									String eyear1 = edateArr1[2];
									
									ListAll = dao.getAllList(1,syear1,eyear1);
									ArrayList<reportBean> list = new ArrayList<reportBean>();
									ArrayList<reportBean> finalnotList = new ArrayList<reportBean>();
									ArrayList<reportBean> listfinal = new ArrayList<reportBean>();
									float total_notreleasef = 0;
									for(reportBean m: notReleaseList2){
										
										System.out.println("2: "+m.getDate_coverage());
										for(reportBean c: ListAll){
											if(c.getDate_coverage().equalsIgnoreCase(m.getDate_coverage())){
												reportBean bean = null;
												
												bean = new reportBean(c.getHousehold_id(),c.getHead_name(),c.getBday(),c.getBrgy(),c.getMunicipality(),c.getDate_coverage(),c.getDate_receive(),c.getTime_receive(),c.getAmount_receive(),c.getPhilhealth_id(),c.getSub());
												
												finalnotList.add(bean);
												
												System.out.println("final size of list: "+finalnotList.size());
												
											}
		
											}
										}
									String sdateArr[] = sdate.split("/");
									String edateArr[] = edate.split("/");
									
									int sday = Integer.parseInt(sdateArr[1]);
									int smonth = Integer.parseInt(sdateArr[0]);
									int syear = Integer.parseInt(sdateArr[2]);
									int eday = Integer.parseInt(edateArr[1]);
									int emonth = Integer.parseInt(edateArr[0]);
									int eyear = Integer.parseInt(edateArr[2]);
									
									System.out.println("syear: month: "+smonth+" day: "+sday+" year: "+syear);
									System.out.println("eyear: month: "+emonth+" day: "+eday+" year: "+eyear);
									
									
									
									
									for(reportBean l: finalnotList){
										String date[] = l.getDate_receive().split("/");
										int day = Integer.parseInt(date[1]);
										int month = Integer.parseInt(date[0]);
										int year = Integer.parseInt(date[2]);
										//12/25/1991
										//01/05/2008 // waray ka anai..
										
										//12/24/1991
										
										reportBean bean = null;
										
										//------ get data that has a date receive greater than sa start date given....
										
										if(year >= syear){
											if(year==syear){
												if(month>=smonth){
													if(month==smonth){
														if(day>=sday){
															System.out.println("pasok ka na sa pinoy records.");
															bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
															list.add(bean);
														}
													}
													else{
														System.out.println("pasok ka na sa pinoy records.");
														bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
														list.add(bean);
													}
												}
											}//end of year==syear
											else{
												System.out.println("pasok ka na sa PGT pwede ka na i'compare sa edate");
												bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
												list.add(bean);
											}
										}
										
									}
									
									//------ get data that has a date receive less than sa end date given....
									
									for(reportBean l:list){
										
										String date[] = l.getDate_receive().split("/");
										int day = Integer.parseInt(date[1]);
										int month = Integer.parseInt(date[0]);
										int year = Integer.parseInt(date[2]);
									
										reportBean bean = null;
										
										if(year <= eyear){
											if(year == eyear){
												
												if(month <= emonth){
													if(month == emonth){
														if(day <= eday){
															//add na ini
															System.out.println("add na ini");
															bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
															listfinal.add(bean);
															//total_notreleasef = total_release + Float.parseFloat(l.getAmount_receive());
														}
													}
													else{
														//add na ini
														System.out.println("add na ini");
														bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
														listfinal.add(bean);
														//total_notreleasef = total_release + Float.parseFloat(l.getAmount_receive());
													}
												}
												
											}
											else{
												//add na ini
												System.out.println("add na ini");
												bean = new reportBean(l.getHousehold_id(),l.getHead_name(),l.getBday(),l.getBrgy(),l.getMunicipality(),l.getDate_coverage(),l.getDate_receive(),l.getTime_receive(),l.getAmount_receive(),l.getPhilhealth_id(),l.getSub());
												listfinal.add(bean);
												//total_notreleasef = total_release + Float.parseFloat(l.getAmount_receive());
											}
										}
										
									}
									
									for(reportBean f:listfinal){
										
										for(reportBean d: notReleaseList2){
											reportBean bean = null;
											if(f.getDate_coverage().equalsIgnoreCase(d.getDate_coverage())){
												bean = new reportBean(d.getHousehold_id(),d.getHead_name(),d.getBday(),d.getBrgy(),d.getMunicipality(),d.getDate_coverage(),d.getDate_receive(),d.getTime_receive(),d.getAmount_receive(),d.getPhilhealth_id(),d.getSub());
												notReleaseList.add(bean);
												total_notrelease = total_notrelease + Float.parseFloat(d.getAmount_receive());
												System.out.println("added"+notReleaseList.size());
											}
											
										}
										
									}
									
								}
								
					*/}
							
					System.out.println("release: "+total_release+" not: "+total_notrelease);
					
					System.out.println("getdata: "+releaseList.size());
					
					session.setAttribute("param_op", param_op);
					if(param_op == 1){
						obj.put("cash_total", NumberFormat.getNumberInstance(Locale.US).format(total_cash));
						obj.put("data", releaseList);
						obj.put("total_release", NumberFormat.getNumberInstance(Locale.US).format(total_release));
						session.setAttribute("releaseList", releaseList );
						session.setAttribute("total_release", NumberFormat.getNumberInstance(Locale.US).format(total_release) );
						session.setAttribute("total_notrelease1", total_release);
						notReleaseList.clear();
						session.setAttribute("notReleaseList", notReleaseList );
						session.setAttribute("total_notrelease", 0.00 );
						session.setAttribute("cash_total", NumberFormat.getNumberInstance(Locale.US).format(total_cash));
					}
					else if(param_op == 2){
						System.out.println(param_op+"getdata2: "+notReleaseList.size());
						obj.put("cash_total", NumberFormat.getNumberInstance(Locale.US).format(total_cash));
						obj.put("data2", notReleaseList);
						obj.put("total_notrelease", NumberFormat.getNumberInstance(Locale.US).format(total_notrelease));
						session.setAttribute("notReleaseList", notReleaseList );
						session.setAttribute("total_notrelease", NumberFormat.getNumberInstance(Locale.US).format(total_notrelease) );
						releaseList.clear();
						session.setAttribute("releaseList",releaseList  );
						session.setAttribute("total_release", 0.00 );
						session.setAttribute("total_notrelease1", 0.00);
						session.setAttribute("cash_total", NumberFormat.getNumberInstance(Locale.US).format(total_cash));
					}
					
					out.print(obj);
					out.flush();
					out.close();
					
				}
				catch(Exception ex){
					System.out.println(ex);
				}
				out.print(obj);
				out.close();
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

}
