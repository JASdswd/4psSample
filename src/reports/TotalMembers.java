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
import bean.reportBean2;

/**
 * Servlet implementation class TotalMembers
 */
@WebServlet("/TotalMembers")
public class TotalMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TotalMembers() {
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
				System.out.println("username is null PdfParse servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				ArrayList<reportBean> list = new ArrayList<reportBean>();
				ArrayList<reportBean2> munList2 = new ArrayList<reportBean2>();
				ArrayList<reportBean> munList = new ArrayList<reportBean>();
				ArrayList<reportBean> finalList = new ArrayList<reportBean>();
				ArrayList<reportBean> finalList2 = new ArrayList<reportBean>();
				ArrayList<reportBean2> totalMun = new ArrayList<reportBean2>();
				ArrayList<reportBean> brgyList = new ArrayList<reportBean>();
				int total_mun = 0;
				boolean found = false;
				boolean found1 = false;
				int munCount = 0;
				int total_member = 0;
				//int total_brgy = 0;
				
				try{
					reportDAO dao = new reportDAO();
					reportBean bean = null;
					list = dao.getTotalMembers();
					munList = dao.getAllMun();
					brgyList = dao.getAllBrgy();
					totalMun = dao.getTotalMun();
					munCount = dao.getMunCount();
					total_member = dao.getHouseholdCount();
					munList2 = dao.getMunBrgy();
					
					for(reportBean m:munList){
						for(reportBean b:brgyList){
							
							if(b.getMun_id() == m.getMun_id()){
								for(reportBean l:list){
									//found = false;
									if((b.getBrgy_id() == l.getBrgy_id() && m.getMun_id() == l.getMun_id())){
										total_mun = total_mun + l.getTotal_mun();
										bean = new reportBean(m.getMun_id(),l.getMun_name().toLowerCase(),total_mun,l.getBrgy_name().toLowerCase(),l.getTotal_brgy());
										finalList.add(bean);
										total_mun = 0;
										found = true;
									}
								}
								
								if(found == false){
									bean = new reportBean(m.getMun_id(),m.getMun_name().toLowerCase(),0,b.getBrgy_name().toLowerCase(),0);
									finalList.add(bean);
								}
								found = false;
							}
							
						}
					}
					
					for(reportBean x:finalList){
						System.out.println(x.getMunicipality().toLowerCase()+" "+x.getBrgy_name().toLowerCase());
					}
					for(reportBean2 m:munList2){
						for(reportBean2 b:totalMun){
							if(b.getMun_id() == m.getMun_id()){
								bean = new reportBean(m.getMun_id(),m.getMunicipality().toLowerCase(),b.getTotal_mun(),m.getTotal_mun());
								finalList2.add(bean);
								found1 = true;
							}
							
						}
						
						if(found1 == false){
							bean = new reportBean(m.getMun_id(),m.getMunicipality().toLowerCase(),0,m.getTotal_mun());
							finalList2.add(bean);
							//System.out.println("row:"+row++);
						}
						found1 = false;
					}
					
					
				}
				catch(Exception ex){
					System.out.println(ex);
				}
				
				request.setAttribute("muntotal", munCount);
				request.setAttribute("householdtotal", total_member);
				request.setAttribute("list", finalList2);
				request.setAttribute("list2", finalList);
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/reports/totalMembers.jsp");
				rd.forward(request, response);
				
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
				ArrayList<String> transactionList = new ArrayList<String>();
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				
				try{
					reportDAO dao = new reportDAO();
					
					transactionList = dao.getTransaction();
						
						obj.put("data", transactionList);
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