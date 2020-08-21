package pack.model;

import pack.enums.Gender;
import pack.enums.Role;

public class Admin extends User{
	
	public Admin() {}
	
	public Admin(String username, String password, String name, String lastname, 
			Gender gender, Role role) {
		super(username, password, name, lastname, gender, role);
	}

}
