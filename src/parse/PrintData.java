package parse;

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
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONObject;


import DAO.reportDAO;
import bean.reportBean;

/**
 * Servlet implementation class PrintData
 */
@WebServlet("/PrintData")
public class PrintData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cPath = request.getContextPath();
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
				
				ArrayList<reportBean> catchList = new ArrayList<reportBean>();
				ArrayList<reportBean> dList = new ArrayList<reportBean>();
				catchList = (ArrayList<reportBean>) session.getAttribute("clist");
				dList= (ArrayList<reportBean>) session.getAttribute("dlist");
				String status = "exists";
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					String fpath = null;
					String path = "C:/"+cPath+"/";
					boolean success = (new File(path)).mkdir();
					if(success){
						fpath = path;
						System.out.println("created");
					}
					else{
						String path2 = "Documents"+cPath+"/";
						boolean succ = (new File(path2)).mkdir();
						
						if(succ){
							fpath = path2;
							System.out.println("Created a directory at desktop not windows");
						}
						
						System.out.println("error in making directory");
					}
					
					if(fpath == null){
						String path1 = "C:/"+cPath+"/";
						String path2 = "Documents"+cPath+"/";
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
					String filename = fpath+request.getParameter("file")+".xls";
					
					File file = new File(filename);
					
					if(file.exists()){
						System.out.println("File already exists..");
					}
					else{
						status = "not";
						  reportDAO dao = new reportDAO();
						  System.out.println("in sa else!!!!!!!!!!!!!!!!!!!!!!!!!!11");
						  HSSFWorkbook hwb=new HSSFWorkbook();
						  HSSFSheet sheet =  hwb.createSheet("Reports");
						  HSSFCell c2;
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
							
							c2 = rowdate.createCell((short) 9);
							c2.setCellValue("Date Printed:");
							c2.setCellStyle(celld1);
							
							HSSFCellStyle celld = hwb.createCellStyle();
							HSSFFont fd = hwb.createFont();
							fd.setBoldweight(Font.BOLDWEIGHT_BOLD);
							fd.setUnderline(Font.U_SINGLE);
							celld.setFont(fd);
							
							c2 = rowdate.createCell((short) 10);
							c2.setCellValue(sdf.format(date));
							c2.setCellStyle(celld);
							
							short row_num = 9;
							int c = 0;
							String val = "";
							for(reportBean l:catchList){
								HSSFRow row=   sheet.createRow((short) row_num);
								
								if(l.getStatus() == 1){
									
									if(c == 0){
										
										HSSFRow row1=   sheet.createRow((short) 6);
										HSSFCell c1;
										HSSFCellStyle cell = hwb.createCellStyle();
										HSSFFont f = hwb.createFont();
											f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
											f.setFontHeightInPoints((short)13);
										    f.setFontName("Courier New");
										    f.setItalic(false);
						
										    sheet.setFitToPage(true);
											cell.setFont(f);
											c1 = row1.createCell((short) 3);
											c1.setCellValue(new HSSFRichTextString("Duplicates Found in Household Table"));
											c1.setCellStyle(cell);
											c = 1;
										HSSFRow hrow=   sheet.createRow((short) 8);
										String th = "";
										for(short hcol = 1;hcol<10;hcol++){
											
											
											String cellValue = "";
											
											if(hcol == 1){
												cellValue = "Municipality";
												sheet.setColumnWidth(hcol, 6000);
											}
											else if(hcol == 2){
												cellValue = "Barangay";
												sheet.setColumnWidth(hcol, 6000);
											}
											else if(hcol == 3){
												cellValue = "Household ID";
												sheet.setColumnWidth(hcol, 6000);
											}
											else if(hcol == 4){
												cellValue = "Household Member ID";
												sheet.setColumnWidth(hcol, 5000);
											}
											else if(hcol == 5){
												cellValue = "Name";
												sheet.setColumnWidth(hcol, 6500);
											}
											else if(hcol == 6){
												cellValue = "Grantee";
												sheet.setColumnWidth(hcol, 4000);
											}
											else if(hcol == 7){
												cellValue = "Gender";
												sheet.setColumnWidth(hcol, 4000);
											}
											else if(hcol == 8){
												cellValue = "Birthday";
												sheet.setColumnWidth(hcol, 4000);
											}
											else if(hcol == 9){
												cellValue = "Relationship to HH Head";
												sheet.setColumnWidth(hcol, 6000);
											}
											HSSFCell hc2;
											HSSFCellStyle hcell = hwb.createCellStyle();
											HSSFFont hf = hwb.createFont();
											hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
											hcell.setFont(hf);
											hc2 = hrow.createCell((short) hcol);
											hc2.setCellValue(th);
											hc2.setCellStyle(hcell);
										}
									}
									
									for(short i = 1;i<10;i++){
										
										
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
											if(l.getGstatus().equals("1")){
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
											if(l.getF_position().equals("1")){
												cellValue = "1 - Head";
											}
											else if(l.getF_position().equals("2")){
												cellValue = "2 - Wife Spouse";
											}
											else if(l.getF_position().equals("3")){
												cellValue = "3 - Son Daughter";
											}
											else if(l.getF_position().equals("4")){
												cellValue = "4 - Brother / Sister";
											}
											else if(l.getF_position().equals("5")){
												cellValue = "5 - Son-in-law / Daughter-in-law";
											}
											else if(l.getF_position().equals("6")){
												cellValue = "6 - GrandSon / GrandDaughter";
											}
											else if(l.getF_position().equals("7")){
												cellValue = "7 - Father / Mother";
											}
											else if(l.getF_position().equals("8")){
												cellValue = "8 - Other Relatives";
											}
											else if(l.getF_position().equals("9")){
												cellValue = "9 - Boarders";
											}
											else if(l.getF_position().equals("10")){
												cellValue = "10 - Domestic Helper";
											}
											else if(l.getF_position().equals("11")){
												cellValue = "11 - Non-relative";
											}
											else if(l.getF_position().equals("12")){
												cellValue = "12 - Grandfather / GrandMother";
											}
											else if(l.getF_position().equals("13")){
												cellValue = "13 - Uncle / Auntie";
											}
											else if(l.getF_position().equals("14")){
												cellValue = "14 - Nephew / Niece";
											}
										}
										if(i==4){
											row.createCell((short) i).setCellValue(Integer.parseInt(cellValue));
										}
										else{
											row.createCell((short) i).setCellValue(cellValue);
										}
									}
									row_num++;
								}
								
							}
							
							
							row_num++;
							c = 0;
							for(reportBean l:catchList){
								
								
								if(l.getStatus() == 2){
									System.out.println("in sa status = 2"+c);
									if(c == 0){
										
										HSSFRow row12=   sheet.createRow((short) row_num);
										System.out.println("rownum inside c=0;"+row_num);
										HSSFCell c12;
										HSSFCellStyle cell2 = hwb.createCellStyle();
										HSSFFont f2 = hwb.createFont();
											f2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
											f2.setFontHeightInPoints((short)13);
										    f2.setFontName("Courier New");
										    f2.setItalic(false);
						
										    sheet.setFitToPage(true);
											cell2.setFont(f2);
											c12 = row12.createCell((short) 3);
											c12.setCellValue(new HSSFRichTextString("Duplicates Found in Spouse Table"));
											c12.setCellStyle(cell2);
											c = 1;
											row_num++;
											row_num++;
											HSSFRow hrow=   sheet.createRow((short) row_num);
											String th = "";
											for(short hcol = 2;hcol<7;hcol++){
												
												
												if(hcol == 2){
													th = "Household ID No.";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 3){
													th = "HouseholdMember ID No.";
													 sheet.setColumnWidth(hcol, 5000);
												}
												else if(hcol == 4){
													th = "Name of Spouse";
													 sheet.setColumnWidth(hcol, 6500);
												}
												else if(hcol == 5){
													th = "Barangay";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 6){
													th = "Municipality";
													 sheet.setColumnWidth(hcol, 6000);
												}
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
												hcell.setFont(hf);
												hc2 = hrow.createCell((short) hcol);
												hc2.setCellValue(th);
												hc2.setCellStyle(hcell);
											}
											row_num++;
									}
									
									HSSFRow row=   sheet.createRow((short) row_num);
									System.out.println("rownum outside c=0;"+row_num);
									for(short col = 2;col<7;col++){
										System.out.println("col:"+col);
										if(col == 2){
											val = l.getHousehold_id();
										}
										else if(col == 3){
											val = l.getHmember_id();
										}
										else if(col == 4){
											val = l.getName();			
										}
										else if(col == 5){
											
											val = 	dao.getbrgy_name(l.getBrgy_id());	
											System.out.println("Brgy:"+val+"--"+l.getBrgy_id());
										}
										else if(col == 6){
											val = dao.getmun_name(l.getMun_id());		
											System.out.println("Brgy:"+val+"--"+l.getMun_id());
										}
										 row.createCell((short) col).setCellValue(val);
									}
									row_num++;
								}
								
							}
							row_num++;
							c = 0;
							for(reportBean l:catchList){
								
								
								if(l.getStatus() == 3){
									//System.out.println("in sa status = 3"+c);
									if(c == 0){
										
										HSSFRow row13=   sheet.createRow((short) row_num);
										HSSFCell c13;
										HSSFCellStyle cell3 = hwb.createCellStyle();
										HSSFFont f3 = hwb.createFont();
											f3.setBoldweight(Font.BOLDWEIGHT_NORMAL);
											f3.setFontHeightInPoints((short)13);
										    f3.setFontName("Courier New");
										    f3.setItalic(false);
						
										    sheet.setFitToPage(true);
											cell3.setFont(f3);
											c13 = row13.createCell((short) 3);
											c13.setCellValue(new HSSFRichTextString("Duplicates Found in Children Table"));
											c13.setCellStyle(cell3);
											c= 1;
											row_num++;
											row_num++;
											HSSFRow hrow=   sheet.createRow((short) row_num);
											String th = "";
											for(short hcol = 2;hcol<7;hcol++){
												
												
												if(hcol == 2){
													th = "Household ID No.";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 3){
													th = "HouseholdMember ID No.";
													 sheet.setColumnWidth(hcol, 5000);
												}
												else if(hcol == 4){
													th = "Name of Child";
													 sheet.setColumnWidth(hcol, 6500);
												}
												else if(hcol == 5){
													th = "Barangay";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 6){
													th = "Municipality";
													 sheet.setColumnWidth(hcol, 6000);
												}
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
												hcell.setFont(hf);
												hc2 = hrow.createCell((short) hcol);
												hc2.setCellValue(th);
												hc2.setCellStyle(hcell);
											}
											row_num++;
									}
									HSSFRow row=   sheet.createRow((short) row_num);
									for(short col = 2;col<7;col++){
										if(col == 2){
											val = l.getHousehold_id();
										}
										else if(col == 3){
											val = l.getHmember_id();
										}
										else if(col == 4){
											val = l.getName();			
										}
										else if(col == 5){
											
											val = 	dao.getbrgy_name(l.getBrgy_id());	
											System.out.println("Brgy:"+val+"--"+l.getBrgy_id());
										}
										else if(col == 6){
											val = dao.getmun_name(l.getMun_id());		
											System.out.println("Brgy:"+val+"--"+l.getMun_id());
										}
										 row.createCell((short) col).setCellValue(val);
									}
									row_num++;
								}
								
							}
							row_num++;
							c = 0;
							for(reportBean l:catchList){
								
								
								if(l.getStatus() == 6){
									System.out.println("in sa status = 6"+c);
									if(c == 0){
										
										HSSFRow row16=   sheet.createRow((short) row_num);
										HSSFCell c16;
										HSSFCellStyle cell6 = hwb.createCellStyle();
										HSSFFont f6 = hwb.createFont();
											f6.setBoldweight(Font.BOLDWEIGHT_NORMAL);
											f6.setFontHeightInPoints((short)13);
										    f6.setFontName("Courier New");
										    f6.setItalic(false);
						
										    sheet.setFitToPage(true);
											cell6.setFont(f6);
											c16 = row16.createCell((short) 3);
											c16.setCellValue(new HSSFRichTextString("Duplicates Found in GrandChild Table"));
											c16.setCellStyle(cell6);
											c = 1;
											row_num++;
											row_num++;
											HSSFRow hrow=   sheet.createRow((short) row_num);
											String th = "";
											for(short hcol = 2;hcol<7;hcol++){
												
												
												if(hcol == 2){
													th = "Household ID No.";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 3){
													th = "HouseholdMember ID No.";
													 sheet.setColumnWidth(hcol, 5000);
												}
												else if(hcol == 4){
													th = "Name of Grandchild";
													 sheet.setColumnWidth(hcol, 6500);
												}
												else if(hcol == 5){
													th = "Barangay";
													 sheet.setColumnWidth(hcol, 6000);
												}
												else if(hcol == 6){
													th = "Municipality";
													 sheet.setColumnWidth(hcol, 6000);
												}
												HSSFCell hc2;
												HSSFCellStyle hcell = hwb.createCellStyle();
												HSSFFont hf = hwb.createFont();
												hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
												hcell.setFont(hf);
												hc2 = hrow.createCell((short) hcol);
												hc2.setCellValue(th);
												hc2.setCellStyle(hcell);
											}
											row_num++;
									}
									HSSFRow row=   sheet.createRow((short) row_num);
									for(short col = 2;col<7;col++){
										if(col == 2){
											val = l.getHousehold_id();
										}
										else if(col == 3){
											val = l.getHmember_id();
										}
										else if(col == 4){
											val = l.getName();			
										}
										else if(col == 5){
											
											val = 	dao.getbrgy_name(l.getBrgy_id());	
											System.out.println("Brgy:"+val+"--"+l.getBrgy_id());
										}
										else if(col == 6){
											val = dao.getmun_name(l.getMun_id());		
											System.out.println("Brgy:"+val+"--"+l.getMun_id());
										}
										 row.createCell((short) col).setCellValue(val);
									}
									row_num++;
								}
								
							}
							
							
							row_num++;
							c = 0;
							for(reportBean l:dList){
								
								if(c == 0){
									
									HSSFRow row16=   sheet.createRow((short) row_num);
									HSSFCell c16;
									HSSFCellStyle cell6 = hwb.createCellStyle();
									HSSFFont f6 = hwb.createFont();
										f6.setBoldweight(Font.BOLDWEIGHT_NORMAL);
										f6.setFontHeightInPoints((short)13);
									    f6.setFontName("Courier New");
									    f6.setItalic(false);
									    
									    sheet.setFitToPage(true);
										cell6.setFont(f6);
										c16 = row16.createCell((short) 3);
										c16.setCellValue(new HSSFRichTextString("Grantee of the Following Data was not found."));
										c16.setCellStyle(cell6);
										c = 1;
										row_num++;
										row_num++;
										HSSFRow hrow=   sheet.createRow((short) row_num);
										String th = "";
										for(int i=1;i<10;i++){
											
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
											
											
											HSSFCell hc2;
											HSSFCellStyle hcell = hwb.createCellStyle();
											HSSFFont hf = hwb.createFont();
											hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
											hcell.setFont(hf);
											hc2 = hrow.createCell((short) i);
											hc2.setCellValue(cellValue);
											hc2.setCellStyle(cell6);
										}
										row_num++;
								}
								HSSFRow row=   sheet.createRow((short) row_num);
								for(short i = 1;i<10;i++){
									
									
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
										if(l.getGstatus().equals("1")){
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
										if(l.getF_position().equals("1")){
											cellValue = "1 - Head";
										}
										else if(l.getF_position().equals("2")){
											cellValue = "2 - Wife Spouse";
										}
										else if(l.getF_position().equals("3")){
											cellValue = "3 - Son Daughter";
										}
										else if(l.getF_position().equals("4")){
											cellValue = "4 - Brother / Sister";
										}
										else if(l.getF_position().equals("5")){
											cellValue = "5 - Son-in-law / Daughter-in-law";
										}
										else if(l.getF_position().equals("6")){
											cellValue = "6 - GrandSon / GrandDaughter";
										}
										else if(l.getF_position().equals("7")){
											cellValue = "7 - Father / Mother";
										}
										else if(l.getF_position().equals("8")){
											cellValue = "8 - Other Relatives";
										}
										else if(l.getF_position().equals("9")){
											cellValue = "9 - Boarders";
										}
										else if(l.getF_position().equals("10")){
											cellValue = "10 - Domestic Helper";
										}
										else if(l.getF_position().equals("11")){
											cellValue = "11 - Non-relative";
										}
										else if(l.getF_position().equals("12")){
											cellValue = "12 - Grandfather / GrandMother";
										}
										else if(l.getF_position().equals("13")){
											cellValue = "13 - Uncle / Auntie";
										}
										else if(l.getF_position().equals("14")){
											cellValue = "14 - Nephew / Niece";
										}
									}
									if(i==4){
										row.createCell((short) i).setCellValue(Integer.parseInt(cellValue));
									}
									else{
										row.createCell((short) i).setCellValue(cellValue);
									}
								}
								row_num++;
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
				
				
				
				/*request.setAttribute("status", status);*/
				/*ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
				rd.forward(request, response);*/
				
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
