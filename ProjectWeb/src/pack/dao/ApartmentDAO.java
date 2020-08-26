package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pack.enums.ApartmentType;
import pack.model.Address;
import pack.model.Amenity;
import pack.model.Apartment;
import pack.model.Location;

public class ApartmentDAO {
	
	private String path;
	private List<Apartment> apartments = new ArrayList<Apartment>();
	
	public ApartmentDAO(String path) {
		this.path = path;
		this.loadApartments();
	}
	
	//refaktorisi posebna metoda za json obj pa izdvoj save u posebnu a add kao poziv te 2
	
	public List<Apartment> getApartments(String username) {
		return apartments;
	}
	
	public Apartment getApartment(int id) {
		for(Apartment a: apartments)
			if(a.getId() == id)
				return a;
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public void addApartment(Apartment apartment) {
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
			
			apartments.add(apartment);
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
	
	public void updateApartment() {
		
	}
	
	public void deleteApartment() {
		
	}
	
	public void loadApartments() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/apartments.json";
		try {
			
			JSONArray apartments = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : apartments) {
				JSONObject apartmentJSON = (JSONObject) o;
				
				int id = (int) apartmentJSON.get("id");
				String name = (String) apartmentJSON.get("name");

				String typeStr = (String) apartmentJSON.get("type");
				ApartmentType type = ApartmentType.valueOf(typeStr);
				
				int numberOfRooms = (int) apartmentJSON.get("numberOfRooms");
				int numberOfGuests = (int) apartmentJSON.get("numberOfGuests");
				int priceForNight = (int) apartmentJSON.get("priceForNight");
				int timeForCheckIn = (int) apartmentJSON.get("timeForCheckIn");
				int timeForCheckOut = (int) apartmentJSON.get("timeForCheckOut");
				boolean active = (boolean) apartmentJSON.get("active");
				boolean deleted = (boolean) apartmentJSON.get("deleted");
				String hostUsername = (String) apartmentJSON.get("host");
				
				JSONArray imagesJSON = (JSONArray) apartmentJSON.get("images");
				List<String> images = new ArrayList<String>();
				for(int i = 0 ; i < imagesJSON.size(); i++) {
					images.add((String) imagesJSON.get(i));
				}
				
				JSONArray datesForRentJSON = (JSONArray) apartmentJSON.get("datesForRent");
				List<LocalDate> datesForRent= new ArrayList<LocalDate>();
				for(int i = 0 ; i < datesForRentJSON.size(); i++) {
					datesForRent.add((LocalDate) datesForRentJSON.get(i));
				}
				
				JSONArray availableDatesJSON = (JSONArray) apartmentJSON.get("availableDates");
				List<LocalDate> availableDates= new ArrayList<LocalDate>();
				for(int i = 0 ; i < availableDatesJSON.size(); i++) {
					availableDates.add((LocalDate) availableDatesJSON.get(i));
				}
				
				JSONArray amenitiesJSON = (JSONArray) apartmentJSON.get("amenities");
				List<Amenity> amenities= new ArrayList<Amenity>();
				for(int i = 0 ; i < amenitiesJSON.size(); i++) {
					JSONObject amenityObject = (JSONObject) amenitiesJSON.get(i);
					int amId = (int) amenityObject.get("id");
					String amName = (String) amenityObject.get("name");
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
				String city = (String) apartmentJSON.get("city");
				String street = (String) apartmentJSON.get("street");
				String number = (String) apartmentJSON.get("number");
				boolean adrDeleted = (boolean) apartmentJSON.get("deleted");
				Address address = new Address(street,city,number);
				address.setDeleted(adrDeleted);
				Location location = new Location(longitude,latitude,address);
				location.setDeleted(locDeleted);
				
								
				//preko DAO
				Apartment apartment = new Apartment();
				
				
				this.apartments.add(apartment);
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