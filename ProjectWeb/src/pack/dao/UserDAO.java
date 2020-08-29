package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import pack.model.Reservation;
import pack.model.User;

public class UserDAO {
	
	private String path;
	private List<User> users = new ArrayList<User>();
	
	private ApartmentDAO apartmentDAO;
	private ReservationDAO reservationDAO;
	
	public UserDAO(String path) {
		this.path = path;
		this.apartmentDAO = new ApartmentDAO(path);
		this.reservationDAO = new ReservationDAO(path);
		
		this.loadUsers();
	}
	
	public List<User> getAllUsers() {
		return users;
	}
	
	
	
	
	
	public User getUser(String username) {
		User user = null;
		
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				user = u;
			}
		}
		
		return user;
	}
	
	public void addUser(User user) {
		users.add(user);
		saveUser(user);
	}
	
	@SuppressWarnings("unchecked")
	public void saveUser(User user) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/users.json";
		System.out.println("Prava putanja: " + fullPath);
		try {
			
			JSONArray usersArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject userJSON = new JSONObject();
			userJSON.put("username", user.getUsername());
			userJSON.put("password", user.getPassword());
			userJSON.put("gender", user.getGender().toString());
			userJSON.put("lastname", user.getLastname());
			userJSON.put("name", user.getName());
			userJSON.put("role", user.getRole().toString());
			userJSON.put("deleted", false);
			
			usersArray.add(userJSON);
			FileWriter file = new FileWriter(fullPath);
            file.write(usersArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
	}
	
	public User updateUser(User user) {
		for(int i = 0 ; i < this.users.size(); i++) {
			if(this.users.get(i).getUsername().equals(user.getUsername())) {
				this.users.get(i).setGender(user.getGender());
				this.users.get(i).setLastname(user.getLastname());
				this.users.get(i).setName(user.getName());
				this.users.get(i).setPassword(user.getPassword());
				
				return this.users.get(i);
				//delete from file
				//save file
			}
		}
		return null;
	}
	
	public void deleteUser() {
		
	}
	
	public void loadUsers() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/users.json";
		System.out.println("Prava putanja ucitavanja: " + fullPath);
		try {
			
			JSONArray users = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

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
				
				if(role == Role.ADMIN) {
					Admin admin = new Admin(username,password,name,lastname,gender,role);
					this.users.add(admin);
					
				} else if (role == Role.GUEST) {
					Guest guest = new Guest(username,password,name,lastname,gender,role);
					guest.setReservations(this.reservationDAO.getUserReservations(username));
					guest.setRentedApartments(this.reservationDAO.getGuestRentedApartments(username));
					this.users.add(guest);
					
				} else {
					Host host = new Host(username,password,name,lastname,gender,role);
					System.out.println(apartmentDAO.getHostApartments(username).size());
					host.setApartmentsToRent(apartmentDAO.getHostApartments(username));
					this.users.add(host);
				}
				
				
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
		System.out.println("Ucitani korisnici");
	}

}
