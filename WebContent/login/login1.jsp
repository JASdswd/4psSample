<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <% String cPath = request.getContextPath(); %>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
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
	System.out.println(session.getAttribute("username"));
	if(session.getAttribute("username") != null) {
		System.out.println("disconnected!");
%>
		<script type="text/javascript">top.document.location.replace('<%= cPath%>/home/home_page.jsp');</script>
<%
	}else{
		System.out.println("connected!");
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login | 4P's</title>
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	

		<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
		<link rel="stylesheet" href="<%= cPath %>/css/login_style.css"/>
		
		<script type="text/javascript">
			$(function(){
				
				
				var user = $("#user"),
					password = $("#password"),
					allFields = $( [] ).add( user ).add( password ),
					tips = $( ".validateTips" );
				user.change(function(){
					password.val("");
					password.focus();
				});
				if(user.val() == ""){
					user.focus();
					
				}
				else{
					
					password.focus();
				}

				function updateTips( t ) {
					tips
						.text( t )
						.addClass( "ui-state-highlight" );
					setTimeout(function() {
						tips.removeClass( "ui-state-highlight", 1500 );
					}, 500 );
				}

				function checkLength( o, n, min, max ) {
					if ( o.val().length > max || o.val().length < min ) {
						o.addClass( "error" );
						updateTips( "Length of " + n + " must be between " +
							min + " and " + max + "." );
						o.focus();
						return false;
					} else {
						return true;
					}
				}

				function checkRegexp( o, regexp, n ) {
					if ( !( regexp.test( o.val() ) ) ) {
						o.addClass( "error" );
						o.focus();
						updateTips( n );
						return false;
					} else {
						return true;
					}
				}
				
				function validateForm(){
					var bValid = true;
					allFields.removeClass( "error" );
				
					bValid = bValid && checkLength( user, "username", 2, 18 );
					//bValid = bValid && checkLength( password, "password", 2, 16 );
					
					//bValid = bValid && checkRegexp( user, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
					// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
					//bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
		
					return bValid;
				}
				$("#login").button({
					icons : {primary : "ui-icon-unlocked" }
				});
				$("#cancel").button({
					icons : {primary : "ui-icon-cancel" }
				});
				
				$("#login").click(function(){
					return validateForm();
				});
			});
			$(document).ready(function(){
				$(window).unload(function(){});
				
			});
			var sURL = unescape(window.location.pathname);
			function doLoad()
			{
			    // the timeout value should be the same as in the "refresh" meta-tag
			    setTimeout( "refresh()", 600*1000 );
			}

			function refresh()
			{
			    //  This version of the refresh function will cause a new
			    //  entry in the visitor's history.  It is provided for
			    //  those browsers that only support JavaScript 1.0.
			    //
			    window.location.href = sURL;
			}
		</script>
		<script language="JavaScript1.1">
			function refresh(){
				    //  This version does NOT cause an entry in the browser's
				    //  page view history.  Most browsers will always retrieve
				    //  the document from the web-server whether it is already
				    //  in the browsers page-cache or not.
				    //  
				    window.location.replace( sURL );
				}
			//-->
		</script>
			
		<script language="JavaScript1.2">
			function refresh(){
				    //  This version of the refresh function will be invoked
				    //  for browsers that support JavaScript version 1.2
				    //
				    
				    //  The argument to the location.reload function determines
				    //  if the browser should retrieve the document from the
				    //  web-server.  In our example all we need to do is cause
				    //  the JavaScript block in the document body to be
				    //  re-evaluated.  If we needed to pull the document from
				    //  the web-server again (such as where the document contents
				    //  change dynamically) we would pass the argument as 'true'.
				    //  
				    window.location.reload( false );
			}
			//-->
		</script>
		<style type="text/css">
			#main{
				box-shadow: 0px 0px 20px silver;
				-moz-box-shadow: 0px 0px 20px silver;
				-webkit-box-shadow: 0px 0px 20px silver;
			}
		</style>
</head>
<body onload="doLoad()">
<div id="header" class="ui-widget-header">
	<div id="header-content">
		  <div id="logo">
			<img alt="" src="<%= cPath %>/logos/maguindanao-copy.png"/>
		</div>
		<h3>pantawid pamilyang pilipino program</h3>
		<img id="image-logo" alt="4ps.logo" src="<%= cPath %>/image/home.png"> 
	</div>
</div> 
<center>
	<div class="fakewindowcontain">
		<br/>
			<div  id="main" class="ui-widget ui-widget-content ui-corner-all">
				<div class="ui-dialog-content ui-widget-content" id="content">
					<div id="login-title">
						<br/>
						<h3>Login</h3>
					</div>
					<p id="error-handling" class="validateTips"><c:out value="${error}"></c:out></p>
					<form action="<%= cPath %>/Login" method="post">
					<table>
						
						<tr>
							<td><label>Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
							<td><%-- <select name="user" id="user" class="input" style="width: 190px">
									<c:forEach items="${userlist}" var="list">
										<c:if test="${list.username==user}">
											<option selected><c:out value="${list.username}"></c:out></option>
										</c:if>
										<c:if test="${list.username!=user}">
											<option><c:out value="${list.username}"></c:out></option>
										</c:if>
										
									</c:forEach>
								</select> --%>
							 <input name="user" id="user" class="input" autocomplete="off" type="text"/></td>
						</tr>
					
						<tr>
							<td><label>Password:</label></td>
							<td><input name="password" id="password" class="input" type="password" value="" />
							</td>
						</tr>
					</table>
					<br/>
					<button type="submit" id="login">Login</button>
					<button type="reset" id="cancel">Cancel</button>
					</form>
					<br/>
					<!--<div><a href="Login1?user=normal" id="ordinary">Login as an Ordinary User</a></div>
				--></div>
			</div>
	</div>
</center>
<c:if test="${session_hh_id_null}">
	<script type="text/javascript">
		alert("Your session is was expired. You must Login.");
	</script>
</c:if>
<div id="login-footer">
	<div id="footer-copyright">
		Copyright Icot-p ICT Programmers <br/>&nbsp;&nbsp;&nbsp;&nbsp;
		All Rights Reserved. © 2012
	</div>
</div>
</body>
</html>