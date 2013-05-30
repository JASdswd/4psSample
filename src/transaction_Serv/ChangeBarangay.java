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

import org.json.JSONArray;
import org.json.JSONObject;

import transaction_DAO.Transaction_DAO;

import beans.Beanstransaction_record;
import beans.Brgy;

/**
 * Servlet implementation class ChangeBarangay
 */
@WebServlet("/ChangeBarangay")
public class ChangeBarangay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeBarangay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Brgy>brgy_list=new ArrayList<Brgy>();
		try{
			PrintWriter out=response.getWriter();
			JSONObject objectall=new JSONObject();
			JSONArray array=new JSONArray();
			Transaction_DAO dao=new Transaction_DAO();
			String municipal=request.getParameter("municipal");
			municipal = getmun(municipal);
			brgy_list=dao.brgy_list(municipal);
			for(Brgy i: brgy_list){
				JSONObject obj=new JSONObject();
				obj.put("barangay", i.getBarangay());
				obj.put("brgy_id", i.getBrgy_id());
				array.put(obj);
			}
			objectall.put("data", array);
			out.print(objectall);
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	String getmun(String mun){
		String []temp = mun.split(" ");
		if(temp.length>=2){
			return temp[0];
		}
		return mun;
		
	}

}
