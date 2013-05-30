<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String cPath = request.getContextPath(); %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pantawid Pamilyang Pilipino Program</title>
<meta name="Author" content="Stu Nicholls" />

<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" href="<%= cPath %>/css/add_transactions_CSS.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />

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
<script type="text/javascript">
	
	
	/*onload=function(){
		var d=new Date();
		cUR = d.getHours();
		currYear = d.getFullYear();
		var sel=document.getElementById('year');
		var year_start=currYear-1921;
		var nrMax=year_start, opt;
		for(var i=0;i<nrMax;i++){
			opt=document.createElement('option');
			opt.setAttribute('value',currYear);
			opt.appendChild(document.createTextNode(currYear));
			sel.appendChild(opt);
			currYear--;
		}
		
	};*/
	

</script>
<style type="text/css">
	#dialog-confirm{
		display: none;
		
	}
	.button-dialog{
		font-size: 16px;
	}
	#change-photo{
		margin-left: 50px;
		display: none;
		
	}
	#success{
		display: none;
		
	}
	.buttons{ font: 110% "Trebuchet MS", sans-serif;}
	#street{
		margin-left: 45px;
	}
	.sd_remove{
		margin-left: 30px;
		margin-top: 10px;
		margin-bottom: 10px;
	}
	.gg_remove{
		margin-left: 30px;
		margin-top: 10px;
		margin-bottom: 10px;
	}
	#monthFingerprint{
		position: relative;
		margin-top: 8px;
	}
	#month2{
		position: absolute;
		top: 0;
		left: 0;
		margin-left: 200px;
	}
	#month3{
		position: absolute;
		top: 0;
		left: 0;
		margin-left: 400px;
	}
	.mar-top{
		margin-top: 8px;
	}
	#reasons{
		margin-top: 10px;
	}
	table{
		margin-top: 10px;
	}
	#action{
		margin-left: auto;
		margin-right: auto;
	}
	#photo_head{
		margin-left: 25px;
	}
	select{
		margin-left: 5px;
		padding: 5px;
	}
</style>
</head>

<body >

<div id="page-wrap">
<div id="header" >
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
	<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
	<!--  <table width="900px;" >
		<tr>
			<td width="180px;" >
				<div id="imgDiv" ><img class="img1"  alt="leyte_sel" src="<%= cPath %>/image/Leyte_seal.png"/></div>
			</td>
			<td width="600px;" >
				
				<h3 style="color:white;" class="h3h" >
				
					Pantawid <br>
					Pamilyang <br> 
					Pilipino <br>
					Program <br>
					
				</h3>
			
			</td>
			<td>
				<img alt="" src="<%= cPath %>/image/legal3dswd.png" id="dswd-image"/>
			</td>
		</tr>
	</table>-->
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu" >

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>

