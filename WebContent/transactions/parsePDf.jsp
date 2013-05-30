<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String cPath = request.getContextPath(); %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="java.util.*"%>
	<%@ page import="java.text.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",-1);
%>
<html >
<%	

if(session.getAttribute("username")==null){
	System.out.println("username is null parse.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pantawid Pamilyang Pilipino Program</title>
<meta name="Author" content="Stu Nicholls" />
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" href="<%= cPath %>/css/view_transactionProfileCSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" href="<%= cPath %>/css/add_transactions_CSS.css"/>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax3.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>

<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>

<script type="text/javascript">
		var validNavigation = false;
		function confirmLogout(distination){
			$( "#dialog-confirm" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
						document.location.href = distination;
					},
					"Cancel": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			
		}
		$(document).ready(function(){
			//$('#load').hide();
			$("#submit_btn").button({
				icons : {primary : "ui-icon-circle-check" }
			});
			
		});
		function check_file(picField) {
			 var picFile = picField;
			 var imagePath = document.getElementById(picFile).value;
			 var pathLength = imagePath.length;
			 var lastDot = imagePath.lastIndexOf(".");
			 var fileType = imagePath.substring(lastDot,pathLength);
			 var input = document.getElementById('pdf_file');
			 var file;
			 if (!input.files[0]) {
				 $( "#select_a_file" ).dialog({
						show: "fade",
						hide: "fade",
						resizable: false,
						height:140,
						modal: true,
						buttons: {
							"Ok": function() {
								$( this ).dialog( "close" );
							}
						}
						});
			 }
			 else if((fileType == ".pdf")|| (fileType == ".PDF")) {
		        /*file = input.files[0];
		        if(file.size>1000000){
		        	 alert("Size of image must be lesser than 1mb(1 megabyte).");
		        	 return false;
		        }*/
					//$('#sign').show('fast');
		        /* show the loading gif.. Argie Abedejos commented this coz there is something he want to do. And it is secret only. haha..*/
					//$('#load').show();
					//document.getElementById("load").innerHTML="<img src=\"../images/loading.gif\" alt=\"loading\"/>";
					$('#fpt_dialog').dialog({
								show: "fade",
								hide: "fade",
								resizable: false,
								height: 150,
								open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
								closeOnEscape: false,
								draggable: false,
								width: 300,
								modal: true
							});
					
							validNavigation = true;
							//xhrGo("POST","Password_Confirmation?confirmation_password=4",fingerprint_confirm, "plain");
							xhrGo("POST","FinancialAnalyst_Confirmation", fingerprint_confirm, "plain");
				
			 }
			 else {
			 	/* alert("Please select a pdf file format."); */
			 	$( "#select_a_pdf" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Ok": function() {
						$( this ).dialog( "close" );
					}
				}
				});
			 }
		}
		
		/* One night na'buang kariyot. nag'Libog nganong wa mi'gana.. Now I know why.. ipa'agi ra d.i first sa servlet hehe.. death note L.. 
		function sample(){
			alert("has gwardia eropdf");
			xhrGo("POST","Password_Confirmation?confirmation_password=121", fingerprint_confirm, "plain");
			alert("ero gwardia");
		} */
		function fingerprint_confirm(data){
			var x=eval('('+data+')');
			validNavigation = false;
			$("#fpt_dialog").dialog('close');
			if(x.failedToVerify == 1){
				$('#verificationFailed').dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:180,
					modal: true,
					buttons: {
						"OK": function() {
							$( this ).dialog( "close" );
						}
					}
				});
				//alert("failed to verify");
			}
			else{
				if(x.fingerNotMatched){
					$('#noMatchFound').dialog({
						show: "fade",
						hide: "fade",
						resizable: false,
						height:140,
						modal: true,
						buttons: {
							"OK": function() {
								$( this ).dialog( "close" );
							}
						}
					});
				}
				else if(x.fingerMatched){
					//fingerprint matched.
					document.forms["parseForm"].submit();
					$('#submit_btn').attr('disabled','disabled');
					$('#load').show();
					validNavigation = true;
				}
			}
			
		}
		
		function its_not_leyte(){
			$( "#its_not_leyte" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Ok": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
		function parse_success(){
			$( "#parse_success" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:150,
				width: 400,
				modal: true,
				buttons: {
					"Ok": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
		function alreadyParsed(){
			$( "#alreadyParsed" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:150,
				width: 400,
				modal: true,
				buttons: {
					"Ok": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
		function not_in_database(){
			$( "#not_in_database" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:150,
				width: 400,
				modal: true,
				buttons: {
					"Ok": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
		
		$(document).ready(function(){
			$(window).unload(function(){});
			
		});
		var dont_confirm_leave = 0; //set dont_confirm_leave to 1 when you want the user to be able to leave without confirmation
		var leave_message = 'You sure you want to leave?';
		function goodbye(e) {
		  if (validNavigation) {
		    if (dont_confirm_leave!==1) {
		      if(!e) e = window.event;
		      //e.cancelBubble is supported by IE - this will kill the bubbling process.
		      e.cancelBubble = false;
		      e.returnValue = leave_message;
		      //e.stopPropagation works in Firefox.
		      if (e.stopPropagation) {
		    	  
		        e.stopPropagation();
		        e.preventDefault();
		      }
		      //return works for Chrome and Safari
		      return leave_message;
		    }
		  }
		}
		window.onbeforeunload=goodbye; 
		</script>
<style type="text/css">
	.ui-widget ui-widget-content ui-corner-all{
		width: 400px;
	}
	#content{
		padding: 10px;
		border: none;
	}
	#login-title{
		border-bottom: 1px solid gray;
		padding: 1px;
	}
	#login-title h3{
		margin-bottom: 20px;
	}
	a{
		text-decoration: none;
	}
	#main-div{
		margin-top: 50px;
		width: 450px;
		height: 350px;
		padding: 5px;
		box-shadow: 0px 0px 10px silver;
		-moz-box-shadow: 0px 0px 10px silver;
		-webkit-box-shadow: 0px 0px 10px silver;
	}
	#main{
		height: 700px;
	}
	.ui-dialog{
		font-size: 12px;
	}
	#its_not_leyte{
		display: none;
	}
	.ui-button-text{
		font-size: 12px; 
	}
	.hidden{
		display: none;
	}
	#load{
		display: none;
	}
</style>
</head>
<%
	SimpleDateFormat dateSDF = new	SimpleDateFormat("MMMM dd,yyyy");
	SimpleDateFormat dw = new SimpleDateFormat("EEEE");
 	Calendar s = Calendar.getInstance();
	Date today = s.getTime();
	String sDate = dateSDF.format(today);
	String Dw = dw.format(today);
%>
<body onload="updateClock(); setInterval('updateClock()', 1000 )">

<div id="page-wrap">
<div id="header" >
<div id="logo">
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
	<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu" >

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>

<div id="displayTD">
<table id="tbl">
	<tr> <td><input checked="checked" onclick="hide();" id="hide" type="checkbox"/> Keep this date & time in view</td> </tr>
 	<tr>
 		<td class="day"><%if(Dw.equals("Sunday")){%> <font id="sun"><% out.println(Dw); %>,</font> 
						  <%}else{%> <font class="mon-sat"><% out.println(Dw); %>,</font><% } %>
		<!-- </td>
 		<td class="timeDate"> --><font class="mon-sat"><% out.println(sDate); %></font><!-- <hr> --></td></tr><!--displaying Date[jm]  -->
	<tr><td class="timeDate"> <span id="clock">&nbsp;</span><!-- <hr> --> </td></tr><!--displaying Time[jm]  -->
	</table>		 
</div>

<div id="main" >
	<center>
	<div class="fakewindowcontain">
		<br/>
			<div  id="main-div" class="ui-widget ui-widget-content ui-corner-all">
				<div class="ui-dialog-content ui-widget-content" id="content">
					<div id="login-title">
						<br/>
						<h3>Import pdf file</h3>
					</div>
					<br/>
					<form action="<%=cPath %>/PdfParse" name="parseForm" method="post" enctype="multipart/form-data" >
						<br/>
						<input type="file" name="pdfFile" class="input" id="pdf_file"/>
						<button id="submit_btn" type="button" onclick="check_file('pdf_file')">Submit</button>
					</form>
				</div>
				<div id="load">
					<!-- <p><label>Processing...</label></p> -->
					<img alt="loading" src="<%= cPath %>/images/loadingtxt.gif">
					<br/>
					<img src="<%= cPath %>/images/loading.gif" />
				</div>
			</div>
	</div>
</center>
	
</div>	
</div> <!-- end of main content -->

<!-- ****************************************************************************************************************************************************** -->
<!-- ****************************************************************** Dialogs *************************************************************************** -->
<!-- ****************************************************************************************************************************************************** -->
<div id="its_not_leyte" title="Pdf file validation.">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		The pdf file you submit was not for the province of Maguindanao.
	</p>
</div>
<div id="parse_success" class="hidden" title="4Ps Message">
	<p><span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 20px 0;"></span>
		<c:out value="${parsedTransactions}"></c:out> transactions for the month of <c:out value="${month}"></c:out> succesfully imported to database.
	</p>
</div>

<div id="alreadyParsed" class="hidden" title="4Ps Message">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		The pdf file you selected was already imported to database.
	</p>
</div>
<div id="not_in_database" class="hidden" title="4Ps Message">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		All Beneficiaries on the pdf file you've selected was not yet added to the database.
	</p>
</div>
<div id="select_a_pdf" class="hidden" title="Pdf file validation.">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Please select a pdf file format.
	</p>
</div>
<div id="select_a_file" class="hidden" title="Pdf file validation.">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Please select a pdf file before clicking submit.
	</p>
</div>

<c:if test="${its_not_leyte}">
	<script type="text/javascript">
		its_not_leyte();
	</script>
</c:if>
<c:if test="${parse_success}">
	<script type="text/javascript">
		parse_success();
	</script>
</c:if>
<c:if test="${alreadyParsed}">
	<script type="text/javascript">
		alreadyParsed();
	</script>
</c:if>
<c:if test="${not_in_database}">
	<script type="text/javascript">
		not_in_database();
	</script>
</c:if>
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
<div id="fpt_dialog" style="display: none;">
	<p>Scan fingerprint of Financial Analyst for security verification.</p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cPath %>/images/loading.gif" /></p>

</div>
<div id="verificationFailed" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to perform verification. The fingerprint reader is used by another application.</p>
</div>
<div id="noMatchFound" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access Denied. Fingerprint didn't matched.</p>
</div>
</div> <!-- End of page wrap -->

</body>
</html>