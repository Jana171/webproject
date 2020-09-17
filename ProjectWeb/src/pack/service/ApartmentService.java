package pack.service;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pack.dao.AmenityDAO;
import pack.dao.ApartmentDAO;
import pack.dao.UserDAO;
import pack.dto.ApartmentDTO;
import pack.enums.Role;
import pack.model.Amenity;
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
	
	public boolean addApartment(ApartmentDTO apartmentDTO, Host host, AmenityDAO amenityDAO) {
		
		Apartment apartment = new Apartment();
		apartment.setActive(false);
		apartment.setHost(host);
		apartment.setName(apartmentDTO.getName());
		apartment.setLocation(apartmentDTO.getLocation());
		apartment.setNumberOfGuests(apartmentDTO.getNumberOfGuests());
		apartment.setNumberOfRooms(apartmentDTO.getNumberOfRooms());
		apartment.setPriceForNight(apartmentDTO.getPriceForNight());
		apartment.setTimeForCheckIn(apartmentDTO.getTimeForCheckIn());
		apartment.setTimeForCheckOut(apartmentDTO.getTimeForCheckOut());
		apartment.setType(apartmentDTO.getType());
		List<Amenity> amenities = new ArrayList<Amenity>();
		for (int amenityId : apartmentDTO.getAmenities()) {
			amenities.add(amenityDAO.getAmenity(amenityId));
		}
		apartment.setAmenities(amenities);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		List<LocalDate> available = new ArrayList<LocalDate>();
		for (String s : apartmentDTO.getAvailableDates()) {
			available.add(LocalDate.parse(s, dtf));
		}
		
		apartment.setAvailableDates(available);
		apartment.setDatesForRent(available);
		
		
		host.getApartmentsToRent().add(apartment);
		return apartmentDAO.addApartment(apartment);
	}
	
	public List<Apartment> getAllApartmentsByUserRole(User user) {
		List<Apartment> allApartments= this.getAllApartments();
		List<Apartment> retVal = new ArrayList<Apartment>();
		
		//neregistrovani
		if(user == null || user.getRole() == Role.GUEST) {
			for(Apartment apartment : allApartments) {
				if(apartment.isActive() && !apartment.isDeleted()) {
					retVal.add(apartment);
				}
			}
		} else if(user.getRole() == Role.ADMIN) {
			return allApartments;
		} else {
			for(Apartment apartment : allApartments) {
				if(apartment.getHost().getUsername().equals(user.getUsername()) && !apartment.isDeleted()) {
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


	public Apartment getApartment(int id) {
		return this.apartmentDAO.getApartment((long) id);
		
	}


	public boolean deleteApartment(int id,UserDAO userDAO) {
		return this.apartmentDAO.deleteApartment(id,userDAO);
		
	}


	public Apartment updateApartment(Apartment apartment, UserDAO userDAO) {
		return this.apartmentDAO.updateApartment(apartment,userDAO);
	}
	

}
