

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


import DAO.BaseDAO;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;


/**
 * Servlet implementation class Sample
 */
@WebServlet("/Sample")
public class Sample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sample() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Servlet in adding fingerprint.
		 * */
		
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
				try {
					
		            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		            int badImageQuality = 0;
		            int startCaptureFailed = 0;
		            
		            while (enrollment.getFeaturesNeeded() > 0){
		            	 DPFPSample sample = null;
		                System.out.println("fingerprint needed :"+enrollment.getFeaturesNeeded());
		                
		                final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
		                DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
		                
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
		                	System.out.println("failed start capture:"+startCaptureFailed);
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
		                } finally {
		                    capture.stopCapture();
		                }
		                if (sample == null){
		                	System.out.println("sample is null.");
		                    continue;
		                }
		                
		                DPFPFeatureSet featureSet = null;
		                DPFPFeatureSet featureSet1;
		                try {
		                	BaseDAO daoForFM = new BaseDAO();
		                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
		                    String household_id = request.getParameter("id");
		                    int count = daoForFM.testIfExist(false, "select * from fingerprint_tbl_tempForFM where household_id = '"+household_id+"' ");
		                    if(count==0){
		                    	featureSet1 = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		                    	daoForFM.add_fingerprintForFM(false, household_id , featureSet1.serialize());
		                    }
		                } catch (DPFPImageQualityException e) {
		                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());
		                    badImageQuality = 1;
		                    break;
		                } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
		                enrollment.addFeatures(featureSet);
		                enrollment.getTemplate();
		                
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
			                PrintWriter out1= response.getWriter();
			                JSONObject obj=new JSONObject();
			            	try {
			    				obj.put("failedToEnroll", "0");
			    				obj.put("badImageQuality", "0");
			    			} catch (JSONException e2) {
			    				// TODO Auto-generated catch block
			    				e2.printStackTrace();
			    			}
			                out1.print(obj);
			    			out1.flush();
			    			out1.close();
			    			/*add fingerprint*/
			    			String hh_id = request.getParameter("id");
			    			
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
			    			
			    			int ctr = dao.testIffingerprintExist(false, hh_id);
			    			int mun_id = dao.getMunId2(hh_id);
			    			String user_id = (String)session.getAttribute("user_id");
			    			int team_id = dao.getTeamId();
			    			int server_id = dao.getServerId();
							if(ctr==0){
								dao.add_fingerprint(false, hh_id, fingerprint_byte, convertedDate, curTime,server_id,team_id,user_id,mun_id);
							}
							else{
								dao.update_fingerprint(false, hh_id, fingerprint_byte, convertedDate, curTime,server_id,team_id,user_id,mun_id);
								String mun_name = dao.getName_ofmunUser(request.getParameter("user"), request.getParameter("pass"));
								dao.add_logs(false, convertedDate, curTime, "Household ID "+hh_id +" fingerprint changed by Municipal Link "+mun_name);
							}
			            	
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
