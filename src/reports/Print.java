package reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
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
 * Servlet implementation class Print
 */
@WebServlet("/Print")
public class Print extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Print() {
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
				ArrayList<reportBean> list = new ArrayList<reportBean>();
				String total_release = "";
				ArrayList<reportBean> list2 = new ArrayList<reportBean>();
				String total_notrelease = "";
				String cash_total = "";
				int list_size = 0;
				int list2_size = 0;
				String reportVal = request.getParameter("reportVal");
				String dtp = "";
				String munp = "";
				
				try{
					
					if(reportVal.equalsIgnoreCase("1")){
						list = (ArrayList<reportBean>) session.getAttribute("releaseList");
						
						list_size = list.size();
						System.out.println("rsize:"+list_size);
					}
					else if(reportVal.equalsIgnoreCase("2")){
						list2 = (ArrayList<reportBean>) session.getAttribute("notReleaseList");
						
						list2_size = list2.size();
						System.out.println("rsize:"+list2_size);
					}
					
					cash_total = (String) session.getAttribute("cash_total");
					total_release = (String) session.getAttribute("total_release");
					total_notrelease = (String) session.getAttribute("total_notrelease");
					System.out.println("size1: "+list_size+" size2: "+list2_size +" reportVal:"+request.getParameter("reportVal"));
					int gsize = 0;
					int rsize = 0;
					int fgsize = 0;  
					int frsize = 0;
					int sub=0;
					for(reportBean l:list){
						
						sub = l.getSub();
						System.out.println("rsize:"+sub);
						munp = l.getMunicipality();
						dtp = l.getDate_coverage();
						if(sub == 0){
							gsize = 1;
							fgsize++;
						}
						
						if(sub != 0){
							rsize = 1;
							frsize++;
							System.out.println("rsize:"+rsize);
						}
						
						
					}
					System.out.println("frsize:"+frsize);
					
					request.setAttribute("fgsize", fgsize);
					request.setAttribute("frsize", frsize);
					request.setAttribute("gsize", gsize);
					request.setAttribute("rsize", rsize);
					request.setAttribute("munp", munp);
					request.setAttribute("dtp", dtp);
					request.setAttribute("size1", list_size);
					request.setAttribute("size2", list2_size);
					request.setAttribute("cash_total", cash_total);
					request.setAttribute("data", list);
					request.setAttribute("total_release", total_release);
					request.setAttribute("data2", list2);
					request.setAttribute("total_notrelease", total_notrelease);
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/reports/print2.jsp");
					rd.forward(request, response);
					
				}
				catch(Exception ex){
					System.out.println(ex);
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
				String val = request.getParameter("val");
				String search = request.getParameter("search");
				ArrayList<String> list = new ArrayList<String>();
				//ArrayList<String> plist = new ArrayList<String>();
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				
				try{
					reportDAO dao = new reportDAO();
					
					if(val.equalsIgnoreCase("household")){
						list = dao.getAllHouseholdID();
					}
					else{
						list = dao.getAllPhilhealthID();
					}
					
					obj.put("data", list);
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
