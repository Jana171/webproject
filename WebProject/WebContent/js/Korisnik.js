load();


//onload event za loadovanje Home stranice
function load() {
	
	alert("BBB");
		
	$.ajax({
		url:"../WebProject/rest/users/test",
//		dataType : "json",	
		method: "GET"
	}).done(function(data) {
			
		alert(data);
	});
		
}




