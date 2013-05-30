package parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONObject;

import DAO.BaseDAO;
import bean.reportBean;

/**
 * Servlet implementation class DownloadChanges
 */
@WebServlet("/DownloadChanges")
public class DownloadChanges extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadChanges() {
        super();
        // TODO Auto-generated constructor stub
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
		System.out.println("In sa Download Changes");
		HttpSession session = request.getSession();
		String cPath = request.getContextPath();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		ArrayList<reportBean> upList = new ArrayList<reportBean>();
		ArrayList<reportBean> addList = new ArrayList<reportBean>();
		ArrayList<reportBean> delList = new ArrayList<reportBean>();
		ArrayList<reportBean> duplicateList = new ArrayList<reportBean>();
		
		upList= (ArrayList<reportBean>) session.getAttribute("upList");
		addList= (ArrayList<reportBean>) session.getAttribute("addList");
		delList= (ArrayList<reportBean>) session.getAttribute("delList");
		String fname = request.getParameter("file");
		int upctr = 0;
		int addctr = 0;
		int delctr = 0;
		
		
		upctr = upList.size();
		addctr = addList.size();
		delctr = delList.size();
		
		try{BaseDAO dao = new BaseDAO();
			String fpath = null;
			String path = "C:/"+cPath+"/";
			boolean success = (new File(path)).mkdir();
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
				
				System.out.println("error in making directory");
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
			
			String fileName  = fpath + fname + ".xls";
			File file = new File(fileName);
			System.out.println(fileName);
			if(file.exists()){
				System.out.println("File already exists");
			}
			else{
				
				  HSSFWorkbook hwb=new HSSFWorkbook();
				  HSSFSheet sheet =  hwb.createSheet("Reports");
				  HSSFCell c2;
				  HSSFRow rowtitle1=   sheet.createRow((short)1);
				  HSSFRow rowtitle2=   sheet.createRow((short)2);
				  HSSFRow rowtitle4=   sheet.createRow((short)7);
				  HSSFCellStyle cell1 = hwb.createCellStyle();
				  HSSFFont f1 = hwb.createFont();
					f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
					f1.setFontHeightInPoints((short)13);
				    f1.setFontName("Courier New");
				    f1.setItalic(true);
				
				HSSFCellStyle cell2 = hwb.createCellStyle();    
			    HSSFFont f2 = hwb.createFont();
				f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
				f2.setFontHeightInPoints((short)11);
			    f2.setFontName("Courier New");
			    f2.setItalic(false);

				    sheet.setFitToPage(true);
					cell1.setFont(f1);
					c2 = rowtitle1.createCell((short) 2);
					c2.setCellValue(new HSSFRichTextString("Pantawid Pamilyang Pilipino Program (4PS)"));
					c2.setCellStyle(cell1);
					 
					c2 = rowtitle2.createCell((short) 2);
					c2.setCellValue(new HSSFRichTextString("Department of Social Welfare and Development"));
					c2.setCellStyle(cell1);
					int row = 8;
					
					if(upctr != 0){
						
						HSSFRow hrow1=   sheet.createRow((short)5);
						c2 = hrow1.createCell((short) 2);
						c2.setCellValue(new HSSFRichTextString("Updated Grantee and Family Members Data"));
						c2.setCellStyle(cell1);
						
						for(int i=1;i<12;i++){
							
							String cellValue = "";
							
							if(i == 1){
								cellValue = "Municipality";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 2){
								cellValue = "Barangay";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 3){
								cellValue = "Household ID";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 4){
								cellValue = "Household Member ID";
								sheet.setColumnWidth(i, 5000);
							}
							else if(i == 5){
								cellValue = "Name";
								sheet.setColumnWidth(i, 6500);
							}
							else if(i == 6){
								cellValue = "Grantee";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 7){
								cellValue = "Gender";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 8){
								cellValue = "Birthday";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 9){
								cellValue = "Relationship to HH Head";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 10){
								cellValue = "Pregnant";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 11){
								cellValue = "Attending School";
								sheet.setColumnWidth(i, 4000);
							}
							
							sheet.setFitToPage(true);
							cell2.setFont(f2);
							c2 = rowtitle4.createCell((short) i);
							c2.setCellValue(new HSSFRichTextString(cellValue));
							c2.setCellStyle(cell2);
						}
						
						for(reportBean l: upList){
							
							HSSFRow rowtitle=   sheet.createRow((short)row);
							HSSFCellStyle cell = hwb.createCellStyle();    
						    HSSFFont f = hwb.createFont();
							f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
							f.setFontHeightInPoints((short)11);
						    f.setFontName("Courier New");
						    f.setItalic(false);
						    sheet.setFitToPage(true);
							cell.setFont(f);
							
							for(int i=1;i<12;i++){
								
								
								String cellValue = "";
								
								if(i == 1){
									cellValue = l.getMun_name();
								}
								else if(i == 2){
									cellValue = l.getBrgy_name();
								}
								else if(i == 3){
									cellValue = l.getHousehold_id();
								}
								else if(i == 4){
									cellValue = l.getHmember_id();
								}
								else if(i == 5){
									cellValue = l.getName();
								}
								else if(i == 6){
									if(l.getStatus() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
									
								}
								else if(i == 7){
									cellValue = l.getGender();
								}
								else if(i == 8){
									cellValue = l.getBday();
								}
								else if(i == 9){
									System.out
											.println("status:"+l.getStatus());
									if(l.getStatus() == 1){
										cellValue = "1 - Head";
									}
									else if(l.getStatus() == 2){
										cellValue = "2 - Wife Spouse";
									}
									else if(l.getStatus() == 3){
										cellValue = "3 - Son Daughter";
									}
									else if(l.getStatus() == 5){
										cellValue = "5 - GrandSon GrandDaughter";
									}
									else if(l.getStatus() == 6){
										cellValue = "6 - Son-in-law   Daughter-in-law";
									}
								}
								else if(i == 10){
									if(l.getPregnant() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								else if(i == 11){
									if(l.getAttending_school() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								
								
								c2 = rowtitle.createCell((short) i);
								c2.setCellValue(new HSSFRichTextString(cellValue));
								c2.setCellStyle(cell);
							}
							row++;
						}
					}
					
					int row2 = row+4;
					int row3 = row2+1;
					if(addctr != 0){
						HSSFRow rowtitle5=   sheet.createRow((short)row2);
						HSSFRow hrowtitle5=   sheet.createRow((short)row +2);
						c2 = hrowtitle5.createCell((short) 2);
						c2.setCellValue(new HSSFRichTextString("New Family Members"));
						c2.setCellStyle(cell1);
						for(int i=1;i<12;i++){
							
							String cellValue = "";
							
							if(i == 1){
								cellValue = "Municipality";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 2){
								cellValue = "Barangay";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 3){
								cellValue = "Household ID";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 4){
								cellValue = "Household Member ID";
								sheet.setColumnWidth(i, 5000);
							}
							else if(i == 5){
								cellValue = "Name";
								sheet.setColumnWidth(i, 6500);
							}
							else if(i == 6){
								cellValue = "Grantee";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 7){
								cellValue = "Gender";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 8){
								cellValue = "Birthday";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 9){
								cellValue = "Relationship to HH Head";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 10){
								cellValue = "Pregnant";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 11){
								cellValue = "Attending School";
								sheet.setColumnWidth(i, 4000);
							}
							
							sheet.setFitToPage(true);
							cell2.setFont(f2);
							c2 = rowtitle5.createCell((short) i);
							c2.setCellValue(new HSSFRichTextString(cellValue));
							c2.setCellStyle(cell2);
						}
						
						for(reportBean k: addList){
							
							HSSFRow rowtitle=   sheet.createRow((short)row3);
							HSSFCellStyle cell = hwb.createCellStyle();    
						    HSSFFont f = hwb.createFont();
							f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
							f.setFontHeightInPoints((short)11);
						    f.setFontName("Courier New");
						    f.setItalic(false);
						    sheet.setFitToPage(true);
							cell.setFont(f);
							
							for(int i=1;i<12;i++){
								
								
								String cellValue = "";
								
								if(i == 1){
									cellValue = k.getMun_name();
								}
								else if(i == 2){
									cellValue = k.getBrgy_name();
								}
								else if(i == 3){
									cellValue = k.getHousehold_id();
								}
								else if(i == 4){
									cellValue = k.getHmember_id();
								}
								else if(i == 5){
									cellValue = k.getName();
								}
								else if(i == 6){
									if(k.getStatus() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
									
								}
								else if(i == 7){
									cellValue = k.getGender();
								}
								else if(i == 8){
									cellValue = k.getBday();
								}
								else if(i == 9){
									System.out
											.println("status:"+k.getStatus());
									if(k.getStatus() == 1){
										cellValue = "1 - Head";
									}
									else if(k.getStatus() == 2){
										cellValue = "2 - Wife Spouse";
									}
									else if(k.getStatus() == 3){
										cellValue = "3 - Son Daughter";
									}
									else if(k.getStatus() == 5){
										cellValue = "5 - GrandSon GrandDaughter";
									}
									else if(k.getStatus() == 6){
										cellValue = "6 - Son-in-law   Daughter-in-law";
									}
								}
								else if(i == 10){
									if(k.getPregnant() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								else if(i == 11){
									if(k.getAttending_school() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								
								
								c2 = rowtitle.createCell((short) i);
								c2.setCellValue(new HSSFRichTextString(cellValue));
								c2.setCellStyle(cell);
							}
							row3++;
						}
					}
					
					int row4 = row3+4;
					int row5 = row4+1;
					
					if(delctr != 0){

						HSSFRow rowtitle6=   sheet.createRow((short)row4);
						HSSFRow hrowtitle6=   sheet.createRow((short)row3 +2);
						c2 = hrowtitle6.createCell((short) 2);
						c2.setCellValue(new HSSFRichTextString("Deleted Members Turn into Grantee"));
						c2.setCellStyle(cell1);
						for(int i=1;i<12;i++){
							
							String cellValue = "";
							
							if(i == 1){
								cellValue = "Municipality";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 2){
								cellValue = "Barangay";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 3){
								cellValue = "Household ID";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 4){
								cellValue = "Household Member ID";
								sheet.setColumnWidth(i, 5000);
							}
							else if(i == 5){
								cellValue = "Name";
								sheet.setColumnWidth(i, 6500);
							}
							else if(i == 6){
								cellValue = "Grantee";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 7){
								cellValue = "Gender";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 8){
								cellValue = "Birthday";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 9){
								cellValue = "Relationship to HH Head";
								sheet.setColumnWidth(i, 6000);
							}
							else if(i == 10){
								cellValue = "Pregnant";
								sheet.setColumnWidth(i, 4000);
							}
							else if(i == 11){
								cellValue = "Attending School";
								sheet.setColumnWidth(i, 4000);
							}
							
							sheet.setFitToPage(true);
							cell2.setFont(f2);
							c2 = rowtitle6.createCell((short) i);
							c2.setCellValue(new HSSFRichTextString(cellValue));
							c2.setCellStyle(cell2);
						}
						
						for(reportBean k: delList){
							
							HSSFRow rowtitle=   sheet.createRow((short)row5);
							HSSFCellStyle cell = hwb.createCellStyle();    
						    HSSFFont f = hwb.createFont();
							f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
							f.setFontHeightInPoints((short)11);
						    f.setFontName("Courier New");
						    f.setItalic(false);
						    sheet.setFitToPage(true);
							cell.setFont(f);
							
							for(int i=1;i<12;i++){
								
								
								String cellValue = "";
								
								if(i == 1){
									cellValue = k.getMun_name();
								}
								else if(i == 2){
									cellValue = k.getBrgy_name();
								}
								else if(i == 3){
									cellValue = k.getHousehold_id();
								}
								else if(i == 4){
									cellValue = k.getHmember_id();
								}
								else if(i == 5){
									cellValue = k.getName();
								}
								else if(i == 6){
									if(k.getStatus() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
									
								}
								else if(i == 7){
									cellValue = k.getGender();
								}
								else if(i == 8){
									cellValue = k.getBday();
								}
								else if(i == 9){
									System.out
											.println("status:"+k.getStatus());
									if(k.getStatus() == 1){
										cellValue = "1 - Head";
									}
									else if(k.getStatus() == 2){
										cellValue = "2 - Wife Spouse";
									}
									else if(k.getStatus() == 3){
										cellValue = "3 - Son Daughter";
									}
									else if(k.getStatus() == 5){
										cellValue = "5 - GrandSon GrandDaughter";
									}
									else if(k.getStatus() == 6){
										cellValue = "6 - Son-in-law   Daughter-in-law";
									}
								}
								else if(i == 10){
									if(k.getPregnant() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								else if(i == 11){
									if(k.getAttending_school() == 1){
										cellValue = "Yes";
									}
									else{
										cellValue = "";
									}
								}
								
								
								c2 = rowtitle.createCell((short) i);
								c2.setCellValue(new HSSFRichTextString(cellValue));
								c2.setCellStyle(cell);
							}
							row5++;
						}
						
					}
					
					System.out.println("row:"+row);
					
					FileOutputStream fileOut =  new FileOutputStream(fileName);
					hwb.write(fileOut);
					fileOut.close();	
				
			}
			
			obj.put("file", fileName);
			out.print(obj);
			out.flush();
			out.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		
		
	}

}
