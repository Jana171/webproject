package pack.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pack.enums.Gender;
import pack.enums.Role;

public class Host extends User{
	
	
	//@JsonBackReference
	private List<Apartment> apartmentsToRent = new ArrayList<Apartment>();
	
	public Host() {}
	
	public Host(String username, String password, String name, String lastname, 
			Gender gender, Role role) {
		super(username, password, name, lastname, gender, role);
	}

	public List<Apartment> getApartmentsToRent() {
		return apartmentsToRent;
	}

	public void setApartmentsToRent(List<Apartment> apartmentsToRent) {
		this.apartmentsToRent = apartmentsToRent;
	}
	
	

}
