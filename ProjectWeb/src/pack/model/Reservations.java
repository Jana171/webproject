package pack.model;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
	
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
	public Reservations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservations(List<Reservation> reservations) {
		super();
		this.reservations = reservations;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	

}
