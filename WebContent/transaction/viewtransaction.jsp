<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,java.awt.*,javax.swing.*"%>
<%@ page import="java.text.*"%> 
        <% String cPath = request.getContextPath(); %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	System.out.println("username is null viewtransaction.jsp");
	ServletContext sc=this.getServletContext();
	RequestDispatcher rd=sc.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="<%= cPath %>/css/home.css"/>
<link rel="stylesheet" type="text/css" href="<%= cPath %>/pro_drop_1/pro_drop_1.css" />
<link rel="stylesheet" type="text/css" href="<%= cPath %>/css/transaction.css" />
<link rel="shotcut icon" href="<%= cPath %>/image/home.png" type="image/x-icon" />
<link type="text/css" href="<%= cPath %>/development-bundle/themes/cupertino/jquery.ui.all.css" rel="stylesheet" />
<title>Pantawid Pamilyang Pilipino Program</title>
<script type="text/javascript" src="<%= cPath %>/js/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax.js" charset="utf-8"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajax3.js" charset="utf-8"></script>
<script type="text/javascript" src="<%= cPath %>/js/ajaxx.js" charset="utf-8"></script><%-- 
<script type="text/javascript" src="<%=cPath %>/js/jquery_ui.js"></script>
<script type="text/javascript" src="<%=cPath %>/js/jquery-1.6.2.min.js"></script> --%>
<script src="<%=cPath %>/development-bundle/jquery-1.6.2.js" charset="utf-8"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.core.js" charset="utf-8"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.widget.js" charset="utf-8"></script>
	<script src="<%=cPath %>/development-bundle/ui/jquery.ui.button.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=cPath %>/development-bundle/ui/jquery.ui.dialog.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=cPath %>/development-bundle/ui/jquery-ui-1.8.16.custom.js" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=cPath %>/development-bundle/demos/demos.css">
	
<script src="<%= cPath %>/development-bundle/external/jquery.bgiframe-2.1.2.js" charset="utf-8"></script>
<script type="text/javascript" src="<%= cPath %>/development-bundle/ui/jquery.ui.datepicker.js" charset="utf-8"></script>
<script type="text/javascript" src="<%= cPath %>/js/pages.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=cPath %>/js/displayTimeDate.js"></script> 
<link rel="stylesheet" type="text/css" href="<%=cPath %>/css/displayTimeDate.css"/>

<script type="text/javascript" src="<%= cPath %>/js/validateNumber.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function(){
	$(window).unload(function(){});
	
});

var validNavigation = false;

