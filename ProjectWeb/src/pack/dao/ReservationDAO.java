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
import pack.model.Reservation;

public class ReservationDAO {
	
	private String path;
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
	public ReservationDAO(String path) {
		this.path = path;
		this.loadReservations();
	}
	
	//refaktorisi posebna metoda za json obj pa izdvoj save u posebnu a add kao poziv te 2
	
	public List<Reservation> getUserReservations(String username) {
		List<Reservation> retVal = new ArrayList<Reservation>();
		
		for(Reservation r : this.reservations) {
			if(r.getGuest().getUsername().equals(username)) {
				retVal.add(r);
			}
		}
		return retVal;
	}
	
	public List<Reservation> getApartmentReservations(Long id) {
		List<Reservation> retVal = new ArrayList<Reservation>();
		
		for(Reservation r : this.reservations) {
			if(r.getApartment().getId()==id) {
				retVal.add(r);
			}
		}
		return retVal;
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
		try {
			
			JSONArray reservations = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : reservations) {
				JSONObject reservationJSON = (JSONObject) o;
				
				int apartmentId = (int) reservationJSON.get("apartmentId");
				String guestUusername = (String) reservationJSON.get("guest");
				
				String statusStr = (String) reservationJSON.get("status");
				ReservationStatus status = ReservationStatus.valueOf(statusStr);
				
				String messageWhenBooking = (String) reservationJSON.get("messageWhenBooking");

				int totalPrice = (int) reservationJSON.get("totalPrice");
				String numberOfOvernightsStay = (String) reservationJSON.get("numberOfOvernightsStay");
				LocalDate startDate = (LocalDate) reservationJSON.get("startDate");
				
				//preko DAO
				Reservation reservation = new Reservation();
				
				
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
