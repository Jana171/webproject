load();

function load() {
		
	console.log("USA");
	$.ajax({
		url:"../ProjectWeb/rest/users/logged",
		method: "GET",
		datatype : "application/json"
	}).success(function(data) {
		$("#passwordId").val(data.password);
		$("#nameId").val(data.name);
		$("#usernameId").val(data.username);
		$("#lastnameId").val(data.lastname);
	});
		
}

function update() {
	
	let newUser = JSON.stringify({
		"password":$("#passwordId").val(),
		"name": $("#nameId").val(),
		"username": $("#usernameId").val(),
		"lastname": $("#lastnameId").val()
	});
			
	console.log(newUser);
	
	$.ajax({
		url:"../ProjectWeb/rest/users/update",
		method: "POST",
		datatype : "application/json",
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		data:newUser
	}).success(function(data) {
		$("#usernameId").val(data.username);
		$("#nameId").val(data.name);
		$("#lastnameId").val(data.lastname);
	}).error(function(data) {
		console.log(data);
	});
		
}

