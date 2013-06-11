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

import DAO.reportDAO;

/**
 * Servlet implementation class ChooseReport
 */
@WebServlet("/ChooseReport")
public class ChooseReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseReport() {
        super();
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
				try{
					
					String param = request.getParameter("op");
					String op = "";
					if(param.equalsIgnoreCase("1")){
						System.out.println("1");
						op = "1";
					}
					else if(param.equalsIgnoreCase("2")){
						System.out.println("2");
						System.out.println("param:"+param);
						op = "2";
					}
				
					request.setAttribute("parami", op);
					request.setAttribute("sample", "sampleeeeeeeeeeeeeeeeee1!!!!!!!!");
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/reports/reports_home.jsp");
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
				ArrayList<String> hhsetgroupList = new ArrayList<String>();
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				
				try{
					reportDAO dao = new reportDAO();
					
					hhsetgroupList = dao.getHHSetList();
						
						obj.put("data", hhsetgroupList);
						out.print(obj);
						out.flush();
						out.close();
					
					
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
		}

	}

}
