load();


function load() {
		
	
	$.ajax({
		url:"../ProjectWeb/rest/apartments/all",
		method: "GET",
		datatype : "application/json"
	}).success(function(data) {
		console.log(data);
		let dynamicHTML = "<<thead><tr><th scope=\"col\">Id</th><th scope=\"col\">Name</th><th scope=\"col\">Type</th>" +
				"<th scope=\"col\">Number of Rooms</th><th scope=\"col\">Number of guests</th><th scope=\"col\">Price for night</th>" + 
				"<th scope=\"col\">Time for check in</th><th scope=\"col\">Time for check out</th><th scope=\"col\">Location</th><th scope=\"col\">Amenities</th>"+  
				"</thead><tbody>";
				
				
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
			var amTemp = "";
			for(k = 0 ; k < data[i].amenities.length; k++){
				amTemp += data[i].amenities[k].title + ", ";
			}
			dynamicHTML+="<td>";
			dynamicHTML+= amTemp;
			dynamicHTML+="</td>";
			dynamicHTML+="<td><button class=\"btn btn-primary\" onclick=\"apartmentView(" + data[i].id + ")\" >View/Update</td>";
			dynamicHTML+="<td><button class=\"btn btn-danger\" onclick=\"deleteApartment(" + data[i].id + ")\">Delete</td>";
			dynamicHTML+="</tr>";
			
		}
		
		dynamicHTML+="</tbody>";
		$("#tab2").empty();
		$("#tab2").append(dynamicHTML);
		
		handleButtons();
	});
		
}

function handleButtons() {
	$.ajax({
		type: "GET",
		url: "rest/users/role",
		dataType: "text",
		success: function(role) {
			if (role == "") {
				$( ":button" ).hide();
			} else if (role == "ADMIN") {
				$( ":button" ).show();
			} else if (role == "HOST") {
				$( ":button" ).show();
			} else {
				$( ":button" ).hide();
			}
		},
		error: function() {
			alert("Something went wrong while getting logged user");
		}
	})
}


function apartmentView(id) {
	window.location.replace("UpdateApartment.html?id=" + id);
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