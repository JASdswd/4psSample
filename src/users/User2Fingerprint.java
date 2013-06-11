package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * Servlet implementation class User2Fingerprint
 */
@WebServlet("/User2Fingerprint")
public class User2Fingerprint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User2Fingerprint() {
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
				        		/*try {
									obj.put("secret", "1");
								} catch (JSONException e1) {
									e1.printStackTrace();
								}*/
				        	}
				        	public void readerDisconnected(DPFPReaderStatusEvent e) {
				        		if (lastStatus != e.getReaderStatus())
				        			System.out.println("Reader is disconnected");
				        			lastStatus = e.getReaderStatus();
				        			/*try {
				    					obj.put("secret", "0");
				    				} catch (JSONException e1) {
				    					e1.printStackTrace();
				    				}*/
				        	}
				                	
				         });
		                try {
		                		capture.startCapture();
		                    
		                   // System.out.print(prompt);
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
			            Calendar calendar= Calendar.getInstance();
						DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
						SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
						
						String date = format.format(calendar.getTime());
						//System.out.println("date:"+day);
						String time = timeInstance.format(Calendar.getInstance().getTime());
			            try{
			            	UserDAO dao = new UserDAO();  
			            	System.out.println("remuel gwapo:"+request.getParameter("id"));
			            	System.out.println("start capture failed.:"+startCaptureFailed);
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
			    			int user_role = Integer.parseInt(request.getParameter("user_role"));
			    			dao.updateuser2_fpt(false,fingerprint_byte,user_role);
			    			
			    			String fname = dao.getUser2Fname(user_role);
							String lname = dao.getUser2Lname(user_role);
							/*for logs (ge set ug 1 para makuha ang pangan ug apelyido sa admin o provlink[jm])*/
							String logs_fname = dao.getUser2Fname(1);
							String logs_lname = dao.getUser2Lname(1);
							if(user_role == 1){
								dao.add_logs(false, date, time, "Provincial link "+fname+" "+lname+" change its fingerprint by "+logs_fname+" "+logs_lname);
								
							}else if(user_role == 3){
								dao.add_logs(false, date, time, "Financial Analyst "+fname+" "+lname+" change its fingerprint by "+logs_fname+" "+logs_lname);
								
							}
							else if(user_role == 10){
								dao.add_logs(false, date, time, "Administrator "+fname+" "+lname+" change its fingerprint by "+logs_fname+" "+logs_lname);
								
							}
							
							
			            }catch(Exception e){
			            	e.printStackTrace();
			            }
			           // user.setTemplate(DPFPFingerIndex.values()[nFinger], template);
			
			            //System.out.printf("%s was enrolled.\n", "Argie's fingerprint");
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
