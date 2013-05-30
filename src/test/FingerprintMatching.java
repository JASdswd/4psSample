package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

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
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
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
        // TODO Auto-generated constructor stub
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
		                    byte[] data = beanFingerprint.getFingerprint();
		                    beanFingerprint = dao.getfingerprint(false, "158407014-5967-00003");
		                    beanFingerprint1 = dao.getfingerprint(false, "158407014-5967-00003");
		                    DPFPSample sample1 = DPFPGlobal.getSampleFactory().createSample(beanFingerprint.getFingerprint());
		                   DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		                    DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample1, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		                   //DPFPFeatureSet featureSet = DPFPGlobal.getFeatureSetFactory().createFeatureSet();
		                  // featureSet.deserialize(beanFingerprint.getFingerprint());
		                    //featureSet.deserialize(beanFingerprint.getFingerprint());
		                    DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
		                    matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
		                    String household_ctr = "";
		    				int controller = 0;
		    				System.out.println("Compare two bytes of array:"+Arrays.equals(beanFingerprint.getFingerprint(), beanFingerprint1.getFingerprint()));
		    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
		    				
		    					
	    					t.deserialize(beanFingerprint1.getFingerprint());
	    					DPFPVerificationResult result = 
	    						matcher.verify(featureSet, t);
	    				
	    					System.out.println("result:"+result.getFalseAcceptRate());
	    					System.out.println("result:"+result.isVerified());
	    					System.out.println("dfdf:"+result.hashCode());
	    					System.out.println("result.toString:"+result.toString());
	    					if (result.isVerified()){
	    						household_ctr ="yes..!!";
	    						System.out.print("-------------------------searched:hsh:"+household_ctr);
	    						controller = 1;
	    					}
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
									// TODO: handle exception
								}*/
		        				System.out.println("match found:"+household_ctr);
		        	            PrintWriter out1= response.getWriter();
		        	            JSONObject obj=new JSONObject();
		        	        	try {
		        					obj.put("matchedHousehold_id", household_ctr);
		        					obj.put("mun", request.getParameter("mun"));
		        				} catch (JSONException e2) {
		        					// TODO Auto-generated catch block
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
		        					obj.put("matchedHousehold_id", "false");
		        					obj.put("mun", request.getParameter("mun"));
		        				} catch (JSONException e2) {
		        					// TODO Auto-generated catch block
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
		        				// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
	}

}
