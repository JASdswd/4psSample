<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String cPath = request.getContextPath(); %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program</title>
<script type="text/javascript" src="<%= cPath %>/js/editreason.js" ></script>
<script type="text/javascript" src="<%= cPath %>/js/validateNumber.js"></script>
<script type="text/javascript">
$(function(){
	$("#yeah").click(function(){
		$("#tago").hide();
		$("#Addbut").hide();
		$("#hek").hide();
		/* $("#hide1").hide(); */
	});
	$("#nope").click(function(){
		$("#tago").show();
		$("#Addbut").show();
		$("#hek").show();
		/* $("#hide1").show(); */
	});
	$("#razon").change(function(){
		var reason=document.getElementById("razon").value;
		if(reason=="Other Reason"){
			xhrGo("GET","AddReason", show, "plain");
		}
	});
	$("#tago").hide();
	$("#Addbut").hide();
	$("#hek").hide();
});
function show(){
	var strbuild="";
	strbuild="<td id='hin' style='font-size:12px;'>Reasons:<textarea name='reason' class='input' id='razon'></textarea></td>";
	document.getElementById("hin").innerHTML=strbuild;
}
function checkall(){
	var emp = Boolean(false);
	var f=document.fields['nwyear'];
	if(f.value==""){
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
	else{
		var log = window.confirm("Are you sure about the data you've enter ?");
		if(log == true){
			return true;
		}
		
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
#razon{
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
<div align="center">
	<form action="EditTransaction" method="post" name="fields" onsubmit="javascript: return checkall();">
	<input name="household_id" value="${id}" type="hidden">
	<input name="month" value="${month}" type="hidden">
	<input name="year" value="${year}" type="hidden">
	<input name="reason_id" type="hidden" value="0">
	<table id="tablet">
		<tr>
			<td class="a" align="right">Month:</td>
			<td class="a"><select name="nwmonth" style="width: 183px;" class="input">
				<c:if test="${month==1}">
					<option selected="selected" value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==2}">
					<option selected="selected" value="2">FEBRARY</option>
					<option value="1">JANUARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==3}">
					<option selected="selected" value="3">MARCH</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==4}">
					<option selected="selected" value="4">APRIL</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==5}">
					<option selected="selected" value="5">MAY</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==6}">
					<option selected="selected" value="6">JUNE</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==7}">
					<option selected="selected" value="7">JULY</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==8}">
					<option selected="selected" value="8">AUGUST</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==9}">
					<option selected="selected" value="9">SEPTEMBER</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==10}">
					<option selected="selected" value="10">OCTOBER</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="11">NOVEMBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==11}">
					<option selected="selected" value="11">NOVEMBER</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="12">DECEMBER</option>
				</c:if>
				<c:if test="${month==12}">
					<option selected="selected" value="12">DECEMBER</option>
					<option value="1">JANUARY</option>
					<option value="2">FEBRARY</option>
					<option value="3">MARCH</option>
					<option value="4">APRIL</option>
					<option value="5">MAY</option>
					<option value="6">JUNE</option>
					<option value="7">JULY</option>
					<option value="8">AUGUST</option>
					<option value="9">SEPTEMBER</option>
					<option value="10">OCTOBER</option>
					<option value="11">NOVEMBER</option>
				</c:if>
			</select></td>
		</tr>
		<tr>
			<td class="a" align="right">Year:</td>
			<td class="a"><input name="nwyear" value="${year}" style="width: 171px;" class="input"></td>
		</tr>
		<tr>
			<td class="a" align="right">Amount:</td>
			<td class="a"><input name="amount" value="${amount}" style="width: 171px;" class="input" onkeypress="return numbersonly1(event,false);"></td>
		</tr>
		<tr>
			<td class="a">Cash Ready For Release:</td>
			<c:if test="${recieve==1}">
			<td class="a"><input class="a" type="radio" name="bol" checked="checked" value="1" id="yeah"/>YES
			<input type="radio" value="3" name="bol" id="nope" />NO</td>
			</c:if>
			<c:if test="${recieve==3}">
				<td class="a"><input class="a" type="radio" name="bol" value="1" id="yeah"/>YES
				<input type="radio" value="3" name="bol" id="nope" checked="checked" />NO</td>
			</c:if>
		</tr>
		<tr>
			<td colspan="2" class="a" id="hek" align="center"><p class="a">Reasons of Unable to Avail the Allowance</p></td>
		</tr>
		<tr id="tago">
			<td align="center" class="a" style="font-size: 12px;font-family:monospace;" id="hin">Reasons:<select name="reason" id="razon" style="font-size: 11px;font-family:monospace;width:150px;" class="input" >
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
				<td><a href="#" id="Addbut" >[+]</a></td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="a" colspan="2" align="center"><button type="submit" class="a">Edit Transaction</button> </td>
			</tr>
			</tfoot>
	</table>
	</form>
</div>
</body>
</html>