



function editRedirect(id) {
	url = "UpdateAmenity.html?id=" + id;
	window.location.replace(url);
}


function deleteAmenity(id) {
	
	$.ajax({
		type: "DELETE",
		url: "rest/amenities/" + id,
		success: function() {
			alert("Amenity deleted!");
			window.location.reload();
		},
		error: function() {
			alert("Something went wrong while deleting amenity!");
			window.location.reload();
		}
	})
}


function addAmenity() {
	var amenity = {};
	amenity.deleted = false;
	amenity.title = $("#amenityName").val();
	
	$.ajax({
		type: "POST",
		url: "rest/amenities",
		data: JSON.stringify(amenity),
		contentType: "application/json",
		success: function() {
			alert("New amenity added!");
			window.location.reload();
		},
		error: function() {
			alert("Something went wrong while adding new amenity!");
			window.location.reload();
		}
	})
};