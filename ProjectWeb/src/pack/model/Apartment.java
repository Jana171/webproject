package pack.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pack.enums.ApartmentType;

public class Apartment {
	
	private Long id;
	private String name;
	private ApartmentType type;
	private int numberOfRooms;
	private int numberOfGuests;
	private int priceForNight;
	private int timeForCheckIn = 14;
	private int timeForCheckOut = 10;
	private boolean active;		//predstavlja status
	
	private Location location;
	private Host host;
	
	private List<LocalDate> datesForRent = new ArrayList<LocalDate>();
	private List<LocalDate> availableDates = new ArrayList<LocalDate>();
	private List<Comment> comments = new ArrayList<Comment>();
	private List<Amenity> amenities = new ArrayList<Amenity>();
	private List<Reservation> reservations = new ArrayList<Reservation>();

	// verovatno ce biti putanje do slika na serveru, izmeniti ako to ne bude slucaj
	private List<String> images = new ArrayList<String>();
	
	private boolean deleted = false;

	public Apartment() {}
	
	public Apartment(ApartmentType type, int numberOfRooms, int numberOfGuests, int priceForNight,
			int timeForCheckIn, int timeForCheckOut, boolean active, Location location, Host host, Long id, String name) {
		
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.priceForNight = priceForNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.active = active;
		this.location = location;
		this.host = host;
		this.id = id;
		this.name = name;
	}

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public int getPriceForNight() {
		return priceForNight;
	}

	public void setPriceForNight(int priceForNight) {
		this.priceForNight = priceForNight;
	}

	public int getTimeForCheckIn() {
		return timeForCheckIn;
	}

	public void setTimeForCheckIn(int timeForCheckIn) {
		this.timeForCheckIn = timeForCheckIn;
	}

	public int getTimeForCheckOut() {
		return timeForCheckOut;
	}

	public void setTimeForCheckOut(int timeForCheckOut) {
		this.timeForCheckOut = timeForCheckOut;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<LocalDate> getDatesForRent() {
		return datesForRent;
	}

	public void setDatesForRent(List<LocalDate> datesForRent) {
		this.datesForRent = datesForRent;
	}

	public List<LocalDate> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(List<LocalDate> availableDates) {
		this.availableDates = availableDates;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
