package pack.service;

import pack.dao.CommentDAO;

public class CommentService {
	
	public CommentDAO commentDAO;
	
	public CommentService(String path) {
		this.commentDAO = new CommentDAO(path);
	}
	
	
}
