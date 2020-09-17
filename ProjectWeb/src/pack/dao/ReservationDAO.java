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
import pack.enums.Role;
import pack.model.Apartment;
import pack.model.Guest;
import pack.model.Reservation;
import pack.model.User;
import pack.service.UserService;

public class ReservationDAO {
	
	private String path;
	private static List<Reservation> reservations = new ArrayList<Reservation>();
	
	public ReservationDAO(String path) {
		this.path = path;
		this.loadReservations();
	}
	
	//refaktorisi posebna metoda za json obj pa izdvoj save u posebnu a add kao poziv te 2
	
	public List<Reservation> getGuestReservations(String username) {
		List<Reservation> retVal = new ArrayList<Reservation>();
		
		for(Reservation r : ReservationDAO.reservations) {
			if(r.getGuest().getUsername().equals(username)) {
				retVal.add(r);
			}
		}
		return retVal;
	}
	
	public Reservation getReservation(Long id) {
		
		for(Reservation r : ReservationDAO.reservations) {
			if(r.getId() == id) {
				return r;
			}
		}
		System.out.println("NOT FOUND!");
		return null;
	}
	
	
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
	public Reservation addReservation(Reservation reservation) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/reservations.json";
		try {
			
			JSONArray reservationsArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject reservationJSON = new JSONObject();
			
			
			if(reservation.getId() == null) {
				int newId = reservations.size() + 1;
				reservation.setId((long) newId);
			}
			
			
			reservationJSON.put("startDate", reservation.getStartDate());
			reservationJSON.put("guest", reservation.getGuest().getUsername());
			reservationJSON.put("apartmentId",reservation.getApartment().getId());
			reservationJSON.put("status", reservation.getStatus().toString());
			reservationJSON.put("messageWhenBooking", reservation.getMessageWhenBooking());
			reservationJSON.put("totalPrice",reservation.getTotalPrice());
			reservationJSON.put("numberOfOvernightsStay",reservation.getNumberOfOvernightsStay());
			reservationJSON.put("id",reservation.getId());
			
			//Random random = new Random();
			//int id = random.nextInt();
	
			int flag = -1;
			for (int i=0; i< reservations.size(); i++) {
				if(reservations.get(i).getId() == reservation.getId()) {
					flag = i;
				}
			}
			if(flag != -1) {
				reservations.remove(flag);
			}
			reservations.add(reservation);
			reservationsArray.add(reservationJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(reservationsArray.toJSONString());
            file.close();
            
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Parse exception!");
			e.printStackTrace();
		}
		return reservation;
		
	}

	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void changeReservation(Reservation reservation) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/reservations.json";
		try {
			
			JSONArray reservationsArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject reservationJSON = new JSONObject();
			
			
			if(reservation.getId() == null) {
				int newId = reservations.size() + 1;
				reservation.setId((long) newId);
			}
			
			
			reservationJSON.put("startDate", reservation.getStartDate());
			reservationJSON.put("guest", reservation.getGuest().getUsername());
			reservationJSON.put("apartmentId",reservation.getApartment().getId());
			reservationJSON.put("status", reservation.getStatus().toString());
			reservationJSON.put("messageWhenBooking", reservation.getMessageWhenBooking());
			reservationJSON.put("totalPrice",reservation.getTotalPrice());
			reservationJSON.put("numberOfOvernightsStay",reservation.getNumberOfOvernightsStay());
			reservationJSON.put("id",reservation.getId());
			
			//Random random = new Random();
			//int id = random.nextInt();
	
			int flag = -1;
			for (int i=0; i< reservations.size(); i++) {
				if(reservations.get(i).getId() == reservation.getId()) {
					flag = i;
				}
			}
			if(flag != -1) {
				reservations.remove(flag);
			}
			reservations.add(reservation);
			reservationsArray.add(reservationJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(reservationsArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Parse exception!");
			e.printStackTrace();
		}
		
	}
	
	
	
	public Reservation changeStatusReservation(Reservation reservation, UserDAO userDAO, ApartmentDAO apartmentDAO) {
		Reservation r = this.getReservation(reservation.getId());
		r.setStatus(reservation.getStatus());
		
		for(User user : userDAO.getAllUsers()) {
			if(user.getRole() == Role.GUEST) {
				for(Reservation res: ((Guest)user).getReservations()) {
					if(res.getId() == reservation.getId()) {
						res.setStatus(reservation.getStatus());
					}
				}
			}
		}
		
		for(Apartment ap : apartmentDAO.getAllApartments()) {
			if(ap.getId() == r.getApartment().getId()) {
				for(Reservation res: ap.getReservations()) {
					if(res.getId() == reservation.getId()) {
						res.setStatus(reservation.getStatus());
					}
				}
			}
		}
		changeReservation(r);
		return r;
	}
	
	public boolean deleteReservation(int id, UserDAO userDAO, ApartmentDAO apartmentDAO) {
		boolean flag = false;
		Reservation a = this.getReservation((long) id);
		for(int i = 0 ; i < this.reservations.size();i++) {
			if(this.reservations.get(i).getId() == id) {
				this.reservations.remove(i);
				flag = true;
				break;
			}
		}
		
		
		for(User user : userDAO.getAllUsers()) {
			if(user.getRole() == Role.GUEST) {
				for(int i = 0 ; i < ((Guest)user).getReservations().size();i++ ) {
					if(((Guest)user).getReservations().get(i).getId() == id) {
						((Guest)user).getReservations().remove(i);
					}
				}
			}
		}
		
		/*
		 * for(Apartment ap : apartmentDAO.getAllApartments()) { if(ap.getId() ==
		 * reservation.getApartment().getId()) { for(Reservation res:
		 * ap.getReservations()) { if(res.getId() == reservation.getId()) {
		 * res.setStatus(reservation.getStatus()); } } } }
		 */
		
		return flag;
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
				
				UserService us = new UserService();
				
				
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
			System.out.println("Parse exception");
			e.printStackTrace();
		}
		
	}

}
