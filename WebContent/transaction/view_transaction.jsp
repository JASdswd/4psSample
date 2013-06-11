<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <% String cPath = request.getContextPath(); %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",-1);
%>
<html>
<%	
if(session.getAttribute("username")==null){
	System.out.println("username is null view_transaction.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />

<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link type="text/css" href="<%= cPath %>/development-bundle/themes/cupertino/jquery.ui.all.css" rel="stylesheet" />
<title>Pantawid Pamilyang Pilipino Program</title>
<script type="text/javascript" src="<%= cPath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script>
<script type="text/javascript" src="<%= cPath %>/js/pages.js"></script>
<!--for pagination  -->
<!--<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.filer.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter.pager.js" ></script>
<script type="text/javascript" src="<%=cPath %>/paginationAndSort/jquery.tablesorter-2.0.3.js" ></script>

--><script type="text/javascript"><!--
	
	var transaction_time = "";
var validNavigation = false;
var sURL = unescape(window.location.pathname);
var today=new Date();

	var todayy=today.getFullYear();
	
	var todaym=today.getMonth();
	var todayd=today.getDate();
	var todayh=today.getHours();
	var todaymin=today.getMinutes();
	var todaysec=today.getSeconds();
	var transactionTime = '<%= session.getAttribute("transactionTime")%>';
	dateFuture1 = new Date(todayy,todaym,todayd,todayh,todaymin+1,0);

	var arrow_ctr = 0;
	
	var temp_o;
	var my_id_o;
	var day_o;
	
	$(document).ready(function(){
		
		$("html").scrollTop(168);
		 $('tbody1 tr:odd', $('#tabl')).hide(); //hiding rows for test
		    var options = {
		      currPage : 1, 
		      ignoreRows : $('tbody1 tr:odd', $('#display')),
		      optionsForRows : [10],
		      rowsPerPage : 10,
		      firstArrow : (new Image()).src="<%= cPath %>/images/firstBlue.gif",
		      prevArrow : (new Image()).src="<%= cPath %>/images/prevBlue.gif",
		      lastArrow : (new Image()).src="<%= cPath %>/images/lastBlue.gif",
		      nextArrow : (new Image()).src="<%= cPath %>/images/nextBlue.gif",
		      topNav : false
		    };
		    $('#tabl').tablePagination(options);

		$("#tablePagination_firstPage").button({
			icons: {primary: "ui-icon-seek-first"}
		});
		$("#tablePagination_prevPage").button({
			icons: {primary: "ui-icon-seek-prev"}
		});
		$("#tablePagination_nextPage").button({
			icons: {secondary: "ui-icon-seek-next"}
		});
		$("#tablePagination_lastPage").button({
			icons: {secondary: "ui-icon-seek-end"}
		});
		$("#sbf_btn").button({
			icons : {primary : "ui-icon-circle-check" }
		});
		
		$("#viewP").button({
			icons : {primary : "ui-icon-circle-check" }
		});
		$(".fpt_btn2sub").hide();
		/*for pagination  */
		$("#tabl").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra']})
		.tablesorterPager({ container: $("#pageronE"), positionFixed: false 
		});
		
		$("#but").click(function(){
			var id=document.getElementById("household_id").value;
			$("#ADDtransaction").dialog('open').load('AddReason?household_id='+id);
		});
		$("html").scrollTop(140);
		$(window).unload(function(){});
		
	});
	/*for pagination  */
	$(".first").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-first"
		}
	});
	$(".prev").button({
		text: false,
		icons: {
			 primary: "ui-icon-seek-prev"
		}
	});
	$(".next").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-next"
		}
	});
	$(".last").button({
		text: false,
		icons: {
			 secondary: "ui-icon-seek-end"
		}
	});
	/*end  */
	$(function(){
		
		
		
		
		var dialogSettingsInEdit = {
				autoOpen:false,
				draggable:false,
				hide:'fade',
				show:'fade',
				resizeable:false,
				modal:true,
				width:'600',
				height:'500'
		};
		$("#ADDtransaction").dialog(dialogSettingsInEdit);
		$("#EditTransaction").dialog(dialogSettingsInEdit);
		  $("#dv").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
	     .tablesorterPager({ container: $("#pagerOne"), positionFixed: false //})
	     /* .tablesorterFilter({ filterContainer: $("#filterBoxOne"),
	      filterClearContainer: $("#filterClearOne"),
	      filterColumns: [0, 1, 2, 3, 4, 5, 6],
	      filterCaseSensitive: false */
		});
	});
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
	 function comment_dialog(id){
		$( "#dialog_comment" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:200,
			modal: true,
			buttons: {
				"Comment": function() {
					$( this ).dialog( "close" );
					var comment=document.getElementById("comment").value;
					document.getElementById(id).innerHTML=comment;
				},
				"Cancel": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	function edittransaction(data){
		var temp=data.split("&");
		$("#EditTransaction").dialog('open').load('EditTransaction?household_id='+temp[0]+'&month='+temp[1]+'&amount='+temp[2]);
	}
	function handos(id){
		var temp=id.split("+");
		var my_id=temp[0]+"_"+temp[1];
		var day=document.getElementById("comment").value;
		/* var comment=0; */
		xhrGo("POST","ChangeView?household_id="+temp[0]+"&month="+temp[1]+"&id="+my_id+"&comment="+day, display, "plain");
	}
	
	function handos123(id){
		var temp=id.split("+");
		var my_id=temp[0]+"_"+temp[1];
		var day=document.getElementById("comment").value;
		/* var comment=0; */
		$('#fpt_dialog').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 150,
			open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
			closeOnEscape: false,
			draggable: false,
			width: 300,
			modal: true
		});
		validNavigation = true;
		xhrGo("POST","ChangeView123?household_id="+temp[0]+"&month="+temp[1]+"&id="+my_id+"&comment="+day, display123, "plain");
	}
	
	function confirmMun(name){
		var temp=name.split("+");
		$('#fpt_dialog123').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 150,
			open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
			closeOnEscape: false,
			draggable: false,
			width: 300,
			modal: true
		});

		validNavigation = true;
		//xhrGo("POST","Password_Confirmation?confirmation_password=4",fingerprint_confirm, "plain");
		xhrGo("POST","Municipal_Confirmation?household_id="+$("#household_id").val()+"&month="+temp[1], fingerprint_confirm, "plain");
	}
	
	function fingerprint_confirm(data){
		var x=eval('('+data+')');
		validNavigation = false;
		$("#fpt_dialog123").dialog('close');
		if(x.failedToVerify == 1){
			$('#verificationFailed').dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:180,
				modal: true,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			//alert("failed to verify");
		}
		else{
			if(x.fingerNotMatched){
				$('#noMatch').dialog({
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
			else if(x.fingerMatched){
				//fingerprint matched.
				document.location.href = "<%=cPath %>/View_trans?id="+$("#household_id").val();
			}
		}
		
	}
	
	function handos1(id){
		document.getElementById('confirm_password1').value = "";
		temp_o=id.split("+");
		my_id_o=temp_o[0]+"_"+temp_o[1];
		day_o=document.getElementById("comment").value;
		$('#confirm_fpt').dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height: 200,
			draggable: false,
			width: 380,
			modal: true,
			buttons: {
				"Ok": function() {
					var password1 = document.getElementById("confirm_password1").value;
					//alert($("#user").val());
					xhrGo("GET","BookKeeperConfirmation?confirmation_password1="+password1+"&household_id="+temp_o[0]+"&month="+temp_o[1]+"&id="+my_id_o+"&comment="+day_o+"&user="+$("#user").val(), prov_confirmed, "plain");
				},
				"Cancel": function(){
					$(this).dialog('close');
				}
			}
		});
		$('#confirm_password1').focus();
	}
	
	function prov_confirmed(data){
		var x=eval('('+data+')');
		
		transaction_time = x.transaction_time;
		if(x.trys){
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
		else{
			$('#confirm_fpt').dialog('close');
			transactionTime = transaction_time;
			//alert("tra:"+transactionTime);
			GetCount(dateFuture1, 'countbox1');
			var strbuild="";
			var ctr = 0;
			for(var i=0;i<x.data.length;i++){

					strbuild+="<td>"+x.data[i].month+"</td>";
					/* strbuild+="<td>"+x.data[i].year+"</td>"; */
					strbuild+="<td>P"+x.data[i].amount+"</td>";
		/* 
			<c:choose>
					<c:when test="${list.sub == 0}">
						<td class="tbl_data">Received by Grantee</td>
					</c:when>
					<c:otherwise>
						<td class="tbl_data">Received by Representative:Confirmed by <c:out value="${list.munLink_name}"></c:out> </td>
					</c:otherwise>
				</c:choose>
		
		*/
					if(x.data[i].recieve=='1'){
						if(x.data[i].sub == 0){
							strbuild+="<td class='tbl_data'>Received by Grantee</td>";
						}
						else{									<%-- <a href="<%=cPath %>/ViewMunProf?name=${list.munLink_name}&h=${list.household_id}"> --%>
							strbuild+="<td class='tbl_data'>Received by Representative:Confirmed by <a href='<%= cPath%>/ViewMunProf?name="+x.data[i].munLink_name+"&h="+$("#household_id").val()+"' >"+x.data[i].munLink_name+"</a></td>";
						}
					}

					strbuild+="<td>"+x.data[i].date_receive+"</td>";
					strbuild+="<td>"+x.data[i].time+"</td>";
					strbuild+="<td></td>";
					strbuild+="<td>"+x.data[i].comment+"</td>";
				
			}
			document.getElementById(x.id).innerHTML=strbuild;
		}
	}
	
	function display(data){
		
		var x=eval('('+data+')');
		/*if(x.has_transaction_time){
			alert("has_transaction = true");
			alert("transaction_time:"+x.transaction_time);
		}
		else{
			alert("transaction_time:"+x.transaction_time);
		}*/
		
		transaction_time = x.transaction_time;
		if(x.trys){
			$( "#noFingerprint" ).dialog({
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
		else{
			transactionTime = transaction_time;
			//alert("tra:"+transactionTime);
			GetCount(dateFuture1, 'countbox1');
			var strbuild="";
			var ctr = 0;
			for(var i=0;i<x.data.length;i++){

					strbuild+="<td>"+x.data[i].month+"</td>";
					/* strbuild+="<td>"+x.data[i].year+"</td>"; */
					strbuild+="<td>P"+x.data[i].amount+"</td>";
					
					if(x.data[i].recieve=='1'){
						if(x.data[i].sub == 0){
							strbuild+="<td class='tbl_data'>Received by Grantee</td>";
						}
						else{
							strbuild+="<td class='tbl_data'>Received by Representative:Confirmed by <a href='<%= cPath%>/ViewMunProf?name="+x.data[i].munLink_name+"&h="+$("#household_id").val()+"' >"+x.data[i].munLink_name+"</a></td>";
						}
					}

					strbuild+="<td>"+x.data[i].date_receive+"</td>";
					strbuild+="<td>"+x.data[i].time+"</td>";
					strbuild+="<td></td>";
					strbuild+="<td>"+x.data[i].comment+"</td>";
				
			}
			document.getElementById(x.id).innerHTML=strbuild;
		}
		
	}
	
	function display123(data){
		
		var x=eval('('+data+')');
		validNavigation = false;
		transaction_time = x.transaction_time;
		$("#fpt_dialog").dialog('close');
		if(x.trys){
			$( "#noFingerprint" ).dialog({
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
		else{
			if(x.fingerNotMatched){
				$('#fingerNotMatched').dialog({
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
			else{
				transactionTime = transaction_time;
				//alert("tra:"+transactionTime);
				GetCount(dateFuture1, 'countbox1');
				var strbuild="";
				var ctr = 0;
				var amount = "";
				for(var i=0;i<x.data.length;i++){
						amount += x.data[i].current_amount;
						strbuild+="<td>"+x.data[i].month+"</td>";
						/* strbuild+="<td>"+x.data[i].year+"</td>"; */
						strbuild+="<td>P"+x.data[i].amount+"</td>";
						
						if(x.data[i].recieve=='1'){
							if(x.data[i].sub == '0'){
								strbuild+="<td class='tbl_data'>Received by Grantee</td>";
							}
							else{
								strbuild+="<td class='tbl_data'>Received by Representative:Confirmed by <a href='<%= cPath%>/ViewMunProf?name="+x.data[i].munLink_name+"&h="+$("#household_id").val()+"' >"+x.data[i].munLink_name+"</a></td>";
							}
						}

						strbuild+="<td>"+x.data[i].date_receive+"</td>";
						strbuild+="<td>"+x.data[i].time+"</td>";
						strbuild+="<td></td>";
						strbuild+="<td>"+x.data[i].comment+"</td>";
					
				}
				
				var new_amount = "";
				var count = 0;
				for(var i = amount.length-1;i>=0;i--){
					if(count==3){
						new_amount = "," + new_amount;
						new_amount = amount[i] + new_amount;
					}
					else{
						new_amount = amount[i] + new_amount;
						count++;
					}
				}
				new_amount = "P "+new_amount+".00";
				document.getElementById(x.id).innerHTML=strbuild;
				document.getElementById("cur_amount").innerHTML = new_amount;
			}
			
		}
		
	}
	function fpt_arrow(name){
		
		$("#"+name).toggle('slow',function(){
			$("#fpt_btnsub"+name).toggle('slow');
		});
		/* if(arrow_ctr==0){
			$(".fpt_btn").fadeOut('slow',function(){
				$("#fpt_btn2").fadeIn('slow');
			});
			arrow_ctr = 1;
		}
		else{
			$("#fpt_btn2").fadeOut('slow',function(){
				$(".fpt_btn").fadeIn('slow');
			});
			arrow_ctr = 0;
		} */
		
	}
	
	function fingerp(){
		$( "#dialog-select_mun" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Ok": function() {
					var municipality = $("#munc1").val();
					if(municipality=="Municipality"){
						$( "#dialog_no_mun_selected" ).dialog({
							show: "show",
							hide: "slide",
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
					else{
						$( this ).dialog( "close" );
						//xhrGo("POST","SearchByFingerprint?mun="+municipality,display, "plain");
						var id = $('#household_id').val();
						var mun = $('#municipal').val();
						document.location.href = "<%=cPath %>/SBF?id="+id+"&mun="+municipality;
					}
				},
				"Cancel": function(){
					$( this ).dialog( "close" );
				}
			}
		});
	}
	function fingerp123(){
		$( "#dialog-select_mun" ).dialog({
			show: "fade",
			hide: "fade",
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Ok": function() {
					var municipality = $("#munc1").val();
					if(municipality=="Municipality"){
						$( "#dialog_no_mun_selected" ).dialog({
							show: "show",
							hide: "slide",
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
					else{
						$( this ).dialog( "close" );
						$('#fpt_dialog').dialog({
							show: "fade",
							hide: "fade",
							resizable: false,
							height: 150,
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
							closeOnEscape: false,
							draggable: false,
							width: 300,
							modal: true
						});
						validNavigation = true;
						xhrGo("POST","SearchByFingerprint123?mun="+municipality,searchByFi, "plain");
					}
				},
				"Cancel": function(){
					$( this ).dialog( "close" );
				}
			}
		});
	}
	function searchByFi(data){
		var x = eval('('+data+')');
		//alert("matched household id hehehe:"+x.matchedHousehold_id);
		//alert("mun:"+x.mun);
		validNavigation = false;
		$( '#fpt_dialog' ).dialog( "close" );
		if(x.failedToVerify == 1){
			$('#verificationFailed').dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:180,
				modal: true,
				buttons: {
					"OK": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			//alert("failed to verify");
		}
		else{
			if(x.matchedHousehold_id=="false"){
				$('#noMatchFound').dialog({
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
			else{
				document.location.href = "<%=cPath %>/SBF123?id="+x.matchedHousehold_id+"&mun="+x.mun;
			}
		}
		
	}
	/**************************************** Sample Time Countdown **********************************************************/
	
	
	
	// TESTING: comment out the line below to print out the "dateFuture" for testing purposes
	//document.write(dateFuture +"<br />");


	function fingerprintConfirm(data){
		var x=eval('('+data+')');
		if(x.pass_con==1){
			transactionTime = x.transaction_time;
			//alert("password confirmed.!!");
			$( '#confirm_prov' ).dialog( "close" );
			GetCount(dateFuture1, 'countbox1');
			
		}
		else{
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
	}
	//###################################
	//nothing beyond this point
	function GetCount(ddate,iid){
		
		if(transactionTime=='null'){
			//alert("transactionTime is null.");
		}
		else{
			
			var seconds = transactionTime.substring(transactionTime.length-2, transactionTime.length);
			var minutes = transactionTime.substring(transactionTime.length-5, transactionTime.length-3);
			var hours = transactionTime.substring(transactionTime.length-8, transactionTime.length-6);
		
			dateFuture2 = new Date(todayy,todaym,todayd,hours,minutes,seconds);
			//alert("minutes:"+dateFuture2.getMinutes());
			
			dateFuture2.setMinutes(dateFuture2.getMinutes() + 10); // for adding 10 minutes.
			//dateFuture2.setSeconds(dateFuture2.getSeconds()+10); // for adding seconds. (10 seconds)
			
		dateNow = new Date();	//grab current date
		amount = dateFuture2.getTime() - dateNow.getTime();	//calc milliseconds between dates
		delete dateNow;
		
		
		// if time is already past.
		if(amount < 0){
			//document.getElementById(iid).innerHTML="Now!";
			document.getElementById('confirm_password').value = "";
			$('#confirm_prov').dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height: 230,
				open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
				closeOnEscape: false,
				draggable: false,
				width: 390,
				modal: true,
				buttons: {
					"Ok": function() {
						var password = document.getElementById("confirm_password").value;
						xhrGo("GET","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
					}
				}
			});
			$('#confirm_password').focus();
		}
		// else date is still good
		else{
			days=0;hours=0;mins=0;secs=0;out="";

			amount = Math.floor(amount/1000);//kill the "milliseconds" so just secs

			days=Math.floor(amount/86400);//days
			amount=amount%86400;

			hours=Math.floor(amount/3600);//hours
			amount=amount%3600;

			mins=Math.floor(amount/60);//minutes
			amount=amount%60;

			secs=Math.floor(amount);//seconds

			if(days != 0){
				out += days +" "+((days==1)?"day":"days")+", ";
			}
			if(hours != 0){
				out += hours +" "+((hours==1)?"hour":"hours")+", ";
			}
			out += mins +" "+((mins==1 || mins==0)?"min":"mins")+" , ";
			out += secs +" "+((secs==1 || secs==0)?"sec":"secs")+", ";
			out = out.substr(0,out.length-2);
			//document.getElementById(iid).innerHTML=out; //Print the countdown timer
			
			
			setTimeout(function(){GetCount(ddate,iid);}, 1000);
			
		}
		} // else para sa transactionTime is null condition.
	}

	window.onload=function(){
		GetCount(dateFuture1, 'countbox1');
		//you can add additional countdowns here (just make sure you create dateFuture2 and countbox2 etc for each)
	};
	/* ASK CONFIRMATION TO THE USER TO ABANDON THE SHIP. HAHA... bitaw.., mu'activate ni cja basta mu'leave ang user sa page sa browser. Request for confirmation */
	var dont_confirm_leave = 0; //set dont_confirm_leave to 1 when you want the user to be able to leave withou confirmation
	var leave_message = 'You sure you want to leave?';
	function goodbye(e) {
	  if (validNavigation) {
	    if (dont_confirm_leave!==1) {
	      if(!e) e = window.event;
	      //e.cancelBubble is supported by IE - this will kill the bubbling process.
	      e.cancelBubble = false;
	      e.returnValue = leave_message;
	      //e.stopPropagation works in Firefox.
	      if (e.stopPropagation) {
	    	  
	        e.stopPropagation();
	        e.preventDefault();
	      }
	      //return works for Chrome and Safari
	      return leave_message;
	    }
	  }
	}
	window.onbeforeunload=goodbye; 
	function OnEnterEvery10Min(e){
		if(e && e.keyCode == 13){
			var password = document.getElementById("confirm_password").value;
			xhrGo("GET","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain");
		}	
	}
</script>
<style type="text/css">
#main{
	min-height: 550px;
}
.ui-widget{
		font-size: 12px;
	}
#tabl{
	border-collapse: collapse;
	width: 900px;
}
#tabl th{
	padding : 10px 5px;
}
#tabl td{
	padding : 5px 3px;
}
.cur_amount{
	padding-top:0px;
	font-size: 50px;
}
#tab-container{
	height: auto;
	margin-bottom: 15px;
}
th{
	padding:0px 5px 0px 5px;
}
.input{
	margin-bottom: 5px;
	border: 1px solid #8FA9BC;
	padding: 5px 3px 5px 10px;
	color:#000000;
	outline: none;
}
.input:FOCUS{
	border: 1px solid #000045;
	box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
	-webkit-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
	-moz-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
}
.input:HOVER{
	border: 1px solid #000886;
}
#firstTH{
	border: 2px solid #000;
	box-shadow: 0px 0px 20px #ccc;
	-moz-box-shadow: 0px 0px 20px #ccc;
	-webkit-box-shadow: 0px 0px 20px #ccc;
	padding: 2px;
}
.HF_photo{
	height: 320px;
	width: 300px;
}
#w{
	width: 250px;
}
table {
	font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
}
#ac{
	width: 100px;
}
.wid{
	width: 50px;
}
.wit{
	width: 50px;
}
.tbl_h{
	background-color: gray;
}
/*style for pagination  */
/* .first, .prev, .next, .last{
	background-color: #58aa00;
    padding: 2px 2px;
    display: inline;
    border: none;
    color: #fff;
    cursor: pointer;
    font-weight: bold;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    text-shadow: 1px 1px #666;
    font-family: helvetica,sans-serif,arial;
    font-size: 12px;
    margin: 3px auto;
	} */
	.tb_r:HOVER{
		background-color: #ddd;
	}
	#viewProfile{
		font-family: arial,helvitica,sans-serif;
		font-size: 17px;
		font-weight: normal;
		margin-bottom: 10px;
		text-decoration: none;
	}
	#viewProfile:HOVER{
		text-decoration: underline;
		color: red;
	}
	#sbf:HOVER{
		text-decoration: none;
	}
	#sbf{
		text-decoration: none;
	}
	.hidden{
		display: none;
	}
	#fpt_arrow{
		cursor: pointer;
	}
	.tbl_data{
		font-family: sans-serif;
		font-size: 12px;
	}
	.info_tbl{
		padding-top: 50px;
	}
	.info_tbl th{
		text-align: left;
	}
	
	.cont{
		margin-top: 50px;	
		width:300px;
	}
	 #tablePagination { 
            font-size: 10px; 
            padding: 0px 5px; 
            height: 40px;
            width: 890px;
            border:  1px solid black; ;
            border-collapse: collapse;
            border-top: none;
            padding-top: 10px;
}
          
 #tablePagination_nextPage,#tablePagination_firstPage,#tablePagination_prevPage{
 	padding-left: 5px;
 	margin-left: 5px;
 }
#tablePagination_lastPage{
	padding-left: 5px;
	margin-left: 10px;
}
#tablePagination_prevPage{
	margin-right: 5px;
}
/* .head_photo{
	height: 560px;
	width: 560px;
} */
--></style>
</head>
<%
	HttpSession session1 = request.getSession(false);
	String household_id = (String)session1.getAttribute("household_id");
	System.out.println("household_id view_transactionProfile : ="+household_id);
	if(household_id.equals(null) || household_id==null){
		request.setAttribute("session_hh_id_null", true);
		response.sendRedirect(cPath+"/login/login1.jsp");
	}
