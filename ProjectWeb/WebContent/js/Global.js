setUpMenu();



function getParams(url) {
	var params = {};
	var parser = document.createElement('a');
	parser.href = url;
	var query = parser.search.substring(1);
	var vars = query.split('&');
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split('=');
		params[pair[0]] = decodeURIComponent(pair[1]);
	}
	return params;
};


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


