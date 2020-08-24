package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pack.model.Comment;

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
	
	@SuppressWarnings("unchecked")
	public void addComment(Comment comment) {
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
			
			comments.add(comment);
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
		try {
			
			JSONArray comments = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : comments) {
				JSONObject commentJSON = (JSONObject) o;
				
				String guestUusername = (String) commentJSON.get("guest");
				int apartmentId = (int) commentJSON.get("apartmentId");
				String text = (String) commentJSON.get("text");
				int rate = (int) commentJSON.get("rate");
				boolean deleted = (Boolean) commentJSON.get("deleted");
				
				if(deleted)
					continue;
				
				//preko DAO
				Comment comment = new Comment();
				
				
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