%>
<body>
<div id="page-wrap">
<div id="header" >
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu">

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>
<div id="main" align="center">
<div id="ADDtransaction" style="display: none;" title="Add Transaction"></div>
<div id="EditTransaction" style="display: none;" title="Edit Transaction"></div>
	<table style="margin-top: 10px; margin-bottom: 0px;" >
		<tr>
			<th >
				<!--<div id="dv">
				
				</div> 
				--><c:choose>
						<c:when test="${photohead_exist}">
							<img id="firstTH" class="head_photo" alt="sampleImage" src="<%=cPath %>/ViewImage123?ctr=qfdj&view_id=<%= household_id %>" />
						</c:when>
						<c:otherwise>
							<img id="firstTH" alt="photo of head" class="HF_photo" src="<%=cPath %>/image/ViewImage123.jpg">
						</c:otherwise>
				</c:choose>	
			</th>
			<th  valign="top"  >
			<div class="cont" >
					<div style="float: left;" >
					<button id="sbf_btn" onclick="fingerp123();" >Search by fingerprint</button>
					</div>
					<div style="float: right;">
						<a href="<%=cPath %>/View_transactions2?hh_id=${id}" id="viewProfile"><button id="viewP" >View Profile</button> </a>
					</div>
				</div>
				<table class="info_tbl" >
					<tr>
						<th  >Household ID NO.:</th>
					</tr>
					<tr>	<th><input size="40" name="household_id" class="input" value="${id}" id="household_id" readonly="readonly" /></th>
					</tr>
					<tr>
						<th>Name of Head:</th>
					</tr>
					<tr>	<th><input size="40" name="head_name" class="input" value="${head_name}" id="head_name" readonly="readonly" /></th>
					</tr>
					<tr>
						<th>Barangay:</th>
					</tr>
					<tr>	<th><input name="brgy" size="40" value="${brgy}" id="brgy" class="input" readonly="readonly" /></th>
					</tr>
					<tr>
						<th>Municipality:</th>
					</tr>
					<tr>	<th><input name="municipal" size="40" value="${municipal}" class="input" id="municipal" readonly="readonly" /></th>
					</tr>
					<tr>
						<td>	
								<p class="cur_amount" id="cur_amount" >P&nbsp;
									<c:choose>
										<c:when test="${last_amount > 999}">
											<fmt:formatNumber pattern="0,000.00" type="currency" >
												<c:out value="${last_amount}"></c:out>
											</fmt:formatNumber>
										</c:when>
										<c:otherwise>
											<c:out value="${last_amount}"></c:out>
										</c:otherwise>
									</c:choose>
								</p>
						</td>
					</tr>
				</table>
			
			</th>
		</tr>
	</table>
	<div id="tab-container">
	<table id="tabl" border="1">
		<thead>
			<tr>
				<th class="tbl_h">Month and Year</th>
				<th class="tbl_h">Amounts</th>
				<th class="tbl_h">Status</th>
				<th class="tbl_h">Date Received (MM-DD-YYYY)</th>
				<th class="tbl_h">Time</th>		
				<c:if test="${book_keeper}">
					<th id="ac" class="tbl_h">Action</th>
					<th class="tbl_h">Comment</th>
				</c:if>
				
			</tr>
		</thead>
		<c:if test="${emp=='emp'}">
			<tr><th colspan="7">NO DATA</th></tr>
		</c:if>
		<c:if test="${emp!='emp'}">
			<tbody >
			<% int count =  0;%>
			<c:forEach items="${transaction_list}" var="list">
			<input type="hidden" name="months" value="${list.month}" >
				<c:if test="${list.household_id==id}">
					<tr id="${list.household_id}_${list.month}" class="tb_r">
						<td><c:out value="${list.month}"></c:out></td>
						<td>P<c:out value="${list.amount}"></c:out> </td>
						<c:if test="${list.receive==1}">
							<c:choose>
								<c:when test="${list.sub == 0}">
									<td class="tbl_data">Received by Grantee</td>
								</c:when>
								<c:otherwise>
									<td class="tbl_data">Received by Representative:Confirmed by <a href="<%=cPath %>/ViewMunProf?name=${list.munLink_name}&h=${list.household_id}"><c:out value="${list.munLink_name}"></c:out></a> </td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${list.receive==0}">
							<c:choose>
								<c:when test="${list.sub == 0}">
									<td class="tbl_data">Not Yet Received</td>
								</c:when>
								<c:otherwise>
									<td class="tbl_data">Not Yet Received:Confirmed by <a href="<%=cPath %>/ViewMunProf?user_role=user_role&mun_id=mun_id&id=id"></a> </td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<td><c:out value="${list.day}"></c:out> </td>
						<td><c:out value="${list.time}"></c:out> </td>
						<%-- <td><button id="${list.household_id}&${list.month}&${list.amount}" onclick="edittransaction(this.id);" >Edit</button></td> --%>
						<c:if test="${book_keeper}">
							<%count++; %>
							<c:if test="${list.receive==0}">
							<td>
								
									<c:choose>
										<c:when test="${list.sub==0}">
											<button id="<%=count %>" name="${list.household_id}+${list.month}" class="fpt_btn" onclick="handos123(this.name);">Received</button>
											<button id="fpt_btnsub<%=count %>" class="fpt_btn2sub" name="${list.household_id}+${list.month}"  onclick="confirmMun(this.name);">Substitute</button>
											<span class="ui-icon ui-icon-circle-triangle-e" id="fpt_arrow"  onclick="fpt_arrow(<%=count %>);" style="float:right; margin:5px 5px;"></span>
										</c:when>
										<c:otherwise>
											<button id="fpt_btn" class="fpt_btn2" name="${list.household_id}+${list.month}"  onclick="handos1(this.name);">Confirmed</button>
										</c:otherwise>
									</c:choose>
								</td>
						</c:if>
						<c:if test="${list.receive==1}">
							<td></td>
						</c:if>
						<c:if test="${list.receive==0}">
							<td id="cm+${household_id}+${list.month}" onclick="comment_dialog(id);"><button>Comment </button></td>
						</c:if>
						<c:if test="${list.receive==1}">
							<td><c:out value="${list.comment}"></c:out></td>
						</c:if>
						</c:if>
						
					</tr>
				</c:if>
				
			</c:forEach>
			</tbody>
		
		</c:if>
		<!-- <tr>
			<td colspan="10" align="center"><button type="button" id="but">Add Transaction</button></td>
		</tr> -->
	</table>
	<!-- <div align="right" style="margin-right: 37px;"><button id="bots"  >Comment</button></div> -->
	<div align="center">
		<c:if test="${mess=='Error Duplicate Data!'}">
			<h3 style="color: red"><blink>Error Duplicate Data!</blink></h3>
		</c:if>
	</div>
	<div id="countbox1"></div>
	</div>
