/**
 * 
 */


function getID(ID){
	return document.getElementById(ID);
}

function getIDValue(ID){
	return document.getElementById(ID).value;
}

$(function(){
	var datenow = new Date();
	var curryear = datenow.getFullYear();
	var currmonth = datenow.getMonth();
	var currday = datenow.getDate();
$( "#sdate" ).datepicker({
	changeMonth: true,
	changeYear: true,
	maxDate: new Date(curryear, currmonth, currday)
});

$( "#edate" ).datepicker({
	changeMonth: true,
	changeYear: true,
	maxDate: new Date(curryear, currmonth, currday)
});

$( "#b-day" ).datepicker({
	changeMonth: true,
	changeYear: true
});

});

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


function confirmLogout(distination){
	$( "#dialog-confirm" ).dialog({
		show: "fade",
		hide: "fade",
		resizable: false,
		height:140,
		modal: true,
		buttons: {
			"OK": function() {
				$( this ).dialog( "close" );
				document.location.href = distination;
			},
			"Cancel": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function changeMunData(data){
	/*
	var x = eval('('+data+')');
	var st= "";
	
	st += "<option> Municipality </option>";

	for(var m=0;m<x.data.length;m++){
		
			st += "<option value='"+ x.data[m].mun_id+"' > "+x.data[m].mun_name+" </option>";
		
		
	}
	
	getID("mun").innerHTML = st;
	
	document.getElementById('bname').style.display= 'inline';
	document.getElementById('mun').style.display= 'inline';*/
	
}



function changeData2(data){
	var x = eval('('+data+')');
	var str = "";
	var r =1;
	var total_release = "";
	var str2 = "";
	var r = 1;
	var total_release = "";
	var total_not_release = "";
	var cash_total = "";
	emp = 0;
	if(x.data == "" || x.data == "{}"){
		
		total_release = "P 0.00";
		getID("total_release").value = total_release;
		getID("tbody").innerHTML = "<tR><th align='center'  colspan='10' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
		getID("pageID").value = "1/1";
	}
	else{
		
		for(var i=0;i<x.data.length;i++){
			emp = 1;
			r++;
			str += "<tr>" +
						"<td></td>"+
						"<td id='id_cell'>" +x.data[i].household_id+ "</td>" +
						"<td id='id_cell'>" +x.data[i].head_name+ "</td>" +
						"<td id='id_cell'>" +x.data[i].brgy+ "</td>"+
						"<td id='id_cell'>" +x.data[i].municipality+ "</td>"+
						"<td id='id_cell'>" +x.data[i].philhealth_id+ "</td>"+
						"<td id='id_cell'>" +x.data[i].date_coverage+ "</td>"+
						"<td id='id_cell'>" +x.data[i].date_receive+ "</td>"+
						"<td id='id_cell'>" +x.data[i].time_receive+ "</td>"+
						"<td id='id_cell9'>" +x.data[i].amount_receive+ "</td>";
			str += "</tr>";			
		}
		
		for(var i=0;i<x.data2.length;i++){
			r++;
			emp = 1;
			str2 += "<tr>" +
						"<td></td>"+
						"<td id='id_cell'>" +x.data2[i].household_id+ "</td>" +
						"<td id='id_cell'>" +x.data2[i].head_name+ "</td>" +
						"<td id='id_cell'>" +x.data2[i].brgy+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].municipality+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].philhealth_id+ "</td>"+
						"<td id='id_cell'>" +x.data2[i].date_coverage+ "</td>"+
						"<td id='id_cell9'>" +x.data2[i].amount_receive+ "</td>";
			str2 += "</tr>";		
			
		}
		
		total_release = "P "+x.total_release+".00";
		total_notrelease = "P "+x.total_notrelease+".00";
		cash_total = "P "+x.cash_total+".00";
		getID("total_release").value = total_release;
		getID("total_notrelease").value = total_notrelease;
		getID("cash_total").value = cash_total;
		getID("tbody").innerHTML = str;
		getID("tbody1").innerHTML = str2;
		
		$("#display").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra']})
		.tablesorterPager({ container: $("#pagerone"), positionFixed: false 
		});
		
		$("#display").focus();
		
		
		if(x.exists == 1){
			getID("error").innerHTML = "<p style='color:red;font-size:10px;' >File Name Already Exists.</p>";
			//alert("not saved");
			
		}
		else{
			$( "#input" ).dialog( "close" );
			getID("mess").innerHTML = "<p>Please check your file at :</p><p ><u style='color:blue;' ><b>"+x.path+"</b></u></p>";
			$( "#exists" ).dialog({
				show: "drop",
				hide: "drop",
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			//alert("saved");
		}
		
	}
	
	
}

function changebrgy2(data){
	/*
	var x = eval('('+data+')');
	var st= "";
	
	st += "<option>Barangay</option>";

	for(var m=0;m<x.data.length;m++){
		
			st += "<option value='"+ x.data[m].mun_id+"' > "+x.data[m].mun_name+" </option>";
		
		
	}
	
	getID("bname").innerHTML = st;
	*/
	
}
