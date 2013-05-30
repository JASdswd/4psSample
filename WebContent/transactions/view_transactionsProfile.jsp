<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,java.awt.*,javax.swing.*"%>
<%@ page import="java.text.*"%> 
    <% String cPath = request.getContextPath(); %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
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
<link rel="stylesheet" href="<%= cPath %>/css/add_transactions_CSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/view_transactionProfileCSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />


<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>

<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>

	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.mouse.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.draggable.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.position.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.resizable.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.effects.core.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.effects.blind.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.effects.explode.js"></script>

<script src="<%= cPath %>/pro_drop_1/stuHover.js" type="text/javascript"></script>
<script type="text/javascript" src="<%= cPath %>/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/ddaccordion.js"></script>
<script type="text/javascript" src="<%= cPath %>/ddaccordion_init.js"></script>
<script type="text/javascript" src="<%= cPath %>/hide_show.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/validateNumber.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/validate_updateProfile.js"></script>
<script type="text/javascript" src="<%= cPath %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css">
<script type="text/javascript" src="<%=cPath %>/capture-image/captureImg.js"></script>
<link type="text/css" rel="stylesheet" href="<%=cPath %>/capture-image/captureImg.css">
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>

<script type="text/javascript" src="<%= cPath %>/js/view_transactionProfile.js"></script>
<script language="JavaScript">

