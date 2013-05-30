package users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import DAO.BaseDAO;
import DAO.FinancialAnalystDAO;
import bean.transactionBean;

import com.thebuzzmedia.imgscalr.Scalr;

/**
 * Servlet implementation class GetPhotoUser2
 */
@WebServlet("/GetPhotoUser2")
public class GetPhotoUser2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPhotoUser2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		
		if(session==null){
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				transactionBean bean = new transactionBean();
			/*	String f = request.getParameter("f");
				String l = request.getParameter("l");*/
				//int m = Integer.parseInt(request.getParameter("m"));
				String id = request.getParameter("pID");
				String user_roleString = "";
				user_roleString = request.getParameter("user_role");
				
				System.out.println("id : "+id);
				try {
					int user_role = Integer.parseInt(user_roleString);
					UserDAO userdao = new UserDAO();
					bean= userdao.getphoto_user2(false,user_role);
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ByteArrayInputStream in = new ByteArrayInputStream(bean.getPhoto_head());
				BufferedImage img = ImageIO.read(in);
				
				BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 250);
				ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
				ImageIO.write(scaledImg,"jpeg",outputstream);
				outputstream.flush();
				byte[] imageInByte = outputstream.toByteArray();
				outputstream.close();
				
				ServletOutputStream out = response.getOutputStream();
				out.write(imageInByte, 0,imageInByte.length);
				out.flush();
				out.close();		
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cPath = request.getContextPath();
		HttpSession session  = request.getSession(false);
		if(session==null){
			System.out.println("session is null add municipality servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_transactions servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
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
					int user_role = 0;
					int x =0;
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();
						if (item.isFormField()){
							if(x==0){
								user_role = Integer.parseInt(item.getString());
							}
							x++;
							
						}
						else{
							
							String req = item.toString();

							System.out.println("Lora and Gula:"+user_role);
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
							String time = timeInstance.format(Calendar.getInstance().getTime());
							
							FileInputStream fis;
							
							try{								
								UserDAO dao = new UserDAO();
								
								fis = new FileInputStream(f);
								BufferedImage img = ImageIO.read((InputStream)fis);
								BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 250);
								
								ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
								ImageIO.write(scaledImg,"jpg",outputstream);
								outputstream.flush();
								byte[] imageInByte = outputstream.toByteArray();
								outputstream.close();
								
								int up =dao.updateuserphoto2(imageInByte,user_role);
								
								String rfname = dao.getUser2Fname(user_role);
								String rlname = dao.getUser2Lname(user_role);
								/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
								String logs_fname = dao.getUser2Fname(1);
								String logs_lname = dao.getUser2Lname(1);
								if(user_role==1){
									dao.add_logs(false, date, time, "Provincial Link user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
								}else if(user_role==3){
									dao.add_logs(false, date, time, "Financial Analyst user named "+rlname+", "+rfname+" change its photo by "+logs_fname+" "+logs_lname);
								}								
							}catch (Exception e) {
								e.printStackTrace();
							}							
							response.sendRedirect(cPath+"/ViewAllUser2?id="+user_role);
							return;
							
						}
					}
				}
			}
		}
	}
	
}
