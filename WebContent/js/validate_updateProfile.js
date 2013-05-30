

function validate_h_info() {
	var household_id = document.getElementById("hh_idNumber");
	if(household_id.value==null || household_id.value==""){
		household_id.style.borderColor="red";
		alert("Household ID is required.");
		return false;
	}
	else{
		household_id.style.borderColor="#8FA9BC";
	}
	var head_name = document.getElementById("u_head_name");
	if(head_name.value==null || head_name.value==""){
		head_name.style.borderColor="red";
		alert("Please enter name of household.");
		return false;
	}
	else{
		head_name.style.borderColor="#8FA9BC";
	}
	var head_memberID = document.getElementById("h_EntryId");
	if(head_memberID.value==null || head_memberID.value==""){
		head_memberID.style.borderColor="red";
		alert("Household member ID is required.");
		return false;
	}
	else{
		head_memberID.style.borderColor="#8FA9BC";
	}
	var head_age = document.getElementById("h_age");
	if(head_age.value==null || head_age.value==""){
		head_age.style.borderColor="red";
		alert("Please enter age of household.");
		return false;
	}
	else if(head_age.value>110){
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
	return true;
}

function validate_ws_info(){
	var ws_name = document.getElementById("ws_name");
	if(ws_name.value==null || ws_name.value==""){
		ws_name.style.borderColor="red";
		alert("Please enter name of wife/spouse.");
		return false;
	}
	else{
		ws_name.style.borderColor="#8FA9BC";
	}
	var ws_memberID = document.getElementById("ws_EntryId");
	if(ws_memberID.value==null || ws_memberID.value==""){
		ws_memberID.style.borderColor="red";
		alert("Wife/Spouse member ID is required.");
		return false;
	}
	else{
		ws_memberID.style.borderColor="#8FA9BC";
	}
	var ws_age = document.getElementById("ws_age");
	if(ws_age.value==null || ws_age.value==""){
		ws_age.style.borderColor="red";
		alert("Please enter age of wife/spouse.");
		return false;
	}
	else if(ws_age.value>110){
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
	return true;
}
function validate_sd_info(){
	var sd_name = document.getElementById("usd_name");
	if(sd_name.value==null || sd_name.value==""){
		sd_name.style.borderColor="red";
		alert("Please enter name of son/daughter.");
		return false;
	}
	else{
		sd_name.style.borderColor="#8FA9BC";
	}
	var sd_memberID = document.getElementById("usd_memberID");
	if(sd_memberID.value==null || sd_memberID.value==""){
		sd_memberID.style.borderColor="red";
		alert("Son/Daughter member ID is required.");
		return false;
	}
	else{
		sd_memberID.style.borderColor="#8FA9BC";
	}
	var sd_age = document.getElementById("usd_age");
	if(sd_age.value==null || sd_age.value==""){
		sd_age.style.borderColor="red";
		alert("Please enter age of son/daughter.");
		return false;
	}
	else if(sd_age.value>110){
		sd_age.style.borderColor="red";
		alert("Invalid input of age of son/daugther.");
		return false;
	}
	else{
		sd_age.style.borderColor="#8FA9BC";
	}
	var sd_birthday = document.getElementById("sd_birthday");
	if(sd_birthday.value==null || sd_birthday.value==""){
		sd_birthday.style.borderColor="red";
		alert("Please enter birthday of son/daughter.");
		return false;
	}
	else{
		sd_birthday.style.borderColor="#8FA9BC";
	}
	return true;
}

function validate_gg_info(){
	var gg_name = document.getElementById("ugg_name");
	if(gg_name.value==null || gg_name.value==""){
		gg_name.style.borderColor="red";
		alert("Please enter name of grandson/grandaughter.");
		return false;
	}
	else{
		gg_name.style.borderColor="#8FA9BC";
	}
	var gg_memberID = document.getElementById("ugg_memberID");
	if(gg_memberID.value==null || gg_memberID.value==""){
		gg_memberID.style.borderColor="red";
		alert("Grandson/Grandaughter member ID is required.");
		return false;
	}
	else{
		gg_memberID.style.borderColor="#8FA9BC";
	}
	var gg_age = document.getElementById("ugg_age");
	if(gg_age.value==null || gg_age.value==""){
		gg_age.style.borderColor="red";
		alert("Please enter age of grandson/grandaughter.");
		return false;
	}
	else if(gg_age.value>110){
		gg_age.style.borderColor="red";
		alert("Invalid input of age of grandson/grandaugther.");
		return false;
	}
	else{
		gg_age.style.borderColor="#8FA9BC";
	}
	var gg_birthday = document.getElementById("gg_birthday");
	if(gg_birthday.value==null || gg_birthday.value==""){
		gg_birthday.style.borderColor="red";
		alert("Please enter birthday of grandson/grandaughter.");
		return false;
	}
	else{
		gg_birthday.style.borderColor="#8FA9BC";
	}
	return true;
}

function checkRadio1(frmName, rbGroupName){
	var radios = document[frmName].elements[rbGroupName]; 
	for (var i=0; i <radios.length; i++) { 
		if (radios[i].checked) { 
		   return true;
		}
	}
	return false;
}

function validate_asd(){
	var asd_name = document.getElementById("asd_name");
	if(asd_name.value==null || asd_name.value==""){
		asd_name.style.borderColor="red";
		alert("Please enter name of son/daughter.");
		return false; 
	}
	else{
		asd_name.style.borderColor="#8FA9BC";
	}
	var asd_id = document.getElementById("asd_memberID");
	if(asd_id.value==null || asd_id.value==""){
		asd_id.style.borderColor="red";
		alert("Son/Daughter member ID is required.");
		return false;
	}
	else{
		asd_id.style.borderColor="#8FA9BC";
	}
	var asd_age = document.getElementById("asd_age");
	if(asd_age.value==null || asd_age.value==""){
		asd_age.style.borderColor="red";
		alert("Please enter age of son/daughter.");
		return false;
	}
	else if(asd_age.value>110 || asd_age.value<0){
		asd_age.style.borderColor="red";
		alert("Invalid input of age of son/daughter.");
		return false;
	}
	else{
		asd_age.style.borderColor="#8FA9BC";
	}
	var asd_birthday = document.getElementById("asd_birthday");
	if(asd_birthday.value==null || asd_birthday.value==""){
		asd_birthday.style.borderColor="red";
		alert("Please enter birthday of son/daughter.");
		return false;
	}
	else{
		asd_birthday.style.borderColor="#8FA9BC";
	}
	if(!checkRadio1('form_sd_add','asd_pregnant')){
		alert("Please select if daughter is pregnant.");
		return false;
	}
	if(!checkRadio1('form_sd_add','asd_attending')){
		alert("Please select if son/daughter is attending school.");
		return false;
	}
	return true;
	
}

function validate_agg(){
	var agg_name = document.getElementById("agg_name");
	if(agg_name.value==null || agg_name.value==""){
		agg_name.style.borderColor="red";
		alert("Please enter name of grandson/grandaughter.");
		return false; 
	}
	else{
		agg_name.style.borderColor="#8FA9BC";
	}
	var agg_id = document.getElementById("agg_memberID");
	if(agg_id.value==null || agg_id.value==""){
		agg_id.style.borderColor="red";
		alert("grandson/grandaughter member ID is required.");
		return false;
	}
	else{
		agg_id.style.borderColor="#8FA9BC";
	}
	var agg_age = document.getElementById("agg_age");
	if(agg_age.value==null || agg_age.value==""){
		agg_age.style.borderColor="red";
		alert("Please enter age of grandson/grandaughter.");
		return false;
	}
	else if(agg_age.value>110 || agg_age.value<0){
		agg_age.style.borderColor="red";
		alert("Invalid input of age of grandson/grandaughter.");
		return false;
	}
	else{
		agg_age.style.borderColor="#8FA9BC";
	}
	var agg_birthday = document.getElementById("agg_birthday");
	if(agg_birthday.value==null || agg_birthday.value==""){
		agg_birthday.style.borderColor="red";
		alert("Please enter birthday of grandson/grandaughter.");
		return false;
	}
	else{
		agg_birthday.style.borderColor="#8FA9BC";
	}
	if(!checkRadio1('form_gg_add','agg_pregnant')){
		alert("Please select if grandaughter is pregnant.");
		return false;
	}
	if(!checkRadio1('form_gg_add','agg_attending')){
		alert("Please select if grandson/grandaughter is attending school.");
		return false;
	}
	return true;
	
}