var validNavigation = false;
var sURL = unescape(window.location.pathname);
$(document).ready(function(){
	$('#divChangePhotoBTN').addClass('hidden');
	$('#divEmptyhead').hover(function(){
		$('#divChangePhotoBTN').removeClass('hidden');
	},function(){
		$('#divChangePhotoBTN').addClass('hidden');
	});
	$('#divChangePhotoBTN').hover(function(){
		$('#divChangePhotoBTN').removeClass('hidden');
	},function(){
		$('#divChangePhotoBTN').addClass('hidden');
	});
	
	
	$('#changeFingerprint_btn').addClass('hidden');
	$('#tdFingerprint').hover(function(){
		$('#changeFingerprint_btn').removeClass('hidden');
	},function(){
		$('#changeFingerprint_btn').addClass('hidden');
	});
	$('#changeFingerprint_btn').hover(function(){
		$('#changeFingerprint_btn').removeClass('hidden');
	},function(){
		$('#changeFingerprint_btn').addClass('hidden');
	});
	/* $('#divEmptyhead').mouseout(function(){
		$('#divChangePhotoBTN').hide();
	});
	$('#divChangePhotoBTN').hide(); */
	/* $('#btn_changeGrantee').button({
		icons : {primary : "ui-icon-wrench" }
	}); */
	$('#grsCasesBtn').button({
		icons : {primary : "ui-icon-circle-plus" }
	});
	
	$(function() {
		$( "#radio" ).buttonset();
	});
	$(window).unload(function(){});
	$('#update_h_p1').hide();
	$('#update_h_p2').hide();
	$('#update_h_p3').hide();
	$('#h_pregnant_no1').hide();
	$('#h_pregnant_yes1').hide();
	
	$('#btn_fingerprint').click(function(){
		document.getElementById("confirm_password").value = "";
		$('#confirm_prov').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 250,
			width: 380,
			modal: true,
			buttons: {
				"Ok": function() {
					var password = document.getElementById("confirm_password").value;
					var username = document.getElementById("munLink_username").value;
					//xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain"); for provincial link
					xhrGo("POST","Password_Confirmation?confirmation_password="+password+"&username="+username, fingerprintConfirm, "plain"); //for municipal link
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$('#munLink_username').focus();
		
	});
	 $('#grsCasesBtn').click(function(){
		document.getElementById("confirm_password").value = "";
		$('#grsCasesDialog').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 520,
			width: 400,
			modal: true,
			buttons: {
				"Ok": function() {
					var password = document.getElementById("confirm_password").value;
					var username = document.getElementById("munLink_username").value;
					 var inputs = document.getElementsByName("grsCasesRadio");
					 var grsCase = "";
					 var grsCasesCombo = "";
					 var grsCasesServer = "";
					 var grsRemarks = "";
					 var household_id = $("#hidden_householdId").val();
					 grsCasesCombo = document.getElementById("grsCasesCombo").value;
					 grsCasesServer = document.getElementById("grsServer").value;
					 grsRemarks = document.getElementById("grsRemarks").value;
					for (var i = 0; i < inputs.length; i++) {
			              if (inputs[i].checked) {
			                grsCase = inputs[i].value;
			              }
			        }
					if(grsCase==""){
						alert("Please select GRS Case.");
					}
					else{
						if(grsCasesServer==""){
							alert("Please select Server. ");
						}
						else{
							xhrGo("POST","AddGrsCases?grsCase="+grsCase+"&grsCasesCombo="+grsCasesCombo+"&household_id="+household_id+"&grsServer="+grsCasesServer+"&grsRemarks="+grsRemarks, addGrsResult, "plain"); //for municipal link
						}
					}
					//xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain"); for provincial link
					//xhrGo("POST","AddGrsCases?confirmation_password="+password+"&username="+username, fingerprintConfirm, "plain"); //for municipal link
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		}); 
		
	});
	/* $('#grsCasesBtn').click(function(){
		document.getElementById("confirm_password").value = "";
		
		
		
	});
	 */
 	function addGrsResult(data){
		 if(data==1){
				//alert("password confirmed.!!");
				$( '#grsCasesDialog' ).dialog( "close" );
				$( "#addgrsOk" ).dialog({
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
			else{
				$( "#addgrsnotOk" ).dialog({
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
	}
	function fingerprintConfirm(data){
		if(data==1){
			//alert("password confirmed.!!");
			$( '#confirm_prov' ).dialog( "close" );
			add_fpt();
		}
		else{
			accessDenied();
		}
	}
	
});


function selectmale(){
	$('#as').removeClass();
	$('#as').addClass('attending');
	$('#update_h_p1').fadeOut();
	$('#update_h_p2').fadeOut();
	$('#update_h_p3').fadeOut();
	$('#h_pregnant_no1').fadeOut();
	$('#h_pregnant_yes1').fadeOut();
	
}
function selectfemale(){
	$('#update_h_p1').fadeIn();
	$('#update_h_p2').fadeIn();
	$('#update_h_p3').fadeIn();
	$('#h_pregnant_no1').fadeIn();
	$('#h_pregnant_yes1').fadeIn();
	$('#as').removeClass();
	$('#as').addClass('attending1');
}
function selectmale1(){
	$('#pp1').fadeOut();
	$('#pp2').fadeOut();
	$('#pp3').fadeOut();
	$('#h_pregnant_yes2').fadeOut();
	$('#h_pregnant_no2').fadeOut();
}
function selectfemale1(){
	$('#pp1').fadeIn();
	$('#pp2').fadeIn();
	$('#pp3').fadeIn();
	$('#h_pregnant_yes2').fadeIn();
	$('#h_pregnant_no2').fadeIn();
}
function doLoad()
{
    // the timeout value should be the same as in the "refresh" meta-tag
    setTimeout( "refresh()", 1800*1000 );
    //for image
    initCanvas(320,240);
}

function refresh()
{
    //  This version of the refresh function will cause a new
    //  entry in the visitor's history.  It is provided for
    //  those browsers that only support JavaScript 1.0.
    //
    window.location.href = sURL;
}
//-->


</script>

<script language="JavaScript1.1">
function refresh()
{
    //  This version does NOT cause an entry in the browser's
    //  page view history.  Most browsers will always retrieve
    //  the document from the web-server whether it is already
    //  in the browsers page-cache or not.
    //  
    window.location.replace( sURL );
}
//-->
</script>

<script language="JavaScript1.2">
function refresh()
{
    //  This version of the refresh function will be invoked
    //  for browsers that support JavaScript version 1.2
    //
    
    //  The argument to the location.reload function determines
    //  if the browser should retrieve the document from the
    //  web-server.  In our example all we need to do is cause
    //  the JavaScript block in the document body to be
    //  re-evaluated.  If we needed to pull the document from
    //  the web-server again (such as where the document contents
    //  change dynamically) we would pass the argument as 'true'.
    //  
    window.location.reload( false );
}
function add_fpt(){
	var i = $("#hidden_householdId").val();
	var username = document.getElementById("munLink_username").value;
	var password = document.getElementById("confirm_password").value;
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
	xhrGo("POST","Sample?id="+i+"&ctrl="+0+"&pass="+password+"&user="+username, w, "plain");
}
function w(data){
	var x=eval('('+data+')');
	if(x.failedToEnroll == 1){
		$('#fpt_dialog').dialog('close');
		//alert("Failed to enroll the finger.");
		$('#FailedToEnroll').dialog({
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
		validNavigation = false;
	}
	else{
		if(x.badImageQuality == 1){
			$('#fpt_dialog').dialog('close');
			
			$('#BadImage').dialog({
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
		else{
			if(x.startCaptureFailed == 1){
				
				//The fingerprint reader is already used.
				
				$('#fpt_dialog').dialog('close');
				validNavigation = false;
				$('#startCaptureFailed').dialog({
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
			else{
				validNavigation = false;
				document.location.href = "<%=cPath %>/View_transactions2?hh_id="+$("#hidden_householdId").val()+"&john=jd85heh23ydbdja";
			}
		}
		
	}
	
}

 
/* ASK CONFIRMATION TO THE USER TO ABANDON THE SHIP.. HAHA... */
var dont_confirm_leave = 0; //set dont_confirm_leave to 1 when you want the user to be able to leave withou confirmation
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

function changeBrgyData(){
	var mun = document.getElementById("municipality").value;
	//alert(mun);
	//var mun =  ("municipality");
	xhrGo("GET", "<%= cPath %>/Get_Brgy_name?municipal="+mun, displayBrgy, "plain");
}


function displayBrgy(data){
	//alert(data);
	var x = eval('('+data+')');
	var st= "";
	
	for(var m=0;m<x.data.length;m++){
		
			st += "<option value='"+ x.data[m].brgy_id+"' >"+x.data[m].barangay+" </option>";
		
	}
	//alert(st);
	document.getElementById("barangay").innerHTML = st;
	/* alert(document.getElementById("new").innerHTML);
	document.getElementById("newVal").show();
	document.getElementById("default").hide(); */
	
}


function displayBrgy2(data){
	//alert(data);
	var x = eval('('+data+')');
	var st= "";
	var brgy = document.getElementById("brgy").value;
	//alert(brgy);
	for(var m=0;m<x.data.length;m++){
		
			if(brgy == x.data[m].brgy_id){
				st += "<option selected = 'selected' value='"+ x.data[m].brgy_id+"' >"+x.data[m].barangay+" </option>";
			}
			else{
				st += "<option value='"+ x.data[m].brgy_id+"' >"+x.data[m].barangay+" </option>";
			}
		
	}
	//alert(st);
	document.getElementById("barangay").innerHTML = st;
	/* alert(document.getElementById("new").innerHTML);
	document.getElementById("newVal").show();
	document.getElementById("default").hide(); */
	
}

//-->
</script>
<style type="text/css">
	#photo_tbl #divEmptyhead{
		border: 2px solid #000;
		box-shadow: 0px 0px 20px #ccc;
		-moz-box-shadow: 0px 0px 20px #ccc;
		-webkit-box-shadow: 0px 0px 20px #ccc;
		padding: 2px;
	}
	.ui-dialog-titlebar {
		border-radius: 0px;
		-moz-border-radius: 0px;
		-webkit-border-radius: 0px;
	}
	.ui-dialog{
		border-radius: 0px;
		-moz-border-radius: 0px;
		-webkit-border-radius: 0px;
		padding: 0px;
		box-shadow: 0px 0px 30px silver;
		-moz-box-shadow: 0px 0px 30px silver;
		-webkit-box-shadow: 0px 0px 30px silver;
	}
	.ui-dialog .ui-dialog-titlebar{
		padding: 5px;
	}
	#fingerprint{
		border: 1px solid #000;
		box-shadow: 0px 0px 30px silver;
		-moz-box-shadow: 0px 0px 30px silver;
		-webkit-box-shadow: 0px 0px 30px silver;
	}
	a{
		text-decoration: none;
	}
	#viewTransaction{
		font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
		font-size: 18px;
		color: green;
	}
	#viewTransaction:HOVER{
		text-decoration: underline;
		color: blue;
	}
	#main{
		font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
	}
	
	.input_select{
		margin-bottom: 5px;
		border:solid 2px #73A6FF;
	    background:#EFF5FF;
	    color:#000;
		padding: 5px 3px 5px 10px;
		outline: none;
		width:300px;
	}
	
	
	#btn_changeGrantee{
		/* margin-top: -20px; */
	}
	#head_img{
		border: 2px solid #000;
		box-shadow: 0px 0px 20px #ccc;
		-moz-box-shadow: 0px 0px 20px #ccc;
		-webkit-box-shadow: 0px 0px 20px #ccc;
		padding: 2px;
	} 
	label.font-new{
		/* font: monospace,verdana,sans-serif; */
		font: icon;
		font-size: 15px;
	}
	 #divChangePhotoBTN{
		margin-top: -10px;
	}
	#divChangePhotoBTN:HOVER{
		border-bottom: 1px solid black;
	}  
	#change_photo{
		font-size: 14px;
	}
	.fpt_btn{
		font-size: 14px;
	}
	 .fpt_btn:HOVER{
		text-decoration: underline;
	} 
	#btn_fingerprint{
		font-size: 14px;
	}
	#changeFingerprint_btn{
		margin-top: -15px;
	}
	#changeFingerprint_btn:HOVER{
		border-bottom: 1px solid black;
	}
	#grsCasesTBL{
		margin: 0 auto; 
		margin-top:12px;
		border:  1px solid black; ;
		border-collapse: collapse;
		font-size: 13px;
		width: 240px;
	}
	#divGRS{
		margin: 0 auto;
		width: 240px;
		margin-left: 70px;
	}
	#divGRS2{
		margin-top: 10px;
	}
	#grsCasesTBL td{
		padding-left: 5px;
		padding-right: 8px;
		font-size: 16px;
	}
	#grsCasesTBL tr:HOVER {
		background-color: #ddd;
		cursor: pointer;
	}
	#grsCasesTBL tr:FOCUS {
		background-color: #ddd;
	}
	.addressData{
		margin-bottom: 5px;
		border: none;
		border-bottom: 1px solid #8FA9BC;
		padding: 3px 3px 3px 3px;
		color:#000000;
		outline: none;
	}
