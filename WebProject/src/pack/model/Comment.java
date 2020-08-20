package pack.model;

public class Comment {
	
	private Guest guest;
	private Apartment apartment;
	private String text;
	private int rate;
	private boolean deleted = false;
	
	public Comment() {}
	
	public Comment(Guest guest, Apartment apartment, String text, int rate) {
		this.guest = guest;
		this.apartment = apartment;
		this.text = text;
		this.rate = rate;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
