package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pack.enums.ApartmentType;
import pack.enums.Role;
import pack.model.Address;
import pack.model.Amenity;
import pack.model.Apartment;
import pack.model.Host;
import pack.model.Location;
import pack.model.User;

public class ApartmentDAO {
	
	private String path;
	private List<Apartment> apartments = new ArrayList<Apartment>();
	private ReservationDAO reservationDAO;
	private CommentDAO commentDAO;
	
	public ApartmentDAO(String path) {
		this.path = path;
		this.reservationDAO = new ReservationDAO(path);
		this.commentDAO = new CommentDAO(path);
		this.loadApartments();
	}
	
	//refaktorisi posebna metoda za json obj pa izdvoj save u posebnu a add kao poziv te 2
	
	public List<Apartment> getAllApartments() {
		return apartments;
	}
	
	public Apartment getApartment(Long id) {
		for(Apartment a: this.apartments) {
			if(a.getId() == id)
				return a;
		}
		
		return null;
	}
	
	public List<Apartment> getHostApartments(String username) {
		List<Apartment> retVal = new ArrayList<Apartment>();
		for(Apartment a : apartments) 
			if(a.getHost().getUsername().equals(username))
				retVal.add(a);
		
		return retVal;
	}
	
	public List<Apartment> getGuestRentedApartmentsByIds(List<Long> apartmentWithIds) {
		List<Apartment> retVal = new ArrayList<Apartment>();
		for(Long a : apartmentWithIds) {
			retVal.add(this.getApartment(a));
		}
		
		return retVal;
	}
	
	
	public boolean addApartment(Apartment apartment) {
		int id = apartments.size() + 1;
		apartment.setId((long) id);
		apartments.add(apartment);
		this.saveApartment(apartment);
		return true;
	}
	
	public Apartment updateApartment(Apartment apartment, UserDAO userDAO) {
		Apartment a = this.getApartment(apartment.getId());
		a.setActive(apartment.isActive());
		a.setLocation(apartment.getLocation());
		a.setName(apartment.getName());
		a.setNumberOfGuests(apartment.getNumberOfGuests());
		a.setNumberOfRooms(apartment.getNumberOfRooms());
		a.setPriceForNight(apartment.getPriceForNight());
		a.setTimeForCheckIn(apartment.getTimeForCheckIn());
		a.setTimeForCheckOut(apartment.getTimeForCheckOut());
		a.setType(apartment.getType());
		
		for(User user : userDAO.getAllUsers()) {
			if(user.getRole() == Role.HOST) {
				for(Apartment ap: ((Host)user).getApartmentsToRent()) {
					if(ap.getId() == apartment.getId()) {
						ap.setActive(apartment.isActive());
						ap.setLocation(apartment.getLocation());
						ap.setName(apartment.getName());
						ap.setNumberOfGuests(apartment.getNumberOfGuests());
						ap.setNumberOfRooms(apartment.getNumberOfRooms());
						ap.setPriceForNight(apartment.getPriceForNight());
						ap.setTimeForCheckIn(apartment.getTimeForCheckIn());
						ap.setTimeForCheckOut(apartment.getTimeForCheckOut());
						ap.setType(apartment.getType());
					}
				}
			}
		}
		
		return a;
		
	}
	
	public boolean deleteApartment(int id, UserDAO userDAO) {
		Apartment apartment = this.getApartment((long) id);
		if(apartment == null) {
			return false;
		} else {
			apartment.setDeleted(true);
			for(User user : userDAO.getAllUsers()) {
				if(user.getRole() == Role.HOST) {
					for(Apartment a: ((Host)user).getApartmentsToRent()) {
						if(a.getId() == id) {
							a.setDeleted(true);
						}
					}
				}
			}
			
			return true;
		}
	}
	
