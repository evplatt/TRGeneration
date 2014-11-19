function submit_source(){
	
	$('#png_output').html('<img src="img/spinner.gif" alt=""/>');
	
	$.ajax({
		type: 'POST',
		url: 'process_source.php',
		data: {src_code: $('#src_code').val(), debug: ($('#debug').is(":checked") ? 1 : 0)},
		dataType: 'json',
		
		success: function(data){
			d = new Date(); //use to prevent image caching
			$('#tr_output').html(data.tr);
			$('#png_output').html('<img src="output/'+data.png+'?'+d.getTime()+'" alt=""/>');
			$('#png_output img').css('max-height',$(window).height()-50+'px');
		},
	});
	
}
