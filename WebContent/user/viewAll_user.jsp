<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ page import="java.util.*"%>
	<%@ page import="java.text.*"%> 
    <%String cpath = request.getContextPath(); %>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program</title>
	<script type="text/javascript" src="<%= cpath %>/js/ajax.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/ajax3.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/ajaxx.js"></script>
	<link rel="shotcut icon" href="<%= cpath %>/image/home.png" type="image/x-icon" />
	<link rel="stylesheet" href="<%= cpath %>/css/add_transactions_CSS.css"/>
	<link rel="stylesheet" href="<%= cpath %>/css/home.css"/>
	<link rel="stylesheet" type="text/css" href="<%= cpath %>/pro_drop_1/pro_drop_1.css" />
	<link rel="stylesheet" href="<%=cpath %>/css/cupertino/jquery-ui-1.8.16.custom.css">
	<link rel="stylesheet" href="<%= cpath %>/development-bundle/demos/demos.css">
	<script src="<%=cpath %>/development-bundle/jquery-1.6.2.js"></script>
	<script src="<%=cpath %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script src="<%=cpath %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script src="<%=cpath %>/development-bundle/ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/pages.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/validateNumber.js"></script>
	<script type="text/javascript" src="<%=cpath %>/js/displayTimeDate.js"></script> 
	<link rel="stylesheet" type="text/css" href="<%=cpath %>/css/displayTimeDate.css"/>
<style type="text/css">
 	#pop-up-td{
		margin-left: 180px;
	} 
	#add_dialog{
		display: none;
	}
