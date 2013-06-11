<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String cpath = request.getContextPath(); %>
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
	System.out.println("username is null home_page.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pantawid Pamilyang Pilipino Program</title>




<script type="text/javascript" src="<%= cpath %>/js/ajax.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/ajax3.js"></script>
	<script type="text/javascript" src="<%= cpath %>/js/ajaxx.js"></script>
	<link rel="shotcut icon" href="<%= cpath %>/image/home.png" type="image/x-icon" />
	<link rel="stylesheet" href="<%= cpath %>/css/add_transactions_CSS.css"/>
	<link rel="stylesheet" href="<%= cpath %>/css/home.css"/>
	
<script type="text/javascript" src="<%=cpath %>/development-bundle/jquery-1.6.2.js"></script> <!-- need for highcharts and dialog JQuery. -->

	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="<%=cpath %>/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
 <script type="text/javascript" src="<%= cpath %>/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%= cpath %>/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%= cpath %>/highcharts/data.js"></script>
<script type="text/javascript" src="<%= cpath %>/highcharts/grid.js"></script> 
	<link rel="stylesheet" type="text/css" href="<%= cpath %>/pro_drop_1/pro_drop_1.css" />
	<link rel="stylesheet" href="<%=cpath %>/css/cupertino/jquery-ui-1.8.16.custom.css">
	<link rel="stylesheet" href="<%= cpath %>/development-bundle/demos/demos.css">
	<script type="text/javascript" src="<%=cpath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cpath %>/css/displayTimeDate.css"/>
<script type="text/javascript" src="<%=cpath %>/js/logout.js"></script> 



<script type="text/javascript">
	$(function () {
		
	    $('#container0').highcharts({
	        data: {
	            table: document.getElementById('datatable0')
	        },
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Registration Dashboard Report'
	        },
	        yAxis: {
	            allowDecimals: false,
	            title: {
<<<<<<< HEAD
	                text: 'Number of Beneficiariesssss'
	            }
	        },
	        tooltip: {
	        	
=======
	                text: 'Number of Beneficiaries'
	            }
	        },
	        tooltip: {
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+
	                    this.y +' '+ this.x.toLowerCase();
	            }
	        }
	    });
	    $('#container1').highcharts({
	        data: {
	            table: document.getElementById('datatable1')
	        },
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Registration Dashboard Report'
	        },
	        yAxis: {
	            allowDecimals: false,
	            title: {
	                text: 'Number of Beneficiaries'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+
	                    this.y +' '+ this.x.toLowerCase();
	            }
	        }
	    });
	    $('#container2').highcharts({
	        data: {
	            table: document.getElementById('datatable2')
	        },
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Registration Dashboard Report'
	        },
	        yAxis: {
	            allowDecimals: false,
	            title: {
	                text: 'Number of Beneficiaries'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+
	                    this.y +' '+ this.x.toLowerCase();
	            }
	        }
	    });
	    $('#container3').highcharts({
	        data: {
	            table: document.getElementById('datatable3')
	        },
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Registration Dashboard Report'
	        },
	        yAxis: {
	            allowDecimals: false,
	            title: {
	                text: 'Number of Beneficiaries'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+
	                    this.y +' '+ this.x.toLowerCase();
	            }
	        }
	    });
	});
</script>
<style type="text/css">
	#container{
		min-width: 400px;
		height: 400px;
		margin: 5px auto;
	}
	.hidden{
		display: none;
	}
	#datatable0{
		width: 500px;
		margin: 0 auto;
		border: 1px solid #000;
		font-size: 13px;
	}
	#datatable0 th{
		background-color: #ccc;
	}
	#datatable0 td{
		padding-left: 5px;
		background-color: #eee;
	}
	#datatable0 #tbodydata .tbl_r:HOVER {
		background-color: #fff;
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
<div id="container0" style=""></div>
<%int row = 0;int ctr = 0; %>

	<table id="datatable0">
		<thead>
			<tr>
				<th>Municipality</th>
				<th>Registered</th>
				<th>Not Registered</th>
			</tr>
		</thead>
		<tbody id="tbodydata">
			<c:forEach items="${list}" var="list">
				<% if(row<=5){ %> 
					<tr class="tbl_r">
						<td ><c:out value="${list.municipality}"></c:out></td>
						<td><c:out value="${list.registered}"></c:out></td>
						<td><c:out value="${list.notRegistered}"></c:out></td>
					</tr>
				<%}else{
					ctr++;
				}
					row++;
				%>	
			</c:forEach>
		</tbody>
	</table>

</div>
</div>
<div id="dialog-confirm" class="hidden" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
</body>
</html>
