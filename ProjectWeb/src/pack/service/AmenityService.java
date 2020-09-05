package pack.service;

import java.util.List;

import pack.dao.AmenityDAO;
import pack.dao.ApartmentDAO;
import pack.model.Amenity;
import pack.model.Apartment;

public class AmenityService {

	public AmenityDAO amenityDAO;
	
	public AmenityService(String path) {
		this.amenityDAO = new AmenityDAO(path);
	}
	
	public List<Amenity> getAllAmenities() {
		return amenityDAO.getAllAmenities();
	}
	
	
	public Amenity getAmenity(int id) {
		return amenityDAO.getAmenity(id);
	}
	
	public boolean addAmenity(Amenity amenity) {
		return amenityDAO.addAmenity(amenity);
	}
	
	public Amenity updateAmenity(Amenity amenity, ApartmentDAO apartmentDAO) {
		Amenity retVal = this.amenityDAO.updateAmenity(amenity);
		List<Apartment> apartments = apartmentDAO.getAllApartments();
		for(Apartment a: apartments) {
			for(Amenity am: a.getAmenities()) {
				if(am.getId() == amenity.getId()) {
					am.setTitle(amenity.getTitle());
				}
			}
		}
		
		return retVal;
	}
	 
	public void deleteAmenity(int id, ApartmentDAO apartmentDAO) {
		List<Apartment> apartments = apartmentDAO.getAllApartments();
		for(Apartment a: apartments) {
			for(int i = 0 ; i < a.getAmenities().size(); i++) {
				if(a.getAmenities().get(i).getId() == id) {
					a.getAmenities().get(i).setDeleted(true);
				}
			}
		}
		this.amenityDAO.deleteAmenity(id);
	}
	
	
	
}
