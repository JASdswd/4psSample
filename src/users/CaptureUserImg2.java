package users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;

import DAO.BaseDAO;

import com.thebuzzmedia.imgscalr.Scalr;

/**
 * Servlet implementation class CaptureUserImg2
 */
@WebServlet("/CaptureUserImg2")
public class CaptureUserImg2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaptureUserImg2() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Capture-Image doPost[jm]");
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null Password_Confirmation servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				String cPath = request.getContextPath();
				int user_role = Integer.parseInt(request.getParameter("CI_user_role"));
				
				BASE64Decoder decoder = new BASE64Decoder(); //1st step of decoding base64 string
				System.out.println("request.getParameter:"+request.getParameter("capture_image"));
				String regex52494[] = request.getParameter("capture_image").split("base64,");
				String imageInString = regex52494[1];
				byte[] imageInByte = decoder.decodeBuffer(imageInString); //2nd step and end of decoding base64 string
				ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
				BufferedImage img = ImageIO.read(in);
				
				BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 250);
				ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
				ImageIO.write(scaledImg,"jpeg",outputstream);
				outputstream.flush();
				byte[] finalImageInByte = outputstream.toByteArray();
				outputstream.close();
				
		    	try{
		    		
					UserDAO dao =  new UserDAO();
					BaseDAO dao1 = new BaseDAO();
					/*================ Geting date from the server ===================*/
					String dateAndTime = dao1.getDateAndTime();
					String regex[] = dateAndTime.split(" ");
					String curDate = regex[0];
					String regex1[] = regex[1].split("\\."); // naa cjay duha ka slash kung mag split ka with only a dot.
					String curTime = regex1[0];
					
					String regex3[] = curDate.split("-");
					String curYear = regex3[0];
					String curMonth = regex3[1];
					String curDay = regex3[2];
					String convertedDate = curMonth+"/"+curDay+"/"+curYear;
					
					
					/*================================================================*/
					dao.upload_capture_photo2(finalImageInByte, false, user_role);
					
					String rfname = dao.getUser2Fname(user_role);
					String rlname = dao.getUser2Lname(user_role);
					/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
					String logs_fname = dao.getUser2Fname(1);
					String logs_lname = dao.getUser2Lname(1);
					if(user_role==1){
						dao.add_logs(false, convertedDate, curTime, "Provincial link user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
					}else if(user_role==3){
						dao.add_logs(false, convertedDate, curTime, "Financial Analyst user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
					}
					else if(user_role==10){
						dao.add_logs(false, convertedDate, curTime, "Administrator user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
					}
		    	}catch(Exception e){
		    	    e.printStackTrace();
		    	}
		    	response.sendRedirect(cPath+"/ViewAllUser2?id="+user_role);
			}
		}
	}


}
