
	var beginFilterIndexRows = 2;
	var beginFilterIndexColumn = 4;
	var indexFilterRow = 1;
	//////////////////////////////////
	var filterParents = [];
	var filterChildren = [];
	var reworkTable;

	$(function() {
		
  		$('[data-toggle="tooltip"]').tooltip();
		
		//$('#reworkTable').find('select').attr('data-style','btn-default btn-lg');
		$('#reworkTable').find('select').selectpicker();
		$('#reworkTable').find('select').selectpicker('hide');
		
				//table FIlter
				reworkTable = document.getElementById("reworkTable");
				//set filters and children
				filterParents = getListElementsByPartId('filter_header');
				filterChildren = getListElementsByPartId('filter_status');
				// add listeners on filters	
				for(obj of  filterParents) {
					for (let i = 0; i < obj.children.length; i++) {
						obj.children[i].addEventListener('click', addClassChange);
							obj.children[i].addEventListener('click', filterRows);
					}
				}
				// 
				var filterCountColumn = reworkTable.rows[0].cells.length;
				for (let indexRow = beginFilterIndexRows; indexRow < reworkTable.rows.length; indexRow++) {
					for (var i = beginFilterIndexColumn; i < filterCountColumn; i++) {
						var cell = reworkTable.rows[indexRow].cells[i];
							cell.addEventListener('click', showSelectedList);
					}
				}
		
		
				$("#reworkTable").find("tr").each(function( index ) {
					var row = $(this);
					var tdArray = row.find("td")
					tdArray.each(function( index ) {
						var td = $(this)
						if (typeof td.attr('hidden') !== 'undefined' && td.attr('hidden') !== false) {
							var wms = td;
							var last = wms.children()[0];
    						console.log( index + ": " + $(last).val());
						}
					});
				});
				$("#searchInput").keyup(function() {
					$("select#inputGroupSelect01 option:selected").each(function() {
					     $("#reworkTable").find("tr").each(function( index ) {
						     if(index >= beginFilterIndexRows) {
									var row = $(this);
										//highlight(row);
							}
						});
					});	
				});
				$("#searchButton").click(function() {
					var wms = "";
				    $("select#inputGroupSelect01 option:selected").each(function() {
				      wms = $( this ).text();
				      $("#reworkTable").find("tr").each(function( index ) {
						var row = $(this);
						var td_wms = $(row).find("td").eq(0).text();
						var td_number_rework = $(row).find("td").eq(1).text();
						var td_descr = $(row).find("td").eq(3).text();
							var searchSubString = $("#searchInput").val().trim();
							var regex = new RegExp('.*'+escapeRegExp(searchSubString)+'.*');
						if(index >= beginFilterIndexRows) {
							if(wms == 'Все') {
								td_wms = wms;
							}
							if(td_wms == wms && regex.test(td_number_rework + td_descr)) {
								$(row).attr("permanentHidden","false");
								$(row).show();
								//console.log("show: "+index+"  "+wms);
							} else {
								$(row).attr("permanentHidden","true");
								$(row).hide();
								//console.log("hide: "+index+"  "+wms);
							}
                   }
					});
				      //console.log(wms);
				    });
				});
				///new insert rework show modal////////////////////////////////////////////////////////////////////////////////
				var newInsertRework_wms=$("#newInsertRework_wms").val();
				var newInsertRework_reworknumber=$("#newInsertRework_reworknumber").val();	
				var newInsertRework_project= $("#newInsertRework_project").val();
				var newInsertRework_status=$("#newInsertRework_status").val();	
				
				if(newInsertRework_wms != '' && newInsertRework_reworknumber !='') {
					var $alertInsert = alertInsert(newInsertRework_reworknumber +" : "+newInsertRework_wms+" : "+newInsertRework_project+" : "+newInsertRework_status, " доработка была добавлена!");
					$("body").before($alertInsert);
				}
				///
				// update status show form start////////////////////////////////////////////////////////////////////////////////
				$(".selectpicker").change(function() {
					var serverName = $("#server").val();
					var port = $("#port").val();
					var $parentThis = $(this);
					var valueStatus = $(this).find(":selected").text();
					//var row_index = $(this).closest("tr").index();
					var response;
					var column_index = $(this).closest("td").index();
					//
					var wms = $(this).closest("tr").children('td').eq(1).text();
					var reworkNumber = $(this).closest("tr").children('td').eq(2).text().trim();
					var project = $('#headerMainTable > tr').children('th').eq(column_index).children('div').eq(0).text().trim();


					$('#updateStatusModal').on('show.bs.modal', function (e) {
						$('#wmsUpdateStatusLabel').text(wms);
						$('#reworkNumberUpdateStatusLabel').text(reworkNumber);
						$('#projectUpdateStatusLabel').text(project);
						$('#statusUpdateStatusLabel').text(valueStatus);
					});
					
					$("#updateStatusButton").click(function() {
						var	wms =	        $('#wmsUpdateStatusLabel').text();
						var	reworkNumber =	$('#reworkNumberUpdateStatusLabel').text();
						var	project =	    $('#projectUpdateStatusLabel').text();
						var	valueStatus =	$('#statusUpdateStatusLabel').text();
						var	whoUpdate =     $('#whoUpdatesSelect').find(":selected").text();
						//
						var updateStatusUrl = "http://"+ serverName +":"+ port +"/rework-app1/mainfilter/updatestatus?"
						+"wms="+ wms 
						+"&reworkNumber="+ reworkNumber 
						+"&project="+ project 
						+"&valueStatus="+valueStatus
						+"&whoUpdate="+whoUpdate;
						
						$.get(updateStatusUrl, function( data ) {
							response = ""+data;
							var $alertUpdate = alertUpdate(reworkNumber +" : "+wms+" : "+project, " статус был обновлён!");
							var $alertErr = alertErr(reworkNumber +" : "+wms+" : "+project, "status is not updated!");
							if(response == 'updateIsDone') {
								$("body").before($alertUpdate);
									upToPage();
										var $visibleValueStatusDIV = $parentThis.parent().parent().find('.statusDIV');
										$visibleValueStatusDIV.text(valueStatus);
							} else {
								$("body").before($alertErr);//unexpected error!
									upToPage();
							}
							hideUpdateStatusModal.call();
						});
						
					});
					
					$('#updateStatusModal').modal('show');
				});
				// update status show form end//////////////////////////////////////////////////////////////////////
				// tooltip cell stauts start////////////////////////////////////////////////////////////////////////
				$(".cell").on('mouseenter', function () {
					var serverName = $("#server").val();
					var port = $("#port").val();
					var $parentThis = $(this);
					var row_index = $(this).closest("tr").index();
					var response;
					var column_index = $(this).closest("td").index();
					//
					var wms = $(this).closest("tr").children('td').eq(1).text();
					var reworkNumber = $(this).closest("tr").children('td').eq(2).text().trim();
					var project = $('#headerMainTable > tr').children('th').eq(column_index).children('div').eq(0).text().trim();
					var tooltipUrl = "http://"+ serverName +":"+ port +"/rework-app1/mainfilter/tooltip/cell?wms="+ wms +"&reworkNumber="+ reworkNumber +"&project="+project;
					
					$.ajax({
				        type: "POST",                            
				        url: tooltipUrl, //'http://localhost:8080/rework-app1/mainfilter/tooltip/cell?wms=INFOR_WMS_10&reworkNumber=FBR0080&project=БАРС',
						dataType: "json",
				        headers: {
				                  "Accept": "application/json"
				                },
				    }).done(function( data ) {
						var dateAdd = ' - ';
						var timeAdd = ' - ';
						var dateEdit = ' - ';
						var timeEdit = ' - ';
							
						if (data.addDate != null) {
							var day = (data.addDate.localDateTime[2] <= 9) ? '0' + data.addDate.localDateTime[2] : data.addDate.localDateTime[2]; //DAY
							var mounth = (data.addDate.localDateTime[1] <= 9) ? '0' + data.addDate.localDateTime[1] : data.addDate.localDateTime[1]; //MOUNTH
							var year =  data.addDate.localDateTime[0]; //YEAR
							var hours =  (data.addDate.localDateTime[3] <= 9) ? '0' + data.addDate.localDateTime[3] : data.addDate.localDateTime[3]; //HOURS
							var minutes = (data.addDate.localDateTime[4] <= 9) ? '0' + data.addDate.localDateTime[4] : data.addDate.localDateTime[4]; //MINUTES
							dateAdd = day + '-' + mounth + '-' + year;
							timeAdd = hours + ':' + minutes;
						}
						if (data.editDate != null) {
							var day = (data.editDate.localDateTime[2] <= 9) ? '0' + data.editDate.localDateTime[2] : data.editDate.localDateTime[2]; //DAY
							var mounth = (data.editDate.localDateTime[1] <= 9) ? '0' + data.editDate.localDateTime[1] : data.editDate.localDateTime[1]; //MOUNTH
							var year =  data.editDate.localDateTime[0]; //YEAR
							var hours =  (data.editDate.localDateTime[3] <= 9) ? '0' + data.editDate.localDateTime[3] : data.editDate.localDateTime[3]; //HOURS
							var minutes = (data.editDate.localDateTime[4] <= 9) ? '0' + data.editDate.localDateTime[4] : data.editDate.localDateTime[4]; //MINUTES
							dateEdit = day + '-' + mounth + '-' + year;
							timeEdit = hours + ':' + minutes;
						}
						//
						$parentThis.attr('data-original-title', '<div class=\'text-left\' >' 
						+'<br><b>Кто доб.: </b>' +(data.addWho == null ? ' - ' : data.addWho)
						+'<br><b>Кто обн.: </b>' +(data.editWho == null ? ' - ' : data.editWho)
						+'<br><b>Когда доб.: </b>'+dateAdd
						+'<br>время : '+timeAdd
						+'<br><b>Когда обн.: </b>'+dateEdit
						+'<br>время : '+timeEdit
						+'</div>');
			  	    });	
				});
				// tooltip cell stauts end////////////////////////////////////////////////////////////////////////////////
				
				$("#buttonWRAP").click(function() {
					if($(this).attr('iswrraped') == 'false') {
						$('#wrapRow').removeClass();
						$('#wrapRow').addClass('wraprowh');
						$(this).attr('iswrraped','true');
						$('div[id*=filter_status_]').addClass("d-none");
							$('#collapseWrap').addClass('d-none');
							$('#expandWrap').removeClass('d-none');
						
					} else {
						$('#wrapRow').removeClass();
						$('#wrapRow').addClass('wraprow');
						$(this).attr('iswrraped','false');
						$('div[id*=filter_status_]').removeClass("d-none");
							$('#collapseWrap').removeClass('d-none');
							$('#expandWrap').addClass('d-none');
					}
				});
				$("#buttonWRAP2").click(function() {
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
				
				$('#cancelUpdateStatusModal').click(hideUpdateStatusModal);
				

	});
	function submitLinkShowReworkForm() {
		$('#formBack').submit();
	}
	
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
	
	function escapeRegExp(text) {
  		return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\$&');
	}
	
	function highlight(row) {
		var td_number_rework = $(row).find("td").eq(1)[0];
		var td_descr = $(row).find("td").eq(3)[0];
		var opar1 = td_number_rework.innerHTML;
		var opar2 = td_descr.innerHTML;
		 var search = $("#searchInput").val();
		 search = search.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); 
		  var re = new RegExp(search, 'g');
		
		  if (search.length > 0) {
		    td_number_rework.innerHTML = opar1.replace(re, `<mark>$&</mark>`);
		    td_descr.innerHTML = opar2.replace(re, `<mark>$&</mark>`);
		  } else {
		    td_number_rework.innerHTML = opar1;
		    td_descr.innerHTML = opar2;
		  }
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

	var addClassChange = function (event) {
		if(event.target.className == 'unselect') {
			event.target.className = 'selected'
			event.target.setAttribute("isSelected", "true");
		} else {
			event.target.className = 'unselect'
			event.target.setAttribute("isSelected", "false");
		}
    };

	var filterRows = function (event) {
		var tableRows = reworkTable.rows;
		for (let i = beginFilterIndexRows; i < tableRows.length; i++) {
			if($(tableRows[i]).attr("permanentHidden") == "false") {
				var isShowLine = checkFilterRow(i);
				if (isShowLine) {
					tableRows[i].removeAttribute('hidden');
				} else {
					tableRows[i].setAttribute("hidden","hidden");
				}
			}
		}
    };
	
	function checkFilterRow(indexRow) {
		var result = true;
		var filterCountColumn = reworkTable.rows[0].cells.length;
		
			for (var i = beginFilterIndexColumn; i < filterCountColumn; i++) {
				var cell = reworkTable.rows[indexRow].cells[i].children[0];
				var indexFilterParent = reworkTable.rows[indexFilterRow].cells[i];
				var selectedFilters = [];
				for(var filter of indexFilterParent.children[0].children) {
					if(filter.getAttribute("isSelected") == 'true')  selectedFilters.push(filter.innerHTML); 
				}
				if(selectedFilters.length === 0)  {
					continue;
				}	
				if(selectedFilters.includes(cell.innerHTML)) {
					result = true;
				} else {
					return false;	
				}
			}
			return result;
		
	};

	function getListElementsByPartId(id) {
		var regex = new RegExp('^'+id+'*');
			var all_tags = document.getElementsByTagName("*");
		var res = [];
		for (var i = all_tags.length-1; i >= 0; -- i){
			  if (regex.test(all_tags[i].id)) {
				  res.push(all_tags[i]);
			  }
		  }
		return res;
	}

	function getListChildrenElementsByParentId(parentId, childrenId) {
		var regex = new RegExp('^'+childrenId+'*');
			var all_tags = document.getElementById(parentId).children;
		var res = [];
		for (var i = all_tags.length-1; i >= 0; -- i) {
			  if (regex.test(all_tags[i].id)) {
				  res.push(all_tags[i]);
			  }
		  }
		return res;
	}