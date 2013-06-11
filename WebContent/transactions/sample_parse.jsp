<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page import="java.util.*"%>
	<%@ page import="java.text.*"%> 
    <% String cPath = request.getContextPath(); %>
    
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
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript">
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
			
			var err = document.getElementById("err").value;
			var catchList = document.getElementById("clist").value;
			var fname = "";
			if(err == 1){
				$( "#messageDialog" ).dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:450,
					width:700,
					modal: true,
					buttons: {
						"Save as Excel": function() {
							
							 $( "#ask" ).dialog({
								show: "fade",
								hide: "fade",
								resizable: false,
								height:160,
								modal: true,
								buttons: {
									"OK": function() {
										fname = document.getElementById("fname").value;
										if(fname == "" || fname == null){
											document.getElementById("errmess").innerHTML = "<p style='color:red;font-size:10px;' >Please enter a name.</p>";
										}
										else{
											xhrGo("GET", "<%= cPath %>/PrintData?file="+fname, changeData, "plain");
										}
									}
								}
							});				
							
						}
					}
				});
			}
			else if(err == 0){
				document.getElementById("mess").style.color = "#0099FF";
			}
			$('#load').hide();
			$( "#note" ).hide();
			$( "#ask" ).hide();
			$('#dialog-confirm1').hide();
			$('#dialog-confirm2').hide();
			 $('#error').hide();
			$("#submit_btn").button({
				icons : {primary : "ui-icon-circle-check" }
			});
			
			$('input[type="text"]').addClass("idleField");
			$('input[type="text"]').focus(function() {
				$(this).removeClass("idleField").addClass("focusField");
		    if (this.value == this.defaultValue){ 
		    	this.value = '';
			}
			if(this.value != this.defaultValue){
				this.select();
			}
			});
			$('input[type="text"]').blur(function() {
				$(this).removeClass("focusField").addClass("idleField");
			    if ($.trim(this.value) == ''){
			    	this.value = (this.defaultValue ? this.defaultValue : '');
				}
			});
				
			});
		
		function changeData(data){
			
			var x = eval('('+data+')');
			document.getElementById("path").innerHTML = "<u>"+x.path+"</u>";
			if(x.status == "not"){
				$( "#ask" ).dialog("close");
				$( "#messageDialog" ).dialog("close");
				$( "#note" ).dialog({
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
				document.getElementById("errmess").innerHTML = "<p style='color:red;font-size:11px;' >File Name already exists.</p>";
			}
		}
		
		function check_file(picField) {
			 var picFile = picField;
			 var imagePath = document.getElementById(picFile).value;
			 var pathLength = imagePath.length;
			 var lastDot = imagePath.lastIndexOf(".");
			 var fileType = imagePath.substring(lastDot,pathLength);
			 var input = document.getElementById('excelFile');
			 var file;
			 if (!input.files[0]) {
			        /* alert("Please select a excel file before clicking submit."); */
			        $( "#dialog-confirm1" ).dialog({
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
			        return false;
			 }
			 else if((fileType == ".xls")|| (fileType == ".XLS") || (fileType == ".csv")|| (fileType == ".CSV")) {
		        /*file = input.files[0];
		        if(file.size>1000000){
		        	 alert("Size of image must be lesser than 1mb(1 megabyte).");
		        	 return false;
		        }*/
					//$('#sign').show('fast');
					$('#load').show();
					$('#error').hide();
					$('#mess').hide();
					//document.getElementById("load").innerHTML="<img src=\"../images/loading.gif\" alt=\"loading\"/>";
				
				return true;
			 }
			 else {
			 	/* alert("Please select an excel file format."); */
			 	 $( "#dialog-confirm2" ).dialog({
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
			 	return false;
			 }
		}
		
		$(document).ready(function(){
			$(window).unload(function(){});
			
		});
		
		function checkifExcel(){
			//alert("in");
			 var input = document.getElementById('excelFile').value;
			 var pathLength = input.length;
			 var lastDot = input.lastIndexOf(".");
			 var fileType = input.substring(lastDot,pathLength);
			 
			 if((fileType == ".xls")|| (fileType == ".XLS") || (fileType == ".csv")|| (fileType == ".CSV")) {
				 //alert("xls it file");
				 $('#error').hide();
				 $('#mess').hide();
			 }
			 else{
				 //alert("not xls");
				  $('#mess').hide();
				 $('#error').show();
			 }
		}
		
	
		</script>
<style type="text/css">

input[type="text"]{
		margin-bottom: 5px;
    	padding: 5px 3px 5px 10px;
    	outline:none;
    	font-family:Arial, Helvetica, sans-serif;
		font-size:14px;
		color:#404040;
    }
    .focusField{
    	border:solid 2px #73A6FF;
    	background:#EFF5FF;
    	color:#000;
    }
    .idleField{
    	background:#EEE;
    	color: #6F6F6F;
		border: solid 2px grey;
    }	

	#ask{
		height: 150px;
	}
	
	#note{
		font-size: 12px;
	}
	
	#error{
		color:red;
		font-size: 13px;
	}
	
	.errclass{
		color:red;
		font-size: 13px;
	}
	
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
	
	.tbl-style{
		border-collapse: collapse;
		border:1px solid black;
		width:650px;
	}
	.tbl-style td{
		padding-left: 5px;
	}
	
	.DialogDiv{
		margin-top: 7px;
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

<input  type="hidden" value="${err}" id="err"  />

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
</div>

<div id="main" >
	<center>
	<div class="fakewindowcontain">
		<br/>
			<div  id="main-div" class="ui-widget ui-widget-content ui-corner-all">
				<div class="ui-dialog-content ui-widget-content" id="content">
					<div id="login-title">
						<br/>
						<h3>Import Excel file</h3>
					</div>
					<br/>
					<!-- <form action="<%=cPath %>/ExcelParse" name="parseForm" method="post" enctype="multipart/form-data"  onsubmit="return check_file('excelFile')">
					 -->
					 <form action="<%=cPath %>/UpdateMemberGender" name="parseForm" method="post" enctype="multipart/form-data"  onsubmit="return check_file('excelFile')">
						<br/>
						<input type="file" name="excelFile" onchange="checkifExcel(this.id);" class="input" id="excelFile"/>
						<button id="submit_btn">Submit</button>
					</form>
					
					
					
				</div>
				<div id="load">
					<!-- <p><label>Loading...</label></p> -->
					<img alt="loading" src="<%= cPath %>/images/loadingtxt.gif">
					<br/>
					<img src="<%= cPath %>/images/loading.gif" />
				</div>
				<c:choose>
					<c:when test="${messext == 0}">
						<p id="mess" class="errclass" ><span> <c:out value="${mess}"></c:out> </span></p>
					</c:when>
					<c:otherwise>
						<p id="mess" ><span> <c:out value="${mess}"></c:out> </span></p>
					</c:otherwise>
				</c:choose>
				
				<p id="error" ><span> Your input is not an excel file. </span></p>
			</div>
	</div>
</center>
	
</div>	
</div> <!-- end of main content -->

<!-- ****************************************************************************************************************************************************** -->
<!-- ****************************************************************** Dialogs *************************************************************************** -->
<!-- ****************************************************************************************************************************************************** -->

<input type="hidden" id="clist" value="${catchList}"/>

<div id="messageDialog" title="Error in Parsing" class="DialogDiv" >
<% int r = 1,r1 = 1,r2 = 1; %>
	<c:if test="${granteeErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="5" ><h4>Duplicates Found in Household Table</h4></th>
			</tr>
			<tr>
				<th>#</th>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Grantee Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${catchList}" var="list" >
			<c:if test="${list.status == 1}">
				<tr>
					<td> <%= r++ %> </td>
					<td><c:out value="${list.household_id}"></c:out></td>
					<td><c:out value="${list.hmember_id}"></c:out></td>
					<td><c:out value="${list.name}"></c:out></td>
				</tr>
			</c:if>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
	<c:if test="${spouseErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Duplicates Found in Spouse Table</h4></th>
			</tr>
			<tr>
				<th>#</th>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Spouse Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${catchList}" var="list" >
			<c:if test="${list.status == 2}">
				<tr>
					<td> <%= r1++ %> </td>
					<td><c:out value="${list.household_id}"></c:out></td>
					<td><c:out value="${list.hmember_id}"></c:out></td>
					<td><c:out value="${list.name}"></c:out></td>
				</tr>
			</c:if>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
	<c:if test="${childErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Duplicates Found in Children Table</h4></th>
			</tr>
			<tr>
				<th>#</th>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Child Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${catchList}" var="list" >
			<c:if test="${list.status == 3}">
				<tr>
					<td> <%= r2++ %> </td>
					<td><c:out value="${list.household_id}"></c:out></td>
					<td><c:out value="${list.hmember_id}"></c:out></td>
					<td><c:out value="${list.name}"></c:out></td>
				</tr>
			</c:if>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
	<c:if test="${gchildErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Duplicates Found in GrandChild Table</h4></th>
			</tr>
			<tr>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>GrandChild Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${catchList}" var="list" >
			<c:if test="${list.status == 6}">
				<tr>
					<td><c:out value="${list.household_id}"></c:out></td>
					<td><c:out value="${list.hmember_id}"></c:out></td>
					<td><c:out value="${list.name}"></c:out></td>
				</tr>
			</c:if>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	<c:if test="${noFoundErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Grantee of the Following Data was not found.</h4></th>
			</tr>
			<tr>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${DataNotSave}" var="list1" >
				<tr>
					<td><c:out value="${list1.household_id}"></c:out></td>
					<td><c:out value="${list1.hmember_id}"></c:out></td>
					<td><c:out value="${list1.name}"></c:out></td>
				</tr>
			
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
</div>

<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="dialog-confirm1" title="Warning">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please choose an excel file before clicking submit.</p>
</div>

<div id="dialog-confirm2" title="Warning">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please select an excel file format.</p>
</div>
<div id="note" title="Note:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your file is saved at <span id="path" ></span> </p>
</div>
<div id="ask" title="Enter a name for the excel file.">
	<p>
		<label for="fname" >File Name:</label>
		<input autocomplete="off" type="text" id="fname" />
		<span id="errmess" ></span>
	</p>
</div>
</div> <!-- End of page wrap -->

</body>
</html>