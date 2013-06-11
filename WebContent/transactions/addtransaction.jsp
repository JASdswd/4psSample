<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String cPath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="<%= cPath %>/css/addtransaction.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Transaction</title>
<%--
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js" ></script>--%>
<script type="text/javascript">
$(document).ready(function(){
	$("#m").click(function(){
		var mon=document.getElementById("m").value;
		document.getElementById("mon").value=mon;
	});
	$("#y").click(function(){
		var y=document.getElementById("y").value;
		document.getElementById("year").value=y;
	});

});
function checkall(){
	alert("heh");
	
	return false;
}
</script>
</head>
<body>
<div align="center">
	<form action="Addtransaction" method="post" id="execute" name="fields" onsubmit="javascript: return checkall();">
		<table>
			<thead>
				<tr>
					<th colspan="3" align="center">Add Transaction</th>
				</tr>
				<tr>
					<th colspan="3" align="center"><div id="dv"><img alt="sampleImage" class="HF_photo" src="<%=cPath %>/ViewImage?view_id=${id}" /></div></th>
				</tr>
				<tr>
					<th colspan="3" align="center"><select name="month" id="m">
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
						<select name="year" id="y">
							<option>2011</option>
							<option>2012</option>
						</select>
					</th>
				</tr>
			</thead>
			<tr>
				<td>HouseHold_ID:</td>
				<td><input name="household_id" value="${id}" readonly="readonly"/></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2"><p>Cash not received due
				in the following reasons:</p></td>
			</tr>	
			<tr>
				<td><input name="months" id="mon" value="" readonly="readonly" size="8" maxlength="9"></td>
				<td><input name="years" id="year" size="4" value="" maxlength="4" readonly="readonly" ></td>
				<td colspan=""><input name="reason" id="ys"/></td>
			</tr>
			<tr>
				<td align="center" colspan="3"><button type="submit">ADD</button> </td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>