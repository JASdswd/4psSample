package parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUploadException;
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
import bean.reportBean2;
import bean.transactionBean;

/**
 * Servlet implementation class UpdateGranteeParser
 */
@WebServlet("/UpdateGranteeParserMag")
public class UpdateGranteeParserMag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateGranteeParserMag() {
        super();
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
				
				try{
					
					int op = Integer.parseInt(request.getParameter("op"));
					JSONObject obj = new JSONObject();
					PrintWriter out = response.getWriter();
					int error = 0;
					BaseDAO dao = new BaseDAO();
					ArrayList<reportBean> dList = new ArrayList<reportBean>();
					ArrayList<reportBean> duplicateList = new ArrayList<reportBean>();
					
					dList= (ArrayList<reportBean>) session.getAttribute("udlist");
					if(op == 1){
						System.out.println("Saving all the data to household_tbl");
						//reportBean bean = null;
						for(reportBean list: dList){
							boolean duplicate = false;
							boolean addedH = false;
							if(list.getGstatus() == "1"){
								//bean = new reportBean(1,list.getHousehold_id(),list.getPhilhealth_id(),list.getHmember_id(),list.getName().toUpperCase(),list.getAge(),list.getBday(),list.getGender(),list.getPregnant(),list.getAttending_school(),list.getStreet(),list.getPurok(),list.getBrgy_id(),list.getMun_id(),list.getF_position());
								
								addedH = dao.searchHousehold(list.getHousehold_id());
								if(addedH){
									System.out.println("household_id exists.");
									
									duplicate = dao.searchHousehold(false, list.getHousehold_id(),list.getName());
									if(!duplicate){
										System.out.println("householdID:"+list.getHousehold_id());
										duplicateList.add(list);
									}
									//hfoundnotsave = true;
								}
								else{
									System.out.println("add sa household"+list.getHousehold_id());
									dao.addHousehold(false,list);
								}
							}
							else{
								System.out.println("gstatus:"+list.getGstatus());
							}
							
							
						}
						
						for(reportBean l:dList){
							boolean or_exists = false;
							boolean granteefound = false;
							boolean grandchild_exists = false;
							boolean child_exists = false;
							boolean wife_exists = false;
							
							if(l.getGstatus() == "1"){
								System.out.println("grantee ini");
							}
							else{
								granteefound = dao.searchHousehold(l.getHousehold_id());
								if(granteefound){
										/*reportBean b = null;*/
										if(l.getF_position() == "2" || l.getF_position() == "1"){ // add sa wife_tbl
											wife_exists = dao.searchWife(false,l.getHousehold_id(),l.getHmember_id());
											if(!wife_exists){
												//myrow++;
												//System.out.println("in sa add spouse:"+l.getHousehold_id());
												dao.addSpouse(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
											}
											else{
												/*b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
												*/duplicateList.add(l);
											}
										}
										else if(l.getF_position() == "3" || l.getF_position() == "5"){ //add sa children_tbl
											child_exists = dao.searchChildren(false,l.getHousehold_id(),l.getHmember_id());
											if(!child_exists){
												//myrow++;
												System.out.println("do get in sa add sa children"+l.getHousehold_id());
												dao.addChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
											}else{
												/*b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
												*/duplicateList.add(l);
											}
										}
										else if(l.getF_position() == "6"){ //add sa grandchild_tbl
											grandchild_exists = dao.searchGrandChild(false,l.getHousehold_id(),l.getHmember_id());
											if(!grandchild_exists){
												//myrow++;
												//System.out.println("in sa add sa gchildren"+l.getHousehold_id());
												dao.addGrandChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStatus(),l.getGender());
											}else{
												duplicateList.add(l);
											}
										}
										else{ //add sa grandchild_tbl
											or_exists = dao.searchOtherRelatives(false,l.getHousehold_id(),l.getHmember_id());
											if(!or_exists){
												//myrow++;
												//System.out.println("in sa add sa gchildren"+l.getHousehold_id());
												dao.addOtherRealtives(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
											}else{
												duplicateList.add(l);
											}
										}
									}
								else{
									/*reportBean b1 = null;
									b1 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
									*/System.out.println("household Id of this grand child is not yet save:"+l.getHousehold_id()+" name:"+l.getName());
									//DataNotSave.add(b1);
								}
							}
							
						}
						
						if(duplicateList.size() != 0){
							
							error = 1;
							System.out.println("-----------------------------------------------------------------------------------");
							System.out.println("may error:");
							System.out.println("-----------------------------------------------------------------------------------");
							obj.put("data", duplicateList);
							obj.put("error", error);
							out.print(obj);
							out.flush();
							out.close();
							
						}
						else{
							
							error = 0;
							obj.put("error", error);
							obj.put("mess", "Successfully Added!");
							out.print(obj);
							out.flush();
							out.close();
							
						}
						
					}
					else{
						System.out.println("not saving maybe downloading");
						String fname = request.getParameter("file");
						
						/*
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-disposition", "attachment;filename=sampleExcel.xls");
						*/
						
						try{
							String fpath = null;
							String path = "C:"+cPath+"/";
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
								String path1 = "C:"+cPath+"/";
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
								  HSSFRow rowtitle4=   sheet.createRow((short)4);
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
											cellValue = "Status";
											sheet.setColumnWidth(i, 4000);
										}
										else if(i == 11){
											cellValue = "HH Set";
											sheet.setColumnWidth(i, 4000);
										}
										else if(i == 12){
											cellValue = "Set Group";
											sheet.setColumnWidth(i, 4000);
										}
										
										sheet.setFitToPage(true);
										cell2.setFont(f2);
										c2 = rowtitle4.createCell((short) i);
										c2.setCellValue(new HSSFRichTextString(cellValue));
										c2.setCellStyle(cell2);
									}
									int row = 5;
									for(reportBean l: dList){
										
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
												cellValue = dao.getmunName(l.getMun_id());
											}
											else if(i == 2){
												cellValue = dao.getbrgyName(l.getBrgy_id());
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
												if(l.getGstatus() == "1"){
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
														.println("status:"+l.getF_position());
												if(l.getF_position() == "1"){
													cellValue = "1 - Head";
												}
												else if(l.getF_position() == "2"){
													cellValue = "2 - Wife Spouse";
												}
												else if(l.getF_position() == "3"){
													cellValue = "3 - Son Daughter";
												}
												else if(l.getF_position() == "4"){
													cellValue = "4 - Brother / Sister";
												}
												else if(l.getF_position() == "5"){
													cellValue = "5 - Son-in-law   Daughter-in-law";
												}
												else if(l.getF_position() == "6"){
													cellValue = "6 - GrandSon GrandDaughter";
												}
												else if(l.getF_position() == "7"){
													cellValue = "7 - Father / Mother";
												}
												else if(l.getF_position() == "8"){
													cellValue = "8 - Other Relatives";
												}
												else if(l.getF_position() == "9"){
													cellValue = "9 - Boarders";
												}
												else if(l.getF_position() == "10"){
													cellValue = "10 - Domestic Helper";
												}
												else if(l.getF_position() == "11"){
													cellValue = "11 - Non-relative";
												}
												else if(l.getF_position() == "12"){
													cellValue = "12 - Grandfather / Grandmother";
												}
												else if(l.getF_position() == "13"){
													cellValue = "13 - Uncle / Auntie";
												}
												else if(l.getF_position() == "14"){
													cellValue = "14 - Nephew / Niece";
												}
												
											}
											else if(i == 10){
												//cellValue = Integer.toString(l.getStatus());
												System.out.println("status:"+l.getStatus());
												
												if(l.getStatus() == 1){
													cellValue = "1 - Active";
												}
												else if(l.getStatus() == 2){
													cellValue = "2 - Deceased";
												}
												else if(l.getStatus() == 3){
													cellValue = "3 - Moved Out";
												}
												else if(l.getStatus() == 4){
													cellValue = "?????";
												}
												else if(l.getStatus() == 5){
													cellValue = "5 - Duplicate";
												}
												else if(l.getStatus() == 6){
													cellValue = "6 - Wrong Entry";
												}
											}
											else if(i == 11){
												cellValue = l.getHh_set();
											}
											else if(i == 12){
												cellValue = l.getSet_group();
											}
											
											
											c2 = rowtitle.createCell((short) i);
											c2.setCellValue(new HSSFRichTextString(cellValue));
											c2.setCellStyle(cell);
										}
										row++;
									}
									
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
				DiskFileUpload upload = new DiskFileUpload();		// Create a new file upload handler 
				
				@SuppressWarnings("rawtypes")
				List items = null;									// parse request
				try {
					items = upload.parseRequest(request);
				} catch (FileUploadException e1) {
					e1.printStackTrace();			
				}
			
				ArrayList<reportBean> catchList = new ArrayList<reportBean>();
				ArrayList<reportBean> upList = new ArrayList<reportBean>();
				ArrayList<reportBean> addList = new ArrayList<reportBean>();
				ArrayList<reportBean> delList = new ArrayList<reportBean>();
				ArrayList<reportBean> blist = new ArrayList<reportBean>();
				ArrayList<transactionBean> mlist = new ArrayList<transactionBean>();
				ArrayList<reportBean> glist = new ArrayList<reportBean>();
				String req = items.get(0).toString();
				System.out.println("req"+req);
				String[] split =req.split("StoreLocation=");
				System.out.println("split[0]:"+split[1]);
				String[] split1 = split[1].split(",");
				System.out.println("split[0]"+split1[0]);
				String savefile = split1[0];
				System.out.println("savefile:"+savefile);
				String xls[] = req.split("=");
				String xlspath[] = xls[1].split(",");
				String xlsname = xlspath[0].toUpperCase();
				
				Calendar cal = Calendar.getInstance();
				DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
				SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
				
				String date = format.format(cal.getTime());
				//System.out.println("date:"+day);
				String time = timeInstance.format(Calendar.getInstance().getTime());
				int curr_day = cal.get(Calendar.DATE);
			
				int curr_month = cal.get(Calendar.MONTH) + 1;
				
				int curr_year = cal.get(Calendar.YEAR);
				
				System.out.println("Current Date: " + cal.getTime());
				System.out.println("month:"+curr_month+" -- day:"+curr_day+" --year:"+curr_year);
				
				File input = new File(savefile);
				Workbook work;
				int row = 0;
				int col = 0;
				String cellValue = "";
				boolean fileCorrect = false;
			//	int startrow = 0;
				try{
					
					BaseDAO dao = new BaseDAO();
					work = Workbook.getWorkbook(input);
					Sheet sheet = work.getSheet(0);
					row = sheet.getRows();
					col = sheet.getColumns();
					System.out.println("row:"+row);
					System.out.println("col:"+col);
					String brgy_Name = "";
					String mun_Name = "";
					boolean granteefound = false;
					boolean MembersChanges = false;
					boolean deletedData = false;
					boolean granteeChanges = false;
					boolean noFoundErr = false;
					boolean wife_exists = false;
					boolean child_exists = false;
					boolean or_exists = false;
					boolean grandchild_exists = false;
					ArrayList<reportBean> rowData = new ArrayList<reportBean>();
					ArrayList<reportBean2> list = new ArrayList<reportBean2>();
					ArrayList<reportBean> DataNotSave = new ArrayList<reportBean>();
					ArrayList<transactionBean> municipal_list = new ArrayList<transactionBean>();
					ArrayList<reportBean> brgy_list = new ArrayList<reportBean>();
					//int myrow = 0;
					int parsing_capacity = 15300;
					int munId = 0;
					int in = 0;
					int startCol = 0, startRow = 0, endRow = 0;
					boolean colStartFound = false, rowStartFound = false;
					rowLoop:
						for(int n=0;n<row;n++){
							for(int m=0;m<col;m++){
								String cell = sheet.getCell(m, n).getContents().trim();
								if(cell != null && cell != ""){
									if(cell.equalsIgnoreCase("CITY")){
										System.out.println("cell is household");
										startCol = m;
										colStartFound = true;
										fileCorrect = true;
										if(colStartFound){
											for (int r2 = n+1; r2 < row; r2++) {
												String cell2 = sheet.getCell(m, r2).getContents().trim();
												if (cell2 != null && cell2 != "") {
													startRow = r2;
													rowStartFound = true;
													//detect endRow
													System.out.println("detected row:"+cell2);
													if (rowStartFound) {
														for (int r3 = startRow; r3 < row; r3++) {
															String cellContent2 = sheet.getCell(startCol, r3).getContents().trim();
															System.out.println(r3 + " - " + cellContent2);
															if (cellContent2 == null || cellContent2.equals("")) {
																System.out.println("cellContent NULL already. r3 = " + r3);
																endRow = r3;
																break;
															}
														}
														break rowLoop;
													}
												}
											}
										}
									}
								}
							}
						}
						
						System.out.println("col:"+colStartFound+"--row:"+rowStartFound);
						
						if (colStartFound == false) {
							fileCorrect = false;
						}
						if (rowStartFound == false) {
							fileCorrect = false;
						}
					if (endRow==0) endRow = row;
					// temporary lah
					System.out.println(fileCorrect);
					if(fileCorrect){
						
						if(endRow > parsing_capacity){
							
							String error_message = "Error in parsing "+xlsname+" : Number of rows is out of capacity.Maximum row capacity is 15000.";
							
							request.setAttribute("mess",error_message);
							ServletContext sc = this.getServletContext();
							RequestDispatcher rd = sc.getRequestDispatcher("/reports/updateGranteeThruParsing.jsp");
							rd.forward(request, response);
						}
						else{
							System.out.println("in sa else");
							String hh_set;
							String set_group;
							String f_position;
							String ff_position;
							boolean addedH;
							boolean duplicate;
							/*boolean isregion8;
							boolean isleyte;*/
							boolean grantee;
							/*boolean spouse;
							boolean child;
							boolean grandchild;
							String household_id;
							String philhealth_id;*/
							//String household_member_id;
							String name;
							String birthday;
							String gender;
							/*String street;
							String purok;*/
							String barangay;
							String municipality;
							int mun_id;
							int brgy_id;
							int age;
							/*int pregnant;
							int attending_school;*/
							int status = 0;
							String stat = "";
							reportBean bean = null;
							//reportBean bean2 = null;
							
							municipal_list = dao.getmunicipal(false);
							brgy_list = dao.getAllBrgy();
							
							for(int r = startRow;r < endRow;r++){
								reportBean hhlist = new reportBean();
								hh_set = "";
								set_group = "";
								status = 0;
								stat = "";
								f_position = "";
								ff_position = "";
								addedH = false;
								duplicate = false;
								/*isregion8 = false;
								isleyte = false;*/
								grantee = false;
								/*spouse = false;
								child = false;
								grandchild = false;
								household_id = "";
								philhealth_id = "";*/
								//household_member_id = "";
								name = "";
								birthday = "";
								gender = "";
								/*street = "";
								purok = "";*/
								barangay = "";
								municipality = "";
								mun_id = 0;
								brgy_id = 0;
								age = 0;
								/*pregnant = 0;
								attending_school = 0;*/
								
								bean = null;
								//bean2 = null;
								
								
								for(int c = startCol;c < col;c++){
									Cell cell = sheet.getCell(c, r);
									
									if (cell.getType() == CellType.LABEL) {
										
										cellValue = cell.getContents();
										
									}else{
										//-- when numbers are found //---
		//								System.out.println("-+-+-+-+-++--++-+-++-+-+-+-+");
		//								System.out.println("cellValue:"+cell.getContents());
		//								System.out.println("-+-+-+-+-++--++-+-++-+-+-+-+");
										cellValue = cell.getContents();
										
									}
												
												if(c == startCol){
													municipality = cellValue;
													//mun_id = dao.getMunId(municipality);
													
													for(transactionBean m: municipal_list){
														if(municipality.equalsIgnoreCase(m.getMunicipality())){
															mun_id = m.getMun_id();
															break;
														}
													}
													
													int up = 0;
													int diff = 0;
													int enter = 0;
													int val = 0;
													int munf = 0;
													if(mun_id == 0){
														//mlist = dao.getmunicipal(false);
														mlist = municipal_list;
														String[] chars = new String[]{",",".","/","!","@","#","$","%","^","&","*","'","\"",";","-","_","(",")",":","|","[","]","}","{"," "};
													    String munName = municipality;
														
														for(int i = 0; i< chars.length; i++ )
												        {
												            if(munName.contains(chars[i]))
												            {
												            	munName = munName.replace(chars[i],"");
												            }
												        }
														
														for(transactionBean l:mlist){
															String str = l.getMunicipality();
															for(int i = 0; i< chars.length; i++ )
													        {
													            if(str.contains(chars[i]))
													            {
													                str = str.replace(chars[i],"");
													            }
													        }
															
															if(munName.equalsIgnoreCase(str)){
																mun_id = l.getMun_id();
																up = 1;
																break;
															}
															
														}
														
														if(up == 0){
															for(transactionBean l:mlist){
																String str = l.getMunicipality();
																for(int i = 0; i< chars.length; i++ )
														        {
														            if(str.contains(chars[i]))
														            {
														                str = str.replace(chars[i],"");
														            }
														        }
																
																String s = munName.toLowerCase();
																String t = str.toLowerCase();
																
																diff = getLevenshteinDistance(s,t);
																
																  if(enter == 0){
																	  val = diff;
																	  enter = 1;
																  }
																  else{
																	  if(diff < val){
																		  val = diff;
																		  munf = l.getMun_id();
																	  }
																  }
																
															}
															mun_id = munf;
														}
														
													}
													
													if(in == 0){
														munId = mun_id;
														//blist = dao.getbrgy(munId);
														for(reportBean b: brgy_list){
															if(munId == b.getMun_id()){
																bean = new reportBean(b.getBrgy_id(),b.getBrgy_name());
																blist.add(bean);
															}
														}
													}
													for(transactionBean m: municipal_list){
														if(mun_id == m.getMun_id()){
															mun_Name = m.getMunicipality();
															break;
														}
													}
													//mun_Name = dao.getmunName(mun_id);
													System.out.println("munID:"+mun_Name);
													hhlist.setMun_id(mun_id);
													hhlist.setMun_name(mun_Name);
													in = 1;
												}
												else if(c == startCol+1){
													barangay = cellValue;
													//brgy_id = dao.getbrgyID(barangay,mun_id);
													for(reportBean b: brgy_list){
														if(mun_id == b.getMun_id() && barangay.equalsIgnoreCase(b.getBrgy_name())){
															brgy_id = b.getBrgy_id();
														}
													}
													//System.out.println("-----------------------------"+brgy_id);
													if(brgy_id == 0){
														//System.out.println("brgy_id not found.");
														String bname = barangay;
														
														String[] chars = new String[]{",",".","/","!","@","#","$","%","^","&","*","'","\"",";","-","_","(",")",":","|","[","]","}","{"," "};
														for(int i = 0; i< chars.length; i++ )
												        {
												            if(bname.contains(chars[i]))
												            {
												            	bname = bname.replace(chars[i],"");
												            }
												        }
														
													if(munId != mun_id){
														int up2 = 0;
														int diff = 0;
														int enter = 0;
														int val = 0;
														int brgyf = 0;
															munId = mun_id;
															for(reportBean b: brgy_list){
																if(munId == b.getMun_id()){
																	bean = new reportBean(b.getBrgy_id(),b.getBrgy_name());
																	blist.add(bean);
																}
															}
															for(reportBean l:blist){
																String str = l.getMun_name();
																for(int i = 0; i< chars.length; i++ )
														        {
														            if(str.contains(chars[i]))
														            {
														                str = str.replace(chars[i],"");
														            }
														        }
																if(bname.equalsIgnoreCase(str)){
																	//System.out.println("--str:"+str+"brgy_id:"+l.getMun_id());
																	brgy_id = l.getMun_id();
																	up2 = 1;
																	break;
																}
																
															}
															
															if(up2 == 0){
																for(reportBean l:blist){
																	String str = l.getMun_name();
																	for(int i = 0; i< chars.length; i++ )
															        {
															            if(str.contains(chars[i]))
															            {
															                str = str.replace(chars[i],"");
															            }
															        }
																	
																String s = bname.toLowerCase();
																String t = str.toLowerCase();
																
																diff = getLevenshteinDistance(s,t);
																
																  if(enter == 0){
																	  val = diff;
																	  enter = 1;
																  }
																  else{
																	  if(diff < val){
																		  val = diff;
																		  brgyf = l.getMun_id();
																	  }
																  }
																
															}
																
																brgy_id = brgyf;
																
															}
															
														}
														else{
															int up2 = 0;
															int diff = 0;
															int enter = 0;
															int val = 0;
															int brgyf = 0;
															for(reportBean l:blist){
																String str = l.getMun_name();
																for(int i = 0; i< chars.length; i++ )
														        {
														            if(str.contains(chars[i]))
														            {
														                str = str.replace(chars[i],"");
														            }
														        }
																if(bname.equalsIgnoreCase(str)){
																	//System.out.println("--str:"+str+"brgy_id:"+l.getMun_id());
																	brgy_id = l.getMun_id();
																	break;
																}
															}
															
															if(up2 == 0){
																for(reportBean l:blist){
																	String str = l.getMun_name();
																	for(int i = 0; i< chars.length; i++ )
															        {
															            if(str.contains(chars[i]))
															            {
															                str = str.replace(chars[i],"");
															            }
															        }
																	
																String s = bname.toLowerCase();
																String t = str.toLowerCase();
																
																diff = getLevenshteinDistance(s,t);
																
																  if(enter == 0){
																	  val = diff;
																	  enter = 1;
																  }
																  else{
																	  if(diff < val){
																		  val = diff;
																		  brgyf = l.getMun_id();
																	  }
																  }
																
															}
																
																brgy_id = brgyf;
																
															}
															
														}
														
													}
													
													for(reportBean b: brgy_list){
														if(b.getBrgy_id() == brgy_id){
															brgy_Name = b.getBrgy_name();
															break;
														}
													}
													
													hhlist.setBrgy_id(brgy_id);
													hhlist.setBrgy_name(brgy_Name);
													//System.out.println(r+"--"+brgy_id+"--"+barangay);
												}
												else if(c == startCol+2){
													hhlist.setHousehold_id(cellValue);
													//household_id = cellValue;
													//System.out.println("household_id:"+cellValue);
												}
												else if(c == startCol+3){
													hhlist.setHmember_id(cellValue);
													//household_member_id = cellValue;
		//											System.out.println("hmember:"+cellValue);
												}
												else if(c == startCol+4){
													name += cellValue+",";
												}
												else if(c == startCol+5){
													name += cellValue+" ";
												}
												else if(c == startCol+6){
													name += cellValue;
													hhlist.setName(name.trim().toUpperCase());
												}
												else if(c == startCol+8){
													String gen = cellValue;
													if(gen.equalsIgnoreCase("Male")){
														gender = "M";
													}
													else if(gen.equalsIgnoreCase("Female")){
														gender = "F";
													}
													hhlist.setGender(gender);
		//											System.out.println("gender:"+gender);
												}
												else if(c == startCol + 7){
													birthday = cellValue;
													System.out.println(birthday);
													if(birthday != ""){
														birthday = cellValue;
														System.out.println(birthday);
														if(birthday != ""){
															String bday[] = birthday.split("/");
															System.out
																	.println(bday.length);
															if(bday.length == 3){
																String month = bday[1];
																String day = bday[0];
																String year = bday[2];
																birthday = month+"/"+day+"/"+year;
																age = curr_year - Integer.parseInt(year);
																if(Integer.parseInt(month)>=curr_month){
																	if(Integer.parseInt(month)==curr_month){
																		if(Integer.parseInt(day)>curr_day){
																			age--;
																		}
																	}
																	else{
																		age--;
																	}
																}
															}
														}
													}
													//birthday = "12/12/1222";
		//											System.out.println("birthday:"+birthday);
													hhlist.setBday(birthday);
												}
												if(c == startCol+10){
													for(int i = 0; i< cellValue.length(); i++)
										            {
														stat = cellValue.substring(i,i+1);
														if(stat.equalsIgnoreCase("1")){
											            	  /* System.out.println("head of the family");
											            	   System.out.println("add sa wife_tbl");*/
																status = 1;
											               }
											               else if(stat.equalsIgnoreCase("2")){
											            	  /* System.out.println("wife or spouse");
											            	   System.out.println("add sa wife_tbl");*/
											            	   status = 2;
											               }
											               else if(stat.equalsIgnoreCase("3")){
											            	  /* System.out.println("son or daughter");
											            	   System.out.println("add sa children_tbl");*/
											            	   status = 3;
											               }
											               else if(stat.equalsIgnoreCase("4")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
											            	   status = 4;
												           }
											               else if(stat.equalsIgnoreCase("5")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
											            	   status = 5;
												           }
											               else if(stat.equalsIgnoreCase("6")){
											            	   /*System.out.println("grandson or grandaughter");
											            	   System.out.println("add sa grandchild_tbl");*/
											            	   status = 6;
											               }
											               
										            }
													hhlist.setStatus(status);
												}
												if(c == startCol+11){
													if(cellValue.equalsIgnoreCase("GRANTEE")){
														grantee = true;
		//												System.out.println("add sa household_tbl");
													}
													
												}
												
												if(c == startCol+9){
													String fp[] = cellValue.split("-");
													String fp1[] = fp[0].split(" ");
													f_position = fp1[0];
														if(f_position.equalsIgnoreCase("1")){
											            	  /* System.out.println("head of the family");
											            	   System.out.println("add sa wife_tbl");*/
																ff_position = "1";
											               }
											               else if(f_position.equalsIgnoreCase("2")){
											            	  /* System.out.println("wife or spouse");
											            	   System.out.println("add sa wife_tbl");*/
											            	   ff_position = "2";
											               }
											               else if(f_position.equalsIgnoreCase("3")){
											            	  /* System.out.println("son or daughter");
											            	   System.out.println("add sa children_tbl");*/
											            	   ff_position = "3";
											               }
											               else if(f_position.equalsIgnoreCase("4")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "4";
												           }
											               else if(f_position.equalsIgnoreCase("5")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "5";
												           }
											               else if(f_position.equalsIgnoreCase("6")){
											            	   /*System.out.println("grandson or grandaughter");
											            	   System.out.println("add sa grandchild_tbl");*/
											            	   ff_position = "6";
											               }
											               else if(f_position.equalsIgnoreCase("7")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "7";
												           }
											               else if(f_position.equalsIgnoreCase("8")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "8";
												           }
											               else if(f_position.equalsIgnoreCase("9")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "9";
												           }
											               else if(f_position.equalsIgnoreCase("10")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "10";
												           }
											               else if(f_position.equalsIgnoreCase("11")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "11";
												           }
											               else if(f_position.equalsIgnoreCase("12")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "12";
												           }
											               else if(f_position.equalsIgnoreCase("13")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "13";
												           }
											               else if(f_position.equalsIgnoreCase("14")){
												            	  /* System.out.println("Son-in-law   Daughter-in-law");
												            	   System.out.println("rai pa table");*/
												            	   ff_position = "14";
												           }
											               
										            
													hhlist.setF_position(ff_position);
												}
												
												if(grantee == true && c == startCol+12){
													hh_set = cellValue;
													System.out.println("add sa household_tbl:"+hh_set);
													hhlist.setHh_set(hh_set);
												}
												if(grantee == true && c == startCol+13){
													set_group = cellValue;
													System.out.println("add sa household_tbl:"+set_group);
													hhlist.setSet_group(set_group);
												}
												
												
								}
							
								//int hmember_id = Integer.parseInt(household_member_id);
								
								if(grantee){
									hhlist.setPhilhealth_id("");
									hhlist.setStreet("");
									hhlist.setPurok("");
									hhlist.setAge(age);
									hhlist.setGstatus("1");

									addedH = dao.searchHousehold(hhlist.getHousehold_id());
									if(addedH){
									duplicate = dao.searchHousehold(false, hhlist.getHousehold_id(),hhlist.getName());
										if(!duplicate){
											//---- update household here ---------------//
											glist.add(hhlist);
										}
									}
									else{
										DataNotSave.add(hhlist);
									}
									
								}
								else{
									hhlist.setGstatus("0");
									rowData.add(hhlist);
								}
									
								}
						
							for(reportBean g: glist){
								String fp = g.getF_position();
								String n = g.getName();
								String hid = g.getHousehold_id();
								String hmember = g.getHmember_id();
								
								catchList = dao.gethousehold(false, hid);
								
								dao.updateGrantee(false,g);
								dao.add_logs(false, date, time, "Household ID "+hid+" change its grantee from "+catchList.get(0).getName()+" to "+n);
								upList.add(g);
								
								//String f_status = dao.getfPosition(household_id);
								//delete the member in case that one of them is being switched as grantee..
								
								if(fp == "1" || fp == "2"){
									
									boolean pres = false;
									
									pres = dao.isExists(n,"wife_tbl","spouse_name",hid,hmember);
									
									if(pres){
										//delete the other data of this member..
										dao.deleteMember(false,hid,hmember,n,"wife_tbl","spouse_name");
										delList.add(g);
									}
									
								}
								else if(fp == "3" || fp == "5"){
									
									boolean pres = false;
									
									pres = dao.isExists(n,"children_tbl","child_name",hid,hmember);
									
									if(pres){
										//delete the other data of this member..
										dao.deleteMember(false,hid,hmember,n,"children_tbl","child_name");
										delList.add(g);
									}
								}
								else if(fp == "6"){
									
									boolean pres = false;
									
									pres = dao.isExists(n,"grandchild_tbl","grandchild_name",hid,hmember);
									
									if(pres){
										//delete the other data of this member..
										dao.deleteMember(false,hid,hmember,n,"grandchild_tbl","grandchild_name");
										delList.add(g);
									}
								}
								else {
									
									if(fp != ""){
										boolean pres = false;
										
										pres = dao.isExists(n,"other_relatives_tbl","fullname",hid,hmember);
										
										if(pres){
											//delete the other data of this member..
											dao.deleteMember(false,hid,hmember,n,"other_relatives_tbl","fullname");
											delList.add(g);
										}
									}
									
								}
							}
							
							list = dao.getHHList("household_tbl", "head_name");
								for(reportBean l:rowData){
									granteefound = false;
									for(reportBean2 p:list){
										if(l.getHousehold_id().equalsIgnoreCase(p.getHousehold_id())){
											granteefound = true;
											System.out.println("-+_+_+_+_+_:"+p.getHousehold_id());
											break;
										}
									}
									
									//granteefound = dao.searchHousehold(l.getHousehold_id());
									if(granteefound){
											// --- in this area.. the household grantee is already saved.. so what will you do here..? check if the other family member is not yet save or just let this go.. ?????
										String fp = l.getF_position();
										
										if(fp == "2" || fp == "1"){ // add sa wife_tbl
											l.setGstatus("2");
											wife_exists = dao.searchWife(false,l.getHousehold_id(),l.getHmember_id());
											if(!wife_exists){
												System.out.println("in sa add spouse:"+l.getHousehold_id());
												dao.addSpouse(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
												addList.add(l);
											}
											else{
												dao.updateMember(false, l,"wife_tbl","spouse_name");
												//DataNotSave.add(b);
												upList.add(l);
											}
										}
										else if(fp == "3" || fp == "5"){
											l.setGstatus("3");
											//add sa children_tbl
											child_exists = dao.searchChildren(false,l.getHousehold_id(),l.getHmember_id());
											if(!child_exists){
												//myrow++;
												System.out.println("in sa add sa children"+l.getHousehold_id());
												dao.addChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
												addList.add(l);
											}else{
												dao.updateMember(false, l,"children_tbl","child_name");
												//DataNotSave.add(b);
												upList.add(l);
											}
										}
										else if(fp == "6"){ //add sa grandchild_tbl
											l.setGstatus("6");
											grandchild_exists = dao.searchGrandChild(false,l.getHousehold_id(),l.getHmember_id());
											if(!grandchild_exists){
												//myrow++;
												System.out.println("in sa add sa gchildren"+l.getHousehold_id());
												dao.addGrandChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStatus(),l.getGender());
												addList.add(l);
											}else{
												dao.updateMemberG(false, l,"grandchild_tbl","grandchild_name");
												//DataNotSave.add(b);
												upList.add(l);
											}
										}
										else{
											if(fp != ""){
												l.setGstatus("7");
												//add sa other_relatives_tbl
												or_exists = dao.searchOtherRelatives(false,l.getHousehold_id(),l.getHmember_id());
												if(!or_exists){
													//myrow++;
													System.out.println("in sa add sa other_relatives"+l.getHousehold_id());
													dao.addOtherRealtives(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
													addList.add(l);
												}else{
													dao.updateMember(false, l,"other_relatives_tbl","fullname");
													//DataNotSave.add(b);
													upList.add(l);
												}
											}
										}
									}
									else{
										System.out.println("household Id of this grand child is not yet save:"+l.getHousehold_id()+" name:"+l.getName());
										DataNotSave.add(l);
										// in this area naman get all the data that will be thrown here coz you will need that if the user choose to save the unsave household later..
									}
										
								//changeID = false;
							}
							
							String messext = " Parsing Complete";
							int err = 0;
							int err2 = 0;
							/*if(catchList.size() != 0 ){
								err = 1;
								System.out.println("Parsing Error: Some duplicates data has been found!");
								messext = " Parsing Error: Some duplicates data has been found!";
							}
							*/
							if(DataNotSave.size() != 0){ // for unsaved datas..
								noFoundErr = true;
								err = 1;
								messext = " Parsing Error: Grantee not Found.";
							}
							if(upList.size() != 0){ // for unsaved datas..
								granteeChanges = true;
								err2 = 2;
								messext = " Parsing Error: Updates.";
							}
							if(delList.size() != 0){ // for unsaved datas..
								deletedData = true;
								err2 = 2;
								messext = " Parsing Error: Delete.";
							}
							if(addList.size() != 0){ // for unsaved datas..
								MembersChanges = true;
								err2 = 2;
								messext = " Parsing Error: Add.";
							}
							
							session.setAttribute("upList", upList);
							session.setAttribute("addList", addList);
							session.setAttribute("delList", delList);
							session.setAttribute("udlist", DataNotSave);
							/*session.setAttribute("clist", catchList);
							*/
							request.setAttribute("err", err);
							request.setAttribute("err2", err2);
							request.setAttribute("noFoundErr", noFoundErr);
							request.setAttribute("upList", upList);
							request.setAttribute("addList", addList);
							request.setAttribute("delList", delList);
							request.setAttribute("MembersChanges", MembersChanges);
							request.setAttribute("deletedData", deletedData);
							request.setAttribute("granteeChanges", granteeChanges);
							
							request.setAttribute("DataNotSave", DataNotSave);
							request.setAttribute("mess",xlsname + messext);
							ServletContext sc = this.getServletContext();
							RequestDispatcher rd = sc.getRequestDispatcher("/reports/updateGranteeThruParsing.jsp");
							rd.forward(request, response);
						}
					
					}
					else{

						if (rowStartFound == false) {
							System.out.println("File Empty");
							request.setAttribute("mess", "\n Error in Parsing "+xlsname+": ERROR: No records to parse.\n");
						}
						
						
						if (colStartFound == false) {
							//errorMessage += "ERROR: Excel file is not the expected format.\n";
							System.out.println("File Not Valid");
							request.setAttribute("mess", "\n Error in Parsing "+xlsname+": Excel file is not the expected format.\n");
						}
						
						ServletContext sc = this.getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/reports/updateGranteeThruParsing.jsp");
						rd.forward(request, response);
						
					}
				}
				catch(Exception ex){
					System.out.println("eeeeeeeerrrrrrrrorrrr:"+ex);
					request.setAttribute("mess", "Error: Parsing Interrupted");
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/reports/updateGranteeThruParsing.jsp");
					rd.forward(request, response); 
				}
				
			}
		
		
		}
	
	}
		
		  public static int getLevenshteinDistance(String s, String t) {
			  int diff = 0;
			  if (s == null || t == null) {
				    throw new IllegalArgumentException("Strings must not be null");
				  }
				  
				  int n = s.length(); // length of s
				  int m = t.length(); // length of t
						
				  if (n == 0) {
					  System.out.println("return m:"+m);
					  diff = m;
				  } else if (m == 0) {
					  System.out.println("return n:"+n);
					  diff = n;
				  }

				  int p[] = new int[n+1]; //'previous' cost array, horizontally
				  int d[] = new int[n+1]; // cost array, horizontally
				  int _d[]; //placeholder to assist in swapping p and d

				  // indexes into strings s and t
				  int i; // iterates through s
				  int j; // iterates through t

				  char t_j; // jth character of t

				  int cost; // cost

				  for (i = 0; i<=n; i++) {
				     p[i] = i;
				  }
						
				  for (j = 1; j<=m; j++) {
				     t_j = t.charAt(j-1);
				     d[0] = j;
						
				     for (i=1; i<=n; i++) {
				        cost = s.charAt(i-1)==t_j ? 0 : 1;
				        // minimum of cell to the left+1, to the top+1, diagonally left and up +cost				
				        d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);  
				     }

				     // copy current distance counts to 'previous row' distance counts
				     _d = p;
				     p = d;
				     d = _d;
				  } 
						
				  // our last action in the above loop was to switch d and p, so p now 
				  // actually has the most recent cost counts
				 
				 // System.out.println("output:"+p[n]);
				  diff = p[n];
				  
				  return diff;
				  
		  }
		
	}
