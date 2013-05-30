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
import bean.transactionBean;

/**
 * Servlet implementation class UpdateGranteeParser
 */
@WebServlet("/UpdateGranteeParser")
public class UpdateGranteeParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateGranteeParser() {
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
						
						
						reportBean bean = null;
						for(reportBean list: dList){
							boolean duplicate = false;
							boolean addedH = false;
							if(list.getStatus() == 1){
								bean = new reportBean(1,list.getHousehold_id(),list.getPhilhealth_id(),list.getHmember_id(),list.getName().toUpperCase(),list.getAge(),list.getBday(),list.getGender(),list.getPregnant(),list.getAttending_school(),list.getStreet(),list.getPurok(),list.getBrgy_id(),list.getMun_id(),list.getF_position());
								
								addedH = dao.searchHousehold(false, list.getHousehold_id());
								if(addedH == true){
									System.out.println("household_id exists.");
									
									duplicate = dao.searchHousehold(false, list.getHousehold_id(),list.getName());
									if(!duplicate){
										System.out.println("householdID:"+list.getHousehold_id());
										duplicateList.add(bean);
									}
									//hfoundnotsave = true;
								}
								else{
									System.out.println("add sa household"+list.getHousehold_id());
									dao.addHousehold(false,bean);
								}
							}
							
							
						}
						
