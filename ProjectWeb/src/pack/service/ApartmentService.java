package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.ApartmentDAO;
import pack.enums.Role;
import pack.model.Apartment;
import pack.model.User;

public class ApartmentService {
	
	public ApartmentDAO apartmentDAO;
	
	public ApartmentService(String path) {
		this.apartmentDAO = new ApartmentDAO(path);
	}
	
	
	public List<Apartment> getAllApartments() {
		return apartmentDAO.getAllApartments();
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
	

}
