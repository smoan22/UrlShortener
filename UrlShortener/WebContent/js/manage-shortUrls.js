function initData(){	  
	
	$.fn.dataTable.ext.errMode = 'none';
	
	table = $('#shortUrls').on( 'error.dt', function ( e, settings, techNote, message ) {
	console.log( 'An error has been reported by DataTables: ', message );
	}).DataTable({
	    data: shortUrls,	   
	    "columnDefs": [ {
	        	"targets": 0,
	        	"data": "id"	       
	      },{
		        "targets": 1,
		        "data": "orignalUrl"	       
		  },{
	        	"targets": 2,
	        	"data": "shortUrl",
	        	"render": function ( data, type, full, meta ) {
	        		return '<a href="/UrlShortening/cc/urlClicks/get/'+full.id+'" target="_blank">'+data+'</a>' 	        				            
		        }
	      },{
		        "targets": 3,
		        "data": "expiryDate"	       
		  },{
	        	"targets": 4,
	        	"data": "hitCount"	       
	      }]
	});	
	
}


function shortenUrlClick(){
	$('#submitBtn').click(function(e) {	    
	    var url = $('#url').val();        

	    $('#errorNoUrl').css("display", "none");
	    $('#errorInvalidUrl').css("display", "none");
	    $("#error").css("display","none");
	    $("#successUrl").css("display","none");
	    if (url.length < 1) {
	      $('#errorNoUrl').css("display", "block");
	    }else if (!isBigUrl(url)) {
	    	$('#error').html("Url is already short");
	    	$('#error').css("display", "block");
	    }else{	    	
	    
		    if(isUrlValid(url)){		
				$.ajax({
				  	url: 'cc/addUrl',
				    data: 'url='+$("#url").val(),
				    type: 'POST',
				    success: function(data) {				    		    			
		    			if(data.status == "200"){
		    				$("#url").val("");
		    				$("#successUrl").html(data.data);
		    				$("#successUrl").css("display","block");		    			
		    			}else{
		    				$("#error").html(data.data);
		    				$("#error").css("display","block");	    			
		    			}
				    }
				     ,
				    error: function(failureMessage) {
	    				//Take to 404 page
				    }
				});
		    }else{
		    	$('#errorInvalidUrl').css("display", "block");
		    }		    	
		}		   
	  });
}

function isUrlValid(url) {
    return /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(url);
}

function initChart(){
	var dataSet = [];
	var labels = [];
	for (var j = 0; j < urlClicks.length; j++) {
		var clickDate = moment(urlClicks[j].clickDate).valueOf();
		var labelDate = moment(urlClicks[j].clickDate).format("DD MMM, YY");
		var obj = {
				t : clickDate,
				y : urlClicks[j].totalClicks 
		}
		dataSet.push(obj);
		labels.push(labelDate);
	}

			var ctx = document.getElementById('myChart').getContext('2d');
			
			var cfg = {
					type: 'bar',
					data: {
						labels: labels,
						datasets: [{							
							label: 'Website hits for URL',
							data: dataSet,
							type: 'line',
							pointRadius: 2,
							fill: false,
							lineTension: 0,
							borderWidth: 2
						}]
					},
					options: {
						legend: {
				            labels: {
				                // This more specific font property overrides the global property
				                fontColor: 'black'				                
				            }
				        },
						scales: {
							xAxes: [{
								time: {
				                    unit: 'month'
				                },
								distribution: 'series',
								ticks: {
									source: 'labels'
								}
							}],
							yAxes: [{
								scaleLabel: {
									display: true,
									labelString: 'WebSite hits'
								}
							}]
						}
					}
				};

				chart = new Chart(ctx, cfg);

}


function isBigUrl(url){
	if(url.length >= 65){
//		var slashCount = url.split("/").length - 1;
//		if(slashCount >= 6){
			return true;
//		}else{
//			return false;
//		}
	}else{
		return false;
	}
	
}


function initWebsocket(){
	websocket = new WebSocket("ws://localhost:8080/UrlShortening/chartSocket");
	websocket.onopen = function(message){
		processOpen(message);
	}
	
	websocket.onmessage = function(message){
		processMessage(message.data);
	}
	
	websocket.onclose = function(message){
		processClose(message);
	}
	
	websocket.onerror = function(message){
		processError(message);
	}
	
//	websocket.send(urlClicks[0].shortenedUrlId);
}

function processOpen(message){
//	alert("Server Connected");
//	websocket.send("Connected sir from lcient");
}

function processMessage(message){
	urlClicks = JSON.parse(message);
	initChart();
//	alert("Server message "+message);	
}

function processClose(message){
//	alert("Server disConnected");
}

function processError(message){
//	alert("Server error");
}














