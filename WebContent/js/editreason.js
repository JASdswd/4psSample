var count=0;
	$("#Addbut").click(function(){
		count++;
		$("#tablet").append('<tr id="tago'+count+'">'+
				'<td style="font-size: 12px;font-family:monospace;" id="hin'+count+'" >Reasons:'+
					'<select id="razon'+count+'" name="reason" class="input" onchange="replace('+count+')" style="font-size: 11px;font-family:monospace;width:150px;" >'+
						'<option>Pregnant women unable to avail Pre-and Post-natal care and be attended during childbirth by a trained health professional.</option>'+
						'<option>Parents unable to attend Family Development Sessions(FDS)</option>'+
						'<option>0-5 year old children unable to receive regular preventive health checks-ups and vaccines.</option>'+
						'<option>3-5 years old children unable to attend day care or pre-school classes at least 85percent of the time.</option>'+
						'<option>6-14 years old children unable to enroll elementary or high school.</option>'+
						'<option>6-14 years old children unable to attend school at least 85percent of the time.</option>'+
						'<option>6-14 years old children unable to receive deworming pills twice a year.</option>'+
						'<option>Other Reason</option>'+
					'</select>'+
				'</td>'+
				'<td>'+
					'<a href="#" id="reject'+count+'" onclick="band('+count+')">[x]</a>'+
				'</td>'+
				'</tr>');
	});
/*$("#yeah").click(function(){
		count++;
		for(i=0;i<count;i++){
			$("#tago"+i).remove();
		}
});*/
function replace(count){
	var strbuild="";
	var reason=document.getElementById("razon"+count).value;
	if(reason=="Other Reason"){
		strbuild="<td style='font-size: 12px;font-family:monospace;' id='font-size:12px;'>Reasons:<textarea name='reason' class='input' id='razon"+count+"' style='font-size: 12px;font-family:monospace;width:150px;' ></textarea></td>";
		document.getElementById("hin"+count).innerHTML=strbuild;
	}
}
function band(count){
	$("#tago"+count).remove();
}