// MAIN loaded document function
$(function() {
	var gradientForHeader = $('#gradientforheader').val();
	const gr = gradientForHeader.split(' ');
	$('#gradientHeader').css({
		"background-image":'linear-gradient(120deg, '+gr[0]+' 0%, '+gr[1]+' 100%)',
		"height":'20'
	});
	
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
		var description = ($("#descrReworkTextarea").val()).trim();
		var serverName = $("#server").val();
		var port = $("#port").val();
		
		if(description == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните поле \'Название доработки/исправления\'!"); isEmptyFields = "true";}
		//Exit
		if(isEmptyFields == "true") return;
		
		var reworkNumber = $("#reworkNumberDiv").text();
		var project = $('#project').val();
		var url = "http://"+ serverName +":"+ port +"/rework/"+project+"/isAlreadyExistsRework";
		$.ajax({
			    type: 'get',
			    url: url,
			    data: {
        			description: description,
					reworknumber: reworkNumber
    			},
			    success: function(data) {
			
			      isAlreadyExistsRework = data;	
				  console.log('-isAlreadyExistsRework = '+data);
					
					if(isAlreadyExistsRework == "true"){
						showErrorMessage($('#descrReworkTextarea'),  "Доработки с таким описанием уже существует! ("+description+")");
						return;
					} 
					
					// remove element for thymeleaf model dublicate				
					if($('#addWhoInput').attr("isActualElement") == "false") {
						$('#addWhoInputDIV').remove();
					} else {
						$('#addWhoSelectDIV').remove();
					}
					$('#formUpdate').submit();
			    }
			});
		
		//$('#formUpdate').submit();
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
