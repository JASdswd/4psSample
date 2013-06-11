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
HttpSession session1 = request.getSession(false);
String household_id = (String)session1.getAttribute("household_id");
System.out.println("household_id view_transactionProfile : ="+household_id);

if(session.getAttribute("username")==null){
	System.out.println("username is null parse.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Pantawid Pamilyang Pilipino Program</title>
<meta name="Author" content="Stu Nicholls" />
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax3.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" href="<%= cPath %>/css/add_transactions_CSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />

<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>

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
<script type="text/javascript" src="<%= cPath %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css">
<script type="text/javascript" src="<%=cPath %>/capture-image/captureImg.js"></script>
<link type="text/css" rel="stylesheet" href="<%=cPath %>/capture-image/captureImg.css">
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript" src="<%= cPath %>/js/add_benificiary.js"></script>
<script type="text/javascript">
var validNavigation = false;
$(document).ready(function(){
	$(window).unload(function(){});
	$("#manageAcc").hide();
	$("#upAlertYes").hide();
	$("#upAlertNo").hide();
	$("#continueErr").hide();
	$("#err").hide();
	$("#pl_acc").button({
		icons : {primary : "ui-icon-locked" }
	});
	$("#pl_edit").button({
		icons : {primary : "ui-icon-pencil" }
	});
	$("#pl_save").button({
		icons : {primary : "ui-icon-circle-check" }
	});
	$("#btn_fingerprint").button({
		icons: {primary:"ui-icon-locked"}
	});
	$("#btn_Addfingerprint").button({
		icons: {primary:"ui-icon-locked"}
	});
	$( "#radio" ).buttonset();
	$("#passwordDenied").hide();
	$("#confirm_prov").hide();
	$("#fpt_dialog").hide();
	$("#FailedToEnroll").hide();
	$("#BadImage").hide();
	$("#startCaptureFailed").hide();
	$(".hidden").hide();
	
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
					xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm123, "plain");
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$('#confirm_password').focus();
		
	});
	
	$('#btn_photo').click(function(){		
		document.getElementById("confirm_password2").value = "";
		$('#confirm_prov2').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 250,
			width: 380,
			modal: true,
			buttons: {
				"Ok": function() {
					var password = document.getElementById("confirm_password2").value;
					xhrGo("POST","Password_Confirmation?confirmation_password="+password, photoConfirm123, "plain");
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$('#confirm_password2').focus();
	});
	
});

function photoConfirm123(data){
	if(data==1){
		change_photo_head();
		$("#confirm_prov2").dialog("close");
	}
	else{
		accessDenied();
	}
}
function fingerprintConfirm123(data){
	if(data==1){
		$( '#confirm_prov' ).dialog( "close" );
		add_fpt();
	}
	else{
		accessDenied();
	}
}
function add_fpt(){
	var user_role = $("#user_role").val();
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
	xhrGo("POST","User2Fingerprint?user_role="+user_role, showResult, "plain");
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
function showResult(data){
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
				//refresh();
				var user_role = $("#user_role").val();
				document.location.href = "<%=cPath %>/ViewAllUser2?id="+user_role;
			}
		}
		
	}
	
}