var controller = 0;
	$(document).ready(function (){
		

		$("#br2").hide();
		$("#overlay").hide();
	    $.widget( "ui.combobox", {
	        _create: function() {
	          var self = this;
	          var select = this.element.hide(),
	            selected = select.children( ":selected" ),
	            value = selected.val() ? selected.text() : "";
	          var input = $( "<input />" )
	          	.css('height','25px')
	          	.css('width','200px')
	            .insertAfter(select)
	            .val( value )
	            .autocomplete({
	              delay: 0,
	              minLength: 0,
	              source: function(request, response) {
	                var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
	                response( select.children("option" ).map(function() {
	                  var text = $( this ).text();
	                  if ( this.value && ( !request.term || matcher.test(text) ) )
	                    return {
	                      label: text.replace(
	                        new RegExp(
	                          "(?![^&;]+;)(?!<[^<>]*)(" +
	                          $.ui.autocomplete.escapeRegex(request.term) +
	                          ")(?![^<>]*>)(?![^&;]+;)", "gi"),
	                        "<strong>$1</strong>"),
	                      value: text,
	                      option: this
	                    };
	                }) );
	              },
	              select: function( event, ui ) {
	                ui.item.option.selected = true;
	                self._trigger( "selected", event, {
	                  item: ui.item.option
	                });
	              },
	              change: function(event, ui) {
	                if ( !ui.item ) {
	                  var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
	                  valid = false;
	                  select.children( "option" ).each(function() {
	                    if ( this.value.match( matcher ) ) {
	                      this.selected = valid = true;
	                      return false;
	                    }
	                  });
	                  if ( !valid ) {
	                    // remove invalid value, as it didn't match anything
	                    $( this ).val( "" );
	                    select.val( "" );
	                    return false;
	                  }
	                }
	              }
	            })
	            .addClass("ui-widget ui-widget-content ui-corner-left");
	         
	          input.data( "autocomplete" )._renderItem = function( ul, item ) {
	            return $( "<li></li>" )
	              .data( "item.autocomplete", item )
	              .append( "<a>" + item.label + "</a>" )
	              .appendTo( ul );
	          };
	         
	          $( "<button> </button>" )
	          .css('padding-top','9px')
	          .css('padding-bottom','9px')
	          .attr( "tabIndex", -1 )
	          .attr( "title", "Show All Items" )
	          .insertAfter( input )
	          .button({
	            icons: {
	              primary: "ui-icon-triangle-1-s"
	            },
	            text: false
	          })
	          .removeClass( "ui-corner-all" )
	          .addClass( "ui-corner-right ui-button-icon" )
	          .click(function() {
	            // close if already visible
	            if (input.autocomplete("widget").is(":visible")) {
	              input.autocomplete("close");
	              return;
	            }
	            // pass empty string as value to search for, displaying all results
	            input.autocomplete("search", "");
	            input.focus();
	          });
	        }
	      });


		
		 $('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
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
		    $('#display').tablePagination(options);

		
		$("#search_field").keyup(function(){
			var name=document.getElementById("search_field").value;
			var transaction=document.getElementById("transaction").value;
			$("#br2").hide();
			var nameLength = name.length;
			
			if(transaction=='municipal'){
				$("#br2").hide();
				var mun=document.getElementById("munc").value;
				var brgay=document.getElementById("brgay").value;
				if(mun!=null && brgay!='Brgy'){
					/* xhrGo("POST","TransactionView?municipal="+mun+"&transaction="+transaction+"&brgy="+brgay+"&name="+name, "plain"); */
					$.getJSON('Autocom',{municipal:mun,transaction:transaction,brgy:brgay,name:name},function(result){
						var pval=result.list;
						var tval=new Array();
						for(var i=0;i<pval.length;i++){
							tval[i]=pval[i];
						}
						$("#search_field").autocomplete({
							source:tval
						});
					});
					
				}else if(mun!=null && brgay=='Brgy'){
					$("#br2").hide();
					/* xhrGo("POST","TransactionView?municipal="+mun+"&transaction="+transaction+"&name="+name,  "plain"); */
					$.getJSON('Autocom',{municipal:mun,transaction:transaction,name:name},function(result){
						var pval=result.list;
						var tval=new Array();
						 for(var i=0;i<pval.length;i++){
							tval[i]=pval[i];
						}
						$("#search_field").autocomplete({
							source:tval
						});
					});
				}
			}else{
				/* xhrGo("POST","TransactionView?name="+name+"&transaction="+transaction, display, "plain"); */
				$("#br2").hide();
				if(nameLength>5 && transaction != 'household'){
					
					$.getJSON('Autocom',{transaction:transaction,name:name},function(result){
					//alert(result.list);
						 var pval=result.list;
						 var tval=new Array();
						 
						 for(var i=0;i<pval.length;i++){
							tval[i]=pval[i];
						 }
						 $("#search_field").autocomplete({
							source:tval
					     });
				 	});
				}
				else if(nameLength>11 && transaction == 'household'){
					$.getJSON('Autocom',{transaction:transaction,name:name},function(result){
							 var pval=result.list;
							 var tval=new Array();
							 
							 for(var i=0;i<pval.length;i++){
								tval[i]=pval[i];
							 }
							 $("#search_field").autocomplete({
								source:tval
						     });
					 	});
				}
			}
		});
		$("#container").hide();
		$("#container1").hide();
		$("#container2").hide();
		$("#container3").hide();
		$("#container4").hide();
		$("#message").hide();
		$("#mun").hide();
		$("#br").hide();
		$("#date").hide();
		$("#br3").hide();
		$("#search_btn").button({
			icons: {primary: "ui-icon-search"}
		});
		$("#sbf_btn123").button({
			icons: {primary: "ui-icon-search"}
		});
		$("#pop-up-add").button({
			icons: {primary: "ui-icon-circle-plus"}
		});
		
		//pagination buttons style
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
		//end
		 $('#grsCasesBtn').click(function(){
				$('#grsCasesDialog').dialog({
					show: "fade",
					hide: "fade",
					resizable: false,
					height: 520,
					width: 450,
					modal: true
					/* buttons: {
						"Ok": function() {
							 var inputs = document.getElementsByName("grsCasesRadio");
							 var grsCase = "";
							 var grsCasesCombo = "";
							 var grsCasesServer = "";
							 var grsRemarks = "";
							 var household_id = $("#hidden_householdId").val();
							 grsCasesCombo = document.getElementById("grsCasesCombo").value;
							 grsCasesServer = document.getElementById("grsServer").value;
							 grsRemarks = document.getElementById("grsRemarks").value;
							for (var i = 0; i < inputs.length; i++) {
					              if (inputs[i].checked) {
					                grsCase = inputs[i].value;
					              }
					        }
							if(grsCase==""){
								alert("Please select GRS Case.");
							}
							else{
								if(grsCasesServer==""){
									alert("Please select Server. ");
								}
								else{
									xhrGo("POST","AddGrsCases?grsCase="+grsCase+"&grsCasesCombo="+grsCasesCombo+"&household_id="+household_id+"&grsServer="+grsCasesServer+"&grsRemarks="+grsRemarks, addGrsResult, "plain"); //for municipal link
								}
							}
							//xhrGo("POST","Password_Confirmation?confirmation_password="+password, fingerprintConfirm, "plain"); for provincial link
							//xhrGo("POST","AddGrsCases?confirmation_password="+password+"&username="+username, fingerprintConfirm, "plain"); //for municipal link
						},
						"Cancel": function() {
							$( this ).dialog( "close" );
						}
					} */
				}); 
				
			});
		$("#sbf_btn").click(function(){
			$( "#dialog-select_mun" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:150,
				modal: true,
				buttons: {
					"Ok": function() {
						var municipality = $("#munc1").val();
						if(municipality=="Municipality"){
							$( "#dialog_no_mun_selected" ).dialog({
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
							$( this ).dialog( "close" );
							//xhrGo("POST","SearchByFingerprint?mun="+municipality,display, "plain");
							document.location.href = "<%=cPath %>/SearchByFingerprint?mun="+municipality;
						}
					},
					"Cancel": function(){
						$( this ).dialog( "close" );
					}
				}
			});
		});
		
		$("#sbf_btn123").click(function(){
			$( "#dialog-select_mun" ).dialog({
				show: "fade",
				hide: "fade",
				resizable: false,
				height:150,
				modal: true,
				buttons: {
					"Ok": function() {
						var municipality = $("#munc1").val();
						if(municipality=="Municipality"){
							$( "#dialog_no_mun_selected" ).dialog({
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
							//document.location.href = "/4PS/SearchByFingerprint?mun="+municipality;
						}
					},
					"Cancel": function(){
						$( this ).dialog( "close" );
					}
				}
			});
		});
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
		
		$("#transaction").change(function(){
			document.getElementById("search_field").value ="";
			var name=document.getElementById("search_field").value;
			var tran_value =document.getElementById("transaction").value;
			document.getElementById("date").value = "";
			
			if(tran_value=='municipal'){
				$( "#mun" ).fadeIn('slow');
				$( "#munc" ).focus();
				$( "#br" ).fadeIn( 'slow');
				$("#search_field").hide();
				$("#br2").hide();
				$("#date").hide();
				$("#br3").hide();
			}
			else if(tran_value=='set'){
				$( "#mun" ).hide();
				$( "#br" ).hide();
				$("#search_field").hide();
				$("#br2").hide();
				$("#date").hide();
				$( "#br3" ).fadeIn('slow');
				$("#hhsetgroup").focus();
				xhrGo("POST", "<%= cPath %>/ChooseReport", viewhhset, "plain");
				
			}
			else if(tran_value=='birth'){
				$("#br3").hide();
				$("#mun").hide();
				$("#br").hide();
				$( "#date" ).show( 'drop', 500);
				$("#br2").hide();
				$("#search_field").hide();
				$( "#date" ).datepicker({
					changeMonth: true,
					changeYear: true
				});
			}
			else if(tran_value == 'brgy'){
				$("#br3").hide();
				$("#search_field").hide();
				$("#mun").hide();
				$("#br").hide();
				$("#date").hide();
				$.getJSON('Autocom',{transaction:tran_value,name:name},function(result){
					//alert(result.list);
					var pval=result.list;
					//var tval=new Array();
					if(controller == 0){
						var str = "<option></option>";
						controller++;
					}
					else{
						var str = "<option value='"+document.getElementById("brgy_select").value+"'></option>";
					}
					
					for(var i=0;i<pval.length;i++){
						/* tval[i]=pval[i].brgy_name; */
						str += "<option value='"+pval[i].brgy_id+"' >"+pval[i].brgy_name+"</option>";
					}
					
					document.getElementById("brgy_select").innerHTML = str;
					$("#brgy_select").combobox();
					//$("#brgy_select").show( 'drop', 500).focus();
					$("#br2").show();
					
					/* $("#search_field").autocomplete({
						source:tval
					}); */
				});
			}
			else{
				$("#mun").hide();
				$("#br").hide();
				$("#date").hide();
				$("#search_field").show( 'drop', 500).focus();
				$("#br2").hide();
				$("#br3").hide();
				
			}
		});
		$("#munc").change(function(){
			var municipal=document.getElementById("munc").value;
			xhrGo("GET","ViewBarangy?municipal="+municipal, show, "plain");
		});
		$("#munc123").change(function(){
			var municipal=document.getElementById("munc123").value;
			var regex = municipal.split("+");
			var mun_id = regex[0];
			xhrGo("GET","ViewBarangy?municipal="+mun_id, show123, "plain");
		});
	
		$('#grsCasesBtn').button({
			icons : {primary : "ui-icon-circle-plus" }
		});
		
		$("html").scrollTop(50);
	});
	function viewhhset(data){
		
		var x = eval('('+data+')');
		var str= "";
		str += "<option value=''>HH Set Group</option>";
		for(var r=0;r<x.data.length;r++){
			str += "<option>"+x.data[r]+"</option>";
		}
		
		getID("hhsetgroup").innerHTML = str;
	}
	function searchByFingerprint(){
			xhrGo("POST","SearchByFingerprint",display, "plain");
	}
	function display(){
		
	}
	function show(data){
		//alert(data);
		var x=eval('('+data+')');
		var strbuild="";
		strbuild+="<td id='br'>"+
				"<select id='brgay' name='brgy' class='input'>"+
				"<option selected='selected' disabled='disabled' >Barangay</option>";
		for(var i=0;i<x.data.length;i++){
			strbuild+="<option value='"+x.data[i].brgy_id+"'>"+x.data[i].barangay+"</option>";
		}
			strbuild+="</select>"+
				"</td>";
		document.getElementById("br").innerHTML=strbuild;
		$( "#brgay" ).focus();
	}
	function show123(data){
		//alert(data);
		var x=eval('('+data+')');
		var strbuild="";
		strbuild+="<td id='br123'>"+
				"<select id='brgay123' required name='brgy123' class='input'>"+
				"<option selected='selected' disabled='disabled' >Barangay</option>";
		for(var i=0;i<x.data.length;i++){
			strbuild+="<option value='"+x.data[i].barangay+"'>"+x.data[i].barangay+"</option>";
		}
			strbuild+="</select>"+
				"</td>";
		document.getElementById("br123").innerHTML=strbuild;
		$( "#brgay123" ).focus();
	}
	$(function(){
		var dialogSettingsInEdit = {
				autoOpen:false,
				draggable:false,
				hide:'fade',
				show:'fade',
				resizeable:false,
				modal:true,
				width:'600',
				height:'440'
		};
		$("#ADDtransaction").dialog(dialogSettingsInEdit);

	});

	
	function AddTransaction(household_id){
	 	$("#ADDtransaction").dialog('open').load('Addtransaction?household_id='+household_id);
	}

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
	
	function getID(ID){
		return document.getElementById(ID);
	}

	function getIDValue(ID){
		return document.getElementById(ID).value;
	}
	
	/* function changeDivData(){
		var transaction = getIDValue("transaction");
		var val = getIDValue("search_field");
		var munc = getIDValue("munc");
		var brgy = getIDValue("brgay");
		var date = getIDValue("date");  
		var brgy_select;
		
		if(transaction == "brgy"){
			brgy_select = getIDValue("brgy_select");
			xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date+"&brgy_select="+brgy_select,displayDivData, "plain");
		}
		else{
			xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date,displayDivData, "plain");
		
		}

	} */
	function addGrsNR(){
		var household_idNR = getIDValue("household_idNRF");
		var fullNameNR = getIDValue("full_nameNRF");
		var municipalityNR = getIDValue("munc123");
		var regex = municipalityNR.split("+");
		var municipalNR = regex[1];
		var barangayNR = getIDValue("brgay123");
		var idocpNR = getIDValue("idocpNR");
		var remarksNR = getIDValue("grsRemarksNR");
		alert("household:"+household_idNR);
		alert(fullNameNR);
		alert(municipalNR);
		alert(barangayNR);
<<<<<<< HEAD
		alert(idocpNR);
		alert(remarksNR);
		xhrGo("POST","AddGrsCase?hh_id="+household_idNR+"&grsCase=NRF&syscode=O",addNRFresult, "plain");
=======
		alert("cf:"+idocpNR);
		alert(remarksNR);
		xhrGo("POST","AddGrsCase?hh_id="+household_idNR+"&grsCase=NRF&syscode=O&fullName="+fullNameNR+"&municipal="+municipalNR+"&barangay="+barangayNR+"&idocp="+idocpNR+"&remarks="+remarksNR,addNRFresult, "plain");
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
		/* brgy_select = getIDValue("brgy_select");
		var transaction = getIDValue("transaction");
		var val = getIDValue("search_field");
		var munc = getIDValue("munc");
		var brgy = getIDValue("brgay");
		var date = getIDValue("date");  
		var hhset = getIDValue("hhsetgroup");
		alert("transaction:"+transaction);
		alert(munc);
		alert(brgy);
		alert(date);
		alert(hhset);
		xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date+"&brgy_select="+brgy_select,displayDivData, "plain");
	 */
	}
	function addNRFresult(data){
<<<<<<< HEAD
		alert("addNRFresult");
=======
		if(data==1){
			//alert("password confirmed.!!");
			$( '#grsCasesDialog' ).dialog( "close" );
			$( "#addgrsOk" ).dialog({
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
		if(data==2){
			$( "#addgrsRecordFound" ).dialog({
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
			$( "#addgrsnotOk" ).dialog({
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
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
	}
	function changeDivData(){
		var transaction = getIDValue("transaction");
		var val = getIDValue("search_field");
		var munc = getIDValue("munc");
		var brgy = getIDValue("brgay");
		var date = getIDValue("date");  
		var hhset = getIDValue("hhsetgroup");
		var brgy_select;
		
		if(transaction == "brgy"){
			$("#div").show();
			$("#overlay").show();
			brgy_select = getIDValue("brgy_select");
			xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date+"&brgy_select="+brgy_select,displayDivData, "plain");
		}
		else if(transaction == "municipal"){
			$("#div").show();
			$("#overlay").show();
			xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date,displayDivData, "plain");
		}
		else if(transaction == "set"){
			$("#div").show();
			$("#overlay").show();
			xhrGo("POST","TransactionSearch?transaction="+transaction+"&hhset="+hhset,displayDivData, "plain");
		}
		else{
			if(val != "" || date != ""){
				$("#div").show();
				$("#overlay").show();
				xhrGo("POST","TransactionSearch?transaction="+transaction+"&val="+val+"&municipal="+munc+"&brgy="+brgy+"&date="+date,displayDivData, "plain");
			}
			else{
				//alert("please enter an input..wag pasaway");
				$( "#warning" ).dialog({
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

	}
	
	
	function displayDivData(data){
		
		var x = eval('('+data+')');
		var str = "";
		var str2 ="<tr><td></td</tr>";
		
		if(x.data == "" || x.data == "{}" || x.mess == "No Record Found" || x.day == "No Birthdays"){
			getID("tbody").innerHTML = "<tR><th align='center'  colspan='6' ><b  style ='color:red;font-size:23px;'  >No records Found!</b></th></tR>";
			/* getID("pager").value = "1/1"; */
		}
		else{
			var count = 1;
			for(var i=0;i<x.data.length;i++){
				
				str += "<tr class='tbl_r'>" +
							"<td id='id_cell'>" +count+ "</td>" +
							"<td id='id_cell' class='listID'>" +x.data[i].household_id+ "</td>" +
							"<td id='id_cell'>" +x.data[i].householdmember_id+ "</td>" +
							"<td id='id_cell'>" +x.data[i].name+ "</td>"+
							"<td id='id_cell9'><a href='<%=cPath %>/View_transactions2?hh_id="+x.data[i].household_id+"' >ViewProfile</a>&nbsp;&nbsp;<a href='Addtransaction?household_id="+x.data[i].household_id+"&head_name="+x.data[i].name+"&brgy="+x.data[i].brgy+"&municipal="+x.data[i].municipal+"' >ViewTransaction</a></td>";
				str += "</tr>";	
				count++;
				
			}
			
			getID("tbody").innerHTML = str;
			 $('tbody1 tr:odd', $('#display')).hide(); //hiding rows for test
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
			    $('#display').tablePagination(options);
		}
		setTimeout("fadeOutLoading()",1000);
	}
	
	/* ASK CONFIRMATION TO THE USER TO ABANDON THE SHIP.. HAHA... */
	var dont_confirm_leave = 0; //set dont_confirm_leave to 1 when you want the user to be able to leave without confirmation
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
	
	function checkSubmit(e)
	{
	   if(e && e.keyCode == 13)
	   {
		   changeDivData();
	   }
	}

</script>
<style type="text/css">
	
#display,#display2{
	margin-top:12px;
	border:  1px solid black; ;
	border-collapse: collapse;
	font-size: 13px;
	width: 880px;
}


#display th,#display2 th{
	padding-left: 5px;
	padding-right: 5px;
}

 #tablePagination { 
            font-size: 10px; 
            padding: 0px 5px; 
            height: 40px;
            width: 868px;
            border:  1px solid black; ;
            border-collapse: collapse;
            border-top: none;
            padding-top: 10px;
}
          
 #tablePagination_nextPage,#tablePagination_firstPage,#tablePagination_prevPage{
 	padding-left: 5px;
 	margin-left: 5px;
 }
 .ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon{
 	padding-bottom: 0px;
 }
#tablePagination_lastPage{
	padding-left: 5px;
	margin-left: 10px;
}
#tablePagination_prevPage{
	margin-right: 5px;
}
img{
	padding-right: 5px;
	cursor: pointer;
}

	
	#main{
		height: 600px;
	}
	.ui-widget{
		font-size: 12px;
	}
	.ui-autocomplete {
		max-height: 150px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
		padding-right: 20px;
	}
	.hidden{
		display: none;
	}
	h3{
		text-transform: capitalize;
		font-family: sans-serif;
		font-size: 18px;
		letter-spacing: 5px;
		margin-bottom: 3px;
	}
	#tableOne{
		border-collapse: collapse;
	
	}
	
	#table{
		border-collapse: collapse;
	}
	#tabOne{
		border-collapse: collapse;
	}
	#tabTwo{
		border-collapse: collapse;
	}
	#tabThree{
		border-collapse: collapse;
	}
	#table1{
		border-collapse: collapse;
	}
	#table2{
		border-collapse: collapse;
	}
	#table3{
		border-collapse: collapse;
	}
	#table4{
		border-collapse: collapse;
	}
	#table5{
		border-collapse: collapse;
	}
	#table6{
		border-collapse: collapse;
	}
	#table7{
		border-collapse: collapse;
	}
	.input{
		margin-bottom: 5px;
		border: 1px solid #8FA9BC;
		padding: 5px 3px 5px 10px;
		color:#000000;
		outline: none;
		margin-left: 20px;
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
	.tbl_r:HOVER {
		background-color: #ddd;
	}
	table {
	font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
	margin: 3px 3px;
}
	a{
		text-decoration: none;
	}
	a:HOVER{
		color: red;
		text-decoration: underline;
	}
	#sbf:HOVER {
		text-decoration: none;
	}
	.t{
		padding: 5px 10px;
		width: 180px;
		background-color: lightblue;
	}
	.ft{
		padding:0px auto;
		background-color: lightblue;
	}
	.tbl_r td{
		padding-left: 5px;
		padding-right: 8px;
		font-size: 16px;
	}
	.t2{
		padding: 5px 10px;
		width: 200px;
		background-color: lightblue;
	}
	.t23{
		padding: 5px 10px;
		width: 120px;
		background-color: lightblue;
	}
	.t3{
		padding: 5px 10px;
		width: 280px;
		background-color: lightblue;
	}
	td{
		padding:5px 0px 5px;
	}
	
	#brgay123{
		width: 245px;
	}
