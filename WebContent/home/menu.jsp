<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   <%  String cPath = request.getContextPath(); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


<script type="text/javascript">


$(document).ready(function(){
	$("#regReport").hide();/* 
	$("#drform2").hide(); */
	$("#note").hide();
});

function pop_up_box(op){
	$( "#regReport" ).dialog({
		show: "drop",
		hide: "blind",
		resizable: false,
		height:230,
		width:410,
		modal: true,
		buttons: {
			"OK": function() {
				var op;
				op = 3;//only municipality
				//alert("only municipality");
				$("#saving2").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
				xhrGo("POST","<%=cPath%>/DailyReports?op="+op,prompt, "plain");
			}
		}
	});	
}

</script>

<style type="text/css">
.input{
		margin-bottom: 5px;
		border: 1px solid #8FA9BC;
		padding: 5px 3px 5px 10px;
		color:#000000;
		outline: none;
	}

</style>

</head>
<body>

<ul id="nav">
	<li class="top"><a href="<%=cPath %>/home/home_page.jsp" class="top_link"><span>Home</span></a></li>

	<li class="top"><a href="#nogo22" id="services" class="top_link"><span >Transactions</span></a>
		<ul class="sub">
			
			<li><a href="<%=cPath %>/TransactionView">View Records</a></li>
			<li><a href="<%=cPath %>/View_RecordTransaction">View Transaction</a></li>
			<c:choose>
				<c:when test="${financial_analyst}">
					<li><a href="<%=cPath %>/PdfParseRedirect">Import Payroll</a></li>
				</c:when>	
			</c:choose>
			
			<c:choose>
				<c:when test="${provlink}">
					<li><a href="<%=cPath %>/transactions/parseExcel.jsp">Parse Excel</a></li>
					<li><a href="<%=cPath %>/reports/updateGranteeThruParsing.jsp">Update Excel</a></li>
				</c:when>
			</c:choose>
			
			<li><a href="<%=cPath %>/ViewLogs">View Logs</a></li>
		</ul>
	</li>
	<li class="top"><a href="#nogo22" id="services" class="top_link"><span >Reports</span></a>
		<ul class="sub">
			<li><a class="long fly" href="#">Payout Reports</a>
				<ul>
					<li><a  class="long"  href="<%=cPath %>/ChooseReport?ope=1&op=1&fi4nskcmgoliw4hso8lkjsdjng=dikjigfh4nl">Released Report</a></li>		
					<li><a class="long" href="<%=cPath %>/ChooseReport?ope=2&op=2&fi4nskcmgoliw4hso8lkjsdjng=dikjigfh4nl">Not Released</a></li>	
				</ul> 
			</li>
			<li><a class="long" href="<%=cPath %>/TotalMembers">Total Members</a></li>
			<li><a class="long fly" href="#">Registration Report</a> 
				 <ul>
				 
					<li><a class="long" href="<%=cPath %>/WithFingerprint">Members with Fingerprint</a></li>
					<li><a class="long" href="<%=cPath %>/NoFingerPrint">Members w/o Fingerprint</a></li>
					<li><a class="long" href="#">Daily Registration Report</a></li>
				</ul> 
			 </li> 
			
			 <li><a class="long fly" href="#">Generate Reports</a> 
				 <ul>
					<li onclick="pop_up_box(0);"><a  class="long"  href="#">Generate Summary Report</a></li>		
					<li><a class="long" href="#">Generate Clean List</a></li>	
				</ul> 
			 </li> 
		</ul>
	</li>
	<li class="top"><a href="#nogo22" id="services" class="top_link"><span >Charts</span></a>
		<ul class="sub">
			<li><a class="long" href="<%= cPath %>/ViewChartsRegisteredPerMun">Registration Dashboard</a></li>
			<li><a class="long" href="<%= cPath %>/ViewChartReportofRegistration">Chart Report</a></li>
		</ul>
	</li>
	
		<li class="top"><a href="#nogo22" id="services" class="top_link"><span >User Accounts</span></a>
			<ul class="sub">
				<c:choose>
					<c:when test="${provlink}">
						<%-- <li ><a class="long1" href="<%=cPath %>/ChangePassword" >Change Password</a></li> --%>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=1&fi4nskcmgoliw4hso8lkjsdjng=dikjigfh4nl" >Provincial Link</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=2&alkembiorJDKGkrTyhakldkdlskdjf=dfieu498RGskj" >Municipal Links</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=3&ajkdasjdfj=djRGsdjgfbwsusldkdj" >Financial Analyst</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=5&hidjkhslargiedslliruyatYTaUIjgdju=ejfnu48" >Data Verifiers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=4&djfi4804ifdkdlsk89ls=kdgi48o984u9484kld" >Municipal Bookkeepers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=6&dsfknwjdlgjfargiedkf=adfkjfkabedejos" >Grievance Officers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=7&djendlodieargiedgjdlk=dkdkjkdjfidjv" >Social Workers</a></li>
					</c:when>
					<c:when test="${financial_analyst}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=3&ajkdasjdfj=djRGsdjgfbwsusldkdj" >Financial Analyst</a></li>
					</c:when>
					<c:when test="${munlink}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=2&alkembiorJDKGkrTyhakldkdlskdjf=dfieu498RGskj" >Municipal Links</a></li>
					</c:when>
					<c:when test="${book_keeper}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=4&djfi4804ifdkdlsk89ls=kdgi48o984u9484kld">Municipal Bookkeepers</a></li>
					</c:when>
					<c:when test="${verifier}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=5&hidjkhslargiedslliruyatYTaUIjgdju=ejfnu48" >Data Verifiers</a></li>
					</c:when>
					<c:when test="${grievance_officer}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=6&dsfknwjdlgjfargiedkf=adfkjfkabedejos" >Grievance Officers</a></li>
					</c:when>
					<c:when test="${social_worker}">
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=7&djendlodieargiedgjdlk=dkdkjkdjfidjv" >Social Workers</a></li>
					</c:when>
				</c:choose>
			</ul>
		</li>
		<li class="top"><a href="<%= cPath %>/about_us/aboutUs.jsp" id="products" class="top_link"><span >About Us</span></a></li>
		<li class="top"><a name="<%= cPath %>/Logout"  id="privacy" class="top_link" onclick="confirmLogout(this.name)"><span>Log Out</span></a></li>
</ul>

<div id="regReport" class="hidden" title="Message:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span>Continue to generate this report..?</span></p>
	<div id="saving" ></div>
</div>

<div id="note" class="hidden" title="Note:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="messageb"></span><span id="path" ></span> </p>
</div>


</body>
</html>