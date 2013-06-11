package logs;

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


import DAO.BaseDAO;
import bean.LogsBean;

/**
 * Servlet implementation class ViewLogs
 */
@WebServlet("/ViewLogs")
public class ViewLogs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewLogs() {
        super();
        // TODO Auto-generated constructor stub
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
				System.out.println("username is null View_RecordTransaction servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<LogsBean> logs_list = new ArrayList<LogsBean>();
				try {
					BaseDAO dao = new BaseDAO();
					logs_list = dao.getLogs_list(false);
					request.setAttribute("search", "");
					request.setAttribute("logs_list", logs_list);
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/transactions/vew_logs.jsp");
					rd.forward(request, response);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_RecordTransaction servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				try{
					
					String sdate = request.getParameter("sdate");
					String edate = request.getParameter("edate");
					String search = request.getParameter("search");
					
					System.out.println("sdate: "+sdate+" edate: "+edate);
					
					String sdateArr[] = sdate.split("/");
					String edateArr[] = edate.split("/");
					
					int sday = Integer.parseInt(sdateArr[1]);
					int smonth = Integer.parseInt(sdateArr[0]);
					int syear = Integer.parseInt(sdateArr[2]);
					int eday = Integer.parseInt(edateArr[1]);
					int emonth = Integer.parseInt(edateArr[0]);
					int eyear = Integer.parseInt(edateArr[2]);
					ArrayList<LogsBean> logs_list = new ArrayList<LogsBean>();
					ArrayList<LogsBean> list = new ArrayList<LogsBean>();
					ArrayList<LogsBean> finalList = new ArrayList<LogsBean>();
					
					BaseDAO dao = new BaseDAO();
					logs_list = dao.getLogs_list(false,search);
					
					for(LogsBean l: logs_list){
						String date[] = l.getDate().split("/");
						int day = Integer.parseInt(date[1]);
						int month = Integer.parseInt(date[0]);
						int year = Integer.parseInt(date[2]);
						
						LogsBean bean = null;
						
						//------ get data that has a date receive greater than sa start date given....
						
						if(year >= syear){
							if(year==syear){
								if(month>=smonth){
									if(month==smonth){
										if(day>=sday){
											System.out.println("pasok ka na sa pinoy records.");
											bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
											list.add(bean);
										}
									}
									else{
										System.out.println("pasok ka na sa pinoy records.");
										bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
										list.add(bean);
									}
								}
							}//end of year==syear
							else{
								System.out.println("pasok ka na sa PGT pwede ka na i'compare sa edate");
								bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
								list.add(bean);
							}
						}
						
					}
					
					//------ get data that has a date receive less than sa end date given....
					
					for(LogsBean l:list){
						
						String date[] = l.getDate().split("/");
						int day = Integer.parseInt(date[1]);
						int month = Integer.parseInt(date[0]);
						int year = Integer.parseInt(date[2]);
					
						LogsBean bean = null;
						
						if(year <= eyear){
							if(year == eyear){
								
								if(month <= emonth){
									if(month == emonth){
										if(day <= eday){
											//add na ini
											System.out.println("add na ini");
											bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
											finalList.add(bean);
										}
									}
									else{
										//add na ini
										System.out.println("add na ini");
										bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
										finalList.add(bean);
									}
								}
								
							}
							else{
								//add na ini
								System.out.println("add na ini");
								bean = new LogsBean(l.getDate(),l.getTime(),l.getLog_message());
								finalList.add(bean);
							}
						}
						
					}
					
					
					obj.put("data", finalList);
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
