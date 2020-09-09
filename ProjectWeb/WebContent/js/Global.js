setUpMenu();


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



function logout() {


	$.ajax({
		url:"../ProjectWeb/rest/users/logout",
		method: "POST"
	}).success(function(data) {	
		console.log(data);
		setUpMenu();
	});
		
}

function setUpMenu() {
	
	$.ajax({
		url:"../ProjectWeb/rest/users/role",
		method: "GET",
		dataType : "text"
	}).success(function(data) {
		console.log(data);
		if(data == "") {
			$("#profileId").hide();
			$("#logoutId").hide();
			$("#registerId").show();
			$("#loginId").show();
		} else if(data === "ADMIN") {
			$("#profileId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}else if(data === "HOST") {
			$("#profileId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}else {
			$("#profileId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}
		
	});
		
}



