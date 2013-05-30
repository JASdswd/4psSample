package transaction;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.thebuzzmedia.imgscalr.Scalr;



import DAO.BaseDAO;
import bean.transactionBean;


/**
 * Servlet implementation class ViewImage
 */
@WebServlet("/ViewImage")
public class ViewImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		transactionBean bean = new transactionBean();
		
		String view_id = request.getParameter("view_id");
		
		try {
			BaseDAO userdao = new BaseDAO();
			bean= userdao.gethead_photo(true,view_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// scale photo
		FileInputStream fis;
		File f = new File(bean.getPhoto_head().toString());
		fis = new FileInputStream(f);
		BufferedImage img = ImageIO.read((InputStream)fis);
		BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 560);
		
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		ImageIO.write(scaledImg,"jpg",outputstream);
		outputstream.flush();
		byte[] imageInByte = outputstream.toByteArray();
		outputstream.close();
		ServletOutputStream out = response.getOutputStream();
		out.write(bean.getPhoto_head(), 0,bean.getPhoto_head().length);
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
