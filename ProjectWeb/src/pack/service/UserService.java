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
	
	public void addUser() {
		
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}

}
