load();
/*
$.ajax({
	url:"../ProjectWeb/rest/users/role",
	method: "GET",
	dataType : "text"
}).success(function(data) {
	console.log(data);
	if(data == "") {
	
	} else if(data === "ADMIN") {
	}else if(data === "HOST") {
		$("#profileId").show();
	}else {
		$("#profileId").show();
	}
	
});
	*/

function load() {
		
	console.log("USA");
	$.ajax({
		url:"../ProjectWeb/rest/apartments/all",
		method: "GET",
		datatype : "application/json"
	}).success(function(data) {
		console.log(data);
		let dynamicHTML = "<<thead><tr><th scope=\"col\">Id</th><th scope=\"col\">Name</th><th scope=\"col\">Type</th>" +
				"<th scope=\"col\">Number of Rooms</th><th scope=\"col\">Number of guests</th><th scope=\"col\">Price for night</th>" + 
				"<th scope=\"col\">Time for check in</th><th scope=\"col\">Time for check out</th><th scope=\"col\">Location</th>"+ 
				"<th class=\"variant1\">Go to profile</th><th class=\"variant2\">Delete</th></tr></thead><tbody>";
				
				
	    let i;
		for(i = 0 ; i < data.length; i++) {
			dynamicHTML+="<tr>";
			dynamicHTML+="<td scope=\"row\">";
			dynamicHTML+=data[i].id;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].name;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].type;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].numberOfRooms;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].numberOfGuests;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].priceForNight;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].timeForCheckIn;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+=data[i].timeForCheckOut;
			dynamicHTML+="</td>";
			dynamicHTML+="<td>";
			dynamicHTML+= data[i].location.address.city + " " + data[i].location.address.street;
			dynamicHTML+="</td>";
			dynamicHTML+="<td><a href=\"UpdateApartment.html?id=" + data[i].id + "\" >Update</a></td>";
			dynamicHTML+="<td><a href=\"Apartment.html?id=" + data[i].id + "\" >Profile</a></td>";
			dynamicHTML+="<td><button onclick=\"deleteApartment(" + data[i].id + ")\">Delete<button></td>";
			
			
			dynamicHTML+="</tr>";
		}
		
		dynamicHTML+="</tbody>";
		$("#tab2").empty();
		$("#tab2").append(dynamicHTML);
	});
		
}

function redirectToAdd() {
	window.location.replace("AddApartment.html");
}

function deleteApartment(id) {
	$.ajax({
		url: "rest/apartments/" + id,
		type: "DELETE",
		success: function() {
			alert("Apartment is deleted!");
			window.location.reload();
		},
		error: function() {
			alert("Error during delete!");
			window.location.reload();
		}
		
	})
}