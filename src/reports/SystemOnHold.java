package reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONObject;

import DAO.reportDAO;
import bean.reportBean;

/**
 * Servlet implementation class SystemOnHold
 */
@WebServlet("/SystemOnHold")
public class SystemOnHold extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemOnHold() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in here");
		HttpSession session  = request.getSession(false);
		String cPath = request.getContextPath();
		if(session==null){
			System.out.println("session is null add user mun servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null add user mun servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				String status = "exists";
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				try{
					ArrayList<reportBean> list = new ArrayList<reportBean>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					String fpath = null;
					//String sdate = request.getParameter("date");
					//int team_id = Integer.parseInt(request.getParameter("team_num"));
					reportDAO dao = new reportDAO();
					//System.out.println("------"+sdate);
					String path = "C:/"+cPath+"/";
					boolean success = (new File(path)).mkdir();
					
					/*String datec[]=sdate.split("/");
					String yc = datec[2];
					String mc = datec[0];
					String dc = datec[1];*/
				//	String date_conducted = yc+"-"+mc+"-"+dc;
					list = dao.getSysOReport();
					//glist = dao.getGRS(date_conducted,team_id);
					
					if(success){
						fpath = path;
						System.out.println("created");
					}
					else{
						String path2 = "Documents/"+cPath+"/";
						boolean succ = (new File(path2)).mkdir();
						
						if(succ){
							fpath = path2;
							System.out.println("Created a directory at desktop not windows");
						}
						
					}
					
					if(fpath == null){
						String path1 = "C:/"+cPath+"/";
						String path2 = "Documents/"+cPath+"/";
						File file = new File(path1);
						if(file.exists()){
							fpath = path1;
						}
						else{
							
							File file2 = new File(path2);
							if(file2.exists()){
								fpath = path2;
							}
						}
						
					}
					//String mun_name="";
					String date1[]=sdf.format(date).split("-");
					String y = date1[0];
					String m = date1[1];
					String d = date1[2];
					//mun_name = dao.getMunServed(sdate,team_id);
					String filename = fpath+"SystemOnHoldasof"+y+m+d+".xls";
					
					//System.out.println(sdate);
					
					File file = new File(filename);
					
					System.out.println(filename);
					
					if(file.exists()){
						System.out.println("File already exists..");
						status="exists";
					}
					else{
						status = "not";
						HSSFWorkbook hwb=new HSSFWorkbook();
						System.out.println("size:"+list.size());
							if(list.size() != 0){
								HSSFSheet sheet =  hwb.createSheet("System On Hold");
								//HSSFCell cell;
								//hwb.cloneSheet(0);
								HSSFRow row=   sheet.createRow((short)0);
								String head_cell = "";
								
								for(int c=0;c<2;c++){
									if(c==0){
										head_cell = "Household ID";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==1){
										head_cell = "Remarks";
										sheet.setColumnWidth(c, 5000);
									}
									
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
									hcell.setFont(hf);
									hc2 = row.createCell((short) c);
									hc2.setCellValue(head_cell);
									hc2.setCellStyle(hcell);
									
								}
								String body_cell = "";
								int rw=1;
								for(reportBean dlist:list){
									
									HSSFRow body_row =   sheet.createRow((short) rw);
									
									for(int j=0;j<2;j++){
										
										if(j==0){
											body_cell = dlist.getMunicipality();
										}
										else if(j==1){
											body_cell = dlist.getBrgy_name();
										}
										
										HSSFCell hc;
										HSSFCellStyle cell = hwb.createCellStyle();
										HSSFFont h = hwb.createFont();
										h.setFontName("Calibri");
										cell.setFont(h);
										hc = body_row.createCell((short) j);
										hc.setCellValue(body_cell);
										hc.setCellStyle(cell);
										
									}
									rw++;
								}
								
							}
							else{
								status = "empty";
							}
						
						FileOutputStream fileOut =  new FileOutputStream(filename);
						hwb.write(fileOut);
						fileOut.close();	
						
					}

					obj.put("path", filename);
					obj.put("status",status);
					out.print(obj);
					out.flush();
					out.close();
					
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
