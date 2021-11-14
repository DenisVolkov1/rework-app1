
$(function() {
	
	$("#button1").click(function(){
		var tooltipUrl = 'http://localhost:8080/generate'
		$.get(tooltipUrl, function( data ) {

			console.log(data);
		});
	});
	$("#button").click(function(){
	   $.ajax({
	        type: "POST",                            
	        url: 'http://localhost:8080/mainfilter/tooltip/cell?wms=INFOR_WMS_10&reworkNumber=FBR0080&project=БАРС',
			dataType: "json",
	        headers: {
	                  "Accept": "application/json"
	                },
	   }).done(function( data ) {
      		console.log( "Sample of data:"+ data);
  	   });
		
	});
	

	
});