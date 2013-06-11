<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="index.jsp"%>
	
<%  
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store,must-revalidate,private");
	response.setHeader("Pragma","no-cache");
	/* response.setHeader("Expires","0"); */ 
	response.setDateHeader("Expires",-1); 
%>
	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">

<%
	String cPath = request.getContextPath();
	
	if(session.getAttribute("user") == null ){ 
		/* System.out.println("disconnected"); */
%>
		<script type="text/javascript"  >top.document.location.replace('<%=cPath%>');</script>
<%
	}/* else{
		System.out.println("connected");
	} */ 
%>
 
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" CONTENT="-1" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Pragma" CONTENT="no-cache" />

<title>View all Users</title>

<link rel="stylesheet" type="text/css" media="all" href="css/maintenanceStyle.css" />
<link type="text/css" href="css/btnStyles.css" rel="stylesheet" media="all">
<link type="text/css" href="jqueryUI/css/le-frog/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="css/stlnorecordsfound.css"/> 

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="js/ajaxx.js"></script>
<script type="text/javascript" src="js/jquery_ui.js"></script>

<script src="paginationAndSort/jquery.tablesorter-2.0.3.js" type="text/javascript"></script> 
<script src="paginationAndSort/jquery.tablesorter.filer.js" type="text/javascript"></script>
<script src="paginationAndSort/jquery.tablesorter.pager.js" type="text/javascript"></script>

<style type="text/css">
.first, .prev, .next, .last{
	background-color: #58aa00;
    padding: 2px 2px;
    display: inline;
    border: none;
    color: #fff;
    cursor: pointer;
    font-weight: bold;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    text-shadow: 1px 1px #666;
    font-family: helvetica,sans-serif,arial;
    font-size: 10px;
    margin: 3px auto;
}
</style>

<script type="text/javascript">
$(function(){
	var dialogSettingsAddNewAUser = {
			autoOpen:false,
			draggable:false,
			resizable: false,
			height:240,
			width:450,
			modal: true,
			title:'Add New User',
			show:'fade',
			hide:'fade'
	};
	var dialogSettingsEditUser = {
			autoOpen:false,
			draggable:false,
			resizable: false,
			height:240,
			width:500,
			modal: true,
			title:'Edit User',
			show:'fade',
			hide:'fade'
	};
	
	if ($("#norecord").val() == "norecords"){
		AddNewUser();
	}
	
	$("#containerAddUser").dialog(dialogSettingsAddNewAUser);
	$("#containerEditser").dialog(dialogSettingsEditUser);
	
	$("#table2").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
	.tablesorterPager({ container: $("#pagerOne"), positionFixed: false });	
	
	$(".first").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-first"
		}
	});
	$(".prev").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-prev"
		}
	});
	$(".next").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-next"
		}
	});
	$(".last").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-end"
		}
	});
	$("#btn_add_user").button({
		icons: {primary: "ui-icon-circle-plus"}
	});
});

