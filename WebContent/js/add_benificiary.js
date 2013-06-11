
	var sd_ctr = 0;
	var gg_ctr = 0;
	var datenow = new Date();
	var curryear = datenow.getFullYear();
	var currmonth = datenow.getMonth();
	var currday = datenow.getDate();
	function confirmlog(){
			var log = window.confirm("Anata wa roguauto shite yoroshiidesu ka?");
			if(log == true){
				
				return true;
			}
			
			return false;
	}
	
	
	function calendar(ID){
		$( "#"+ID ).datepicker({
			changeMonth: true,
			changeYear: true
		});
	}
	
	$(function(){
		
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
		$("#sd_Addmore").button({
			icons : {primary : "ui-icon-circle-plus" }
		});
		$("#gg_Addmore").button({
			icons : {primary : "ui-icon-circle-plus" }
		});
		$("#save").button({
			icons : {primary : "ui-icon-circle-check" }
		});
		$("#cancel").button({
			icons : {primary : "ui-icon-circle-close" }
		});
		
		$("#sd_Addmore").click(function(){
			sd_ctr++;
			$( "#sonDaugther" ).append('<div id="addmore'+sd_ctr+'"><label>Son/Daughter :</label>'+
					'<input type="text" name="sd_name" size="30" class="input" autocomplete = "off"/>'+
					'<div class="indent"><label>Household Member ID :</label><input type="text" name="sd_EntryID" class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
					'<label> Age :</label><input type="text" name="sd_age" maxlength="3" size="5" class="input" onkeypress="return numbersonly1(event,false);" autocomplete = "off"/><label>'+
					' Birthday :</label><input type="text" name="sd_bday" class="input" id="bd'+sd_ctr+'" onclick="calendar(this.id);" autocomplete = "off"/>'+
					/*' Birthday :</label><select name="sd_month">'+
								'<option value="">month</option>'+
								
								'<option  value="1">Jan</option>'+
								'<option  value="2">Feb</option>'+
								'<option  value="3">Mar</option>'+
								'<option  value="4">Apr</option>'+
								'<option  value="5">May</option>'+
								'<option  value="6">June</option>'+
								'<option  value="7">July</option>'+
								'<option  value="8">Aug</option>'+
								'<option  value="9">Sept</option>'+
								'<option  value="10">Oct</option>'+
								'<option  value="11">Nov</option>'+
								'<option  value="12">Dec</option>'+
							
						'</select>'+
						'<input type="text" maxlength="2" size="2" name="sd_day" class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
						'<input type="text" maxlength="4" size="4" name="sd_year" class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
						*/'<input type="hidden" name="sd_pregnantCtrl" value="'+sd_ctr+'"/>'+
						'<input type="hidden" name="sd_attendingSchoolCtrl" value="'+sd_ctr+'" />'+
						
					'<br/><label>Pregnant:</label><input type="radio" value="true" name="sd_pregnant'+sd_ctr+'" id="sd_P_yes'+sd_ctr+'"/><label class="radio" for="sd_P_yes'+sd_ctr+'">Yes</label><input type="radio" value="false" name="sd_pregnant'+sd_ctr+'" id="sd_P_no'+sd_ctr+'"/>'+
					'<label  class="radio" for="sd_P_no'+sd_ctr+'">No</label><label  class="attending">Attending School:</label><input type="radio" value="true" name="sd_attendingSchool'+sd_ctr+'" id="sd_AS_yes'+sd_ctr+'"/><label class="radio" for="sd_AS_yes'+sd_ctr+'">Yes</label> <input type="radio" value="false" name="sd_attendingSchool'+sd_ctr+'" id="sd_AS_no'+sd_ctr+'"/><label class="radio" for="sd_AS_no'+sd_ctr+'">No</label>'+
					'</div><button class="sd_remove" type="button" onclick="sd_addmore('+sd_ctr+')">Cancel</button><br/><hr/><br/></div>');
			calendar('bd'+sd_ctr);
		});
		
		$("#gg_Addmore").click(function(){
			gg_ctr++;
			$("#grandsonGrandaugther").append('<div id="gg_add'+gg_ctr+'"><label>Grandson/Grandaughter :</label>'+
					'<input type="text" value="" size="30" name="gg_name" class="input" autocomplete = "off"/>'+
					'<div class="indent"><label>Household Member ID :</label><input type="text" name="gg_EntryID" value="" class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
					'<label> Age :</label><input type="text" name="gg_age" value="" maxlength="3" size="5" class="input" onkeypress="return numbersonly1(event,false);" autocomplete = "off"/><label>'+
					' Birthday :</label><input type="text" name="gg_bday" class="input" id="gg_bd'+gg_ctr+'" autocomplete = "off"/>'+
								/*'<option value="" selected>month</option>'+
								
								'<option  value="1">Jan</option>'+
								'<option  value="2">Feb</option>'+
								'<option  value="3">Mar</option>'+
								'<option  value="4">Apr</option>'+
								'<option  value="5">May</option>'+
								'<option  value="6">June</option>'+
								'<option  value="7">July</option>'+
								'<option  value="8">Aug</option>'+
								'<option  value="9">Sept</option>'+
								'<option  value="10">Oct</option>'+
								'<option  value="11">Nov</option>'+
								'<option  value="12">Dec</option>'+
							
						'</select>'+
						'<input type="text" maxlength="2" size="2" name="gg_day"  class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
						'<input type="text" maxlength="4" size="4" name="gg_year" class="input" onkeypress="return numbersonly(event,false);" autocomplete = "off"/>'+
						*/'<input type="hidden" name="gg_pregnantCtrl" value="'+gg_ctr+'"/>'+
						'<input type="hidden" name="gg_attendingSchoolCtrl" value="'+gg_ctr+'" />'+
					'<br/><label>Pregnant:</label><input type="radio" value="true" name="gg_pregnant'+gg_ctr+'" id="gg_P_yes'+gg_ctr+'"/><label class="radio" for="gg_P_yes'+gg_ctr+'">Yes</label><input type="radio" value="false" name="gg_pregnant'+gg_ctr+'" id="gg_P_no'+gg_ctr+'"/>'+
					'<label for="gg_P_no'+gg_ctr+'" class="radio">No</label><label  class="attending">Attending School:</label><input type="radio" value="true" name="gg_attendingSchool'+gg_ctr+'" id="gg_AS_yes'+gg_ctr+'"/><label class="radio" for="gg_AS_yes'+gg_ctr+'">Yes</label> <input type="radio" value="false" name="gg_attendingSchool'+gg_ctr+'" id="gg_AS_no'+gg_ctr+'"/><label class="radio" for="gg_AS_no'+gg_ctr+'">No</label>'+
					'</div><button class="sd_remove" type="button" onclick="gg_addmore('+gg_ctr+')">Cancel</button><br/><hr/><br/></div>');
			calendar('gg_bd'+gg_ctr);
		});
		$( "#h_birthday" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});
		$( "#h_birthday" ).datepicker( "option", "showAnim", "show" );
		$( "#bd" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});
		$( "#gg_bd" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});

		$( "#ws_birthday" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});
		$( "#ws_birthday" ).datepicker( "option", "showAnim", "show" );
		$( "#sd_birthday" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});
		$( "#sd_birthday" ).datepicker( "option", "showAnim", "show" );
		$( "#gg_birthday" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(curryear, currmonth, currday)
		});
		$( "#gg_birthday" ).datepicker( "option", "showAnim", "show" );
		
		
	});

	function sd_addmore(count){
		//$("#addmore"+count).remove();
		setTimeout(function(){
			$("#addmore"+count).slideUp("normal",function(){
				$("#addmore"+count).remove();
			});
		}, 200);
	}
	function gg_addmore(count){
		//$("#gg_add"+count).remove();
		setTimeout(function(){
			$("#gg_add"+count).slideUp("normal",function(){
				$("#gg_add"+count).remove();
			});
		}, 200);
	}
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
	
	function checkRadio(frmName, rbGroupName){
		var radios = document[frmName].elements[rbGroupName]; 
		for (var i=0; i <radios.length; i++) { 
			if (radios[i].checked) { 
			   return true;
			}
		}
		return false;
	}
	
	function validateWS(){
		var ws_name = document.getElementById("v_ws_name");
		if(ws_name.value=="N/A"){
			return true;
		}
		else{
		if(ws_name.value==null || ws_name.value==""){
			ws_name.style.borderColor="red";
			alert("Please enter name of wife.");
			return false; 
		}
		else{
			ws_name.style.borderColor="#8FA9BC";
		}
		var ws_id = document.getElementById("v_ws_id");
		if(ws_id.value==null || ws_id.value==""){
			ws_id.style.borderColor="red";
			alert("Wife/Spouse member ID is required.");
			return false;
		}
		else{
			ws_id.style.borderColor="#8FA9BC";
		}
		var ws_age = document.getElementById("v_ws_age");
		if(ws_age.value==null || ws_age.value==""){
			ws_age.style.borderColor="red";
			alert("Please enter age of wife/spouse.");
			return false;
		}
		else if(ws_age.value>110 || ws_age.value<0){
			ws_age.style.borderColor="red";
			alert("Invalid input of age of wife/spouse.");
			return false;
		}
		else{
			ws_age.style.borderColor="#8FA9BC";
		}
		var ws_birthday = document.getElementById("ws_birthday");
		if(ws_birthday.value==null || ws_birthday.value==""){
			ws_birthday.style.borderColor="red";
			alert("Please enter birthday of wife/spouse.");
			return false;
		}
		else{
			ws_birthday.style.borderColor="#8FA9BC";
		}
		/*if(!checkRadio('transactionForm','ws_pregnant')){
			alert("Please select if wife is pregnant.");
			return false;
		}
		if(!checkRadio('transactionForm','ws_attendingSchool')){
			alert("Please select if wife/spouse is attending school.");
			return false;
		}*/
		return true;
		}
	}
	
	function validateSD(){
		var sd_name_ctr = 0;
		var sd_id_ctr = 0;
		var sd_age_ctr = 0;
		var sd_ageLimit_ctr = 0;
		var sd_birthday_ctr = 0;
		/*var sd_month_ctr = 0;
		var sd_day_ctr = 0;
		var sd_dayLimit_ctr = 0;
		var sd_year_ctr = 0;
		var sd_yearLimit_ctr = 0;*/
		var sd_pregnant_ctr = 0;
		var sd_attending_ctr = 0;
		var sd_name = document.getElementsByName("sd_name");
		var sd_id = document.getElementsByName("sd_EntryID");
		var sd_age = document.getElementsByName("sd_age");
		var sd_birthday = document.getElementsByName("sd_bday");
		/*var sd_month = document.getElementsByName("sd_month");
		var sd_day = document.getElementsByName("sd_day");
		var sd_year = document.getElementsByName("sd_year");
		var d = new Date();
		var currYear = d.getFullYear();*/
		for ( var i = 0; i < sd_name.length; i++) {
			if(sd_name[i].value==null || sd_name[i].value==""){
				sd_name[i].style.borderColor="red";
				sd_name_ctr++;
				break;
			}
			else{
				sd_name[i].style.borderColor="#8FA9BC";
			}
			if(sd_id[i].value==null || sd_id[i].value==""){
				sd_id[i].style.borderColor="red";
				sd_id_ctr++;
				break;
			}
			else{
				sd_id[i].style.borderColor="#8FA9BC";
			}
			if(sd_age[i].value==null || sd_age[i].value==""){
				sd_age[i].style.borderColor="red";
				sd_age_ctr++;
				break;
			}
			else if(sd_age[i].value>110 || sd_age[i].value<0){
				sd_age[i].style.borderColor="red";
				sd_ageLimit_ctr++;
				break;
			}
			else{
				sd_age[i].style.borderColor="#8FA9BC";
			}
			if(sd_birthday[i].value==null || sd_birthday[i].value==""){
				sd_birthday[i].style.borderColor="red";
				sd_birthday_ctr++;
				break;
			}
			else{
				sd_birthday[i].style.borderColor="#8FA9BC";
			}
			/*if(sd_month[i].value==null || sd_month[i].value==""){
				sd_month[i].style.borderColor="red";
				sd_month_ctr++;
				break;
			}
			else{
				sd_month[i].style.borderColor="#8FA9BC";
			}
			if(sd_day[i].value==null || sd_day[i].value==""){
				sd_day[i].style.borderColor="red";
				sd_day_ctr++;
				break;
			}
			else if(sd_day[i].value>=32 || sd_day[i].value<0){
				sd_day[i].style.borderColor="red";
				sd_dayLimit_ctr++;
				break;
			}
			else{
				sd_day[i].style.borderColor="#8FA9BC";
			}
			if(sd_year[i].value==null || sd_year[i].value==""){
				sd_year[i].style.borderColor="red";
				sd_year_ctr++;
				break;
			}
			else if(sd_year[i].value>currYear || sd_year[i].value<1945){
				sd_year[i].style.borderColor="red";
				sd_yearLimit_ctr++;
				break;
			}
			else{
				sd_year[i].style.borderColor="#8FA9BC";
			}*/
		}	// END of for loop
		if(sd_name_ctr>0){
			alert("Please enter name of son/daughter");
			return false;
		}
		if(sd_id_ctr>0){
			alert("Son/daugther member ID is required.");
			return false;
		}
		if(sd_age_ctr>0){
			alert("Please enter age of son/daughter.");
			return false;
		}
		if(sd_ageLimit_ctr>0){
			alert("Invalid input of age of son/daughter.");
			return false;
		}
		if(sd_birthday_ctr>0){
			alert("Please enter birthday of son/daughter.");
			return false;
		}
		/*if(sd_month_ctr>0){
			alert("Please select month of birthday of son/daughter.");
			return false;
		}
		if(sd_day_ctr>0){
			alert("Please enter day of birthday of son/daughter.");
			return false;
		}
		if(sd_dayLimit_ctr>0){
			alert("Invalid input of birthday of son/daughter.");
			return false;
		}
		if(sd_year_ctr>0){
			alert("Please enter year of birthday of son/daughter.");
			return false;
		}
		if(sd_yearLimit_ctr>0){
			alert("Invalid input of birthday of son/daughter.");
			return false;
		}*/
		return true;
		
	}
	
	function validateGG(){
		var gg_name_ctr = 0;
		var gg_id_ctr = 0;
		var gg_age_ctr = 0;
		var gg_ageLimit_ctr = 0;
		/*var gg_month_ctr = 0;
		var gg_day_ctr = 0;
		var gg_dayLimit_ctr = 0;
		var gg_year_ctr = 0;
		var gg_yearLimit_ctr = 0;*/
		var gg_bday_ctr = 0;
		var gg_name = document.getElementsByName("gg_name");
		var gg_id = document.getElementsByName("gg_EntryID");
		var gg_age = document.getElementsByName("gg_age");
		var gg_bday = document.getElementsByName("gg_bday");
		/*var gg_month = document.getElementsByName("gg_month");
		var gg_day = document.getElementsByName("gg_day");
		var gg_year = document.getElementsByName("gg_year");
		var getdate = new Date();
		var current_year = getdate.getFullYear();*/
		if(gg_name.length>1){
			for(var y = 0; y<gg_name.length; y++){
				if(gg_name[y].value==null || gg_name[y].value==""){
					gg_name[y].style.borderColor="red";
					gg_name_ctr++;
					break;
				}
				else{
					gg_name[y].style.borderColor="#8FA9BC";
				}
				if(gg_id[y].value==null || gg_id[y].value==""){
					gg_id[y].style.borderColor="red";
					gg_id_ctr++;
					break;
				}
				else{
					gg_id[y].style.borderColor="#8FA9BC";
				}
				if(gg_age[y].value=="" || gg_age[y].value==null){
					gg_age[y].style.borderColor="red";
					gg_age_ctr++;
					break;
				}
				else if(gg_age[y].value>110 || gg_age[y].value<0){
					gg_age[y].style.borderColor="red";
					gg_ageLimit_ctr++;
					break;
				}
				else{
					gg_age[y].style.borderColor="#8FA9BC";
				}
				if(gg_bday[y].value==null || gg_bday[y].value==""){
					gg_bday[y].style.borderColor="red";
					gg_bday_ctr++;
					break;
				}
				else{
					gg_bday[y].style.borderColor="#8FA9BC";
				}
				/*if(gg_month[y].value==null || gg_month[y].value==""){
					gg_month[y].style.borderColor="red";
					gg_month_ctr++;
					break;
				}
				else{
					gg_month[y].style.borderColor="#8FA9BC";
				}
				if(gg_day[y].value==null || gg_day[y].value==""){
					gg_day[y].style.borderColor="red";
					gg_day_ctr++;
					break;
				}
				else if(gg_day[y].value>=32 || gg_day[y].value<0){
					gg_day[y].style.borderColor="red";
					gg_dayLimit_ctr++;
					break;
				}
				else{
					gg_day[y].style.borderColor="#8FA9BC";
				}
				if(gg_year[y].value==null || gg_year[y].value==""){
					gg_year[y].style.borderColor="red";
					gg_year_ctr++;
					break;
				}
				else if(gg_year[y].value>current_year || gg_year[y].value<1921){
					gg_year[y].style.borderColor="red";
					gg_yearLimit_ctr++;
					break;
				}
				else{
					gg_year[y].style.borderColor="#8FA9BC";
				}*/
			} // END of for loop
		}
		else{
			for(var x=0;x< gg_name.length;x++){
				gg_name[x].style.borderColor="#8FA9BC";
				if(gg_name[x].value==null || gg_name[x].value=="" ){
					return true;
				}
				else{
					if(gg_id[x].value==null || gg_id[x].value==""){
						gg_id[x].style.borderColor="red";
						gg_id_ctr++;
						break;
					}
					else{
						gg_id[x].style.borderColor="#8FA9BC";
					}
					if(gg_age[x].value=="" || gg_age[x].value==null){
						gg_age[x].style.borderColor="red";
						gg_age_ctr++;
						break;
					}
					else if(gg_age[x].value>110 || gg_age[x].value<0){
						gg_age[x].style.borderColor="red";
						gg_ageLimit_ctr++;
						break;
					}
					else{
						gg_age[x].style.borderColor="#8FA9BC";
					}
					if(gg_bday[x].value==null || gg_bday[x].value==""){
						gg_bday[x].style.borderColor="red";
						gg_bday_ctr++;
						break;
					}
					else{
						gg_bday[x].style.borderColor="#8FA9BC";
					}
					/*if(gg_month[x].value==null || gg_month[x].value==""){
						gg_month[x].style.borderColor="red";
						gg_month_ctr++;
						break;
					}
					else{
						gg_month[x].style.borderColor="#8FA9BC";
					}
					if(gg_day[x].value==null || gg_day[x].value==""){
						gg_day[x].style.borderColor="red";
						gg_day_ctr++;
						break;
					}
					else if(gg_day[x].value>=32 || gg_day[x].value<0){
						gg_day[x].style.borderColor="red";
						gg_dayLimit_ctr++;
						break;
					}
					else{
						gg_day[x].style.borderColor="#8FA9BC";
					}
					if(gg_year[x].value==null || gg_year[x].value==""){
						gg_year[x].style.borderColor="red";
						gg_year_ctr++;
						break;
					}
					else if(gg_year[x].value>current_year || gg_year[x].value<1921){
						gg_year[x].style.borderColor="red";
						gg_yearLimit_ctr++;
						break;
					}
					else{
						gg_year[x].style.borderColor="#8FA9BC";
					}*/
				}
			}// END of for loop
		}
		if(gg_name_ctr>0){
			alert("Please enter name of grandson/grandaughter.");
			return false;
		}
		if(gg_id_ctr>0){
			alert("Grandson/grandaughter member ID is required.");
			return false;
		}
		if(gg_age_ctr>0){
			alert("Please enter age of grandson/grandaughter.");
			return false;
		}
		if(gg_ageLimit_ctr>0){
			alert("Invalid input of age of grandson/grandaughter.");
			return false;
		}
		if(gg_bday_ctr>0){
			alert("Please enter birthday of grandson/grandaughter.");
			return false;
		}
		/*if(gg_month_ctr>0){
			alert("Please select month of birthday of grandson/grandaughter.");
			return false;
		}
		if(gg_day_ctr>0){
			alert("Please enter day of birthday of grandson/grandaughter.");
			return false;
		}
		if(gg_dayLimit_ctr>0){
			alert("Invalid input of birthday of grandson/grandaughter.");
			return false;
		}
		if(gg_year_ctr>0){
			alert("Please enter year of birthday of grandson/grandaughter.");
			return false;
		}
		if(gg_yearLimit_ctr>0){
			alert("Invalid input of birthday of grandson/grandaughter.");
			return false;
		}*/
		return true;
	}
	
	function validateAddress(){
		var street = document.getElementById("street");
		if(street.value==null || street.value==""){
			street.style.borderColor="red";
			alert("Please enter value of street.");
			return false;
		}
		else{
			street.style.borderColor="#8FA9BC";
		}
		var purok = document.getElementById("purok");
		if(purok.value==null || purok.value==""){
			purok.style.borderColor="red";
			alert("Please enter value of purok.");
			return false;
		}
		else{
			purok.style.borderColor="#8FA9BC";
		}
		var municipality = document.getElementById("mun_name");
		if(municipality.value==null || municipality.value=="0"){
			municipality.style.borderColor="red";
			alert("Municipality is required.");
			return false;
		}
		else{
			municipality.style.borderColor="#8FA9BC";
		}
		var barangay = document.getElementById("brgy_name");
		if(barangay.value==null || barangay.value=="0"){
			barangay.style.borderColor="red";
			alert("Please enter value of barangay.");
			return false;
		}
		else{
			barangay.style.borderColor="#8FA9BC";
		}
		
		
		return true;
	}
	
	function addProfileValidation(){
		var h_id = document.getElementById("v_hh_id");
		if(h_id.value==null || h_id.value==""){
			h_id.focus;
			h_id.style.borderColor="red";
			alert("Household ID is required.");
			return false;
		}
		else{
			h_id.style.borderColor="#8FA9BC";
		}
		var head_name = document.getElementById("v_head_name");
		if(head_name.value==null || head_name.value ==""){
			head_name.style.borderColor="red";
			alert("Please enter name of household.");
			return false;
		}
		else{
			head_name.style.borderColor="#8FA9BC";
		}
		var head_id = document.getElementById("v_head_id");
		if(head_id.value==null || head_id.value==""){
			head_id.style.borderColor="red";
			alert("household member ID is required.");
			return false;
		}
		else{
			head_id.style.borderColor="#8FA9BC";
		}
		var head_age = document.getElementById("v_head_age");
		if(head_age.value==null || head_age.value==""){
			head_age.style.borderColor="red";
			alert("Please enter age of household.");
			return false;
		}
		else if(head_age.value>110 || head_age.value<0){
			head_age.style.borderColor="red";
			alert("Invalid input of age of household.");
			return false;
		}
		else{
			head_age.style.borderColor="#8FA9BC";
		}
		var head_birthday = document.getElementById("h_birthday");
		if(head_birthday.value==null || head_birthday.value==""){
			head_birthday.style.borderColor="red";
			alert("Please enter birthday of household.");
			return false;
		}
		else{
			head_birthday.style.borderColor="#8FA9BC";
		}
		if(!checkRadio('transactionForm','h_gender')){
			alert("Please select gender of household.");
			return false;
		}
		
		var f_position = document.getElementById("rel");
		if(f_position.value == null || f_position.value == ""){
			f_position.style.borderColor = "red";
			alert("Please select the relation  of the grantee to the head.");
			return false;
		}
		else{
			f_position.style.borderColor = "#8FA9BC";
		}
		/*if(!checkRadio('transactionForm','h_pregnant')){
			alert("Please select if head is pregnant.");
			return false;
		}
		if(!checkRadio('transactionForm','h_attendingSchool')){
			alert("Please select if head is attending school.");
			return false;
		}*/
		
		// validate wife/spouse..
		if(!validateWS()){
			return false;
		}
		if(!validateSD()){
			return false;
		}
		if(!validateGG()){
			return false;
		}
		if(!validateAddress()){
			return false;
		}
		return true;
	}