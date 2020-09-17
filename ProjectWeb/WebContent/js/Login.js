$("#loginButton").click(function(){
	
    	user = toLoginJSON($("#inputUsernameId").val(),$("#inputPasswordId").val());
    	

    	console.log(user);
    	
    	$.ajax({
    		method: "POST",
    		url:"../ProjectWeb/rest/users/login",
    		contentType: 'application/json',
    		data: user,
    		datatype : "application/json"
    	}).success(function(data) {
    		
    		alert("Successfull login!");
    		window.location.replace("Home.html");
    	}).error(function() {
    		alert("Bad Credentials!");
    		window.location.reload();
    	})
});


function toLoginJSON(username,password){
	return JSON.stringify({
		"username":username,
		"password":password
	});
}