</style>
</head>
<%
	HttpSession session1 = request.getSession(false);
	String household_id = (String)session1.getAttribute("household_id");
	System.out.println("household_id view_transactionProfile : ="+household_id);
	if(household_id.equals(null) || household_id==null){
		request.setAttribute("session_hh_id_null", true);
		response.sendRedirect(cPath+"/login/login1.jsp");
	}
%>
<%
	SimpleDateFormat dateSDF = new	SimpleDateFormat("MMMM dd,yyyy");
	SimpleDateFormat dw = new SimpleDateFormat("EEEE");
 	Calendar s = Calendar.getInstance();
	Date today = s.getTime();
	String sDate = dateSDF.format(today);
	String Dw = dw.format(today);
%>
<body onload="doLoad(); updateClock(); setInterval('updateClock()', 1000 )">
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
<input type="hidden" value="${mun}" id="mun" />
<input type="hidden" value="${brgy}" id="brgy" />
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

<div id="main" >
	<h3>4Ps beneficiary profile</h3>
	<div align="right" id="connectToViewTrans">
	<c:forEach items="${household}" var="name">
		<a id="viewTransaction" href="<%=cPath %>/View_trans?id=${name.household_id }">View Transactions of <c:out value="${name.head_name}"></c:out></a>
		<br/><br/>
		<button type="button" id="grsCasesBtn" onclick="add_grs()">Add GRS CASES</button>
	</c:forEach>
	</div>
	
		<div id="transactionForm">
		<c:forEach items="${household}" var="list">
				<table>
					<tr>
						<td>
							 <table id="photo_tbl">			
								<tr>
									<td align="center"  >
										<div >
										<c:choose>
											<c:when test="${photohead_exist}">
												<img  id="head_img" alt="photo of head" src="<%= cPath %>/ViewImage123?ctr=52494&view_id=<%= household_id %>" />
											</c:when>
											<c:otherwise>
												
													<img id="divEmptyhead" alt="photo of head" class="HF_photo" src="<%=cPath %>/image/head.jpg">
												
											</c:otherwise>
										</c:choose>	
										</div>
									</td>
									<td width="200px" align="center" id="tdFingerprint" >
										<c:choose>
											<c:when test="${fingerprint_exist}">
												<img id="fingerprint"  alt="fingerprint"  src="<%=cPath %>/images/fingerprint1.jpg">
											</c:when>
											<c:otherwise>
												<img alt="No fingerprint" id="fingerprint" src="<%=cPath %>/images/questionmark.jpg">
											</c:otherwise>
										</c:choose>	
											
										
									</td>
								</tr>
								<tr style="height: 20px;" >
								<c:choose>
									<c:when test="${user_Prov}">
										<td align="center" >
											<div id="radio">
												<input type="radio" id="radio1" name="radio" checked="checked" /><label for="radio1" onclick="pop_up_image();">Capture Photo</label>
												<input type="radio" id="radio2" name="radio" /><label for="radio2" onclick="change_photo_head();" >Upload Photo</label>
											</div>
										</td>
										<td align="center">
										
											<c:choose>
												<c:when test="${fingerprint_exist}">
													<div id="changeFingerprint_btn" class="hidden">
														<label><a href="#" id="btn_fingerprint" ><span class="ui-icon ui-icon-pencil" style="float:left;"></span>Change Fingerprint</a></label>
													</div>
												</c:when>
												<c:otherwise>
													<label><a href="#" id="AddFingerprintBTN" class="fpt_btn" onclick="add_fpt();">Add fingerprint</a></label>
												</c:otherwise>
											</c:choose>	
										</td>
									</c:when>
									<c:otherwise>
										<td align="center">
											<div id="radio">
												<input type="radio" id="radio1" name="radio" checked="checked" /><label for="radio1" onclick="pop_up_image();">Capture Photo</label>
												<input type="radio" id="radio2" name="radio" /><label for="radio2" onclick="change_photo_head();" >Upload Photo</label>
											</div>
										</td>
										<td align="center">
											<c:choose>
												<c:when test="${fingerprint_exist}">
													
												</c:when>
												<c:otherwise>
													<label><a href="#" id="AddFingerprintBTN2" class="fpt_btn" onclick="add_fpt();">Add fingerprint</a></label>
												</c:otherwise>
											</c:choose>	
											
										</td>
									</c:otherwise>
								</c:choose>
								</tr>
							
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>
										<label class="data123" >Barangay :</label>
									</td>
									<td>
										<input size="35" value="${list.barangay}" class="addressData" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td>
										<label class="data123" >Municipality :</label>
									</td>
									<td>
										<input size="35" value="${list.municipality}" class="addressData" readonly="readonly" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<input type="hidden" id="hidden_householdId" name = "household_id" value ="${list.household_id}" />
				<br/>
			<hr/>
			<div class="description">
					<c:choose>
						<c:when test="${f_position == '1'}">
							<h2>Household Info   --  Head </h2>
						</c:when>
						<c:when test="${f_position == '2'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Spouse </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Wife </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '3'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Son </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Daughter </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '4'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Brother </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Sister </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '5'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Son-in-law </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Daughter-in-law </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '6'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  GrandSon </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  GrandDaughter </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '7'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Father </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Mother </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '8'}">
							<h2>Household Info   --  Other Relatives </h2>
						</c:when>
						<c:when test="${f_position == '9'}">
							<h2>Household Info   --  Boarders </h2>
						</c:when>
						<c:when test="${f_position == '10'}">
							<h2>Household Info   --  Domestic Helper </h2>
						</c:when>
						<c:when test="${f_position == '11'}">
							<h2>Household Info   --  Non-Relative </h2>
						</c:when>
						<c:when test="${f_position == '12'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  GrandFather </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  GrandMother </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '13'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Uncle </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Auntie </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${f_position == '14'}">
							<c:choose>
								<c:when test="${h_gender}">
									<h2>Household Info   --  Nephew </h2>
								</c:when>
								<c:otherwise>
									<h2>Household Info   --  Niece </h2>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<h2>Household Info</h2>
							
						</c:otherwise>
					</c:choose>
			</div>
