<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String cPath = request.getContextPath(); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",-1);
%>
<html>
<%	
	String cpath = request.getContextPath();
if(session.getAttribute("username") ==null){
	System.out.println("username is null about.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}

if(session.getAttribute("username") == null) {
	System.out.println("disconnected!");
%>
	<script type="text/javascript">top.document.location.replace('<%=cpath%>');</script>
<%
}else{
	System.out.println("connected!");
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program</title>

<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/about_us.css" />
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/slideshow.css" />
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css"/>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
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

function showDialog(){
	
	
	
}


jQuery(document).ready(function () {
    $("#slideshow img:first").addClass("active");
    setInterval("slideshow()", 3000);
    $("#dialog-confirm").hide();
   });
   function slideshow() {
      var $active = $("#slideshow .active");
      var $next = ($("#slideshow .active").next().length > 0) ? $("#slideshow .active").next() : $("#slideshow img:first");
      $active.fadeOut(function(){
      $active.removeClass("active");
      $next.fadeIn().addClass("active");
      });
      
    }
 

</script>
<style type="text/css">
	#page-wrap{
		margin-top: 0;
	}
	body {
	margin-top: 0;
}
</style>
</head>
<body>


<div id="page-wrap">
<div id="header" >
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu" >

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>


<div id="main" >
	
<p id="p1" ><b>Pantawid Pamilyang Pilpino Program</b></p>


<div id="dept-seal" >
	<b class="dept" >Partner Agencies</b><br/><br/>
	<img alt="LandBank" src="<%= cPath%>/image/Landbank.png"><br/>
	<img class="doh" alt="DOH" src="<%= cPath%>/image/doh.jpeg"><br/><br/>
	<img alt="DEPED" src="<%= cPath%>/image/deped.jpeg"><br/><br>
	<img class="napc" alt="NAPC" src="<%= cPath%>/image/Napc.jpg"><br/><br/>
	<img alt="DILG" src="<%= cPath%>/image/dilg.jpeg"><br/>
</div>


<div id="page1" >
	<div id="slideshow" >
		<img  class="floatingleft" alt="pics1" src="<%= cPath%>/image/4ps4.jpg" />
		<img  class="floatingleft" alt="pics2" src="<%= cPath%>/image/img2.jpg" />
		<img  class="floatingleft" alt="pics3" src="<%= cPath%>/image/img3.jpg" />
		<img  class="floatingleft" alt="pics4" src="<%= cPath%>/image/img4.jpg" />
		<img  class="floatingleft" alt="pics5" src="<%= cPath%>/image/img5.jpg" />
		<img  class="floatingleft" alt="pics6" src="<%= cPath%>/image/img1.png" />
	</div>
	
	<p style="overflow: auto;" align="left"  >The <b> Pantawid Pamilyang Pilipino Program (4Ps)</b> is a program by the <b> Department of Social Welfare and Development </b>  that aims to reduce poverty by providing qualified families conditional cash grants to improve their health, nutrition and education particularly of children aged 0-14.</p>
	<p>Partner agencies include the <b>Department of Health</b>, the <b>Department of Education</b>, the  <b>Department of the Interior and Local Government</b> , the <b>National Anti-Poverty Commission</b> and <b>Landbank.</b> </p>
<br/>
<p>Families must fulfill the following conditions to be eligible for the program:</p>
<ul  type="square" >
	<li>Residing in one of the poorest municipalities based on the 2003 Small Area Estimates of the Philippine National Statistical Coordination Board (NSCB)</li>
	<li>Are living below or equal the provincial poverty threshold</li>
	<li>Have a pregnant woman or children 14 years old and below</li>
	<li>Be able to meet the conditions specified in the program, namely:
		<ol>
			<li>Pregnant women must avail pre-and-post-natal care and be attended during childbirth by a health professional</li>
			<li>Parents must attend responsible parenthood, parent effectiveness and mother's classes.</li>
			<li>Children below 5 years old must receive regular preventive health check-ups and vaccines</li>
			<li>Children from 3-5 years old must have an attendance rate of 85% or higher in preschool or day care classes</li>
			<li>Children from 6-14 years old must enroll, and have an attendance rate of 85% or higher in elementary and/or high school classes</li>
			<li>Children 6-14 years old must receive deworming pills twice a year.</li>
		</ol>
	</li>	
</ul>

<b style="color:green" >Objectives:</b><br/><br/>
<b>Social Assistance</b>
<ul>
	<li>Provide assistance to the poor to alleviate their immediate needs (short term).</li>
</ul>

<b>Social Development</b>
<ul>
	<li>Break the intergenerational cycle of poverty through investment in human capital i.e., education, health and nutrition (long term).</li>
</ul>
<br/>
<b style="color:green" >Program Coverage:</b><br/>
<p> <b>Pantawid Pamilya</b> operates in <b>80</b> provinces covering <b>734</b> municipalities and <b>62</b> key cities. 
The Pantawid Pamilya targets to cover 2.3 million households by end of 2011.  </p>
<br/>
<b style="color:green" >Program Package:</b><br/>
<p><b>Pantawid Pamilya</b> provides cash grants to the beneficiaries to wit:</p>
<ul>
	<li>P6,000 a year or P500 per month per household for health and nutrition expenses; and</li>
	<li>P3000 for one school year or 10 months or P300/month per child for educational expenses. A maximum of three children per household is allowed.</li>
</ul>

<p>A household with three qualified children receives a subsidy of P1,400/month during the school year or P15,000 annually as long as they comply with the conditionalities. The cash grants shall be received by the most responsible person in the household, usually the mother, through a Land Bank cash card.</p>
<p>In cases where payment through cash card is not feasible, the beneficiaries shall be provided their cash grants through an alternative payment scheme such as over-the-counter transactions from the nearest Landbank branch or offsite payments through Landbank. </p>

<br/>
<br/>
<hr/>
<hr/>
</div>

</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->

<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>


</body>
</html>