package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.AmenityDAO;
import pack.dao.UserDAO;
import pack.dto.RegisterDTO;
import pack.enums.Gender;
import pack.enums.Role;
import pack.model.Admin;
import pack.model.Apartment;
import pack.model.Guest;
import pack.model.Host;
import pack.model.Reservation;
import pack.model.User;

public class UserService {
	
	public UserDAO userDAO;
	public AmenityDAO amenityDAO;
	
	public UserService(String path) {
		this.userDAO = new UserDAO(path);
		this.amenityDAO = new AmenityDAO(path);
	}
	
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	public List<Admin> getAllAdmins() {
		List<Admin> retVal = new ArrayList<>();
		List<User> allUsers = this.getAllUsers();
		for(User u : allUsers) {
			if(u instanceof Admin) {
				retVal.add((Admin) u);
			}
		}
		
		return retVal;
	}
	
	public List<Host> getAllHosts() {
		List<Host> retVal = new ArrayList<>();
		List<User> allUsers = this.getAllUsers();
		for(User u : allUsers) {
			if(u instanceof Host) {
				retVal.add((Host) u);
			}
		}
		
		return retVal;
	}
	
	public List<Guest> getAllGuests() {
		List<Guest> retVal = new ArrayList<>();
		List<User> allUsers = this.getAllUsers();
		for(User u : allUsers) {
			if(u instanceof Guest) {
				retVal.add((Guest) u);
			}
		}
		
		return retVal;
	}
	
	public User getUser(String username) {
		return userDAO.getUser(username);
	}
	
	public void register(RegisterDTO register) {
		//userDAO.addUser(new Guest(register.getUsername(), register.getPassword(), register.getName(), register.getLastname(), Gender.valueOf(register.getGender()), Role.GUEST));
		userDAO.addUser(new Guest(register.getUsername(), register.getPassword(), register.getName(), "aaa", Gender.MALE, Role.GUEST));
	}
	
	public User updateUser(User user) {
		User retVal = this.userDAO.updateUser(user);
		return retVal;
	}
	
	public void deleteUser() {
		
	}
	
	
	public boolean checkIfUserExists(String username) {
		User u = userDAO.getUser(username);

		if(u == null)
			return false;
		else
			return true;
		
	}
	
	public boolean addReservationToGuest(Reservation reservation) {
		Guest guest = (Guest) userDAO.getUser(reservation.getGuest().getUsername());
		guest.getReservations().add(reservation);
		return true;
	}
	
	public List<Guest> getMyApartmentsGuestHistory(Host host) {
		List<Guest> retVal = new ArrayList<>();
		List<String> guestHistoryUsernames= new ArrayList<>();
		
		List<Apartment> myApartments = host.getApartmentsToRent();
		for(Apartment apartment : myApartments) {
			for(Reservation reservation : apartment.getReservations()) {
				if(!guestHistoryUsernames.contains(reservation.getGuest().getUsername())) {
					guestHistoryUsernames.add(reservation.getGuest().getUsername());
				}
			}
		}
		
		for(String username : guestHistoryUsernames) {
			retVal.add((Guest) this.getUser(username));
		}
		
		return retVal;
	}

}