	public void loadApartments() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/apartments.json";
		try {
			
			JSONArray apartments = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : apartments) {
				JSONObject apartmentJSON = (JSONObject) o;
				
				Long id = (Long) apartmentJSON.get("id");
				String name = (String) apartmentJSON.get("name");

				String typeStr = (String) apartmentJSON.get("type");
				ApartmentType type = ApartmentType.valueOf(typeStr);
				
				int numberOfRooms = ((Long) apartmentJSON.get("numberOfRooms")).intValue();
				int numberOfGuests = ((Long) apartmentJSON.get("numberOfGuests")).intValue();
				int priceForNight = ((Long) apartmentJSON.get("priceForNight")).intValue();
				int timeForCheckIn = ((Long) apartmentJSON.get("timeForCheckIn")).intValue();
				int timeForCheckOut = ((Long) apartmentJSON.get("timeForCheckOut")).intValue();
				boolean active = (boolean) apartmentJSON.get("active");
				boolean deleted = (boolean) apartmentJSON.get("deleted");
				
				if(deleted)
					continue;
				
				String hostUsername = (String) apartmentJSON.get("host");
				Host host = new Host();
				host.setUsername(hostUsername);
				
				JSONArray imagesJSON = (JSONArray) apartmentJSON.get("images");
				List<String> images = new ArrayList<String>();
				for(int i = 0 ; i < imagesJSON.size(); i++) {
					images.add((String) imagesJSON.get(i));
				}
				
				JSONArray datesForRentJSON = (JSONArray) apartmentJSON.get("datesForRent");
				List<LocalDate> datesForRent= new ArrayList<LocalDate>();
				for(int i = 0 ; i < datesForRentJSON.size(); i++) {
					datesForRent.add(LocalDate.parse((String) datesForRentJSON.get(i)));
				}
				
				JSONArray availableDatesJSON = (JSONArray) apartmentJSON.get("availableDates");
				List<LocalDate> availableDates= new ArrayList<LocalDate>();
				for(int i = 0 ; i < availableDatesJSON.size(); i++) {
					availableDates.add(LocalDate.parse((String) availableDatesJSON.get(i)));
				}
				
				JSONArray amenitiesJSON = (JSONArray) apartmentJSON.get("amenities");
				List<Amenity> amenities= new ArrayList<Amenity>();
				for(int i = 0 ; i < amenitiesJSON.size(); i++) {
					JSONObject amenityObject = (JSONObject) amenitiesJSON.get(i);
					int amId = ((Long) amenityObject.get("id")).intValue();
					String amName = (String) amenityObject.get("title");
					boolean amDeleted= (Boolean) amenityObject.get("deleted");
					
					Amenity amenity = new Amenity(amId,amName);
					amenity.setDeleted(amDeleted);
					amenities.add(amenity);
				}
				
				JSONObject locationJSON = (JSONObject) apartmentJSON.get("location");
				double longitude = (double) locationJSON.get("longitude");
				double latitude = (double) locationJSON.get("latitude");
				boolean locDeleted= (boolean) locationJSON.get("deleted");
				JSONObject addressJSON = (JSONObject) locationJSON.get("address");
				String city = (String) addressJSON.get("city");
				String street = (String) addressJSON.get("street");
				String number = (String) addressJSON.get("number");
				boolean adrDeleted = (boolean) addressJSON.get("deleted");
				Address address = new Address(street,city,number);
				address.setDeleted(adrDeleted);
				Location location = new Location(longitude,latitude,address);
				location.setDeleted(locDeleted);
				
				Apartment apartment = new Apartment(type,numberOfRooms,numberOfGuests,priceForNight,timeForCheckIn,timeForCheckOut,active,location,host,id,name);				
				apartment.setAvailableDates(availableDates);
				apartment.setAmenities(amenities);
				apartment.setDatesForRent(datesForRent);
				apartment.setImages(images);
				apartment.setReservations(reservationDAO.getApartmentReservations(id));
				apartment.setComments(commentDAO.getApartmentComments(id));
				this.apartments.add(apartment);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		

	}
	
	@SuppressWarnings("unchecked")
	public void saveApartment(Apartment apartment) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/apartments.json";
		try {
			
			JSONArray apartmentsArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject apartmentJSON = new JSONObject();
			apartmentJSON.put("id", apartment.getId());
			apartmentJSON.put("name", apartment.getName());
			apartmentJSON.put("type",apartment.getType().toString());
			apartmentJSON.put("numberOfRooms", apartment.getNumberOfRooms());
			apartmentJSON.put("numberOfGuests", apartment.getNumberOfGuests());
			apartmentJSON.put("priceForNight",apartment.getPriceForNight());
			apartmentJSON.put("timeForCheckIn",apartment.getTimeForCheckIn());
			apartmentJSON.put("timeForCheckOut",apartment.getTimeForCheckOut());
			apartmentJSON.put("active",apartment.isActive());
			apartmentJSON.put("deleted",apartment.isDeleted());
			apartmentJSON.put("host",apartment.getHost().getUsername());

			
			JSONArray imagesJSON = new JSONArray();
			for(String im : apartment.getImages()) {
				imagesJSON.add(im);
			}
			apartmentJSON.put("images",imagesJSON);
			
			
			JSONArray datesForRentJSON = new JSONArray();
			for(LocalDate ld : apartment.getDatesForRent()) {
				datesForRentJSON.add(ld);
			}
			apartmentJSON.put("datesForRent",datesForRentJSON);
			
			JSONArray availableDatesJSON = new JSONArray();
			for(LocalDate ld : apartment.getAvailableDates()) {
				datesForRentJSON.add(ld);
			}
			apartmentJSON.put("availableDates",availableDatesJSON);
			
			JSONArray amenitiesJSON = new JSONArray();
			for(Amenity am : apartment.getAmenities()) {
				JSONObject amenityJSON = new JSONObject();
				amenityJSON.put("id", am.getId());
				amenityJSON.put("title", am.getTitle());
				amenityJSON.put("deleted", am.isDeleted());
				amenitiesJSON.add(amenityJSON);
			}
			apartmentJSON.put("amenities",amenitiesJSON);
			
			JSONObject locationJSON = new JSONObject();
			locationJSON.put("longitude", apartment.getLocation().getLongitude());
			locationJSON.put("latitude", apartment.getLocation().getLatitude());
			locationJSON.put("deleted", apartment.getLocation().isDeleted());
			JSONObject addressJSON = new JSONObject();
			addressJSON.put("city", apartment.getLocation().getAddress().getCity());
			addressJSON.put("street", apartment.getLocation().getAddress().getStreet());
			addressJSON.put("numer", apartment.getLocation().getAddress().getNumber());
			addressJSON.put("deleted", apartment.getLocation().getAddress().isDeleted());
			locationJSON.put("address", addressJSON);
			apartmentJSON.put("location", locationJSON);
			
			apartmentsArray.add(apartmentJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(apartmentsArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
	}

}
