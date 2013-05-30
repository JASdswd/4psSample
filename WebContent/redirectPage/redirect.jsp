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
	<script src="<%= cPath %>/development-bundle/ui/jquery.effects.blind.js"></script>
	<script src="<%= cPath %>/development-bundle/ui/jquery.effects.explode.js"></script>
	<link rel="stylesheet" href="../development-bundle/demos/demos.css">
	
	<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(window).unload(function(){});
		
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
		/* $( "#dialog-confirm" ).dialog({
			autoOpen: false,
			show: "explode",
			hide: "explode",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});

		$( "#privacy" ).click(function() {
			$( "#dialog-confirm" ).dialog( "open" );
			return false;
		}); */

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
	#main{
		height: 570px;
	}
	.ui-dialog{
		font-size: 12px;
	}
	#maindiv{
		margin-top: 60px;
		height: 300px;
		width: 600px;
		padding: 20px;
	}
	#top{
		margin-top: 50px;
		margin-bottom: 50px;
	}
	h3{
		font-family: sans-serif;
		font-size: 17px;
	}
</style>
</head>

<body >

<div id="page-wrap">
<div id="header" >
<div id="logo" >
		<img id="logo_image" alt="" src="<%=cPath %>/images/headers.jpg">
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu" >

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>

<div id="main" >
<center>
	<div class="fakewindowcontain">
		<br/>
			<div  id="maindiv" class="ui-widget ui-widget-content ui-corner-all">
				<div id="top">
					<h1>ERROR 404</h1><br/>
					<hr/>
				</div>
				<div id="bottom">
					<h3>The page you requested was not found.</h3>
				</div>
			</div>
	</div>
</center>	
</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
</body>
</html>