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
			String time = timeInstance.format(Calendar.getInstance().getTime());
			dao.upload_capture_photo2(finalImageInByte, false, user_role);
			
			String rfname = dao.getUser2Fname(user_role);
			String rlname = dao.getUser2Lname(user_role);
			/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
			String logs_fname = dao.getUser2Fname(1);
			String logs_lname = dao.getUser2Lname(1);
			if(user_role==1){
				dao.add_logs(false, date, time, "Provincial link user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}else if(user_role==3){
				dao.add_logs(false, date, time, "Financial Analyst user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
			}
    	}catch(Exception e){
 
    	    e.printStackTrace();
 
    	}
    	response.sendRedirect(cPath+"/ViewAllUser2?id="+user_role);

	}


}
