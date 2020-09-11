$("#button1").click(function(){
	console.log("SSS");
    	var gender = "FEMALE";
    	
    	if ($('#checkId').is(":checked"))
    	{
    	  gender = "MALE";
    	}
    	
    	user = toUserJSON($("#inputUsernameId").val(),$("#inputPasswordId").val(),$("#inputNameId").val(),$("#inputLastnameId").val(),gender);
    	/*var user  = {
    		username:"aa",
    		password:"bb",
    		name: "cc"		
    	};*/
//		"lastname": "dd",
//		"gender" : gender
    	

    	
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
    		alert("Successfull!");
    	}).error(function(data) {
    		alert("Error");
    	});
});


function toUserJSON(username,password,name,lastname,gender){
	return JSON.stringify({
		"username":username,
		"password":password,
		"name": name,
		"lastname": lastname,
		"gender" : gender
	});
}