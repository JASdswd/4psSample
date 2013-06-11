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
if(session.getAttribute("username") == null) {
	System.out.println("disconnected!");
%>
	<script type="text/javascript">top.document.location.replace('<%=cPath%>');</script>
<%
}else{
	System.out.println("connected!");
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pantawid Pamilyang Pilipino Program</title>
<meta name="Author" content="Stu Nicholls" />

<link type="text/css" rel="stylesheet" href="<%= cPath %>/css/view_logs.css"/>
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<%-- <link rel="stylesheet" href="<%= cPath %>/css/table.css"/> --%>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css"  href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css"/>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/pages.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.core.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.mouse.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
<script src="<%= cPath %>/pro_drop_1/stuHover.js" type="text/javascript"></script>
<script type="text/javascript" src="<%= cPath %>/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/reports.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		$(window).unload(function(){});
		
		$('tbody1 tr:odd', $('#content-table')).hide(); //hiding rows for test
	    var options = {
	      currPage : 1, 
	      ignoreRows : $('tbody1 tr:odd', $('#content-table')),
	      optionsForRows : [5,10,15],
	      rowsPerPage : 5,
	      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
	      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
	      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
	      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
	      topNav : false
	    };
	    $('#content-table').tablePagination(options);
	    
	    $( "#sdate" ).datepicker({
			changeMonth: true,
			changeYear: true
		});
		$( "#edate" ).datepicker({
			changeMonth: true,
			changeYear: true
		});
		
		$(".search").button({
			icons: {
				 primary: "ui-icon-search"
			}
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
	function changeDateLogs(){
		var sdate = "";
		var edate = "";
		var search = "";
		
		edate = getIDValue("edate");
		sdate = getIDValue("sdate");
		search = getIDValue("search");
		
		if(edate != "" && sdate != ""){
			xhrGo("POST", "<%= cPath %>/ViewLogs?sdate="+sdate+"&edate="+edate+"&search="+search, changeData, "plain");
		}	
	}

	function changeData(data){
		
		var x = eval('('+data+')');
		var str = "";
		var r = 0;
		var log_mess = "";
		if(x.data == "" || x.data == "{}"){
			getID("tbody").innerHTML = "<tR><td align='center'  colspan='4' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></td></tR>";
		}else{
			
			for(var i=0;i<x.data.length;i++){
				var row = 0;
				r++;
				str += "<tr>"+
						"<td>"+r+"</td>"+
						"<td>"+x.data[i].date+"</td>"+
					 	"<td>"+x.data[i].time+"</td>";/* +
						"<td>"+x.data[i].log_message+"</td>"; */

					log_mess = x.data[i].log_message;
					
					var mess = log_mess.split(" ");
					str += "<td>";
					for(var mr =  0;mr < mess.length;mr++){
						var crow = row++;
						
						if(crow < 2 || crow > 2){
							str += mess[mr]+" ";
						}
						
						if(crow == 2){
							var cr = 0;
							var hID = mess[mr].split("-");
							
							for(var cc = 0;cc < hID.length; cc++){
								var ccr = cr++;
							}
							
							if(ccr == 2){
								str += "<a href='<%=cPath %>/View_transactions2?hh_id="+mess[mr]+"'>"+mess[mr]+"</a> ";
							}
							else{
								str += mess[mr]+" ";
							}
							
						}
						
					}
					str += "</td>";
					str += "</tr>";
			}
			
			getID("tbody").innerHTML = str;
			$('tbody1 tr:odd', $('#content-table')).hide(); //hiding rows for test
		    var options = {
		      currPage : 1, 
		      ignoreRows : $('tbody1 tr:odd', $('#content-table')),
		      optionsForRows : [5,10,15],
		      rowsPerPage : 5,
		      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
		      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
		      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
		      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
		      topNav : false
		    };
		    $('#content-table').tablePagination(options);
		}
		
	}
	
	function confirmLogout(distination){
		$( "#dialog-confirm" ).dialog({
			show: "explode",
			hide: "explode",
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

	function ChangeLogs(){
		var sdate = "";
		var edate = "";
		var search = "";
		
		edate = getIDValue("edate");
		sdate = getIDValue("sdate");
		search = getIDValue("search");
		
		//alert("edate:"+edate+" sdate:"+sdate+" search:"+search);
		
		if(edate != "" && sdate != ""){
			xhrGo("POST", "<%= cPath %>/ViewLogs?sdate="+sdate+"&edate="+edate+"&search="+search, changeData, "plain");
		}
		else{
			alert("Please fill up all the fields.");
		}
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

</script>
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
<% int r = 1; %>
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
	<div id="filterDiv" >
		<p class="prgrph" >Date Coverage:</p>
		<label for="sdate" >From</label>
		<input type="text" id="sdate" name="sdate" onchange="changeDateLogs()" />
		<label for="edate" >To:</label>
		<input type="text" id="edate" name="edate" onchange="changeDateLogs()" />
		
	</div>
	
	<div id="filterDiv" >
		<!-- <form action="GetLogs"  > -->
			<label class="prgrph" for="searchField" >Search:</label>
			<input type="text" name="search" id="search" value="${search}" autocomplete="off" id="search" />
			<button type="submit" onclick="ChangeLogs();" class="search" >Search</button>
		<!-- </form> -->
	</div>
	<c:set var = "emp" value="0" ></c:set>
	<div >
		<table id="content-table" border="1" >
			<thead>
				<tr>
					<th>#</th>
					<th>Date</th>
					<th>Time</th>
					<th>Log message</th>
				</tr>
			</thead>
			<tbody id="tbody" >
			
			
				<c:forEach items="${logs_list}" var ="list">
				<%int row=0; %>
				<c:set var = "emp" value="1" ></c:set>
					<tr>
						<td><%= r++ %></td>
						<td><c:out value="${list.date}"></c:out></td>
						<td><c:out value="${list.time}"></c:out></td>
						<td>
							<c:forTokens items="${list.log_message}" var="mess" delims=" ">
								<%int c=0; %>
								<c:set var="frow" value="<%= row++ %>" ></c:set>
								<c:if test="${frow == 2}">
									<c:forTokens items="${mess}" var="col" delims="-">
										<c:set var="fc" value="<%=c++ %>" ></c:set>
										<c:if test="${fc == 2}">
											<a href="<%=cPath %>/View_transactions2?hh_id=${mess}"><c:out value="${mess}"></c:out></a>
										</c:if>
									</c:forTokens>
									<c:if test="${fc > 2 || fc < 2}">
										<c:out value="${mess}"></c:out>
									</c:if>
								</c:if>	
								<c:if test="${frow<2 || frow>2}">
									<c:out value="${mess}"></c:out>
								</c:if>	
							</c:forTokens>
						</td>
					</tr>
				</c:forEach>
				
				<c:if test="${emp == 0}">
					<tr><td align='center'  colspan='4' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></td> </tr>
				</c:if>
				
			</tbody>
			
		</table>
	</div>
</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
</body>
</html>