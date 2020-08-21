load();


//onload event za loadovanje Home stranice
function load() {
	
	alert("BBB");
		
	$.ajax({
		url:"../ProjectWeb/rest/users/all",
		dataType : "json",	
		method: "GET"
	}).done(function(data) {
			
		console.log(data);
	});
		
}




