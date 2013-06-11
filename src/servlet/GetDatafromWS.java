package servlet;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DAO.BaseDAO;
/**
 * Servlet implementation class GetDataOnWEB
 */
@WebServlet("/GetDatafromWS")
public class GetDatafromWS extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDatafromWS() {
        super();
    }    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Make a URL to the web page
        URL url = new URL("http://beta.mcct-dswd.com/McctService.svc/Mcct/family/province/batangas");
        System.out.println("getDatafromWS");
        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        // read each line and write to System.out
        while ((line = br.readLine()) != null) {
            System.out.println("Data From Web: "+line);
	        try {    
	             JSONArray array;
	             array = new JSONArray(line);
	             BaseDAO dao = new BaseDAO();
	             System.out.println("Total Grantee: "+ array.length());
	             for (int i = 0; i < array.length(); i++) {
	               JSONObject obj = array.getJSONObject(i);
	               String id = obj.getString("FamilyId");
	               String fname = obj.getString("FirstName");
	               String mname = obj.getString("MiddleName");
	               String lname = obj.getString("LastName");
	               String bday = obj.getString("Birthday");
	               String gender = obj.getString("Gender");
	               int LOF = obj.getInt("LengthOfStay");
	               int MS = obj.getInt("MaritalStat");
	               int occupation = obj.getInt("Occupation");
	               
	               String replace_enye_fname = fname.replace("Ã‘", "Ñ");
	               String replace_enye_mname = mname.replace("Ã‘", "Ñ");
	               String replace_enye_lname = lname.replace("Ã‘", "Ñ");
	 
		           dao.addWebData(id, replace_enye_fname, replace_enye_mname, replace_enye_lname,bday, gender, LOF, MS, occupation);
		          
	          }
	          } catch (JSONException e) {
	              e.printStackTrace();
	          } catch (SQLException e) {
				e.printStackTrace();
			}
        }
        ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/TransactionView");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
