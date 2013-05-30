function xhrGo2(m, u, cb, rf) {
	var xmlhttp;
	if (window.XMLHttpRequest) {
  		xmlhttp = new XMLHttpRequest();
  	} else {
  		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
  		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
  			if (rf=='plain') {
  				cb(xmlhttp.responseText);
  				//document.getElementById("loading").innerHTML="";
  			} else if (rf=='xml') {
  				cb(xmlhttp.responseXML);
  				//alert("in");
  			}
    	}
  		else{
  			//document.getElementById("loading").innerHTML="<img  src=\"images/279.gif\" alt='loading_transparent' height='20px' width='50px'>";
  			
  			
  		}
  	};
	xmlhttp.open(m,u,true);
	xmlhttp.send();
};


function fadeOutLoading(){
	$("#div").fadeOut(100);	
	$("#overlay").fadeOut(900);
}