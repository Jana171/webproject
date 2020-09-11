load();

function load() {
		
	console.log("USA");
	$.ajax({
		url:"../ProjectWeb/rest/users/all",
		method: "GET",
		datatype : "application/json"
	}).success(function(data) {
		let dynamicHTML = "<<thead><tr><th scope=\"col\">Username</th><th scope=\"col\">First name</th><th scope=\"col\">Last name</th><th scope=\"col\">Gender</th></tr></thead><tbody>";
	    let i;
		for(i = 0 ; i < data.length; i++) {
			dynamicHTML+="<tr>";
			dynamicHTML+="<td scope=\"row\">";
			dynamicHTML+=data[i].username;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].name;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].lastname;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].gender;
			dynamicHTML+="</td>";
			dynamicHTML+="</tr>";
		}
		
		dynamicHTML+="</tbody>";
		
		$("#tab1").append(dynamicHTML);
	});
		
}