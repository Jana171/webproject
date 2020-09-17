setUpMenu();



function getParams(url) {
	var params = {};
	var parser = document.createElement('a');
	parser.href = url;
	var query = parser.search.substring(1);
	var vars = query.split('&');
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split('=');
		params[pair[0]] = decodeURIComponent(pair[1]);
	}
	return params;
};


function logout() {


	$.ajax({
		url:"../ProjectWeb/rest/users/logout",
		method: "POST"
	}).success(function(data) {	
		console.log(data);
		window.location.replace("Login.html");
	});
		
}

function setUpMenu() {
	
	$.ajax({
		url:"../ProjectWeb/rest/users/role",
		method: "GET",
		dataType : "text"
	}).success(function(data) {
		console.log(data);
		if(data == "") {
			$("#profileId").hide();
			$("#userViewId").hide();
			$("#logoutId").hide();
			$("#registerId").show();
			$("#loginId").show();
			$("#amenitiesId").hide();
			$("#reservationsId").hide();
			
			// buttons
			$("#makeReservation").hide();
		} else if(data === "ADMIN") {
			$("#profileId").show();
			$("#userViewId").show();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
			$("#amenitiesId").show();
			$("#reservationsId").show();
			
			// buttons
			$("#makeReservation").hide();
		}else if(data === "HOST") {
			$("#profileId").show();
			$("#userViewId").hide();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
			$("#amenitiesId").hide();
			$("#reservationsId").show();
			
			//buttons
			$("#makeReservation").hide();
		}else {
			$("#profileId").show();
			$("#userViewId").hide();
			$("#logoutId").show();
			$("#registerId").hide();
			$("#loginId").hide();
			$("#amenitiesId").hide();
			$("#reservationsId").show();
			
			//buttons
			$("#makeReservation").show();
		}
		
	});
		
}




function searchAndFilter() {
	var min_price_filter, max_price_filter, min_rooms_filter, max_rooms_filter, min_person_filter, max_person_filter;
	var table, tr, td_price, td_rooms, td_person, i;
	min_price_filter = document.getElementById("min-price").value;
	max_price_filter = document.getElementById("max-price").value;
	min_rooms_filter = document.getElementById("min-rooms").value;
	max_rooms_filter = document.getElementById("max-rooms").value;
	min_person_filter = document.getElementById("min-person").value;
	max_person_filter = document.getElementById("max-person").value;
	table = document.getElementById("tab2");
	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {
		
		if (tr[i].style.display == "none") {
			continue;
		}

		td_pr = tr[i].getElementsByTagName("td")[5];
		td_price = td_pr.textContent || td_pr.innerText;
		td_price = Number(td_price);
		td_r = tr[i].getElementsByTagName("td")[3];
		td_rooms = td_r.textContent || td_r.innerText;
		td_rooms = Number(td_rooms);
		td_pe = tr[i].getElementsByTagName("td")[4];
		td_person = td_pe.textContent || td_pe.innerText;
		td_person = Number(td_person);

		// nema filtera
		if (min_price_filter == "" && max_price_filter == ""
				&& min_rooms_filter == "" && max_rooms_filter == ""
				&& min_person_filter == "" && max_person_filter == "") {
			tr[i].style.display = "";
			return;
		}

		if (min_price_filter != "" || max_price_filter != "") {
			// postavljen samo MAX PRICE filter
			if (min_price_filter == "") {
				if (td_price <= Number(max_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN PRICE filter
			} else if (max_price_filter == "") {
				if (td_price >= Number(min_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljeni i MIN i MAX
			} else {
				if (td_price >= Number(min_price_filter)
						&& td_core <= Number(max_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_rooms_filter != "" || max_rooms_filter != "") {
			// postavljen samo MAX ROOMS filter
			if (min_rooms_filter == "") {
				if (td_rooms <= Number(max_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN ROOMS filter
			} else if (max_rooms_filter == "") {
				if (td_rooms >= Number(min_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_rooms >= Number(min_rooms_filter)
						&& td_rooms <= Number(max_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_person_filter != "" || max_person_filter != "") {
			// postavljen samo MAX PERSON filter
			if (min_person_filter == "") {
				if (td_person <= Number(max_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN PERSON filter
			} else if (max_person_filter == "") {
				if (td_person >= Number(min_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_person >= Number(min_person_filter)
						&& td_person <= Number(max_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

	}
	
}

function searchApartments() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("search-box");
	filter = input.value.toUpperCase();
	table = document.getElementById("tab2");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {

		td = tr[i].getElementsByTagName("td")[8];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function filter() {
	var min_price_filter, max_price_filter, min_rooms_filter, max_rooms_filter, min_person_filter, max_person_filter;
	var table, tr, td_price, td_rooms, td_person, i;
	min_price_filter = document.getElementById("min-price").value;
	max_price_filter = document.getElementById("max-price").value;
	min_rooms_filter = document.getElementById("min-rooms").value;
	max_rooms_filter = document.getElementById("max-rooms").value;
	min_person_filter = document.getElementById("min-person").value;
	max_person_filter = document.getElementById("max-person").value;
	table = document.getElementById("tab2");
	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {

		td_pr = tr[i].getElementsByTagName("td")[5];
		td_price = td_pr.textContent || td_pr.innerText;
		td_price = Number(td_price);
		td_r = tr[i].getElementsByTagName("td")[3];
		td_rooms = td_r.textContent || td_r.innerText;
		td_rooms = Number(td_rooms);
		td_pe = tr[i].getElementsByTagName("td")[4];
		td_person = td_pe.textContent || td_pe.innerText;
		td_person = Number(td_person);

		// nema filtera
		if (min_price_filter == "" && max_price_filter == ""
				&& min_rooms_filter == "" && max_rooms_filter == ""
				&& min_person_filter == "" && max_person_filter == "") {
			tr[i].style.display = "";
			continue;
		}

		if (min_price_filter != "" || max_price_filter != "") {
			// postavljen samo MAX PRICE filter
			if (min_price_filter == "") {
				if (td_price <= Number(max_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN PRICE filter
			} else if (max_price_filter == "") {
				if (td_price >= Number(min_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljeni i MIN i MAX
			} else {
				if (td_price >= Number(min_price_filter)
						&& td_core <= Number(max_price_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_rooms_filter != "" || max_rooms_filter != "") {
			// postavljen samo MAX ROOMS filter
			if (min_rooms_filter == "") {
				if (td_rooms <= Number(max_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN ROOMS filter
			} else if (max_rooms_filter == "") {
				if (td_rooms >= Number(min_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_rooms >= Number(min_rooms_filter)
						&& td_rooms <= Number(max_rooms_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_person_filter != "" || max_person_filter != "") {
			// postavljen samo MAX PERSON filter
			if (min_person_filter == "") {
				if (td_person <= Number(max_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN PERSON filter
			} else if (max_person_filter == "") {
				if (td_person >= Number(min_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_person >= Number(min_person_filter)
						&& td_person <= Number(max_person_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

	}

}



