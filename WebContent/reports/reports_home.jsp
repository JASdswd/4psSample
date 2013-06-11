<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String cPath = request.getContextPath(); %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="java.text.NumberFormat"  %>
	<%@ page import="java.util.*"%>
	<%@ page import="java.text.*"%> 
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
<link href="<%= cPath %>/css/reports.css" type="text/css" rel="stylesheet" />
<link href="<%= cPath %>/css/filter.css" type="text/css" rel="stylesheet" />
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" href="<%= cPath %>/css/table.css"/>
<link rel="stylesheet" href="<%= cPath %>/development-bundle/themes/base/jquery.ui.all.css">
<link type="text/css"  href="<%= cPath %>/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<link rel="stylesheet" href="<%= cPath %>/development-bundle/demos/demos.css"/>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js"></script>
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/page.js"></script>
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
<script type="text/javascript" src="<%= cPath %>/js/reports.js"></script>
<script type="text/javascript" src="<%= cPath %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script src="<%= cPath %>/development-bundle/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>

<script type="text/javascript">

var emp = 0;
var empl = 0;
var femp = 0;

function changeTbl(op){
	var param_op = 0;
	var sdate = "";
	var edate = "";
	var search = "";
	var val = "";
	var mun = "";
	var brgy = "";
	var t = "";
	var hhset = "";
	edate = getIDValue("edate");
	sdate = getIDValue("sdate");
	t = getIDValue("view_trans");
	hhset = getIDValue("view_hhset");
	param_op  =getIDValue("param_op");
	if(op == '1'){
		
		if(t != "Transactions"){
			$("#terr").hide();
			val = getIDValue("transaction");
			
			if(val == "municipal"){
				
				mun = getIDValue("mun");
				brgy = getIDValue("bname");
				$("#div").show();
				$("#overlay").show();
				xhrGo("GET", "<%= cPath %>/GetData?sdate="+sdate+"&edate="+edate+"&mun="+mun+"&val="+val+"&brgy="+brgy+"&transaction="+t+"&param_op="+param_op+"&hh_set="+hhset, changeData, "plain");
			}
			else{
				search = getIDValue("search");
				if(edate != "" && sdate != ""){
					$("#div").show();
					$("#overlay").show();
					xhrGo("GET", "<%= cPath %>/GetData?sdate="+sdate+"&edate="+edate+"&search="+search+"&val="+val+"&transaction="+t+"&param_op="+param_op+"&hh_set="+hhset, changeData, "plain");
				}
				else{
					$("#div").show();
					$("#overlay").show();
					xhrGo("GET", "<%= cPath %>/GetData?sdate="+sdate+"&edate="+edate+"&search="+search+"&val="+val+"&transaction="+t+"&param_op="+param_op+"&hh_set="+hhset, changeData, "plain");
				}
			}
			
			
		}
		else{
			$("#terr").show();
		}
		
	}
	else if(op == '2'){
		
		var search = getIDValue("search");
		var mun = getIDValue("mun");
		var brgy = getIDValue("bname");
		var val = getIDValue("transaction");
		var t = getIDValue("view_trans");
		if(val == "municipal" ){
			if(mun == "Municipality" && brgy == "Barangay" && t == "Transactions"){
				if(edate != "" && sdate != ""){
					$("#div").show();
					$("#overlay").show();
					xhrGo("POST", "<%= cPath %>/GetData?sdate="+sdate+"&edate="+edate, changeData, "plain");
				}
			}
		}
		else{
			if(search == "" || search == null){
				if(edate != "" && sdate != "" && t == "Transactions"){
					$("#div").show();
					$("#overlay").show();
					xhrGo("POST", "<%= cPath %>/GetData?sdate="+sdate+"&edate="+edate, changeData, "plain");
				}
			}
		}
	}
		
	
}

function changeAuto(data){
	//alert(data);
	var x = eval('('+data+')');
	var tval=new Array();
	for(var i=0;i<x.data.length;i++){
		tval[i]=x.data[i];
		
	}
	$("#search").autocomplete({
		source:tval
	});
	
}