<div id="main" >
	<h3>add beneficiary profile</h3>
		<form name="transactionForm" id="transactionForm" method="post" action="<%=cPath %>/AddProfileValidation" onsubmit="return addProfileValidation();">
			<div>
				<label>Household ID NO. :</label><input type="text" name="hh_idNumber" id="v_hh_id" class="input" size="30" onkeypress="return numbersonly(event,false);" autocomplete = "off"/><br/><br/><hr/><br/>
				
				
				<label>Name of Head :</label><input type="text" name="h_headName" id="v_head_name" class="input" size="30"/>
				<div class="indent">
					<label>Household Member ID :</label><input type="text" id="v_head_id" name="h_EntryID" class="input" onkeypress="return numbersonly(event,false);"/>
					<label>Age :</label><input type="text" name="h_age" id="v_head_age" maxlength="3" size="5" class="input" onkeypress="return numbersonly(event,false);"/>
					<label>Birthday :</label><input type="text" name="h_birthday" class="input" id="h_birthday" /><br/>
					<br/>
					<label>Pregnant:</label><input type="radio" value="true" name="h_pregnant" id="h_pregnant_yes"/>
					<label class="radio" for="h_pregnant_yes">Yes</label><input type="radio" value="false" name="h_pregnant" id="h_pregnant_no"/><label  class="radio" for="h_pregnant_no">No</label>
					<label  class="attending">Attending School:</label><input type="radio" id="h_AS_yes" value="true" name="h_attendingSchool"/>
					<label class="radio" for="h_AS_yes">Yes</label> <input type="radio" value="false" id="h_AS_no" name="h_attendingSchool"/><label class="radio" for="h_AS_no">No</label>
				</div><br/><hr/><br/>
				
				<label>Wife/Spouse:</label><input type="text" name="ws_name" id="v_ws_name" class="input" size="30" autocomplete = "off"/>
				<div class="indent">
					<label>Household Member ID :</label><input type="text" name="ws_EntryID" id="v_ws_id" class="input" onkeypress="return numbersonly(event,false);"/>
					<label>Age :</label><input type="text" name="ws_age" maxlength="3" size="5" id="v_ws_age" class="input" onkeypress="return numbersonly(event,false);"/>
					<label>Birthday :</label><input type="text" name="ws_birthday" class="input" id="ws_birthday"/>
					<br/>
					<label>Pregnant:</label><input type="radio" value="true" name="ws_pregnant" id="ws_pregnant_yes"/>
					<label class="radio" for="ws_pregnant_yes">Yes</label><input type="radio" value="false" name="ws_pregnant" id="ws_pregnant_no"/><label for="ws_pregnant_no"  class="radio">No</label>
					<label  class="attending">Attending School:</label><input type="radio" value="true" name="ws_attendingSchool" id="ws_AS_yes"/>
					<label class="radio" for="ws_AS_yes">Yes</label> <input type="radio" value="false" name="ws_attendingSchool" id="ws_AS_no"/><label class="radio" for="ws_AS_no">No</label>
				</div><br/><hr/><br/>
				
				<div id="sonDaugther"><br/>
					<label>Son/Daughter :</label><input type="text" name="sd_name" class="input" size="30"/>
						<div class="indent">
							<label>Household Member ID :</label><input type="text" name="sd_EntryID" class="input" onkeypress="return numbersonly(event,false);"/>
							<label>Age :</label><input type="text" name="sd_age" maxlength="3" size="5" class="input" onkeypress="return numbersonly(event,false);"/>
							<label>Birthday :</label>
										<select name="sd_month">
											<option value="">month</option>
											<%String months[] = {"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
											for(int i = 0; i < months.length ; i++){%>
												<option  value=<%=i+1%>><%= months[i] %></option>
											<%}%>
										</select>
										<input type="text" maxlength="2" size="2" name="sd_day" class="input" onkeypress="return numbersonly(event,false);"/>
										<input type="text" maxlength="4" size="4" name="sd_year" class="input" onkeypress="return numbersonly(event,false);"/>
										
							<br/>
							<input type="hidden" name="sd_pregnantCtrl" value="0"/>
							<input type="hidden" name="sd_attendingSchoolCtrl" value="0" />
							<label>Pregnant:</label><input type="radio" value="true" name="sd_pregnant0" id="sd_P_yes0"/>
							<label class="radio" for="sd_P_yes0">Yes</label><input type="radio" value="false" name="sd_pregnant0" id="sd_P_no0"/><label for="sd_P_no0" class="radio">No</label>
							<label  class="attending">Attending School:</label><input type="radio" value="true" name="sd_attendingSchool0" id="sd_AS_yes0"/>
							<label class="radio" for="sd_AS_yes0">Yes</label> <input type="radio" value="false" name="sd_attendingSchool0" id="sd_AS_no0"/><label class="radio" for="sd_AS_no0">No</label>
						</div>
						<button id="sd_Addmore" type="button" class="buttons">Add more son/daugther</button>
						<br/><hr/><br/>
				</div>

				<div id="grandsonGrandaugther">
					<label>Grandson/Grandaughter :</label><input type="text" value="" name="gg_name" class="input" size="30"/>
					<div class="indent">
						<label>Household Member ID :</label><input type="text" value="" name="gg_EntryID" class="input" onkeypress="return numbersonly(event,false);"/>
						<label>Age :</label><input type="text" name="gg_age" value="" maxlength="3" size="5" class="input" onkeypress="return numbersonly(event,false);"/>
						<label>Birthday :</label> 
										<select name="gg_month">
											<option value="" selected>month</option>
											<%String months1[] = {"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
											for(int i = 0; i < months1.length ; i++){%>
												<option  value=<%=i+1%>><%= months1[i] %></option>
											<%}%>
										</select>
										<input type="text" maxlength="2" value="" size="2" name="gg_day" class="input" onkeypress="return numbersonly(event,false);"/>
										<input type="text" maxlength="4" value="" size="4" name="gg_year" class="input" onkeypress="return numbersonly(event,false);"/>
							
						<br/>
						<input type="hidden" name="gg_pregnantCtrl" value="0"/>
						<input type="hidden" name="gg_attendingSchoolCtrl" value="0" />
						<label>Pregnant:</label><input type="radio" value="true" name="gg_pregnant0" id="gg_P_yes0"/>
						<label class="radio" for="gg_P_yes0">Yes</label><input type="radio" value="false" name="gg_pregnant0" id="gg_P_no0"/><label for="gg_P_no0" class="radio">No</label>
						<label  class="attending">Attending School:</label><input type="radio" value="true" name="gg_attendingSchool0" id="gg_AS_yes0"/>
						<label class="radio" for="gg_AS_yes0">Yes</label> <input type="radio" value="false" name="gg_attendingSchool0" id="gg_AS_no0"/><label class="radio" for="gg_AS_no0">No</label>
					</div>
					<button id="gg_Addmore" type="button" class="buttons">Add more grandson/grandaugther</button>
					<br/><hr/><br/>
				</div>
				
				<label>Street :</label><input type="text" id="street" value="" name="street" class="input" size="25"/>
				<label>Purok :</label><input type="text" name="purok" id="purok" value="" class="input" size="20"/>
				<label>Barangay :</label><input type="text" name="barangay" id="barangay" value="" class="input" size="25"/><br/>
				<label>Municipality :</label><input type="text" name="municipality" id="municipality" value="" class="input" size="25"/>
				<br/>
				<table id="action">
					<tr>
						<td><button id="save" type="submit" >Save</button></td>
						<td><button id="cancel" type="reset" >Cancel</button></td>
					</tr>
				</table>
				
				<br/>
			</div>
		</form>
</div>
</div> <!-- end of main content -->
<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
<c:if test="${hh_idExist}">
	<script type="text/javascript">
		alert("Household ID already exist");
	</script>
</c:if>
<c:if test="${head_idExist}">
	<script type="text/javascript">
		alert("Head member ID already exist");
	</script>
</c:if>
<c:if test="${ws_idExist}">
	<script type="text/javascript">
		alert("Wife/Spouse already exist");
	</script>
</c:if>
<c:if test="${sd_idExist}">
	<script type="text/javascript">
		alert("Son/Daugther member ID already exist");
	</script>
</c:if>
<c:if test="${gg_idExist}">
	<script type="text/javascript">
		alert("Grandson/Grandaughter member ID already exist");
	</script>
</c:if>

</div> <!-- end of page wrap -->

</body>
</html>