</style>
<script type="text/javascript">
	$(function(){

		$("#btn_search").button({
			icons : {primary: "ui-icon-search"}
		});
		$("#btn_user").button({
			icons : {primary: "ui-icon-circle-plus"}
		});
		$("#pop-up-add").button({
			icons : {primary: "ui-icon-circle-check"}
		});
		$("#pop-up-reset").button({
			icons : {primary: "ui-icon-refresh"}
		});
	});
	function msg_checkedTrue(){
		$("#msg_c").dialog({
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
	function duplicateUser(){
		$('#duplicateUser').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:300,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	function add_dialog(){
		$("#add_dialog").dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 440,
			width:450,
			modal: true,
			title:"Add User Account"
		});
	}

var validNavigation = false;
$(document).ready(function(){
	
	$('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
    var options = {
      currPage : 1, 
      ignoreRows : $('tbody1 tr:odd', $('#display')),
      optionsForRows : [10],
      rowsPerPage : 10,
      firstArrow : (new Image()).src="<%= cpath %>/images/firstBlue.gif",
      prevArrow : (new Image()).src="<%= cpath %>/images/prevBlue.gif",
      lastArrow : (new Image()).src="<%= cpath %>/images/lastBlue.gif",
      nextArrow : (new Image()).src="<%= cpath %>/images/nextBlue.gif",
      topNav : false
    };
    $('#display').tablePagination(options);
	
	$(window).unload(function(){});
	$("#pl_edit").button({
		icons : {primary : "ui-icon-pencil" }
	});
	$("#pl_save").button({
		icons : {primary : "ui-icon-circle-check" }
	});
	
	$("#passwordDenied").hide();
	$("#confirm_prov").hide();
	$("#fpt_dialog").hide();
	$("#FailedToEnroll").hide();
	$("#BadImage").hide();
	$(".hidden").hide();
	$("#startCaptureFailed").hide();
	
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
	
});
function fingerprintConfirm123(data){
	alert("data javascript:"+data);
	if(data==1){
		
		//alert("password confirmed.!!");
		$( '#confirm_prov' ).dialog( "close" );
		add_fpt();
	}
	else{
		accessDenied();
	}
}

function searchUser(){
	
	var m = document.getElementById("mun_link").value;
	var user_role = document.getElementById("hiddenUser_role").value;
	xhrGo("POST", "<%= cpath %>/ViewAllUser?mun_id="+m+"&user_role="+user_role, viewUser,"plain");
	
}

function viewUser(data){
	var x = eval('('+data+')');
	var str = "";
	
	if(x.data == "" || x.data == "{}" ){
		getID("tbody").innerHTML = "<tR><th align='center'  colspan='5' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
		//fadeOutLoading();
	}
	else{
		var count = 1;
		for(var i=0;i<x.data.length;i++){
			
			str += "<tr class='tbl_r'>" +
						"<td id='id_cell'>" +count+ "</td>" +
						"<td id='id_cell'>" +x.data[i].pl_fname+ "</td>" +
						"<td id='id_cell'>" +x.data[i].pl_lname+ "</td>" +
						"<td id='id_cell'>" +x.data[i].municipality+ "</td>"+
						
						"<td class='action' id='id_cell9'><a href='<%=cpath %>/Add_user?user_role="+x.data[i].user_role+"&id="+x.data[i].id+"&mun="+x.data[i].mun_id+"'>View Profile</a></td>";
			str += "</tr>";		
			count++;
		}
		
		getID("tbody").innerHTML = str;
		
		 $('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
		    var options = {
		      currPage : 1, 
		      ignoreRows : $('tbody1 tr:odd', $('#display')),
		      optionsForRows : [10],
		      rowsPerPage : 10,
		      firstArrow : (new Image()).src="<%= cpath %>/images/firstBlue.gif",
		      prevArrow : (new Image()).src="<%= cpath %>/images/prevBlue.gif",
		      lastArrow : (new Image()).src="<%= cpath %>/images/lastBlue.gif",
		      nextArrow : (new Image()).src="<%= cpath %>/images/nextBlue.gif",
		      topNav : false
		    };
		    $('#display').tablePagination(options);
	}
	
}

function add_fpt(){
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
	xhrGo("POST","PLFingerprint", showResult, "plain");
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
				document.location.href = "<%=cpath %>/AccountSettings";
			}
		}
		
	}
	
}




function getID(ID){
	return document.getElementById(ID);
}

function update_provlink(){
	
	var fname = document.getElementById("fname").value;
	var lname = document.getElementById("lname").value;
	
	var str = "";
	
	str += "<label>First Name:</label><input type='text' name='pl_fname' id='pl_fname' value='"+fname+"' class='input' size='30' autocomplete = 'off'/><br/>"+
			"<label>Last Name:</label><input type='text' name='pl_lname' id='pl_lname' value='"+lname+"' class='input' size='30' autocomplete = 'off'/>";
	document.getElementById("editable").value = "1";
	document.getElementById("info_div").innerHTML = str;
}

function confirm_saving(){
	
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
				xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$('#confirm_password').focus();
	
	
}

function fingerprintConfirm(data){
	if(data==1){
		//alert("password confirmed.!!");
		//$("#add_dialog").dialog( "close" );
		$( '#confirm_prov' ).dialog( "close" );
		add_dialog();
		//document.forms["add_User"].submit();
	}
	else{
		accessDenied();
	}
}
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

function validation(){
	var fname = document.getElementById("fname");
	var lname = document.getElementById("lname");
	var user = document.getElementById("uname");
	var pword = document.getElementById("pword");
	var retype_pword = document.getElementById("rtpword");
	var email = document.getElementById("email");
	var contact = document.getElementById("contact");
	var mun = document.getElementById("mun");
		

		if(fname.value == null || fname.value == ""){
			fname.focus();
			fname.style.borderColor = "red";
			alert("Please enter firstname.");
			return false;
		}
		else{
			fname.style.borderColor = "#8FA9BC";
		}
		if(lname.value == null || lname.value == ""){
			lname.focus();
			lname.style.borderColor = "red";
			alert("Please enter lastname.");
			return false;
		}
		else{
			lname.style.borderColor = "#8FA9BC";
		}
		if(user.value == null || user.value == ""){
			user.focus();
			user.style.borderColor = "red";
			alert("Please enter username.");
			return false;
		}
		else{
			user.style.borderColor = "#8FA9BC";
		}
		if(pword.value == null || pword.value == ""){
			pword.focus();
			pword.style.borderColor = "red";
			alert("Please enter your password.");
			return false;
		}
		else{
			pword.style.borderColor = "#8FA9BC";
		}
		if(retype_pword.value != pword.value){
			pword.style.borderColor = "red";
			retype_pword.style.borderColor = "red";
			$("#errorpassword").dialog({
				modal: true,
				show: "fade",
				hide: "fade",
				resizable: false,
				height:140,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			return false;
		}
		else{
			pword.style.borderColor = "#8FA9BC";
			retype_pword.style.borderColor = "#8FA9BC";
		}
		//for radio buttons
		function checkRadio(frmName, rbGroupName){
			var radios = document[frmName].elements[rbGroupName]; 
			for (var i=0; i <radios.length; i++) { 
				if (radios[i].checked) { 
				   return true;
				}
			}
			return false;
		}
		if(!checkRadio('add_User','gender')){ //validation for gender
			alert("Please select gender");
			return false;
		}
		if(mun.value == null || mun.value == ""){
			//mun.focus();
			mun.style.borderColor = "red";
			alert("Please select municipal.");
			return false;
		}
		else{
			mun.style.borderColor = "#8FA9BC";
		}
		if(ctrl==3){
			confirm_saving();
		}
		return true;
		/* if(emp == 1){
			lname.style.borderColor = "#8FA9BC";
			fname.style.borderColor = "#8FA9BC";
			//confirm_saving();
			return true
		}else{
			return false;
		}
	 */
}
$(function(){
	$("#tablePagination_firstPage").button({
		icons: {primary: "ui-icon-seek-first"}
	});
	$("#tablePagination_prevPage").button({
		icons: {primary: "ui-icon-seek-prev"}
	});
	$("#tablePagination_nextPage").button({
		icons: {secondary: "ui-icon-seek-next"}
	});
	$("#tablePagination_lastPage").button({
		icons: {secondary: "ui-icon-seek-end"}
	});
});
function OnEnter(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password").value;
		xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
	}	
}
</script>
<style type="text/css">
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
	.hidden{
		display: none;
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
	#filter-tbl{
		margin: 0 auto;
	}
	.tbl_r td{
		padding-left: 5px;
		padding-right: 8px;
		font-size: 16px;
	}
	.tbl_r:HOVER {
		background-color: #ddd;
	}
 #tablePagination { 
            font-size: 10px; 
            padding: 0px 5px; 
            height: 40px;
            width:800px;
            border:  1px solid black; ;
            border-collapse: collapse;
            border-top: none;
            padding-top: 10px;
            text-align: center;
}
          
 #tablePagination_nextPage,#tablePagination_firstPage,#tablePagination_prevPage{
 	padding-left: 5px;
 	margin-left: 5px;
 }
