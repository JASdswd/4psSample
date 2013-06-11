package test;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.reportBean;

import DAO.reportDAO;

/**
 * Servlet implementation class TotalNoData
 */
@WebServlet("/TotalNoData")
public class TotalNoData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TotalNoData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			reportDAO dao = new reportDAO();
			ArrayList<reportBean> mlist = new ArrayList<reportBean>();
			
			mlist = dao.getAllMun();
			
			request.setAttribute("mlist", mlist);
			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/isolateDB.jsp");
			rd.forward(request, response);
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//..ayawlapaglabti ini..:P
		
		System.out.println("in sa post");
		
		String mun = request.getParameter("municipal");
		int mess =0;
		try{
			
			//fingerprintBean bean = new fingerprintBean();
			ArrayList<fingerprintBean> flist = new ArrayList<fingerprintBean>();
			reportDAO dao = new reportDAO();
			
			flist = dao.getFingerprintDatas(mun);
			System.out.println(flist.size());;
			for(fingerprintBean l:flist){
				System.out.println("ma in na sa for each");
				dao.insertFingerpritns(false,l.getHouseold_id(),l.getFingerprint(),l.getDate_recorded(),l.getTime_recorded());
				mess = 1;
				System.out.println("ma out na sa foreach");
			}
			
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		if(mess == 1){
			request.setAttribute("message","Gathering Data Completed");
			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/isolateDB.jsp");
			rd.forward(request, response);
		}
		else{
			request.setAttribute("message","Gathering Data Failed");
			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/isolateDB.jsp");
			rd.forward(request, response);
		}

		
	}

}
