



function editRedirect(id) {
	url = "UpdateAmenity.html?id=" + id;
	window.location.replace(url);
}


function deleteAmenity(id) {
	
	$.ajax({
		type: "DELETE",
		url: "rest/users/amenities/" + id,
		success: function() {
			alert("Amenity deleted!");
		},
		error: function() {
			alert("Something went wrong while deleting amenity!");
		}
	})
}


function addAmenity() {
	var amenity = {};
	amenity.deleted = false;
	amenity.title = $("#amenityName").val();
	
	$.ajax({
		type: "POST",
		url: "rest/users/amenities",
		data: JSON.stringify(amenity),
		contentType: "application/json",
		success: function() {
			alert("New amenity added!");
		},
		error: function() {
			alert("Something went wrong while adding new amenity!");
		}
	})
};