package pack.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import pack.enums.Gender;
import pack.enums.Role;

public class Guest extends User{
	
	
	private List<Apartment> rentedApartments = new ArrayList<Apartment>();
	
	
	private List<Long> reservations = new ArrayList<Long>();
	
	public Guest() {}
	
	public Guest(String username, String password, String name, String lastname,
			Gender gender, Role role) {
		super(username, password, name, lastname, gender, role);
	}

	public List<Apartment> getRentedApartments() {
		return rentedApartments;
	}

	public void setRentedApartments(List<Apartment> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}

	public List<Long> getReservations() {
		return reservations;
	}

	public void setReservations(List<Long> reservations) {
		this.reservations = reservations;
	}
	
	

}
