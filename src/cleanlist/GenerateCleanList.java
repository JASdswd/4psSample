package cleanlist;

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

import bean.reportBean;

import DAO.BaseDAO;

/**
 * Servlet implementation class GenerateCleanList
 */
@WebServlet("/GenerateCleanList")
public class GenerateCleanList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateCleanList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session  = request.getSession(false);
		if(session==null){
			System.out.println("session is null excelParse servelet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_transactions2 servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				try{
					
					String status = "exists";
					JSONObject obj = new JSONObject();
					PrintWriter out = response.getWriter();
					String cPath = request.getContextPath();
					String path = "C:/"+cPath+"/";
					boolean success = (new File(path)).mkdir();
					String fpath = null;
					CleanListDAO dao = new CleanListDAO();
					ArrayList<String> grslist = new ArrayList<String>();
					ArrayList<String> grsbymunlist = new ArrayList<String>();
					ArrayList<String> coalist = new ArrayList<String>();
					ArrayList<String> solist = new ArrayList<String>();
					ArrayList<reportBean> receivedlist = new ArrayList<reportBean>();
					ArrayList<reportBean> cleanlist = new ArrayList<reportBean>();
					BaseDAO dao1 = new BaseDAO();
					String mun = request.getParameter("mun");
					boolean grsfound = false;
					boolean coafound = false;
					boolean sofound = false;
					grslist = dao.getAllGRSCases("grscases2_tbl_temp",mun);
					grsbymunlist = dao.getAllGRSCases("grscases_tbl_temp",mun);
					coalist = dao.getAllCOACases("coacases2_tbl",mun);
					solist = dao.getAllSO("system_onhold_tbl",mun);
					receivedlist = dao.getAllReceivedList(mun);
					int countgrs = 0;
					int countso = 0;

					System.out.println("grs:"+grslist.size());
					System.out.println("grs:"+solist.size());
					
					for(reportBean l: receivedlist){
						grsfound = false;
						sofound = false;
						coafound = false;
						for(String g: grslist){
							if(l.getHousehold_id().equalsIgnoreCase(g)){
								grsfound = true;
								countgrs++;
							}
						}
						for(String gm: grsbymunlist){
							if(l.getHousehold_id().equalsIgnoreCase(gm)){
								grsfound = true;
								countgrs++;
							}
						}
						
						for(String s: solist){
							if(l.getHousehold_id().equalsIgnoreCase(s)){
								sofound = true;
								countso++;
							}
						}for(String c: coalist){
							if(l.getHousehold_id().equalsIgnoreCase(c)){
								coafound = true;
								//countgrs++;
							}
						}
						if(!grsfound && !sofound && !coafound){
							cleanlist.add(l);
						}
						
						
					}
					

					
					System.out.println("grscount:"+countgrs);
					System.out.println("socount:"+countso);
					
					if(cleanlist.size() != 0){
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
						String mun_name="";
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date = new Date();
						
						mun_name = dao1.getmunName(Integer.parseInt(mun));
						String filename = fpath+mun_name+"_CleanList_"+sdf.format(date)+".xls";
						
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
							
						
								HSSFSheet sheet =  hwb.createSheet("Clean List of "+mun_name.toUpperCase());
								//HSSFCell cell;
								HSSFRow row=   sheet.createRow((short)0);
								String head_cell = "";
								
								for(int c=0;c<6;c++){
									if(c==0){
										head_cell = "Household ID";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==1){
										head_cell = "Head Name";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==2){
										head_cell = "Barangay Name";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==3){
										head_cell = "Municipal Name";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==4){
										head_cell = "HH Set";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==5){
										head_cell = "Set Group";
										sheet.setColumnWidth(c, 2000);
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
							//	int grand_total = 0;
								for(reportBean dlist:cleanlist){
									
									HSSFRow body_row =   sheet.createRow((short) rw);
									
									for(int j=0;j<6;j++){
										
										if(j==0){
											body_cell = dlist.getHousehold_id();
										}
										else if(j==1){
											body_cell = dlist.getHead_name();
										}
										else if(j==2){
											body_cell = dlist.getBrgy();
										}
										else if(j==3){
											body_cell = dlist.getMunicipality();
										}
										else if(j==4){
											body_cell = dlist.getHh_set();
										}
										else if(j==5){
											body_cell = dlist.getSet_group();
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

								
								FileOutputStream fileOut =  new FileOutputStream(filename);
								hwb.write(fileOut);
								fileOut.close();	
							
							
							
						
						}

						System.out.println("last:"+filename);
						obj.put("path", filename);
						
					}
					else{
						status = "empty";
					}
					
					
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

}
