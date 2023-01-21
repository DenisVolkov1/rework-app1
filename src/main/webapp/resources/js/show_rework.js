// MAIN loaded document function
$(function() {
	
	$('#taskEditButton').click(function() {
			$('#taskEditInput').removeClass("d-none");
				$('#taskEditDiv').attr("required","");
			$('#taskEditDiv').addClass("d-none");
			$('#taskEditButton').addClass("d-none");
	});
	
	$('#taskMonetkaEditButton').click(function() {
			$('#taskMonetkaEditInput').removeClass("d-none");
				$('#taskMonetkaEditDiv').attr("required","");
			$('#taskMonetkaEditDiv').addClass("d-none");
			$('#taskMonetkaEditButton').addClass("d-none");
	});
	

	var reworkNumber = $("#reworkNumberDiv").text();
	
	var $alertUpdate = alertUpdate(reworkNumber, "Данная доработка была обновлена!");
	var $alertErr = alertErr(reworkNumber, "rework was not updated!");
	
	if($("#formUpdate").attr("isUpdate") == 'true') {
		$("body").before($alertUpdate);
		$("#formUpdate").attr("isUpdate","false")
		var onlyUrl = window.location.href.replace(window.location.search,'');
		history.replaceState(null, '', onlyUrl);
	} else if ($("#formUpdate").attr("isUpdate") != 'true' && $("#formUpdate").attr("isUpdate") != 'false') {
		$("body").before($alertErr);
	}
	
	$('#deleteReworkConfirmButton').click(function() {
		$('#deleteReworkLink')[0].click();
	});

	$('#updateButton').click(function() {
		var isEmptyFields;
		var descrRework = $("#descrReworkTextarea").val();
		
		if(descrRework.trim() == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните поле \'Название доработки/исправления\'!"); isEmptyFields = "true";}
		
		if(isEmptyFields == "true") return;
		$('#formUpdate').submit();
	});
	$('input, textarea').click(function() {
		hideError($(this));
	});
	$('#deleteReworkButton').click(function() {
		$('#myModal').modal('show');
	});	
	
	$('#formBackSubmitButton').click(function() {

			$('#formBack').submit(); 
	});	

});
