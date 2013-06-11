<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    
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
HttpSession session1 = request.getSession(false);
String household_id = (String)session1.getAttribute("household_id");
System.out.println("household_id view_transactionProfile : ="+household_id);

if(session.getAttribute("username")==null){
	System.out.println("username is null parse.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax3.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" href="<%= cPath %>/css/add_transactions_CSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>

<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css" href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
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
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css">
<script type="text/javascript" src="<%= cPath %>/js/add_benificiary.js"></script>
<script type="text/javascript" src="<%=cPath %>/capture-image/captureImg.js"></script>
<script type="text/javascript">

	function uploadfile(){
		var file = document.getElementById("fileid").files[0];
		try{
				//file = files[i];
				//alert(document.fileUpload.filname.files.length);
				var xhr = new XMLHttpRequest();
				xhr.upload.addEventListener("progress", function(e) {
					var pc = parseInt( ((e.loaded /e.total) * 100 )) ;
					progress(pc, $('#progressBar'));
					document.getElementById("progress").innerHTML = pc + " % (  file uploaded out of "+file.length+" )";
					$(function() {
						 $( "#progressbar" ).progressbar({
							 value: pc
						 });
					 });
				}, false);
				
				xhr.onreadystatechange = function(e) {
					if (xhr.readyState == 4) {
						/* if((i+1) < files.length){
							uploadfile(i+1);
							uploadedfile++;
						} */
						alert("Ok na.. hanep na ang buhay basta may hanap buhay,.");
					}
				};
				xhr.open("POST","SampleUpload", true);
				xhr.setRequestHeader("filename", file.size);
				xhr.setRequestHeader("name", "Hello World!");
				xhr.setRequestHeader("Abedejos", "Si remuel broken hearted");
				xhr.send(file);
		}catch(e){
			alert(e.message);
		}
		
	}
	function progress(percent, $element) {
		var progressBarWidth = percent * $element.width() / 100;
		$element.find('div').animate({ width: progressBarWidth }, 500).html(percent + "%&nbsp;");
	}
</script>
<style type="text/css">
	#progressBar {
		width: 400px;
		height: 22px;
		border: 1px solid #111;
		background-color: #292929;
	}
	#progressbar {
		width: 400px;
		height: 22px;
		border: 1px solid #111;
		background-color: #292929;
	}
	
	#progressBar div {
			height: 100%;
			color: #fff;
			text-align: right;
			line-height: 22px; /* same as #progressBar height if we want text middle aligned */
			width: 0;
			background-color: #0099ff;
	}
	.default {
	    background: none repeat scroll 0% 0% rgb(41, 41, 41);
	    border: 1px solid rgb(17, 17, 17);
	    border-radius: 5px 5px 5px 5px;
	    overflow: hidden;
	    box-shadow: 0px 0px 5px rgb(51, 51, 51);
    }
</style>
</head>
<body>
	<input type="file" name="filname" action="/SampleUpload" id="fileid"  />
	<button onclick="uploadfile(0)">upload gula gwapo</button>
	<div id="progress"></div>
	<div id="progressbar" class="default"></div>
	<div id="progressBar" class="default"><div></div></div>
</body>
</html>