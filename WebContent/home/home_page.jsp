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
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<script src="<%= cPath %>/pro_drop_1/stuHover.js" type="text/javascript"></script>
<script type="text/javascript" src="<%= cPath %>/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/ddaccordion.js"></script>
<script type="text/javascript" src="<%= cPath %>/ddaccordion_init.js"></script>
<script type="text/javascript" src="<%= cPath %>/hide_show.js"></script>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<script src="<%= cPath %>/development-bundle/jquery-1.6.2.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.core.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.mouse.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.draggable.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.position.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.resizable.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.dialog.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.core.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.fade.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.effects.explode.js"></script>
<link rel="stylesheet" href="<%=cPath %>/development-bundle/demos/demos.css">
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>
<script type="text/javascript">
$(document).ready(function(){
	$(window).unload(function(){});
	$("#welcome_note").hide();
	var pop_up = document.getElementById("pop_up").value;
	if(pop_up == "Ajaw vah,ajaw woiest"){
		$("#welcome_note").dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:170,
			width:280,
			modal: true,
			title: "4PS Welcome note",
			buttons:{
				"Close": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
});
	function confirmlog(){
			var log = window.confirm("Anata wa roguauto shite yoroshiidesu ka?");
			if(log == true){
				
				return true;
			}
			
			return false;
	}
	
	$(function() {
		
		$( "#dialog:ui-dialog" ).dialog( "destroy" );

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

</script>
<style type="text/css">
	#dialog-confirm{
		display: none;
	}
	p.wnfont{
		margin-left: 110px;
		margin-top: -65px;
		font-family: helvetica, sans-serif;
		font-size: 15px;
		letter-spacing: 2px;
		text-transform: capitalize;
		text-shadow: 4px 4px 4px #666;
	}
	.im{
		border: 1px solid #000;
		box-shadow: 0px 0px 20px #aaa;
		-moz-box-shadow: 0px 0px 20px #aaa;
		-webkit-box-shadow: 0px 0px 20px #aaa;
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

<input type="hidden" id="pop_up" value='<c:out value="${popup}"></c:out>'/>
<div id="page-wrap">
<div id="header" >
<div id="logo" >
		<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
		<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu" >

<jsp:include page="menu.jsp"></jsp:include>

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
<div id="main" >
		<img alt="home" src="<%=cPath %>/image/homepage.jpg" width="900px;" height="500px;" >
</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
<div id="welcome_note">
<c:choose>
	<c:when test="${photohead_exist}">
		<img class="im" alt="User's photo" src="<%= cPath %>/GetPhotoUser?ID=${user_id}&user_role=${user_role}&sizeImage=dkFjGhsdkTRsld734snk8JDSK" /> 
	</c:when>
	<c:otherwise>
		<img class="im" alt="photo of head" height="80" class="HF_photo" src="<%=cPath %>/image/head.jpg">
	</c:otherwise>
</c:choose>	
<p class="wnfont">Welcome <c:out value="${fname }"></c:out></p>
</div>
</body>
</html>