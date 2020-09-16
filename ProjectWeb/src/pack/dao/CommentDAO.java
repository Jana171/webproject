package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pack.model.Apartment;
import pack.model.Comment;
import pack.model.Guest;

public class CommentDAO {

	private String path;
	private List<Comment> comments = new ArrayList<Comment>();
	
	public CommentDAO(String path) {
		this.path = path;
		this.loadComments();
	}
	
	public List<Comment> getUserComments(String username) {
		List<Comment> retVal = new ArrayList<Comment>();
		
		for(Comment c : this.comments) {
			if(c.getGuest().getUsername().equals(username)) {
				retVal.add(c);
			}
		}
		return retVal;
	}
	
	public List<Comment> getApartmentComments(Long id) {
		List<Comment> retVal = new ArrayList<Comment>();
		
		for(Comment c : this.comments) {
			if(c.getApartment().getId()==id) {
				retVal.add(c);
			}
		}
		return retVal;
	}
	
	public List<Comment> getAllComments() {
		return comments;
	}
	
	@SuppressWarnings("unchecked")
	public void addComment(Comment comment) {
		comments.add(comment);
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/comments.json";
		try {
			
			JSONArray commentsArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject commentJSON = new JSONObject();
			commentJSON.put("guest", comment.getGuest().getUsername());
			commentJSON.put("apartmentId",comment.getApartment().getId());
			commentJSON.put("text", comment.getText());
			commentJSON.put("rate", comment.getRate());
			commentJSON.put("deleted",comment.isDeleted());
			commentJSON.put("selected",comment.isSelected());
			
			
			commentsArray.add(commentJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(commentsArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
	}
	
	public void updateComment() {
		
	}
	
	public void deleteComment() {
		
	}
	
	public void loadComments() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/comments.json";
		System.out.println("Prava putanja ucitavanja: " + fullPath);
		try {
			
			JSONArray comments = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : comments) {
				JSONObject commentJSON = (JSONObject) o;
				
				String guestUsername = (String) commentJSON.get("guest");
				Guest guest = new Guest();
				guest.setUsername(guestUsername);
				
				Long apartmentId = (Long) commentJSON.get("apartmentId");
				Apartment apartment = new Apartment();
				apartment.setId(apartmentId);
				
				String text = (String) commentJSON.get("text");
				int rate = ((Long) commentJSON.get("rate")).intValue();
				boolean deleted = (Boolean) commentJSON.get("deleted");
				boolean selected = (Boolean) commentJSON.get("selected");
				
				if(deleted)
					continue;
				
				Comment comment = new Comment(guest,apartment,text,rate,selected);
				comment.setDeleted(deleted);
				
				this.comments.add(comment);
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
