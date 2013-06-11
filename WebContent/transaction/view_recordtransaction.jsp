<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page import="java.util.*"%> 
	<%@ page import="java.text.*"%> 
    <% String cPath = request.getContextPath(); %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",-1);
%>
<html>
<%	
if(session.getAttribute("username")==null){
	System.out.println("username is null view_record.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program</title>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />

<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link type="text/css" href="<%= cPath %>/development-bundle/themes/cupertino/jquery.ui.all.css" rel="stylesheet" />
<title>View Transaction</title>
<script type="text/javascript" src="<%= cPath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script><%-- 
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script> --%>
<script type="text/javascript" src="<%= cPath %>/js/validateNumber.js"></script>
<!--FOR pagination  -->
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.filer.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.pager.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter-2.0.3.js" ></script>

<!--pagination  -->
<script src="<%=cPath %>/development-bundle/jquery-1.6.2.js"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="<%=cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="<%=cPath %>/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
	<link rel="stylesheet" href="<%=cPath %>/development-bundle/demos/demos.css">
<script type="text/javascript" src="<%= cPath %>/js/pages.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<!-- end -->
<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").button({
		icons : {primary: "ui-icon-search"}
	});
	$("#municipal").change(function(){
		var mun=document.getElementById("municipal").value;
		xhrGo("GET","ChangeBarangay?municipal="+mun, display, "plain");
	});
	//$("html").scrollTop(168);
	/*for pagination  */
	/* $("#tab").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra']})
	.tablesorterPager({ container: $("#pagerone"), positionFixed: false 
	}); */
	

	 $('tbody1 tr:odd', $('#tab')).hide(); //hiding rows for test
	    var options = {
	      currPage : 1, 
	      ignoreRows : $('tbody1 tr:odd', $('#display')),
	      optionsForRows : [10],
	      rowsPerPage : 10,
	      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
	      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
	      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
	      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
	      topNav : false
	    };
	    $('#tab').tablePagination(options);

	$(window).unload(function(){});
});
	/* $(function(){
		$("#form").submit(function(){
			var emp=Boolean(false);
			var f=document.fields['municipal'];
			if(f.value=="-Select Municipality-"){
				f.style.borderColor="red";
				f.focus();
				emp=true;
			}
			else{
				f.style.borderColor="#8FA9BC";
			}
			var f=document.fields['brgy'];
			if(f.value=="-Select Barangay-"){
				f.style.borderColor="red";
				f.focus();
				emp=true;
			}
			else{
				f.style.borderColor="#8FA9BC";
			}
			var f=document.fields['month'];
			if(f.value=="-Select Month_Year-"){
				f.style.borderColor="red";
				f.focus();
				emp=true;
			}
			else{
				f.style.borderColor="#8FA9BC";
			}
			/* var f=document.fields['year'];
			if(f.value==""){
				f.style.borderColor="red";
				f.focus();
				emp=true;
			}
			else{
				f.style.borderColor="#8FA9BC";
			} */
			/*if(emp == true){
				alert("Fill-up all Fields Color Red");
				return false;
			}
			
			return true;
		});
	}); */
	function display(data){
		var x=eval('('+data+')');
		var strbuild="";
		strbuild+="<select id='brgy_name' name='brgy' class='input'>"+
				"<option value='' selected='selected' disabled='disabled' >-Select Barangay-</option>";
		for(var i=0;i<x.data.length;i++){
			strbuild+="<option value='"+x.data[i].brgy_id+"'>"+x.data[i].barangay+"</option>";
		}
		strbuild+="</select>";
		document.getElementById("by").innerHTML=strbuild;
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
	/* $(".first").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-first"
		}
	});
	$(".prev").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-prev"
		}
	});
	$(".next").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-next"
		}
	});
	$(".last").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-end"
		}
	}); */
	
	function func(){
		//alert("you click on me");
		
		var trans_date = $("#trans_date").val();
		var municipal = $("#municipal").val();
		var brgy_name = $("#brgy_name").val();
		var emp = 0;
		
		if(trans_date == ""){
			//alert("trans date emp");
			$("#trans_date").css("border-color","red");
			emp = 1;
		}
		else{
			//alert("trans date not emp");f.style.borderColor="#8FA9BC";
			$("#trans_date").css("border-color","#8FA9BC");
		}
		
		if(municipal == ""){
			$("#municipal").css("border-color","red");
			emp = 1;
		}
		else{
			$("#municipal").css("border-color","#8FA9BC");
		}
		
		if(brgy_name == ""){
			$("#brgy_name").css("border-color","red");
			emp = 1;
		}
		else{
			$("#brgy_name").css("border-color","#8FA9BC");
		}
		
		if(emp == 1){
			return false;
		}
		else {
			return true;
		}
		
	}
	
	/*end for pagination  */
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
<style type="text/css">

a:LINK{
	color:blue;
}
a:HOVER{
	color:red;
}
a:VISITED{
	color:purple;
}

#main-content{
		min-height: 580px;
}

