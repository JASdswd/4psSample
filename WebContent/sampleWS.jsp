<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MCCT WS</title>
<link href="css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>

<div class="container">
<h1>MCCT WS</h1>

<button class="btn btn-primary" id="btnFetch">Fetch</button>
<table class="table table-striped table-hover" id="tblFetch">
<thead>
	<tr>
		<th>A</th>
		<th>B</th>
		<th>C</th>
	</tr>
</thead>
<tbody>
	<tr>
		<th>1</th>
		<th>...</th>
		<th>...</th>
	</tr>
	<tr>
		<th>2</th>
		<th>...</th>
		<th>...</th>
	</tr>
</tbody>
</table>

</div>

<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!--
<script src="js/cryptojs/components/core-min.js"></script>

<script src="js/cryptojs/components/enc-utf16-min.js"></script>
<script src="js/cryptojs/components/enc-base64-min.js"></script>

<script src="js/cryptojs/rollups/hmac-md5.js"></script>
<script src="js/cryptojs/rollups/hmac-sha1.js"></script>
<script src="js/cryptojs/rollups/hmac-sha256.js"></script>
<script src="js/cryptojs/rollups/hmac-sha512.js"></script>


<script src="js/Crypto.js"></script>
<script src="js/HMAC.js"></script>
-->
<script type="text/javascript">
$(function(){
	$('#btnFetch').on('click', function(e){
		alert("if you are N I T you are E");
		var theURI = 'http://beta.mcct-dswd.com/McctService.svc/McctRest';
		
		/*
		var username = 'mcct';
		var key = 'testkey';
		*/
		//var token = encodeBase64(key + theURI);
		//var token = btoa(username + ':' + key + theURI);
		//var token = btoa(key + theURI);
		
		//key = Crypto.charenc.Binary.stringToBytes(key);
		//var t = Crypto.charenc.Binary.stringToBytes(key + theURI);
		/*
		var bytes = Crypto.charenc.Binary.stringToBytes(key);
		var hmac = CryptoJS.HmacMD5(theURI, key);
		*/
		//var t = key + theURI;
		//console.log('hmac: ' + hmac);
		//var token = Crypto.util.bytesToBase64(hmac);
		//console.log('token: ' + token);
		var getURI = 'http://beta.mcct-dswd.com/McctService.svc/Mcct/family/province/batangas';
		$.ajax({
            url: getURI,
            //type: 'get',
            dataType: 'json',
            success: function (data) {
                alert(data[0].FamilyId);
                alert('success');
            },
            error: function(data){
            	alert("error");
            	var x = JSON.parse(data);
            	alert("error. "+x);
            	//var jsonobj = eval("(" + data + ")");
            	//alert(jsonobj.FamilyId);
            }
        });
		/* $.ajax({
			//cache: false,
			url: getURI,
			type: 'POST',
			dataType: 'json',
			jsonCallback: 'displayData',
			contentType: "application/json; charset=utf-8",
			processData: true,
			success: function(data){
				alert('success');
				
				$.each(data.items, function(i, item){
					var tRow = '<tr><td>' + '' + '</td><td>' + '' + '</td><td>' + '' + '</td><td></tr>';
					$('#tblFetch tbody').empty().append(tRow);
				});
				
			
			AvatarContent
			BiometricContent
			Birthday
			ExtName
			FamilyId
			FirstName
			Gender
			LastName
			LengthOfStay
			MaritalStat
			MiddleName
			Occupation
			
			},
			error: function(data){
				alert("still doin good.");
				alert("data:"+data[1].FamilyId);
				$.each(data, function(i, item){
					var tRow = '<tr><td>' + '' + '</td><td>' + '' + '</td><td>' + '' + '</td><td></tr>';
					$('#tblFetch tbody').empty().append(tRow);
				});
				alert('ok na');
			}
			
			//crossDomain: true,
			
			beforeSend: function(xhr){
				xhr.setRequestHeader('Authorization', 'Basic ' + username + ':' + token);
				//xhr.setRequestHeader('Authorization', 'Basic ' + btoa(username + ':' + key + theURI));
			}
			
		}); */
	});
	function displayData(data){
		alert('displayData');
	}
});
</script>
</body>
</html>