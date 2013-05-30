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
/*import org.apache.poi.hssf.usermodel.HSSFRichTextString;
*/import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONObject;

import DAO.BaseDAO;
import DAO.reportDAO;
import bean.reportBean;
import bean.reportBean2;

/**
 * Servlet implementation class DailyReports
 */
@WebServlet("/DailyReports")
public class DailyReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyReports() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
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
					ArrayList<reportBean> glist = new ArrayList<reportBean>();
					String sdate = request.getParameter("date");
					int team_id = Integer.parseInt(request.getParameter("team_num"));
					reportDAO dao = new reportDAO();
					System.out.println("------"+sdate);
					String path = "C:/"+cPath+"/";
					boolean success = (new File(path)).mkdir();
					String fpath = null;
					
					String datec[]=sdate.split("/");
					String yc = datec[2];
					String mc = datec[0];
					String dc = datec[1];
					String date_conducted = yc+"-"+mc+"-"+dc;
					
					list = dao.getRegistered(team_id,sdate,"fingerprint_tbl_temp");
					glist = dao.getGRS("grscases2_tbl_temp",date_conducted,team_id);
					System.out.println("glist:"+glist.size()+" list:"+list.size());
					if(list.size() != 0 || glist.size()!= 0){
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

						mun_name = dao.getMunServed(sdate,team_id);
						String filename = fpath+"DailyReports("+mun_name+")_"+yc+mc+dc+"_S"+Integer.toString(team_id)+".xls";
						
						System.out.println(sdate);
						
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
									HSSFSheet sheet =  hwb.createSheet("Daily Reports");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<8;c++){
										if(c==0){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==1){
											head_cell = "Barangay";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==2){
											head_cell = "Household ID";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==3){
											head_cell = "Name of Grantee";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==4){
											head_cell = "Team No";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==5){
											head_cell = "Server No";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==6){
											head_cell = "HH Set";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==7){
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
									for(reportBean dlist:list){
										
										HSSFRow body_row =   sheet.createRow((short) rw);
										
										for(int j=0;j<8;j++){
											
											if(j==0){
												body_cell = dlist.getMun_name();
											}
											else if(j==1){
												body_cell = dlist.getBrgy_name();
											}
											else if(j==2){
												body_cell = dlist.getHousehold_id();
											}
											else if(j==3){
												body_cell = dlist.getHead_name();
											}
											else if(j==4){
												//body_cell = Integer.toString(dlist.getTeam_id());
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(dlist.getTeam_id());
												hc.setCellStyle(cell);
											}
											else if(j==5){
												//body_cell = Integer.toString(dlist.getServer_id());
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(dlist.getServer_id());
												hc.setCellStyle(cell);
											}
											else if(j==6){
												body_cell = dlist.getHh_set();
											}
											else if(j==7){
												body_cell = dlist.getSet_group();
											}
											if(j == 4 || j == 5){
												//integer here for cellValues
											}
											else{
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(body_cell);
												hc.setCellStyle(cell);
											}
											
										}
										rw++;
									}
									
								}
								else{
									status = "emptylist";
								}
							
								
							if(glist.size() != 0){
								HSSFSheet sheet =  hwb.createSheet("GRS Report");
								//HSSFCell cell;
								HSSFRow row=   sheet.createRow((short)0);
								String head_cell = "";
								
								for(int c=0;c<10;c++){
									if(c==0){
										head_cell = "Municipality";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==1){
										head_cell = "Barangay";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==2){
										head_cell = "Date Conducted";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==3){
										head_cell = "Team ID";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==4){
										head_cell = "Household ID";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==5){
										head_cell = "Name of Grantee";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==6){
										head_cell = "GRS Case Type";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==7){
										head_cell = "GRS IDOCTYPE";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==8){
										head_cell = "Notes/Remarks";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==9){
										head_cell = "Grievance Officer";
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
								int rw = 1;
								for(reportBean h: glist){
									HSSFRow body_row =   sheet.createRow((short) rw);
									for(int k=0;k<10;k++){
										if(k==0){
											body_cell = h.getMun_name();
										}
										else if(k==1){
											body_cell = h.getBrgy_name();
										}
										else if(k==2){
											body_cell = h.getDate_coverage();
										}
										else if(k==3){
											//body_cell = Integer.toString(h.getTeam_id());
											HSSFCell hc2;
											HSSFCellStyle hcell = hwb.createCellStyle();
											HSSFFont hf = hwb.createFont();
											hf.setFontName("Calibri");
											hcell.setFont(hf);
											hc2 = body_row.createCell((short) k);
											hc2.setCellValue(h.getTeam_id());
											hc2.setCellStyle(hcell);
										}
										else if(k==4){
											body_cell = h.getHousehold_id();
										}
										else if(k==5){
											body_cell = h.getHead_name();
										}
										else if(k==6){
											body_cell = h.getGrscasetype();
										}
										else if(k==7){
											body_cell = h.getGrsidoctype();
										}
										else if(k==8){
											body_cell = h.getRemarks();
										}
										else if(k==9){
											body_cell = h.getGrievance_officer();
										}
										
										if(k!=3){
											HSSFCell hc2;
											HSSFCellStyle hcell = hwb.createCellStyle();
											HSSFFont hf = hwb.createFont();
											hf.setFontName("Calibri");
											hcell.setFont(hf);
											hc2 = body_row.createCell((short) k);
											hc2.setCellValue(body_cell);
											hc2.setCellStyle(hcell);
										}
										
									}
									rw++;
								}
								
							}
							else{
								status = "emptygrs";
							}
							FileOutputStream fileOut =  new FileOutputStream(filename);
							hwb.write(fileOut);
							fileOut.close();	
							
						}

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "deprecation" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
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
					
					reportDAO dao = new reportDAO();
					BaseDAO dao1 = new BaseDAO();
					String op = request.getParameter("op");
					
					System.out.println("op sa daily reports:"+op);
					
					if(op.equals("1")){
						
						int  mun_id = Integer.parseInt(request.getParameter("m"));
						System.out.println("by Municipality list");
						ArrayList<reportBean> list = new ArrayList<reportBean>();
						ArrayList<reportBean> glist = new ArrayList<reportBean>();
						String path = "C:/"+cPath+"/";
						boolean success = (new File(path)).mkdir();
						String fpath = null;
						
						list = dao.getRegistered(mun_id, "fingerprint_tbl_temp");
						glist = dao.getGRS("grscases2_tbl_temp",mun_id);
						System.out.println("glist:"+glist.size()+" list:"+list.size());
						
						if(list.size() != 0 || glist.size()!= 0){
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
							
							mun_name = dao1.getmunName(mun_id);
							String filename = fpath+mun_name+"_Registration_Report_"+sdf.format(date)+".xls";
							
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
										HSSFSheet sheet =  hwb.createSheet("Registration Report Per Municipality");
										//HSSFCell cell;
										HSSFRow row=   sheet.createRow((short)0);
										String head_cell = "";
										
										for(int c=0;c<8;c++){
											if(c==0){
												head_cell = "Municipality";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==1){
												head_cell = "Barangay";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==2){
												head_cell = "Household ID";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==3){
												head_cell = "Name of Grantee";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==4){
												head_cell = "Team No";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==5){
												head_cell = "Server No";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==6){
												head_cell = "HH Set";
												sheet.setColumnWidth(c, 2000);
											}
											else if(c==7){
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
										for(reportBean dlist:list){
											
											HSSFRow body_row =   sheet.createRow((short) rw);
											
											for(int j=0;j<8;j++){
												
												if(j==0){
													body_cell = dlist.getMun_name();
												}
												else if(j==1){
													body_cell = dlist.getBrgy_name();
												}
												else if(j==2){
													body_cell = dlist.getHousehold_id();
												}
												else if(j==3){
													body_cell = dlist.getHead_name();
												}
												else if(j==4){
													HSSFCell hc;
													HSSFCellStyle cell = hwb.createCellStyle();
													HSSFFont h = hwb.createFont();
													h.setFontName("Calibri");
													cell.setFont(h);
													hc = body_row.createCell((short) j);
													hc.setCellValue(dlist.getTeam_id());
													hc.setCellStyle(cell);
												}
												else if(j==5){
													HSSFCell hc;
													HSSFCellStyle cell = hwb.createCellStyle();
													HSSFFont h = hwb.createFont();
													h.setFontName("Calibri");
													cell.setFont(h);
													hc = body_row.createCell((short) j);
													hc.setCellValue(dlist.getServer_id());
													hc.setCellStyle(cell);
												}
												else if(j==6){
													body_cell = dlist.getHh_set();
												}
												else if(j==7){
													body_cell = dlist.getSet_group();
												}
												
												if(j == 4 || j == 5){
													
												}
												else{
													HSSFCell hc;
													HSSFCellStyle cell = hwb.createCellStyle();
													HSSFFont h = hwb.createFont();
													h.setFontName("Calibri");
													cell.setFont(h);
													hc = body_row.createCell((short) j);
													hc.setCellValue(body_cell);
													hc.setCellStyle(cell);
												}
												
											}
											rw++;
										}
										
									}
									else{
										status = "emptylist";
									}
								
									
								if(glist.size() != 0){
									HSSFSheet sheet =  hwb.createSheet("GRS Report");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<10;c++){
										if(c==0){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==1){
											head_cell = "Barangay";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==2){
											head_cell = "Date Conducted";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==3){
											head_cell = "Team ID";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==4){
											head_cell = "Household ID";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==5){
											head_cell = "Name of Grantee";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==6){
											head_cell = "GRS Case Type";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==7){
											head_cell = "GRS IDOCTYPE";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==8){
											head_cell = "Notes/Remarks";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==9){
											head_cell = "Grievance Officer";
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
									int rw = 1;
									for(reportBean h: glist){
										HSSFRow body_row =   sheet.createRow((short) rw);
										for(int k=0;k<10;k++){
											if(k==0){
												body_cell = h.getMun_name();
											}
											else if(k==1){
												body_cell = h.getBrgy_name();
											}
											else if(k==2){
												body_cell = h.getDate_coverage();
											}
											else if(k==3){
												body_cell = Integer.toString(h.getTeam_id());
											}
											else if(k==4){
												body_cell = h.getHousehold_id();
											}
											else if(k==5){
												body_cell = h.getHead_name();
											}
											else if(k==6){
												body_cell = h.getGrscasetype();
											}
											else if(k==7){
												body_cell = h.getGrsidoctype();
											}
											else if(k==8){
												body_cell = h.getRemarks();
											}
											else if(k==9){
												body_cell = h.getGrievance_officer();
											}
											
											HSSFCell hc2;
											HSSFCellStyle hcell = hwb.createCellStyle();
											HSSFFont hf = hwb.createFont();
											hf.setFontName("Calibri");
											hcell.setFont(hf);
											hc2 = body_row.createCell((short) k);
											hc2.setCellValue(body_cell);
											hc2.setCellStyle(hcell);
											
										}
										rw++;
									}
									
								}
								else{
									status = "emptygrs";
								}
								FileOutputStream fileOut =  new FileOutputStream(filename);
								hwb.write(fileOut);
								fileOut.close();	
								
							}

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
					else if(op.equals("2")){
					
						ArrayList<reportBean> mlist = new ArrayList<reportBean>();
						ArrayList<reportBean2> blist = new ArrayList<reportBean2>();
						String path = "C:/"+cPath+"/";
						boolean success = (new File(path)).mkdir();
						String fpath = null;
						
						mlist = dao.getTotalPerMunicipality( "fingerprint_tbl_temp");
						blist = dao.getTotalPerMunAndBrgy("fingerprint_tbl_temp");
						//System.out.println("glist:"+glist.size()+" list:"+list.size());
						
						if(mlist.size() != 0 || blist.size() != 0){
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
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = new Date();
							
							//mun_name = dao1.getmunName(mun_id);
							String filename = fpath+"Registration_Summary_Report_"+sdf.format(date)+".xls";
							
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
								
								if(mlist.size() != 0){

									HSSFSheet sheet =  hwb.createSheet("Total Reg Per Municipality");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<2;c++){
										if(c==0){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==1){
											head_cell = "Count Total";
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
									int grand_total = 0;
									for(reportBean dlist:mlist){
										
										HSSFRow body_row =   sheet.createRow((short) rw);
										
										for(int j=0;j<2;j++){
											
											if(j==0){
												body_cell = dlist.getMunicipality();
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(body_cell);
												hc.setCellStyle(cell);
											}
											else if(j==1){
												grand_total = grand_total + dlist.getTotal_mun();
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(dlist.getTotal_mun());
												hc.setCellStyle(cell);
											}
										
										}
										rw++;
										
									}
									
									HSSFRow total_row =   sheet.createRow((short) rw+1);
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 0);
									hc2.setCellValue("Grand Total:");
									hc2.setCellStyle(hcell);
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 1);
									hc2.setCellValue(grand_total);
									hc2.setCellStyle(hcell);
								}
								
								if(blist.size() != 0){
									HSSFSheet sheet =  hwb.createSheet("Total Reg Per Barangay");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<3;c++){
										if(c==0){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==1){
											head_cell = "Barangay";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==2){
											head_cell = "Count Total";
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
									int rw = 1;
									int grand_total = 0;
									for(reportBean2 h: blist){
										HSSFRow body_row =   sheet.createRow((short) rw);
										for(int k=0;k<10;k++){
											if(k==0){
												body_cell = h.getMun_name();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(body_cell);
												hc2.setCellStyle(hcell);
											}
											else if(k==1){
												body_cell = h.getBrgy_name();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(body_cell);
												hc2.setCellStyle(hcell);
											}
											else if(k==2){
												//body_cell = h.getDate_coverage();
												grand_total = grand_total + h.getTotal_brgy();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(h.getTotal_brgy());
												hc2.setCellStyle(hcell);
												
											}
										}
										rw++;
										
									}
									HSSFRow total_row =   sheet.createRow((short) rw+1);
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 1);
									hc2.setCellValue("Grand Total:");
									hc2.setCellStyle(hcell);
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 2);
									hc2.setCellValue(grand_total);
									hc2.setCellStyle(hcell);
									
								}
								FileOutputStream fileOut =  new FileOutputStream(filename);
								hwb.write(fileOut);
								fileOut.close();	
							}
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
					else if(op == "3"){
						String path = "C:/"+cPath+"/";
						boolean success = (new File(path)).mkdir();
						String fpath = null;
						ArrayList<reportBean> mlist = new ArrayList<reportBean>();
						ArrayList<reportBean> blist = new ArrayList<reportBean>();
						ArrayList <reportBean> grsgrouplist = new ArrayList<reportBean>();
						ArrayList <reportBean> grslist = new ArrayList<reportBean>();
						reportBean grsbean = null;
						mlist = dao.getAllMun();
						blist = dao.getAllBrgy();
						
						for(reportBean m:mlist){
							grsgrouplist = dao.getGSRGrouped(m.getMun_id());
							grsbean = new reportBean(m.getMun_id(),m.getGrscasetype(),m.getTotal_mun());
							grslist.add(grsbean);
						}
						
						if(grslist.size() != 0){

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
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = new Date();
							
							//mun_name = dao1.getmunName(mun_id);
							String filename = fpath+"Registration_Summary_Report_"+sdf.format(date)+".xls";
							
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
								
								if(mlist.size() != 0){

									HSSFSheet sheet =  hwb.createSheet("Summary Totals per Municipality");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<2;c++){
										if(c==0){
											head_cell = "GRS Cases";
											sheet.addMergedRegion((new CellRangeAddress(
										            1, //first row (0-based)
										            1, //last row  (0-based)
										            1, //first column (0-based)
										            2  //last column  (0-based));));
										            )));
										}
										else if(c==1){
											head_cell = "Count Total";
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
									int grand_total = 0;
									for(reportBean dlist:mlist){
										
										HSSFRow body_row =   sheet.createRow((short) rw);
										
										for(int j=0;j<2;j++){
											
											if(j==0){
												body_cell = dlist.getMunicipality();
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(body_cell);
												hc.setCellStyle(cell);
											}
											else if(j==1){
												grand_total = grand_total + dlist.getTotal_mun();
												HSSFCell hc;
												HSSFCellStyle cell = hwb.createCellStyle();
												HSSFFont h = hwb.createFont();
												h.setFontName("Calibri");
												cell.setFont(h);
												hc = body_row.createCell((short) j);
												hc.setCellValue(dlist.getTotal_mun());
												hc.setCellStyle(cell);
											}
										
										}
										rw++;
										
									}
									
									HSSFRow total_row =   sheet.createRow((short) rw+1);
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 0);
									hc2.setCellValue("Grand Total:");
									hc2.setCellStyle(hcell);
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 1);
									hc2.setCellValue(grand_total);
									hc2.setCellStyle(hcell);
								}
								
								if(blist.size() != 0){
									HSSFSheet sheet =  hwb.createSheet("Total Reg Per Barangay");
									//HSSFCell cell;
									HSSFRow row=   sheet.createRow((short)0);
									String head_cell = "";
									
									for(int c=0;c<3;c++){
										if(c==0){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==1){
											head_cell = "Barangay";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c==2){
											head_cell = "Count Total";
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
									int rw = 1;
									int grand_total = 0;
									/*for(reportBean2 h: blist){
										HSSFRow body_row =   sheet.createRow((short) rw);
										for(int k=0;k<10;k++){
											if(k==0){
												body_cell = h.getMun_name();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(body_cell);
												hc2.setCellStyle(hcell);
											}
											else if(k==1){
												body_cell = h.getBrgy_name();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(body_cell);
												hc2.setCellStyle(hcell);
											}
											else if(k==2){
												//body_cell = h.getDate_coverage();
												grand_total = grand_total + h.getTotal_brgy();
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) k);
												hc2.setCellValue(h.getTotal_brgy());
												hc2.setCellStyle(hcell);
												
											}
										}
										rw++;
										
									}*/
									HSSFRow total_row =   sheet.createRow((short) rw+1);
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 1);
									hc2.setCellValue("Grand Total:");
									hc2.setCellStyle(hcell);
									hf.setFontName("Calibri");
									hcell.setFont(hf);
									hc2 = total_row.createCell((short) 2);
									hc2.setCellValue(grand_total);
									hc2.setCellStyle(hcell);
									
								}
								FileOutputStream fileOut =  new FileOutputStream(filename);
								hwb.write(fileOut);
								fileOut.close();	
							}
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
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
		
		
	
	}

}
