package users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import bean.transactionBean;

import com.thebuzzmedia.imgscalr.Scalr;

/**
 * Servlet implementation class GetPhotoSW
 */
@WebServlet("/GetPhotoUser")
public class GetPhotoUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPhotoUser() {
        super();
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
				String id = request.getParameter("ID");
				int user_role = Integer.parseInt(request.getParameter("user_role"));
				System.out.println("id : "+id);
				System.out.println("user role:"+user_role);
				try {
					
					UserDAO userdao = new UserDAO();
					bean= userdao.getUser_photo(false,id,user_role);
					 
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ByteArrayInputStream in = new ByteArrayInputStream(bean.getPhoto_head());
				BufferedImage img = ImageIO.read(in);
				String sizeImage = request.getParameter("sizeImage");
				int size = 0;
				if (sizeImage.equals("dkFjGhsdkTRsld734snk8JDSK")){// value of parameter in getting the image.
					size = 100;
				}
				else{
					size = 250;
				}
				
				BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, size);
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
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);				
				
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
					String fname = "";
					String lname = "";
					int mun = 0;
					String uniqueID = "";
					int user_role = 0;
					int x = 0;
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();
						if (item.isFormField()){
							
							if(x == 0){
								fname = item.getString();
							}
							else if(x==1){
								lname = item.getString();
							}
							else if(x==2){
								mun = Integer.parseInt(item.getString());
							}
							else if(x==3){
								uniqueID = item.getString();
							}
							else if(x==4){
								user_role = Integer.parseInt(item.getString());
							}
							x++;
							String name = item.getFieldName();
							System.out.println("name: "+name+" "+x);
						}
						else{
							
							String req = item.toString();
							
							System.out.println("req"+req);
							String[] split =req.split("StoreLocation=");
							System.out.println("split[0]:"+split[1]);
							String[] split1 = split[1].split(",");
							System.out.println("split[0]"+split1[0]);
							String savefile = split1[0];
							System.out.println("savefile:"+savefile);
							System.out.println("ayaw vah.. ayaw woie..:"+uniqueID);
							File f = new File(savefile);
							Calendar calendar= Calendar.getInstance();
							DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
							SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
							
							String date = format.format(calendar.getTime());
						
							String time = timeInstance.format(Calendar.getInstance().getTime());
						
							
							
							FileInputStream fis;
							
							
							try{
								
								
								//SocialWorkerDAO dao = new SocialWorkerDAO();

								fis = new FileInputStream(f);
								BufferedImage img = ImageIO.read((InputStream)fis);
								BufferedImage scaledImg =Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 250);
								
								ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
								ImageIO.write(scaledImg,"jpg",outputstream);
								outputstream.flush();
								byte[] imageInByte = outputstream.toByteArray();
								outputstream.close();
								
								
								UserDAO uploadPhoto = new UserDAO();
								
								String mun_name = uploadPhoto.getmunName(mun);
								/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink)*/
								String logs_fname = uploadPhoto.getUser2Fname(1);
								String logs_lname = uploadPhoto.getUser2Lname(1);
								boolean ret = uploadPhoto.upload_user_photo(imageInByte, mun,uniqueID, false, user_role);
								if(ret){
									if(user_role==2){
										uploadPhoto.add_logs(false, date, time, "Municipal Link user from "+mun_name+" named "+lname+", "+fname+" change its photo by "+logs_fname +" " +logs_lname );
									}
									else if(user_role==4){
										uploadPhoto.add_logs(false, date, time, "Book Keeper user from "+mun_name+" named "+lname+", "+fname+" change its photo by "+logs_fname +" " +logs_lname );
									}
									else if(user_role==5){
										uploadPhoto.add_logs(false, date, time, "Verifier user from "+mun_name+" named "+lname+", "+fname+" change its photo by "+logs_fname +" " +logs_lname );
									}
									else if(user_role==6){
										uploadPhoto.add_logs(false, date, time, "Grievance Officer user from "+mun_name+" named "+lname+", "+fname+" change its photo by "+logs_fname +" " +logs_lname );
									}
									else if(user_role==7){
										uploadPhoto.add_logs(false, date, time, "Social Worker user from "+mun_name+" named "+lname+", "+fname+" change its photo by "+logs_fname +" " +logs_lname );
									}
									/*uploadPhoto.add_logs(false, date, time, "Social worker Officer user from "+mun_name+" named "+lname+", "+fname+" change its fingerprint by Provincial Link");*/
								}
								
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							response.sendRedirect(cPath+"/Add_user?id="+uniqueID+"&mun="+mun+"&user_role="+user_role);
							return;
							
						}
					}
				}
			}
		}
	
	}
}
