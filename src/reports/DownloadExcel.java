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
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONObject;

import bean.reportBean;

/**
 * Servlet implementation class DownloadExcel
 */
@WebServlet("/DownloadExcel")
public class DownloadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
				ArrayList<reportBean> list = new ArrayList<reportBean>();
				String total_release = "";
				ArrayList<reportBean> list2 = new ArrayList<reportBean>();
				String total_notrelease = "";
				String cash_total = "";
				int list_size = 0;
				int list2_size = 0;
				String reportVal = request.getParameter("reportVal");
				String dtp = "";
				String munp = "";
				String status = "exists";
				JSONObject obj = new JSONObject();
				PrintWriter out = response.getWriter();
				try{
					
					list = (ArrayList<reportBean>) session.getAttribute("releaseList");
					list_size = list.size();
					list2 = (ArrayList<reportBean>) session.getAttribute("notReleaseList");
					list2_size = list2.size();
					
					cash_total = (String) session.getAttribute("cash_total");
					total_release = (String) session.getAttribute("total_release");
					total_notrelease = (String) session.getAttribute("total_notrelease");
					
					System.out.println("size1: "+list_size+" size2: "+list2_size +" reportVal:"+request.getParameter("reportVal"));
					
					int gsize = 0;
					int rsize = 0;
					int fgsize = 0;  
					int frsize = 0;
					
					for(reportBean l:list){
						munp = l.getMunicipality();
						dtp = l.getDate_coverage();
						if(l.getSub() == 0){
							gsize = 1;
							fgsize++;
						}
						
						if(l.getSub() == 1){
							rsize = 1;
							frsize++;
						}
						
						
					}
					
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
					String filename = fpath+request.getParameter("file")+".xls";
					
					File file = new File(filename);
					
					if(file.exists()){
						System.out.println("File already exists..");
						status="exists";
					}
					else{
						status = "not";
						HSSFWorkbook hwb=new HSSFWorkbook();
						if(list_size != 0){
							  int rrow = 0;
							  HSSFSheet sheet =  hwb.createSheet("CCT Payments");
							  HSSFCell c2;
							  HSSFRow row1=   sheet.createRow((short)1);
							  HSSFRow row2=   sheet.createRow((short)2);
							  HSSFRow row32=   sheet.createRow((short)3);
							  HSSFRow row3=   sheet.createRow((short)6);
							  HSSFRow row5=   sheet.createRow((short)5);
							  HSSFRow row4=   sheet.createRow((short)9);
							  HSSFRow rowdate=   sheet.createRow((short)4);
							  HSSFCellStyle cell1 = hwb.createCellStyle();
							  HSSFFont f1 = hwb.createFont();
								f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
								f1.setFontHeightInPoints((short)13);
							    f1.setFontName("Calibri");
							    f1.setItalic(true);
							    
							    sheet.setFitToPage(true);
								cell1.setFont(f1);
								row1.setHeight((short)300);
								c2 = row1.createCell((short) 4);
								c2.setCellValue(new HSSFRichTextString("Pantawid Pamilyang Pilipino Program (4PS)"));
								c2.setCellStyle(cell1);
								
								row2.setHeight((short)300);
								c2 = row2.createCell((short) 4);
								c2.setCellValue(new HSSFRichTextString("Department of Social Welfare and Development"));
								c2.setCellStyle(cell1);
							  
								HSSFCellStyle celld1 = hwb.createCellStyle();
								HSSFFont fd1 = hwb.createFont();
								fd1.setBoldweight(Font.BOLDWEIGHT_BOLD);
								celld1.setFont(fd1);
								celld1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
								//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(s)(total_release)));
								c2 = row32.createCell((short) 1);
								c2.setCellValue("Starting Balance:");
								c2.setCellStyle(celld1);
								c2 = row32.createCell((short) 2);
								c2.setCellValue("P "+cash_total+".00");
								c2.setCellStyle(celld1);
								c2 = rowdate.createCell((short) 1);
								c2.setCellValue("Total Payments:");
								c2.setCellStyle(celld1);
								c2 = rowdate.createCell((short) 2);
								c2.setCellValue("P "+total_release+".00");
								c2.setCellStyle(celld1);
								c2 = row5.createCell((short) 1);
								c2.setCellValue("Cash in Hand:");
								c2.setCellStyle(celld1);
								c2 = row5.createCell((short) 2);
								c2.setCellValue("P "+total_notrelease+".00");
								c2.setCellStyle(celld1);
								c2 = rowdate.createCell((short) 7);
								c2.setCellValue("Date Created:");
								c2.setCellStyle(celld1);
								
								
								HSSFCellStyle celld = hwb.createCellStyle();
								HSSFFont fd = hwb.createFont();
								fd.setBoldweight(Font.BOLDWEIGHT_BOLD);
								fd.setUnderline(Font.U_SINGLE);
								celld.setFont(fd);
								
								c2 = rowdate.createCell((short) 8);
								c2.setCellValue(sdf.format(date));
								c2.setCellStyle(celld);
								
								row3.setHeight((short)300);
								c2 = row3.createCell((short) 4);
								c2.setCellValue(new HSSFRichTextString("Conditional Cash Transfer Payments"));
								c2.setCellStyle(cell1);
								
								if(gsize != 0){
									row4.setHeight((short)300);
									c2 = row4.createCell((short) 4);
									c2.setCellValue(new HSSFRichTextString("Released to Grantee"));
									c2.setCellStyle(cell1);
									
									int row = 10;
									HSSFRow hrow =   sheet.createRow((short) row);
									String head_cell = "";
									for(int c = 0;c<10;c++){
										if(c == 0){
											head_cell = "No.";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c == 1){
											head_cell = "Household ID NO.";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 2){
											head_cell = "Name of Grantee";
											sheet.setColumnWidth(c, 6500);
										}
										else if(c == 3){
											head_cell = "Barangay";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 4){
											head_cell = "Municipality";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 5){
											head_cell = "Set Group";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 6){
											head_cell = "Date Of Transaction";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 7){
											head_cell = "Date Received";
											sheet.setColumnWidth(c, 4000);
										}
										else if(c == 8){
											head_cell = "Time Received";
											sheet.setColumnWidth(c, 3000);
										}
										else if(c == 9){
											head_cell = "Amount Received";
											sheet.setColumnWidth(c, 3000);
										}
										HSSFCell hc2;
										HSSFCellStyle hcell = hwb.createCellStyle();
										HSSFFont hf = hwb.createFont();
										hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
										hcell.setFont(hf);
										hc2 = hrow.createCell((short) c);
										hc2.setCellValue(head_cell);
										hc2.setCellStyle(hcell);
										
									}
										row++;
										int num_row = 0;
										
										for(reportBean l1:list){
											HSSFRow body_row =   sheet.createRow((short) row);
											System.out.println("row:"+row);
											if(l1.getSub() == 0){
												
												num_row++;
												for(int bc = 0;bc<10;bc++){
													if(bc == 0){
														//head_cell = Integer.toString(num_row);
														sheet.setColumnWidth(bc, 2000);
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = body_row.createCell((short) bc);
														hc.setCellValue(num_row);
														hc.setCellStyle(cell);
													}
													else if(bc == 1){
														head_cell = l1.getHousehold_id();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 2){
														head_cell = l1.getHead_name();
														sheet.setColumnWidth(bc, 6500);
													}
													else if(bc == 3){
														head_cell = l1.getBrgy();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 4){
														head_cell = l1.getMunicipality();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 5){
														head_cell = l1.getPhilhealth_id();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 6){
														head_cell = l1.getDate_coverage();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 7){
														head_cell = l1.getDate_receive();
														sheet.setColumnWidth(bc, 4000);
													}
													else if(bc == 8){
														head_cell = l1.getTime_receive();
														sheet.setColumnWidth(bc, 3000);
													}
													else if(bc == 9){
														sheet.setColumnWidth(bc, 3000);
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = body_row.createCell((short) bc);
														hc.setCellValue(l1.getAmount_receive());
														hc.setCellStyle(cell);
													}
													if(bc != 9 && bc != 0){
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = body_row.createCell((short) bc);
														hc.setCellValue(head_cell);
														hc.setCellStyle(cell);
													}
												}
												
												row++;
											}
										}
										rrow = row;
								}
								
								if(rsize != 0){
									rrow=rrow+2;
									HSSFRow rrow4=   sheet.createRow((short)rrow);
									rrow4.setHeight((short)300);
									c2 = rrow4.createCell((short) 4);
									c2.setCellValue(new HSSFRichTextString("Released to Representative"));
									c2.setCellStyle(cell1);
									//int row = 10;
									rrow++;
									HSSFRow rhrow =   sheet.createRow((short) rrow);
									String rhead_cell = "";
									for(int c = 0;c<10;c++){
										if(c == 0){
											rhead_cell = "No.";
											sheet.setColumnWidth(c, 2000);
										}
										else if(c == 1){
											rhead_cell = "Household ID NO.";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 2){
											rhead_cell = "Name of Grantee";
											sheet.setColumnWidth(c, 6500);
										}
										else if(c == 3){
											rhead_cell = "Barangay";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 4){
											rhead_cell = "Municipality";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 5){
											rhead_cell = "Set Group";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 6){
											rhead_cell = "Date Of Transaction";
											sheet.setColumnWidth(c, 6000);
										}
										else if(c == 7){
											rhead_cell = "Date Received";
											sheet.setColumnWidth(c, 4000);
										}
										else if(c == 8){
											rhead_cell = "Time Received";
											sheet.setColumnWidth(c, 3000);
										}
										else if(c == 9){
											rhead_cell = "Amount Received";
											sheet.setColumnWidth(c, 3000);
										}
										HSSFCell hc2;
										HSSFCellStyle hcell = hwb.createCellStyle();
										HSSFFont hf = hwb.createFont();
										hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
										hcell.setFont(hf);
										hc2 = rhrow.createCell((short) c);
										hc2.setCellValue(rhead_cell);
										hc2.setCellStyle(hcell);
										
									}
										rrow++;
										int rnum_row = 0;
										
										for(reportBean l1:list){
											HSSFRow rbody_row =   sheet.createRow((short) rrow);
											if(l1.getSub() != 0){
												rnum_row++;
												for(int bc = 0;bc<10;bc++){
													if(bc == 0){
														//rhead_cell = Integer.toString(rnum_row);
														sheet.setColumnWidth(bc, 2000);
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = rbody_row.createCell((short) bc);
														hc.setCellValue(rnum_row);
														hc.setCellStyle(cell);
													}
													else if(bc == 1){
														rhead_cell = l1.getHousehold_id();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 2){
														rhead_cell = l1.getHead_name();
														sheet.setColumnWidth(bc, 6500);
													}
													else if(bc == 3){
														rhead_cell = l1.getBrgy();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 4){
														rhead_cell = l1.getMunicipality();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 5){
														rhead_cell = l1.getPhilhealth_id();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 6){
														rhead_cell = l1.getDate_coverage();
														sheet.setColumnWidth(bc, 6000);
													}
													else if(bc == 7){
														rhead_cell = l1.getDate_receive();
														sheet.setColumnWidth(bc, 4000);
													}
													else if(bc == 8){
														rhead_cell = l1.getTime_receive();
														sheet.setColumnWidth(bc, 3000);
													}
													else if(bc == 9){
														//rhead_cell = l1.getAmount_receive();
														sheet.setColumnWidth(bc, 3000);
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = rbody_row.createCell((short) bc);
														hc.setCellValue(l1.getAmount_receive());
														hc.setCellStyle(cell);
													}
													if(bc != 9 && bc != 0){
														HSSFCell hc;
														HSSFCellStyle cell = hwb.createCellStyle();
														HSSFFont h = hwb.createFont();
														h.setFontName("Calibri");
														cell.setFont(h);
														hc = rbody_row.createCell((short) bc);
														hc.setCellValue(rhead_cell);
														hc.setCellStyle(cell);
													}
												}
												rrow++;
											}
										}
									
								}
						}
						if(list2_size != 0){
							
							  HSSFSheet sheet2 =  hwb.createSheet("CCT Not Released");
							  HSSFCell nc2;
							  HSSFRow nrow1=   sheet2.createRow((short)1);
							  HSSFRow nrow2=   sheet2.createRow((short)2);
							  HSSFRow nrow3=   sheet2.createRow((short)6);
							  HSSFRow nrow4=   sheet2.createRow((short)3);
							  HSSFRow nrow5=   sheet2.createRow((short)5);
							  HSSFRow nrowdate=   sheet2.createRow((short)4);
							  HSSFCellStyle ncell1 = hwb.createCellStyle();
							  HSSFFont nf1 = hwb.createFont();
							  
								nf1.setBoldweight(Font.BOLDWEIGHT_BOLD);
								nf1.setFontHeightInPoints((short)13);
							    nf1.setFontName("Calibri");
							    nf1.setItalic(true);
			
							    
							    sheet2.setFitToPage(true);
								ncell1.setFont(nf1);
								nrow1.setHeight((short)300);
								nc2 = nrow1.createCell((short) 4);
								nc2.setCellValue(new HSSFRichTextString("Pantawid Pamilyang Pilipino Program (4PS)"));
								nc2.setCellStyle(ncell1);
								
								nrow2.setHeight((short)300);
								nc2 = nrow2.createCell((short) 4);
								nc2.setCellValue(new HSSFRichTextString("Department of Social Welfare and Development"));
								nc2.setCellStyle(ncell1);
							  
								HSSFCellStyle ncelld1 = hwb.createCellStyle();
								HSSFFont nfd1 = hwb.createFont();
								nfd1.setBoldweight(Font.BOLDWEIGHT_BOLD);
								ncelld1.setFont(nfd1);
								ncelld1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
								
								nc2 = nrow4.createCell((short) 1);
								nc2.setCellValue("Starting Balance:");
								nc2.setCellStyle(ncelld1);
								nc2 = nrow4.createCell((short) 2);
								nc2.setCellValue("P "+cash_total+".00");
								nc2.setCellStyle(ncelld1);
								nc2 = nrowdate.createCell((short) 1);
								nc2.setCellValue("Total Payments:");
								nc2.setCellStyle(ncelld1);
								nc2 = nrowdate.createCell((short) 2);
								nc2.setCellValue("P "+total_release+".00");
								nc2.setCellStyle(ncelld1);
								nc2 = nrow5.createCell((short) 1);
								nc2.setCellValue("Cash in Hand:");
								nc2.setCellStyle(ncelld1);
								nc2 = nrow5.createCell((short) 2);
								nc2.setCellValue("P "+total_notrelease+".00");
								nc2.setCellStyle(ncelld1);
								nc2 = nrowdate.createCell((short) 7);
								nc2.setCellValue("Date Created:");
								nc2.setCellStyle(ncelld1);
								
								HSSFCellStyle ncelld = hwb.createCellStyle();
								HSSFFont nfd = hwb.createFont();
								nfd.setBoldweight(Font.BOLDWEIGHT_BOLD);
								nfd.setUnderline(Font.U_SINGLE);
								ncelld.setFont(nfd);
								
								nc2 = nrowdate.createCell((short) 8);
								nc2.setCellValue(sdf.format(date));
								nc2.setCellStyle(ncelld);
								
								nrow3.setHeight((short)300);
								nc2 = nrow3.createCell((short) 4);
								nc2.setCellValue(new HSSFRichTextString("Conditional Cash Transfer Not Released"));
								nc2.setCellStyle(ncell1);
								HSSFRow hrow =   sheet2.createRow((short) 8);
								String head_cell = "";
								for(int c = 0;c<8;c++){
									if(c == 0){
										head_cell = "No.";
										sheet2.setColumnWidth(c, 2000);
									}
									else if(c == 1){
										head_cell = "Household ID NO.";
										sheet2.setColumnWidth(c, 6000);
									}
									else if(c == 2){
										head_cell = "Name of Grantee";
										sheet2.setColumnWidth(c, 6500);
									}
									else if(c == 3){
										head_cell = "Barangay";
										sheet2.setColumnWidth(c, 6000);
									}
									else if(c == 4){
										head_cell = "Municipality";
										sheet2.setColumnWidth(c, 6000);
									}
									else if(c == 5){
										head_cell = "Set Group";
										sheet2.setColumnWidth(c, 6000);
									}
									else if(c == 6){
										head_cell = "Date Of Transaction";
										sheet2.setColumnWidth(c, 6000);
									}
									else if(c == 7){
										head_cell = "Amount Received";
										sheet2.setColumnWidth(c, 3000);
									}
									HSSFCell hc2;
									HSSFCellStyle hcell = hwb.createCellStyle();
									HSSFFont hf = hwb.createFont();
									hf.setBoldweight(Font.BOLDWEIGHT_BOLD);
									hcell.setFont(hf);
									hc2 = hrow.createCell((short) c);
									hc2.setCellValue(head_cell);
									hc2.setCellStyle(hcell);
								}
								
								int rnum_row = 0;
								int row = 9;
								for(reportBean l2:list2){
									HSSFRow rbody_row =   sheet2.createRow((short) row);
										rnum_row++;
										for(int bc = 0;bc<8;bc++){
											if(bc == 0){
												head_cell = Integer.toString(rnum_row);
												sheet2.setColumnWidth(bc, 2000);
											}
											else if(bc == 1){
												head_cell = l2.getHousehold_id();
												sheet2.setColumnWidth(bc, 6000);
											}
											else if(bc == 2){
												head_cell = l2.getHead_name();
												sheet2.setColumnWidth(bc, 6500);
											}
											else if(bc == 3){
												head_cell = l2.getBrgy();
												sheet2.setColumnWidth(bc, 6000);
											}
											else if(bc == 4){
												head_cell = l2.getMunicipality();
												sheet2.setColumnWidth(bc, 6000);
											}
											else if(bc == 5){
												head_cell = l2.getPhilhealth_id();
												sheet2.setColumnWidth(bc, 6000);
											}
											else if(bc == 6){
												head_cell = l2.getDate_coverage();
												sheet2.setColumnWidth(bc, 6000);
											}
											else if(bc == 7){
												head_cell = l2.getAmount_receive();
												sheet2.setColumnWidth(bc, 3000);
											}
											
											HSSFCell hc;
											HSSFCellStyle cell = hwb.createCellStyle();
											HSSFFont h = hwb.createFont();
											h.setFontName("Calibri");
											cell.setFont(h);
											hc = rbody_row.createCell((short) bc);
											hc.setCellValue(head_cell);
											hc.setCellStyle(cell);
										}
									row++;
								}
						}
						
						 FileOutputStream fileOut =  new FileOutputStream(filename);
						 hwb.write(fileOut);
						 fileOut.close();	
						
					}
					/*Desktop dt = Desktop.getDesktop();
					dt.open(new File(filename));
*/
					obj.put("path", filename);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
