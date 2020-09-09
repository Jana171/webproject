package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.ApartmentDAO;
import pack.dao.UserDAO;
import pack.enums.Role;
import pack.model.Apartment;
import pack.model.Comment;
import pack.model.Host;
import pack.model.Reservation;
import pack.model.User;

public class ApartmentService {
	
	public ApartmentDAO apartmentDAO;
	
	public ApartmentService(String path) {
		this.apartmentDAO = new ApartmentDAO(path);
	}
	
	
	public List<Apartment> getAllApartments() {
		return apartmentDAO.getAllApartments();
	}
	
	public boolean addApartment(Apartment apartment, UserDAO userDAO) {
		Host host = (Host) userDAO.getUser(apartment.getHost().getUsername());
		host.getApartmentsToRent().add(apartment);
		return apartmentDAO.addApartment(apartment);
	}
	
	public List<Apartment> getAllApartmentsByUserRole(User user) {
		List<Apartment> allApartments= this.getAllApartments();
		List<Apartment> retVal = new ArrayList<Apartment>();
		
		//neregistrovani
		if(user == null || user.getRole() == Role.GUEST) {
			for(Apartment apartment : allApartments) {
				if(apartment.isActive()) {
					retVal.add(apartment);
				}
			}
		} else if(user.getRole() == Role.ADMIN) {
			return allApartments;
		} else {
			for(Apartment apartment : allApartments) {
				if(apartment.getHost().getUsername().equals(user.getUsername())) {
					retVal.add(apartment);
				}
			}
		}
		
		
		return retVal;
	}
	
	public boolean addReservationToApartment(Reservation reservation) {
		Apartment apartment = apartmentDAO.getApartment(reservation.getApartment().getId());
		apartment.getReservations().add(reservation);
		return true;
	}
	
	//Bilo duplo dodavanje
	  public boolean addCommentToApartment(Comment comment) {
		Apartment apartment = apartmentDAO.getApartment(comment.getApartment().getId());
		apartment.getComments().add(comment);
		return true;
	}
	

}
