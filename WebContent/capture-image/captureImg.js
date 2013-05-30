//capture image function
var gCtx = null;
var gCanvas = null;

var imageData = null;
var imageDataBuffer = null;
var ii=0;
var jj=0;
var c=0;

var background = false;
//function for displaying captured photo[jm]
function pageLoad() { 
	initCanvas(320,240);
} 
function initCanvas(ww,hh){
	gCanvas = document.getElementById("canvas");
	var w = ww;
	var h = hh;
	gCanvas.style.width = w + "px";
	gCanvas.style.height = h + "px";
	gCanvas.width = w;
	gCanvas.height = h;
	gCtx = gCanvas.getContext("2d");
	gCtx.clearRect(0, 0, w, h);
	imageData = gCtx.getImageData( 0,0,320,240);
	imageDataBuffer = gCtx.getImageData( 0,0,320,240);
}
function passLine(stringPixels) { 
	//a = (intVal >> 24) & 0xff;
	var coll = stringPixels.split("-");
	for(var i=0;i<320;i++) { 
		var intVal = parseInt(coll[i]);
		r = (intVal >> 16) & 0xff;
		g = (intVal >> 8) & 0xff;
		b = (intVal ) & 0xff;
		if(background) { 
		imageDataBuffer.data[c+0]=r;
		imageDataBuffer.data[c+1]=g;
		imageDataBuffer.data[c+2]=b;
		imageDataBuffer.data[c+3]=255;
		} else {
			var oldR = imageDataBuffer.data[c+0];
			var oldG = imageDataBuffer.data[c+1];
			var oldB = imageDataBuffer.data[c+2];
	
			var bR = imageData.data[c-4+0];
		 	var bG = imageData.data[c-4+1];
		  	var bB = imageData.data[c-4+2];
	
			mVote=0;
			rr = false;
			gg = false;
			bb = false;
	
			rr = Math.abs(r-oldR);
			gg = Math.abs(g-oldG);
			bb = Math.abs(b-oldB);
	
			if(rr>20&&gg>20&&bb>20) { 
				imageData.data[c+0]=r;
				imageData.data[c+1]=g;
				imageData.data[c+2]=b;
				imageData.data[c+3]=255;
			} else {  
				imageData.data[c+0]=r;
				imageData.data[c+1]=g;
				imageData.data[c+2]=b;
				imageData.data[c+3]=0;
			} 
		}
		c+=4;
	} 
	if(c>=320*240*4) { 
		c=0;
		if(background) { 
			gCtx.putImageData(imageDataBuffer, 0,0);
		} else { 
			gCtx.putImageData(imageData, 0,0);
		}
	} 
} 

function captureToCanvas() {
	background = false;
	flash = document.getElementById("embedflash");
	flash.ccCapture();
}
function copyImageToCanvas(aImg){
  //gCtx.drawImage(aImg, 0, 0);
}
function replaceImageData() {
  	  var img = new Image();
  	  img.src="bars.png";
  	  img.onload = function() { copyImageToCanvas(img); };
} 
function captureToCanvasBackground() {
	background = true;
	flash = document.getElementById("embedflash");
	//alert('me sud:'+flash);
	flash.ccCapture();
	image();
}
function image(){      	
	var canvas = document.getElementById("canvas");
	var data = canvas.toDataURL("image/jpeg");
	//alert("data: "+data);
	data = data.replace("image/jpeg", "image/octet-stream");    		        		
	//window.location.href = data; //automatic download 
	//path C:\Documents and Settings\iCOTP-ICT\My Documents\Downloads
	//start of embedding image
	var str = "";
	str += "<table> "+
					"<tr><td> <button type='submit' id='upload_image_btn'>Upload</button> </td><br/></tr>"+
					"<tr><td> <img src="+data+" width='320' height='240'/> </td></tr>"+
					"<tr><td> <input type='hidden' name='capture_image'  value='"+data+"'/> </td></tr>";
	str+= "</table>";
	document.getElementById("image_div").innerHTML = str;
	$('#upload_image_btn').button({
		icons : {primary : "ui-icon-circle-arrow-n" }
	});
	//end
	//document.forms["myuploadImageform"].submit(); //automatic submit 
}
/*Capture Image*/
function pop_up_image(){
	$("#image-pop-up").dialog({			
		show: "fade",
		hide: "fade",
		resizable: false,
		height: 350,
		width: 680,
		modal: true,
		title:"Capture Image"
	});
}	
$(document).ready(function(){
	$('#capture_btn').button({
	icons : {primary : "ui-icon-image" }
	});
	//security purpose
	$('#btn_capture').click(function(){
			document.getElementById("confirm_password5").value = "";
			$('#confirm_prov5').dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height: 250,
				width: 380,
				modal: true,
				buttons: {
					"Ok": function() {
						var password = document.getElementById("confirm_password5").value;
						xhrGo("POST","Password_Confirmation?confirmation_password="+password, photoConfirmCapture, "plain");
					},
					"Cancel": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			$('#confirm_password5').focus();
		});
});
function photoConfirmCapture(data){
	if(data==1){
		$( '#confirm_prov5' ).dialog( "close" );
		pop_up_image();
	}
	else{
		accessDenied();
	}
}
function OnEnterCapture(e){
	if(e && e.keyCode == 13){
		var password = document.getElementById("confirm_password5").value;
		xhrGo("POST","Password_Confirmation?confirmation_password="+password, photoConfirmCapture, "plain");
	}	
}//end security purpose