function xhrGo(m, u, cb, rf) {
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
  			} else if (rf=='xml') {
  				cb(xmlhttp.responseXML);
  			}
    	}
  	};
	xmlhttp.open(m,u,true);
	xmlhttp.send();
};
