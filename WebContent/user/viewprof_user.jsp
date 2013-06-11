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
<script type="text/javascript" src="<%= cPath %>/js/add_benificiary.js"></script>
<script type="text/javascript" src="<%=cPath %>/capture-image/captureImg.js"></script>
<link type="text/css" rel="stylesheet" href="<%=cPath %>/capture-image/captureImg.css">
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript">
var validNavigation = false;
$(document).ready(function(){
	$(window).unload(function(){});
	$(document).ready(function(){
		$('#capture_btn').button({
			icons : {primary : "ui-icon-image" }
		});
	});
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
		icons: {primary: "ui-icon-locked"}
	});
	$(function() {
		$( "#radio" ).buttonset();
	});
	$("#passwordDenied").hide();
	$("#confirm_prov").hide();
	$("#confirm_prov2").hide();
	$("#confirm_prov3").hide();
	$("#confirm_prov4").hide();
	$("#confirm_prov5").hide();
	$("#fpt_dialog").hide();
	$("#FailedToEnroll").hide();
	$("#BadImage").hide();
	$("#startCaptureFailed").hide();
	$("#image-pop-up").hide();
	
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
function fingerprintConfirm123(data){
	if(data==1){
		$("#confirm_prov").dialog("close");
		add_fpt();
		
	}
	else{
		accessDenied();
	}
}
function photoConfirm123(data){
	if(data==1){
		$("#confirm_prov2").dialog("close");
		change_photo_head();
	}
	else{
		accessDenied();
	}
}

function add_fpt(){
	var fname = $("#h_fname").val();
	var lname = $("#h_lname").val();
	var id = $("#uniqueID").val();
	var user_role = $("#user_role").val();
	var mun = $("#h_mun").val();
	var municipality = $("#municipality").val();
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
	xhrGo("POST","UserFingerprint?id="+id+"&user_role="+user_role+"&fname="+fname+"&municipality="+municipality+"&mun="+mun+"&lname="+lname, showResult, "plain");
}

/* ASK CONFIRMATION TO THE USER TO ABANDON THE SHIP.. HAHA... */
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
function showResult(data){
	var x=eval('('+data+')');
	if(x.failedToEnroll == 1){
		$('#fpt_dialog').dialog('close');
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
				var fname = $("#h_fname").val();
				var lname = $("#h_lname").val();
				var mun = $("#h_mun").val();
				var id = $("#uniqueID").val();
				var user_role = $("#user_role").val();
				document.location.href = "<%=cPath %>/Add_user?id="+id+"&mun="+mun+"&user_role="+user_role;
			}
		}
		
	}
	
}

