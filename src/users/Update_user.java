package users;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.transactionBean;

/**
 * Servlet implementation class Update_user
 */
@WebServlet("/Update_user")
public class Update_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update_user() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		if(session==null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<transactionBean> plList = new ArrayList<transactionBean>();
				ArrayList<transactionBean> mlist = new ArrayList<transactionBean>();
				//System.out.println("in sa account settings...");
				try{
					/*String id = request.getParameter("uniqueID");
					String mun = request.getParameter("mun");*/
					UserDAO dao = new UserDAO();
					//dao.updateMunUser(request.getParameter("pl_fname"),request.getParameter("pl_lname"));
					//plList = dao.getGOData((String) session.getAttribute("pl_fname"),(String) session.getAttribute("pl_lname"),(String) session.getAttribute("real_mun_name"));
					plList = dao.getUserdataUpdate((String) session.getAttribute("uniqueID"),(String) session.getAttribute("real_mun_id"));
					mlist = dao.getmunicipal(false);
					System.out.println("First name -"+(String) session.getAttribute("ml_username"));
					for(transactionBean l: plList){
						request.setAttribute("m", l.getMun_id());
						request.setAttribute("f", l.getPl_fname());
						request.setAttribute("l", l.getPl_lname());
						
						if(l.getFingerprint()==null || l.getFingerprint().equals(null)){
							System.out.println("no fingerprint found...");
							request.setAttribute("fingerprint_exist", false);
						}
						else{
							request.setAttribute("fingerprint_exist", true);
						}
						
						if(l.getPhoto_head()==null || l.getPhoto_head().equals(null)){
							request.setAttribute("photohead_exist", false);
						}
						else{
							request.setAttribute("photohead_exist", true);
						}
					}
					request.setAttribute("municipal_list", mlist);
					request.setAttribute("ID", (String) session.getAttribute("uniqueID"));
					request.setAttribute("user_role",(String)session.getAttribute("user_role"));
					request.setAttribute("user_list", plList);
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/user/viewprof_user.jsp");
					rd.forward(request, response);
					
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session  = request.getSession(false);
		if(session==null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				try{
					UserDAO dao = new UserDAO();
					Calendar calendar= Calendar.getInstance();
					DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
					SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
					
					String fname = request.getParameter("m_fname");
					String lname = request.getParameter("m_lname");
					String gender = request.getParameter("m_gender");
					String email = request.getParameter("m_email");
					String contact = request.getParameter("m_contact");
					String mun_id =request.getParameter("mun_id");
					String user_role =request.getParameter("update_user_role");
					
					String real_fname = request.getParameter("real_fname");
					String real_lname = request.getParameter("real_lname");
					String real_gender = request.getParameter("real_gender");
					String real_email = request.getParameter("real_email");
					String real_contact = request.getParameter("real_contact");
					String real_mun_id = request.getParameter("real_mun_name");
					
					String id = request.getParameter("uniqueID");
					
					/*String rfname = dao.getFname(user_role,id);
					String rlname = dao.getLname(user_role,id);*/
					/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
					String logs_fname = dao.getUser2Fname(1);
					String logs_lname = dao.getUser2Lname(1);
					
					if(real_fname.equals(fname) && real_lname.equals(lname) && real_mun_id.equals(mun_id) && real_gender.equalsIgnoreCase(gender) && real_email.equals(email) && real_contact.equals(contact)){
						/*use this condition for updating without changing any data.. in short 
						 * iwas disgrasya.. whahahahah kabuang ni john og Argie.
						 * */
						
					}
					else{
						String date = format.format(calendar.getTime());
						String mun_value = dao.getMunValue(mun_id);
						String rmun = dao.getMunValue(real_mun_id);
						String time = timeInstance.format(Calendar.getInstance().getTime());
						if(real_fname.equals(fname) && real_lname.equals(lname) && real_mun_id.equals(mun_id)){
							
							dao.update_user(fname,lname,gender,email,contact,id,user_role,Integer.parseInt(real_mun_id), Integer.parseInt(mun_id));
							if(!gender.equalsIgnoreCase(real_gender)){
								String updated_realGender = "Male";
								String updated_gender = "Male";
								if(real_gender.equalsIgnoreCase("f")){
									updated_realGender = "Female";
								}
								if(updated_gender.equalsIgnoreCase("f")){
									updated_gender = "Female";
								}
								if(Integer.parseInt(user_role)==2){
									dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==4){
									dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==5){
									dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==6){
									dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==7){
									dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by "+logs_fname +" "+logs_lname);
								}
								/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+lname+", "+fname+" change its gender from "+
										updated_realGender+" to "+updated_gender+" by Provincial Link");*/
							}
							if(!contact.equals(real_contact)){
								if(Integer.parseInt(user_role)==2){
									dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
											real_contact+" to "+contact+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==4){
									dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
											real_contact+" to "+contact+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==5){
									dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
											real_contact+" to "+contact+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==6){
									dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
											real_contact+" to "+contact+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==7){
									dao.add_logs(false, date, time, "Social Worker user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
											real_contact+" to "+contact+" by "+logs_fname +" "+logs_lname);
								}
								/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+lname+", "+fname+" change its contact from "+
										real_contact+" to "+contact+" by Provincial Link");*/
							}
							if(!email.equals(real_email)){
								if(Integer.parseInt(user_role)==2){
									dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
											real_email+" to "+email+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==4){
									dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
											real_email+" to "+email+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==5){
									dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
											real_email+" to "+email+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==6){
									dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
											real_email+" to "+email+" by "+logs_fname +" "+logs_lname);
								}
								else if(Integer.parseInt(user_role)==7){
									dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
											real_email+" to "+email+" by "+logs_fname +" "+logs_lname);
								}
								/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+lname+", "+fname+" change its email from "+
										real_email+" to "+email+" by Provincial Link");*/
							}
								
							
						}
						else{
							/*int checkIfExist = dao.testIfExist(false, "select * from user_tbl where user_id = 7 and mun_id = '"+mun_id+"' and fname = '"+fname+"' and lname = '"+lname+"' ");
							if(checkIfExist > 0){
								System.out.println("checkif exist is greater than 0");
								session.setAttribute("pl_fname", real_fname);
								session.setAttribute("pl_lname", real_lname);
								session.setAttribute("real_mun_name",real_mun_id);
								request.setAttribute("unableToEdit", true);
								
							}
							else{*/
								System.out.println("check if exist == 0");
								dao.update_user(fname,lname,gender,email,contact,id,user_role,Integer.parseInt(real_mun_id), Integer.parseInt(mun_id));
								
								
								
								int lname_changeController = 0;
								int fname_changeController = 0;
								String updated_lname = real_lname;
								String updated_fname = real_fname;
								
								if(!lname.equals(real_lname)){
									if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
												real_lname+" to "+lname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==4){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
												real_lname+" to "+lname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==5){
										dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
												real_lname+" to "+lname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==6){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
												real_lname+" to "+lname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Social Worker user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
												real_lname+" to "+lname+" by "+logs_fname+" "+logs_lname);
									}
									/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+real_lname+", "+real_fname+" change its lastname from "+
											real_lname+" to "+lname+" by Provincial Link");*/
									lname_changeController++;
								}
								if(!fname.equals(real_fname)){
									if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
												real_fname+" to "+fname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==4){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
												real_fname+" to "+fname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==5){
										dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
												real_fname+" to "+fname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==6){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
												real_fname+" to "+fname+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Social Worker user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
												real_fname+" to "+fname+" by "+logs_fname+" "+logs_lname);
									}/*
									dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+real_lname+", "+real_fname+" change its firstname from "+
											real_fname+" to "+fname+" by Provincial Link");*/
									fname_changeController++;
								}
								
								if(lname_changeController>0){
									updated_lname = lname;
								}
								if(fname_changeController>0){
									updated_fname = fname;
								}
								
								
								if(!mun_id.equals(real_mun_id)){
									if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
												rmun+" to "+mun_value+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==4){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
												rmun+" to "+mun_value+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==5){
										dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
												rmun+" to "+mun_value+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==6){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
												rmun+" to "+mun_value+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
												rmun+" to "+mun_value+" by "+logs_fname+" "+logs_lname);
									}
									/*
									dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its municipal from "+
											rmun+" to "+mun_value+" by Provincial Link");*/
								}
								if(!gender.equalsIgnoreCase(real_gender)){
									String updated_realGender = "Male";
									String updated_gender = "Male";
									if(real_gender.equalsIgnoreCase("f")){
										updated_realGender = "Female";
									}
									if(gender.equalsIgnoreCase("f")){
										updated_gender = "Female";
									}
									if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Municipal Link user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
												updated_realGender+" to "+updated_gender+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==4){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
												updated_realGender+" to "+updated_gender+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==5){
										dao.add_logs(false, date, time, "verifier user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
												updated_realGender+" to "+updated_gender+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==6){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
												updated_realGender+" to "+updated_gender+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
												updated_realGender+" to "+updated_gender+" by "+logs_fname+" "+logs_lname);
									}
									
									/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its gender from "+
											updated_realGender+" to "+updated_gender+" by Provincial Link");*/
								}
								if(!contact.equals(real_contact)){
									if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
												real_contact+" to "+contact+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==4){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
												real_contact+" to "+contact+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==5){
										dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
												real_contact+" to "+contact+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==6){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
												real_contact+" to "+contact+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==7){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
												real_contact+" to "+contact+" by "+logs_fname+" "+logs_lname);
									}
									
									/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its contact from "+
											real_contact+" to "+contact+" by Provincial Link");*/
								}
								if(!email.equals(real_email)){
									if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
												real_email+" to "+email+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Book Keeper user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
												real_email+" to "+email+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Verifier user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
												real_email+" to "+email+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Grievance Officer user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
												real_email+" to "+email+" by "+logs_fname+" "+logs_lname);
									}
									else if(Integer.parseInt(user_role)==2){
										dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
												real_email+" to "+email+" by "+logs_fname+" "+logs_lname);
									}
									/*dao.add_logs(false, date, time, "Social worker user from "+mun_value+" named "+updated_lname+", "+updated_fname+" change its email from "+
											real_email+" to "+email+" by Provincial Link");*/
								}
								/*dsession.setAttribute("ml_ID", request.getAttribute("m_ID"));
								session.setAttribute("pl_fname", fname);
								session.setAttribute("pl_lname", lname);
								session.setAttribute("ml_username", real_username);
								session.setAttribute("ml_gender", gender);
								session.setAttribute("ml_email", email);
								session.setAttribute("ml_contact", contact);*/
								
								
							//} // END block of else condition.
						}
					}
					String dfname = dao.getFname(Integer.parseInt(user_role), Integer.parseInt(id));
					String loginUserId = (String)session.getAttribute("user_id");
					if(loginUserId.equals(id)){
						session.setAttribute("dfname", dfname);
					}
					session.setAttribute("real_mun_id",mun_id);
					session.setAttribute("uniqueID", id);
					session.setAttribute("user_role", user_role);
					doGet(request, response);
					
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}

}
