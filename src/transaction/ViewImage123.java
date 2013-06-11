package transaction;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thebuzzmedia.imgscalr.Scalr;

import bean.transactionBean;
import DAO.BaseDAO;

/**
 * Servlet implementation class ViewImage123
 */
@WebServlet("/ViewImage123")
public class ViewImage123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewImage123() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		transactionBean bean = new transactionBean();
		
		String view_id = request.getParameter("view_id");
		try {
			BaseDAO userdao = new BaseDAO();
			bean= userdao.gethead_photo(true,view_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ByteArrayInputStream in = new ByteArrayInputStream(bean.getPhoto_head());
		BufferedImage img = ImageIO.read(in);
		String ctr = request.getParameter("ctr");
		int size = 0;
		if(ctr.equals("qfdj")){
			size = 400;
		}
		else{
			size = 250;
		}
		BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, size);
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		ImageIO.write(scaledImg,"png",outputstream);
		outputstream.flush();
		byte[] imageInByte = outputstream.toByteArray();
		outputstream.close();
		ServletOutputStream out = response.getOutputStream();
		out.write(imageInByte, 0,imageInByte.length);
		out.flush();
		out.close();
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
