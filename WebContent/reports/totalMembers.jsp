<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String cPath = request.getContextPath(); %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<meta name="Author" content="Stu Nicholls" />

<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/><%-- 
<link rel="stylesheet" href="<%= cPath %>/css/total_member.css"/> --%>
<link rel="stylesheet" href="<%= cPath %>/css/totalmembers.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/css/menuWPM.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" href="<%=cPath %>/development-bundle/demos/demos.css">
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script src="<%= cPath %>/pro_drop_1/stuHover.js" type="text/javascript"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ddaccordion.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ddaccordion_init.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/hide_show.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
<script src="<%= cPath %>/development-bundle/jquery-1.6.2.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.core.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.mouse.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.draggable.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.position.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.accordion.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.resizable.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.core.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.blind.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.fade.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/virtualpaginate.js"></script><%-- 
<script type="text/javascript" src="<%= cPath %>/js/page.js"></script> --%>
<script type="text/javascript" src="<%= cPath %>/js/paginator.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript">

//http://dl.dropbox.com/u/4151695/html/pajinate/examples/example1.html 

$(document).ready(function(){
	$(window).unload(function(){});
	
	$(function() {
	    var $research = $('.research');/* 
	    $research.find("tr").not('.accordion').hide();
	    $research.find("tr").eq(0).show();  */
	    $research.find("tr:odd").addClass("odd");
	    $research.find(".accordion").click(function(){
	        $research.find('.accordion').not(this).siblings().fadeOut(500);
	        $(this).siblings().fadeToggle(500);
	    }).eq(0).trigger('click');
	    
	});
	
});

$(document).ready(function(){
	$('#paging_container7').pajinate({
		num_page_links_to_display : 3,
		items_per_page : 10	
	});
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
  		xmlhttp = new XMLHttpRequest();
  	} else {
  		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
  		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
  			$("#div").fadeOut(300);
  			$("#overlay").fadeOut(800);
  		}
	};
	xmlhttp.open("GET","<%= cPath%>/TotalMembers",true);
	xmlhttp.send();
	
});		
	
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

	function loading(){
		
	
	}
	
</script>
<style type="text/css">
	#dialog-confirm{
		display: none;
	}
	
	.research{
		width:inherit;
	}
	
	#main{
		text-transform: capitalize;
		font: 12px "Lucida Grande", "Trebuchet MS", Verdana, Helvetica,
		sans-serif bolder;
		/* font-weight: bold; */
	}
	
	.subheader{
		width:450px;
		padding-left: 5px;
	} 
	
	
	.contents{
		 padding-left: 50px; 
	}
	.contents2{
		 padding-left: 80px;
	}
	#main-content{
		min-height: 600px;
	}
	.page_navigation a{
		color: white;
		text-decoration: none;
		padding: 3px 3px 3px 3px;	
	}
	.accordion:HOVER{
		background:  #1b75bb url(../image/gbg2.gif) repeat bottom left;
		color: white;
		border-color: #216796;
		border-style: none;
		font-weight: bolder;
	}
	
	
.overlay{
	height: 100%;
	width: 100%;
	padding: 0;
	top: 0;
	bottom: 0;
	left:0;
	background-color: black;
	opacity: .85;
	z-index: 900;
	-moz-opacity: .85;
	display: block;
	position: fixed;
	filter: alpha(opacity=1000);
	
}	
	
.load{
	/* margin-top: -450px; */
	z-index: 1000;
	right: 50%;
	position: fixed;
	top: 30%;
	background-position: center;
	color: red;
}	

	
</style>
</head>
<%
	SimpleDateFormat dateSDF = new	SimpleDateFormat("MMMM dd,yyyy");
	SimpleDateFormat dw = new SimpleDateFormat("EEEE");
 	Calendar ss = Calendar.getInstance();
	Date today = ss.getTime();
	String sDate = dateSDF.format(today);
	String Dw = dw.format(today);
%>
<body onload="loading(); updateClock(); setInterval('updateClock()', 1000 )" >

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
	<tr> <td><input checked="checked" onclick="hide();" id="hide-totalmembers" type="checkbox"/> Keep this date & time in view</td> </tr>
 	<tr>
 		<td class="day"><%if(Dw.equals("Sunday")){%> <font id="sun"><% out.println(Dw); %>,</font> 
						  <%}else{%> <font class="mon-sat"><% out.println(Dw); %>,</font><% } %>
		<!-- </td>
 		<td class="timeDate"> --><font class="mon-sat"><% out.println(sDate); %></font><!-- <hr> --></td></tr><!--displaying Date[jm]  -->
	<tr><td class="timeDate"> <span id="clock">&nbsp;</span><!-- <hr> --> </td></tr><!--displaying Time[jm]  -->
	</table>		 
</div>
<% int i = 0; %>
<div id="main" >
		<div id="paging_container7" class="container">
			<div class="page_navigation"></div>
			<table id="theader" >
				<tr >
					<td>
						Municipality -- <span id="total1" ><c:out value="${muntotal}"></c:out></span>
						<% for(int s=0;s<50;s++){ %>
						&nbsp;
						<%} %>
						Total Members --  <span id="total1" ><c:out value="${householdtotal}"></c:out></span>
					</td>
				</tr>
			</table> 
			<div class="content">
				<c:forEach items="${list}" var="l" >
					<div>
						<table border="1" class="research" >
							  <tbody>
								<c:set var = "ctr" value="<%= i++ %>" ></c:set>
									<tr  style="cursor: pointer;" class="accordion" >
										<td class="subheader" ><c:out value="${l.municipality}" ></c:out></td>
										<td class="subheader" ><span id="total2" ><c:out value="${l.total_mun}" ></c:out></span></td>
									</tr>
									<tr class="acc" >
										<td class="contents total2" >Barangay -- Total:<span id="total" > <c:out value="${l.total_brgy}"></c:out> </span></td>
										<td class="contents total2" >Total Members </td>
									</tr>
									 <c:forEach items="${list2}" var="l2" >
												<c:if test="${l2.mun_id == l.mun_id}">
													<tr class="acc"  >
														<td class="contents" ><c:out  value="${l2.brgy_name}"></c:out></td>
														<td class="contents2" ><c:out value="${l2.total_brgy}"></c:out></td>
													</tr>
												</c:if>
									</c:forEach>
									
							 </tbody>
						</table>
					</div>
				</c:forEach>
			</div>
			<div class="page_navigation"></div>
		</div>
</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>


<div class="overlay" id="overlay"></div>
	<div id="div" class="load">
		<img alt="" src="<%=cPath %>/images/loading_transparent.gif"><br>
	</div>

</body>
</html>