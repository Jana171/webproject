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

import pack.enums.ReservationStatus;
import pack.model.Apartment;
import pack.model.Guest;
import pack.model.Reservation;

public class ReservationDAO {
	
	private String path;
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
	public ReservationDAO(String path) {
		this.path = path;
		this.loadReservations();
	}
	
	//refaktorisi posebna metoda za json obj pa izdvoj save u posebnu a add kao poziv te 2
	
	public List<Reservation> getGuestReservations(String username) {
		List<Reservation> retVal = new ArrayList<Reservation>();
		
		for(Reservation r : this.reservations) {
			if(r.getGuest().getUsername().equals(username)) {
				retVal.add(r);
			}
		}
		return retVal;
	}
	
	public Reservation getReservation(Long id) {
		
		for(Reservation r : this.reservations) {
			if(r.getId() == id) {
				return r;
			}
		}
		System.out.println("NOT FOUND!");
		return null;
	}
	
	//kako ovo radi
	/*@SuppressWarnings("unlikely-arg-type")
	public List<Apartment> getGuestRentedApartmentsIds(String username) {
		List<Apartment> retVal = new ArrayList<Apartment>();
		
		for(Reservation r : this.reservations) {
			if(r.getGuest().getUsername().equals(username) && !retVal.contains(r.getApartment().getId())) {
				retVal.add(r.getApartment());
			}
		}
		return retVal;
	}*/
	public List<Long> getGuestRentedApartmentsIds(String username) {
		List<Long> retVal = new ArrayList<Long>();
		
		for(Reservation r : this.reservations) {
			if(r.getGuest().getUsername().equals(username) && !retVal.contains(r.getApartment().getId())) {
				retVal.add(r.getApartment().getId());
			}
		}
		return retVal;
	}
	
	public List<Reservation> getApartmentReservations(Long id) {
		List<Reservation> retVal = new ArrayList<Reservation>();
		System.out.println(id);
		for(Reservation r : this.reservations) {
			if(r.getApartment().getId()==id) {
				retVal.add(r);
			}
		}
		return retVal;
	}
	
	public List<Reservation> getAllReservations() {
		return reservations;
	}
	
	@SuppressWarnings("unchecked")
	public void addReservation(Reservation reservation) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/reservations.json";
		try {
			
			JSONArray reservationsArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject reservationJSON = new JSONObject();
			reservationJSON.put("startDate", reservation.getStartDate());
			reservationJSON.put("guest", reservation.getGuest().getUsername());
			reservationJSON.put("apartmentId",reservation.getApartment().getId());
			reservationJSON.put("status", reservation.getStatus().toString());
			reservationJSON.put("messageWhenBooking", reservation.getMessageWhenBooking());
			reservationJSON.put("totalPrice",reservation.getTotalPrice());
			reservationJSON.put("numberOfOvernightsStay",reservation.getNumberOfOvernightsStay());
			reservationJSON.put("id",reservation.getId());
			
			reservations.add(reservation);
			reservationsArray.add(reservationJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(reservationsArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
	}
	
	public void updateReservation() {
		
	}
	
	public void deleteReservation() {
		
	}
	
	public void loadReservations() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/reservations.json";
		System.out.println("Prava putanja ucitavanja: " + fullPath);
		try {
			System.out.println(fullPath);
			JSONArray reservations = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : reservations) {
				JSONObject reservationJSON = (JSONObject) o;
				
				Long apartmentId = (Long) reservationJSON.get("apartmentId");
				Apartment apartment = new Apartment();
				apartment.setId(apartmentId);

				String guestUusername = (String) reservationJSON.get("guest");
				Guest guest = new Guest();
				guest.setUsername(guestUusername);
				
				String statusStr = (String) reservationJSON.get("status");
				ReservationStatus status = ReservationStatus.valueOf(statusStr);
				
				String messageWhenBooking = (String) reservationJSON.get("messageWhenBooking");
				
				Long id = (Long) reservationJSON.get("id");

				int totalPrice = (int) (long) reservationJSON.get("totalPrice");
				int numberOfOvernightsStay = (int) (long)  reservationJSON.get("numberOfOvernightsStay");
				LocalDate startDate = LocalDate.parse((String) reservationJSON.get("startDate"));
				
				Reservation reservation = new Reservation(apartment,startDate,numberOfOvernightsStay,totalPrice,messageWhenBooking,guest,status,id);
				
				this.reservations.add(reservation);
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
