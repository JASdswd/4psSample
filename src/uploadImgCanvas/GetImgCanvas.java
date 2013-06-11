package uploadImgCanvas;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thebuzzmedia.imgscalr.Scalr;

import sun.misc.BASE64Decoder;
import DAO.BaseDAO;
import DAO.UploadPhotoDAO;

/**
 * Servlet implementation class GetImgCanvas
 */
@WebServlet("/GetImgCanvas")
public class GetImgCanvas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImgCanvas() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/View_transactions");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("post getimage servlet");
		
		HttpSession session = request.getSession(false);
		
		BASE64Decoder decoder = new BASE64Decoder(); //1st step of decoding base64 string
		String regex52494[] = request.getParameter("capture_image").split("base64,");
		String imageInString = regex52494[1];
		byte[] imageInByte = decoder.decodeBuffer(imageInString); //2nd step and end of decoding base64 string
		ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage img = ImageIO.read(in);
		
		BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 400);
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		ImageIO.write(scaledImg,"jpg",outputstream);
		outputstream.flush();
		byte[] finalImageInByte = outputstream.toByteArray();
		outputstream.close();
		
    	try{
    		String household_id = request.getParameter("hh_id");
			UploadPhotoDAO uploadPhoto = new UploadPhotoDAO();		
			BaseDAO dao = new BaseDAO();
			
			/*================ Geting date from the server ===================*/
			String dateAndTime = dao.getDateAndTime();
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
			
			int ctr = uploadPhoto.testIfPhotoExist(false, household_id);
			int server_id = dao.getServerId();
			String user_id = (String)session.getAttribute("user_id");
			int user_idInt = Integer.parseInt(user_id);
			int mun_id = dao.getMunId2(household_id);
			int team_id = dao.getTeamId();
			if(ctr==0){
				
				uploadPhoto.uploadBeneficiaryPhoto(household_id, finalImageInByte,convertedDate,curTime,server_id,team_id,user_idInt,mun_id, false,1);
			}
			else{
				uploadPhoto.uploadBeneficiaryPhoto(household_id, finalImageInByte,convertedDate,curTime,server_id,team_id,user_idInt,mun_id, false, 2);
				dao.add_logs(false, convertedDate, curTime, "Household ID "+household_id +" change its profile picture by "+session.getAttribute("username"));
			}
			
 
    	}catch(Exception e){
    	    e.printStackTrace();
    	}
    	session.setAttribute("household_id", request.getParameter("hh_id"));
    	ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/View_transactions");
		rd.forward(request, response);
		

	}

}
