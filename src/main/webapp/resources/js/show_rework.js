// MAIN loaded document function
$(function() {
	
	$('#wikilinkEditButton').click(function() {
			$('#wikilinkEditInput').removeClass("d-none");
				$('#wikilinkEditInput').attr("required",""); 
			$('#wikilinkEditA').addClass("d-none");
	});
	
	$('#wikilinkEditButton').click(function() {
		$('#wikilinkEditInput').removeClass("form-control-plaintext");
		$('#wikilinkEditInput').prop("readonly",false);
		$('#wikilinkEditInput').addClass("form-control");
	});

	var reworkNumber = ($(".row.mt-2.align-items-center.border-bottom").eq(1).children()[1]).innerText;
	
	var $alertUpdate = alertUpdate(reworkNumber, "This rework was updated!");
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
		var wikilink = $("#wikilinkEditInput").val();
		var descrRework = $("#descrReworkTextarea").val();
		if($('#wikilinkEditInput').attr("required") == "required") {
			if(wikilink.trim() == '') {showErrorMessage($('#wikilinkEditInput'), "Заполните поле \'WIKILINK\'!"); isEmptyFields = "true";}
		}
		if(descrRework.trim() == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните поле \'Описание\'!"); isEmptyFields = "true";}
		
		if(isEmptyFields == "true") return;
		$('#formUpdate').submit();
	});
	$('input, textarea').click(function() {
		hideError($(this));
	});
	$('#deleteReworkButton').click(function() {
		console.log('ENTER!!!');
		$('#myModal').modal('show');
	});	
	
	$('#formBackSubmitButton').click(function() {
		if ($('#wmsBack').val() == '') {
			//$('#startLink')[0].click();
			window.location = document.getElementById('startLink').href;
		} else { $('#formBack').submit(); }
	});	

});
