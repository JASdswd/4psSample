package parse;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;

import DAO.BaseDAO;



/**
 * Servlet implementation class PdfParse123
 */
@WebServlet("/PdfParse123")
public class PdfParse123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	PDFParser parser;
	String parsedText;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	PDDocumentInformation pdDocInfo;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdfParse123() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parsePDf.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null PdfParse servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				@SuppressWarnings("deprecation")
				DiskFileUpload upload = new DiskFileUpload();		// Create a new file upload handler 
		
				@SuppressWarnings("rawtypes")
				List items = null;									// parse request
				try {
					items = upload.parseRequest(request);
				} catch (FileUploadException e1) {
					e1.printStackTrace();			
				}
			
				String req = items.get(0).toString();
				System.out.println("req"+req);
				String[] split =req.split("StoreLocation=");
				System.out.println("split[0]:"+split[1]);
				String[] split1 = split[1].split(",");
				System.out.println("split[0]"+split1[0]);
				String savefile = split1[0];
				System.out.println("savefile:"+savefile);
				
				PdfParse123 pdfTextParserObj = new PdfParse123();
				
				//String pdfToText1 = pdfTextParserObj.pdftoText("C:\\Users\\postgres.HIS-PC\\Documents\\DSWD Files\\GCASH -SEPT-OCT 2011\\P5 Payroll List GCASH Set 1-FO-VIII.pdf");
		
				// This pdf file is for (OTC) Over The Counter.
				//String pdfToText1 = pdfTextParserObj.pdftoText("C:\\Users\\postgres.HIS-PC\\Documents\\DSWD Files\\OTC 2011 SEPT-OCT\\"+pdfFile);
				
				String pdfToText1 = pdfTextParserObj.pdftoText(savefile);
				
				//String pdfToText1 = pdfTextParserObj.pdftoTrext("C:\\Users\\postgres.HIS-PC\\Documents\\DSWD Files\\GCASH -SEPT-OCT 2011\\P5 Payroll List GCASH Set 2-FO-VIII.pdf");
				if (pdfToText1 == null) {
					System.out.println("PDF to Text Conversion failed.");
				} else {
					
					/*Si mikata bongoton.*/
					//System.out.println("\nThe text parsed from the PDF Document....\n"
						//	+ pdfToText1);
					/*
					 * pag'create og text file.
					 * 
					 * */
					int minus = 0;
					for(int i  = savefile.length(); i > 0; i--){
						if(savefile.substring(i-1, i).equals("\\")){
							break;
						}
						else{
							minus++;
						}
					}
					savefile = savefile.substring(0, savefile.length()-minus);
					System.out.println("savefile after:"+savefile);
		
					pdfTextParserObj.writeTexttoFile(pdfToText1, savefile+"\\parse_pdf.txt");
					try{
					    // Open the file that is the first 
					    // command line parameter
					    FileInputStream fstream = new FileInputStream(savefile+"\\parse_pdf.txt");
					    // Get the object of DataInputStream
					    DataInputStream in = new DataInputStream(fstream);
					        BufferedReader br = new BufferedReader(new InputStreamReader(in));
					    
					    String strLine;
					    String month = "";
					    String province = "";
					    String id = "";
					    String partial_id = "";
					    int amount = 0;
					    
					    //Read File Line By Line
					    
					    int x = 0;
					    int gcash = 0;
					    int if_its_not_leyte = 0;
					    int parsedTransactions = 0;
					    int parseReverse = 0;
					    int it_has_leyte = 0;
					    
					    int parsing_id_controller = 0;
					    int parsing_id = 0;
					    
					    while ((strLine = br.readLine()) != null){
					      // Print the content on the console
					    	System.out.println(x+".)strLine:"+strLine);
					    	if(x==0){ // variable is used to print out the first line of the text file.
					    		System.out.println (strLine.length()+" :"+strLine);
					    		for(int i = strLine.length(); i>=11 ;i-- ){
					    			//System.out.println("x:"+ strLine.substring(i-11, i));
					    			if(strLine.substring(i-11, i).equalsIgnoreCase("Globe Remit")){
					    				if(strLine.subSequence(i-12, i-11).equals(" ") && (Integer.parseInt((String) strLine.subSequence(i-16, i-12))>=2011 && Integer.parseInt((String) strLine.subSequence(i-16, i-12))<=2085)){
					    					month = strLine.substring(i-30, i-12);
					    					gcash++;
					    					break;
					    				}
					    			}
					    		}
					    		if(gcash==0){
					    			//System.out.println("over the counter");
					    			for(int i = strLine.length(); i>=16 ;i-- ){
						    			//System.out.println("x:"+ strLine.substring(i-11, i));
						    			if(strLine.substring(i-16, i).equalsIgnoreCase("over the counter")){
						    				if(strLine.subSequence(i-17, i-16).equals(" ") && (Integer.parseInt((String) strLine.subSequence(i-21, i-17))>=2011 && Integer.parseInt((String) strLine.subSequence(i-21, i-17))<=2085)){
						    					month = strLine.substring(i-35, i-17);
						    					//System.out.println("month :"+month);
						    					gcash++;
						    					break;
						    				}
						    			}
						    		}
					    		}
					    	}
					    	else{
					    		//System.out.println (x+".) by line :"+strLine.length()+" "+strLine);
					    		//System.out.println("month:"+month);
					    		if(strLine.length()>11){
					    			//System.out.println (x+".) by line :"+strLine.length()+" "+strLine);
					    			//System.out.println("fgfg:"+strLine.substring(0,10));
					    			
					    			if(strLine.substring(0,10).equalsIgnoreCase("trianglert")){
					    				if(strLine.substring(10, 11).equals(" ") && strLine.substring(11, 22).equalsIgnoreCase("Province of")){
					    					if(strLine.substring(22, 23).equals(" ")){
					    						//System.out.println("province :"+strLine.substring(23, strLine.length()));
					    						province = strLine.substring(23, strLine.length());
					    						if(province.equalsIgnoreCase("LEYTE")){
					    							if_its_not_leyte++;
					    							it_has_leyte++;
					    						}
					    						else{
					    							if_its_not_leyte = 0;
					    						}
					    					}
					    				}
					    			}
					    			else if(strLine.substring(strLine.length()-10, strLine.length()).equalsIgnoreCase("trianglert")){
					    				//System.out.println("has");
					    			}
					    		}// end of IF condition.
					    		if(if_its_not_leyte>0){
					    			if(strLine.length()>20){
					    			//	System.out.println(x+".) leyte inih:"+strLine);
						    			/*System.out.println("id pre::::::::"+strLine.substring(strLine.length()-20, strLine.length()));
						    			System.out.println("wahaa:"+strLine.substring(strLine.length()-20, strLine.length()).substring(0, 11));*/
						    			if((strLine.substring(strLine.length()-20, strLine.length()).substring(0, 11)).equalsIgnoreCase("Total Grant")){
						    				partial_id = strLine.substring(strLine.length()-20, strLine.length()).substring(11, 20);
						    			}
						    			/*System.out.println("partial_id::"+partial_id);
						    			System.out.println("sunod sa id:"+strLine.substring(strLine.length()-10, strLine.length()));*/
						    			id = partial_id +'-'+ strLine.substring(strLine.length()-10, strLine.length());
						    			boolean check_id = pdfTextParserObj.check_hh_id(id);
						    			if(check_id){
						    				/*System.out.println("id pre::::::::"+strLine.substring(strLine.length()-20, strLine.length()));
							    			System.out.println("wahaa:"+strLine.substring(strLine.length()-20, strLine.length()).substring(0, 11));
							    			System.out.println("partial_id::"+partial_id);
							    			System.out.println("sunod sa id:"+strLine.substring(strLine.length()-10, strLine.length()));*/
						    				//System.out.println("id inih "+strLine);
						    				//System.out.println("strLine:"+strLine);
						    				amount = pdfTextParserObj.get_amount(strLine);
						    				//System.out.println("province:"+province+" -- month:"+month+" -- id:"+id);
						    				try{
						    					int if_id_exist = 0;
						    					int if_household_exist = 0;
						    					BaseDAO dao = new BaseDAO();
						    					
						    					if_household_exist = dao.testIfExist(false, "select * from household_tbl where household_id = '"+id+"' ");
						    					if(if_household_exist>0){
						    						if_id_exist = dao.testIfExist(false, "select * from received_tbl where household_id = '"+id+"' && month_and_year = '"+month+"' ");
							    					System.out.println("select * from received_tbl where household_id = '"+id+"' && month_and_year = '"+month+"' "+"id_exist:"+if_id_exist);
							    					
							    					if(if_id_exist==0){
							    						if(parsing_id_controller==0){
							    							int last_parsing_id = dao.getLastParsing_id();
									    					System.out.println("last parsing_id:"+last_parsing_id);
									    					parsing_id = last_parsing_id+1;
							    							parsing_id_controller++;
							    						}
							    						dao.parsePDf(false, id, month, amount, 0,parsing_id);
							    						parsedTransactions++;
							    					}
							    					else{
							    						parseReverse++;
							    					}
						    					}
						    				}catch (Exception e) {
						    					e.printStackTrace();
											}
						    			}
						    			else{
						    				//System.out.println("its not an ID my friend.");
						    			}
						    		}
					    		}
					    	} // END of else condition.
					      x++;
					      
					    } 	// END of while loop.
					   // System.out.println("============It has leyte:"+it_has_leyte);
					    if(it_has_leyte==0){
					    	System.out.println("dre inih leyte my friend..^_^..");
					    	request.setAttribute("its_not_leyte", true);
					    }
					    else{
					    	System.out.println(" parsedTransactions:"+parsedTransactions);
					    	System.out.println("parsedreversed:"+parseReverse);
					    	if(parsedTransactions>0){
					    		System.out.println("parsedTransactions:"+parsedTransactions);
					    		request.setAttribute("month", month);
					    		request.setAttribute("parsedTransactions", parsedTransactions);
					    		request.setAttribute("parse_success", true);
					    	}
					    	else if(parseReverse>0 && parsedTransactions==0){
					    		System.out.println("already parsed.");
					    		request.setAttribute("alreadyParsed", true);
					    	}
					    	else{
					    		System.out.println("wa ma parse ang transactions kai wala pa sa database ning mga nawnga..!!!");
					    		request.setAttribute("not_in_database", true);
					    	}
					    }
					    //Close the input stream
					    in.close();
					    }catch (Exception e){//Catch exception if any
					      System.err.println("Error: " + e.getMessage());
					    }
					    
					   /*
					    * 	Delete text file after reading.
					    * */
					    
					File file = new File(savefile+"\\parse_pdf.txt");
					file.delete();
				}	// END of else statement
		
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/transactions/parsePDf.jsp");
				rd.forward(request, response);
			}
		}
	}

	String pdftoText(String fileName) {

		System.out.println("Parsing text from PDF file " + fileName + "....");
		File f = new File(fileName);
		if (!f.isFile()) {
			System.out.println("File " + fileName + " does not exist.");
			return null;
		}
		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.closing the pdf..");
					pdDoc.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}

	// Write the parsed text from PDF to a file
	void writeTexttoFile(String pdfText, String fileName) {

		System.out.println("\nWriting PDF text to output text file " + fileName
				+ "....");
		try {
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(pdfText);
			pw.close();
		} catch (Exception e) {
			System.out
					.println("An exception occured in writing the pdf text to file.");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}
	
	boolean check_hh_id(String id){
		System.out.println("string id :"+id);
		int ctr = 0;
		int count = 0;
		for(int i = 0; i<id.length(); i++){
			if("0912345678-".indexOf(id.substring(i, i+1)) > -1){ // if condition ni sija para sa indexOf.
				ctr++;
			}
		}
		if(ctr==20){
			for(int i = 0; i<id.length(); i++){
				if("-".indexOf(id.substring(i, i+1))> -1){
					count++;
				}
			}
			if(count==2){
				return true;
			}
			return false;
		}
		return false;
	}
	int get_amount(String id){
		String string_amount = "";
		int ctr = 0;
		int int_amount = 0;
		for(int i = 0; i<id.length(); i++){
			if("09123456789".indexOf(id.substring(i, i+1))> -1){
				//System.out.println(id.substring(i, i+1));
				if(ctr<4){
					string_amount = string_amount+id.substring(i, i+1);
					ctr++;
				}
			}
			
		}
		//System.out.println("amount:"+string_amount);
		int_amount = Integer.parseInt(string_amount);
		if(int_amount>2800){
			string_amount = string_amount.substring(0, 3);
			int_amount = Integer.parseInt(string_amount);
		}
		else if((string_amount.substring(0, 3).equals("100") || string_amount.substring(0, 3).equals("200") ) && ("123456789".indexOf(string_amount.substring(3, 4)) > -1) ){
			string_amount = string_amount.substring(0, 3);
			int_amount = Integer.parseInt(string_amount);
		}
		//System.out.println("int_amount:"+int_amount);
		return int_amount;
	}


}