</div>
</div> <!-- end of main content -->
 <div id="dialog_comment" title="Comment" style="display: none;">
	<textarea rows="" cols="" style="width: 270px;height: 90px;font-size: 16px;" id="comment" name="comment"></textarea>
</div>
<div id="dialog-confirm" title="Logout" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>
<div id="noFingerprint" title="Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><c:out value="${head_name}"></c:out> doesn't have a registered fingerprint.</p>
</div>
<div class="hidden" id="confirm_prov" title="Confirmation" onkeypress="return OnEnterEvery10Min(event);">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Please enter password of bookkeeper for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td><input type="text" class="input" readonly="readonly" value="${username}"/></tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password" id="confirm_password" class="input" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div class="hidden" id="confirm_fpt" title="Confirmation">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Please enter password of bookkeeper for confirmation.
	</p>
	<table>
						
		<tr>
			<td><label>Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td>
				<input id="user" class="input" style="width: 170px" readonly="readonly" value="${username}"/>
			</tr>
		<tr>
			<td><label>Password:</label></td>
			<td><input name="password1" id="confirm_password1" class="input" style="width: 170px" type="password" value="" />
			</td>
		</tr>
	</table>
	
</div>
<div id="fpt_dialog" style="display: none;">
	<p><label>Scan your fingerprint.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cPath %>/images/loading.gif" /></p>

