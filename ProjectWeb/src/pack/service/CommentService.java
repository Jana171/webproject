package pack.service;

import java.util.ArrayList;
import java.util.List;

import pack.dao.ApartmentDAO;
import pack.dao.CommentDAO;
import pack.enums.Role;
import pack.model.Apartment;
import pack.model.Comment;
import pack.model.User;

public class CommentService {
	
	public CommentDAO commentDAO;
	
	public CommentService(String path) {
		this.commentDAO = new CommentDAO(path);
	}
	
	public void addComment(Comment comment) {
		this.commentDAO.addComment(comment);
	}
	
	public List<Comment> getAllComments(User user, ApartmentDAO apartmentDAO) {
		
		if(user.getRole() == Role.ADMIN)
			return this.commentDAO.getAllComments();
		else if(user.getRole() == Role.HOST) {
			List<Comment> allComments = new ArrayList<Comment>();
			List<Apartment> apartments = apartmentDAO.getHostApartments(user.getUsername());
			for(Apartment a : apartments) {
				allComments.addAll(a.getComments());
				return allComments;
			}
		}
		
		return null;
	}
	
	public Comment changeVisibilityOfComment(Comment comment) {
		List<Comment> allComments = this.commentDAO.getAllComments();
		for(Comment c: allComments) {
			if(c.getApartment().getId() == comment.getApartment().getId() && c.getGuest().getUsername() == comment.getGuest().getUsername()) {
				c.setSelected(!c.isSelected());
				System.out.println(c.isDeleted());
				return c;
			}
		}
		
		return null;
		
	}
	
}