<table>
					<tr>
						<td><label class="data">Household ID NO. :</label></td>
						<td><label><c:out value="${list.household_id }"></c:out></label></td>
						<c:forEach items="${hh_setgroup}" var="h" >
							<td><label class="data">HH Set :</label></td>
							<td><label><c:out value="${h.sub }"></c:out></label></td>
							<td><label class="data">Set Group :</label></td>
							<td><label><c:out value="${h.dateoftransaction }"></c:out></label></td>
						</c:forEach>
						<td><label class="data">Status :</label></td>
							<td>
								<c:choose>
									<c:when test="${list.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${list.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${list.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${list.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${list.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${list.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
							</td>
					</tr>
					<tr>
						<td><label class="data">Name of Grantee :</label></td>
						<td colspan="7" ><label ><c:out value="${list.head_name}"></c:out></label></td>
					</tr>
				</table>
				<div class="indent">
					<label class="data">Household Member ID :</label><label ><c:out value="${list.householdMemberID }"></c:out></label>
					<label class="data">Age :</label><label ><c:out value="${list.h_age }"></c:out></label>
					<label class="data">Birthday :</label><label><c:out value="${list.h_birthday }"></c:out></label><br/>
					<br/>
					<c:choose>
							<c:when test="${h_gender}"><!-- if gender is equal to male -->
								<label class="data">Gender:</label>
										<label class="radio">Male</label>		
							</c:when>
							<c:otherwise>
								<label class="data">Gender:</label>
										<label  class="radio">Female</label>	
								<label class="data">Pregnant:</label>
								<c:choose>
									<c:when test="${list.h_pregnant == true }">
										<input type="radio"  checked="checked"/>
										<label class="radio">Yes</label><input type="radio" disabled="disabled" /><label  class="radio">No</label>
									</c:when>
									<c:otherwise>
										<input type="radio"  disabled="disabled" />
										<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${list.h_attendingSchool == true }">
							<label  class="attending1">Attending School:</label><input type="radio"  checked="checked"/>
							<label class="radio">Yes</label> <input type="radio" disabled="disabled" /><label class="radio">No</label>
						</c:when>
						<c:otherwise>
							<label  class="attending1">Attending School:</label><input type="radio" disabled="disabled" />
							<label class="radio">Yes</label> <input type="radio"  checked="checked"/><label class="radio">No</label>
						</c:otherwise>
					</c:choose>
				</div><br/><hr/>
				
				<c:forEach items="${wife}" var="wife">
						<c:if test="${wife.f_position == '1'}">
							<div class="description">
								<h2>Head Info</h2>
							</div><br/>
							<label class="data">Name :</label><label ><c:out value="${wife.ws_name }"></c:out></label>
							<label class="data">Status :</label>
								<c:choose>
									<c:when test="${wife.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${wife.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${wife.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${wife.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${wife.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${wife.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
							
							<div class="indent">
							<label class="data">Household Member ID :</label><label ><c:out value="${wife.ws_householdMemberID }"></c:out></label>
							<label class="data">Age :</label><label ><c:out value="${wife.ws_age}"></c:out></label>
							<label class="data">Birthday :</label><label ><c:out value="${wife.ws_birthday }"></c:out></label><br/>
							<br/>
							<c:choose>
								<c:when test="${h_gender}"><!-- if gender is equal to male -->
									<label class="data">Pregnant:</label>
									<c:choose>
										<c:when test="${wife.ws_pregnant == true }">
											<input type="radio"  checked="checked"/>
											<label class="radio" >Yes</label><input type="radio"  disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<input type="radio" disabled="disabled" />
											<label class="radio" >Yes</label><input type="radio"  checked="checked"/><label  class="radio">No</label>
										</c:otherwise>
									</c:choose>
									<!-- For wife / spouse  attending school -->
									<c:choose>
										<c:when test="${wife.ws_attendingSchool == true }">
											<label  class="attending">Attending School:</label><input type="radio" checked="checked">
											<label class="radio">Yes</label> <input type="radio"disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
											<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
										</c:otherwise>
										
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- For wife / spouse  attending school -->
									<c:choose>
										<c:when test="${wife.ws_attendingSchool == true }">
											<label  class="attending1">Attending School:</label><input type="radio" checked="checked">
											<label class="radio">Yes</label> <input type="radio"disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<label  class="attending1">Attending School:</label><input type="radio" disabled="disabled" />
											<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
										</c:otherwise>
										
									</c:choose>
								</c:otherwise>
							</c:choose><!-- end of c:choose after birthday -->
							
						</div><br/><hr/>
						</c:if>
						<c:if test="${wife.f_position == '2'}">
							<div class="description">
								<h2>Wife / Spouse Info</h2>
							</div>
							<label class="data">Name :</label><label ><c:out value="${wife.ws_name }"></c:out></label>
							<label class="data">Status :</label>
								<c:choose>
									<c:when test="${wife.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${wife.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${wife.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${wife.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${wife.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${wife.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
							<div class="indent">
							<label class="data">Household Member ID :</label><label ><c:out value="${wife.ws_householdMemberID }"></c:out></label>
							<label class="data">Age :</label><label ><c:out value="${wife.ws_age}"></c:out></label>
							<label class="data">Birthday :</label><label ><c:out value="${wife.ws_birthday }"></c:out></label><br/>
							<br/>
							<c:choose>
								<c:when test="${h_gender}"><!-- if gender is equal to male -->
									<label class="data">Pregnant:</label>
									<c:choose>
										<c:when test="${wife.ws_pregnant == true }">
											<input type="radio"  checked="checked"/>
											<label class="radio" >Yes</label><input type="radio"  disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<input type="radio" disabled="disabled" />
											<label class="radio" >Yes</label><input type="radio"  checked="checked"/><label  class="radio">No</label>
										</c:otherwise>
									</c:choose>
									<!-- For wife / spouse  attending school -->
									<c:choose>
										<c:when test="${wife.ws_attendingSchool == true }">
											<label  class="attending">Attending School:</label><input type="radio" checked="checked">
											<label class="radio">Yes</label> <input type="radio"disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
											<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
										</c:otherwise>
										
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- For wife / spouse  attending school -->
									<c:choose>
										<c:when test="${wife.ws_attendingSchool == true }">
											<label  class="attending1">Attending School:</label><input type="radio" checked="checked">
											<label class="radio">Yes</label> <input type="radio"disabled="disabled" /><label class="radio">No</label>
										</c:when>
										<c:otherwise>
											<label  class="attending1">Attending School:</label><input type="radio" disabled="disabled" />
											<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
										</c:otherwise>
										
									</c:choose>
								</c:otherwise>
							</c:choose><!-- end of c:choose after birthday -->
							
						</div><br/><hr/>
						</c:if>
						
				</c:forEach>
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${children}" var="c">
					<c:if test="${c.f_position == '3' && ex == '0'}">
						<div class="description">
							<h2>Son/Daughter Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items="${children}" var="children">
					
					<c:if test="${children.f_position == '3'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
								<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
					
				</c:forEach>
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '4' && ex == '0'}">
						<div class="description">
							<h2>Brother / Sister Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '4'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>
				
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${children}" var="c">
					<c:if test="${c.f_position == '5' && ex == '0'}">
						<div class="description">
							<h2>Son-in-law / Daughter-in-law Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${children}" var="children">
				<c:if test="${children.f_position == '5'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>
				<div class="description">
					<h2>Grandson/Grandaughter Info</h2>
				</div><br/>
				<c:forEach items="${grandchild}" var="grandchild">
					
					<div id="grandsonGrandaugther_${grandchild.gg_householdMemberID}">
						<table>
							<tr>
								<td>
									<input type="radio" name="gg_radio" id="${grandchild.gg_householdMemberID }" value="${grandchild.gg_name}" onclick="click_gg(this.id);"/>
								</td>
								<td>
								<label class="data">Name :</label>
									<label><c:out value="${grandchild.gg_name}"></c:out></label>
							<label class="data">Status :</label>
								<c:choose>
									<c:when test="${grandchild.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${grandchild.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${grandchild.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${grandchild.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${grandchild.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${grandchild.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label><c:out value="${grandchild.gg_householdMemberID }"></c:out></label>
										<label class="data">Age :</label><label><c:out value="${grandchild.gg_age}"></c:out></label>
										<label class="data">Birthday :</label> <label><c:out value="${grandchild.gg_birthday}"></c:out></label><br/>
											
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${grandchild.gg_pregnant}">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${grandchild.gg_attendingSchool == true }">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled" /><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>	
									</div>
								</td>
							</tr>
						</table>
						<br/><br/><hr/>
					</div>
				</c:forEach>
				
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '7' && ex == '0'}">
						<div class="description">
							<h2>Father / Mother Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '7'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>
				
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '8' && ex == '0'}">
						<div class="description">
							<h2>Other Relatives Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '8'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
							<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>
				
				
				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '9' && ex == '0'}">
						<div class="description">
							<h2>Boarders Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '9'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>


				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '10' && ex == '0'}">
						<div class="description">
							<h2>Domestic Helper Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '10'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>


				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '11' && ex == '0'}">
						<div class="description">
							<h2>Non-relative Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '11'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>


				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '12' && ex == '0'}">
						<div class="description">
							<h2>Grandfather / Grandmother Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '12'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>


				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '13' && ex == '0'}">
						<div class="description">
							<h2>Uncle / Auntie Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '13'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>


				<c:set var="ex" value="0" ></c:set>
				<c:forEach items = "${other_relatives}" var="c">
					<c:if test="${c.f_position == '14' && ex == '0'}">
						<div class="description">
							<h2>Nephew / Niece Info</h2>
						</div>
						<c:set var="ex" value="1" ></c:set>
					</c:if>
				</c:forEach>
				<c:forEach items = "${other_relatives}" var="children">
				<c:if test="${children.f_position == '14'}">
					
					<div id="sonDaugther_${children.sd_householdMemberID}">
					<table>
						<tr>
							<td>
								<input type="radio" name="sd_radio" id="${children.sd_householdMemberID}" value="${children.sd_name}" onclick="click_sd(this.id);"/>
							</td>
							<td>
							<label class="data">Name :</label>
								<label ><c:out value="${children.sd_name}"></c:out></label>
									<label class="data">Status :</label>
								<c:choose>
									<c:when test="${children.status == 1}">
										<label>Active</label>
									</c:when>
									<c:when test="${children.status == 2}">
										<label>Deceased</label>
									</c:when>
									<c:when test="${children.status == 3}">
										<label>Moved Out</label>
									</c:when>
									<c:when test="${children.status == 4}">
										<label>?????</label>
									</c:when>
									<c:when test="${children.status == 5}">
										<label>Duplicate</label>
									</c:when>
									<c:when test="${children.status == 6}">
										<label>Wrong Entry</label>
									</c:when>
								</c:choose>
									<div class="indent">
										<label class="data">Household Member ID :</label><label ><c:out value="${children.sd_householdMemberID}"></c:out></label>
										<label class="data" >Age :</label><label><c:out value="${children.sd_age}"></c:out></label>
										<label class="data">Birthday :</label><label ><c:out value="${children.sd_birthday}"></c:out></label>	<br/>
										<br/>
										<label class="data">Pregnant:</label>
										<c:choose>
											<c:when test="${children.sd_pregnant == true }">
												<input type="radio" checked="checked"/>
												<label class="radio">Yes</label><input type="radio" disabled="disabled"/><label  class="radio">No</label>
											</c:when>
											<c:otherwise>
												<input type="radio" disabled="disabled"/>
												<label class="radio">Yes</label><input type="radio" checked="checked"/><label  class="radio">No</label>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${children.sd_attendingSchool == true}">
												<label  class="attending">Attending School:</label><input type="radio" checked="checked"/>
												<label class="radio">Yes</label> <input type="radio" disabled="disabled"/><label class="radio">No</label>
											</c:when>
											<c:otherwise>
												<label  class="attending">Attending School:</label><input type="radio" disabled="disabled" />
												<label class="radio">Yes</label> <input type="radio" checked="checked"/><label class="radio">No</label>
											</c:otherwise>
										</c:choose>
										
									</div>
									
							</td>
						</tr>
					</table>
					<br/><br/><hr/>
					</div>
					</c:if>
				</c:forEach>
				
				
				
				
				<br/>

			</c:forEach>
				
				<br/>
				
		</div>
