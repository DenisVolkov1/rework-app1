// MAIN loaded document function

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
	
	$('#inputNewAddWhoButton_selectExists').click(function() {
		
		switchingBetweenInputOrSelect ($(this), 'Ввести нового пользователя' , 'Выбрать пользователя' , 'addWho');

	});
	
	$('#formNewSubmitButton').click(function() {	
		var isAlreadyExistsRework;
		var isEmptyFields;
			
		var task 		= $("#taskInput").val();
		var taskMonetka = $("#taskMonetkaInput").val();
		var description = $("#descrReworkTextarea").val();
		var addWho = $("#addWhoInput").val();
		//
		var serverName = $("#server").val();
		var port = $("#port").val();
		
		// Check empty "Кто добавил"
		if($('#addWhoInput').attr("isActualElement") == "true") {
			addWho = $("#addWhoInput").val();
			if(addWho.trim() == '') {showErrorMessage($('#addWhoInput'), "Заполните поле \'Кто добавил\'!"); isEmptyFields = "true"}
		}
		// Check empty "Название доработки/исправления"
		if(description.trim() == '') {showErrorMessage($('#descrReworkTextarea'), "Заполните \'Название доработки/исправления\'!"); isEmptyFields = "true"}
		
		if(isEmptyFields == "true") return;
		
		//var url = "http://"+ serverName +":"+ port +"/isAlreadyExistsRework?wms="+ wms +"&reworkNumber="+ reworkNumber +"";
		var url = "http://"+ serverName +":"+ port +"/rework-app1-monetka/isAlreadyExistsRework";
		$.ajax({
			    type: 'get',
			    url: url,
			    data: {
        			description: description
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
					$('#formNew').submit();
			    }
			});
	});
	
	$('#formBackSubmitButton').click(function() {
//		if ($('#wmsBack').val() == '') {
//			window.location = document.getElementById('startLink').href;
//		} else { 
			
			$('#formBack').submit(); 
	});	
	
	
	$('input, textarea, select').click(function() {
		hideError($(this));
	});
	
});