$(document).ready(function() {
	
	$("#div").hide();	
	$("#overlay").hide();
	$("#terr").hide();
	
	$("#search").keyup(function(){
		
		var val = getIDValue("transaction");
		var search = getIDValue("search");
		//alert(val);
		if(val == "household"){
			//alert("val:"+val);
			xhrGo("POST", "<%= cPath %>/Print?val="+val+"&search="+search, changeAuto, "plain");
		}
		else if(val == "pId"){
			//alert("in");
			xhrGo("POST", "<%= cPath %>/Print?val="+val+"&search="+search, changeAuto, "plain");
		}
		
	});
	
	$('input[type="text"]').addClass("idleField");
		$('input[type="text"]').focus(function() {
			$(this).removeClass("idleField").addClass("focusField");
	    if (this.value == this.defaultValue){ 
	    	this.value = '';
		}
		if(this.value != this.defaultValue){
			this.select();
		}
	});
	$('input[type="text"]').blur(function() {
		$(this).removeClass("focusField").addClass("idleField");
	    if ($.trim(this.value) == ''){
	    	this.value = (this.defaultValue ? this.defaultValue : '');
		}
	});
	
	$("#dialog-confirm").hide();
	$("#input").hide();
	$("#exists").hide();
	$("#alert").hide();
	$("#ask").hide();
	$("#note").hide();
	$("#alert2").hide();
	
	/* $("#ok").button({
		icons:{primary:"ui-icon-heart"}
	}); */
	$("#download_btn").button({
		icons : {primary : "ui-icon-script" }
	});
	$("#ok").button({
		icons : {primary : "ui-icon-circle-check" }
	});
	$("#print_btn").button({
		icons : {primary : "ui-icon-print" }
	});
	

	 $('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
    var options = {
      currPage : 1, 
      ignoreRows : $('tbody1 tr:odd', $('#display')),
      optionsForRows : [5,10,15],
      rowsPerPage : 5,
      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
      topNav : false
    };
    $('#display').tablePagination(options);
    
    $('tbody1 tr:odd', $('#display3')).hide(); //hiding rows for test
    var options = {
      currPage : 1, 
      ignoreRows : $('tbody1 tr:odd', $('#display3')),
      optionsForRows : [5,10,15],
      rowsPerPage : 5,
      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
      topNav : false
    };
    $('#display3').tablePagination(options);

    $('tbody1 tr:odd', $('#display2')).hide(); //hiding rows for test
    var options = {
      currPage : 1, 
      ignoreRows : $('tbody1 tr:odd', $('#display2')),
      optionsForRows : [5,10,15],
      rowsPerPage : 5,
      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
      topNav : false
    };
    $('#display2').tablePagination(options);
	
   /*  document.getElementById('view_trans').style.display= 'none'; */
	document.getElementById('bname').style.display= 'none';
	document.getElementById('mun').style.display= 'none';
	xhrGo("POST", "<%= cPath %>/TotalMembers", viewtransactins, "plain");
	xhrGo("POST", "<%= cPath %>/ChooseReport", viewhhset, "plain");
});

function viewhhset(data){
	
	var x = eval('('+data+')');
	var str= "";
	str += "<option value=''>HH Set Group</option>";
	for(var r=0;r<x.data.length;r++){
		str += "<option>"+x.data[r]+"</option>";
	}
	
	getID("view_hhset").innerHTML = str;
}

function changeBrgy(mun){
	xhrGo("POST", "<%= cPath %>/PrintReports?op=2&mun="+mun, brgyData, "plain");
	
}

function brgyData(data){
	
	var x = eval('('+data+')');
	var st= "";
	
	st += "<option>Barangay</option>";

	for(var m=0;m<x.data.length;m++){
		
			st += "<option value='"+ x.data[m].mun_id+"' > "+x.data[m].mun_name+" </option>";
		
		
	}
	
	getID("bname").innerHTML = st;
	
}

function checkValue(value){
	
	if(value == "bday"){
		document.getElementById('b-day').style.display= 'inline';
		document.getElementById('search').style.display= 'none';
		document.getElementById('bname').style.display= 'none';
		document.getElementById('mun').style.display= 'none';
		/* document.getElementById('view_trans').style.display= 'none'; */
	}
	else if(value == "transact"){
		document.getElementById('bname').style.display= 'none';
		document.getElementById('mun').style.display= 'none';
		document.getElementById('b-day').style.display= 'none';
		document.getElementById('search').style.display= 'none';
		/* document.getElementById('view_trans').style.display= 'inline'; */
		xhrGo("POST", "<%= cPath %>/TotalMembers", viewtransactins, "plain");
	}
	else if(value == "municipal"){
		/* document.getElementById('view_trans').style.display= 'none'; */
		document.getElementById('b-day').style.display= 'none';
		document.getElementById('search').style.display= 'none';
		xhrGo("POST", "<%= cPath %>/PrintReports?op=1", munData, "plain");
	}
	else{
	/* 	document.getElementById('view_trans').style.display= 'none'; */
		document.getElementById('b-day').style.display= 'none';
		document.getElementById('bname').style.display= 'none';
		document.getElementById('mun').style.display= 'none';
		document.getElementById('search').style.display= 'inline';
	}
	
}

function viewtransactins(data){
	
	var x = eval('('+data+')');
	var str= "";
	
	str += "<option>Transactions</option>";
	
	for(var r=0;r<x.data.length;r++){
		str += "<option>"+x.data[r]+"</option>";
	}
	
	getID("view_trans").innerHTML = str;
}

function munData(data){

	var x = eval('('+data+')');
	var st= "";
	
	st += "<option >Municipality</option>";

	for(var m=0;m<x.data.length;m++){
		
			st += "<option value='"+ x.data[m].mun_id+"' > "+x.data[m].mun_name+" </option>";
		
		
	}
	
	getID("mun").innerHTML = st;
	
	document.getElementById('bname').style.display= 'inline';
	document.getElementById('mun').style.display= 'inline';
}


function goPrint(){
	
	var len = document.getElementById("tbody").rows.length; 
	var file ;
	
	if(len > 0 && emp == 1){
		$("#file").focus();
		$( "#input" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"OK": function() {
					file = document.getElementById("file").value;
					if(file == "" || file == null){
						getID("error").innerHTML = "<p style='color:red;font-size:10px;' >Please filled up the text field above.</p>";
					}
					else{
						xhrGo("GET", "<%= cPath %>/PrintReports?file="+file, changeData2, "plain");
					}
				},
				"Clear": function() {
					document.getElementById("file").value = "";
				}
			}
		});
		$("#file").focus();
	}
	else{
		$( "#alert" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}


function changeData(data){
	//alert("in here...");
	var x = eval('('+data+')');
	var str = "";
	var str2 = "";
	var str3 = "";
	var r = 0;
	var r3 = 0;
	var r2 = 0;
	var total_release = "";
	var total_not_release = "";
	var cash_total = "";
	/* var munp = "";
	var dtp = ""; */
	//alert("in");
	emp = 0;
	empl = 0;
	/* alert(x.data); */
	if(x.data == "" || x.data == "{}"){
		//alert("i");
		total_release = "P 0.00";
		getID("total_release").value = total_release;
		getID("tbody").innerHTML = "<tR><th align='center'  colspan='10' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
		getID("tbody3").innerHTML = "<tR><th align='center'  colspan='10' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
		$("#div").fadeOut(100);	
		$("#overlay").fadeOut(900);
		//alert("out");
		/* getID("pageID").value = "1/1"; */
	}
	else{
		//alert(x.data.length);
		for(var i=0;i<x.data.length;i++){
			emp = 1;
			
			if(x.data[i].sub == 0){
				r++;
				str += "<tr>" +
				"<td>"+r+"</td>"+
				"<td id='id_cell'>" +x.data[i].household_id+ "</td>" +
				"<td id='id_cell'>" +x.data[i].head_name+ "</td>" +
				"<td id='id_cell'>" +x.data[i].brgy+ "</td>"+
				"<td id='id_cell'>" +x.data[i].municipality+ "</td>"+ 
				"<td id='id_cell'>" +x.data[i].philhealth_id+ "</td>"+
				"<td id='id_cell'>" +x.data[i].date_coverage+ "</td>"+ 
				"<td id='id_cell'>" +x.data[i].date_receive+ "</td>"+
				"<td id='id_cell'>" +x.data[i].time_receive+ "</td>"+
				"<td id='id_cell9' class='amount' ><div> <div style='float:left' >P</div><div style='float:right'>" +x.data[i].amount_receive+ ".00</div> </div> </td>";
				/* munp = x.data[i].municipality;
				dtp = x.data[i].date_coverage; */
				emp = 1;
			}
			else if(x.data[i].sub != 0){
				r3++;
				str3 += "<tr>" +
				"<td>"+r3+"</td>"+
				"<td id='id_cell'>" +x.data[i].household_id+ "</td>" +
				"<td id='id_cell'>" +x.data[i].head_name+ "</td>" +
				"<td id='id_cell'>" +x.data[i].brgy+ "</td>"+
				"<td id='id_cell'>" +x.data[i].municipality+ "</td>"+ 
				"<td id='id_cell'>" +x.data[i].philhealth_id+ "</td>"+
				"<td id='id_cell'>" +x.data[i].date_coverage+ "</td>"+
				"<td id='id_cell'>" +x.data[i].date_receive+ "</td>"+
				"<td id='id_cell'>" +x.data[i].time_receive+ "</td>"+
				"<td id='id_cell9' class='amount' ><div> <div style='float:left' >P</div><div style='float:right'>" +x.data[i].amount_receive+ ".00</div> </div> </td>";
				/* munp = x.data[i].municipality;
				dtp = x.data[i].date_coverage; */
				emp =1;
			}
		}
		
		total_release = "P "+x.total_release+".00";
		/* alert(str);
		alert(str3); */
		getID("total_release").value = total_release;
		getID("tbody").innerHTML = str;
		getID("tbody3").innerHTML = str3;
		/* getID("munp").innerHTML = munp;
		getID("dtp").innerHTML = dtp; */
		
		 $('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
         var options = {
          currPage : 1, 
          ignoreRows : $('tbody1 tr:odd', $('#display')),
          optionsForRows : [5,10,15],
          rowsPerPage : 5,
          firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
          prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
          lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
          nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
          topNav : false
        };
        $('#display').tablePagination(options);
		
		$("#display").focus();
		
		 $('tbody1 tr:odd', $('#display3')).hide(); //hiding rows for test
         var options = {
          currPage : 1, 
          ignoreRows : $('tbody1 tr:odd', $('#display3')),
          optionsForRows : [5,10,15],
          rowsPerPage : 5,
          firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
          prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
          lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
          nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
          topNav : false
        };
        $('#display3').tablePagination(options);
	}
	cash_total = "P "+x.cash_total+".00";
	getID("cash_total").value = cash_total;
	$("#div").fadeOut(100);	
	$("#overlay").fadeOut(900);
	
	if(x.data2 == "" || x.data2 == "{}"){
		total_notrelease = "P 0.00";
		getID("total_notrelease").value = total_notrelease;
		getID("tbody1").innerHTML = "<tR><th align='center'  colspan='8' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
		/* getID("pageID1").value = "1/1"; */
	}
	else{
		
		for(var i=0;i<x.data2.length;i++){
			r2++;
			empl = 1;
			str2 += "<tr>" +
						"<td>"+r2+"</td>"+
						"<td id='id_cell'>" +x.data2[i].household_id+ "</td>" +
						"<td id='id_cell'>" +x.data2[i].head_name+ "</td>" +
						"<td id='id_cell'>" +x.data2[i].brgy+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].municipality+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].philhealth_id+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].date_coverage+ "</td>"+
						"<td id='id_cell9' class='amount' > <div> <div style='float:left' >P</div><div style='float:right'>" +x.data2[i].amount_receive+ ".00</div> </div> </td>";
			str2 += "</tr>";		
			
		}
		
		total_notrelease = "P "+x.total_notrelease+".00";
		getID("total_notrelease").value = total_notrelease;
		getID("tbody1").innerHTML = str2;
		
		 $('tbody1 tr:odd', $('#display2')).hide(); //hiding rows for test
         var options = {
           currPage : 1, 
           ignoreRows : $('tbody1 tr:odd', $('#display2')),
           optionsForRows : [5,10,15],
           rowsPerPage : 5,
           firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
           prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
           lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
           nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
           topNav : false
         };
         $('#display2').tablePagination(options);

		$("#display2").focus();
	}
	
	cash_total = "P "+x.cash_total+".00";
	getID("cash_total").value = cash_total;
	//alert(emp);
	$("#div").fadeOut(100);	
	$("#overlay").fadeOut(900);
	
}

