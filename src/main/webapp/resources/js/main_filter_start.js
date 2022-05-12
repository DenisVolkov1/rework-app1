
	$(function() {
  		$('[data-toggle="tooltip"]').tooltip();
		$('#loading').addClass('d-none');
		$('.container-fluid').removeClass('d-none');
		///delete rework show modal////////////////////////////////////////////////////////////////////////////////
		var deleteRework_wms=$("#deleteRework_wms").val();
		var deleteRework_reworknumber=$("#deleteRework_reworknumber").val();	
			
		if(deleteRework_wms != '' && deleteRework_reworknumber !='') {
			var $alertDelete = alertDelete(deleteRework_reworknumber +" : "+deleteRework_wms, " доработка была удалена!");
			$("body").before($alertDelete);
		}
		///
		
	});
	
