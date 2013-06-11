

import java.io.IOException;
import java.io.PrintWriter;
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
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;


/**
 * Servlet implementation class PLFingerprint
 */
@WebServlet("/PLFingerprint")
public class PLFingerprint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PLFingerprint() {
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
		HttpSession session = request.getSession(false);
		if(session==null){
			System.out.println("session is null add municipality servlet");
			ServletContext sc=this.getServletContext();
			RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else{
			if(session.getAttribute("username")==null){
				System.out.println("username is null View_transactions servlet");
				ServletContext sc=this.getServletContext();
				RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}
			else{
				try {
					
					System.out.println("request."+request.getParameter("id"));
		           // DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
		            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		            int badImageQuality = 0;
		            int startCaptureFailed = 0;
		            
		            while (enrollment.getFeaturesNeeded() > 0){
		            	 DPFPSample sample = null;
		         
		                System.out.println("fingerprint needed :"+enrollment.getFeaturesNeeded());
		                
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
		                capture.addReaderStatusListener(new DPFPReaderStatusAdapter()
				        {
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
		                	startCaptureFailed = 1;
		                	System.out.println("star failed capture:"+startCaptureFailed);
		                	PrintWriter out1= response.getWriter();
		                    JSONObject obj=new JSONObject();
		                	try {
		        				obj.put("failedToEnroll", "0");
		        				obj.put("badImageQuality", "0");
		        				obj.put("startCaptureFailed", "1");
		        			} catch (JSONException e2) {
		        				e2.printStackTrace();
		        			}
		                    out1.print(obj);
		        			out1.flush();
		        			out1.close();
		                    System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
		                    
		                    break;
		                  // throw e;
		                } finally {
		                    capture.stopCapture();
		                }
		                if (sample == null){
		                	System.out.println("sample is null.");
		                    continue;
		                }
		                DPFPFeatureSet featureSet;
		                try {
		                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
		                } catch (DPFPImageQualityException e) {
		                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());
		                    badImageQuality = 1;
		                    break;
		                }
		
		                enrollment.addFeatures(featureSet);
		            } // END of while loop.
		           
		            if(badImageQuality==1){
		            	PrintWriter out1= response.getWriter();
		                JSONObject obj=new JSONObject();
		            	try {
		    				obj.put("failedToEnroll", "0");
		    				obj.put("badImageQuality", "1");
		    				obj.put("startCaptureFailed", "0");
		    			} catch (JSONException e2) {
		    				e2.printStackTrace();
		    			}
		                out1.print(obj);
		    			out1.flush();
		    			out1.close();
		            }
		            else{
		            	
			            DPFPTemplate template = enrollment.getTemplate();
			            byte[] fingerprint_byte = template.serialize();
			            try{
			            	BaseDAO dao = new BaseDAO();  
			            	/*================ Geting date from the server ===================*/
			    			String dateAndTime = dao.getDateAndTime();
			    			String regex[] = dateAndTime.split(" ");
			    			String curDate = regex[0];
			    			String regex1[] = regex[1].split("\\."); // naa cjay duha ka slash kung mag split ka with only a dot.
			    			String curTime = regex1[0];
			    			
			    			String regex3[] = curDate.split("-");
			    			String curYear = regex3[0];
			    			String curMonth = regex3[1];
			    			String curDay = regex3[2];
			    			String convertedDate = curMonth+"/"+curDay+"/"+curYear;
			    			
			    			
			    			/*================================================================*/
			                PrintWriter out1= response.getWriter();
			                JSONObject obj=new JSONObject();
			            	try {
			    				obj.put("failedToEnroll", "0");
			    				obj.put("badImageQuality", "0");
			    			} catch (JSONException e2) {
			    				e2.printStackTrace();
			    			}
			                out1.print(obj);
			    			out1.flush();
			    			out1.close();
			    			/*add fingerprint*/
			    			dao.update_fingerprint(false,fingerprint_byte);
							dao.add_logs(false, convertedDate, curTime, "Provincial user change its fingerprint by Provincial Link");
							
			            }catch(Exception e){
			            	e.printStackTrace();
			            }
		            }
		        } catch (DPFPImageQualityException e) {
		            System.out.printf("Failed to enroll the finger.\n");
		            PrintWriter out1= response.getWriter();
		            JSONObject obj=new JSONObject();
		        	try {
						obj.put("failedToEnroll", "1");
					} catch (JSONException e2) {
						e2.printStackTrace();
					}
		            out1.print(obj);
					out1.flush();
					out1.close();
		        } catch (InterruptedException e) {
		            throw new RuntimeException(e);
		        }
				
			}
		}
		
	}

}
