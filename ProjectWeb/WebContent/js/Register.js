$("#button1").click(function(){
	console.log("SSS");
    	var male = false;
    	
    	if ($('#checkId').is(":checked"))
    	{
    	  male = true;
    	}
    	
    	user = toUserJSON($("#inputUsernameId").val(),$("#inputPasswordId").val(),$("#inputNameId").val(),$("#inputLastnameId").val(),
				$("#inputEmailId").val(),male);
    	
    	console.log(user);
    	
    	$.ajax({
    		type: "POST",
    		url:"../ProjectWeb/rest/users/register",
    		contentType: 'application/json',
    		data: user,
    		dataType : "text"
    	}).success(function(data) {
    		console.log("BBB");
    		console.log(data);
    	});
});


function toUserJSON(username,password,name,lastname,email,male){
	return JSON.stringify({
		"username":username,
		"password":password,
		"name": name,
		"lastname": lastname,
		"email" : email,
		"male" : male
	});
}