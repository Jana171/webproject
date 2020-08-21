package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pack.enums.Gender;
import pack.enums.Role;
import pack.model.Admin;
import pack.model.Guest;
import pack.model.Host;
import pack.model.User;

public class UserDAO {
	
	private List<User> users = new ArrayList<User>();
	
	public UserDAO(String path) {
		this.loadUsers(path);
	}
	
	public List<User> getAllUsers() {
		return users;
	}
	
	public User getUser() {
		return new User();
	}
	
	public void addUser() {
		
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	public void loadUsers(String path) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/users.json";
		
		try {
			
			JSONObject usersObject = (JSONObject) jsonParser.parse(new FileReader(fullPath));	
			JSONArray users = (JSONArray) usersObject.get("users");
			
			for(Object o : users) {
				JSONObject userJSON = (JSONObject) o;
				
				String username = (String) userJSON.get("username");
				String password = (String) userJSON.get("password");
				String name = (String) userJSON.get("name");
				String lastname = (String) userJSON.get("lastname");
				
				String roleStr = (String) userJSON.get("role");
				Role role = Role.valueOf(roleStr);
				
				String genderStr = (String) userJSON.get("gender");
				Gender gender = Gender.valueOf(genderStr);
				
				boolean deleted = (Boolean) userJSON.get("deleted");
				
				if(deleted)
					continue;
				
				User user;
				if(role == Role.ADMIN) {
					user = new Admin(username,password,name,lastname,gender,role);
				} else if (role == Role.GUEST) {
					user = new Guest(username,password,name,lastname,gender,role);
				} else {
					user = new Host(username,password,name,lastname,gender,role);
				}
				
				this.users.add(user);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
		System.out.println("Ucitana vozila");
	}

}
