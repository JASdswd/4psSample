<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
    
      <% String cPath = request.getContextPath(); %>
      <%@ page import="java.text.*" %>
      <%@ page import="java.util.*" %>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pantawid Pamilyang Pilipino Program Total Release Reports</title>

<link href="<%= cPath %>/css/table.css" type="text/css" rel="stylesheet" /> 
<link href="<%= cPath %>/css/print.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.filer.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.pager.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter-2.0.3.js" ></script>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css"  href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css"/>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>

<style type="text/css">
#thead{
	background-color: white;
}

@media print {
	#printer, #div, .clickable, .phr{
		display: none;
	}
	.active{
		color:black !important;
		display: inline !important;
	}
	
	@page{
		size:auto;
		margin:0mm;
		display:none;
	}
	
	
	
}

</style> 

<script type="text/javascript">

$(document).ready(function() {
	$("#printData").button({
		icons : {primary : "ui-icon-circle-check" }
	});
});

function CallPrint(){
	
	document.URL
	var url = document.URL;
	window.print();
	var urlArr = url.split("/");
	alert("url:"+url+" url len:"+urlArr.length);
	url.style.display = "none";
	url.style.visibility = "hidden";
	
}


</script>

</head>
<body onload="Load();" >

	<div id="printer" >
		<button id="printData" onclick="window.print();" >Print</button>
	</div>
	
	<hr id="div" />
	
<div id="headers" >
	<span>Pantawid Pamilyang Pilipino Program (4PS)</span><br/>
	<span>Department of Social Welfare and Development</span>
</div>


<div id="contain" >

<div class="tRelease" >
	<div class="firstd" ><label for="cash_total" >Starting Balance:</label></div>
	<div class="secd" ><label class="totals" ><u><c:out value="P ${cash_total}.00"></c:out></u></label></div>
</div>

<div id="date" >
	<label for="datePrinted" >Date Printed:</label>
	<u id="datePrinted" >
	<% SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
	%>
	<b><%= sdf.format(date) %></b>
	</u>

</div>

</div>
	<%-- <c:choose>
		<c:when test="${size1 > 0}"> --%>
			<div class="tRelease" >
				<div class="firstd" ><label for="total_release" >Total Payments:</label></div>
				<div class="secd2" ><label class="totals" ><u><c:out value="P ${total_release}.00"></c:out></u></label></div>
			</div>
	<%-- 	</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${size2 > 0}"> --%>
			<div class="tRelease" >
				<div class="firstd1 firstd" ><label for="total_notrelease" >Cash in Hand:</label></div>
				<div class="secd3" ><label class="totals" ><u><c:out value="P ${total_notrelease}.00"></c:out></u></label></div>
			</div>
	<%-- 	</c:when>
	</c:choose> --%><br/>
<br/>
<c:choose>
		
	<c:when test="${size1 > 0}">
		<script> document.title = "Pantawid Pamilyang Pilipino Program Total Released Reports"; </script>
		
<div class="dh2" >

<div class="ext" >
		<label> </label>&nbsp;<span id="dtp" > </span><br/>
		<label></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="munp" ></span>
</div>