</div>
</div> <!-- end of main content -->

<!-- ****************************************************************************************************************************************************** -->
<!-- ****************************************************************** Dialogs *************************************************************************** -->
<!-- ****************************************************************************************************************************************************** -->

<div id="FailedToEnroll" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to enroll the finger. Please try again.</p>
</div>
<div id="BadImage" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Bad image quality. Please try again.</p>
</div>
<div id="startCaptureFailed" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to start capture. The fingerprint reader is used by another application.</p>
</div>

<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="passwordDenied" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access Denied..!!!</p>
</div>
<div id="addgrsOk" title="4Ps Message" class="hidden">
	<p>GRS Cases Successfully added.</p>
</div>
<div id="addgrsnotOk" title="4Ps Message" class="hidden">
	<p>Failed to add GRS Cases.</p>
</div>
<div id="change-photo" title="Change photo of head">
	<form action="<%=cPath %>/ChangePhoto123" id="photo_change" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="hh_id" value="<c:out value="${hh_id}"></c:out>"/>
		<label>Photo of head :</label><label class="button-dialog"><input name="file" type="file" id="changePhoto" /></label><br/>
		
	</form>	
</div>
<!-- confirm_prov an id kai provincial link man an may power nga maka change og fingerprint sauna karon kai ang municipal link na man -->
<div class="hidden" id="confirm_prov" title="Confirmation">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the username and password of Municipal Link for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>Username:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" id='munLink_username' name="munLink_username" value=""/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password" id="confirm_password" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div class="hidden" id="grsCasesDialog" title="GRS CASES">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Add GRS CASES	
	</p>
	<table id="grsCasesTBL" border="1" >
		<tr onclick="selectedGRSCases('case_mrd')">
			<td><label>MRD&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_mrd" value="MRD"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_mro')">
			<td ><label>MRO</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_mro" value="MRO"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_nrf')">
			<td><label>NRF</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_nrf" value="NRF"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_cgo')">
			<td><label>CGO</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_cgo" value="CGO"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_n14d')">
			<td><label>N14D&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_n14d" value="N14D"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_mco')">
			<td><label>MCO</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_mco" value="MCO"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_deo')">
			<td><label>DEO&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_deo" value="DEO"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_fubir')">
			<td><label>FUBIR</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_fubir" value="FUBIR"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_fudnr')">
			<td><label>FUDNR&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_fudnr" value="FUDNR"/>
			</td>
		</tr>
		<tr onclick="selectedGRSCases('case_fudno')">
			<td><label>FUDNO</label></td>
			<td><input type="radio" name="grsCasesRadio" id="case_fudno" value="FUDNO"/>
			</td>
		</tr>
	</table>
	<div id="divGRS2" >
		<table id="divGRS" >
			<tr>
				<td>
					<label>ID-OCP</label>
				</td>
				<td>
					<select name= "grsCasesCombo" id="grsCasesCombo">
						<option value="">------</option>
						<option value="LID">LID</option>
						<option value="TID">TID</option>
						<option value="OCP">OCP</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Server</label>
				</td>
				<td>
					<!-- <select name= "grsCasesCombo">
						<option value="">------</option>
						<option value="case_lid">1</option>
						<option value="case_tid">2</option>
						<option value="case_ocp">3</option>
					</select> -->
					<select name="grsServer" id="grsServer">
							<option value="">------</option>
							<c:forEach items="${servers}" var="list">
								<c:if test="${serv==list.serverId}">
									<option selected value="${list.serverId}" ><c:out value="${list.serverId}"></c:out></option>
								</c:if>
								<c:if test="${serv!=list.serverId}">
									<option value="${list.serverId}" ><c:out value="${list.serverId}"></c:out></option>
								</c:if>
								
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		<label></label>
	</div>
	<div> 
		<table>
			<tr>
				<td>
					<label>Remarks</label>
				</td>
				<td>
					<textarea rows="" cols="" style="width: 270px;height: 90px;font-size: 16px;" id="grsRemarks" name="grsRemarks"></textarea>
				</td>
			</tr>
		</table>
	</div>
	
