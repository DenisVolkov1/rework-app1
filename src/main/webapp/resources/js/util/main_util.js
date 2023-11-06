// MAIN UTIL document function
const DELAY = 3600;

////////////////////////////
var countAlert =0;
var topShift =0;
//
function loadingPage() {
	$('#loading').addClass('d-none');
	$('.container-fluid').removeClass('d-none');
}
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
function copyToClipboard(element) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val($(element).text()).select();
    document.execCommand("copy");
    $temp.remove();
}

function hideError(elementForMessage) {
	var parent = $(elementForMessage).parent();
	var errElement = $(parent).children('.invalid-feedback');
	$(errElement).remove();
	if($(elementForMessage).hasClass('is-invalid')) {
		$(elementForMessage).removeClass("is-invalid");
	}
}

function alertDelete(boldMessage, plainMessage) {
		var $alertDelete=$("<div class=\"overlay alert alert-dark alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>"+ boldMessage +"</strong> "+ plainMessage +
	    				     "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
	      		           	   "<span aria-hidden=\"true\">&times;</span>"+
	    	                 "</button>"+
  	                    	"</div>");
return $alertDelete;
}
function alertInsert(boldMessage, plainMessage) {
		var $alertInsert=$("<div class=\"overlay alert alert-success alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>"+ boldMessage +"</strong> "+ plainMessage +
	    				     "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
	      		           	   "<span aria-hidden=\"true\">&times;</span>"+
	    	                 "</button>"+
  	                    	"</div>");
return $alertInsert;
}
function alertUpdate(boldMessage, plainMessage) {
		var $alertUpdate=$("<div class=\"overlay alert alert-warning alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>"+ boldMessage +"</strong> "+ plainMessage +
  	                    	"</div>");
		if(topShift>0) {
			$alertUpdate.css("top",topShift*45);
		}
		
		if(topShift==0) {
			setTimeout(() => {
				$('.alert').alert('close');
				topShift=0;
			}, DELAY);
		}
		++topShift;
return $alertUpdate;
}
function alertErr(errBoldMessage, errPlainMessage) {
		var $alertErr=$("<div class=\"overlay alert alert-danger alert-dismissible fade show\" role=\"alert\">"+
	    				    "<strong>Ошибка! "+ errBoldMessage +"</strong> "+ errPlainMessage +
	    				     "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
	      		           	   "<span aria-hidden=\"true\">&times;</span>"+
	    	                 "</button>"+
  	                    	"</div>");
		
return $alertErr;
}