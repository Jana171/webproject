package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.UserDAO;
import pack.model.Reservation;
import pack.model.User;

public class UserService {
	
	public UserDAO userDAO;
	
	public UserService(String path) {
		this.userDAO = new UserDAO(path);
	}
	
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	public User getUser(String username) {
		return userDAO.getUser(username);
	}
	
	public void addUser(User user) {
		userDAO.addUser(user);
	}
	
	public User updateUser(User user) {
		User retVal = this.userDAO.updateUser(user);
		return retVal;
	}
	
	public void deleteUser() {
		
	}
	
	
	public boolean checkIfUserExists(User user) {
		User u = userDAO.getUser(user.getUsername());

		if(u == null)
			return false;
		else
			return true;
		
	}

}
