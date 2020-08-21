package pack.service;

import java.util.List;

import pack.dao.UserDAO;
import pack.model.User;

public class UserService {
	
	public UserDAO userDAO;
	
	public UserService(String path) {
		this.userDAO = new UserDAO(path);
	}
	
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	public User getUser() {
		return userDAO.getUser();
	}
	
	public void addUser(User user) {
		userDAO.addUser(user);
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	public boolean checkIfUserExists(User user) {
		List<User> users = userDAO.getAllUsers();
		boolean exists = false;
		
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername())) {
				exists = true;
			}
		}
		
		return exists;
	}

}
