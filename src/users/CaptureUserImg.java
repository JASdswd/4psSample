package users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
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
 * Servlet implementation class CaptureUserImg
 */
@WebServlet("/CaptureUserImg")
public class CaptureUserImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaptureUserImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Capture-Image doPost[jm]");
		HttpSession session = request.getSession(false);
		String cPath = request.getContextPath();
		int id = Integer.parseInt(request.getParameter("CI_id"));
		String mun = request.getParameter("CI_mun");
		int user_role = Integer.parseInt(request.getParameter("CI_user_role"));
		
		BASE64Decoder decoder = new BASE64Decoder(); //1st step of decoding base64 string
		System.out.println("request.getParameter:"+request.getParameter("capture_image"));
		String regex[] = request.getParameter("capture_image").split("base64,");
		String imageInString = regex[1];
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
			
			Calendar calendar= Calendar.getInstance();
			DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
			SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
			
			String date = format.format(calendar.getTime());
			//System.out.println("date:"+day);
			String time = timeInstance.format(Calendar.getInstance().getTime());
			
			//uploadPhoto.uploadProvPhoto(imageInByte, false); //uploading image to database
			//boolean ret = uploadPhoto.uploadMunPhoto(imageInByte, mun, fname, lname, false);
			dao.upload_capture_photo(finalImageInByte, false, id, user_role);
			//if(ret){
				//dao.add_logs(false, date, time, "Municipal user from "+mun_name+" named "+lname+", "+fname+" change its photo by Provincial Link");
			//}
			String rfname = dao.getFname(user_role,id);
			String rlname = dao.getLname(user_role,id);
			/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
			String logs_fname = dao.getUser2Fname(1);
			String logs_lname = dao.getUser2Lname(1);
			if(user_role==2){
				dao.add_logs(false, date, time, "Municipal Link user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
			else if(user_role==4){
				dao.add_logs(false, date, time, "BookKeeper user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
			else if(user_role==5){
				dao.add_logs(false, date, time, "Verifier user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
			else if(user_role==6){
				dao.add_logs(false, date, time, "Grievance Officer user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
			else if(user_role==7){
				dao.add_logs(false, date, time, "Social Worker user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
    	}catch(Exception e){
 
    	    e.printStackTrace();
 
    	}
    	response.sendRedirect(cPath+"/Add_user?id="+id+"&mun="+mun+"&user_role="+user_role);

	}


}
