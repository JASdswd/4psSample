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

/*import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;*/

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;


import DAO.reportDAO;
import bean.reportBean;

/**
 * Servlet implementation class PrintReports
 */
@WebServlet("/PrintReports")
public class PrintReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintReports() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null PrintReports servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
			ArrayList<reportBean> releaseList = new ArrayList<reportBean>();
			String total_release = "";
			PrintWriter out = response.getWriter();
			JSONObject obj = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Date date = new Date();
			
			try{
				System.out.println(request.getParameter("file"));
				releaseList = (ArrayList<reportBean>) session.getAttribute("releaseList");
				total_release = (String) session.getAttribute("total_release");
				String path = "C:\\Users\\postgres.HIS-PC\\Documents\\DSWD Files\\GCASH -SEPT-OCT 2011\\";
				String filename = path+request.getParameter("file")+".xls";
				File file = new File(filename);
				
				if(file.exists()){
					 System.out.println("File already exists");
					 obj.put("data", releaseList);
					 obj.put("total_release", total_release);
					 obj.put("exists", 1);
					 out.print(obj);
					 out.flush();
					 out.close();
				     System.out.println("i'm done");
				}
				else{
					  System.out.println("Successfully saved");
					  HSSFWorkbook hwb=new HSSFWorkbook();
					  HSSFSheet sheet =  hwb.createSheet("Reports");
					  HSSFCell c2;
					  HSSFRow r;
					  
					  HSSFRow rowtitle1=   sheet.createRow((short)1);
					  HSSFRow rowtitle2=   sheet.createRow((short)2);
					  HSSFRow rowdate=   sheet.createRow((short)4);
					  HSSFCellStyle cell1 = hwb.createCellStyle();
					  HSSFFont f1 = hwb.createFont();
						f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
						f1.setFontHeightInPoints((short)13);
					    f1.setFontName("Courier New");
					    f1.setItalic(true);
	
					    sheet.setFitToPage(true);
						cell1.setFont(f1);
						c2 = rowtitle1.createCell((short) 4);
						c2.setCellValue(new HSSFRichTextString("Pantawid Pamilyang Pilipino Program (4PS)"));
						c2.setCellStyle(cell1);
						 
						c2 = rowtitle2.createCell((short) 4);
						c2.setCellValue(new HSSFRichTextString("Department of Social Welfare and Development"));
						c2.setCellStyle(cell1);
					  
						HSSFCellStyle celld1 = hwb.createCellStyle();
						HSSFFont fd1 = hwb.createFont();
						celld1.setFont(fd1);
						celld1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						
						c2 = rowdate.createCell((short) 6);
						c2.setCellValue("Date Printed:");
						c2.setCellStyle(celld1);
						
						HSSFCellStyle celld = hwb.createCellStyle();
						HSSFFont fd = hwb.createFont();
						fd.setBoldweight(Font.BOLDWEIGHT_BOLD);
						fd.setUnderline(Font.U_SINGLE);
						celld.setFont(fd);
						
						c2 = rowdate.createCell((short) 7);
						c2.setCellValue(sdf.format(date));
						c2.setCellStyle(celld);
						
					  String th = "";
					  HSSFRow rowhead=   sheet.createRow((short)6);
					  
						for(int c = 2;c<10;c++){
							
							
							if(c == 2){
								th = "Household ID";
								 sheet.setColumnWidth(c, 6000);
							}
							else if(c == 3){
								th = "Head Name";	
								 sheet.setColumnWidth(c, 6500);
							}
							else if(c == 4){
								th = "Barangay";
								sheet.setColumnWidth(c, 4000);
							}
							else if(c == 5){
								th = "Municipality";
								sheet.setColumnWidth(c, 3500);
							}
							else if(c == 6){
								th = "Date of Transaction";
								sheet.setColumnWidth(c, 6000);
							}
							else if(c == 7){
								th = "Date Received";
								sheet.setColumnWidth(c, 4000);
							}
							else if(c == 8){
								th = "Time Received";
								sheet.setColumnWidth(c, 4000);
							}
							else if(c == 9){
								th = "Amount Received";
								sheet.setColumnWidth(c, 2500);
							}
							HSSFCellStyle cell = hwb.createCellStyle();
							HSSFFont f = hwb.createFont();
							f.setBoldweight(Font.BOLDWEIGHT_BOLD);
							cell.setFont(f);
							c2 = rowhead.createCell((short) c);
							c2.setCellValue(th);
							c2.setCellStyle(cell);
							 
							 
						}
						
						
						short row_num = 7;
						
						String val = "";
						for(reportBean list: releaseList){
							HSSFRow row=   sheet.createRow((short) row_num);
							for(short col = 2;col<10;col++){
								
								if(col == 2){
									val = list.getHousehold_id();
								}
								else if(col == 3){
									val = list.getHead_name();
								}
								else if(col == 4){
									val = list.getBrgy();			
								}
								else if(col == 5){
									val = list.getMunicipality();
								}
								else if(col == 6){
									val = list.getDate_coverage();
								}
								else if(col == 7){
									val = list.getDate_receive();
								}
								else if(col == 8){
									val = list.getTime_receive();
								}
								else if(col == 9){
									val = list.getAmount_receive();
								}
								 row.createCell((short) col).setCellValue(val);
							}
							row_num++;
						}
						
						 HSSFRow rowtotal =   sheet.createRow((short)row_num+3);
						 HSSFCellStyle cell = hwb.createCellStyle();
						 HSSFFont f = hwb.createFont();
						 f.setBoldweight(Font.BOLDWEIGHT_BOLD);
						 cell.setFont(f);
						 c2 = rowtotal.createCell((short) 8);
						 c2.setCellValue("Total Release:");
						 c2.setCellStyle(cell);
						 rowtotal.createCell((short) 9).setCellValue("₱ "+total_release+".00");
						
						  FileOutputStream fileOut =  new FileOutputStream(filename);
						  hwb.write(fileOut);
						  fileOut.close();
					
					     //JOptionPane.showMessageDialog(null, "Please check your file at "+filename);
					  
						 obj.put("data", releaseList);
						 obj.put("total_release",   total_release);
						 obj.put("exists", 0);
						 obj.put("path", filename);
						 out.print(obj);
						 out.flush();
						 out.close();
					     System.out.println("i'm done");
						
				}
						
						
						
						/*WorkbookSettings wbSettings = new WorkbookSettings();
				        WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), wbSettings);
						workbook.createSheet("Report", 0);
						WritableSheet excelSheet = workbook.getSheet(0);
						WritableCellFormat timesBoldUnderline;
						WritableCellFormat timesBoldUnderLine11;
						WritableCellFormat times;
						String inputFile;
			
						WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
						// Define the cell format
						times = new WritableCellFormat(times10pt);
						// Lets automatically wrap the cells
						times.setWrap(true);
			
						// Create create a bold font with unterlines
						WritableFont times10ptBoldUnderline = new WritableFont(
								WritableFont.TIMES, 12, WritableFont.BOLD, false,
								UnderlineStyle.SINGLE);
						timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
						// Lets automatically wrap the cells
						timesBoldUnderline.setWrap(true);
			
						WritableFont timesBoldUnderLine = new WritableFont(
								WritableFont.TIMES, 11, WritableFont.BOLD, false,
								UnderlineStyle.SINGLE);
						timesBoldUnderLine11 = new WritableCellFormat(timesBoldUnderLine);
						timesBoldUnderLine11.setWrap(true);
						
						CellView cv = new CellView();
						cv.setFormat(times);
						cv.setFormat(timesBoldUnderline);
						
						// add header columns format (column, row,text, times)
						
						Label label;
						String th = "";
						for(int c = 2;c<10;c++){
							
							if(c == 2){
								th = "Household ID";
								
							}
							else if(c == 3){
								th = "Head Name";		
							}
							else if(c == 4){
								th = "Barangay";
							}
							else if(c == 5){
								th = "Municipality";
							}
							else if(c == 6){
								th = "Date of Transaction";
							}
							else if(c == 7){
								th = "Date Received";
							}
							else if(c == 8){
								th = "Time Received";
							}
							else if(c == 9){
								th = "Amount Received";
							}
							
							
							label = new Label( c, 5,th , timesBoldUnderline);
							excelSheet.addCell(label);
						}
						
						int row = 6;
						String val = "";
						for(reportBean list: releaseList){
							for(int col = 2;col<10;col++){
								
								if(col == 2){
									val = list.getHousehold_id();
									//excelSheet.getSettings().setDefaultColumnWidth(25);
								}
								else if(col == 3){
									val = list.getHead_name();
									//excelSheet.getSettings().setDefaultColumnWidth(25);
								}
								else if(col == 4){
									val = list.getBrgy();			
									//excelSheet.getSettings().setDefaultColumnWidth(15);
								}
								else if(col == 5){
									val = list.getMunicipality();
									//excelSheet.getSettings().setDefaultColumnWidth(10);
								}
								else if(col == 6){
									val = list.getDate_coverage();
									//excelSheet.getSettings().setDefaultColumnWidth(15);
								}
								else if(col == 7){
									val = list.getDate_receive();
									//excelSheet.getSettings().setDefaultColumnWidth(18);
								}
								else if(col == 8){
									val = list.getTime_receive();
									//excelSheet.getSettings().setDefaultColumnWidth(18);
								}
								else if(col == 9){
									val = Float.toString(list.getAmount_receive());
									//excelSheet.getSettings().setDefaultColumnWidth(20);
								}
								
								label = new Label( col,row, val, times);
								excelSheet.addCell(label);
								
							}
							row++;
						}
						
						label = new Label( 8,row+3,"Total Release: ", timesBoldUnderLine11);
						excelSheet.addCell(label);
						label = new Label( 9,row+3,Float.toString(total_release), times);
						excelSheet.addCell(label);
						excelSheet.getSettings().setDefaultColumnWidth(20);
						
						 workbook.write();
					     workbook.close();  
						*/
						  
					
					     
					}
					catch(Exception ex){
						System.out.println(ex);
					}
					 /*out.flush();
					 out.close();*/
				}
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		
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
				ArrayList<reportBean> munList = new ArrayList<reportBean>();
				ArrayList<reportBean> brgyList = new ArrayList<reportBean>();
				reportBean bean = null;
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				int op = Integer.parseInt(request.getParameter("op"));
				
				try{
					reportDAO dao = new reportDAO();
					if(op == 1){
						munList = dao.getMunList();
						
						obj.put("data", munList);
						out.print(obj);
						out.flush();
						out.close();
					}
					else if(op == 2){
						String mun = request.getParameter("mun");
						
						brgyList = dao.getBrgy(mun);				
						obj.put("data", brgyList);
						out.print(obj);
						out.flush();
						out.close();
					}
					else if(op == 3){
						munList = dao.getAllMun();
						
						obj.put("data", munList);
						out.print(obj);
						out.flush();
						out.close();
					}
					
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
		}
		
	}

}