function unableToEdit(){
	$("#unableToEdit").dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:140,
		modal: true,
		zindex:3999,
		buttons: {
			"OK": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function change_photo_head(){
	$( "#change-photo" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:180,
		width: 420,
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

function update(){
	$("#mun_id").removeAttr("disabled");
 	var fname = document.getElementById("m_fname").value;
	var lname = document.getElementById("m_lname").value;
	var gender = document.getElementById("m_gender").value;
	var email = document.getElementById("m_email").value;
	var contact = document.getElementById("m_contact").value;
	
	var str = "";
	
	str += "<table> "+
				"<tr><td><label>First Name:</label></td> <td><input required type='text' id='mun_fname' maxlength='50' name='m_fname'  value='"+fname+"' class='input' size='30' autocomplete = 'off'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td/>"+
					"<td><label>Contact No.:</label></td> <td><input required  type='text' id='mun_contact' maxlength='15' onkeypress='return numbersonly(event,false);' name='m_contact' value='"+contact+"' class='input' size='30' autocomplete = 'off'/></td></tr>"+
				"<tr><td><label>Last Name:</label></td> <td><input required type='text' id='mun_lname' maxlength='50' name='m_lname' value='"+lname+"' class='input' size='30' autocomplete = 'off'/><br/><td/>"+
					"<td><label>Email Address:</label></td> <td><input required type='text' id='mun_email' maxlength='30' name='m_email' value='"+email+"' class='input' size='30' autocomplete = 'off'/></td></tr>";
			
				if(gender =="f" || gender=="F"){
					str+= "<tr><td><label>Gender:</label></td> <td><input required id='m' type='radio' name='m_gender' value='M'/><label for='m'>&nbsp;Male</label> <input checked='checked' id='f' type='radio' name='m_gender' value='F'/><label for='f'>&nbsp;Female</label></td></tr>";
				}else{
					str+= "<tr><td><label>Gender:</label></td> <td><input id='m' checked='checked' type='radio' name='m_gender' value='M'/> <label for='m'>&nbsp;Male</label> <input id='f' type='radio' name='m_gender' value='F'/><label for='f'>&nbsp;Female</label></td></tr>";
				}
	str+= "</table>"; 
	document.getElementById("editable").value = "1";
	document.getElementById("info_div").innerHTML = str; 
}

function confirm_saving(){
	
	document.getElementById("confirm_password3").value = "";
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

function save(){
	var edit = document.getElementById("editable").value;
	var fname = document.getElementById("mun_fname");
	var lname = document.getElementById("mun_lname");
	var email = document.getElementById("mun_email");
	var contact = document.getElementById("mun_contact");
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
		}
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email.value)) {
			emp = 1;
			email.style.borderColor = "red";
			email.focus;
			return false;
		}
		*/
		if(contact.value == null || contact.value == ""){
			emp = 1;
			contact.style.borderColor = "red";
		}

		
		if(emp == 0){
			lname.style.borderColor = "#8FA9BC";
			fname.style.borderColor = "#8FA9BC";
			email.style.borderColor = "#8FA9BC";
			contact.style.borderColor = "#8FA9BC";
			confirm_saving();
		}
		
	}
	
	
}

//------------------------ Manage Account --------------------------------------------// 
var newUname;

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
					show: "blind",
					hide: "blind",
					resizable: false,
					height:140,
					modal: true,
					buttons: {
						"Continue": function() {
							$( this ).dialog( "close" );
							var id = $("#uniqueID").val();
							var user_role = $("#user_role").val();
							var uname = $("#u_name").val();
							var pword = $("#u_pword").val();
							var fname = $("#m_fname").val();
							var lname = $("#m_lname").val();
							var mname = $("#m_mun").val();
							var old_user = old_uname;
							var old_pass = old_pword;
							newUname = uname;
							if(uname != "" && pword != ""){
								xhrGo("GET","UserManageAccount?password="+pword+"&username="+uname+"&user_role="+user_role+"&fname="+fname+"&lname="+lname+"&mun_id="+mname+"&id="+id, result, "plain");
							}
							else{
								$( "#err" ).dialog({
									show: "blind",
									hide: "blind",
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


function result(data){
	$('#manageAcc').dialog("close");
	if(data == 1){
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
				var password = document.getElementById("confirm_password4").value;
				var id = $("#uniqueID").val();
				var fname = $("#m_fname").val();
				var user_role = $("#user_role").val();
				var lname = $("#m_lname").val();
				var mname = $("#m_mun").val();
				xhrGo("POST","GetPassUname?confirmation_password="+password+"&user_role="+user_role+"&id="+id+"&mun_id="+mname, fingerprintConfirm2, "plain");
				//$("#confirm_prov4").dialog("close");
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$('#confirm_password4').focus();
	
	
}

function fingerprintConfirm2(data){
	var x = eval('('+data+')');
	if(x.c==1){
		old_uname = x.data[0].username;
		old_pword = x.data[0].password;
		$("#u_name").val(x.data[0].username);
		$("#u_pword").val(x.data[0].password);
		$("#confirm_prov4").dialog("close");
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
	#pl_acc{
		position: absolute;
		top: 0;
		right: 138px;
		margin-top: 2px;
	}
	#manageAcc img{
		width:25px;
		height: 15px;
	}
	#dialog-confirm{
		display: none;
		
	}
	.button-dialog{
		font-size: 16px;
	}
	#change-photo{
		margin-left: 50px;
		display: none;
		
	}
	.description{
		background-color: silver;
		position: relative;
		height: 30px;
		width: 800px;
	}
	#main{
		height: 500px;
	}
	.description h2{
		margin-left: 20px;
		color: #1E54DC;
		padding-top: 5px;
	}
	#pl_edit{
		position: absolute;
		top: 0;
		right: 70px;
		margin-top: 2px;
	}
	
	#pl_save{
		position: absolute;
		top: 0;
		right: 2px;
		margin-top: 2px;
	}
	
	#info_div{
		margin-top: 5px;
	}
	.im{
		border: 2px solid #000;
		box-shadow: 0px 0px 20px #ccc;
		-moz-box-shadow: 0px 0px 20px #ccc;
		-webkit-box-shadow: 0px 0px 20px #ccc;
	}
	#btn_fingerprint{height: 28px;width: 150px;	font-size: 11px;}
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
<body onload="pageLoad(); updateClock(); setInterval('updateClock()', 1000 )" >

<div id="page-wrap">
<div id="header" >
<div id="logo" >
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
<div id="main">
<c:choose>
	<c:when test="${user_role==2}">
		<h3  >Municipal Link Account</h3>
	</c:when>
		<c:when test="${user_role==4}">
		<h3  >BookKeeper Account</h3>
	</c:when>
	<c:when test="${user_role==6}">
		<h3  >Grievance Officer Account</h3>
	</c:when>
	<c:when test="${user_role==7}">
		<h3  >Social Worker Account</h3>
	</c:when>
	<c:when test="${user_role == 5 }">
		<h3>Verifier Account</h3>
	</c:when>
</c:choose>


<input type="hidden" name="mun" value="${m}"/>
<input type="hidden" id="user_role" value="${user_role }"/>
<input type="hidden" value="0" id="editable" />
 <table id="photo_tbl" >			
					<tr>
						<td id="firstTD" align="center"> <!-- sorry sir.. we did a mortal sin.. hehe-->
							
							<c:choose>
								<c:when test="${photohead_exist}">
									<img class="im" alt="photo of head" src="<%= cPath %>/GetPhotoUser?ID=<c:out value="${ID}" ></c:out>&user_role=${user_role}&sizeImage=dk4isddk" />
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
					
							<td align="center">
								<div id="radio">
									<input type="radio" id="radio1" name="radio" checked="checked" /><label for="radio1" id="btn_capture">Capture Photo</label>
									<input type="radio" id="radio2" name="radio" /><label for="radio2" id="btn_photo" >Upload Photo</label>
								</div>
							</td>
							<td align="center">
								<c:choose>
									<c:when test="${fingerprint_exist}">
										<button  class="fpt_btn" id="btn_fingerprint" >Change fingerprint</button>
									</c:when>
									<c:otherwise>
										<button  class="fpt_btn" id="btn_fingerprint">Add fingerprint</button>
									</c:otherwise>
								</c:choose>	
								
							</td>
					</tr>
				</table><br/>
				<form action="Update_user" method="post" id="pl_update" name="pl_update" >
				<input type="hidden" name="uniqueID" id="uniqueID" value="${ID}"/>
				<input type="hidden" name="update_user_role" value="${user_role }"/>
				 <c:choose><c:when test="${user_role == 5 }"> <input type="hidden" id="mun_id" name="mun_id" value="64001"/></c:when></c:choose>
					<div class="description">
						<c:choose>
							<c:when test="${user_role==2}">
								<h2  >Municipal Link Info</h2>
							</c:when>
							<c:when test="${user_role==4}">
								<h2  >BookKeeper Info</h2>
							</c:when>
							<c:when test="${user_role==6}">
								<h2  >Grievance Officer Info</h2>
							</c:when>
							<c:when test="${user_role==7}">
								<h2>Social Worker Info</h2>
							</c:when>
							<c:when test="${user_role == 5 }">
								<h2>Verifier Info</h2>
							</c:when>
						</c:choose>
						<button id="pl_acc" type="button" onclick="confirm_saving2();">Manage Account</button> 
						<button id="pl_edit" type="button" onclick="update();">Edit</button> 
						<button id="pl_save" type="button" onclick="save();">Save</button> 
					</div>
					<br/>
					<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><label for="mun_id" >Municipality:</label>
					
						<select disabled="disabled" id="mun_id" name="mun_id" class = "input" style="width: 210px" >
							<c:forEach items="${municipal_list}" var="ml" >
							   <c:choose>	
								<c:when test="${m == ml.mun_id}">
									<option selected="selected" value="${ml.mun_id}" > <c:out value="${ml.municipality}"></c:out> </option>
								</c:when>
								<c:otherwise>
									<option value="${ml.mun_id}" > <c:out value="${ml.municipality}"></c:out> </option>
								</c:otherwise>
							   </c:choose>
							  </c:forEach>
						</select></c:when></c:choose>
					<c:forEach items="${user_list}"  var = "m">
						<input type="hidden" id="m_fname" name="real_fname" value="${m.pl_fname}" />
						<input type="hidden" id="m_lname" name="real_lname" value="${m.pl_lname}"/>						
						<input type="hidden" id="m_user" name="real_username" value="${m.username}"/>
						<input type="hidden" id="m_gender" name="real_gender" value="${m.gender}"/>
						<input type="hidden" id="m_email" name="real_email" value="${m.user_email}"/>
						<input type="hidden" id="m_contact" name="real_contact" value="${m.contact}"/>
						<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><input type="hidden" id="m_mun" name="real_mun_name" value="${m.mun_id}"/></c:when></c:choose>
						<c:choose><c:when test="${user_role==5 }"><input type="hidden" id="m_mun" name="real_mun_name" value="64001"/></c:when></c:choose>
						<div id="info_div" >
						<table>
							<tr>
								<td><label>First Name:</label></td> <td><input disabled="disabled" type="text" value="${m.pl_fname}" class="input" size="30" autocomplete = "off"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
								<td><label>Contact No.:</label></td><td><input onkeypress="return numbersonly(event,false);" disabled="disabled"  type="text"   value="${m.contact}" class="input" size="30" autocomplete = "off"/></td>
							</tr>
							<tr>
								<td><label>Last Name:</label></td> <td><input disabled="disabled" type="text" value="${m.pl_lname}" class="input" size="30" autocomplete = "off"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><label>Email Address:</label></td> <td><input disabled="disabled"  type="text"  value="${m.user_email}" class="input" size="30" autocomplete = "off"/></td>
							</tr>
							<tr>
								<td><label>Username:</label></td> <td><input disabled="disabled" id="newUname"  type="text"  value="${m.username}" class="input" size="30" autocomplete = "off"/><br/></td>
							</tr>
							<tr>
								 <c:choose>
									<c:when test="${m.gender == 'F' || m.gender == 'f'}">
										<td><label>Gender</label></td> <td> <input disabled="disabled" type="radio"  id="m" /> <label for="m" >Male</label> <input disabled="disabled" type="radio" checked="checked" id="f" /> <label for="f" >Female</label><br/></td>
									</c:when>
									<c:otherwise>
										<td><label>Gender</label></td> <td><input disabled="disabled" type="radio" id="m" checked="checked"/> <label for="m" >Male</label> <input id="f" disabled="disabled" type="radio"/> <label for="f">Female</label> <br/></td>
									</c:otherwise>
								</c:choose> 
							</tr>
						</table>
<%-- 							<label>First Name:</label><input disabled="disabled" type="text" name="pl_fname" id="pl_fname" value="${m.pl_fname}" class="input" size="30" autocomplete = "off"/><br/>
							<label>Last Name:</label><input disabled="disabled"  type="text" name="pl_lname" id="pl_lname" value="${m.pl_lname}" class="input" size="30" autocomplete = "off"/> --%>
						</div>
					</c:forEach>
				</form>
				<input type="hidden" name="municipality" id="municipality" value="${municipality}"/>
</div>
</div> <!-- end of main content -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="change-photo" title="Change photo of head">
	<form action="<%=cPath %>/GetPhotoUser" id="photo_change" method="post"  enctype="multipart/form-data">
		
		<input type="hidden" name="fname" id="h_fname" value="<c:out value="${f}"></c:out>"/>
		<input type="hidden" name="lname" id = "h_lname" value="<c:out value="${l}"></c:out>"/>
		<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><input type="hidden" name="mun" id = "h_mun" value="<c:out value="${m}"></c:out>"/></c:when></c:choose>
		<c:choose><c:when test="${user_role == 5 }"><input type="hidden" name="mun" id = "h_mun" value="64001"/></c:when></c:choose>
		<input type="hidden" name="p_uniqueID" id="p_uniqueID" value="<c:out value="${ID}"></c:out>"/>
		<input type="hidden" name="p_user_role" value="<c:out value="${user_role}"></c:out>"/>
	
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
			<td><input type="text" class="input" readonly="readonly" value="Administrator"/></tr>
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
<div id="unableToEdit" title="4Ps Message" style = "display: none;" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to update due to duplicate data.</p>
</div>
<c:if test="${unableToEdit}">
	<script type="text/javascript">
		unableToEdit();
	</script>
</c:if>
<div id="FailedToEnroll" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to enroll the finger. Please try again.</p>
</div>
<div id="BadImage" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Bad image quality. Please try again.</p>
</div>
<div id="startCaptureFailed" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to start capture. The fingerprint reader is used by another application.</p>
</div>

 <!--  dialogs for Manage Account   -->


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
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"  ></span>User Successfully changed!</p>
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
				<form action="<%=cPath %>/CaptureUserImg" name="myuploadImageform" method="post" >
					<input type="hidden" name="CI_id" value="${ID}"/>
					<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><input type="hidden" name="CI_mun" value="${m}"/></c:when></c:choose>
					<c:choose><c:when test="${user_role == 5 }"><input type="hidden" name="CI_mun" value="64001"/></c:when> </c:choose>
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