function goPrintpre(){
	var emp2 = 1;
	var rel;
	var notrel;
	
	$( "#chooseReport" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:180,
		modal: true,
		buttons: {
			"OK": function() {
				
				rel = document.getElementById("rel").checked;
				notrel = document["form_name"].elements["report"][1].checked;
				
				//alert(rel);
				
				if(rel == true){
					emp2 = 0;
					//alert("you choose the released report");
					$("#reportVal").val("1");
					$( this ).dialog( "close" );
					
					//alert(emp);
					if(emp == 1){
						getID("femp").value = 1;
					}
					else{
						getID("femp").value = 0;
					}
					goPrint2();
				}
				if(notrel == true){
					emp2 =0;
					//alert("you've chhosen the not released report.");
					$("#reportVal").val("2");
					$( this ).dialog( "close" );
					//alert("empL:"+empl);
					if(empl == 1){
						getID("femp").value = 1;
					}
					else{
						getID("femp").value = 0;
					}
					goPrint2();
				}
				// alert(femp);
				if(emp2 == 1){
					alert("Please choose a report");
				} 
				 
			},
			"Cancel": function(){
				$( this ).dialog( "close" );
			}
		}
	});
	
}

function goPrint2(){
	//alert("lalabad");
	var len = document.getElementById("tbody").rows.length; 
	var file ;
	var sdate = "";
	var edate = "";
	var search = "";
	var val = "";
	var mun = "";
	var brgy = "";
	var submit = 0;
	var femp = getID("femp").value;
	//alert("hain nah..");
	edate = getIDValue("edate");
	sdate = getIDValue("sdate");
	//alert("gop:"+femp);
	if(femp == 1){
		
			val = getIDValue("transaction");
			
			if(val == "bday"){
				search = getIDValue("b-day");
			}
			else if(val == "municipal"){
				mun = getIDValue("mun");
				brgy = getIDValue("bname");
			 
				document.getElementById("startDate").value = sdate;
				document.getElementById("endDate").value = edate;
				document.getElementById("munVal").value = mun;
				document.getElementById("brgyVal").value = brgy;
				document.getElementById("trans").value = val;
				
			}
			else{
				search = getIDValue("search");
			}
			
			if(edate != "" && sdate != ""){
				document.getElementById("startDate").value = sdate;
				document.getElementById("endDate").value = edate;
				document.getElementById("searchVal").value = search;
				document.getElementById("trans").value = val;
			}
			else{
				document.getElementById("startDate").value = sdate;
				document.getElementById("endDate").value = edate;
				document.getElementById("searchVal").value = search;
				document.getElementById("trans").value = val;
			}
		
		
			if(edate != "" && sdate != ""){
				document.getElementById("startDate").value = sdate;
				document.getElementById("endDate").value = edate;
			}
		
		submit = 1;
	}
	else{
		submit = 0;
		$( "#alert" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	if(submit == 1){
		document.forms["form"].submit();
	}
	
}

function DownloadExcel(){
	var trans = getIDValue("transaction");
	var sdate = getIDValue("sdate");
	var edate = getIDValue("edate");
	var date_coverage = getIDValue("view_trans");
	var mun = getIDValue("mun");
	var bname = getIDValue("bname");
	var search = getIDValue("search");
	//var femp = getID("femp").value;
	var fname = "";
	if(emp == 1 || empl == 1){
	 $( "#ask" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"OK": function() {
					fname = document.getElementById("fname").value;
					if(fname == "" || fname == null){
						document.getElementById("errmess").innerHTML = "<p style='color:red;font-size:10px;' >Please enter a name.</p>";
					}
					else{
						//alert("send data to servlet");
						xhrGo("GET", "<%= cPath %>/DownloadExcel?file="+fname, finishDownload, "plain");
					}
				}
			}
		});	
	}
	else{
		$( "#alert2" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"OK": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
}

function finishDownload(data){
	
	var x = eval('('+data+')');
	document.getElementById("reportspath").innerHTML = "<u>"+x.path+"</u>";
	if(x.status == "not"){
		$( "#ask" ).dialog("close");
		$( "#note" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
					}
				}
			});		
	}
	else{
		document.getElementById("errmess").innerHTML = "<p style='color:red;font-size:11px;' >File Name already exists.</p>";
	}
	
}


