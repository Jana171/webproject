package model;

import java.time.LocalDate;

import enums.ReservationStatus;

public class Reservation {

	private Apartment apartment;
	private LocalDate startDate;
	private int numberOfOvernightsStay = 1;
	private int totalPrice;
	private String messageWhenBooking;
	private Guest guest;
	private ReservationStatus status;
	
	public Reservation() {}
	
	public Reservation(Apartment apartment, LocalDate startDate, int numberOfOvernightsStay, int totalPrice,
			String messsageWhenBooking, Guest guest, ReservationStatus status) {
		
		this.apartment = apartment;
		this.startDate = startDate;
		this.numberOfOvernightsStay = numberOfOvernightsStay;
		this.totalPrice = totalPrice;
		this.messageWhenBooking = messsageWhenBooking;
		this.guest = guest;
		this.status = status;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfOvernightsStay() {
		return numberOfOvernightsStay;
	}

	public void setNumberOfOvernightsStay(int numberOfOvernightsStay) {
		this.numberOfOvernightsStay = numberOfOvernightsStay;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMessageWhenBooking() {
		return messageWhenBooking;
	}

	public void setMessageWhenBooking(String messageWhenBooking) {
		this.messageWhenBooking = messageWhenBooking;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	
	
	
}