.overlay{
	height: 100%;
	width: 100%;
	padding: 0;
	top: 0;
	bottom: 0;
	left:0;
	background-color: black;
	opacity: .85;
	z-index: 900;
	-moz-opacity: .85;
	display: block;
	position: fixed;
	filter: alpha(opacity=1000);
	
}	
	
.load{
	/* margin-top: -450px; */
	z-index: 1000;
	right: 50%;
	position: fixed;
	top: 30%;
	background-position: center;
	color: red;
}
#page-title{
	text-transform: capitalize;
	font-size: 18px;
	letter-spacing: 4px;
	font-family: helvetica, sans-serif;
	text-shadow: 2px 2px 2px #999;
}

#munc{
	width:180px;
}
#brgay{
	width:230px;
}
.tbl_r .listID{
	font-size: 14px;
}
#grsCasesBtn{
	position: absolute;
	right: 0;
	margin-right: 20px;
}
#divGrsBTN{
	position: relative;	
	height: 30px;
}

</style>
</head>
<%
	SimpleDateFormat dateSDF = new	SimpleDateFormat("MMMM dd,yyyy");
	SimpleDateFormat dw = new SimpleDateFormat("EEEE");
 	Calendar s = Calendar.getInstance();
	Date today = s.getTime();
	String sDate = dateSDF.format(today);
	String Dw = dw.format(today);
