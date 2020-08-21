load();


//onload event za loadovanje Home stranice
function load() {
	

		
	$.ajax({
		url:"../ProjectWeb/rest/users/all",
		method: "GET",
		dataType : "json"
	}).success(function(data) {
			
		console.log(data);
	});
		
}




