	var beginFilterIndexRows = 2;
	var beginFilterIndexColumn = 4;
	var indexFilterRow = 1;
	//////////////////////////////////
	var filterParents = [];
	var filterChildren = [];
	var reworkTable;

$(function() {
	
		$('#datepicker').datepicker({
			format: 'dd.mm.yyyy',
			locale: 'ru-ru',
            uiLibrary: 'bootstrap4'
        });
		
  		$('[data-toggle="tooltip"]').tooltip();
		$('#reworkTable').find('select').selectpicker();
		$('#reworkTable').find('select').selectpicker('hide');
		
				// Erace field search
				$("#eraserSearch").click(function() {
					$("#searchInput").val('');
				});
				
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
				
				// update status show form start///////////////////////////////////////////////////////////////////////////
				$(".selectpicker").change(function() {
					var serverName = $("#server").val();
					var port = $("#port").val();
					var $parentThis = $(this);
					var valueStatus = $(this).find(":selected").text();

					var response;
					var column_index = $(this).closest("td").index();
						if(column_index===4) {column_index = 4}
						else { column_index =  Math.floor((column_index + 3) / 2) + 1 }
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
												
						$.get(updateStatusUrl, function(  ) {
		
							}).done(function( data ){
								response = ""+data;
								if(response == "All servers is done!") {
									response =  "Доработка полностью установлена!"; 
									$parentThis.parent().parent().parent().addClass('liteGreenAllServers');
								} else {$parentThis.parent().parent().parent().removeClass('liteGreenAllServers');}
								
								var $alertUpdate = alertUpdate(response+" "+name +" : "+server+" : ", " Статус был обновлён! на "+valueStatus);
	
									$("body").before($alertUpdate);
										
											var $visibleValueStatusDIV = $parentThis.parent().parent().find('.statusDIV');
											$visibleValueStatusDIV.text(valueStatus);
											var $dateCell = $parentThis.parent().parent().next('td');
											if(valueStatus) {
												const year = new Date().getFullYear().toString().slice(-2);
												const month = new Date().getMonth();
												const MONTH = ["01","02","03","04","05","06","07","08","09","10","11","12"];
												const day = new Date().getDate();
												var dateFormat =  ""+day + "." + MONTH[month] + "." + year;
											    $dateCell.text(dateFormat);
											} else {
												$dateCell.text("");
											}
								
							}).fail(function() {
									var $alertErr = alertErr(name +" : "+server, " Статус не был обновлён!");
	 								$("body").before($alertErr);//unexpected error!
										upToPage();
	  						}).always(function() {
								hideUpdateStatusModal.call();
							});
					});
					
					$('#updateStatusModal').modal('show');
				});
				///////Update date
				$('[date="updateDate"]').click(function() {
					
					var serverName = $("#server").val();
					var port = $("#port").val();
					var $parentThis = $(this);
					
					var response;
					var column_index = $(this).closest("td").index()-1;
						if(column_index===4) {column_index = 4}
						else { column_index =  Math.floor((column_index + 3) / 2) + 1 }
					//
					var reworkNumber = $(this).closest("tr").children('td').eq(0).text();
					var name = $(this).closest("tr").children('td').eq(1).text();
					var task = $(this).closest("tr").children('td').eq(2).text().trim();
					var server = $('#headerMainTable > tr').children('th').eq(column_index).text().trim();
					var date = $parentThis.text().trim();
					
					if(!date) return;

					$('#updateDateModal').on('show.bs.modal', function (e) {
						$('#nameUpdateDateLabel').text(name);
						$('#taskUpdateDateLabel').text(task);
						$('#serverUpdateDateLabel').text(server);
						var $datepicker = $('#datepicker').datepicker();
						const year_Now = new Date().getFullYear();
						const partsDate = date.split('.');						
						$datepicker.value(partsDate[0]+'.'+partsDate[1]+'.'+year_Now);
						$('#reworkNumberUpdateDateLabel').text(reworkNumber);
					});
					
					$("#updateDateButton").click(function() {
						var $datepicker = $('#datepicker').datepicker();
						var	name =	        $('#nameUpdateDateLabel').text();
						var	reworkNumber =	$('#reworkNumberUpdateDateLabel').text();
						var	server =	    $('#serverUpdateDateLabel').text();
						var	date =			$datepicker.value();
						//
						var updateDatesUrl = "http://"+ serverName +":"+ port +"/rework-app1-monetka/main/updatedate?"
						+"reworkNumber="+ reworkNumber 
						+"&server="+ server
						+"&date="+date;					
												
						$.get(updateDatesUrl, function(  ) {
		
							}).done(function( data ){
								response = ""+data;
	
									var $dateCell = $parentThis;
									$dateCell.text(response);
											
									var $alertUpdate = alertUpdate(name +" : "+server+" : ", " Дата была обновлёна! на "+response);
									$("body").before($alertUpdate);
								
							}).fail(function() {
									var $alertErr = alertErr(name +" : "+server, "'"+response+"' Дата не обновлена!");
	 								$("body").before($alertErr);//unexpected error!
										upToPage();
	  						}).always(function() {
								hideUpdateDateModal.call();
							});
					});
					$('#updateDateModal').modal('show');
				});
				
				$('#cancelUpdateStatusModal').click(hideUpdateStatusModal);			
				$('#cancelUpdateDateModal').click(hideUpdateDateModal);
				
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
	
	var hideUpdateDateModal = function(){
		$('#updateDateModal').modal('hide');
		$("#updateDateButton").unbind( "click" );
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