%>
<body onload="updateClock(); setInterval('updateClock()', 1000 )">
<div id="displayTD">
<table id="tbl">
	<tr> <td><input checked="checked" onclick="hide();" id="hide" type="checkbox"/> Keep this date & time in view</td> </tr>
 	<tr>
 		<td class="day"><%if(Dw.equals("Sunday")){%> <font id="sun"><% out.println(Dw); %>,</font> 
						  <%}else{%> <font class="mon-sat"><% out.println(Dw); %>,</font><% } %>
		<!-- </td>
 		<td class="timeDate"> --><font class="mon-sat"><% out.println(sDate); %></font><!-- <hr> --></td></tr><!--displaying Date[jm]  -->
	<tr><td class="timeDate"> <span id="clock">&nbsp;</span><!-- <hr> --> </td></tr><!--displaying Time[jm]  -->
	</table>		 
</div>
<div id="page-wrap">
<div id="header" >
<div id="logo" >
	<img id="logo_image" alt="" src="<%=cPath %>/logos/headers-mag.jpg">
	<div id="display-login"> Login as <c:out value="${duser_rolename }"></c:out>(<c:out value="${dfname }"></c:out>) </div>
</div><!-- End of Logo -->
</div><!-- End of Header -->
<div id="main-content">

<div id="menu">