#main{
	height: auto;
	padding-bottom: 10px;
}
#dv{
	padding:5px 0px 0px 0px;
}
.ui-widget{
		font-size: 12px;
	}
.input{
		margin-bottom: 2px;
		border: 1px solid #8FA9BC;
		padding: 3px 2px 3px 4px;
		color:#000000;
		outline: none;
	}
	.input:FOCUS{
		border: 1px solid #000045;
		box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
		-webkit-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
		-moz-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
	}
	.input:HOVER{
		border: 1px solid #000886;
	}
	#tab{
		margin-top:10px;
		border-collapse: collapse;
		border: 1px solid black;
		font-size: 14px;
	}
	#tab thead{
		font-size: 13px;
	}
	#tb{
		margin-bottom: 3px;
		
	}
	.t{
		padding: 5px 10px ;
		width: 140px;
		background-color: lightblue;
	}
	.raw{
		background-color: lightblue;
	}
	.t2{
		padding: 5px 10px;
		width: 200px;
		background-color: lightblue;
	}
	.t1{
		padding: 5px 10px;
		width: 80px;
		background-color: lightblue;
	}
	.t4{
		padding: 5px 10px;
		width: 100px;
		background-color: lightblue;
	}
	.t3{
		padding: 5px 10px;
		width: 300px;
		background-color: lightblue;
	}
	.tb{
		margin-bottom: 10px;
	}
	h3{
		text-transform: capitalize;
		font-family: sans-serif;
		font-size: 18px;
		letter-spacing: 5px;
		margin-bottom: 3px;
	}
	table {
	font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
}
	.tbl_data{
		padding: 5px 1px 0px 6px;
	}
	.tbl_row:HOVER {
		background-color: #ddd;
}
	
	#main h3{
		margin-top: 8px;
		margin-bottom: 5px;
	}
	
	.tbl_data a{
		text-decoration: none;
	}
	.link{
		padding-left: 7px;
		font-size: 12px;
	}
	#main{
		/* height: auto; */
	}
	
	/* #tfoot{
		padding: 6px 0px 6px 0px;
	} */
	 #tablePagination { 
            font-size: 10px; 
            padding: 0px 5px; 
            height: 40px;
            width: 890px;
            border:  1px solid black; ;
            border-collapse: collapse;
            border-top: none;
            padding-top: 10px;
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
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
	<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu">

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
<div id="main" align="center">
	<h3 align="center">View Transaction</h3>
	<div align="center" id="dv">
		<form action="View_RecordTransaction" method="post" id="form" name="fields">
		<table id="tb">
			<tr>
				<td><select name="municipal" id="municipal" class="input">
					<option value="" selected="selected" disabled="disabled">-Select Municipality-</option>
					<c:forEach items="${municipal_list}" var="list">
						<c:if test="${municipal==list.mun_id}">
							<option value="${list.mun_id}" selected="selected"><c:out value="${list.municipal}"></c:out></option>
						</c:if>
						<c:if test="${municipal!=list.mun_id}">
							<option value="${list.mun_id}" ><c:out value="${list.municipal}"></c:out></option>
						</c:if>
					</c:forEach>
				</select>
				</td>
				<td id="by"><select id="brgy_name" name="brgy" class="input" style="width: 150px;">
					
					
						<option value="" selected="selected" disabled="disabled">-Select Barangay-</option>
					
					<c:if test="${brgy!=''}">
						<c:forEach items="${brgy_list}" var="list">
						<c:if test="${brgy==list.brgy_id}">
							<option value="${list.brgy_id}" selected="selected"><c:out value="${list.barangay}"></c:out></option>
						</c:if>
						<c:if test="${brgy!=list.brgy_id}">
							<option value="${list.brgy_id}" ><c:out value="${list.barangay}"></c:out></option>
						</c:if>
						</c:forEach>
					</c:if>
				</select>
				</td>
				<td style="width:100px;text-align: right;">
					<button onclick="javascript: return func();" id="search_btn" type="submit">Search</button>
				</td>
			</tr>
			<tr>
				<td colspan="2"><select id="trans_date" name="month" class="input" style="width: 315px;">
					<option value="" selected="selected" disabled="disabled">- Select Month-Year -</option>
					<c:forEach items="${month_year_list}" var="list">
						<c:if test="${month==list.month_year}">
							<option selected="selected"><c:out value="${list.month_year}"></c:out> </option>
						</c:if>
						<c:if test="${month!=list.month_year}">
							<option><c:out value="${list.month_year}"></c:out> </option>
						</c:if>
						
					</c:forEach>
				</select>
				</td>
				<%-- <td>Year:<input name="year" class="input" style="width: 130px" value="${year}" onkeypress="return numbersonly1(event,false);" maxlength="4" ></td> --%>
			</tr>
		</table>
		</form>
		<c:if test="${mess=='No Record Found'}">
			<table id="tab" border="1">
			<thead>
				<tr>
					<th class="raw">&nbsp;#&nbsp;</th>
					<th class="t">Household ID NO.</th>
					<th class="t3">Name of Head</th>
					<th class="t1">Amount</th>
					<th class="t2">Status</th>
					<th class="t4">Method</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="6">
						<h2 style="padding: 5px;" align="center">No Record Found</h2>
					</td>
				</tr>
			</tbody>
			</table>
		</c:if>
		<c:if test="${mess!='No Record Found'}">
			<% int ctr = 0;  %>
		<table id="tab" border="1">
			<thead>
				<tr>
					<th class="raw">&nbsp;#&nbsp;</th>
					<th class="t">Household ID NO.</th>
					<th class="t3">Name of Head</th>
					<th class="t1">Amount</th>
					<th class="t2">Status</th>
					<th class="t4">Method</th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${record_list}" var="list">
					<tr class="tbl_row">
						<td class="tbl_data" ><%=++ctr %></td>
						<td class="tbl_data"><c:out value="${list.household_id}"></c:out></td>
						<td class="tbl_data"><c:out value="${list.head_name}"></c:out></td>
						<td class="tbl_data">P<c:out value="${list.amount}"></c:out></td>
						<c:if test="${list.receive==0}">
							<td class="tbl_data">Cash for Disbursement</td>
						</c:if>
						<c:if test="${list.receive==1}">
							<c:choose>
								<c:when test="${list.sub == 0}">
									<td class="tbl_data">Received by Grantee</td>
								</c:when>
								<c:otherwise>
									<td class="tbl_data">Received by Representative</td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<td class="tbl_data link"><a id="viewTransaction" href="<%=cPath %>/View_trans?id=${list.household_id}">View Transaction</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<%-- <tfoot id="tfoot" align="center">
				<c:if test="${record_list!=NULL}">
				<tr id="pagerone">
				 <td colspan="11" style="border-right: solid 3px #7f7f7f;">
				 <input type="button" class="first" value="First" />
				 <input type="button" class="prev" value="Prev" />
				 <input readonly="readonly" size="3" type="text" class="pagedisplay" value="first" />
				 <input type="button" class="next" value="Next" />
				 <input type="button" class="last" value="Last" />
				 <select class="pagesize">
					<option selected="selected"  value="10">10</option>
				</select>
				</td>
				</tr>
				</c:if>
			</tfoot> --%>
		</table>
		</c:if>
	</div>
</div>

</div><!-- end main-content -->
<div id="dialog-confirm" title="Logout" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
</div> <!-- end of page wrap -->
</body>
</html>