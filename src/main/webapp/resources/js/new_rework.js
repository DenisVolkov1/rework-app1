// MAIN loaded document function
var selectWms;

var switchingBetweenInputOrSelect = function(_selectExistsButton_obj, inputText, selectText, name ) {
	
		if($(_selectExistsButton_obj).text() == inputText) {
			$('#'+name+'InputDIV').removeClass("d-none");
			$('#'+name+'Input').attr("isActualElement","true"); 
			$('#'+name+'SelectDIV').addClass("d-none");
			$('#'+name+'Input').val(""); 
			$(_selectExistsButton_obj).text(selectText);
		} else if($(_selectExistsButton_obj).text() == selectText) {
			$('#'+name+'SelectDIV').removeClass("d-none");
			$('#'+name+'InputDIV').addClass("d-none");
			$('#'+name+'Input').attr("isActualElement","false");
			$(_selectExistsButton_obj).text(inputText);
		}
}	

$(function() {
	
	$('#inputNewWmsButton_selectExists').click(function() {
		
		switchingBetweenInputOrSelect ($(this), 'Ввести новую WMS' , 'Выбрать WMS' , 'wms');
		
	});
	
	$('#inputNewProjectButton_selectExists').click(function() {
		
		switchingBetweenInputOrSelect ($(this), 'Ввести новый проект' , 'Выбрать проект' , 'project');

	});
	$('#inputNewAddWhoButton_selectExists').click(function() {
		
		switchingBetweenInputOrSelect ($(this), 'Ввести нового пользователя' , 'Выбрать пользователя' , 'addWho');

	});
	
	$('#formNewSubmitButton').click(function() {
		var isAlreadyExistsRework;
		var isEmptyFields;
		var wms;	
		var reworkNumber = $("#reworkNumberInput").val();
		var wikilink = $("#wikilinkInput").val();
		var resource = $("#resourceInput").val();
		var descrRework = $("#descrReworkTextarea").val();
		var project;
		var addWho = $("#addWhoInput").val();
		//
		var serverName = $("#server").val();
		var port = $("#port").val();
		
		if($('#wmsInput').attr("isActualElement") == "true") {
			wms = $('#wmsInput').val();
			if(wms.trim() == '') {showErrorMessage($('#wmsInput'), "Заполните поле \'WMS\'!"); isEmptyFields = "true"}
		}
		else { wms = $('#wmsSelect').find(":selected").text();}
		
		if($('#projectInput').attr("isActualElement") == "true") {
			project = $("#projectInput").val();
			if(project.trim() == '') {showErrorMessage($('#projectInput'), "Заполните поле \'Проект\'!"); isEmptyFields = "true"}
		}
		//else { project = $('#projectSelect').find(":selected").text();}
		
		if($('#addWhoInput').attr("isActualElement") == "true") {
			addWho = $("#addWhoInput").val();
			if(addWho.trim() == '') {showErrorMessage($('#addWhoInput'), "Заполните поле \'Кто добавил\'!"); isEmptyFields = "true"}
		}
		//else { addWho = $('#addWhoSelect').find(":selected").text();}
		
		if(reworkNumber.trim() == '') {showErrorMessage($('#reworkNumberInput'), "Заполните поле \'Номер доработки\'!"); isEmptyFields = "true";}
		if(wikilink.trim() == '') {showErrorMessage($('#wikilinkInput'), "Заполните поле \'WIKILINK\'!"); isEmptyFields = "true"}
		if(resource.trim() == '') {showErrorMessage($('#resourceInput'), "Заполните поле \'Ресурс\'!"); isEmptyFields = "true"}
		if(descrRework.trim() == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните \'Описание\'!"); isEmptyFields = "true"}
		if(isEmptyFields == "true") return;
		
		//var url = "http://"+ serverName +":"+ port +"/isAlreadyExistsRework?wms="+ wms +"&reworkNumber="+ reworkNumber +"";
		var url = "http://"+ serverName +":"+ port +"/rework-app1/isAlreadyExistsRework";
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
					if($('#addWhoInput').attr("isActualElement") == "false") {
						$('#addWhoInputDIV').remove();
					} else {
						$('#addWhoSelectDIV').remove();
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