						for(reportBean l:dList){
							boolean duplicate = false;
							boolean granteefound = false;
							boolean grandchild_exists = false;
							boolean child_exists = false;
							boolean wife_exists = false;
							
							granteefound = dao.searchHousehold(false, l.getHousehold_id());
							if(granteefound == true){
									reportBean b = null;
									if(l.getStatus() == 2){ // add sa wife_tbl
										wife_exists = dao.searchWife(false,l.getHousehold_id(),l.getHmember_id());
										if(wife_exists == false){
											//myrow++;
											//System.out.println("in sa add spouse:"+l.getHousehold_id());
											dao.addSpouse(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
										}
										else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											duplicateList.add(b);
										}
									}
									else if(l.getStatus() == 3){ //add sa children_tbl
										child_exists = dao.searchChildren(false,l.getHousehold_id(),l.getHmember_id());
										if(child_exists == false){
											//myrow++;
											//System.out.println("in sa add sa children"+l.getHousehold_id());
											dao.addChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
										}else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											duplicateList.add(b);
										}
									}
									else if(l.getStatus() == 6){ //add sa grandchild_tbl
										grandchild_exists = dao.searchGrandChild(false,l.getHousehold_id(),l.getHmember_id());
										if(grandchild_exists == false){
											//myrow++;
											//System.out.println("in sa add sa gchildren"+l.getHousehold_id());
											dao.addGrandChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
										}else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											duplicateList.add(b);
										}
									}
								}
							else{
								reportBean b1 = null;
								b1 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
								System.out.println("household Id of this grand child is not yet save:"+l.getHousehold_id()+" name:"+l.getName());
								//DataNotSave.add(b1);
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
				int startrow = 0;
				try{
					
					BaseDAO dao = new BaseDAO();
					work = Workbook.getWorkbook(input);
					Sheet sheet = work.getSheet(0);
					row = sheet.getRows();
					col = sheet.getColumns();
					System.out.println("row:"+row);
					System.out.println("col:"+col);
					//String currHousehold_id = "";
					//String prevHousehold_id = "";
					//boolean changeID = false;
					String brgy_Name = "";
					String mun_Name = "";
					boolean granteefound = false;
					boolean MembersChanges = false;
					boolean deletedData = false;
					boolean granteeChanges = false;
					boolean noFoundErr = false;
					boolean wife_exists = false;
					boolean child_exists = false;
					boolean grandchild_exists = false;
					ArrayList<reportBean> rowData = new ArrayList<reportBean>();
					ArrayList<reportBean> DataNotSave = new ArrayList<reportBean>();
					int myrow = 0;
					int parsing_capacity = 4000;
					int munId = 0;
					int in = 0;
					for(int n=0;n<row;n++){
						for(int m=0;m<col;m++){
							Cell cell = sheet.getCell(m, n);
							if(cell.getContents() != null || cell.getContents() != ""){
								if(cell.getContents().equalsIgnoreCase("HOUSEHOLD MEMBER ID / ENTRY ID")){
									System.out.println("cell is household");
									fileCorrect = false;
									break;
								}
								else{
									String cellval[] = cell.getContents().split(":");
									String val = cellval[0];
									if(val.equalsIgnoreCase("CVS MASTERLIST REPORTING PERIOD")){
										System.out.println("cvs found");
										fileCorrect = true;
										break;
									}
								}
							}
						}
						if(fileCorrect == true){
							break;
						}
					}
					
					fileCorrect = true;
					System.out.println(fileCorrect);
					if(fileCorrect == true){
						
						if(row > parsing_capacity){
							
							String error_message = "Error in parsing "+xlsname+" : Number of rows is out of capacity.Maximum row capacity is 4000.";
							
							request.setAttribute("mess",error_message);
							ServletContext sc = this.getServletContext();
							RequestDispatcher rd = sc.getRequestDispatcher("/reports/updateGranteeThruParsing.jsp");
							rd.forward(request, response);
						}
						else{
							System.out.println("in sa else");
							for(int r = 0;r < row;r++){
								String f_position = "";
								String ff_position = "";
								boolean addedH = false;
								boolean duplicate = false;
								boolean isregion8 = false;
								boolean isleyte = false;
								boolean grantee = false;
								boolean spouse = false;
								boolean child = false;
								boolean grandchild = false;
								String household_id = "";
								String philhealth_id = "";
								String household_member_id = "";
								String name = "";
								String birthday = "";
								String gender = "";
								String street = "";
								String purok = "";
								String barangay = "";
								String municipality = "";
								int mun_id = 0;
								int brgy_id = 0;
								int age = 0;
								int pregnant = 0;
								int attending_school = 0;
								
								reportBean bean = null;
								reportBean bean2 = null;
								for(int c = 0;c < col;c++){
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
									
										if(cellValue.equalsIgnoreCase("HOUSEHOLD MEMBER ID / ENTRY ID")){
											startrow = r+1;
										}
										
										if(r >= startrow){
											if(c == 0){
												if(cellValue.equalsIgnoreCase("VIII")){
													isregion8 = true;
												}
											}
											if(c == 1){
												if(isregion8 == true){
													if(cellValue.equalsIgnoreCase("Leyte")){
														isleyte = true;
													}
												}
											}
											
											if(isregion8 == true && isleyte == true){
												//System.out.println("region 8 and leyte province");
												
												if(c == 2){
													municipality = cellValue;
													mun_id = dao.getMunId(municipality);
													int up = 0;
													int diff = 0;
													int enter = 0;
													int val = 0;
													int munf = 0;
													if(mun_id == 0){
														mlist = dao.getmunicipal(false);
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
														blist = dao.getbrgy(munId);
													}
													
												mun_Name = dao.getmunName(mun_id);
													in = 1;
												}
												else if(c == 3){
													barangay = cellValue;
													brgy_id = dao.getbrgyID(barangay,mun_id);
													
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
															blist = dao.getbrgy(munId);
															
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
														
														/*if(brgy_id == 0){
															
															System.out.println("brgy_id 0"+r+"--"+brgy_id+"--"+barangay);
															
														}
													*/
													
													}
													brgy_Name = dao.getbrgyName(brgy_id);
													//System.out.println(r+"--"+brgy_id+"--"+barangay);
												}
												else if(c == 5){
													household_id = cellValue;
													//System.out.println("household_id:"+cellValue);
												}
												else if(c == 6){
													household_member_id = cellValue;
		//											System.out.println("hmember:"+cellValue);
												}
												else if(c == 7){
													name += cellValue+",";
												}
												else if(c == 8){
													name += cellValue+" ";
												}
												else if(c == 9){
													name += cellValue;
												}
												else if(c == 10){
													name += " "+cellValue;
		//											System.out.println("name:"+name);
												}
												else if(c == 12){
													gender = cellValue;
		//											System.out.println("gender:"+gender);
												}
												else if(c == 13){
													birthday = cellValue;
													System.out.println(birthday);
													if(birthday != ""){
														String bday[] = birthday.split("/");
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
													//birthday = "12/12/1222";
		//											System.out.println("birthday:"+birthday);
												}
												else if(c == 17){
													if(cellValue.equalsIgnoreCase("pregnant") || cellValue.equalsIgnoreCase("yes")){
														pregnant = 1;
													}
													else{
														pregnant = 0;
													}
												}
												else if(c == 30){
													if(cellValue.equalsIgnoreCase("ATTENDING") || cellValue.equalsIgnoreCase("yes")){
														attending_school = 1;
													}
													else{
														attending_school = 0;
													}
												}
												
												if(c == 11){
													if(cellValue.equalsIgnoreCase("yes")){
														grantee = true;
		//												System.out.println("add sa household_tbl");
													}
												}
												
												if(c == 14){
													for(int i = 0; i< cellValue.length(); i++)
										            {
														f_position = cellValue.substring(i,i+1);
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
											               else if(f_position.equalsIgnoreCase("6")){
											            	   /*System.out.println("grandson or grandaughter");
											            	   System.out.println("add sa grandchild_tbl");*/
											            	   ff_position = "6";
											               }
											               else if(f_position.equalsIgnoreCase("5")){
											            	  /* System.out.println("Son-in-law   Daughter-in-law");
											            	   System.out.println("rai pa table");*/
											            	   ff_position = "5";
											               }
										            }
												}
												
												if(grantee == false && c == 14) {
													//System.out.println("diri grantee");
													for(int i = 0; i< cellValue.length(); i++)
										            {
										               String rel = cellValue.substring(i,i+1);
										              
										               if(rel.equalsIgnoreCase("1")){
										            	  /* System.out.println("head of the family");
										            	   System.out.println("add sa wife_tbl");*/
										            	   spouse = true;
										            	   break;
										               }
										               else if(rel.equalsIgnoreCase("2")){
										            	  /* System.out.println("wife or spouse");
										            	   System.out.println("add sa wife_tbl");*/
										            	   spouse = true;
										            	   break;
										               }
										               else if(rel.equalsIgnoreCase("3")){
										            	  /* System.out.println("son or daughter");
										            	   System.out.println("add sa children_tbl");*/
										            	   child = true;
										            	   break;
										               }
										               else if(rel.equalsIgnoreCase("6")){
										            	   /*System.out.println("grandson or grandaughter");
										            	   System.out.println("add sa grandchild_tbl");*/
										            	   grandchild = true;
										            	   break;
										               }
										               else if(rel.equalsIgnoreCase("5")){
										            	  /* System.out.println("Son-in-law   Daughter-in-law");
										            	   System.out.println("rai pa table");*/
										            	   child = true;
										            	   break;
										               }
										             }
												}
											}
									}
								}
							
								//int hmember_id = Integer.parseInt(household_member_id);
								
								if(grantee == true){
									
									bean = new reportBean(1,household_id,philhealth_id,household_member_id,name.toUpperCase(),age,birthday,gender,pregnant,attending_school,street,purok,brgy_id,mun_id,ff_position);
									bean2 = new reportBean(1,household_id,philhealth_id,household_member_id,name.toUpperCase(),age,birthday,gender,pregnant,attending_school,street,purok,brgy_Name,mun_Name,ff_position);
									//catchList.add(bean);
									
		//							System.out.println(household_id+philhealth_id+household_member_id+name+age+birthday+gender+pregnant+attending_school+street+purok+barangay+municipality);
									//granteefound = true;
									addedH = dao.searchHousehold(false, household_id);
									if(addedH == true){
										
										//System.out.println("--household_id exists."+household_id);
										duplicate = dao.searchHousehold(false, household_id,name);
										if(!duplicate){
											
											/*System.out.println("householdID:"+household_id);
											catchList.add(bean);
											granteeErr = true;*/
											//---- update household here ---------------//
											catchList = dao.gethousehold(false, household_id);
											
											dao.updateGrantee(false,bean);
											dao.add_logs(false, date, time, "Household ID "+household_id +" change its grantee from "+catchList.get(0).getName()+" to "+name);
											upList.add(bean2);
											//String f_status = dao.getfPosition(household_id);
											//delete the member in case that one of them is being switched as grantee..
											
											if(ff_position == "1" || ff_position == "2"){
												
												boolean pres = false;
												
												pres = dao.isExists(name,"wife_tbl","spouse_name",household_id);
												
												if(pres){
													//delete the other data of this member..
													dao.deleteMember(false,household_id,name,"wife_tbl","spouse_name");
													delList.add(bean2);
												}
												
											}
											else if(ff_position == "3" || ff_position == "5"){
												
												boolean pres = false;
												
												pres = dao.isExists(name,"children_tbl","child_name",household_id);
												
												if(pres){
													//delete the other data of this member..
													dao.deleteMember(false,household_id,name,"children_tbl","child_name");
													delList.add(bean2);
												}
											}
											else if(ff_position == "6"){
												
												boolean pres = false;
												
												pres = dao.isExists(name,"grandchild_tbl","grandchild_name",household_id);
												
												if(pres){
													//delete the other data of this member..
													dao.deleteMember(false,household_id,name,"grandchild_tbl","grandchild_name");
													delList.add(bean2);
												}
											}
											
											//check the f_position of the person that will be changed  then save it to the corresponding table..
											// ayaw nla kuno iadd ncring hi argie.. >_<
											
											/*for(reportBean l:catchList){
												
												if(l.getF_position().equalsIgnoreCase("1") || l.getF_position().equalsIgnoreCase("2")){
													dao.addSpouse(false, l.getHousehold_id(), l.getName(), Integer.toString(l.getHousehold_member_id()), l.getAge(), l.getBday(), l.getGender(), l.getPregnant(), l.getAttending_school());
												}
												else if(l.getF_position().equalsIgnoreCase("3") || l.getF_position().equalsIgnoreCase("5")){
													dao.addChildren(false, l.getHousehold_id(), l.getName(), Integer.toString(l.getHousehold_member_id()), l.getAge(), l.getBday(), l.getGender(), l.getPregnant(), l.getAttending_school());
												}
												else if(l.getF_position().equalsIgnoreCase("6")){
													dao.addGrandChildren(false, l.getHousehold_id(), l.getName(), Integer.toString(l.getHousehold_member_id()), l.getAge(), l.getBday(), l.getGender(), l.getPregnant(), l.getAttending_school());
												}
												
											}*/
										}
										//hfoundnotsave = true;
									}
									else{
										//myrow++;
										//System.out.println(myrow+"add sa household"+household_id);
										//dao.addHousehold(false,bean);
										// ------- get unsave household here ----------------//
										//System.out.println("not save household:"+household_id+" name:"+name);
										DataNotSave.add(bean);
									}
									
								}
								else{
									
										if(child == true){
											//System.out.println("add sa children");
			//								System.out.println(household_id+philhealth_id+household_member_id+name+age+birthday+gender+pregnant+attending_school+street+purok+barangay+municipality);
											bean = new reportBean(3,household_id,philhealth_id,household_member_id,name.toUpperCase(),age,birthday,gender,pregnant,attending_school,street,purok,brgy_id,mun_id);
											rowData.add(bean);
											
										}
																		
										if(grandchild == true){
											//System.out.println("add sa grandchild");
			//								System.out.println(household_id+philhealth_id+household_member_id+name+age+birthday+gender+pregnant+attending_school+street+purok+barangay+municipality);
											bean = new reportBean(6,household_id,philhealth_id,household_member_id,name.toUpperCase(),age,birthday,gender,pregnant,attending_school,street,purok,brgy_id,mun_id);
											rowData.add(bean);
											
										}
																		
										if(spouse == true){
											//System.out.println("add sa wife");
			//								System.out.println(household_id+philhealth_id+household_member_id+name+age+birthday+gender+pregnant+attending_school+street+purok+barangay+municipality);
											bean = new reportBean(2,household_id,philhealth_id,household_member_id,name.toUpperCase(),age,birthday,gender,pregnant,attending_school,street,purok,brgy_id,mun_id);
											rowData.add(bean);
											
										}
												
									}
									
								}
						
								for(reportBean l:rowData){
									granteefound = dao.searchHousehold(false, l.getHousehold_id());
									if(granteefound == true){
											// --- in this area.. the household grantee is already saved.. so what will you do here..? check if the other family member is not yet save or just let this go.. ?????
										reportBean b = null;
										reportBean b2 = null;
										if(l.getStatus() == 2){ // add sa wife_tbl
											wife_exists = dao.searchWife(false,l.getHousehold_id(),l.getHmember_id());
											String bname= dao.getbrgyName(l.getBrgy_id());
											String mname = dao.getmunName(l.getMun_id());
											b2 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),bname,mname);
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											if(wife_exists == false){
												//myrow++;
												System.out.println("in sa add spouse:"+l.getHousehold_id());
												dao.addSpouse(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
												addList.add(b2);
											}
											else{
												dao.updateMember(false, b,"wife_tbl","spouse_name");
												//DataNotSave.add(b);
												upList.add(b2);
											}
										}
										else if(l.getStatus() == 3){
											//add sa children_tbl
											child_exists = dao.searchChildren(false,l.getHousehold_id(),l.getHmember_id());
											String bname= dao.getbrgyName(l.getBrgy_id());
											String mname = dao.getmunName(l.getMun_id());
											b2 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),bname,mname);
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											if(child_exists == false){
												//myrow++;
												System.out.println("in sa add sa children"+l.getHousehold_id());
												dao.addChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
												addList.add(b2);
											}else{
												dao.updateMember(false, b,"children_tbl","child_name");
												//DataNotSave.add(b);
												upList.add(b2);
											}
										}
										else if(l.getStatus() == 6){ //add sa grandchild_tbl
											grandchild_exists = dao.searchGrandChild(false,l.getHousehold_id(),l.getHmember_id());
											String bname= dao.getbrgyName(l.getBrgy_id());
											String mname = dao.getmunName(l.getMun_id());
											b2 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),bname,mname);
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											if(grandchild_exists == false){
												//myrow++;
												System.out.println("in sa add sa gchildren"+l.getHousehold_id());
												dao.addGrandChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school());
												addList.add(b2);
											}else{
												dao.updateMember(false, b,"grandchild_tbl","grandchild_name");
												//DataNotSave.add(b);
												upList.add(b2);
											}
										}
									}
									else{
										reportBean b1 = null;
										b1 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id(),l.getF_position());
										System.out.println("household Id of this grand child is not yet save:"+l.getHousehold_id()+" name:"+l.getName());
										DataNotSave.add(b1);
										// in this area naman get all the data that will be thrown here coz you will need that if the user choose to save the unsave household later..
									}
										
								//changeID = false;
							}
							
							String messext = " Parsing Complete";
							int err = 0;
							int err2 = 0;
			/*for duplicates*/	/*if(catchList.size() != 0 ){
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
							
							/*if(catchList.size() != 0 && DataNotSave.size() != 0){ // both error at the above
								noFoundErr = true;
								err = 1;
								messext = " Parsing Error: Some Grantee were not Found. Duplicates Found.";
							}*/
							
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
						System.out.println("File Not Valid");
						request.setAttribute("mess", "Error in Parsing "+xlsname+" File Not Valid");
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
