<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   <%  String cPath = request.getContextPath(); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


<script type="text/javascript" src="<%= cPath %>/js/jquery_ui.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax3.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript" src="<%= cPath %>/ddaccordion.js"></script>
<script type="text/javascript" src="<%= cPath %>/ddaccordion_init.js"></script>

<script type="text/javascript">


$(document).ready(function(){
	var datenow = new Date();
	var curryear = datenow.getFullYear();
	var currmonth = datenow.getMonth();
	var currday = datenow.getDate();
	$( "#coveragedate" ).datepicker({
		changeMonth: true,
		changeYear: true,
		maxDate: new Date(curryear, currmonth, currday)
	});
});

function getID(ID){
	return document.getElementById(ID);
}

function getIDValue(ID){
	return document.getElementById(ID).value;
}

var ope2;
function pop_up_boxasdf(op){
	ope2 = op;
	if(op == 0){
		$( "#regReport" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:230,
			width:410,
			modal: true,
			title: "Generate Summary Report per Municipality",
			buttons: {
				"OK": function() {
					var op;
					op = 3;
					$("#savingasdf").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("POST","<%=cPath%>/DailyReports?op="+op,prompt, "plain");
				}
			}
		});	
	}
	else if(op == 1){
		
		xhrGo("GET", "<%= cPath %>/GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 2){
		xhrGo("GET", "<%= cPath %>/GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 3){
		$( "#confirm-process" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:200,
			width:500,
			modal: true,
			title: "Registration Summary Total",
			buttons: {
				"OK": function() {
					var op;
					op = 2;//only municipality
					//alert("only municipality");
					$("#saving2").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("POST","<%=cPath%>/DailyReports?op="+op,prompt, "plain");
				}
			}
		});	
	}
	else if(op == 4){
		//$("#cleanListmenu").title = "Generate Household Data";
		xhrGo("GET", "<%= cPath %>/GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 5){
	//	alert("in here..");
		xhrGo("POST", "<%= cPath %>/GetMunicipality", munData2jkl, "plain");
		
	}
	else if(op == 6){
		$( "#confirm-process" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:200,
			width:500,
			modal: true,
			title: "Generate System On Hold",
			buttons: {
				"OK": function() {
					//alert("only municipality");
					$("#saving2").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("GET","<%=cPath%>/SystemOnHold",prompt, "plain");
				}
			}
		});	
	}
}

function munData2asdf(data){
	var x = eval('('+data+')');
	var st= "";
	
	st = "<option value = '' >--------------</option>";

	for(var m=0;m<x.data.length;m++){
		st += "<option value='"+ x.data[m].mun_id+"'>"+x.data[m].mun_name+" </option>";
	}
	
	getID("muncmenu").innerHTML = st;
	pop_up_box2asdf();
}
function munData2jkl(data){
	var x = eval('('+data+')');
	var st= "";
	//alert(x.data);
	for(var m=0;m<x.data.length;m++){
		st += "<option value='"+ x.data[m]+"'>"+x.data[m]+" </option>";
	}
	//alert("setting value..");
	//alert(st);
	getID("team_no").innerHTML = st;
	pop_upbox2jkl();
}

function pop_upbox2jkl(){
	
	document.getElementById("messerr").innerHTML = "";	
	$( "#daily_RP" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:250,
		width:410,
		modal: true,
		title: "Daily Registration List w/ GRS",
		buttons: {
			"OK": function() {
				
				var sdate = document.getElementById("coveragedate").value;
				var team_num = document.getElementById("team_no").value;
				
				if(sdate != "" && team_num != ""){
					$("#saving1").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("GET","<%=cPath%>/DailyReports?date="+sdate+"&team_num="+team_num,prompt, "plain");
				}
				else{
					document.getElementById("messerr").innerHTML = "Please complete your input.";	
				}
			}
		}
	});
}

