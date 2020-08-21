$("#registerButton").click(function(){
	event.preventDefault();

	var male = false;
	
	if ($('#checkId').is(":checked"))
	{
	  male = true;
	}
	
	
	return;
	$.ajax({
		url:"../ProjectWeb/rest/users/register",
		contentType: 'application/json',
		data: toUserJSON($("#inputUsernameId").val(),$("#inputPasswordId").val(),$("#inputNameId").val(),$("#inputLastnameId").val(),
				$("#inputEmailId").val(),male),
		method: "POST",
		dataType : "text",
		
	}).success(function(data) {
			
		console.log(data);
	});
});

function userToJSON(username,password,name,lastname,email,male){
	return JSON.stringify({
		"username":username,
		"password":password,
		"name": name,
		"lastname": lastname,
		"email" : email,
		"male" : male
	});
}