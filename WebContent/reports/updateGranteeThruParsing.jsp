<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page import="java.util.*"%>
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
<link rel="stylesheet" href="<%= cPath %>/css/view_transactionProfileCSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/reports.js"></script>
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
			var err2 = document.getElementById("err2").value;
			if(err == 1){
				$( "#messageDialog" ).dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:450,
					width:700,
					modal: true,
					buttons: {
						"Download": function(){
							
							$( "#ask" ).dialog({
								show: "fade",
								hide: "fade",
								resizable: false,
								height:160,
								modal: true,
								buttons: {
									"OK": function() {
										fname = document.getElementById("fname").value;
										//alert(fname);
										
										if(fname == "" || fname == null){
											document.getElementById("errmess").innerHTML = "<p style='color:red;font-size:10px;' >Please enter a name.</p>";
										}
										else{
											$(this).dialog("close");
											xhrGo("GET", "<%= cPath %>/UpdateGranteeParser?op="+2+"&file="+fname, afterDownloading, "plain");
										}
									}
								}
							});	
							
							$("#fname").focus();
							
						},
						"Save All": function() {
							//alert("you choose to save all");
							$("#saving").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Saving some changes..</p><img src='<%= cPath%>/images/loadingtxt.gif' />");
							xhrGo("GET", "<%= cPath %>/UpdateGranteeParser?op="+1, afterSaving, "plain");
							
						},
						"Cancel": function() {
							$(this).dialog("close");
						}
					}
				});
			}
			else if(err == 0){
				document.getElementById("mess").style.color = "#0099FF";
			} 
			
			if(err2 == 2){

				$( "#updates" ).dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:450,
					width:800,
					modal: true,
					buttons: {
						"Download": function(){
							//alert("download codes rai pah..."); 
							var fname2;
							$( "#ask2" ).dialog({
								show: "fade",
								hide: "fade",
								resizable: false,
								height:160,
								modal: true,
								buttons: {
									"OK": function() {
										fname2 = document.getElementById("fname2").value;
										//alert(fname2);
										
										if(fname2 == "" || fname2 == null){
											document.getElementById("errmess2").innerHTML = "<p style='color:red;font-size:10px;' >Please enter a name.</p>";
										}
										else{
											$(this).dialog("close");
											xhrGo("POST", "<%= cPath %>/DownloadChanges?file="+fname2, afterDownloading2, "plain");
										}
									}
								}
							});	
							$("#fname2").focus();
							
						},
						"Cancel": function() {
							$(this).dialog("close");
						}
					}
				});
			}
			else if(err2 == 0){
				document.getElementById("mess").style.color = "#0099FF";
			} 
			
			$( "#alertme" ).hide();
			$( "#ask" ).hide();
			$("#duplicatesDialog").hide();
			$('#load').hide();
			$("#prompt").hide();
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
		
		function afterDownloading(data){
				
			var x = eval('('+data+')');
			
			$( "#messageDialog" ).dialog( "close" );
			$("#path").html("<u style='color:blue;font-size:12px;font-weight:bold;' >"+x.file+"</u>");
			$( "#alertme" ).dialog({
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
		
		function afterDownloading2(data){
			
			var x = eval('('+data+')');
			
			$( "#updates" ).dialog( "close" );
			$("#path").html("<u style='color:blue;font-size:12px;font-weight:bold;' >"+x.file+"</u>");
			$( "#alertme" ).dialog({
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
		
		function afterSaving(data){
			var x = eval("("+data+")");
			//alert("in:"+x.error+" data:"+data);
			var str = "";
			var r = 1;
			
			$("#saving").html("");
			$( "#messageDialog" ).dialog( "close" );
			
			if(x.error == 1){
				//alert("in sa 1");
				for(var i=0;i<x.data.length;i++){
					
					str += "<tr>" +
								"<td>"+r+"</td>"+
								"<td id='id_cell'>" +x.data[i].household_id+ "</td>" +
								"<td id='id_cell'>" +x.data[i].hmember_id+ "</td>" +
								"<td id='id_cell'>" +x.data[i].name+ "</td>";
					str += "</tr>";		
					r++;
				}
				
				getID("duptbody").innerHTML = str;
				
				
				$( "#duplicatesDialog" ).dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height:450,
					width:700,
					modal: true,
					buttons: {
						"OK": function() {
							$( this ).dialog( "close" );
						}
					}
				});	
			}
			else{
				//alert("in sa 0");
				
				$( "#prompt" ).dialog({
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
	
	#mess,#error{
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
	.tbl-style2{
		border-collapse: collapse;
		border:1px solid black;
		width:750px;
	}
	.tbl-style2 td{
		padding-left: 5px;
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
<input  type="hidden" value="${err2}" id="err2"  />

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
						<h3>Update Excel file</h3>
					</div>
					<br/>
					<form action="<%=cPath %>/UpdateGranteeParserMag" name="parseForm" method="post" enctype="multipart/form-data"  onsubmit="return check_file('excelFile')">
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
				<p id="mess" ><span> <c:out value="${mess}"></c:out> </span></p>
				<p id="error" ><span> Your input is not an excel file. </span></p>
			</div>
	</div>
</center>
	
</div>	
</div> <!-- end of main content -->

<!-- ****************************************************************************************************************************************************** -->
<!-- ****************************************************************** Dialogs *************************************************************************** -->
<!-- ****************************************************************************************************************************************************** -->
<%-- <div id="messageDialog1" title="Updates in Parsing" class="DialogDiv" >

	<c:if test="${upList.s == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Grantee not saved in the database.</h4></th>
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
 --%>
<div id="messageDialog" title="Error in Parsing" class="DialogDiv" >

<div id="saving" ></div>

	<c:if test="${noFoundErr == true}">
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Grantee not saved in the database.</h4></th>
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


<div id="updates" >

	<c:if test="${granteeChanges == true}">
		<table border="1" class="tbl-style2" >
		<thead>
			<tr>
				<th colspan="5" ><h4>Updated Grantee and Members</h4></th>
			</tr>
			<tr>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Name</th>
				<th>Barangay</th>
				<th>Municipality</th>
			</tr>
		</thead>
		<tbody>
		<c:set var="gctr" value="0" ></c:set>
		<c:set var="cctr" value="0" ></c:set>
		<c:set var="sctr" value="0" ></c:set>
		<c:set var="gcctr" value="0" ></c:set>
		<c:forEach items="${upList}" var="list1" >
				
				<c:if test="${list1.status == 1}" >
				<c:if test="${gctr == 0}">
					<tr>
						<td colspan="5"  style="text-align: center;height: 10px;" >Grantee</td>
					</tr>
					<c:set var="gctr" value="1" ></c:set>
				</c:if>
					<tr>
						<td><c:out value="${list1.household_id}"></c:out></td>
						<td><c:out value="${list1.hmember_id}"></c:out></td>
						<td><c:out value="${list1.name}"></c:out></td>
						<td><c:out value="${list1.brgy_name}"></c:out></td>
						<td><c:out value="${list1.mun_name}"></c:out></td>
					</tr>
				</c:if>
			
		</c:forEach>
		
		
		<c:forEach items="${upList}" var="list1" >
				
				<c:if test="${list1.status == 2}" >
				<c:if test="${sctr == 0}">
					<tr>
						<td colspan="5"  style="text-align: center;height: 10px;" >Spouse</td>
					</tr>
					<c:set var="sctr" value="1" ></c:set>
				</c:if>
					<tr>
						<td><c:out value="${list1.household_id}"></c:out></td>
						<td><c:out value="${list1.hmember_id}"></c:out></td>
						<td><c:out value="${list1.name}"></c:out></td>
						<td><c:out value="${list1.brgy_name}"></c:out></td>
						<td><c:out value="${list1.mun_name}"></c:out></td>
					</tr>
				</c:if>
			
		</c:forEach>
		
		<c:forEach items="${upList}" var="list1" >
				
				<c:if test="${list1.status == 3}" >
				<c:if test="${cctr == 0}">
					<tr>
						<td colspan="5"  style="text-align: center;height: 10px;" >Children</td>
					</tr>
					<c:set var="cctr" value="1" ></c:set>
				</c:if>
					<tr>
						<td><c:out value="${list1.household_id}"></c:out></td>
						<td><c:out value="${list1.hmember_id}"></c:out></td>
						<td><c:out value="${list1.name}"></c:out></td>
						<td><c:out value="${list1.brgy_name}"></c:out></td>
						<td><c:out value="${list1.mun_name}"></c:out></td>
					</tr>
				</c:if>
			
		</c:forEach>
		
		<c:forEach items="${upList}" var="list1" >
				
				<c:if test="${list1.status == 6}" >
				<c:if test="${gcctr == 0}">
					<tr>
						<td colspan="5"  style="text-align: center;height: 10px;" >GrandChild</td>
					</tr>
					<c:set var="gcctr" value="1" ></c:set>
				</c:if>
					<tr>
						<td><c:out value="${list1.household_id}"></c:out></td>
						<td><c:out value="${list1.hmember_id}"></c:out></td>
						<td><c:out value="${list1.name}"></c:out></td>
						<td><c:out value="${list1.brgy_name}"></c:out></td>
						<td><c:out value="${list1.mun_name}"></c:out></td>
					</tr>
				</c:if>
			
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
	<c:if test="${deletedData == true}">
		<table border="1" class="tbl-style2" >
		<thead>
			<tr>
				<th colspan="3" ><h4>Deleted Members turn to Grantee</h4></th>
			</tr>
			<tr>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Name</th>
				<th>Barangay</th>
				<th>Municipality</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${delList}" var="list1" >
				<tr>
					<td><c:out value="${list1.household_id}"></c:out></td>
					<td><c:out value="${list1.hmember_id}"></c:out></td>
					<td><c:out value="${list1.name}"></c:out></td>
					<td><c:out value="${list1.brgy_name}"></c:out></td>
					<td><c:out value="${list1.mun_name}"></c:out></td>
				</tr>
			
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	<c:if test="${MembersChanges == true}">
		<table border="1" class="tbl-style2" >
		<thead>
			<tr>
				<th colspan="3" ><h4>New Family Members</h4></th>
			</tr>
			<tr>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Name</th>
				<th>Barangay</th>
				<th>Municipality</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${addList}" var="list1" >
				<tr>
					<td><c:out value="${list1.household_id}"></c:out></td>
					<td><c:out value="${list1.hmember_id}"></c:out></td>
					<td><c:out value="${list1.name}"></c:out></td>
					<td><c:out value="${list1.brgy_name}"></c:out></td>
					<td><c:out value="${list1.mun_name}"></c:out></td>
				</tr>
			
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
</div>

<div id="duplicatesDialog" title="Error in Parsing" class="DialogDiv" >
	
		<table border="1" class="tbl-style" >
		<thead>
			<tr>
				<th colspan="4" style="font-size: 13px;" ><h4>Duplicates entry at the following data were found.</h4></th>
			</tr>
			<tr>
				<th>#</th>
				<th>Household ID No.</th>
				<th>Household Member ID No.</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody id="duptbody" >
		
		</tbody>
		</table>

</div>

<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="dialog-confirm1" title="Warning">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please choose an excel file before clicking submit.</p>
</div>

<div id="prompt" title="Message">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Done saving.</p>
</div>

<div id="alertme" title="Message">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your file is saved at <span id="path" ></span> </p>
</div>
<div id="dialog-confirm2" title="Warning">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please select an excel file format.</p>
</div>
<div id="ask" title="Enter a name for the excel file.">
	<p>
		<label for="fname" >File Name:</label>
		<input autocomplete="off" type="text" id="fname" />
		<span id="errmess" ></span>
	</p>
</div>
<div id="ask2" title="Enter a name for the excel file." class="hidden">
	<p>
		<label for="fname2" >File Name:</label>
		<input autocomplete="off" type="text" id="fname2" />
		<span id="errmess2" ></span>
	</p>
</div>
</div> <!-- End of page wrap -->

</body>
</html>