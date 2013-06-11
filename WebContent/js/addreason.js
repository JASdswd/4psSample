var ctr=0;

	$("#butAd").click(function(){
		ctr++;
		$("#taBle").append('<tr id="hide'+ctr+'">'+
				'<td style="font-size: 12px;font-family:monospace;" >Reasons:'+
					'<select id="alliby'+ctr+'" name="reason" onchange="change('+ctr+')" style="font-size: 11px;font-family:monospace;width:150px;" >'+
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
					'<a href="#" id="butDel'+ctr+'" onclick="Del('+ctr+')">[x]</a>'+
				'</td>'+
				'</tr>');
	});
$("#yes").click(function(){
		ctr++;
		for(i=0;i<ctr;i++){
			$("#hide"+i).remove();
		}
});
function change(count){
	var strbuild="";
	var reason=document.getElementById("alliby"+count).value;
	if(reason=="Other Reason"){
		strbuild="<tr><td style='font-size: 12px;font-family:monospace;'>Reasons:<textarea name='reason' id='alliby"+count+"' style='font-size: 12px;font-family:monospace;width:150px;' ></textarea></td>" +
				"<td><a href='#' id='butDel"+count+"' onclick='Del("+count+")'>[x]</a></td></tr>";
		document.getElementById("hide"+count).innerHTML=strbuild;
	}
}
function Del(count){
	$("#hide"+count).remove();
}