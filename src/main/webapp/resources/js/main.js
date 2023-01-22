	var beginFilterIndexRows = 2;
	var beginFilterIndexColumn = 4;
	var indexFilterRow = 1;
	//////////////////////////////////
	var filterParents = [];
	var filterChildren = [];
	var reworkTable;

$(function() {
	
  		$('[data-toggle="tooltip"]').tooltip();
		$('#reworkTable').find('select').selectpicker();
		$('#reworkTable').find('select').selectpicker('hide');
		
				$("#buttonWRAP").click(function() {
					if($(this).attr('iswrraped') == 'false') {
						$(this).attr('iswrraped','true');
							$('#arrow-bar-right').addClass('d-none');
							$('#arrow-bar-left').removeClass('d-none');
							$(this).parent().css('width', '1400px');
						
					} else {
						$(this).attr('iswrraped','false');
							$('#arrow-bar-left').addClass('d-none');
							$('#arrow-bar-right').removeClass('d-none');
							$(this).parent().css('width', '460px');
					}
				});
				//table FIlter
				reworkTable = document.getElementById("reworkTable");
			
				var filterCountColumn = reworkTable.rows[1].cells.length;
				for (let indexRow = beginFilterIndexRows; indexRow < reworkTable.rows.length; indexRow++) {
					for (var i = beginFilterIndexColumn; i < filterCountColumn; i++) {
						var cell = reworkTable.rows[indexRow].cells[i];
							cell.addEventListener('click', showSelectedList);
					}
				}
		
				///delete rework show modal////////////////////////////////////////////////////////////////////////////////
				var deleteRework_reworknumber=$("#deleteRework_reworknumber").val();	
					
				if(deleteRework_reworknumber !='') {
					var $alertDelete = alertDelete(deleteRework_reworknumber +" : ", " доработка была удалена!");
					$("body").before($alertDelete);
				}		
				// update status show form start///////////////////////////////////////////////////////////////////////////
				$(".selectpicker").change(function() {
					var serverName = $("#server").val();
					var port = $("#port").val();
					var $parentThis = $(this);
					var valueStatus = $(this).find(":selected").text();

					var response;
					var column_index = $(this).closest("td").index();
						if(column_index>4) column_index -= 1;
					//
					var reworkNumber = $(this).closest("tr").children('td').eq(0).text();
					var name = $(this).closest("tr").children('td').eq(1).text();
					var task = $(this).closest("tr").children('td').eq(2).text().trim();
					var server = $('#headerMainTable > tr').children('th').eq(column_index).text().trim();

					$('#updateStatusModal').on('show.bs.modal', function (e) {
						$('#nameUpdateStatusLabel').text(name);
						$('#taskUpdateStatusLabel').text(task);
						$('#serverUpdateStatusLabel').text(server);
						$('#statusUpdateStatusLabel').text(valueStatus);
						$('#reworkNumberUpdateStatusLabel').text(reworkNumber);
					});
				
					$("#updateStatusButton").click(function() {
						var	name =	        $('#nameUpdateStatusLabel').text();
						var	reworkNumber =	$('#reworkNumberUpdateStatusLabel').text();
						var	server =	    $('#serverUpdateStatusLabel').text();
						var	valueStatus =	$('#statusUpdateStatusLabel').text();
						var	whoUpdate =     $('#whoUpdatesSelect').find(":selected").text();
						//
						var updateStatusUrl = "http://"+ serverName +":"+ port +"/rework-app1-monetka/main/updatestatus?"
						+"reworkNumber="+ reworkNumber 
						+"&server="+ server
						+"&valueStatus="+valueStatus
						+"&whoUpdate="+whoUpdate;					
												
						$.get(updateStatusUrl, function( data ) {
							response = ""+data;
							var $alertUpdate = alertUpdate(name +" : "+server, " статус был обновлён!");
							var $alertErr = alertErr(name +" : "+server, "status is not updated!");
							if(response == 'updateIsDone') {
								$("body").before($alertUpdate);
									upToPage();
										var $visibleValueStatusDIV = $parentThis.parent().parent().find('.statusDIV');
										$visibleValueStatusDIV.text(valueStatus);
											setTimeout(() => {
												$('.alert').alert('close');
											}, 2100);
							} else {
								$("body").before($alertErr);//unexpected error!
									upToPage();
							}
							hideUpdateStatusModal.call();
						});
					});
					
					$('#updateStatusModal').modal('show');
				});
				
				$('#cancelUpdateStatusModal').click(hideUpdateStatusModal);
				
				$('#loading').addClass('d-none');
				$('.container-fluid').removeClass('d-none');
				
				$('#topPageButton').click(function() {
						upToPage();
				});
				
				var mybutton = document.getElementById("topPageButton");

				// When the user scrolls down 20px from the top of the document, show the button
				window.onscroll = function() {scrollFunction()};
				
				function scrollFunction() {
				  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
				    mybutton.style.display = "block";
				  } else {
				    mybutton.style.display = "none";
				  }
				}
				$('[submitLinkShowReworkForm]').click(function() {
					$(this).closest('form').submit();
				});				

});
	
	var hideUpdateStatusModal = function(){
		$('#updateStatusModal').modal('hide');
		$('#reworkTable').find('select').selectpicker('hide');
		$('.statusDIV').show();
		$("#updateStatusButton").unbind( "click" );
	}
	
	function upToPage() {
		document.body.scrollTop = 0;
		document.body.scrollLeft = 0;
  		document.documentElement.scrollTop = 0;
		document.documentElement.scrollLeft = 0;
	}

	var showSelectedList = function (event) {
		var $target = $(event.target);
	
		switch ($target.prop('nodeName')) {
			case 'div'.toUpperCase():
				//console.log('----Click on DIV');
				$target = $target.parent();
				break;
			case 'td'.toUpperCase():
				//console.log('----Click on TD');
				$target = $target;
				break;
			default:
				//console.log('return: '+$target.prop('nodeName'));
				return;
		}
		if($target.children().length === 2) {
			$target.children().each(function(index, value){
				if(index === 0) {
					if($(value).is(':visible')) {
						$('.statusDIV').show();
							$(value).hide();
					}
				} 
				
				if(index === 1) {
					if(!$(value).find('.selectpicker').is(':visible')) {
						$('.selectpicker').selectpicker('hide');
						var $selectpicker = $(value).find('.selectpicker');
						var statusVisible = $(value).parent().find('.statusDIV').eq(0).text();
						$selectpicker.selectpicker('val', statusVisible);
						$selectpicker.selectpicker('show');
					}
				}
			});
		}
	 };