function pop_up_box2asdf(){
	document.getElementById("messerr123").innerHTML = "";	
	var title_div;
	if(ope2 == 1){
		title_div = "Generate Clean List";
	}
	else if(ope2 == 2){
		title_div = "Generate Household List";
	}
	else if(ope2 == 4){
		title_div = "Registration List per Mun w/ GRS";
	}
	
	
	$( "#cleanListmenu" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:250,
		width:500,
		modal: true,
		title: title_div,
		buttons: {
			"OK": function() {
				var mun = getID("muncmenu").value;
				var op;
				if(mun != ""){
					$("#saving123").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
				
					op = 1;
					if(ope2 == 1){
						xhrGo("POST","<%=cPath%>/GenerateCleanList?mun="+mun,prompt, "plain");
					}
					else if(ope2 == 2){
						xhrGo("POST","<%=cPath%>/SummaryReport?mun="+mun,prompt, "plain");
					}
					else if(ope2 == 4){
						xhrGo("POST","<%=cPath%>/DailyReports?op="+op+"&m="+mun,prompt, "plain");
					}
					
				}
				else{
					document.getElementById("messerr123").innerHTML = "Please complete your input.";	
				}
			}
		}
	});
}

function prompt(data){
	var x = eval('('+data+')');
	$( "#cleanListmenu" ).dialog("close");
	$("#saving123").html("");
	$("#savingasdf").html("");
	$( "#regReport" ).dialog( "close" );
	$("#saving1").html("");
	$( "#daily_RP" ).dialog( "close" );
	$("#saving2").html("");
	$( "#confirm-process" ).dialog( "close" );
	if(x.status == "not"){
		document.getElementById("menupath").innerHTML = "<u>"+x.path+"</u>";	
		document.getElementById("menumessageb").innerHTML = "Your file is saved at ";	
	}
	else if(x.status == "empty"){
		document.getElementById("menupath").innerHTML = "No data Found.</p>";
		document.getElementById("menumessageb").innerHTML = "File cannot be save.";	
	}
	else if(x.status == "emptylist"){
		document.getElementById("menupath").innerHTML = "<p>Your file is saved at <u>"+x.path+"</u></p>";
		document.getElementById("menumessageb").innerHTML = "Note:No registered Found with this date.Only GRS";	
	}
	else if(x.status == "emptygrs"){
		document.getElementById("menupath").innerHTML = "<p>Your file is saved at <u>"+x.path+"</u></p>";
		document.getElementById("menumessageb").innerHTML = "Note:No grs Found with this date.Registration list only.";	
	}
	else{
		document.getElementById("menupath").innerHTML = "<p style='color:red;font-size:11px;' >"+x.path+":File Name already exists.</p>";
		document.getElementById("menumessageb").innerHTML = "File cannot be save at ";	
	}
	$( "#menunote" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:200,
		width:500,
		modal: true,
		buttons: {
			"OK": function() {
				$( this ).dialog( "close" );
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
.hidden{
	display: none;
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
					<li><a class="long" href="<%=cPath %>/reports/reports_home.jsp">Released Report</a></li>		
					<li><a class="long" href="<%=cPath %>/reports/notReleaseReport.jsp">Not Released</a></li>
				</ul> 
			</li>
			<li><a class="long" href="<%=cPath %>/TotalMembers">Total Members</a></li>
			<li><a class="long fly" href="#">Registration Report</a> 
				 <ul>
					<li><a class="long" href="<%=cPath %>/NoFingerPrint">Members w/o Fingerprint</a></li>
					<li><a class="long" href="<%=cPath %>/WithFingerprint">Members with Fingerprint</a></li>
					<li><a class="long" href="<%=cPath %>/VerifiersDailyReports">Verifiers Registration Report</a></li>	
				</ul> 
			 </li> 
			
			 <li><a class="long fly" href="#">Generate Reports</a> 
				 <ul>
					<li onclick="pop_up_boxasdf(0);"><a  class="long"  href="#">Generate Summary Report</a></li>		
					<li onclick="pop_up_boxasdf(1);"><a  class="long"  href="#">Generate Clean List</a></li>
					<li onclick="pop_up_boxasdf(2);"><a  class="long"  href="#">Generate Household List</a></li>
					<li onclick="pop_up_boxasdf(3);"><a  class="long"  href="#">Registration Summary Total</a></li>
					<li onclick="pop_up_boxasdf(4);"><a  class="long"  href="#">Reg List per Mun w/ GRS</a></li>
					<li onclick="pop_up_boxasdf(5);"><a  class="long"  href="#">Daily Reg List w/ GRS</a></li>
					<li onclick="pop_up_boxasdf(6);"><a  class="long"  href="#">Generate System On Hold</a></li>
				</ul> 
			 </li> 
		</ul>
	</li>
	<li class="top"><a href="#nogo22" id="services" class="top_link"><span >Charts</span></a>
		<ul class="sub">
			<li><a class="long" href="<%= cPath %>/ViewChartsRegisteredPerMun">Registration dashboard</a></li>
			<li><a class="long" href="<%= cPath %>/ViewChartReportofRegistration">Chart Report</a></li>
		</ul>
	</li>
	
		<li class="top"><a href="#nogo22" id="services" class="top_link"><span >User Accounts</span></a>
			<ul class="sub">
				<c:choose>
					<c:when test="${provlink}">
						<%-- <li ><a class="long1" href="<%=cPath %>/ChangePassword" >Change Password</a></li> --%>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=10&hgdfhgdsfghdfgfgtfhgdsdshdtetbvnxjsaqw" >Administrator</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=1&fi4nskcmgoliw4hso8lkjsdjng=dikjigfh4nl" >Provincial Link</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=2&alkembiorJDKGkrTyhakldkdlskdjf=dfieu498RGskj" >Municipal Links</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=3&ajkdasjdfj=djRGsdjgfbwsusldkdj" >Financial Analyst</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=5&hidjkhslargiedslliruyatYTaUIjgdju=ejfnu48" >Data Verifiers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=4&djfi4804ifdkdlsk89ls=kdgi48o984u9484kld" >Bookkeepers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=6&dsfknwjdlgjfargiedkf=adfkjfkabedejos" >Grievance Officers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=7&djendlodieargiedgjdlk=dkdkjkdjfidjv" >Social Workers</a></li>
					</c:when>
					<c:when test="${admin}">
						<%-- <li ><a class="long1" href="<%=cPath %>/ChangePassword" >Change Password</a></li> --%>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=10&hgdfhgdsfghdfgfgtfhgdsdshdtetbvnxjsaqw" >Administrator</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=1&fi4nskcmgoliw4hso8lkjsdjng=dikjigfh4nl" >Provincial Link</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=2&alkembiorJDKGkrTyhakldkdlskdjf=dfieu498RGskj" >Municipal Links</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser2?id=3&ajkdasjdfj=djRGsdjgfbwsusldkdj" >Financial Analyst</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=5&hidjkhslargiedslliruyatYTaUIjgdju=ejfnu48" >Data Verifiers</a></li>
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=4&djfi4804ifdkdlsk89ls=kdgi48o984u9484kld" >Bookkeepers</a></li>
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
						<li ><a class="long1" href="<%=cPath %>/ViewAllUser?id=4&djfi4804ifdkdlsk89ls=kdgi48o984u9484kld">Bookkeepers</a></li>
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
	<div id="savingasdf" ></div>
</div>

<div id="menunote" class="hidden" title="Note:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="menumessageb"></span><span id="menupath" ></span> </p>
</div>

<div title="Generate Clean List" class="hidden" id="cleanListmenu" style = "text-align: left;font-size:15px;"  >
<br/>
	<form action="GenerateCleanList" method="POST" name="drform2" id="drform2" >
	<div style="margin-bottom:10px;">
	<label for="team_no" >Municipality:</label>
		<select class="input" id="muncmenu" name="muncmenu"  ></select>
	</div>
	<div id="saving123" ></div>
	
	<span style="font-size:11px;color:red;" id="messerr123"></span>
	</form>
</div>

<div id="confirm-process" class="hidden" title="Message:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span>Continue to generate this report..?</span></p>
	<div id="saving2" ></div>
</div>

<div title="Generate Daily Registration Report" class="hidden" id="daily_RP" style = "text-align: left;font-size:15px;"  >
<br/>
	<form action="DailyReports" method="GET" name="drform" id="drform" >
	<div style="margin-bottom:10px;">
	<label for="team_no" >Server #:</label>
	<select class="input" id="team_no" name="team_no"  ></select>
	</div>
	<div>
	<label for="coveragedate" >Date:</label>
	<input class="text-style input" type="text" maxlength="10" id="coveragedate" name="coveragedate" />
	</div>
	<span style="font-size:10px;color:red;" id="messerr"></span>
	<div id="saving1" ></div>
	</form>
</div>

</body>
</html>