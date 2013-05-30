
function validateEmpty(field, alerttxt) {
	with (field) {
		if (field.value=="" ||field.value==null) {
			alert(alerttxt);
			return false;
		} else {
			return true;
		}
	}
}

function validate_form(thisform) {
	
	alert("sd");
	with (thisform) {
		alert("ad");
		if (validateEmpty(thisform.name1, "Cheque number Required") == false) {
			thisform.name1.select();
			return false;
		} else if (validateEmpty(thisform.name2, "Student Name is Required") == false) {
			thisform.name2.select();
			return false;
		}else if (validateEmpty(thisform.name3, "MoR ") == false) {
			thisform.name3.select();
			return false;
		}else if (validateEmpty(thisform.name4, "Y red") == false) {
			thisform.name4.select();
			return false;
		}else if (validateEmpty(thisform.name5, "Amount is Required") == false) {
			thisform.name5.select();
			return false;
		}else{
			return true;
		}
	}
}
