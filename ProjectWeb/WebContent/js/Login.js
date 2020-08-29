$("#loginButton").click(function(){
	console.log("SSS");
    	
    	user = toLoginJSON($("#inputUsernameId").val(),$("#inputPasswordId").val());
    	
    	console.log(user);
    	
    	$.ajax({
    		type: "POST",
    		url:"../ProjectWeb/rest/users/login",
    		contentType: 'application/json',
    		data: user,
    		dataType : "application/json"
    	}).success(function(data) {
    		console.log(data);
    		window.location.replace("http://localhost:8002/ProjectWeb/Profile.html");
    	});
});


function toLoginJSON(username,password){
	return JSON.stringify({
		"username":username,
		"password":password
	});
}