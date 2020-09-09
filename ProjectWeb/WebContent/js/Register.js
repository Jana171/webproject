$("#button1").click(function(){
	console.log("SSS");
    	var gender = "FEMALE";
    	
    	if ($('#checkId').is(":checked"))
    	{
    	  gender = "MALE";
    	}
    	
    	//user = toUserJSON($("#inputUsernameId").val(),$("#inputPasswordId").val(),$("#inputNameId").val(),$("#inputLastnameId").val(),
			//	$("#inputEmailId").val(),gender);
    	user  = {
    		"username":"aa",
    		"password":"bb",
    		"name": "cc",
//    		"lastname": "dd",
    		"email" : "ss"
//    		"gender" : gender
    	};
    	
    	console.log(user);
    	$.ajax({
    		method: "POST",
    		url:"../ProjectWeb/rest/users/register",
    		headers: { 
    	        'Accept': 'application/json',
    	        'Content-Type': 'application/json' 
    	    },
    		data: user,
    		datatype : "application/json"
    	}).success(function(data) {
    		console.log("BBB");
    		console.log(data);
    	}).error(function(data) {
    		console.log(data);
    	});
});


function toUserJSON(username,password,name,lastname,email,gender){
	return JSON.stringify({
		"username":username,
		"password":password,
		"name": name,
		"lastname": lastname,
		"email" : email,
		"gender" : gender
	});
}