<jsp:include page="../home/menu.jsp"></jsp:include>

</div>

<div id="main">
<div>
<h3 align="center" id="page-title" >View Records</h3>
		<c:choose>
			<c:when test="${verifier}">
				<div id="divGrsBTN">
					<button type="button" id="grsCasesBtn" onclick="add_grs()">Add GRS No Record</button>
				</div>
			</c:when>
		</c:choose>
<div align="center">

	<table id="tab">
		<tr>
			<td><label for="transaction"><select name="transaction" id="transaction" class="input">
					<option selected="selected" value="household" id="household">Search By HouseHold ID NO.</option>
					<option value="grantee" id="grantee">Search By Grantee</option>
					<option value="wife" id="wife">Search By Wife/Spouse</option>
					<option value="student" id="student">Search By Student</option>
					<option value="municipal" id="municipal">Search By Municipality</option>
					<option value="brgy" id="brgy">Search By Barangay</option>
					<option value="birth" id="birth">Search By Birthday</option>
					<option value="set" id="set">Search By HH Set Group</option>
				</select></label>
			</td>
			<td id="mun"><select name="municipal" id="munc" class="input">
							<option selected="selected" disabled="disabled" >Municipality</option>
							<c:forEach items="${municipal_list}" var="list">
								<option value="${list.mun_id}" ><c:out value="${list.municipal}"></c:out></option>
							</c:forEach>
						</select>
			</td>
			<td id="br"><select name="brgy" id="brgay" class="input">
						<option selected="selected" disabled="disabled">Barangay</option>
					</select>
			</td>
			<td id="br2" >
				<select id='brgy_select' name='brgy_select' >
					
				</select>
			</td>
			<td id="br3" >
				<select id='hhsetgroup' class="input" name='hhsetgroup' >
					
				</select>
			</td>
			<td><label for="search_field"><input name="val" onKeyPress="return checkSubmit(event)" class="input" id="search_field" size="35" autocomplete="off">
			<input name="date" class="input" id="date"></label></td>
			<td><button onclick="changeDivData();" id="search_btn">Search</button> </td>
			<!-- <td><button type="button" id="sbf_btn" >Search by fingerprint</button></td> -->  
			<td><button type="button" id="sbf_btn123" >Search by fingerprint</button></td>
		</tr>
	</table>

