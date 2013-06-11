
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

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


import DAO.BaseDAO;
import bean.reportBean;
import bean.reportBean2;
import bean.transactionBean;

/**
 * Servlet implementation class SampleUpload
 */
@WebServlet("/SampleUpload")
public class SampleUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SampleUpload() {
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
		HttpSession session = request.getSession(false);
		/*InputStream in = request.getInputStream();
		
		byte[] data = IOUtils.toByteArray(in);
		String filename = request.getHeader("filename");
		System.out.println("filename:"+filename);
		System.out.println("name:"+request.getHeader("name"));
		File file = new File("C:\\eclipse\\uploads\\"+filename);
		if(file.exists() == false){
			try {
				System.out.println(filename);
				//file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}*/
		InputStream inStrm = request.getInputStream();
		String argie = request.getHeader("Abedejos");
		System.out.println("Argie message: "+argie);
		Calendar cal = Calendar.getInstance();
		ArrayList<reportBean> catchList = new ArrayList<reportBean>();
		ArrayList<reportBean> blist = new ArrayList<reportBean>();
		ArrayList<transactionBean> mlist = new ArrayList<transactionBean>();
		int curr_day = cal.get(Calendar.DATE);
	
		int curr_month = cal.get(Calendar.MONTH) + 1;
		
		int curr_year = cal.get(Calendar.YEAR);
		
