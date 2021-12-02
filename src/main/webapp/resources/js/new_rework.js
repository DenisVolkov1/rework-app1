// MAIN loaded document function
var selectWms;

$(function() {
	
	$('#inputNewWmsButton_selectExists').click(function() {
		
		if($(this).text() == 'Ввести новую WMS') {
			$('#wmsInputDIV').removeClass("d-none");
				$('#wmsInput').attr("isActualElement","true"); 
			$('#wmsSelectDIV').addClass("d-none");
			$(this).text('Выбрать WMS');
		} else if($(this).text() == 'Выбрать WMS') {
			$('#wmsSelectDIV').removeClass("d-none");
			$('#wmsInputDIV').addClass("d-none");
				$('#wmsInput').attr("isActualElement","false"); 
			$(this).text('Ввести новую WMS');
		}

	});
	
	$('#inputNewProjectButton_selectExists').click(function() {
		if($(this).text() == 'Ввести новый проект') {
			$('#projectInputDIV').removeClass("d-none");
				$('#projectInput').attr("isActualElement","true"); 
			$('#projectSelectDIV').addClass("d-none");
			$(this).text('Выбрать проект');
			
		} else if($(this).text() == 'Выбрать проект') {
			$('#projectSelectDIV').removeClass("d-none");
			$('#projectInputDIV').addClass("d-none");
				$('#projectInput').attr("isActualElement","false"); 
			$(this).text('Ввести новый проект');
		}

	});
	
	$('#formNewSubmitButton').click(function() {
		var isAlreadyExistsRework;
		var isEmptyFields;
		var wms;	
		var reworkNumber = $("#reworkNumberInput").val();
		var wikilink = $("#wikilinkInput").val();
		var resource = $("#resourceInput").val();
		var descrRework = $("#descrReworkTextarea").val();
		var project = $("#projectInput").val();
		var addWhoInput = $("#addWhoInput").val();
		//
		var serverName = $("#server").val();
		var port = $("#port").val();
		
		if($('#wmsInput').attr("isActualElement") == "true") {
			wms = $('#wmsInput').val();
			if(wms.trim() == '') {showErrorMessage($('#wmsInput'), "Заполните поле \'WMS!\'"); isEmptyFields = "true"}
		}
		else {
			wms = $('#wmsSelect').find(":selected").text();
		}
		if(reworkNumber.trim() == '') {showErrorMessage($('#reworkNumberInput'), "Заполните поле \'Номер доработки\'!"); isEmptyFields = "true";}
		if(wikilink.trim() == '') {showErrorMessage($('#wikilinkInput'), "Заполните поле \'WIKILINK\'!"); isEmptyFields = "true"}
		if(resource.trim() == '') {showErrorMessage($('#resourceInput'), "Заполните поле \'Ресурс\'!"); isEmptyFields = "true"}
		if(descrRework.trim() == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните \'Описание\'!"); isEmptyFields = "true"}
		if($('#projectInput').attr("isActualElement") == "true") {if(project.trim() == '') {showErrorMessage($('#projectInput'), "Заполните \'Описание\'!"); isEmptyFields = "true"}}
		if(addWhoInput.trim() == '') {showErrorMessage($('#addWhoInput'), "Заполните поле \'Кто добавил\'!"); isEmptyFields = "true"}
		if(isEmptyFields == "true") return;
		
		//var url = "http://"+ serverName +":"+ port +"/isAlreadyExistsRework?wms="+ wms +"&reworkNumber="+ reworkNumber +"";
		var url = "http://"+ serverName +":"+ port +"/isAlreadyExistsRework";
		$.ajax({
			    type: 'get',
			    url: url,
			    data: {
        			wms: wms,
					reworkNumber: reworkNumber
    			},
			    success: function(data) {
			
			      isAlreadyExistsRework = data;	
				  console.log('-isAlreadyExistsRework = '+data);
		
					if(isAlreadyExistsRework == "true"){
						if($('#wmsInput').attr("isActualElement") == "false") {
							showErrorMessage($('#wmsSelect'), "Такой номер доработки уже существует! wms: "+wms+" номер доработки: "+reworkNumber);
						} else {
							showErrorMessage($('#wmsInput'), "Такой номер доработки уже существует! wms: "+wms+" номер доработки: "+reworkNumber);
						}
						showErrorMessage($('#reworkNumberInput'), "Такой номер доработки уже существует! wms: "+wms+" номер доработки: "+reworkNumber); 	
		
						return;
					} 
					
					// remove element for thymeleaf model dublicate
					if($('#wmsInput').attr("isActualElement") == "false") {
						$('#wmsInputDIV').remove();
					} else {
						$('#wmsSelectDIV').remove();
					}
					if($('#projectInput').attr("isActualElement") == "false") {
						$('#projectInputDIV').remove();
					} else {
						$('#projectSelectDIV').remove();
					}
					$('#formNew').submit();
			    }
			});
	});
	
	$('#formBackSubmitButton').click(function() {
		if ($('#wmsBack').val() == '') {
			//$('#startLink')[0].click();
			window.location = document.getElementById('startLink').href;
		} else { $('#formBack').submit(); }
	});	
	
	
	$('input, textarea, select').click(function() {
		hideError($(this));
	});
	

});