#tablePagination_lastPage{
	padding-left: 5px;
	margin-left: 10px;
}
#tablePagination_prevPage{
	margin-right: 5px;
}
#add_mun_tbl{
	margin: 0 auto;
}
#socialLabel{
	font-family: sans-serif;
	margin: 0 auto;
	width: 400px;
}
	
 #display thead{
	color:black;
	background-color: Lightblue;
}
	#display{
		padding-left:5px;
		margin-top:12px;
		border:  1px solid black; ;
		border-collapse: collapse;
		font-size: 13px;
		width: 812px;
	}
	#data_tbl{
		margin: 20px auto;
		width: 700px;
		font-size: 16px;
		border-collapse: collapse;
		border:1px solid black;
	}
	
	.tbl_tr{
		background-color: #ccc;
	}
	#data_tbl tr td{
		padding:0px auto;
	}
	
	#act{
		text-align: center;
	}

	a{
		text-decoration: none;
	}
	
	#main_div{
		width: 850px;
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
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cpath %>/logos/headers-mag.jpg">
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

<div id="main_div">
<div id="socialLabel">

<c:choose>
	<c:when test="${user_role==2}">
		<h3  >Municipal Link Users List</h3>
	</c:when>
		<c:when test="${user_role==4}">
		<h3  >BookKeeper Users List</h3>
	</c:when>
	<c:when test="${user_role==6}">
		<h3  >Grievance Officer Users List</h3>
	</c:when>
	<c:when test="${user_role==7}">
		<h3  >Social Worker Users List</h3>
	</c:when>
	<c:when test="${user_role == 5 }">
		<h3>Verifier Users List</h3>
	</c:when>
</c:choose>


