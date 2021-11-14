// MAIN UTIL document function
function showErrorMessage(elementForMessage, message) {
	var parent = $(elementForMessage).parent();
	var errElement = $(parent).children('.invalid-feedback');
	$.each($(errElement), function( index, value ) {
 		 if($(value).text() == message) {
			$(value).remove();
		}
	});
	$(parent).append('<div class="invalid-feedback">'+message+'</div>');
	if(!$(elementForMessage).hasClass('is-invalid')) {
		$(elementForMessage).addClass("is-invalid");
	}
}

function hideErrorMessage(elementForMessage,message) {
	var parent = $(elementForMessage).parent();
	var errElement = $(parent).children('.invalid-feedback');
	$.each($(errElement), function( index, value ) {
 		 if($(value).text() == message) {
			$(value).remove();
		}
	});
	if($(parent).children('.invalid-feedback').length == 0) {
		if($(elementForMessage).hasClass('is-invalid')) {
			$(elementForMessage).removeClass("is-invalid");
		}
	}
}

function hideError(elementForMessage) {
	var parent = $(elementForMessage).parent();
	var errElement = $(parent).children('.invalid-feedback');
	$(errElement).remove();
	if($(elementForMessage).hasClass('is-invalid')) {
		$(elementForMessage).removeClass("is-invalid");
	}
}

function alertUpdate(boldMessage, plainMessage) {
		var $alertUpdate=$("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>"+ boldMessage +"</strong> "+ plainMessage +
	    				     "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
	      		           	   "<span aria-hidden=\"true\">&times;</span>"+
	    	                 "</button>"+
  	                    	"</div>");
return $alertUpdate;
}
function alertErr(errBoldMessage, errPlainMessage) {
		var $alertErr=$("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>Error! "+ errBoldMessage +"</strong> "+ errPlainMessage +
	    				     "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
	      		           	   "<span aria-hidden=\"true\">&times;</span>"+
	    	                 "</button>"+
  	                    	"</div>");
return $alertErr;
}