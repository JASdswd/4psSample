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
import bean.reportBean;

/**
 * Servlet implementation class GetMunicipality
 */
@WebServlet("/GetMunicipality")
public class GetMunicipality extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMunicipality() {
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
				ArrayList<reportBean> munList = new ArrayList<reportBean>();
				ArrayList<reportBean> brgyList = new ArrayList<reportBean>();
				reportBean bean = null;
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				
				try{
					reportDAO dao = new reportDAO();
					
						munList = dao.getAllMun();
//						for (reportBean list : munList) {
//							System.out.println("municipal id: "+list.getMun_id());
//							System.out.println("municipal name: "+list.getMun_name());
//						}
						obj.put("data", munList);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//for reporting

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
				ArrayList<reportBean> munList = new ArrayList<reportBean>();
				ArrayList<reportBean> brgyList = new ArrayList<reportBean>();
				reportBean bean = null;
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				
				try{
					reportDAO dao2 = new reportDAO();
					ArrayList<String> tm = new ArrayList<String>();
					tm = dao2.getTeamsReg("fingerprint_tbl_temp");
					
					//--------------------------
					
						obj.put("data", tm);
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