</div>

<div id="ADDtransaction" style="display: none;" title="Add Transaction"></div>

<div id="mess" align="center">
<c:if test="${mess=='No Record Found'}">
	<h1>No Records Found</h1>
</c:if>
<c:if test="${day=='No Birthdays'}">
	<h1>No Records found on that birthday.</h1>
</c:if>
</div>
<div align="center" id="message"></div>

<div id="if5" align="center">

	<table align="center" id="display" class="paginated display sortable"  border="1" cellpadding="5px">
		<thead>
		<tr>
			<th class="ft">#</th>
			<th class="t">Household ID NO.</th>
			<th class="t23">HH Member ID NO.</th>
			<th class="t3">Name of Head</th>
			<th colspan="2" align="center" class="t2">Action</th>
		</tr>
		</thead>
		<tbody id="tbody" >
		<% int count = 0; %>
			<c:forEach items="${householdlist}" var="list">
				<tr class="tbl_r">
					<td><%=++count %></td>
					<td class="listID"><c:out value="${list.household_id}"></c:out></td>
					<td><c:out value="${list.householdmember_id}"></c:out></td>
					<td class="listID"><c:out value="${list.name}"></c:out></td>
					<td><a href="<%=cPath %>/View_transactions2?hh_id=${list.household_id}">ViewProfile</a>&nbsp;&nbsp;<a href="Addtransaction?household_id=${list.household_id}&head_name=${list.name}&brgy=${list.brgy}&municipal=${list.municipal}" >ViewTransaction</a></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot id="tfoot" align="center">

		</tfoot>
	</table>

