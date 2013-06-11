package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BaseDAO;
import DAO.UploadPhotoDAO;

import bean.transactionBean;

import com.thebuzzmedia.imgscalr.Scalr;

/**
 * Servlet implementation class ReduceFilesizeImg
 */
@WebServlet("/ReduceFilesizeImg")
public class ReduceFilesizeImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReduceFilesizeImg() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		transactionBean bean = new transactionBean();
		//String view_id = request.getParameter("view_id");
		ArrayList<transactionBean> hhid = new ArrayList<transactionBean>();

		try {
			BaseDAO userdao = new BaseDAO();
			hhid = userdao.gethhid(false);
			ByteArrayInputStream in =null;
			BufferedImage img =null;
			BufferedImage scaledImg =null;
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			byte[] imageInByte = null;
			
			UploadPhotoDAO uploadPhoto = new UploadPhotoDAO();
			int count = 1;
			System.out.println("hh_id size:"+hhid.size());
			for(transactionBean l: hhid){
				System.out.println("dfd:"+l.getBarangay());
				bean= userdao.gethead_photo(false,l.getBarangay());
				 in = new ByteArrayInputStream(bean.getPhoto_head());
				 img = ImageIO.read(in);
				 scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 500);
				 outputstream = new ByteArrayOutputStream();
				ImageIO.write(scaledImg,"jpg",outputstream);
				outputstream.flush();
				imageInByte = outputstream.toByteArray();
				outputstream.close();
				try {
					
					uploadPhoto.uploadBeneficiaryPhoto(l.getBarangay(),imageInByte,"","",0,0,0,0, false, 2);
					System.out.println(hhid.size()+"count:"+count++);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Finished reducing file size of image");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
			
		/*For viewing image in browser*/
		/*ServletOutputStream out = response.getOutputStream();
		out.write(imageInByte, 0,imageInByte.length);
		out.flush();
		out.close();*/
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
