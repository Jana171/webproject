package model;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Guest extends User{
	
	private List<Apartment> rentedApartments = new ArrayList<Apartment>();
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
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

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	

}
