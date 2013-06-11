package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.BaseDAO;
import bean.transactionBean;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

/**
 * Servlet implementation class FingerprintMatching
 */
@WebServlet("/FingerprintMatching")
public class FingerprintMatching extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FingerprintMatching() {
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
				
				System.out.printf("Performing fingerprint verification...\n");
		  
		                try {
		                   
		                    transactionBean beanFingerprint = new transactionBean();
		                    transactionBean beanFingerprint1 = new transactionBean();
		                    BaseDAO dao = new BaseDAO();
		                    beanFingerprint = dao.getfingerprint(false, "select fingerprint from fingerprint_tbl_temp where household_id = '153828004-4279-00051'");
		                    beanFingerprint1 = dao.getfingerprint(false, "select fingerprintForFM from fingerprint_tbl_tempForFM where household_id = '153828004-5493-00016'");
		                   /* DPFPSample sample1 = DPFPGlobal.getSampleFactory().createSample();
		                    sample1.deserialize(beanFingerprint.getFingerprint());
		                    DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		                   // DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample1, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		                   DPFPFeatureSet featureSet = DPFPGlobal.getFeatureSetFactory().createFeatureSet();
		                   featureSet.deserialize(sample1.serialize());*/
		                    /*DPFPSample sample = DPFPGlobal.getSampleFactory().createSample();
		                    sample.deserialize(beanFingerprint1.getFingerprint());
		                    DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		                    DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);*/
		                    
		                    //DPFPFeatureSet featureSet1 = DPFPGlobal.getFeatureSetFactory().createFeatureSet(beanFingerprint1.getFingerprint());
		                  //  featureSet1.deserialize(beanFingerprint1.getFingerprint());
		                    
		                    ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
		                    ArrayList<transactionBean> bean123 = new ArrayList<transactionBean>();
		                    DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
		                    matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
		                    bean = dao.getallfingerprintForFM(false, "select fingerprint,household_id from fingerprint_tbl_temp");
		                    bean123 = dao.getallfingerprintForFM(false, "select fingerprintForFM,household_id from fingerprint_tbl_tempForFM");
		                    DPFPFeatureSet featureSet1;
		                    DPFPFeatureSet featureSet2;
		                    int controller = 0;
		                    int count = 0;
		                    int ctr = 0;
		                    for(transactionBean k: bean123){
		                    	//controller = 0;
		                    	
		                    		//if(count==20){
		                    		Calendar calendar= Calendar.getInstance();
									DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
									SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
									
									String date = format.format(calendar.getTime());
									String time = timeInstance.format(Calendar.getInstance().getTimeInMillis());
									//System.out.println(time);
									System.out.println("df:"+Calendar.getInstance().getTimeInMillis());
		                    		for(transactionBean l: bean){
			                    		 featureSet1 = DPFPGlobal.getFeatureSetFactory().createFeatureSet(k.getFingerprint());
			                    		 /*DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
			                    		 t.deserialize(l.getFingerprint());*/
			                    		 // t1 was created kai mas pas pas cja inig compare.
			                    		// System.out.println("count:"+k.getHousehold_id());
			                    		 DPFPTemplate t1 = DPFPGlobal.getTemplateFactory().createTemplate(l.getFingerprint());
			                    		 featureSet2 = DPFPGlobal.getFeatureSetFactory().createFeatureSet(l.getFingerprint());
			                    		 DPFPVerificationResult result = 
			 	    						matcher.verify(featureSet1, t1);
			                    		 //System.out.println("count:"+ctr++);
			                    		 if (result.isVerified()){
			 	    						//household_ctr ="yes..!!";
			                    			// controller++;
			                    			if(!l.getHousehold_id().equals(k.getHousehold_id())){
			                    				System.out.println("duplicate fingerprint:"+k.getHousehold_id()+"-and-:"+l.getHousehold_id());
			                    			}
			 	    						
			 	    						controller = 1;
			 	    					}
			                    		 
			                    	}
		                    		String time1 = timeInstance.format(Calendar.getInstance().getTimeInMillis());
		                    		System.out.println(Calendar.getInstance().getTimeInMillis());
		                    		//System.out.println("time1:"+time1);
		                    		//}
		                    	count++;
		                    	
		                    }
		                    
		                    
		                   
		                    String household_ctr = "";
		    				//int controller = 0;
		    				/*System.out.println("Compare two bytes of array:"+Arrays.equals(beanFingerprint.getFingerprint(), beanFingerprint1.getFingerprint()));
		    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
		    				
		    					
	    					t.deserialize(beanFingerprint.getFingerprint());*/
	    					/*DPFPVerificationResult result = 
	    						matcher.verify(featureSet1, t);
	    		
	    					System.out.println("result:"+result.getFalseAcceptRate());
	    					System.out.println("result:"+result.isVerified());
	    					if (result.isVerified()){
	    						household_ctr ="yes..!!";
	    						System.out.print("-------------------------searched:hsh:"+household_ctr);
	    						controller = 1;
	    					}*/
		                    /*    try search by fingerprint        */
		        			/*try {
		        				BaseDAO dao = new BaseDAO();
		        				int ctrl = 0;
		        				// if(session.getAttribute("brgy_id")==null){
				                    	bean = dao.getallfingerprint(false,request.getParameter("mun"),"");
				                    	ctrl = 1;
				                }
		        				 else{
		        					 bean = dao.getallfingerprint(false,request.getParameter("mun"),(String)session.getAttribute("brgy_id"));
		        				 }
		        				
		        			} catch (SQLException e) { 
		        				e.printStackTrace();
		        			}
		        			
		        			byte[] data = null;
		    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
		    				int count = 1;
		    				String household_ctr = "";
		    				int controller = 0;
		    				for(transactionBean l: bean){
		    					System.out.println("john 3:16 count:"+count++ +" "+l.getHousehold_id());
		    					data = l.getFingerprint();
		    					
		    					t.deserialize(data);
		    					DPFPVerificationResult result = 
		    						matcher.verify(featureSet, t);
		    					
		    					
		    					if (result.isVerified()){
		    						household_ctr = l.getHousehold_id();
		    						System.out.print("-------------------------count:"+count+++"hsh:"+household_ctr);
		    						controller = 1;
		    						break;
		    						//makeReport("The fingerprint was VERIFIED.");
		    						
		    					}
		    				}*/
		        			if(controller>0){
		        				/*try {
									BaseDAO d = new BaseDAO();
									byte[] argie = sample.serialize();
									d.searchArgie(false, argie,household_ctr);
								} catch (Exception e) {
								}*/
		        				System.out.println("match found:"+household_ctr);
		        	            PrintWriter out1= response.getWriter();
		        	            JSONObject obj=new JSONObject();
		        	        	try {
		        					obj.put("matchedHousehold_id", household_ctr);
		        					obj.put("mun", request.getParameter("mun"));
		        				} catch (JSONException e2) {
		        					e2.printStackTrace();
		        				}
		        	            out1.print(obj);
		        				out1.flush();
		        				out1.close();
		        			}
		        			else{
		        				System.out.println("No match found. ");
		        	            PrintWriter out1= response.getWriter();
		        	            JSONObject obj=new JSONObject();
		        	        	try {
		        					obj.put("fingerprintMatching", "false");
		        					obj.put("mun", request.getParameter("mun"));
		        				} catch (JSONException e2) {
		        					e2.printStackTrace();
		        				}
		        	            out1.print(obj);
		        				out1.flush();
		        				out1.close();
		        			}
		        			
		        			/* This block of code is for console matching fingerprint configuration. maong ajaw hiLabti.. hehe*/
		                    /*for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
		                        DPFPTemplate template = null; //user.getTemplate(finger);
		                        if (template != null) {
		                            DPFPVerificationResult result = matcher.verify(featureSet, template);
		                            if (result.isVerified()) {
		                               // System.out.printf("Matching finger: %s, FAR achieved: %g.\n",
		                               // 		fingerName(finger), (double)result.getFalseAcceptRate()/DPFPVerification.PROBABILITY_ONE);
		                               // return;
		                            }
		                        }
		                    }*/
		                } catch (Exception e) {
		                    System.out.printf("Failed to perform verification.");
		                    
		                    PrintWriter out1= response.getWriter();
		                    JSONObject obj=new JSONObject();
		                	try {
		        				obj.put("failedToVerify", "1"); 
		        			} catch (JSONException e2) {
		        				e2.printStackTrace();
		        			}
		                    out1.print(obj);
		        			out1.flush();
		        			out1.close();
		        			e.printStackTrace();
		                }
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