function change_photo_head(){
	$( "#change-photo" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:180,
		width: 415,
		modal: true,
		buttons: {
			"Submit": function() {
				if(checkPhoto('changePhoto')){
					$( this ).dialog( "close" );
					document.forms["photo_change"].submit();
				}
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}

function checkPhoto(picField) {
	 var picFile = picField;
	 var imagePath = document.getElementById(picFile).value;
	 var pathLength = imagePath.length;
	 var lastDot = imagePath.lastIndexOf(".");
	 var fileType = imagePath.substring(lastDot,pathLength);
	 var input = document.getElementById('changePhoto');
	 var file;

	 if (!input.files[0]) {
	        alert("Please select an image before clicking submit.");
	        return false;
	 }
	 else if((fileType == ".gif") || (fileType == ".jpg") || (fileType == ".jpeg") || (fileType == ".png") || (fileType == ".GIF") || (fileType == ".JPG") || (fileType == ".JPEG") || (fileType == ".PNG")) {
		   
       file = input.files[0];
       if(file.size>1000000){
       	 alert("Size of image must be lesser than 1mb(1 megabyte).");
       	 return false;
       }
		return true;
	 }
	 else {
	 	alert("This system supports .JPG, .PNG, and .GIF image formats. Your file-type is " + fileType + ".");
	 	return false;
	 }
}

function update_financial2(){
	
	var fname = document.getElementById("fname").value;
	var lname = document.getElementById("lname").value;
	
	var str = "";
	
	str += "<label>First Name:</label><input type='text' name='f_fname' id='pl_fname' value='"+fname+"' class='input' size='30' autocomplete = 'off'/><br/>"+
			"<label>Last Name:</label><input type='text' name='f_lname' id='pl_lname' value='"+lname+"' class='input' size='30' autocomplete = 'off'/>";
	document.getElementById("editable").value = "1";
	document.getElementById("info_div").innerHTML = str;
}
function update_user2(){
	var fname = document.getElementById("f_fname").value;
	var lname = document.getElementById("f_lname").value;
	var gender = document.getElementById("f_gen").value;
	var email = document.getElementById("f_email").value;
	var contact = document.getElementById("f_contact").value;
	
	var str = "";
	
	str += "<table> "+
				"<tr><td><label>First Name: </label></td> <td><input required type='text' maxlength='50' name='f_fname' id='fa_fname' value='"+fname+"' class='input' size='30' autocomplete = 'off'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td/>"+
					"<td><label>Contact No.: </label></td> <td><input required  type='text' id='fa_contact' maxlength='15' onkeypress='return numbersonly(event,false);' name='f_contact' value='"+contact+"' class='input' size='30' autocomplete = 'off'/></td></tr>"+
				"<tr><td><label>Last Name: </label></td> <td><input required type='text' maxlength='50' name='f_lname' id='fa_lname' value='"+lname+"' class='input' size='30' autocomplete = 'off'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td/>"+
					"<td><label>Email Address: </label></td> <td><input  type='text' id='fa_email' maxlength='30' name='f_email' value='"+email+"' class='input' size='30' autocomplete = 'off'/></td></tr>";
			
				if(gender =="f" || gender=="F"){
					str+= "<tr><td><label>Gender:</label></td> <td><input required type='radio' id='m' name='f_gender' value='M'/><label for='m'>Male</label> <input checked='checked' type='radio' name='f_gender' value='F' id='f'/><label for='f'>Female</label></td></tr>";
				}else{
					str+= "<tr><td><label>Gender:</label></td> <td><input required checked='checked' type='radio' name='f_gender' value='M' id='m'/><label for='m'>Male</label> <input type='radio' name='f_gender' value='F' id='f'/><label for='f'>Female</label></td></tr>";
				}
	str+= "</table>"; 
	document.getElementById("editable").value = "1";
	document.getElementById("info_div").innerHTML = str;
}

function confirm_saving(){
	document.getElementById("confirm_password").value = "";
	$('#confirm_prov3').dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height: 250,
		width: 380,
		modal: true,
		buttons: {
			"Ok": function() {
				var password = document.getElementById("confirm_password3").value;
				xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$('#confirm_password3').focus();
	
	
}

function fingerprintConfirm(data){
	if(data==1){
		$("#confirm_prov3").dialog("close");
		document.forms["pl_update"].submit();
	}
	else{
		accessDenied();
	}
}

function accessDenied(){
	$( "#passwordDenied" ).dialog({
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

function save_financial(){
	var edit = document.getElementById("editable").value;
	var fname = document.getElementById("fa_fname");
	var lname = document.getElementById("fa_lname");
	/* var email = document.getElementById("fa_email"); */
	var contact = document.getElementById("fa_contact");
	var emp = 0;
	if(edit == 1){
		if(fname.value == null || fname.value == ""){
			fname.style.borderColor = "red";
			emp = 1;
		}
		if(lname.value == null || lname.value == ""){
			emp = 1;
			lname.style.borderColor = "red";
		}
		/* if(email.value == null || email.value == ""){			
			emp = 1;
			email.style.borderColor = "red";
		} */
		if(contact.value == null || contact.value == ""){
			emp = 1;
			contact.style.borderColor = "red";
		}
		if(emp == 0){
			lname.style.borderColor = "#8FA9BC";
			fname.style.borderColor = "#8FA9BC";
			/* email.style.borderColor = "#8FA9BC"; */
			contact.style.borderColor = "#8FA9BC";
			confirm_saving();
		}
	}
}
// --------- Manage Account functions --------------//
var newUname;
function confirm_saving2(){	
	document.getElementById("confirm_password4").value = "";
	$('#confirm_prov4').dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height: 250,
		width: 380,
		modal: true,
		buttons: {
			"Ok": function() {
				var user_role = $("#user_role").val();
				var password = document.getElementById("confirm_password4").value;
				//xhrGo("POST","FAManageAccount?confirmation_password="+password, fingerprintConfirm2, "plain");
				xhrGo("POST","GetPassUname?confirmation_password="+password+"&user_role="+user_role, fingerprintConfirm2, "plain");
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$('#confirm_password4').focus();
}
function update_account(){
	$('#manageAcc').dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height: 250,
		width: 380,
		modal: true,
		buttons: {
			"Change": function() {
				
				$( "#continueErr" ).dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:140,
					modal: true,
					buttons: {
						"Continue": function() {
							$( this ).dialog( "close" );
							var user_role = $("#user_role").val();
							var uname = $("#u_name").val();
							var pword = $("#u_pword").val();
							newUname = uname;
							if(uname != "" && pword != ""){
								xhrGo("GET","UserManageAccount?password="+pword+"&username="+uname+"&user_role="+user_role, result, "plain");
							}
							else{
								$( "#err" ).dialog({
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
						},
						"Cancel":function(){
							$(this).dialog("close");
						}
					}
				});
				
			},
			"Exit": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

var sURL = unescape(window.location.pathname);

function refresh()
{
    //  This version of the refresh function will cause a new
    //  entry in the visitor's history.  It is provided for
    //  those browsers that only support JavaScript 1.0.
    //
    window.location.href = sURL;
}

function result(data){
	//alert(data);
	$('#manageAcc').dialog("close");
	if(data == 1){
		/* document.getElementById("upSpan").innerHTML = "Provincial Link Account successfully changed!"; */
		$( "#upAlertYes" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
					$("#newUname").val(newUname);
				}
			}
		});
	}
	else{
		//document.getElementById("upSpan").innerHTML = "Updating Provincial Link Account failed!";
		$( "#upAlertNo" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:180,
			width:350,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
	
	
}

function fingerprintConfirm2(data){
	//alert(data);
	var x = eval('('+data+')');
	//alert("data:"+x.data[0].username);
	if(x.c==1){
		//alert("password confirmed.!!");
		$( '#confirm_prov4' ).dialog( "close" );
		$("#u_name").val(x.data[0].username);
		$("#u_pword").val(x.data[0].password);
		update_account(); 
	}
	else{
		accessDenied();
	}
}


function isEmpty(ID){
	var input = $("#"+ID).val();	
	if(ID == "u_name"){
		if(input == ""){
			$("#uname_span").html("<img src='<%= cPath %>/images/error.png' alt='img' />");
		}
		else{
			$("#uname_span").html("<img src='<%= cPath %>/images/check.png' alt='img' />");
		}
	}
	else{
		if(input == ""){
			$("#pword_span").html("<img src='<%= cPath %>/images/error.png' alt='img' />");
		}
		else{
			$("#pword_span").html("<img src='<%= cPath %>/images/check.png' alt='img' />");
		}
	}	
}
function OnEnterFPT(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password").value;
		xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm123, "plain");
		
	}
}
function OnEnterPhoto(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password2").value;
		xhrGo("POST","Password_Confirmation?confirmation_password="+password, photoConfirm123, "plain");
		
	}	
}
function OnEnterSave(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password3").value;
		xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
		
	}	
}
function OnEnterManage(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password4").value;
		var id = $("#uniqueID").val();
		var fname = $("#m_fname").val();
		var user_role = $("#user_role").val();
		var lname = $("#m_lname").val();
		var mname = $("#m_mun").val();
		xhrGo("POST","GetPassUname?confirmation_password="+password+"&user_role="+user_role+"&id="+id+"&mun_id="+mname, fingerprintConfirm2, "plain");

	}	
}
</script>
<style type="text/css">
	#pl_acc{ position: absolute;top: 0;right: 138px;margin-top: 2px;}
	#manageAcc img{width:25px;height: 15px;	}
	#dialog-confirm{display: none;}
	.button-dialog{	font-size: 16px;}
	#change-photo{margin-left: 50px;display: none;}
	.description{background-color: silver;position: relative;height: 30px;width: 800px;}
	#main{height: 500px;}
	.description h2{margin-left: 20px;color: #1E54DC;padding-top: 5px;}
	#pl_edit{position: absolute;top: 0;right: 70px;margin-top: 2px;}
	#pl_save{position: absolute;top: 0;right: 2px;margin-top: 2px;}
	#info_div{margin-top: 5px;}
	.im{border: 2px solid #000;	box-shadow: 0px 0px 20px #ccc;	-moz-box-shadow: 0px 0px 20px #ccc;	-webkit-box-shadow: 0px 0px 20px #ccc;}
	#btn_fingerprint{height: 28px;width: 155px;	font-size: 11px;}
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
<body onload="pageLoad(); updateClock(); setInterval('updateClock()', 1000 )">
<div id="page-wrap">
<div id="header" >
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
	<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
	<!--  <table width="900px;" >
		<tr>
			<td width="180px;" >
				<div id="imgDiv" ><img class="img1"  alt="leyte_sel" src="<%= cPath %>/image/Leyte_seal.png"/></div>
			</td>
			<td width="600px;" >
				
				<h3 style="color:white;" class="h3h" >
				
					Pantawid <br>
					Pamilyang <br> 
					Pilipino <br>
					Program <br>
					
				</h3>
			
			</td>
			<td>
				<img alt="" src="<%= cPath %>/image/legal3dswd.png" id="dswd-image"/>
			</td>
		</tr>
	</table>-->
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
<div id="main">
<c:choose>
	<c:when test="${user_role == 1 }">
		<h3>Provincial Link Profile</h3>
	</c:when>
	<c:when test="${user_role == 3 }">
		<h3>Financial Analyst Profile</h3>	
	</c:when>
	<c:when test="${user_role == 10 }">
		<h3>Administrator Profile</h3>	
	</c:when>
</c:choose>

<input type="hidden" id="user_role" value='<c:out value="${user_role}"></c:out>'/>
<input type="hidden" value="0" id="editable" />
 <table id="photo_tbl">			
					<tr>
						<td id="firstTD" align="center"> <!-- sorry sir.. we did a mortal sin.. whahahaha c john og argie nag ingon -->
							
							<c:choose>
								<c:when test="${photohead_exist}">
									<img class="im" alt="photo of head" src="<%= cPath %>/GetPhotoUser2?pID=<c:out value="${ID}" ></c:out>&user_role=${user_role}&sizeImage=dk4isddk" />
								</c:when>
								<c:otherwise>
									<img class="im" alt="photo of head" class="HF_photo" src="<%=cPath %>/image/head.jpg">
								</c:otherwise>
							</c:choose>	
						
						</td>
						<td width="200px" align="center" >
							<c:choose>
								<c:when test="${fingerprint_exist}">
									<img class="im" id="fingerprint"  alt="fingerprint"  src="<%=cPath %>/images/fingerprint1.jpg">
								</c:when>
								<c:otherwise>
									<img class="im" alt="No fingerprint" id="fingerprint" src="<%=cPath %>/images/questionmark.jpg">
								</c:otherwise>
							</c:choose>	
								
							
						</td>
					</tr>
					<tr >
					<c:choose>
						<c:when test="${admin}">
							<td align="center">
								<div id="radio">
									<input type="radio" id="radio1" name="radio" checked="checked" /><label for="radio1" id="btn_capture">Capture Photo</label>
									<input type="radio" id="radio2" name="radio" /><label for="radio2" id="btn_photo">Upload Photo</label>
								</div>
								<!-- <label><button id="change_photo" type="button" onclick="change_photo_head();">Change photo</button></label> -->
							</td>
							<td align="center">
								<c:choose>
									<c:when test="${fingerprint_exist}">
										<label><button id="btn_fingerprint">Change Fingerprint</button></label>
									</c:when>
									<c:otherwise>
										<button id="btn_fingerprint">Add fingerprint</button>
									</c:otherwise>
								</c:choose>	
								
							</td>
						</c:when>
					</c:choose>
					</tr>
				
				</table><br/>
				<form action="Update_user2" method="post" id="pl_update" name="pl_update" >
					<input type="hidden" name="u_user_role" value='<c:out value="${user_role}"></c:out>'/>
					<div class="description">
						<c:choose>
							<c:when test="${user_role == 1 }">
								<h2>Provincial Link Info</h2>
							</c:when>
							<c:when test="${user_role == 3 }">
								<h2>Financial Analyst Info</h2>
							</c:when>
							<c:when test="${user_role == 10 }">
								<h2>Administrator Profile</h2>	
							</c:when>
						</c:choose>
						<c:choose>
						<c:when test="${admin}">
							<button id="pl_acc" type="button" onclick="confirm_saving2();">Manage Account</button> 
							<button id="pl_edit" type="button" onclick="update_user2();">Edit</button> 
							<button id="pl_save" type="button" onclick="save_financial();">Save</button> 
						</c:when>
						</c:choose>
						<br/>
					</div>
					
					<c:forEach items="${user_list2}"  var = "list">
						<%-- <input type="hidden" id="fname" value="${list.pl_fname}" />
						<input type="hidden" id="lname" value="${list.pl_lname}"/> --%>
						<input type="hidden" id="f_fname" value="${list.pl_fname}" />
						<input type="hidden" id="f_lname" value="${list.pl_lname}"/>
						<input type="hidden" id="f_gen" value="${list.gender}"/>
						<input type="hidden" id="f_email" value="${list.user_email}"/>
						<input type="hidden" id="f_contact" value="${list.contact}"/>
						<div id="info_div" >
						<table>
							<tr>
								<td><label>First Name:</label></td> <td><input disabled="disabled" type="text"  value="${list.pl_fname}" class="input" size="30" autocomplete = "off"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><label>Contact No.:</label></td> <td><input disabled="disabled"  type="text"  value="${list.contact}" class="input" size="30" autocomplete = "off"/></td>
							</tr>
							<tr>
								<td><label>Last Name:</label></td> <td><input disabled="disabled" type="text" value="${list.pl_lname}" class="input" size="30" autocomplete = "off"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><label>Email Address:</label></td> <td><input disabled="disabled"  type="text"  value="${list.user_email}" class="input" size="30" autocomplete = "off"/></td>
							</tr>
							<tr>
								<td><label>Username:</label></td> <td><input disabled="disabled"  type="text" id="newUname"  value="${list.username}" class="input" size="30" autocomplete = "off"/><br/></td>
							</tr>
							<tr>
								 <c:choose>
									<c:when test="${list.gender == 'F' || list.gender == 'f'}">
										<td><label>Gender</label></td> <td> <input disabled="disabled" id="m" type="radio"/> <label for="m">Male</label> <input disabled="disabled" type="radio" checked="checked" id="f"/> <label for="f">Female</label><br/></td>
									</c:when>
									<c:otherwise>
										<td><label>Gender</label></td> <td><input disabled="disabled" type="radio" checked="checked" id="m"/> <label for="m">Male</label> <input disabled="disabled" type="radio" id="f"/> <label for="f">Female</label> <br/></td>
									</c:otherwise>
								</c:choose> 
							</tr>
						</table>
						</div>
					</c:forEach>
				</form>
				
</div>
</div> <!-- end of main content -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="change-photo" title="Change photo of head">
	<form action="<%=cPath %>/GetPhotoUser2" id="photo_change" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="companyjohn" id="dfgfd" value='<c:out value="${user_role}"></c:out>'/>
		<label>Photo:</label><label class="button-dialog"><input name="file" type="file" id="changePhoto" /></label><br/>
	</form>
</div>
<div id="passwordDenied" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access Denied..!!!</p>
</div>
<div class="hidden" id="confirm_prov" title="Confirmation" onkeypress="return OnEnterFPT(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Administrator for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>User Role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Administrator"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password" id="confirm_password" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div class="hidden" id="confirm_prov2" title="Confirmation" onkeypress="return OnEnterPhoto(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Administrator for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>User Role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Administrator"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password" id="confirm_password2" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div class="hidden" id="confirm_prov3" title="Confirmation" onkeypress="return OnEnterSave(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Administrator for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>User Role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Administrator"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password3" id="confirm_password3" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
</div>
<div class="hidden" id="confirm_prov4" title="Confirmation" onkeypress="return OnEnterManage(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Administrator for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>User Role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Adiministrator"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password4" id="confirm_password4" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
</div>
<div class="hidden" id="confirm_prov5" title="Confirmation" onkeypress="return OnEnterCapture(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Administrator for confirmation.
	</p>
	<table>
		<tr>
			<td><label>User Role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Administrator"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password5" id="confirm_password5" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
</div>

<div id="fpt_dialog" class="hidden">
	<p><label>Scan your fingerprint 4 times.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cPath %>/images/loading.gif" /></p>

</div>

<div id="FailedToEnroll" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to enroll the finger. Please try again.</p>
</div>
<div id="BadImage" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Bad image quality. Please try again.</p>
</div>
<div id="startCaptureFailed" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to start capture. The fingerprint reader is used by another application.</p>
</div>

<!-- Manage Account Dialogs  -->

<div id="manageAcc" title="Manage Account" >
	
	 <table>
						
		<tr>
			<td><label>Username:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input onkeyup="isEmpty(this.id)" type="text" id="u_name" class="input" />&nbsp;<span class="error_style" id="uname_span" ><img src="<%= cPath %>/images/check.png" alt="img" /> </span></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input onkeyup="isEmpty(this.id)" name="password" id="u_pword" class="input" type="password" value="" />&nbsp;<span class="error_style" id="pword_span" ><img src="<%= cPath %>/images/check.png" alt="img" /> </span>
			</td>
		</tr>
	</table>

</div>
<div id="err" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Complete all the fields.</p>
</div>
<div id="continueErr" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Updating User Account is very important. Are you willing to take the risk?</p>
</div>
<div id="upAlertYes" title="4Ps Message" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"  ></span>User Account successfully changed!</p>
</div>
<div id="upAlertNo" title="4Ps Message" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 60px 0;" ></span>Updating User Account failed!</p>
	<br/><p>Possible solutions:</p>
	<ul>
		<li>Username must be unique.</li>
		<li>Password length must be between 2 and 18.</li>
	</ul>
</div>
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
				<form action="<%=cPath %>/CaptureUserImg2" name="myuploadImageform" method="post" >
					<input type="hidden" name="CI_user_role" value="<c:out value="${user_role}"></c:out>"/>
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
</div> <!-- end of page wrap -->

</body>
</html>