</div>
<div id="fpt_dialog123" style="display: none;">
	<p><label>Scan fingerprint of Municipal link for confirmation.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cPath %>/images/loading.gif" /></p>

</div>
<div id="dialog-select_mun" title="4PS system" style="display:none">
	<label>Select municipal :</label>
	<select name="municipal" id="munc1" class="input">
							<option selected="selected" disabled="disabled" >Municipality</option>
							<c:forEach items="${municipal_list}" var="list">
								<c:if test="${mun==list.mun_id}">
									<option selected value="${list.mun_id}" ><c:out value="${list.municipal}"></c:out></option>
								</c:if>
								<c:if test="${mun!=list.mun_id}">
									<option value="${list.mun_id}" ><c:out value="${list.municipal}"></c:out></option>
								</c:if>
								
							</c:forEach>
						</select>
</div>
<div id="dialog_no_mun_selected" title="4PS message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Please select a municipal for faster searching.</p>
</div>
<div id="passwordDenied" title="4Ps Message" class="hidden">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access Denied..!!!</p>
</div>
<div id="verificationFailed" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to perform verification. The fingerprint reader is used by another application.</p>
</div>
<div id="noMatchFound" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>No match found.</p>
</div>
<div id="noMatch" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Access denied. Municipal link fingerprint didn't match.</p>
</div>
<div id="fingerNotMatched" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Fingerprint not matched.</p>
</div>
</div> <!-- end of page wrap -->
</body>
</html>