</script>
<style type="text/css">
	.hidden{
		display: none;
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

<% String user = (String) session.getAttribute("username"); %>

<input type="hidden" value="<%= user %>" id="username" />
<input type="hidden" value="0" id="femp" />
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
<% int r=1; %>

<div id="main" >
	<div id="filterDiv1" >
		<label for="transaction">
				<select name="transaction" id="transaction" class="input" onchange="checkValue(this.value);" >
					<option selected="selected" value="household">Search By HouseHold ID No.</option>
					<option value="municipal" id="municipal">Search By Municipality</option>
				</select>
		</label>
		<select class="input" id="mun" name="mun" onchange="changeBrgy(this.value);" >
			<option>Municipality</option>
		</select>
		<select class="input" id="bname" name="bname" >
			<option>Barangay</option>
		</select>
		<input type="hidden" value="1" id="param_op" name = "param_op" />
		<label for="search" > <input type="text" id="search" autocomplete="off" name="search" /> <input style="display: none;" type="text" id="b-day" name="b-day" />  </label>
		<label for="ok" > <button id="ok" onclick="changeTbl(1);" >Search</button> </label>
	</div>
	
	<div id="filterDiv" >
		<label class="prgrph" >Search By Transaction:</label>
		<select class="input trans" id="view_trans" name="view_trans" >
			<option>Transactions</option>
		</select><span  id ="terr"  style="font-size:11px;color:red;" >&nbsp;&nbsp;Choose Value here.</span><br/>
		<label class="prgrph" >Search By Set Group:&nbsp;&nbsp;&nbsp;</label>
		<select class="input hhset" id="view_hhset" name="view_hhset" >
			<option value="">HH Set Group</option>
		</select><br/>
		<p class="prgrph" >Date Coverage:</p>
		<label for="sdate" >From</label>
		<input type="text" id="sdate" name="sdate" />
		<label for="edate" >To:</label>
		<input type="text" id="edate" name="edate"  />
		
	</div>
	<div id="startb" >
		<label for="cash_total" >Starting Balance:</label>
		<input class="textField" id="cash_total" value="P 0.00" name="cash_total" readonly="readonly" />
	 	
			<form action="<%= cPath %>/Print" method="get"  id= "form"  target="blank" onsubmit="window.open('', 'blank', 'width=1300,height=1000,status=yes,resizable=no,scrollbars=yes')" >
				<label class="plabel" >
					<button type="button"  id="download_btn" onclick="DownloadExcel()" >Download as Excel</button>
				</label>
				<label class="plabel" >
					<input type="submit" value="Print Preview" id="print_btn" />
				</label>
				<input type="hidden" id="startDate" name="startDate" />
				<input type="hidden" id="endDate" name="endDate" />
				<input type="hidden" id="searchVal" name="searchVal" />
				<input type="hidden" id="munVal" name="munVal" />
				<input type="hidden" id="brgyVal" name="brgyVal" />
				<input type="hidden" id="trans" name="trans" />
				<input type="hidden" id="reportVal" name="reportVal" value="1"/>
			</form>
		</div>
	<hr/>
	
	<div align="center">
	<h3>CCT Payments</h3>
	
		<table id="display" border="1"  class="paginated display sortable"  >
			<thead id="thead" >
				<tr>
					<td colspan="10" class="title" >Released to Grantee</td>
				</tr>
				<tr>
					<th>#</th>
					<th class="sort-numeric" >Household ID No.</th>
					<th>Name of Grantee</th>
					<th>Barangay</th>
					<th>Municipality</th> 
					<th>Set Group</th>
					<th>Date of Transaction</th>
					<th>Date Received</th>
					<th>Time Received</th>
					<th id="id_cell9" >Amount Received</th>
					<!-- <th>Substitute</th> -->
				</tr>
			</thead>
			
			<tbody id="tbody" >
				<%-- <% for(int i=0;i<20;i++){ %> --%>
					<tr>
						<%for(int c=0;c<10;c++){ %> 
							<th>&nbsp;</th>
						 <%} %> 
					</tr>
				<%-- <%} %> --%>
			</tbody>
			
			<tfoot id="tfoot" >
		
			</tfoot>
			 
		</table>
		
		<table id="display3" border="1"  class="paginated display sortable"  >
			<thead id="thead" >
				<tr>
					<td colspan="10" class="title" >Released to Representative</td>
				</tr>
				<tr>
					<th>#</th>
					<th class="sort-numeric" >Household ID No.</th>
					<th>Name of Grantee</th>
					<th>Barangay</th>
					<th>Municipality</th> 
					<th>Set Group</th>
					<th>Date of Transaction</th> 
					<th>Date Received</th>
					<th>Time Received</th>
					<th id="id_cell9" >Amount Received</th>
					<!-- <th>Substitute</th> -->
				</tr>
			</thead>
			
			<tbody id="tbody3" >
				<%-- <% for(int i=0;i<20;i++){ %> --%>
					<tr>
						<%for(int c=0;c<10;c++){ %> 
							<th>&nbsp;</th>
						 <%} %> 
					</tr>
				<%-- <%} %> --%>
			</tbody>
			
			<tfoot id="tfoot" >
		
			</tfoot>
			 
		</table>
	</div>
	
	<div id="tRelease" >
		<label for="total_release" >Total Payments:</label>
		<input class="textField" id="total_release" value="P 0.00" name="total_release" readonly="readonly" />
	</div>
	
</div>
</div> <!-- end of main content -->

</div> <!-- end of page wrap -->


<div id="dialog-confirm" title="Logout">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="alert" title="Alert" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Theres no data to print.</p>
</div>

<div id="alert2" title="Alert" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Theres no data to download.</p>
</div>

<div id="exists" title="Data Saved" >
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><label id="mess" ></label></p>
</div>


<div id="chooseReport" class="hidden" title="Choose Report Type" >
	<form name="form_name" >
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please select the report to be printed.</p>
		<br/><input id="rel" type="radio"  name="report"/><label for="rel" >Released Report</label>
		<input id="notrel" type="radio" name="report"/><label for="notrel" >Not Released Report</label>
	</form>
</div>


<div id="input" title="File Name" >
	<p><span class="ui-icon ui-icon-document" style="float:left; margin:0 7px 20px 0;"></span>Please specify the filename.</p>
	<input class="textField" id="file" name="file" />
	<span><label id="error" ></label></span>
</div>

<div class="overlay" id="overlay"></div>
		<div id="div" class="load">
			<img alt="" src="<%=cPath %>/images/loading_transparent.gif"><br>
		</div>
<div id="ask" title="Enter a name for the excel file.">
	<p>
		<label for="fname" >File Name:</label>
		<input autocomplete="off" type="text" id="fname" />
		<span id="errmess" ></span>
	</p>
</div>
<div id="note" title="Note:">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your file is saved at <span id="reportspath" ></span> </p>
</div>


</body>
</html>