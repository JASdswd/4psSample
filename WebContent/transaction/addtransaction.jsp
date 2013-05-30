<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	String cpath = request.getContextPath();

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
<link type="text/css" rel="stylesheet" href="<%= cPath %>/css/addtransaction.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program</title>
<script type="text/javascript" src="<%= cPath %>/js/addreason.js" ></script>
<script type="text/javascript" src="<%= cPath %>/js/validateNumber.js"></script>
<script type="text/javascript">
$(function(){
	$("#sey").click(function(){
		$("#hid").hide();
		$("#hide").hide();
		$("#butAd").hide();
	});
	$("#no").click(function(){
		$("#hid").show();
		$("#hide").show();
		$("#butAd").show();
	});
	$("#alliby").change(function(){
		var reason=document.getElementById("alliby").value;
		if(reason=="Other Reason"){
			xhrGo("GET","AddReason", show, "plain");
		}
	});
	$("#hid").hide();
	$("#hide").hide();
	$("#butAd").hide();
});

function show(){
	var strbuild="";
	strbuild="<td id='dos' style='font-size:12px;'>Reasons:<textarea name='reason' class='input' id='alliby'></textarea></td>";
	document.getElementById("dos").innerHTML=strbuild;
}

function checkall(){
	var emp = Boolean(false);
	var f=document.fields['months'];
	if(f.value=="Month"){
		f.style.borderColor="red";
		f.focus();
		emp=true;
	}
	var f=document.fields['years'];
	if(f.value=="Year"){
		f.style.borderColor="red";
		f.focus();
		emp=true;
	}
	var f=document.fields['amount'];
	if(f.value==""){
		f.style.borderColor="red";
		f.focus();
		emp=true;
	}
	if(emp == true){
		alert("Fill-up all Fields Color Red");
		return false;
	}
	
	return true;
}
</script>
<style type="text/css">
.a{
	font-family: monospace;
	font-size: 12px;
}
.b{
	font-family: monospace;
	font-size: 9px;
}
#alliby{
	width: 150px;
}
.input{
	margin-bottom: 5px;
	border: 1px solid #8FA9BC;
	padding: 5px 3px 5px 10px;
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
</style>
</head>
<body>
<div align="center" >
	<form action="Addtransaction" method="post" id="execute" name="fields" onsubmit="javascript: return checkall();">
	<input class="input" size="26" name="day" type="hidden" value="" readonly="readonly"/>
	<input class="input" size="26" name="time" type="hidden" value="" readonly="readonly"/>
	<c:forEach items="${lists}" var="list">
		<input type="hidden" value="${list.head_name}" id="head_name" name="head_name" />
		<input type="hidden" value="${list.brgy}" id="brgy" name="brgy"/>
		<input type="hidden" value="${list.municipal}" id="municipal" name="municipal" />
	</c:forEach>
		<table align="center" id="taBle">
			<tr>
				<td colspan="2"><input type="hidden" class="input" name="reason_id" id="reason_id" value="${reason_id}" /></td>
			</tr>
			<tr>
				<td align="center" class="a">Household_ID:</td>
				<td class="a" ><input name="household_id" size="26" class="input" value="${household_id}" id="house_id" readonly="readonly" /></td>
			</tr>
			<tr>
				<td align="center" class="a">Month:</td>
				<td class="a"><select class="input"  name="months" id="m" style="width: 183px;">
					<option selected="selected" disabled="disabled" >Month</option>
					<option>JANUARY</option>
					<option>FEBRARY</option>
					<option>MARCH</option>
					<option>APRIL</option>
					<option>MAY</option>
					<option>JUNE</option>
					<option>JULY</option>
					<option>AUGUST</option>
					<option>SEPTEMBER</option>
					<option>OCTOBER</option>
					<option>NOVEMBER</option>
					<option>DECEMBER</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center" class="a">Year:</td>
				<td class="a">
					<input name="years" id="y" size="26" class="input" value="${year}" readonly="readonly">
				</td>
			</tr>
			<%-- <tr align="center">
				<td class="a">Time:</td>
				<td class="a" align="left"><input class="input" size="26" name="time" value="${time}" readonly="readonly"/></td>
			</tr> --%>
			<tr>
				<td align="center" class="a">Amount:</td>
				<td class="a"><input class="input" size="26" name="amount" onkeypress="return numbersonly1(event,false);" autocomplete="off"></td>
			</tr>
			<tr>
				<td align="center" class="a">Cash Ready For Release:</td>
				<td class="a"><input class="a" type="radio" name="bol" checked="checked" value="1" id="sey" onclick="yes();"/>YES
				<input type="radio" value="3" name="bol" onclick="no();" id="no" />NO</td>
			</tr>
			<tr>
				<td colspan="2" class="a" id="hid" align="center"><p class="a">Reasons of Unable to Avail the Allowance</p></td>
			</tr>	
			<tr id="hide">
				<td align="center" class="a" id="dos" style="font-size: 12px;font-family:monospace;" >Reasons:<select name="reason" id="alliby" style="font-size: 11px;font-family:monospace;width:150px;"  class="input">
					<option>Pregnant women unable to avail Pre-and Post-natal care and be attended during childbirth by a trained health professional.</option >
					<option>Parents unable to attend Family Development Sessions(FDS).</option>
					<option>0-5 year old children unable to receive regular preventive health checks-ups and vaccines.</option>
					<option>3-5 years old children unable to attend day care or pre-school classes at least 85percent of the time.</option>
					<option>6-14 years old children unable to enroll elementary or high school.</option>
					<option>6-14 years old children unable to attend school at least 85percent of the time.</option>
					<option>6-14 years old children unable to receive deworming pills twice a year.</option>
					<option>Other Reason</option>
				</select>
				</td>
				<td><a href="#" id="butAd" >[+]</a></td>
			</tr>
			<tfoot>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="a" colspan="2" align="center"><button type="submit" class="a">ADD Transaction</button> </td>
			</tr>
			</tfoot>
		</table>
	</form>
</div>
</body>
</html>