function AddNewUser(){
	$("#containerAddUser").dialog('open').load('CO_AddUser');
}
function EditUser(id){
		//alert("sdsdsdsds");
	$("#containerEditser").dialog('open').load('CO_EditUser?id='+id);
}
function DeleteUser(id){
	$("#confirmationDeleteUser").dialog({
		draggable:false,
		resizable: false,
		height:200,
		width:300,
		modal: true,
		title:'Delete User',
		show:'fade',
		hide:'fade',
		buttons:{
			"Delete": function() {
				document.location.href = 'CO_DeleteUser?id='+id;
				
			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function isExistData(){	
	ajax({
		method: "POST",
		url: "CO_GetAllUsers",
		data: "uname="+$("#uname").val()+"&pword="+$("#pword").val(),
		callback: function successHandler(data){
			if (data == 1){
				$("#exist").html("Account Exist..");
				$(".imgError").show();
				document.getElementById("btn_add_user_add").disabled = true;
			}else if (data == 0 ){
				$("#exist").html("");
				$(".imgError").hide();
				document.getElementById("btn_add_user_add").disabled = false;
			}
		},
		resultType: "plain",
		async: true
	});
}

function isNoRecords(){	
	if (document.getElementById("norecord").value == 'norecords'){
		$("#table2 > tbody").html("<tr><td colspan='6'><h1 class='stl_norecords'> no records found </h1></td></tr>");
		AddNewUser();
	}
}

/* Seacrh Role Names using JSON */
function FilterUser(){
	var name = document.getElementById("searchUser").value;
	alert(name);
	xhrGo("POST","CO_SearchUser?username="+name+"",Display,"plain");
}
function Display(data){
			alert(data);
			if (data == ""){		
				document.getElementById("tbody").innerHTML = "<tr><td colspan='6'><h1 class='stl_norecords'> no records found </h1></td></tr>";
				$("#tfoot").hide();
			}else{
				$("#tfoot").show();
				var x = eval('('+data+')');
				$("#table2 > tbody").html("");		
				var str = "";
				for(var i = 0; i< x.data.length ;i++){	
						str = "<tr id='hover_tr'>"+
									"<td class='resize_td'>"+x.data[i].username+" <input name='uname' type='hidden' value='"+x.data[i].username+"'/> </td>"+
									"<td class='resize_td'>"+x.data[i].password+" <input name='pword' type='hidden' value='"+x.data[i].password+"'/> </td>"+
									"<td>"+x.data[i].name+"</td>";
									if(x.data[i].activation == true){
										 str += "<td class='resize_td' style='background-color: green;' align='center'><input disabled='disabled' checked='checked' type='checkbox'/></td>";
									 }else if (x.data[i].activation == false){
										 str += "<td class='resize_td' style='background-color: green;' align='center'><input disabled='disabled' type='checkbox'/></td>"; 
									 }
									str+="<td><a  href='#' class='edit' id='"+x.data[i].user_id+"' onclick='EditUser(this.id);' >Edit</a></td>"+
									"<td><a href='#' class='delete' id='"+x.data[i].user_id+"' onclick='DeleteUser(this.id);'>Delete</a></td>"+
								"</tr>" ; 
						$("#table2 > tbody").append(str);
				} 
				pagination();
			}	
}
function pagination(){
	$("#table2").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
	.tablesorterPager({ container: $("#pagerOne"), positionFixed: false });
}
</script>

</head>
<body  onload="isNoRecords();">

	<input type="hidden" value="${norecords }" id="norecord">

	<div id="containerAddUser" style="display: none;"></div>
	<div id="containerEditser" style="display: none;"></div>
	<div id="confirmationDeleteUser" style="display: none;">
		<br>
		Are you sure you want to delete?
	</div>
	
	<div id="container">
		<table border="0" id="table1">
			<tr>
				<th align="left">Users</th>
				<td align="right" > <button id="btn_add_user" onclick="AddNewUser();">Add New</button></td>
			</tr>
		</table>
		<br>
		<table align="left" style="margin-left: 25px">
			<tr>
				<td>Search : </td>
				<td>
					<input class="searchInput" id="searchUser" type="text" value="User Name" 
					onkeyup="FilterUser();" 
					onfocus="if(this.value=='User Name'){this.value='';}" 
					onblur="if(this.value==''){this.value='User Name';}" />
				</td>
			</tr>
		</table> 
		<table border="1" id="table2">
		<thead id="theadMain">
			<tr>
				<th class="cell">Username</th>
				<th class="cell">Password</th>
				<th class="cell">Role</th>
				<th class="cell">Active</th>
				<th class="cell" colspan="2">Actions</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${list}" var="list">
				<tr id="hover_tr">
					<td class="cell"><c:out value="${list.username}"></c:out> <input
						name="uname" type="hidden" value="${list.username}" /></td>
					<td class="cell"><c:out value="${list.password}"></c:out> <input
						name="pword" type="hidden" value="${list.password}" /></td>
					<td class="cell"><c:out value="${list.name}"></c:out></td>
					<td class="cell" style="background-color: green;" align="center"><c:if
							test="${list.activation == true}">
							<input type="checkbox" checked disabled="disabled" />
						</c:if> <c:if test="${list.activation == false}">
							<input type="checkbox" disabled="disabled" />
						</c:if></td>

					<td class="cell" align="center" width="23"><a href="#" id="${list.user_id}" class="edit" onclick="EditUser(this.id);">Edit </a></td>
					<td class="cell" align="center" width="38"><a href="#" id="${list.user_id}" class="delete" onclick="DeleteUser(this.id);">Delete </a></td>
				</tr>
			</c:forEach>
			</tbody>
				<tfoot id="tfoot" align="center">
				    <tr id="pagerOne">
				        <td colspan="11" style="border-right: solid 3px #7f7f7f;">
					     	<input type="button" class="first" value="first" />
				        	<input type="button" class="prev" value="prev" />
				        	<input readonly="readonly" size="3" type="text" class="pagedisplay" value="first" />
				        	<input type="button" class="next" value="next" />
				        	<input type="button" class="last" value="last" />
				        	<select class="pagesize">
						        <option selected="selected"  value="10">10</option>					
						        <option value="15">15</option>
						        <option value="20">20</option>
					        </select>
					    </td>
				    </tr>
				</tfoot>
		</table>
	</div>
</body>
</html>
