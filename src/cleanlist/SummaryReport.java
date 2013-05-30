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

import cleanlist.SummaryDAO;
import DAO.BaseDAO;
import bean.reportBean;
import bean.reportBean2;

/**
 * Servlet implementation class SummaryReport
 */
@WebServlet("/SummaryReport")
public class SummaryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SummaryReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
					System.out.println("in here sa summary reportpost");
					String status = "exists";
					JSONObject obj = new JSONObject();
					PrintWriter out = response.getWriter();
					String cPath = request.getContextPath();
					String path = "C:/"+cPath+"/";
					boolean success = (new File(path)).mkdir();
					String fpath = null;
					
					ArrayList<reportBean2> grslist = new ArrayList<reportBean2>();
					ArrayList<reportBean2> grslist2 = new ArrayList<reportBean2>();
					ArrayList<reportBean2> solist = new ArrayList<reportBean2>();
					ArrayList<reportBean2> coalist = new ArrayList<reportBean2>();
					ArrayList<reportBean2> fplist = new ArrayList<reportBean2>();
					ArrayList<reportBean> householdlist = new ArrayList<reportBean>();
					ArrayList<reportBean> cleanlist = new ArrayList<reportBean>();
					SummaryDAO dao = new SummaryDAO();
					BaseDAO dao1 = new BaseDAO();
					String mun = request.getParameter("mun");
					grslist2 = dao.getAllGRSCases("grscases2_tbl_temp",mun);
					coalist = dao.getAllCOACases("coacases2_tbl",mun);
					grslist = dao.getAllGRSCases("grscases_tbl_temp",mun);
					solist = dao.getAllSO("system_onhold_tbl",mun);
					householdlist = dao.getAllReceivedList(mun);
					fplist = dao.getRegistered(mun,"fingerprint_tbl_temp");
					int countgrs = 0;
					int countso = 0;
					
					System.out.println("grs:"+grslist.size());
					System.out.println("so:"+solist.size());
					System.out.println("grs2:"+grslist2.size());
					System.out.println("hh:"+householdlist.size());
					System.out.println("fp:"+fplist.size());
					
					for(reportBean l: householdlist){
						reportBean hlist = new reportBean();
						hlist.setF_position("");
						hlist.setDate_receive("");
						hlist.setGstatus("");
						hlist.setGrscasetype("");
						hlist.setGrsidoctype("");
						System.out.println("in sa hhlist loop");
						
						for(reportBean2 g: grslist2){
								System.out.println("in sa grs loop");
								if(l.getHousehold_id().equalsIgnoreCase(g.getHousehold_id())){
									hlist.setGstatus(g.getHead_name());
									//grsfound = "true";
									break;
								}
						}
						for(reportBean2 g: grslist){
							System.out.println("in sa grs loop");
							if(l.getHousehold_id().equalsIgnoreCase(g.getHousehold_id())){
								hlist.setGrscasetype(g.getHead_name());//change bean
								//grsfound = "true";
								break;
							}
					}
						for(reportBean2 s: solist){
							System.out.println("in sa so loop");
							if(l.getHousehold_id().equalsIgnoreCase(s.getHousehold_id())){
								hlist.setF_position(s.getHead_name());
								//sofound = "true";
								countso++;
								break;
							}
						}
						for(reportBean2 c: coalist){
							System.out.println("in sa so loop");
							if(l.getHousehold_id().equalsIgnoreCase(c.getHousehold_id())){
								hlist.setGrsidoctype(c.getHead_name());
								//sofound = "true";
								countso++;
								break;
							}
						}
						for(reportBean2 f: fplist){
							System.out.println("in sa fp loop");
							if(l.getHousehold_id().equalsIgnoreCase(f.getHousehold_id())){
								hlist.setDate_receive(f.getHead_name());
								//fpfound = 1;
								break;
							}
						}
						hlist.setSet_group(l.getSet_group());
						hlist.setHh_set(l.getHh_set());
						hlist.setHousehold_id(l.getHousehold_id());
						hlist.setHead_name(l.getHead_name());
						hlist.setBrgy(l.getBrgy());
						hlist.setMunicipality(l.getMunicipality());
						cleanlist.add(hlist);
					}
					
					System.out.println("clist:"+cleanlist.size());
					
					if(cleanlist.size() != 0){
						System.out.println("in sa cleanlist if");
						
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
						String filename = fpath+"SummaryList_"+mun_name+"_"+sdf.format(date)+".xls";
						
						System.out.println(mun_name);
						
						File file = new File(filename);
						
						System.out.println(filename);
						System.out.println("grscount:"+countgrs);
						System.out.println("socount:"+countso);
						
						if(file.exists()){
							System.out.println("File already exists..");
							status="exists";
						}
						else{
							System.out.println("in sa file not exists..");
							status = "not";
							HSSFWorkbook hwb=new HSSFWorkbook();
							HSSFCell hc2;
							HSSFCellStyle hcell = hwb.createCellStyle();
							HSSFFont hf = hwb.createFont();
							
								HSSFSheet sheet =  hwb.createSheet("Summary List of "+mun_name.toUpperCase());
								//HSSFCell cell;
								HSSFRow row=   sheet.createRow((short)0);
								String head_cell = "";
								
								for(int c=0;c<11;c++){
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
									else if(c==6){
										head_cell = "Registration Status";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==7){
										head_cell = "GRS Case";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==8){
										head_cell = "System On Hold";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==9){
										head_cell = "List of MRD Cases";
										sheet.setColumnWidth(c, 2000);
									}
									else if(c==10){
										head_cell = "COA Cases";
										sheet.setColumnWidth(c, 2000);
									}
									
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
									
									for(int j=0;j<11;j++){
										body_cell = "";
										if(j==0){
											body_cell = dlist.getHousehold_id().trim().toUpperCase();
										}
										else if(j==1){
											body_cell = dlist.getHead_name().trim().toUpperCase();
										}
										else if(j==2){
											body_cell = dlist.getBrgy().trim().toUpperCase();
										}
										else if(j==3){
											body_cell = dlist.getMunicipality().trim().toUpperCase();
										}
										else if(j==4){
											 	hf.setBoldweight((short)0);
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) j);
												hc2.setCellValue(dlist.getHh_set());
												hc2.setCellStyle(hcell);
										}
										else if(j==5){
											body_cell = dlist.getSet_group().trim().toUpperCase();
										}
										else if(j==6){
											body_cell = dlist.getDate_receive().trim() ;
										}
										else if(j==7){
											body_cell = dlist.getGstatus().trim().toUpperCase();
										}
										else if(j==8){
											body_cell = dlist.getF_position().trim().toUpperCase();
										}
										else if(j==9){
											body_cell = dlist.getGrscasetype().trim().toUpperCase();
										}
										else if(j==10){
											body_cell = dlist.getGrsidoctype().trim().toUpperCase();
										}
										
										   if(j!=4){
											   hf.setBoldweight((short)0);
												hf.setFontName("Calibri");
												hcell.setFont(hf);
												hc2 = body_row.createCell((short) j);
												hc2.setCellValue(body_cell);
												hc2.setCellStyle(hcell);
										   }
										
									}
									rw++;
									
								}

								
								FileOutputStream fileOut =  new FileOutputStream(filename);
								hwb.write(fileOut);
								fileOut.close();	
							
							
							
						}
						obj.put("path", filename);
						

					}else{
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
