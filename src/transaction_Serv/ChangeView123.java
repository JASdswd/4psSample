package transaction_Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
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
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;


import transaction_DAO.Transaction_DAO;
import DAO.BaseDAO;
import bean.transactionBean;
import beans.BeansAdd;
import beans.Beanslistson;

/**
 * Servlet implementation class ChangeView123
 */
@WebServlet("/ChangeView123")
public class ChangeView123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeView123() {
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
				
				ArrayList<BeansAdd>list_house=new ArrayList<BeansAdd>();
				ArrayList<Beanslistson>reason_list=new ArrayList<Beanslistson>();
				try{
					BaseDAO bDAO = new BaseDAO();
					int count = bDAO.testIffingerprintExist(false, request.getParameter("household_id"));
					if(count>0){
					
						int verifyCtrl = 0;
						PrintWriter out= response.getWriter();
						JSONArray array=new JSONArray();
						//JSONArray array1=new JSONArray();
						JSONObject objectall=new JSONObject();
						Calendar calendar= Calendar.getInstance();
						DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
						SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy");
						//MainForm form = new MainForm(request.getParameter("household_id"), 2);
						//int verifyCtrl = form.getVerifyCtrl();
						//System.out.println("verifyCtrl = "+verifyCtrl);
						
						
						
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
			        				BaseDAO dao = new BaseDAO();
			        				bean = dao.getfingerprint(false, request.getParameter("household_id"));
			        			} catch (SQLException e) {
			        				// TODO Auto-generated catch block
			        				e.printStackTrace();
			        			}
			        			
			        			byte[] data = null;
			    				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
			    				int count1 = 1;
			    				String household_ctr = "";
			    				int controller = 0;
			    				
			    					data = bean.getFingerprint();
			    					t.deserialize(data); /* Converts byte[] into DPFP template.*/
			    					DPFPVerificationResult result = 
			    					matcher.verify(featureSet, t);
			    					if (result.isVerified()){
			    						System.out.print("-------------------------count:"+count1+++"hsh:"+household_ctr);
			    						controller = 1;
			    						
			    					}
			    					else{
			    						System.out.print("count:"+count++);
			    					}
			    				
			        			if(controller>0){
			        				System.out.println("fingerprint matched.");
			        				String day=format.format(calendar.getTime());
									System.out.println("date="+day);
									String time=timeInstance.format(Calendar.getInstance().getTime());
									System.out.println("time="+time);
									
									String household_id=request.getParameter("household_id");
									String month=request.getParameter("month");
									//int year=Integer.parseInt(request.getParameter("year"));
									String id=request.getParameter("id");
									System.out.println("id:"+id);
									String comment=request.getParameter("comment");
									Transaction_DAO dao = new Transaction_DAO();
									dao.updaterecieve(household_id, month, day,time,1,comment);
									
									/*JSONObject transaction_time=new JSONObject();
									transaction_time.put("transaction_time", time);
									out.print(transaction_time);*/
									
									list_house=dao.list_house(household_id, month);
									//reason_list=dao.listreason();
									for(BeansAdd i:list_house){
										JSONObject obj=new JSONObject();
										obj.put("household_id", i.getHousehold_id());
										obj.put("month", i.getMonth());
										/*obj.put("year", i.getYear());*/
										obj.put("amount", i.getAmount());
										obj.put("recieve", 1);
										obj.put("date_receive", day);
										obj.put("time", time);
										obj.put("comment", i.getComment());
										obj.put("sub", i.getSub());
										obj.put("munLink_name", i.getMunLink_name());
										
										array.put(obj);
									}
									objectall.put("data", array );
									
									objectall.put("id", id);
									String start_transaction = (String)session.getAttribute("startTransaction");
									System.out.println("start_transaction:"+start_transaction);
									Date date = new Date();
								    SimpleDateFormat simpDate;

								    simpDate = new SimpleDateFormat("kk:mm:ss");
								    System.out.println("24 hour format:"+simpDate.format(date));
									if(start_transaction == null || start_transaction.equals(null)){
										System.out.println("starting of transaction");
										objectall.put("transaction_time", simpDate.format(date));
										session.setAttribute("transactionTime", simpDate.format(date));
									}
									else{
										System.out.println("nag'start na an transaction");
										objectall.put("has_transaction_time", true);
										objectall.put("transaction_time", session.getAttribute("transactionTime"));
									}
									session.setAttribute("startTransaction", "yes");
									
									System.out.println(objectall);
									out.print(objectall);
									
									out.flush();
									out.close();
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
					}
					else{
						PrintWriter out= response.getWriter();
						JSONObject obj=new JSONObject();
						obj.put("trys", true);
						out.print(obj);
						out.flush();
						out.close();
					}
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

}