		System.out.println("Current Date: " + cal.getTime());
		System.out.println("month:"+curr_month+" -- day:"+curr_day+" --year:"+curr_year);
		
		
		int row = 0;
		int col = 0;
		String cellValue = "";
		boolean fileCorrect = false;
		//int startrow = 0;
		try{
			
			BaseDAO dao = new BaseDAO();
			Workbook work = Workbook.getWorkbook(inStrm);
			
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
			boolean wife_exists = false;
			boolean child_exists = false;
			boolean grandchild_exists = false;
			boolean other_relatives_exists = false;
			ArrayList<reportBean> rowData = new ArrayList<reportBean>();
			ArrayList<reportBean> glist = new ArrayList<reportBean>();
			ArrayList<reportBean2> list = new ArrayList<reportBean2>();
			ArrayList<reportBean> DataNotSave = new ArrayList<reportBean>();
			ArrayList<transactionBean> municipal_list = new ArrayList<transactionBean>();
			ArrayList<reportBean> brgy_list = new ArrayList<reportBean>();
			int myrow = 0;
			int parsing_capacity = 25200;
			int munId = 0;
			String filename = request.getHeader("filename");
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
			System.out.println(fileCorrect+"--"+endRow);
			if(fileCorrect){
				
				if(endRow > parsing_capacity){
					
					String error_message = "Error in parsing "+filename+" : Number of rows is out of capacity.Maximum row capacity is "+parsing_capacity+".";
					
					request.setAttribute("mess",error_message);
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
					rd.forward(request, response);
				}
				else{
					System.out.println("in sa else");
					
					@SuppressWarnings("unused")
					boolean add = false;
					String f_position = "";
					String ff_position = "";
					int status = 0;
					String stat = "";
					String hh_set;
					String set_group;
					boolean addedH = false;
					boolean duplicate = false;
					/*boolean isregion8 = false;
					boolean other_relatives = false;*/
					boolean grantee = false;
					/*boolean spouse = false;
					boolean child = false;
					boolean grandchild = false;
					String household_id = "";
					String philhealth_id = "";
					String household_member_id = "";*/
					String name = "";
					String birthday = "";
					String gender = "";
					/*String street = "";
					String purok = "";*/
					String barangay = "";
					String municipality = "";
					int mun_id = 0;
					int brgy_id = 0;
					int age = 0;
					/*int pregnant = 0;
					int attending_school = 0;*/
					reportBean bean = null;

					municipal_list = dao.getmunicipal(false);
					brgy_list = dao.getAllBrgy();
					for(int r = startRow;r < endRow;r++){
						reportBean hhlist = new reportBean();
						 add = false;
						 stat = "";
						 status = 0;
						 f_position = "";
						 ff_position = "";
						 hh_set = "";
						 set_group = "";
						 addedH = false;
						 duplicate = false;
						 /*isregion8 = false;
						 other_relatives = false;*/
						 grantee = false;
						/* spouse = false;
						 child = false;
						 grandchild = false;
						 household_id = "";
						 philhealth_id = "";
						 household_member_id = "";*/
						 name = "";
						 birthday = "";
						 gender = "";
						/* street = "";
						 purok = "";*/
						 barangay = "";
						 municipality = "";
						 mun_id = 0;
						 brgy_id = 0;
						 age = 0;
						 /*pregnant = 0;
						 attending_school = 0;*/
						
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
											System.out
													.println("munID:"+mun_id);
											hhlist.setMun_id(mun_id);
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
											
											System.out.println("-----------------------------"+brgy_id);
											if(brgy_id == 0){
												System.out.println("brgy_id not found.");
												String bname = barangay;
												
												String[] chars = new String[]{",",".","/","!","@","#","$","%","^","&","*","'","\"",";","-","_","(",")",":","|","[","]","}","{"," "};
												for(int i = 0; i< chars.length; i++ )
										        {
										            if(bname.contains(chars[i]))
										            {
										            	bname = bname.replace(chars[i],"");
										            }
										        }
												System.out
														.println("munID:"+munId+"/mun_id:"+mun_id+"bname:"+bname);
											if(munId != mun_id){
												int up2 = 0;
												int diff = 0;
												int enter = 0;
												int val = 0;
												int brgyf = 0;
													munId = mun_id;
													//blist = dao.getbrgy(munId);
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
													System.out
															.println("blist size:"+blist.size()+"up2:"+up2+"--brgy_id:"+brgy_id);
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
															System.out.println("--str:"+str+"brgy_id:"+l.getMun_id());
															brgy_id = l.getMun_id();
															break;
														}
													}
													System.out
													.println("2:blist size:"+blist.size()+"up2:"+up2+"--brgy_id:"+brgy_id);
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
															  brgyf = l.getMun_id();
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
											
												if(brgy_id == 0){
													
													System.out.println("brgy_id 0"+r+"--"+brgy_id+"--"+barangay);
													
												}
											
											
											}
											hhlist.setBrgy_id(brgy_id);
											System.out.println(r+"--"+brgy_id+"--"+barangay);
										}
										else if(c == startCol+2){
											//household_id = cellValue;
											hhlist.setHousehold_id(cellValue);
											//System.out.println("household_id:"+cellValue);
										}
										else if(c == startCol+3){
											//household_member_id = cellValue;
											hhlist.setHmember_id(cellValue);
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
										else if(c == startCol+7){
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
					
						if(grantee){
							//hhlist.setStatus(1);
							hhlist.setPhilhealth_id("");
							hhlist.setStreet("");
							hhlist.setPurok("");
							hhlist.setAge(age);
						
							addedH = dao.searchHousehold( hhlist.getHousehold_id());
							if(addedH){
								System.out.println("household_id exists.");
								
								duplicate = dao.searchHousehold(false, hhlist.getHousehold_id(),hhlist.getName());
								if(!duplicate){
									System.out.println("householdID:"+hhlist.getHousehold_id()+" "+hhlist.getName());
									catchList.add(hhlist);
									granteeErr = true;
								}
								
							}
							else{
									myrow++;
									System.out.println(myrow+"add sa household"+hhlist.getHousehold_id());
									//dao.addHousehold(false,hhlist);
									glist.add(hhlist);
							}
							
						}
						else{
								//hhlist.setStatus(Integer.parseInt(ff_position));
								rowData.add(hhlist);
							}
							
						}
					
						dao.addHousehold(false, glist);
					
					
						list = dao.getHHList("household_tbl", "head_name");
						System.out.println("list:"+list.size());
						for(reportBean l:rowData){
							granteefound = false;
							
							for(reportBean2 p:list){
								if(l.getHousehold_id().equalsIgnoreCase(p.getHousehold_id())){
									granteefound = true;
									System.out.println("-+_+_+_+_+_:"+p.getHousehold_id());
									break;
								}
							}
							if(granteefound){
									//reportBean b = null;
									if(l.getF_position().equals("2") || l.getF_position().equals("1")){ // add sa wife_tbl
										wife_exists = dao.searchWife(false,l.getHousehold_id(),l.getHmember_id());
										if(wife_exists == false){
											//myrow++;
											//System.out.println(myrow+"in sa add spouse:"+l.getHousehold_id());
											dao.addSpouse(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
										}
										/*else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id(),l.getF_position());
											catchList.add(b);
											spouseErr = true;
										}*/
									}
									
									else if(l.getF_position().equals("3") || l.getF_position().equals("5")){ //add sa children_tbl
										child_exists = dao.searchChildren(false,l.getHousehold_id(),l.getHmember_id());
										if(child_exists == false){
											//myrow++;
											//System.out.println(myrow+"in sa add sa children"+l.getHousehold_id());
											dao.addChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
										}/*else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id(),l.getF_position());
											catchList.add(b);
											childErr = true;
										}*/
									}
									else if(l.getF_position().equals("6")){ //add sa grandchild_tbl
										grandchild_exists = dao.searchGrandChild(false,l.getHousehold_id(),l.getHmember_id());
										if(grandchild_exists == false){
											//myrow++;
											//System.out.println(myrow+"in sa add sa gchildren"+l.getHousehold_id());
											dao.addGrandChildren(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getStatus(),l.getGender());
										}/*else{
											b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
											catchList.add(b);
											gchildErr = true;
										}*/
									}
									else{ //add sa other_relatives tbl
										
										if(l.getF_position() != ""){
											other_relatives_exists = dao.searchOtherRelatives(false,l.getHousehold_id(),l.getHmember_id());
											if(other_relatives_exists == false){
												//myrow++;
												//System.out.println(myrow+"in sa add sa children"+l.getHousehold_id());
												dao.addOtherRealtives(false,l.getHousehold_id(),l.getName(),l.getHmember_id(),l.getAge(),l.getBday(),l.getPregnant(),l.getAttending_school(),l.getF_position(),l.getStatus(),l.getGender());
											}/*else{
												b = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id(),l.getF_position());
												catchList.add(b);
												orelErr = true;
											}*/
										}
									}
								}
							else{
								reportBean b1 = null;
								b1 = new reportBean(l.getStatus(),l.getHousehold_id(),l.getPhilhealth_id(),l.getHmember_id(),l.getName(),l.getAge(),l.getBday(),l.getGender(),l.getPregnant(),l.getAttending_school(),l.getStreet(),l.getPurok(),l.getBrgy_id(),l.getMun_id());
								System.out.println("household Id of this grand child is not yet save:"+l.getHousehold_id()+" name:"+l.getName());
								DataNotSave.add(b1);
							}
								
						//changeID = false;
							System.out.println(granteefound);
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
					request.setAttribute("mess",filename + messext);
					ServletContext sc = this.getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parseExcel.jsp");
					rd.forward(request, response);
				}
			
			}
			else{

				if (rowStartFound == false) {
					System.out.println("File Empty");
					request.setAttribute("mess", "\n Error in Parsing "+filename+": ERROR: No records to parse.\n");
				}
				
				
				if (colStartFound == false) {
					//errorMessage += "ERROR: Excel file is not the expected format.\n";
					System.out.println("File Not Valid");
					request.setAttribute("mess", "\n Error in Parsing "+filename+": Excel file is not the expected format.\n");
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