</div>
<div id="select_sd" title="Error" class="hidden">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>No selected option.
	</p>
</div>
<div id ="editing_sd" style="display: none"></div>
<div id ="editing_gg" style="display: none"></div>
<div id ="editing_p" style="display: none"></div>
<div id ="add_fingerprint" style="display: none"></div>
<c:if test="${succesAdd}">
	<script type="text/javascript">
		success();
	</script>
</c:if>


<div id="fpt_dialog" class="hidden">
	<p><label>Scan your fingerprint 4 times.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%=cPath %>/images/loading.gif" /></p>

</div>
<!-- div for uploading photo -->
<div class="hidden" id="image-pop-up">
	<button type="submit" id="capture_btn" onclick="captureToCanvasBackground()">Capture</button>
	<table>
		<tr>
			<td>
				<div class="container">
					<object  id="iembedflash" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="320" height="240">
					<param name="movie" value="camcanvas.swf" />
					<param name="quality" value="high" />
					<param name="allowScriptAccess" value="always" />
					<embed id="embedflash" allowScriptAccess="always" src="<%= cPath %>/capture-image/camcanvas.swf" quality="high" width="320" height="240" type="application/x-shockwave-flash" mayscript="true" />
					</object>	
				</div>
			</td>
			<td>
				<form action="<%=cPath %>/GetImgCanvas" name="myuploadImageform" method="post" >
					<input type="hidden" name="hh_id" value="${hh_id}"/>
					<div id="image_div">
					</div>
				</form>
			</td>
		</tr>
	</table>
	<br/>
	<div style="display: none;">
		<canvas style="border:1px solid black" id="canvas" width="320" height="240"></canvas>  
	</div>
  	<br/>
</div>
</div> <!-- End of page wrap -->

</body>
</html>