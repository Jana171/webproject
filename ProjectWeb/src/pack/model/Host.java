package pack.model;

import java.util.ArrayList;
import java.util.List;

import pack.enums.Gender;
import pack.enums.Role;

public class Host extends User{
	
	private List<Apartment> apartmentsToRent = new ArrayList<Apartment>();
	
	public Host() {}
	
	public Host(String username, String password, String name, String lastname, 
			Gender gender, Role role) {
		super(username, password, name, lastname, gender, role);
	}

}
