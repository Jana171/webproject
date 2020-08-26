package pack.service;

import pack.dao.ApartmentDAO;

public class ApartmentService {
	
	public ApartmentDAO apartmentDAO;
	
	public ApartmentService(String path) {
		this.apartmentDAO = new ApartmentDAO(path);
	}
	
	

}
