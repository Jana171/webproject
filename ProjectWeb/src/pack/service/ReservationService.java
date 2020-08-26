package pack.service;

import pack.dao.ReservationDAO;
import pack.dao.UserDAO;

public class ReservationService {
	
	public ReservationDAO reservationDAO;
	
	public ReservationService(String path) {
		this.reservationDAO = new ReservationDAO(path);
	}
	
	

}
