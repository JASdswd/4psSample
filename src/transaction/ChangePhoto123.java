package transaction;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;


import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


import DAO.BaseDAO;
import DAO.UploadPhotoDAO;

import com.thebuzzmedia.imgscalr.Scalr;


/**
 * Servlet implementation class ChangePhoto
 */
/**
 * Servlet implementation class ChangePhoto
 */
@WebServlet("/ChangePhoto123")
public class ChangePhoto123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePhoto123() {
        super();
        // TODO Auto-generated constructor stub
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
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("request: "+request);
		if (!isMultipart) {
			System.out.println("File Not Uploaded");
		} else {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = null;

			try {
				items = upload.parseRequest(request);
				System.out.println("items: "+items);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator itr = items.iterator();
			String household_id = "";
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()){
					String name = item.getFieldName();
					System.out.println("name: "+name);
					household_id = item.getString();
					System.out.println("value: "+household_id);
				}
				else{
					System.out.println("fieldname:"+item.getFieldName()+"+ hh:"+item.getName()+" size:"+item.getSize()+" ");
					System.out.println("item.toString:"+item.toString()+" it.getcontextType:"+item.getContentType());
					
					String req = item.toString();
					
					System.out.println("req"+req);
					String[] split =req.split("StoreLocation=");
					System.out.println("split[0]:"+split[1]);
					String[] split1 = split[1].split(",");
					System.out.println("split[0]"+split1[0]);
					String savefile = split1[0];
					System.out.println("savefile:"+savefile);
					
					File f = new File(savefile);
					Calendar calendar= Calendar.getInstance();
					DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
					SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
					
					String date = format.format(calendar.getTime());
					//System.out.println("date:"+day);
					String time = timeInstance.format(Calendar.getInstance().getTime());
					//System.out.println("time:"+time);
					
					/*Connection connection = null;
					String connectionURL = "jdbc:mysql://localhost:3306/4psedit";
					PreparedStatement psmnt = null;*/
					FileInputStream fis;
					
					session.setAttribute("household_id", household_id);
					household_id = (String) session.getAttribute("household_id");
					
					try{
						/*Class.forName("com.mysql.jdbc.Driver").newInstance();
						connection = DriverManager.getConnection(connectionURL, "root", "");*/
						
						BaseDAO dao = new BaseDAO();
						System.out.println("change_photo -> household_id : "+household_id);
						int ctr = dao.testIfPhotoExist(false, household_id);
						
						/* Scale the image uploaded */
						
						fis = new FileInputStream(f);
						BufferedImage img = ImageIO.read((InputStream)fis);
						BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 400);
						
						ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
						ImageIO.write(scaledImg,"jpg",outputstream);
						outputstream.flush();
						byte[] imageInByte = outputstream.toByteArray();
						outputstream.close();
						UploadPhotoDAO uploadPhoto = new UploadPhotoDAO();
						int server_id = dao.getServerId();
						String user_id = (String)session.getAttribute("user_id");
						int user_idInt = Integer.parseInt(user_id);
						int mun_id = dao.getMunId2(household_id);
						if(ctr==0){
							/*psmnt = connection.prepareStatement("insert into photo_tbl(household_id,photo_head) values(?,?)");	
							psmnt.setString(1, household_id);
							psmnt.setBytes(2, imageInByte);*/
							
							uploadPhoto.uploadBeneficiaryPhoto(household_id, imageInByte,date,time,server_id,0,user_idInt,mun_id, false,1);
						}
						else{
							/*psmnt = connection.prepareStatement("update photo_tbl set photo_head = ? where household_id = '"+household_id+"'");		
							psmnt.setBytes(1, imageInByte);*/
							uploadPhoto.uploadBeneficiaryPhoto(household_id, imageInByte,date,time,server_id,0,user_idInt,mun_id, false, 2);
							dao.add_logs(false, date, time, "Household ID "+household_id +" change its profile picture by "+session.getAttribute("username"));
						}
						
						System.out.println("going to View_transaction servlet");
						ServletContext sc = this.getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/View_transactions");
						rd.forward(request, response);
							
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
			/* Upload image noong unang panahon. */
			
			
			/*Calendar calendar= Calendar.getInstance();
			DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
			SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
			
			String date = format.format(calendar.getTime());
			//System.out.println("date:"+day);
			String time = timeInstance.format(Calendar.getInstance().getTime());
			//System.out.println("time:"+time);
			
			Connection connection = null;
			String connectionURL = "jdbc:mysql://localhost:3306/4psedit";
			PreparedStatement psmnt = null;
			FileInputStream fis;
			
			session.setAttribute("household_id", household_id);
			household_id = (String) session.getAttribute("household_id");
			String req = items.get(0).toString();
			
			System.out.println("req"+req);
			String[] split =req.split("StoreLocation=");
			System.out.println("split[0]:"+split[1]);
			String[] split1 = split[1].split(",");
			System.out.println("split[0]"+split1[0]);
			String savefile = split1[0];
			System.out.println("savefile:"+savefile);
			
			File f = new File(savefile);
			
				try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(connectionURL, "root", "");
				
				BaseDAO dao = new BaseDAO();
				System.out.println("change_photo -> household_id : "+household_id);
				int ctr = dao.testIfPhotoExist(false, household_id);
				
				Scale image
				fis = new FileInputStream(f);
				BufferedImage img = ImageIO.read((InputStream)fis);
				BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 180);
				
				ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
				ImageIO.write(scaledImg,"jpg",outputstream);
				outputstream.flush();
				byte[] imageInByte = outputstream.toByteArray();
				outputstream.close();
				if(ctr==0){
					psmnt = connection.prepareStatement("insert into photo_tbl(household_id,photo_head) values(?,?)");	
					psmnt.setString(1, household_id);
					psmnt.setBytes(2, imageInByte);
				}
				else{
					psmnt = connection.prepareStatement("update photo_tbl set photo_head = ? where household_id = '"+household_id+"'");		
					psmnt.setBytes(1, imageInByte);
					dao.add_logs(false, date, time, "Household ID "+household_id +" change its profile picture by "+session.getAttribute("username"));
				}
				
				
				
				
				int s = psmnt.executeUpdate();
				if(s>0){
					System.out.println("Uploaded successfully !");
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/View_transactions");
					rd.forward(request, response);
				}
				else{
					System.out.println("Upload image Error!");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}*/
		}
	}

	}