</div>

</div>

</div>
</div> <!-- end of main content -->
<div id="dialog-select_mun" title="4PS system" style="display:none;">
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
<div id="dialog-confirm" title="Logout" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to logout ?</p>
</div>

<div id="warning" title="Warning!" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>No input to be search. Please fill up the field.</p>
</div>
<div id="fpt_dialog" style="display: none;">
	<p><label>Scan your fingerprint.</label></p>
					<br/>
	<p style="margin-left: 30px;"><img src="<%= cPath %>/images/loading.gif" /></p>

</div>
<div id="verificationFailed" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Failed to perform verification. The fingerprint reader is used by another application.</p>
</div>
<div id="noMatchFound" title="4Ps Message" style="display: none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>No match found.</p>
</div>

<div class="overlay" id="overlay">
</div>
<div id="div" class="load hidden">
	<img alt="" src="<%=cPath %>/images/loading_transparent.gif"><br>
	
</div>
<div class="hidden" id="grsCasesDialog" title="GRS CASES">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		Add GRS No Records
	</p>
	<div id="divGRS2" >
		<table id="divGRS" >
			<tr>
				<td>
					<label>Household ID : </label>
				</td>
				<td>
					<input required type="text"  class="input" onkeypress="return numbersonly(event,false);" size="35" name="household_idNRF" id="household_idNRF" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Full Name : </label>
				</td>
				<td>
					<input required type="text" class="input" size="35" name="full_nameNRF" id="full_nameNRF" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Municipality : </label>
				</td>
				<td>
					<select required name="municipal" id="munc123" class="input">
						<option selected="selected" disabled="disabled" >Municipality</option>
						<c:forEach items="${municipal_list}" var="list">
							<option value="${list.mun_id}+${list.municipal}" ><c:out value="${list.municipal}"></c:out></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Barangay : </label>
				</td>
				<td id="br123">
					<select required name="brgy123" id="brgay123" class="input">
						<option selected="selected" disabled="disabled">Barangay</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>ID-OCP : </label>
				</td>
				<td>
					<!-- <select name= "grsCasesCombo" class="input" id="grsCasesCombo">
						<option value="">------</option>
						<option value="LID">LID</option>
						<option value="TID">TID</option>
						<option value="OCP">OCP</option>
					</select> -->
					<select name="grsCasesCombo" class="input" id="idocpNR">
