package pack.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pack.dao.ApartmentDAO;
import pack.dao.ReservationDAO;
import pack.dao.UserDAO;
import pack.dto.ReservationDTO;
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
			return this.reservationDAO.getGuestReservations(user.getUsername());
			
			//return ((Guest)user).getReservations();
		} else if(user.getRole() == Role.HOST) {
			List<Apartment> apartments = ((Host)user).getApartmentsToRent();
			List<Reservation> reservationsOfAllApartments = new ArrayList<Reservation>();
			for(Apartment a : apartments) {
				for (Reservation r : reservationDAO.getAllReservations()) {
					if (a.getId() == r.getApartment().getId()) {
						reservationsOfAllApartments.add(r);
					}
				}
				//reservationsOfAllApartments.addAll(a.getReservations());
			}
			return reservationsOfAllApartments;
		} else {
			return reservationDAO.getAllReservations();
		}
		
	}
	
	public Reservation addReservation(Reservation reservation) {
		return this.reservationDAO.addReservation(reservation);
	}
	
	public boolean checkIfPossibleToLeaveComment(Long reservationId) {
		Reservation reservation = this.reservationDAO.getReservation(reservationId);
		if(reservation.getStatus() == ReservationStatus.FINISHED || reservation.getStatus() == ReservationStatus.REJECTED) {
			return true;
		}
		
		return false;
	}


	public Reservation changeStatusReservation(Reservation reservation, UserDAO userDAO, ApartmentDAO apartmentDAO) {
		return this.reservationDAO.changeStatusReservation(reservation, userDAO, apartmentDAO);
	}


	public Reservation getReservation(int id) {
		return this.reservationDAO.getReservation((long) id);
	}


	public boolean deleteReservation(int id, UserDAO userDAO, ApartmentDAO apartmentDAO) {
		return this.reservationDAO.deleteReservation(id, userDAO, apartmentDAO);
	}
	
	
	public Reservation handleReservationDTO(ReservationDTO dto, ApartmentService aptService, Guest guest) {
		Reservation newReservation = new Reservation();
		newReservation.setStatus(ReservationStatus.CREATED);
		newReservation.setGuest(guest);
		
		Apartment apt = aptService.getApartment(dto.getApartmentId().intValue());
		newReservation.setApartment(apt);
		newReservation.setTotalPrice(dto.getNumberOfOvernightsStay() * apt.getPriceForNight());
		
		newReservation.setMessageWhenBooking(dto.getMessageWhenBooking());
		newReservation.setNumberOfOvernightsStay(dto.getNumberOfOvernightsStay());
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-M-dd");
		
		newReservation.setStartDate(LocalDate.parse(dto.getStartDate(), df));
		
		
		
		List<LocalDate> reservedDates = new ArrayList<LocalDate>();
		
		for (int i = 1; i < newReservation.getNumberOfOvernightsStay() + 1; i++) {
			reservedDates.add(newReservation.getStartDate().plusDays(i));
		}
		
		if (apt.getAvailableDates().containsAll(reservedDates)) {
			return newReservation;
		} else {
			return null;
		}
		
		
		
	}
	
	
	

}
