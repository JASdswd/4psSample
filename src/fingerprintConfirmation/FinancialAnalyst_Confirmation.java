package fingerprintConfirmation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

import DAO.Fingerprint_conDAO;
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
 * Servlet implementation class FinancialAnalyst_Confirmation
 */
@WebServlet("/FinancialAnalyst_Confirmation")
public class FinancialAnalyst_Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinancialAnalyst_Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("fingerprint_confirmation servlet:");
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null ChangeView servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				
				try{

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
			                       // System.out.print(prompt);
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

			                    DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
			                    matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
			                    
			                    transactionBean bean = new transactionBean();
			        			try {
			        				Fingerprint_conDAO dao = new Fingerprint_conDAO();
			        				bean = dao.getFinancialAnalyst_fingerprint(false,3);
			        			} catch (SQLException e) {
			        				// TODO Auto-generated catch block
			        				e.printStackTrace();
			        			}
			        			
			        			byte[] data = null;
			    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
			    				int controller = 0;
			    				
			    					data = bean.getFingerprint();
			    					t.deserialize(data); /* Converts byte[] into DPFP template.*/
			    					DPFPVerificationResult result = 
			    					matcher.verify(featureSet, t);
			    					if (result.isVerified()){
			    						System.out.println("fingerprint is verified according to Prof. Argie Abedejos. Hahaha ang ma'inggit pikon.");
			    						controller = 1;
			    						
			    					}
			    				
			        			if(controller>0){
			        				System.out.println("fingerprint matched. ");
			        	            PrintWriter out1= response.getWriter();
			        	            JSONObject obj=new JSONObject();
			        	        	try {
			        					obj.put("fingerMatched", true);
			        				} catch (JSONException e2) {
			        					// TODO Auto-generated catch block
			        					e2.printStackTrace();
			        				}
			        	            out1.print(obj);
			        				out1.flush();
			        				out1.close();
			        			}
			        			else{
			        				System.out.println("fingerprint not matched. ");
			        	            PrintWriter out1= response.getWriter();
			        	            JSONObject obj=new JSONObject();
			        	        	try {
			        					obj.put("fingerNotMatched", true);
			        				} catch (JSONException e2) {
			        					// TODO Auto-generated catch block
			        					e2.printStackTrace();
			        				}
			        	            out1.print(obj);
			        				out1.flush();
			        				out1.close();
			        			}
			        			
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
			                }
					
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

}
