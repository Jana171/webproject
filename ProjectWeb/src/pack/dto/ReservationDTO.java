package pack.dto;

public class ReservationDTO {

	private String startDate;
	private int numberOfOvernightsStay;
	private String messageWhenBooking;
	private Long apartmentId;
	
	
	public ReservationDTO() {
		
	}


	public ReservationDTO(String startDate, int numberOfOvernightsStay, String messageWhenBooking, Long apartmentId) {
		super();
		this.startDate = startDate;
		this.numberOfOvernightsStay = numberOfOvernightsStay;
		this.messageWhenBooking = messageWhenBooking;
		this.apartmentId = apartmentId;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public int getNumberOfOvernightsStay() {
		return numberOfOvernightsStay;
	}


	public void setNumberOfOvernightsStay(int numberOfOvernightsStay) {
		this.numberOfOvernightsStay = numberOfOvernightsStay;
	}


	public String getMessageWhenBooking() {
		return messageWhenBooking;
	}


	public void setMessageWhenBooking(String messageWhenBooking) {
		this.messageWhenBooking = messageWhenBooking;
	}


	public Long getApartmentId() {
		return apartmentId;
	}


	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}
	
	
}
