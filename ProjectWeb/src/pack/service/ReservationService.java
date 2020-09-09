package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.ApartmentDAO;
import pack.dao.ReservationDAO;
import pack.enums.ReservationStatus;
import pack.enums.Role;
import pack.model.Apartment;
import pack.model.Guest;
import pack.model.Host;
import pack.model.Reservation;
import pack.model.User;

public class ReservationService {
	
	public ReservationDAO reservationDAO;
//	public ApartmentDAO apartmentDAO;
	
	public ReservationService(String path) {
		this.reservationDAO = new ReservationDAO(path);
	//	this.apartmentDAO = new ApartmentDAO(path);
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	public List<User> getApartmentGuestHistory(User user, ApartmentDAO apartmentDAO) {
		
		List<Reservation> apartmentReservations = new ArrayList<Reservation>();
		
		for(Apartment apartment : apartmentDAO.getHostApartments(user.getUsername())) {
			apartmentReservations.addAll(reservationDAO.getApartmentReservations(apartment.getId()));
		}
		
		List<User> retVal = new ArrayList<User>();
		for(Reservation r: apartmentReservations) {
			System.out.println("111");
			if(!retVal.contains(r)) {
				retVal.add(r.getGuest());
			}
		}
		
		return retVal;
	}
	
	public List<Reservation> getAllReservations(User user) {
		if(user.getRole() == Role.GUEST) {
			return ((Guest)user).getReservations();
		} else if(user.getRole() == Role.HOST) {
			List<Apartment> apartments = ((Host)user).getApartmentsToRent();
			List<Reservation> reservationsOfAllApartments = new ArrayList<Reservation>();
			for(Apartment a : apartments) {
				reservationsOfAllApartments.addAll(a.getReservations());
			}
			return reservationsOfAllApartments;
		} else {
			return reservationDAO.getAllReservations();
		}
		
	}
	
	public void addReservation(Reservation reservation) {
		this.reservationDAO.addReservation(reservation);
	}
	
	public boolean checkIfPossibleToLeaveComment(Long reservationId) {
		Reservation reservation = this.reservationDAO.getReservation(reservationId);
		if(reservation.getStatus() == ReservationStatus.FINISHED || reservation.getStatus() == ReservationStatus.REJECTED) {
			return true;
		}
		
		return false;
	}
	
	
	
	

}