<<<<<<< HEAD
						<option value="">------</option>
=======
						<option value="0">------</option>
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
						<c:forEach items="${iDOCP}" var="list">
								<option value="${list.grsidoctypes_id}" title="${list.grsidoctypes_description}" ><c:out value="${list.grsidoctypes_casetypes}"></c:out></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<label></label>
	</div>
	<div> 
		<table>
			<tr>
				<td>
					<label>Remarks</label>
				</td>
				<td>
					<textarea class="input" rows="" cols="" style="width: 270px;height: 90px;font-size: 16px;" id="grsRemarksNR" name="grsRemarks"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div style="border-top: 1px solid gray; margin-top: 20px;">
	<div style=" width: 85px; margin: 0 auto; margin-top: 8px; margin-right: 20px;">
		<button id="pop-up-add" onclick="addGrsNR()" >Add</button>
	</div>
	</div>
</div>
<<<<<<< HEAD
=======
<div id="addgrsOk" title="4Ps Message" class="hidden">
	<p>GRS Cases Successfully added.</p>
</div>
<div id="addgrsnotOk" title="4Ps Message" class="hidden">
	<p>Failed to add GRS Cases.</p>
</div>
<div id="addgrsRecordFound" title="4Ps Message" class="hidden">
	<p>Household ID is found in the database.</p>
</div>
>>>>>>> c9b8fd23e02e6eadf94f92d3bee831babfaab912
</div> <!-- end of page wrap -->
</body>
</html>