<h2>CCT Payments</h2>
</div>
		<div id="div1" >
			
			<c:if test="${gsize == 1}">
			
					<% int row = 1; %>
						<table id="display" border="1"  class="paginated display sortable"  >
							<thead id="thead" >
								<tr style="border-style: double;" >
									<td colspan="9" class="title" >Released to Grantee</td>
								</tr>	
								<tr  style="border-style: double;" >
									<th>#</th>
									<th  >Household ID No.</th>
									<th id="head" >Name of Grantee</th>
									<th  >Barangay</th>
									<th class="min" >Municipality</th>
									<!-- <th  >PhilHealth ID No.</th> -->
									<th class="min" >Date of Transaction</th>
									<th class="fix" >Date Received</th>
									<th  class="fix" >Time Received</th>
									<th  >Amount Received</th>
									<!-- <th>Substitute</th> -->
								</tr>
							</thead>
							
							<tbody id="tbody" >
							<%int numrow = 0;int in = 0; %>
							<c:set var="sub" value="0" ></c:set>
							<c:set var="sub_brought" value="0" ></c:set>
								<c:forEach items="${data}" var="list" >
									<c:choose>
										<c:when test="${list.sub == 0}">
											<%numrow++; %>
											<tr>
												<td><%= row++ %></td>
												<td><c:out value="${list.household_id}"></c:out></td>
												<td><%-- <%int c=0; %>
													<c:forTokens items="${list.head_name}" var="name" delims="*">
														<c:set var="mname" value="<%= c++ %>" ></c:set>
														<c:if test="${mname == 1}">
															<%int p=0; %>
															
															<c:forTokens items="${name}" delims="" >
																<c:set var="p1" value="<%= p++ %>" ></c:set>
																<c:out value="${mname}"></c:out>
																<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
																<c:out value="${msplit}."></c:out>
															</c:forTokens> 
															
														</c:if>
														<c:if test="${mname < 1 || mname > 1}">
															<c:out value="${name}"></c:out>
														</c:if> 
													</c:forTokens> --%>
													<%-- <label style="text-transform: capitalize;color: black;font-weight: bold;f"><c:out value="${list.head_name}"></c:out></label> --%>
													<label style="text-transform: capitalize;color: black;font-weight: bold;f">
														<%-- <c:out value="${list.head_name}"></c:out> --%>
														 <%int c=0,d=0; %>
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname" value="<%= c++ %>" ></c:set>
														</c:forTokens>	
														
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname2" value="<%= d++ %>" ></c:set>
															<c:choose>
																<c:when test="${mname2 == mname}">
																	<%int p=0; %>
																	<c:forTokens items="${name}" delims="" >
																		<c:set var="p1" value="<%= p++ %>" ></c:set>
																		<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
																		<c:out value="${msplit}."></c:out>
																	</c:forTokens> 
																</c:when>
																<c:otherwise>
																	<c:out value="${name}"></c:out>
																</c:otherwise>
															</c:choose>
														</c:forTokens>	
													</label>
												</td>
												<td><c:out value="${list.brgy}"></c:out></td>
												<td><c:out value="${list.municipality}"></c:out></td>
												<%-- <td><c:out value="${list.philhealth_id}"></c:out></td> --%>
												<td style="font-size: 14px;" ><c:out value="${list.date_coverage}"></c:out></td>
												<td><c:out value="${list.date_receive}"></c:out></td>
												<td><c:out value="${list.time_receive}"></c:out></td>
												<td class="amount"><div> <div style="float:left" >P</div>
													<div style="float:right">
													 
														<%-- <fmt:formatNumber pattern="0.00" type="currency"  > --%>
															<c:out value="${list.amount_receive}"></c:out>.00
															
															<%int enter = 0; %>
															
															<c:set var="sub2" value="0" ></c:set>
															<c:set var="sub3" value="0" ></c:set>
															<c:forTokens items="${list.amount_receive}" delims="," var="amount" >
																<%if(enter == 0){ %>
																	<c:set var="sub2" value="${amount}" ></c:set>
																<% enter = 1; }else{ %>
																	<c:set var="sub3" value="${amount}" ></c:set>
																<% enter =2;} %>
															</c:forTokens>
															
															<% if(enter == 1){%>
																<c:set var="subt" value="${sub2}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%}else if(enter == 2){ %>
																<c:set var="subt" value="${sub2}${sub3}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%} %>
														<%-- </fmt:formatNumber> --%>
													</div> </div> 
													
												</td>
												<%-- <c:choose>
													<c:when test="${list.sub == 0}">
														<td>No</td>
													</c:when>
													<c:otherwise>
														<td>Yes</td>
													</c:otherwise>
												</c:choose> --%>
											</tr>

										<%
											if(row == 60){
												in=1;
										%>
										
										<tr style="border: 0px;border-left: 0px;border-right: 0px;" >
											<c:set value="<%= numrow %>" var="trows" ></c:set>
											<td style="border-color: white;border-left-color: white" colspan="5" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${fgsize}"></c:out></td>
											<td style="border:0px;text-align: right;" colspan="2">Total for this page:
												<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
											</td>
											<td colspan="2" style="border:0px;text-align: right;font-weight: bold;" >
											<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
											</td>
											<%numrow =0; %>
											<c:set var="sub" value="0" ></c:set>
										</tr>
											<tr  style="border-color: white;background-color: white;">
											 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
											 	Sub-total brought forward:
											 	</td>
											 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;font-weight: bold;" colspan="2">
											 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
													<c:out value="${sub_brought}"></c:out>
													</fmt:formatNumber> </u>
											 	</td>
											 </tr>
										<%
											}
										%>

										<%if(in == 1 && numrow == 71){ %>
											<tr style="border: 0px;border-left: 0px;border-right: 0px;" >
											<c:set value="<%= numrow %>" var="trows" ></c:set>
											<td style="border-color: white;border-left-color: white" colspan="5" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${fgsize}"></c:out></td>
											<td style="border:0px;text-align: right;" colspan="2">Total for this page:
												<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
											</td>
											<td colspan="2" style="font-weight: bold;border:0px;text-align: right;" >
											<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
											</td>
											<%numrow =0; %>
											<c:set var="sub" value="0" ></c:set>
										</tr>
											<tr  style="border-color: white;background-color: white;">
											 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
											 	Sub-total brought forward:
											 	</td>
											 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
											 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
													<c:out value="${sub_brought}"></c:out>
													</fmt:formatNumber> </u>
											 	</td>
											 </tr>
										<%} %>
										</c:when>
										
									</c:choose>
								</c:forEach>
							</tbody>
							
							<tfoot id="tfoot" >
						
							</tfoot>
							  <tr  style="border-color: white;background-color: white;" >
							 <c:set value="<%= numrow %>" var="trows" ></c:set>
							<td valign="top" style="border-color: white;" colspan="5" >Row(s):<c:out value="<%= numrow-- %>"></c:out>/<c:out value="${fgsize}"></c:out> </td>
							<td  style="border-color: white;border:0px;text-align: right;" colspan="2">Total for this page:
							<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
							</td>
							<td  style="font-weight: bold;text-align: right;border:0px; " colspan="2">
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
							</td>
				 </tr>
				 <tr  style="border-color: white;background-color: white;">
				 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
				 	Sub-total brought forward:
				 	</td>
				 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
				 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
						<c:out value="${sub_brought}"></c:out>
						</fmt:formatNumber> </u>
				 	</td>
				 </tr>
						</table>
						
						<c:set var="last_numrowof1st_tbl" value="<%= numrow%>" ></c:set>
			</c:if>
					
					
					<c:if test="${rsize == 1}">
					
							<br/><br/>
  							<% int row2= 1;int numrow = 0; %>
  							<c:set var="sub" value="0" ></c:set>
						<table id="display3" border="1"  class="paginated display sortable"  >
							<thead id="thead" >
								<tr  style="border-style: double;" >
									<td colspan="9" class="title" >Released to Representative</td>
								</tr>
								<tr  style="border-style: double;" >
									<th>#</th>
									<th  >Household ID No.</th>
									<th id="head" >Name of Grantee</th>
									<th  >Barangay</th>
									<th class="min" >Municipality</th>
									<!-- <th  >PhilHealth ID No.</th> -->
									<th class="min" >Date of Transaction</th>
									<th class="fix"  >Date Received</th>
									<th class="fix"  >Time Received</th>
									<th  >Amount Received</th>
									<!-- <th>Substitute</th> -->
								</tr>
							</thead>
							
							<tbody id="tbody3" >
								<c:set var="new_numrow" value="${last_numrowof1st_tbl+7}" ></c:set>
								<c:set var="sub_brought" value="0" ></c:set>
								<%int in=0; %>
								<c:forEach items="${data}" var="list" >
									<c:choose>
										<c:when test="${list.sub != 0}">
										<c:set var="new_numrow" value="${new_numrow+1}" ></c:set>
										<%numrow++; %>
											<tr>
												<td><%= row2++ %></td>
												<td><c:out value="${list.household_id}"></c:out></td>
												<td><%-- <%int c=0; %>
													<c:forTokens items="${list.head_name}" var="name" delims="*">
														<c:set var="mname" value="<%= c++ %>" ></c:set>
														<c:if test="${mname == 1}">
															<%int p=0; %>
															
															<c:forTokens items="${name}" delims="" >
																<c:set var="p1" value="<%= p++ %>" ></c:set>
																<c:out value="${mname}"></c:out>
																<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
																<c:out value="${msplit}."></c:out>
															</c:forTokens> 
															
														</c:if>
														<c:if test="${mname < 1 || mname > 1}">
															<c:out value="${name}"></c:out>
														</c:if> 
													</c:forTokens> --%>
													<label style="text-transform: capitalize;color: black;font-weight: bold;f">
														<%-- <c:out value="${list.head_name}"></c:out> --%>
														 <%int c=0,d=0; %>
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname" value="<%= c++ %>" ></c:set>
														</c:forTokens>	
														
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname2" value="<%= d++ %>" ></c:set>
															<c:choose>
																<c:when test="${mname2 == mname}">
																	<%int p=0; %>
																	<c:forTokens items="${name}" delims="" >
																		<c:set var="p1" value="<%= p++ %>" ></c:set>
																		<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
																		<c:out value="${msplit}."></c:out>
																	</c:forTokens> 
																</c:when>
																<c:otherwise>
																	<c:out value="${name}"></c:out>
																</c:otherwise>
															</c:choose>
														</c:forTokens>	
													</label>
												</td>
												<td><c:out value="${list.brgy}"></c:out></td>
												<td><c:out value="${list.municipality}"></c:out></td> 
												<%-- <td><c:out value="${list.philhealth_id}"></c:out></td> --%>
												<td style="font-size: 14px;" ><c:out value="${list.date_coverage}"></c:out></td> 
												<td><c:out value="${list.date_receive}"></c:out></td>
												<td><c:out value="${list.time_receive}"></c:out></td>
												<td class="amount"><div> <div style="float:left" >P</div>
													<div style="float:right">
													 
														<%-- <fmt:formatNumber pattern="0.00" type="currency"  > --%>
															<c:out value="${list.amount_receive}"></c:out>.00
															<%int enter = 0; %>
															
															<c:set var="sub2" value="0" ></c:set>
															<c:set var="sub3" value="0" ></c:set>
															<c:forTokens items="${list.amount_receive}" delims="," var="amount" >
																<%if(enter == 0){ %>
																	<c:set var="sub2" value="${amount}" ></c:set>
																<% enter = 1; }else{ %>
																	<c:set var="sub3" value="${amount}" ></c:set>
																<% enter =2;} %>
															</c:forTokens>
															
															<% if(enter == 1){%>
																<c:set var="subt" value="${sub2}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%}else if(enter == 2){ %>
																<c:set var="subt" value="${sub2}${sub3}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%} %>
														<%-- </fmt:formatNumber> --%>
													</div> </div> 
												</td>
												<%-- <c:choose>
													<c:when test="${list.sub == 0}">
														<td>No</td>
													</c:when>
													<c:otherwise>
														<td>Yes</td>
													</c:otherwise>
												</c:choose> --%>
											</tr>
											
											<%if(in == 0){ %>
											<c:if test="${new_numrow == 69}">
												<%in = 1; %>
												<tr style="border: 0px;border-left: 0px;border-right: 0px;" >
												<c:set value="<%= numrow %>" var="trows" ></c:set>
												<td style="border-color: white;border-left-color: white" colspan="5" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${frsize}"></c:out></td>
												<td style="border:0px;text-align: right;" colspan="2">Total for this page:
												
												<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
												</td>
												<td style="font-weight: bold;text-align: right;border:0px;" colspan="2" >
												<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
												</td>
												<%numrow =0; %>
												<c:set var="new_numrow" value="0" ></c:set>
												<c:set var="sub" value="0" ></c:set>
											</tr>
												<tr  style="border-color: white;background-color: white;">
											 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
											 	Sub-total brought forward:
											 	</td>
											 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
											 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
													<c:out value="${sub_brought}"></c:out>
													</fmt:formatNumber> </u>
											 	</td>
											 </tr>
											 
											
											</c:if>
											<%} %>
											 <%if(in == 1){ %>
											 	<c:if test="${new_numrow == 77}">
												<%in = 1; %>
												<tr style="border: 0px;border-left: 0px;border-right: 0px;" >
												<c:set value="<%= numrow %>" var="trows" ></c:set>
												<td style="border-color: white;border-left-color: white" colspan="5" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${frsize}"></c:out></td>
												<td style="border:0px;text-align: right;" colspan="2">Total for this page:
												
												<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
												</td>
												<td style="font-weight: bold;text-align: right;border:0px;" colspan="2" >
												<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
												</td>
												<%numrow =0; %>
												<c:set var="new_numrow" value="0" ></c:set>
												<c:set var="sub" value="0" ></c:set>
											</tr>
												<tr  style="border-color: white;background-color: white;">
											 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
											 	Sub-total brought forward:
											 	</td>
											 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
											 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
													<c:out value="${sub_brought}"></c:out>
													</fmt:formatNumber> </u>
											 	</td>
											 </tr>
											 
											
											</c:if>
											 <%} %>
										</c:when>
									</c:choose>
								</c:forEach>
							</tbody>
							
							<tfoot id="tfoot" >
						
								
							</tfoot>
							 <tr  style="border-color: white;background-color: white;" >
							 <c:set value="<%= numrow %>" var="trows" ></c:set>
							<td valign="top" style="border-color: white;" colspan="5" >Row(s):<c:out value="<%= numrow-- %>"></c:out>/<c:out value="${frsize}"></c:out> </td>
							<td  style="border-color: white;border:0px;text-align: right;" colspan="2">Total for this page:
							<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
							</td>
							<td  style="font-weight: bold;text-align: right;border:0px; " colspan="2">
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
							</td>
				 </tr>
				 <tr  style="border-color: white;background-color: white;">
				 	<td  style="border-color: white;border:0px;text-align: right;" colspan="7" >
				 	Sub-total brought forward:
				 	</td>
				 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
				 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
						<c:out value="${sub_brought}"></c:out>
						</fmt:formatNumber> </u>
				 	</td>
				 </tr>
						</table>
					<!-- <div style="border:1px solid black;"  > -->
					<%-- <br>
								<label style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;text-align: left;" > Row(s):<c:out value="<%= numrow-- %>"></c:out>/<c:out value="${frsize}"></c:out> </label>
								<%for(int d=0;d<100;d++){ %>
								&nbsp;
								<%} %>
								<label style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;text-align: right;" > SubTotal:
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u> </label>
					<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
					<label  style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;text-align: left;">
						<%for(int d=0;d<261;d++){ %>
								&nbsp;
								<%} %>
						 Sub-total brought forward:<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub_brought}"></c:out>
												</fmt:formatNumber> </u></label> --%>
					<!-- </div> -->			
					</c:if>
					
		</div>
		
	</c:when>
		
	
</c:choose>

<c:choose>

	<c:when test="${size2 > 0}">
<script> document.title = "Pantawid Pamilyang Pilipino Program Total Not Release Reports"; </script>
		<div class="dh2" >
			<h2>CCT Not Released</h2>
		</div>
		
		<div id="div2" >
		<%int row2 = 1;int num_row = 0;int in=0; %>
		<c:set var="sub" value="0" ></c:set>
			<table id="display2" border="1"  class="paginated display sortable"  >
				<thead id="thead" >
					<tr  style="border-style: double;" >
						<th>#</th>
						<th  >Household ID No.</th>
						<th id="head" >Name of Grantee</th>
						<th  >Barangay</th>
						<th  >Municipality</th>
						<!-- <th  >PhilHealth ID No.</th> -->
						<th  >Date of Transaction</th>
						<th  >Amount</th>
					</tr>
				</thead>
				<c:set var="sub_brought" value="0" ></c:set>
				<tbody id="tbody1" >
					<c:forEach items="${data2}" var="list" >
						<% num_row++; %>
						<tr>
							<td><%= row2++ %></td>
							<td><c:out value="${list.household_id}"></c:out></td>
							<td  ><%-- <%int c=0; %>
								<c:forTokens items="${list.head_name}" var="name" delims="*">
									<c:set var="mname" value="<%= c++ %>" ></c:set>
									<c:if test="${mname == 1}">
										<%int p=0; %>
										
										<c:forTokens items="${name}" delims="" >
											<c:set var="p1" value="<%= p++ %>" ></c:set>
											<c:out value="${mname}"></c:out>
											<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
											<c:out value="${msplit}."></c:out>
										</c:forTokens> 
										
									</c:if>
									<c:if test="${mname < 1 || mname > 1}">
										<c:out value="${name}"></c:out>
									</c:if> 
								</c:forTokens> --%>
								<%-- <c:out value="${list.head_name }"></c:out> --%>
								<label style="text-transform: capitalize;color: black;font-weight: bold;f">
														<%-- <c:out value="${list.head_name}"></c:out> --%>
														 <%int c=0,d=0; %>
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname" value="<%= c++ %>" ></c:set>
														</c:forTokens>	
														
														<c:forTokens items="${list.head_name}" var="name" delims=" ">
															<c:set var="mname2" value="<%= d++ %>" ></c:set>
															<c:choose>
																<c:when test="${mname2 == mname}">
																	<%int p=0; %>
																	<c:forTokens items="${name}" delims="" >
																		<c:set var="p1" value="<%= p++ %>" ></c:set>
																		<c:set var="msplit" value="${fn:substring(name,0,1)}" ></c:set>
																		<c:out value="${msplit}."></c:out>
																	</c:forTokens> 
																</c:when>
																<c:otherwise>
																	<c:out value="${name}"></c:out>
																</c:otherwise>
															</c:choose>
														</c:forTokens>	
													</label>
							</td>
							<td><c:out value="${list.brgy}"></c:out></td>
							<td><c:out value="${list.municipality}"></c:out></td>
							<%-- <td><c:out value="${list.philhealth_id}"></c:out></td> --%>
							<td><c:out value="${list.date_coverage}"></c:out></td>
							<td class="amount" >
							<div> <div style="float:left" >P</div>
							<div style="float:right">
							 
								<%-- <fmt:formatNumber pattern="0.00" type="currency"  > --%>
									<c:out value="${list.amount_receive}"></c:out>.00
									<%int enter = 0; %>
															
															<c:set var="sub2" value="0" ></c:set>
															<c:set var="sub3" value="0" ></c:set>
															<c:forTokens items="${list.amount_receive}" delims="," var="amount" >
																<%if(enter == 0){ %>
																	<c:set var="sub2" value="${amount}" ></c:set>
																<% enter = 1; }else{ %>
																	<c:set var="sub3" value="${amount}" ></c:set>
																<% enter =2;} %>
															</c:forTokens>
															
															<% if(enter == 1){%>
																<c:set var="subt" value="${sub2}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%}else if(enter == 2){ %>
																<c:set var="subt" value="${sub2}${sub3}" ></c:set>
																<c:set var="sub" value="${sub+subt}" ></c:set>
															<%} %>
								<%-- </fmt:formatNumber> --%>
							</div> </div> 
							</td>
						</tr>
						
						<%
							if(row2 == 60	){
								in=1;
						%>
						
						<tr  style="border-color: white;"  >
							<c:set value="<%= num_row %>" var="trows" ></c:set>
							<td valign="top" style="border-color:white" colspan="3" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${size2}"></c:out></td>
							<td  style="border:0px;text-align: right;" colspan="2">Total for this page:
							<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
							</td>
							<td  style="font-weight: bold;border:0px;text-align: right;" colspan="2" >
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
							</td>
							<%num_row =0; %>
							<c:set var="sub" value="0" ></c:set>
						</tr>
						<tr  style="border-color: white;background-color: white;">
				 	<td  style="border-color: white;border:0px;text-align: right;" colspan="5" >
				 	Sub-total brought forward:
				 	</td>
				 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
				 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
						<c:out value="${sub_brought}"></c:out>
						</fmt:formatNumber> </u>
				 	</td>
				 </tr>
						
						<%
							}
						%>

						<%if(in == 1 && num_row == 68){ %>
							<tr  style="border-color: white;"  >
							<c:set value="<%= num_row %>" var="trows" ></c:set>
							<td valign="top" style="border-color:white" colspan="3" >Row(s):<c:out value="${trows}"></c:out>/<c:out value="${size2}"></c:out></td>
							<td  style="border:0px;text-align: right;" colspan="2">Total for this page:
							<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
							</td>
							<td  style="font-weight: bold;border:0px;text-align: right;" colspan="2" >
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
							</td>
							<%num_row =0; %>
							<c:set var="sub" value="0" ></c:set>
						</tr>
						<tr  style="border-color: white;background-color: white;">
				 	<td  style="border-color: white;border:0px;text-align: right;" colspan="5" >
				 	Sub-total brought forward:
				 	</td>
				 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
				 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
						<c:out value="${sub_brought}"></c:out>
						</fmt:formatNumber> </u>
				 	</td>
				 </tr>
						<%} %>
					</c:forEach>
				</tbody>
				
				<tfoot id="tfoot" >
			
				</tfoot>
				 <tr  style="border-color: white;background-color: white;" >
				 <c:set value="<%= num_row %>" var="trows" ></c:set>
							<td valign="top" style="border-color: white;" colspan="3" >Row(s):<c:out value="<%= num_row-- %>"></c:out>/<c:out value="${size2}"></c:out> </td>
							<td  style="border-color: white;border:0px;text-align: right;" colspan="2">Total for this page:
							<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
							</td>
							<td  style="text-align: right;border:0px;font-weight: bold; " colspan="2">
								<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u>
							</td>
				 </tr>
				 <tr  style="border-color: white;background-color: white;border:0px;">
				 	<td  style="border-color: white;border:0px;text-align: right;" colspan="5" >
				 	Sub-total brought forward:
				 	</td>
				 	<td  style="font-weight: bold;border-color: white;border:0px;text-align: right;" colspan="2">
				 		<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
						<c:out value="${sub_brought}"></c:out>
						</fmt:formatNumber> </u>
				 	</td>
				 </tr>
			</table>
		<%-- 	<br/>
		<label style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;" > Row(s):<c:out value="<%= num_row-- %>"></c:out>/<c:out value="${size2}"></c:out> </label>
		<%for(int d=0;d<100;d++){ %>
		&nbsp;
		<%} %>
		<label style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;text-align: right;" > Total for this page:<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub}"></c:out>
												</fmt:formatNumber> </u></label><br/>
		<c:set var="sub_brought" value="${sub_brought+sub}" ></c:set>
					<label  style="border: 0px;border-left: 0px;border-right: 0px;font-size: 16px;text-align: left;">
						<%for(int d=0;d<74;d++){ %>
								&nbsp;
								<%} %>
						 Sub-total brought forward:<u> <fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${sub_brought}"></c:out>
												</fmt:formatNumber> </u></label> --%>
		</div>
		
	</c:when>

</c:choose>
 	
<div  id="app_container">
<p>Approved by:</p>
<div id="approval_sig" >

<label>Municipal Link:</label><u><% for(int l=0;l<50;l++){%>&nbsp;<%} %></u><% for(int l=0;l<15;l++){%>&nbsp;<%} %>
<label>LandBank Representative:</label><u><% for(int l=0;l<50;l++){%>&nbsp;<%} %></u><% for(int l=0;l<15;l++){%>&nbsp;<%} %>
<label>IT Representative:</label><u><% for(int l=0;l<50;l++){%>&nbsp;<%} %></u>

</div>
</div>
</body>
</html>