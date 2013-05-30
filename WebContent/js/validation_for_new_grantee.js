
function select_M(){
	$('.preg-show-hide').fadeOut();
	$('.hide-show-class').removeClass();
}
function select_F(){
	$('.preg-show-hide').fadeIn();
}

//VALIDATION FOR CREATING NEW GRANTEE
function validationNG(){
	var name = document.getElementById("new_head_name").value;
	var member_id = document.field_name['new_hh_mem_id'].value;
	var age = document.getElementById("new_head_age").value;
	var bday = document.getElementById("new_h_birthday").value;
	var rel = document.field_name['relation'].value;
	
	//name
	if(name == null || name == ""){
		alert('Please enter name.');
		document.getElementById("new_head_name").style.borderColor="red";						
		return false;
	}
	else{
		document.getElementById("new_head_name").style.borderColor="#8FA9BC";
	}
	//relation
	if(rel == null || rel == ""){
		alert('Please select relation');
		document.field_name['relation'].style.borderColor = 'red';
		return false;
	}
	else{
		document.field_name['relation'].style.borderColor="#8FA9BC";
	}
	//member id
	if(member_id == null || member_id == ""){
		alert('Member ID is required');
		document.field_name['new_hh_mem_id'].style.borderColor = 'red';
		return false;
	}
	else{
		document.field_name['new_hh_mem_id'].style.borderColor="#8FA9BC";
	}
	//age
	if(age == null || age == ""){
		alert("Please enter age");
		document.getElementById("new_head_age").style.borderColor="red";
		return false;
	}
	else if(age>110){
		alert("Invalid input of age");
		document.getElementById("new_head_age").style.borderColor="red";
		return false;
	}	
	else{
		document.getElementById("new_head_age").style.borderColor="#8FA9BC";
	}
	//birthday
	if(bday == null || bday == ""){
		alert("Please select birthday");
		document.getElementById("new_h_birthday").style.borderColor="red";
		return false;
	}
	else{
		document.getElementById("new_h_birthday").style.borderColor="#8FA9BC";
	}
	//for radio buttons
	function checkRadio(frmName, rbGroupName){
		var radios = document[frmName].elements[rbGroupName]; 
		for (var i=0; i <radios.length; i++) { 
			if (radios[i].checked) { 
			   return true;
			}
		}
		return false;
	}
	if(!checkRadio('field_name','new_grantee_gen')){ //validation for gender
		alert("Please select gender");
		return false;
	}

	return true;
	
}
//VALIDATION WIFE
function validationWife(){
	alert("wife");
	var name = document.getElementById("wife_name_g").value;
	var age = document.getElementById("wife_age").value;
	var bday = document.getElementById("wife_bday").value;

	//name
	if(name == null || name == ""){
		alert('Please enter name.');
		document.getElementById("wife_name_g").style.borderColor="red";						
		return false;
	}
	else{
		document.getElementById("wife_name_g").style.borderColor="#8FA9BC";
	}
	//age
	if(age == null || age == ""){
		alert("Please enter age");
		document.getElementById("wife_age").style.borderColor="red";
		return false;
	}
	else if(age>110){
		alert("Invalid input of age");
		document.getElementById("wife_age").style.borderColor="red";
		return false;
	}	
	else{
		document.getElementById("wife_age").style.borderColor="#8FA9BC";
	}
	//birthday
	if(bday == null || bday == ""){
		alert("Please select birthday");
		document.getElementById("wife_bday").style.borderColor="red";
		return false;
	}
	else{
		document.getElementById("wife_bday").style.borderColor="#8FA9BC";
	}
	//for radio buttons
	function checkRadio(frmName, rbGroupName){
		var radios = document[frmName].elements[rbGroupName]; 
		for (var i=0; i <radios.length; i++) { 
			if (radios[i].checked) { 
			   return true;
			}
		}
		return false;
	}
	if(!checkRadio('wife_field','wife_grantee_gen')){ //validation for gender
		alert("Please select gender");
		return false;
	}

	return true;
	
}
//VALIDATION SON AND DAUGHTER
function validationChildren(){
	alert("CHILDREN");
	var name = document.getElementById("usd_name").value;
	var age = document.getElementById("usd_age").value;
	var bday = document.getElementById("sd_birthday").value;

	//name
	if(name == null || name == ""){
		alert('Please enter name.');
		document.getElementById("usd_name").style.borderColor="red";						
		return false;
	}
	else{
		document.getElementById("usd_name").style.borderColor="#8FA9BC";
	}
	//age
	if(age == null || age == ""){
		alert("Please enter age");
		document.getElementById("usd_age").style.borderColor="red";
		return false;
	}
	else if(age>110){
		alert("Invalid input of age");
		document.getElementById("usd_age").style.borderColor="red";
		return false;
	}	
	else{
		document.getElementById("usd_age").style.borderColor="#8FA9BC";
	}
	//birthday
	if(bday == null || bday == ""){
		alert("Please select birthday");
		document.getElementById("sd_birthday").style.borderColor="red";
		return false;
	}
	else{
		document.getElementById("sd_birthday").style.borderColor="#8FA9BC";
	}
	//for radio buttons
	function checkRadio(frmName, rbGroupName){
		var radios = document[frmName].elements[rbGroupName]; 
		for (var i=0; i <radios.length; i++) { 
			if (radios[i].checked) { 
			   return true;
			}
		}
		return false;
	}
	if(!checkRadio('child_form_name','children_grantee_gen')){ //validation for gender
		alert("Please select gender");
		return false;
	}

	return true;
	
}
//VALIDATION GRANDSON AND GRANDAUGHTER
function validationGrandchild(){
	alert("GRANDCHILD");
	var name = document.getElementById("ugg_id_name").value;
	var age = document.getElementById("ugg_age").value;
	var bday = document.getElementById("ugg_birthday").value;
	
	//name
	if(name == null || name == ""){
		alert('Please enter name.');
		document.getElementById("ugg_id_name").style.borderColor="red";						
		return false;
	}
	else{
		document.getElementById("ugg_id_name").style.borderColor="#8FA9BC";
	}
	//age
	if(age == null || age == ""){
		alert("Please enter age");
		document.getElementById("ugg_age").style.borderColor="red";
		return false;
	}
	else if(age>110){
		alert("Invalid input of age");
		document.getElementById("ugg_age").style.borderColor="red";
		return false;
	}	
	else{
		document.getElementById("ugg_age").style.borderColor="#8FA9BC";
	}
	//birthday
	if(bday == null || bday == ""){
		alert("Please select birthday");
		document.getElementById("ugg_birthday").style.borderColor="red";
		return false;
	}
	else{
		document.getElementById("ugg_birthday").style.borderColor="#8FA9BC";
	}
	//for radio buttons
	function checkRadio(frmName, rbGroupName){
		var radios = document[frmName].elements[rbGroupName]; 
		for (var i=0; i <radios.length; i++) { 
			if (radios[i].checked) { 
			   return true;
			}
		}
		return false;
	}
	if(!checkRadio('grandchild_form_name','grandchild_grantee_gen')){ //validation for gender
		alert("Please select gender");
		return false;
	}

	return true;
	
}