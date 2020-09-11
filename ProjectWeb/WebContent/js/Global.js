setUpMenu();






function logout() {


	$.ajax({
		url:"../ProjectWeb/rest/users/logout",
		method: "POST"
	}).success(function(data) {	
		console.log(data);
		window.location.href = "http://localhost:8080/ProjectWeb/Home.html";
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
			$("#userViewId").hide();
			$("#logoutId").hide();
			$("#registerId").show();
			$("#loginId").show();
		} else if(data === "ADMIN") {
			$("#profileId").show();
			$("#userViewId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}else if(data === "HOST") {
			$("#profileId").show();
			$("#userViewId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}else {
			$("#profileId").show();
			$("#userViewId").hide();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
		}
		
	});
		
}



