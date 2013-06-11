package searchByFingerprint123;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

/**
 * Servlet implementation class SearchByFingerprint123
 */
@WebServlet("/SearchByFingerprint123")
public class SearchByFingerprint123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchByFingerprint123() {
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
		                    DPFPSample sample = null;
		                    
		                    /*Get sample */
		                    final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
		                    DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
		                    //capture.setReaderSerialNumber(activeReader);
		                    capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
		                    capture.addDataListener(new DPFPDataListener(){
			                    public void dataAcquired(DPFPDataEvent e) {
			                        if (e != null && e.getSample() != null) {
		                       	 		try {
		                        	    		samples.put(e.getSample());
		                	       		} catch (InterruptedException e1) {
		                            			e1.printStackTrace();
		                        		}
		                    		}
			                     }
				             });
				             capture.addReaderStatusListener(new DPFPReaderStatusAdapter(){
				                   int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
				                   public void readerConnected(DPFPReaderStatusEvent e) {
				                	   if (lastStatus != e.getReaderStatus())	System.out.println("Reader is connected");
				                	   lastStatus = e.getReaderStatus();
				        		}
				        		public void readerDisconnected(DPFPReaderStatusEvent e) {
				        			if (lastStatus != e.getReaderStatus())
				        				System.out.println("Reader is disconnected");
				        				lastStatus = e.getReaderStatus();
				        		}
				                    	
				              });
		                    try {
		                        capture.startCapture();
		                        sample =  samples.take();
		                    } catch (RuntimeException e) {
		                        System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
		                        throw e;
		                    } finally {
		                        capture.stopCapture();
		                    }
		                    /*Get sample*/
		                    if (sample == null)
		                        throw new Exception();
		
		                    DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		                    DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		                 
		                    /*Created for enrolling featureSet in fingerprint_tbl_temForFM*/
		                    /* DPFPFeatureSet featureSet1;
		                    try{
		                    	BaseDAO daoForFM = new BaseDAO();
		                    	int count = daoForFM.testIfExist(false, "select * from fingerprint_tbl_tempForFM where household_id = '158402017-4278-00093'");
			                    if(count==0){
			                    	featureSet1 = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
			                    	daoForFM.add_fingerprintForFM(false, "158402017-4278-00093", featureSet1.serialize());
			                    }
		                    }catch (Exception e) {
								e.printStackTrace();
							}*/
		                    
		                    /*****************************************************************************/
		                    
		                    DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
		                    matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
		                    String household_ctr = "";
		    				int controller = 0;
		    				BaseDAO dao = new BaseDAO();
		    				int pabalik = 0;
		    				String brgy_id = (String)session.getAttribute("brgy_id");
		                    while(true){
		                    	 ArrayList<transactionBean> bean = new ArrayList<transactionBean>();
		        				int ctrl = 0;
		        				int countAllFPT = dao.getCountAllFPT(false);
		        				
		        				 if(session.getAttribute("brgy_id")==null || pabalik == 1){
				                    	bean = dao.getallfingerprint(false,request.getParameter("mun"),"");
				                    	ctrl = 1;
				                }
		        				 else{
		        					 bean = dao.getallfingerprint(false,request.getParameter("mun"),brgy_id);
		        					 pabalik = 1;
		        				 }
		        				 System.out.println("bean size:"+ bean.size());
		        				 byte[] data = null;
				    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
				    				int count = 1;
				    				
				    				for(transactionBean l: bean){
				    					//System.out.println("Searching for matched fingerprint:"+count++ +" "+l.getHousehold_id());
				    					data = l.getFingerprint();
				    					
				    					t.deserialize(data);
				    					DPFPVerificationResult result = 
				    						matcher.verify(featureSet, t);
				    					
				    					System.out.println("result:"+result.getFalseAcceptRate()+":"+l.getHousehold_id());
				    					System.out.println("result:"+result.isVerified()+":"+l.getHousehold_id());
				    					if (result.isVerified()){
				    						household_ctr = l.getHousehold_id();
				    						System.out.print("-------------------------searchedm:"+count+++"hsh:"+household_ctr);
				    						controller = 1;
				    						break;
				    						
				    					}
				    				}
				    				if((controller > 0) || (ctrl > 0) ){
				    					break;
				    				}
		                    }/*    try search by fingerprint        */
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

}
