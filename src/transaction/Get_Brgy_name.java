package transaction;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


import transaction_DAO.Transaction_DAO;


import DAO.BaseDAO;
import bean.transactionBean;
import beans.Brgy;


/**
 * Servlet implementation class Get_Brgy_name
 */
@WebServlet("/Get_Brgy_name")
public class Get_Brgy_name extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Get_Brgy_name() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------------------- in");
		ArrayList<Brgy>brgy_list=new ArrayList<Brgy>();
		try{
			PrintWriter out=response.getWriter();
			JSONObject objectall=new JSONObject();
			
			Transaction_DAO dao=new Transaction_DAO();
			String municipal=request.getParameter("municipal");
			//JOptionPane.showMessageDialog(null, municipal);
			//municipal = getmun(municipal);
			brgy_list=dao.getAllbrgy(municipal);
			objectall.put("data",brgy_list);
			out.print(objectall);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<transactionBean>brgy_name = new ArrayList<transactionBean>();
		
		String mun_name = request.getParameter("mun_name");
		String regex[] = mun_name.split("-");
		int mun_id = Integer.parseInt(regex[0]);
		try{
			BaseDAO dao = new BaseDAO();
			brgy_name = dao.getbrgy_name(true,mun_id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write("<Brgy_Name>");
		for (transactionBean l: brgy_name) {
			response.getWriter().write(
					"<brgy_name>"+l.getBarangay()+"</brgy_name> " +
					"<title>"+l.getBarangay()+"</title>");
			
			
			
		}
		response.getWriter().write("</Brgy_Name>");
	}

}
