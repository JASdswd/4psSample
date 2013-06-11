package parse;

import java.io.File;
import java.io.IOException;
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

import DAO.BaseDAO;
import bean.reportBean;
import bean.reportBean2;
import bean.transactionBean;

/**
 * Servlet implementation class UpdateMemberGender
 */
@WebServlet("/UpdateMemberGender")
public class UpdateMemberGender extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberGender() {
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
		
		
		//Take note for maguindanao and lanao changes
		
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
				
				int curr_day = cal.get(Calendar.DATE);
			
				int curr_month = cal.get(Calendar.MONTH) + 1;
				
				int curr_year = cal.get(Calendar.YEAR);
				
				System.out.println("Current Date: " + cal.getTime());
				System.out.println("month:"+curr_month+" -- day:"+curr_day+" --year:"+curr_year);
				
				File input = new File(savefile);
				
				int row = 0;
				int col = 0;
				String cellValue = "";
				boolean fileCorrect = false;
				//int startrow = 0;
				try{
					
					BaseDAO dao = new BaseDAO();
					Workbook work = Workbook.getWorkbook(input);
					Sheet sheet = work.getSheet(0);
					row = sheet.getRows();
					col = sheet.getColumns();
					System.out.println("row:"+row);
					System.out.println("col:"+col);
					//String currHousehold_id = "";
					//String prevHousehold_id = "";
					boolean orelErr = false;
					boolean granteefound = false;
					boolean granteeErr = false;
					boolean childErr = false;
					boolean gchildErr = false;
					boolean spouseErr = false;
					boolean noFoundErr = false;
					ArrayList<reportBean> rowData = new ArrayList<reportBean>();
					ArrayList<reportBean> DataNotSave = new ArrayList<reportBean>();
					int parsing_capacity = 25200;
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
					System.out.println(fileCorrect+"--"+endRow);
					if(fileCorrect){
						
						if(endRow > parsing_capacity){
							
							String error_message = "Error in parsing "+xlsname+" : Number of rows is out of capacity.Maximum row capacity is 25000.";
							
							request.setAttribute("mess",error_message);
							ServletContext sc = this.getServletContext();
							RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
							rd.forward(request, response);
						}
						else{
							System.out.println("in sa else");
							
							String f_position = "";
							String ff_position = "";
							boolean grantee = false;
							String name = "";
							String gender = "";
							
							reportBean bean = null;

							for(int r = startRow;r < endRow;r++){
								reportBean hhlist = new reportBean();
								 f_position = "";
								 ff_position = "";
								 grantee = false;
								 name = "";
								 gender = "";
								 bean = null;
								
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
										
												if(c == startCol){}
												else if(c == startCol+1){}
												else if(c == startCol+2){
													hhlist.setHousehold_id(cellValue);
												}
												else if(c == startCol+3){
													hhlist.setHmember_id(cellValue);
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
												else if(c == startCol+7){}
												if(c == startCol+10){}
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
												
								}
							
									if(grantee){}
									else{
											rowData.add(hhlist);
									}
									
								}
									for(reportBean l:rowData){
											//reportBean b = null;
											if(l.getF_position().equals("2") || l.getF_position().equals("1")){ // add sa wife_tbl
												dao.UpdateMember(false,l.getHousehold_id(),Integer.parseInt(l.getHmember_id()),l.getGender(),"wife_tbl");
											}
											
											else if(l.getF_position().equals("3") || l.getF_position().equals("5")){ //add sa children_tbl
												dao.UpdateMember(false,l.getHousehold_id(),Integer.parseInt(l.getHmember_id()),l.getGender(),"children_tbl");
											}
											else if(l.getF_position().equals("6")){ //add sa grandchild_tbl
												dao.UpdateMember(false,l.getHousehold_id(),Integer.parseInt(l.getHmember_id()),l.getGender(),"grandchild_tbl");
											}
											else{ //add sa other_relatives tbl
												dao.UpdateMember(false,l.getHousehold_id(),Integer.parseInt(l.getHmember_id()),l.getGender(),"other_relatives_tbl");
											}
									
									}
							
							String messext = " Parsing Complete";
							int err = 0;
							System.out.println("rowData:"+rowData.size());
							System.out.println("catchList:"+catchList.size());
							if(catchList.size() != 0 ){
								err = 1;
								System.out.println("Parsing Error: Some duplicates data has been found!");
								messext = " Parsing Error: Some duplicates data has been found!";
							}
							
							if(DataNotSave.size() != 0){
								noFoundErr = true;
								err = 1;
								messext = " Parsing Error: Grantee not Found.";
							}
							
							if(catchList.size() != 0 && DataNotSave.size() != 0){
								noFoundErr = true;
								err = 1;
								messext = " Parsing Error: Some Grantee were not Found. Duplicates Found.";
							}
							if(messext.equalsIgnoreCase(" Parsing Complete")){
								request.setAttribute("messext", 1);
							}
							else{
								request.setAttribute("messext", 0);
							}
							session.setAttribute("dlist", DataNotSave);
							session.setAttribute("clist", catchList);
							request.setAttribute("err", err);
							request.setAttribute("noFoundErr", noFoundErr);
							request.setAttribute("gchildErr", gchildErr);
							request.setAttribute("childErr", childErr);
							request.setAttribute("spouseErr", spouseErr);
							request.setAttribute("granteeErr", granteeErr);
							request.setAttribute("orelErr", orelErr);
							request.setAttribute("catchList", catchList);
							request.setAttribute("DataNotSave", DataNotSave);
							request.setAttribute("mess",xlsname + messext);
							ServletContext sc = this.getServletContext();
							RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
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
						RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
						rd.forward(request, response);
						
					}
				}
				catch(Exception ex){
					System.out.println("eeeeeeeerrrrrrrrorrrr:"+ex);
					request.setAttribute("mess", "Error: Parsing Interrupted");
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
					rd.forward(request, response); 
				}
				
			}
		}
	}

}
