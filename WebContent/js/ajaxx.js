/*
* Use:
		ajax({
			method: "post",
			url: "test.jsp",
			data: "id=2&name='juan'",
				data: { id: 2, name: 'juan' },
			callback: successHandler,
				callback: { success: successHandler, loading: loadingHandler, error: errorHandler },
			resultType: "plain",
			async: true
		})
		ajax({ url: 'hello.jsp', data: 'id=2', callback: myCallback });
*/
function ajax(s) {
	if (typeof(s.url) == 'undefined') return false;
	var xhr;
	var passData = '';
	s.method = (typeof(s.method) == 'undefined') ? 'GET' : s.method;
	s.method = s.method.toUpperCase();
	s.resultType = (typeof(s.resultType) == 'undefined') ? 'PLAIN' : s.resultType;
	s.resultType = s.resultType.toUpperCase();
	s.async = (typeof(s.async) == 'undefined') ? true : s.async ;

	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		try {
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
	   } catch (e) {
			try {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	if (xhr == null) return false; //check for undefined

	if (typeof(s.callback) === 'function') {
		s.callback.success = s.callback;
	}		
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			if (s.resultType == 'PLAIN') {
				s.callback.success(xhr.responseText);
			} else if (s.resultType == 'XML') {
				xhr.overrideMimeType('text/xml');
				s.callback.success(xhr.responseXML.documentElement);
			} else if (s.resultType == 'JSON') {
				s.callback.success( eval("(" + xhr.responseText + ")") );
			} else if (s.resultType == 'JSONP') {
				//
			}
		} else {
			if (s.callback.error) {
				s.callback.error("There was a problem with the request.");
			}
		}
	};

	if (typeof(s.data) == 'object') {
		for (var k in s.data){
			passData += encodeURIComponent(k) + '=' + encodeURIComponent(s.data[k]) + '&';
		}
		passData = passData.substring(0, passData.length-1);
	} else if (typeof(s.data) == 'string') {
		passData = (s.data == '') ? null : s.data;
	} else if (typeof(s.data) == 'undefined') {
		passData = null;
	}

	if (s.method === 'GET' && passData != null){
		s.url += '?' + passData;
		passData = null;
	}
	xhr.open(s.method, s.url, s.async);
	
	if (s.method === 'POST'){
		passData = passData.substring(1, passData.length-1);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.setRequestHeader("Content-length", passData.length);
		xhr.setRequestHeader("Connection", "close");
	}

	xhr.send(passData);
	if (s.callback.loading){ s.callback.loading();}
}
