package pack.dto;

import java.util.List;

import pack.enums.ApartmentType;
import pack.model.Location;

public class ApartmentDTO {
	
	private String name;
	private ApartmentType type;
	private int numberOfRooms;
	private int numberOfGuests;
	private int priceForNight;
	private int timeForCheckIn = 14;
	private int timeForCheckOut = 10;
	private Location location;
	private List<Integer> amenities;
	private List<String> availableDates;
	
	public ApartmentDTO() {
		
	}

	public ApartmentDTO( String name, ApartmentType type, int numberOfRooms, int numberOfGuests,
			int priceForNight, int timeForCheckIn, int timeForCheckOut, Location location, List<Integer> amenities) {
		super();
		this.name = name;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.priceForNight = priceForNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.location = location;
		this.amenities = amenities;
	}
	
	

	public List<String> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(List<String> availableDates) {
		this.availableDates = availableDates;
	}

	public ApartmentDTO(String name, ApartmentType type, int numberOfRooms, int numberOfGuests,
			int priceForNight, int timeForCheckIn, int timeForCheckOut, Location location, List<Integer> amenities,
			List<String> availableDates) {
		super();

		this.name = name;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.priceForNight = priceForNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.location = location;
		this.amenities = amenities;
		this.availableDates = availableDates;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Integer> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Integer> amenities) {
		this.amenities = amenities;
	}
	
	
	
	

}
