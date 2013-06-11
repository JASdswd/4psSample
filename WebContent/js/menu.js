

$(document).ready(function(){
	var datenow = new Date();
	var curryear = datenow.getFullYear();
	var currmonth = datenow.getMonth();
	var currday = datenow.getDate();
	$( "#coveragedate" ).datepicker({
		changeMonth: true,
		changeYear: true,
		maxDate: new Date(curryear, currmonth, currday)
	});
});

function getID(ID){
	return document.getElementById(ID);
}

function getIDValue(ID){
	return document.getElementById(ID).value;
}

var ope2;
function pop_up_boxasdf(op){
	ope2 = op;
	if(op == 0){
		$( "#regReport" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:230,
			width:410,
			modal: true,
			title: "Generate Summary Report per Municipality",
			buttons: {
				"OK": function() {
					var op;
					op = 3;
					$("#savingasdf").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("POST","<%=cPath%>/DailyReports?op="+op,prompt, "plain");
				}
			}
		});	
	}
	else if(op == 1){
		
		xhrGo("GET", "GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 2){
		xhrGo("GET", "GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 3){
		$( "#confirm-process" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:200,
			width:500,
			modal: true,
			title: "Registration Summary Total",
			buttons: {
				"OK": function() {
					var op;
					op = 2;//only municipality
					//alert("only municipality");
					$("#saving2").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("POST","<%=cPath%>/DailyReports?op="+op,prompt, "plain");
				}
			}
		});	
	}
	else if(op == 4){
		//$("#cleanListmenu").title = "Generate Household Data";
		xhrGo("GET", "<%= cPath %>/GetMunicipality", munData2asdf, "plain");
	}
	else if(op == 5){
	//	alert("in here..");
		xhrGo("POST", "<%= cPath %>/GetMunicipality", munData2jkl, "plain");
		
	}
	else if(op == 6){
		$( "#confirm-process" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:200,
			width:500,
			modal: true,
			title: "Generate System On Hold",
			buttons: {
				"OK": function() {
					//alert("only municipality");
					$("#saving2").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("GET","<%=cPath%>/SystemOnHold",prompt, "plain");
				}
			}
		});	
	}
}

function munData2asdf(data){
	var x = eval('('+data+')');
	var st= "";
	
	st = "<option value = '' >--------------</option>";

	for(var m=0;m<x.data.length;m++){
		st += "<option value='"+ x.data[m].mun_id+"'>"+x.data[m].mun_name+" </option>";
	}
	
	getID("muncmenu").innerHTML = st;
	pop_up_box2asdf();
}
function munData2jkl(data){
	var x = eval('('+data+')');
	var st= "";
	//alert(x.data);
	for(var m=0;m<x.data.length;m++){
		st += "<option value='"+ x.data[m]+"'>"+x.data[m]+" </option>";
	}
	//alert("setting value..");
	//alert(st);
	getID("team_no").innerHTML = st;
	pop_upbox2jkl();
}

function pop_upbox2jkl(){
	//alert("popdialog box..");var datenow = new Date();
	
	document.getElementById("messerr").innerHTML = "";	
	$( "#daily_RP" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:250,
		width:410,
		modal: true,
		title: "Daily Registration List w/ GRS",
		buttons: {
			"OK": function() {
				
				var sdate = document.getElementById("coveragedate").value;
				var team_num = document.getElementById("team_no").value;
				
				if(sdate != "" && team_num != ""){
					$("#saving1").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
					xhrGo("GET","<%=cPath%>/DailyReports?date="+sdate+"&team_num="+team_num,prompt, "plain");
				}
				else{
					document.getElementById("messerr").innerHTML = "Please complete your input.";	
				}
			}
		}
	});
}

function pop_up_box2asdf(){
	document.getElementById("messerr123").innerHTML = "";	
	var title_div;
	if(ope2 == 1){
		title_div = "Generate Clean List";
	}
	else if(ope2 == 2){
		title_div = "Generate Household List";
	}
	else if(ope2 == 4){
		title_div = "Registration List per Mun w/ GRS";
	}
	
	
	$( "#cleanListmenu" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:250,
		width:500,
		modal: true,
		title: title_div,
		buttons: {
			"OK": function() {
				var mun = getID("muncmenu").value;
				var op;
				if(mun != ""){
					$("#saving123").html("<p style='color:red;text-decoration:blink;font-size:12px;' >Please wait for a while. Processing Report..</p><img src='<%= cPath%>/images/276.gif' />");
				
					op = 1;
					if(ope2 == 1){
						xhrGo("POST","<%=cPath%>/GenerateCleanList?mun="+mun,prompt, "plain");
					}
					else if(ope2 == 2){
						xhrGo("POST","<%=cPath%>/SummaryReport?mun="+mun,prompt, "plain");
					}
					else if(ope2 == 4){
						xhrGo("POST","<%=cPath%>/DailyReports?op="+op+"&m="+mun,prompt, "plain");
					}
					
				}
				else{
					document.getElementById("messerr123").innerHTML = "Please complete your input.";	
				}
			}
		}
	});
}

function prompt(data){
	var x = eval('('+data+')');
	$( "#cleanListmenu" ).dialog("close");
	$("#saving123").html("");
	$("#savingasdf").html("");
	$( "#regReport" ).dialog( "close" );
	$("#saving1").html("");
	$( "#daily_RP" ).dialog( "close" );
	$("#saving2").html("");
	$( "#confirm-process" ).dialog( "close" );
	if(x.status == "not"){
		document.getElementById("menupath").innerHTML = "<u>"+x.path+"</u>";	
		document.getElementById("menumessageb").innerHTML = "Your file is saved at ";	
	}
	else if(x.status == "empty"){
		document.getElementById("menupath").innerHTML = "No data Found.</p>";
		document.getElementById("menumessageb").innerHTML = "File cannot be save.";	
	}
	else if(x.status == "emptylist"){
		document.getElementById("menupath").innerHTML = "<p>Your file is saved at <u>"+x.path+"</u></p>";
		document.getElementById("menumessageb").innerHTML = "Note:No registered Found with this date.Only GRS";	
	}
	else if(x.status == "emptygrs"){
		document.getElementById("menupath").innerHTML = "<p>Your file is saved at <u>"+x.path+"</u></p>";
		document.getElementById("menumessageb").innerHTML = "Note:No grs Found with this date.Registration list only.";	
	}
	else{
		document.getElementById("menupath").innerHTML = "<p style='color:red;font-size:11px;' >"+x.path+":File Name already exists.</p>";
		document.getElementById("menumessageb").innerHTML = "File cannot be save at ";	
	}
	$( "#menunote" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:200,
		width:500,
		modal: true,
		buttons: {
			"OK": function() {
				$( this ).dialog( "close" );
			}
		}
	});	
}