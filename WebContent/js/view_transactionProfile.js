	var sd_ctr = 0;
	var gg_ctr = 0;
	var usd_id = "";
	var id_sd = 0;
	var Dsd_name = "";
	var id_gg = 0;
	var Dgg_name = "";

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
	function checkPhoto(picField) {
		 var picFile = picField;
		 var imagePath = document.getElementById(picFile).value;
		 var pathLength = imagePath.length;
		 var lastDot = imagePath.lastIndexOf(".");
		 var fileType = imagePath.substring(lastDot,pathLength);
		 var input = document.getElementById('changePhoto');
		 var file;

		 if (!input.files[0]) {
		        alert("Please select an image before clicking submit.");
		        return false;
		 }
		 else if((fileType == ".bmp") || (fileType == ".BMP") || (fileType == ".gif") || (fileType == ".jpg") || (fileType == ".jpeg") || (fileType == ".png") || (fileType == ".GIF") || (fileType == ".JPG") || (fileType == ".JPEG") || (fileType == ".PNG")) {
			    
			   
	        file = input.files[0];
	        if(file.size>1000000){
	        	 alert("Size of image must be lesser than 1mb(1 megabyte).");
	        	 return false;
	        }

	        else if(file.size<10321){
	        	alert("Size of image must be greater than 10kb(10 kilobytes).");
	        	return false;
	        }
			return true;
		 }
		 else {
		 	alert("This system supports .JPG, .PNG, .BMP and .GIF image formats. Your file-type is " + fileType + ".");
		 	return false;
		 }
	}
	function change_photo_head(){
		$( "#change-photo" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:180,
			width: 415,
			modal: true,
			buttons: {
				"Submit": function() {
					if(checkPhoto('changePhoto')){
						$( this ).dialog( "close" );
						document.forms["photo_change"].submit();
					}
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
	}
	
	function accessDenied(){
		$( "#passwordDenied" ).dialog({
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
	function success(){
		$( "#success" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Ok": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}