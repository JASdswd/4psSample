package transaction_Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import transaction_DAO.Transaction_DAO;

import bean.reportBean;

/**
 * Servlet implementation class Autocom
 */
@WebServlet("/Autocom")
public class Autocom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Autocom() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String>list_id=new ArrayList<String>();
		ArrayList<String>list_head_name=new ArrayList<String>();
		ArrayList<String>list_wife=new ArrayList<String>();
		ArrayList<String>list_student=new ArrayList<String>();
		ArrayList<String>list_municipal=new ArrayList<String>();
		ArrayList<String>list_municipal1=new ArrayList<String>();
		ArrayList<String>list_phil=new ArrayList<String>();
		ArrayList<reportBean>list_brgy2=new ArrayList<reportBean>();
		JSONArray array=new JSONArray();
		try{
			String name=request.getParameter("name");
			System.out.println("name="+name);
			String transaction=request.getParameter("transaction");
			System.out.println("transaction="+transaction);
			Transaction_DAO dao=new Transaction_DAO();
			//JSONArray array=new JSONArray();
			JSONObject objectall=new JSONObject();
			PrintWriter out=response.getWriter();
			if(transaction.equals("household")){
				list_id=dao.list_id(name);
				objectall.put("list", list_id);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("grantee")){
				list_head_name=dao.list_head_name(name);
				System.out.println("name:"+name+" ***list_head_name:"+list_head_name);
				objectall.put("list", list_head_name);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("wife")){
				list_wife=dao.list_spouse(name);
				objectall.put("list", list_wife);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("student")){
				list_student=dao.list_student(name);
				objectall.put("list", list_student);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("student")){
				list_student=dao.list_student(name);
				objectall.put("list", list_student);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("municipal")){
				String municipal=request.getParameter("municipal");
				String brgy=request.getParameter("brgy");
				System.out.println("municipal="+municipal);
				System.out.println("brgy="+brgy);
				if(municipal!=null && brgy!=null){
					list_municipal=dao.list_municipal(name,municipal,brgy);
					objectall.put("list", list_municipal);
					System.out.println("Objectall="+objectall);
					out.print(objectall);
					out.flush();
					out.close();
				}else{
					list_municipal1=dao.list_municipal1(name, municipal);
					objectall.put("list", list_municipal1);
					System.out.println("Objectall="+objectall);
					out.print(objectall);
					out.flush();
					out.close();
				}
			}else if(transaction.equals("brgy")){
				list_brgy2=dao.list_brgy2(name);
				for(reportBean x:list_brgy2){
					JSONObject jsonObjs=new JSONObject();
					jsonObjs.put("brgy_id", x.getMun_id());
					jsonObjs.put("brgy_name", x.getMun_name());
					array.put(jsonObjs);
				}
				objectall.put("list", array);
				System.out.println("Objectall="+objectall);
				out.print(objectall);
				out.flush();
				out.close();
			}else if(transaction.equals("phil")){
				list_phil=dao.phil_list(name);
				objectall.put("list", list_phil);
				out.print(objectall);
				System.out.println("Objectall="+objectall);
				out.flush();
				out.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
