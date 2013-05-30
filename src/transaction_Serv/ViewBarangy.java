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

import org.json.JSONArray;
import org.json.JSONObject;

import transaction_DAO.Transaction_DAO;

import beans.Brgy;

/**
 * Servlet implementation class ViewBarangy
 */
@WebServlet("/ViewBarangy")
public class ViewBarangy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBarangy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null ViewBarangy servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				ArrayList<Brgy>bry_list=new ArrayList<Brgy>();
				try{
					PrintWriter out=response.getWriter();
					String municipal=request.getParameter("municipal");
					Transaction_DAO dao=new Transaction_DAO();
					//municipal = getmun(municipal);
					bry_list=dao.brgy_list(municipal);
					JSONObject object=new JSONObject();
					JSONArray array=new JSONArray();
					for(Brgy i:bry_list){
						JSONObject obj=new JSONObject();
						obj.put("brgy_id", i.getBrgy_id());
						obj.put("barangay", i.getBarangay());
						array.put(obj);
					}
					object.put("data", array);
					out.print(object);
					out.flush();
					out.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	String getmun(String mun){
		String []temp = mun.split(" ");
		if(temp.length>=2){
			return temp[0];
		}
		return mun;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
