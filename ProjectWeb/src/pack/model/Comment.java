package pack.model;

public class Comment {
	
	private Guest guest;
	private Apartment apartment;
	private String text;
	private int rate;
	private boolean deleted = false;
	private boolean selected = true;
	
	public Comment() {}
	
	public Comment(Guest guest, Apartment apartment, String text, int rate, boolean selected) {
		this.guest = guest;
		this.apartment = apartment;
		this.text = text;
		this.rate = rate;
		this.selected = selected;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	

}