</div><br/>
		<table id="filter-tbl">
			<tr><c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }">
				<td>
				<select id="mun_link" name="mun_link" class="input">
					<option selected="selected">Select Municipality</option>
					<c:forEach items="${municipal_list }" var="list">
						<c:if test="${municipality==list.mun_id}">
								<option value="${list.mun_id}" selected="selected"><c:out value="${list.municipality}"></c:out></option>
							</c:if>
							<c:if test="${municipality!=list.mun_id}">
								<option value="${list.mun_id}" ><c:out value="${list.municipality}"></c:out></option>
							</c:if>
					</c:forEach>
				</select>
				</td >
				<td width="200px" > <button onclick="searchUser();" id="btn_search">Search</button>&nbsp;
				</c:when></c:choose>
					<c:choose>
						<c:when test="${provlink}">
							<button id="btn_user" onclick="confirm_saving();">Add User</button>
						</c:when>
					</c:choose>
					
				</td> 
			</tr>
			</table>
		<table border="1" id="display" class="paginated display sortable"> 
		<%int row = 1; %>
		 <thead>
				<tr >
					<th>#</th>
					<th align="center"> First name </th>
					<th align="center"> Last name </th>
					<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><th align="center"> Municipal </th></c:when></c:choose>
					<th align="center"> Action </th>
				</tr>
			</thead>
			<tbody  id="tbody" >
				<c:forEach items="${user_list}" var="list123">
				<tr class="tbl_r">
					<td> <%=row++ %> </td>
					<td><c:out value="${list123.pl_fname}"></c:out></td>
					<td><c:out value="${list123.pl_lname}"></c:out></td>
					<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><td><c:out value="${list123.municipality}"></c:out></td></c:when></c:choose>
					<td id="act" ><a href="<%=cpath %>/Add_user?user_role=${user_role}&id=${list123.id}&mun=${list123.mun_id}&ODFOIkdjfoeOADK48ire84jFGjaie8oisdYsakjs23">View Profile</a></td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot id="tfoot" >
			
			</tfoot>
		</table>
	</div>
	
	
	<!-- dialog -->
	<div id="add_dialog">
	<form action="<%=cpath %>/Add_user" name="add_User" onsubmit="return validation()" method="post" >
		<br/>
		<table id="add_mun_tbl">
			<tr><td> <label>First name:</label></td><td><input required class="input" type="text" autocomplete = "off" name="fname" id="fname" size="25"/> </td></tr>
			<tr><td> <label>Last name:</label></td><td> <input required class="input" type="text" autocomplete = "off" name="lname" id="lname" size="25"/></td></tr>
			<tr><td> <label>User name:</label></td><td><input required class="input" type="text" autocomplete = "off" name="uname" id="uname" size="25"/></td></tr>
			<tr><td> <label>Password:</label></td><td><input required class="input" type="password" autocomplete = "off" name="pword" id="pword" size="25"/></td></tr>
			<tr><td> <label>Retype password:</label></td><td><input required class="input" type="password" autocomplete = "off" id="rtpword"   size="25"/></td></tr>
			<tr><td> <label>Gender </label></td><td> <input required type="radio" id="md" name="gender" value="M"/><label for="md">&nbsp;Male </label >  <input type="radio" id="fd" name="gender" value="F"/><label for="fd">&nbsp;Female</label></td></tr>
			<tr><td> <label>Email: </label></td><td><input placeholder="-Optional-" class="input" type="text" autocomplete = "off" name="email" id="email"  size="25"/></td></tr>
			<tr><td> <label>Contact: </label></td><td><input required class="input" type="text" autocomplete = "off" name="contact" id="contact" onkeypress="return numbersonly(event,false)"  size="25"/></td></tr>
			<c:choose><c:when test="${user_role==2||user_role==4||user_role==6||user_role==7 }"><tr><td> <label>Municipality :</label></td><td align="right"><select required class="input" name="mun" style="width: 232px;" id="mun" >
										<option selected="selected" value="">Select Municipality</option>
										<c:forEach items="${municipal_list }" var="list">
											<c:if test="${msg_mun==list.mun_id}">
													<option value="${list.mun_id}" selected="selected"><c:out value="${list.municipality}"></c:out></option>
												</c:if>
												<c:if test="${msg_mun!=list.mun_id}">
													<option value="${list.mun_id}" ><c:out value="${list.municipality}"></c:out></option>
												</c:if>
										</c:forEach>
									</select>
			 </td>  </tr></c:when></c:choose>
			 <c:choose><c:when test="${user_role == 5 }"> <input type="hidden" name="mun" value="64001"/></c:when></c:choose>
			<tr><td></td><td id="pop-up-td"> <br/> <button id="pop-up-add"  type="submit">Add</button>&nbsp;<button id="pop-up-reset" type="reset">Reset</button> </td></tr> 
		</table>
		<input type="hidden" name="hiddenUser_role" id="hiddenUser_role" value="${user_role}"/>
	</form>
	</div> 
</div> <!-- end of main content -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
<div id="passwordDenied" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access Denied..!!!</p>
</div>
</div>
<div id="duplicateUser" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Unable to add. Duplicate username.</p>
</div>
<div id="msg_c" title="4Ps Message" class="hidden" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to add due to duplicate data.</p>
</div>
<div class="hidden" id="confirm_prov" title="Confirmation" onkeypress="return OnEnter(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Before you perform this action, please enter the password of Provincial Link for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>User role:&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="Provincial link"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password" id="confirm_password"  class="input" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div id="fpt_dialog" class="hidden">
	<p><label>Scan your fingerprint 4 times.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cpath %>/images/loading.gif" /></p>

</div>
<c:if test="${msg_checked}">
	<script type="text/javascript">
		add_dialog();
		msg_checkedTrue();
	</script>
</c:if>
<c:if test="${duplicateUser}">
	<script type="text/javascript">
	$(function(){
		$('#duplicateUser').dialog({
			show: "explode",
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
	});
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

<div class="hidden" id="errorpassword" title="4Ps Message">
	Password not match
</div>
<div class="hidden" id="invalidemail" title="4Ps Message">
	Please provide a valid email address
</div>
</div> 
</div><!-- end of page wrap -->
</body>
</html>