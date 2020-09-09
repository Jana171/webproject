package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.model.Comment;
import pack.model.Reservation;
import pack.model.User;
import pack.service.ApartmentService;
import pack.service.CommentService;
import pack.service.ReservationService;
import pack.service.UserService;

@Path("/reservations")
public class ReservationController {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	
	@GET
	@Path("/apartment-guest-history")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getApartmentGuestHistory() {
		return this.getReservationService().getApartmentGuestHistory((User)request.getSession().getAttribute("user"),getApartmentService().apartmentDAO);
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reservation> getAllReservations() {
		return this.getReservationService().getAllReservations((User)request.getSession().getAttribute("user"));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String addReservation(Reservation reservation) {
		//(User)request.getSession().getAttribute("user")
		this.getReservationService().addReservation(reservation);
		this.getApartmentService().addReservationToApartment(reservation);
		this.getUserService().addReservationToGuest(reservation);
		return "<h3>Successfull!</h3>";
	}
	
	@POST
	@Path("/comments/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String addComment(@PathParam("id") Long id, Comment comment) {
		boolean possibleToLeaveComment = this.getReservationService().checkIfPossibleToLeaveComment(id);
		if(possibleToLeaveComment) {
			this.getCommentService().addComment(comment);
			this.getApartmentService().addCommentToApartment(comment);
			return "<h3>Successfull!</h3>";			
		} else {
			return "<h3>Not possible!</h3>";
		}

	}
	
	@GET
	@Path("/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllComments() {
		User user = (User) request.getSession().getAttribute("user");
		return this.getCommentService().getAllComments(user, this.getApartmentService().apartmentDAO);
	}
	
	@PUT
	@Path("/comments/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Comment changeVisibilityOfComment(Comment comment) {
		return this.getCommentService().changeVisibilityOfComment(comment);
	}
	
	
	private ReservationService getReservationService() {	
		
		ReservationService reservationService = (ReservationService) ctx.getAttribute("reservationService");
		if (reservationService == null) {
			reservationService = new ReservationService(ctx.getRealPath(""));
			ctx.setAttribute("reservationService", reservationService);
		}
		
		return reservationService;
	}
	
	private UserService getUserService() {	
		
		UserService userService = (UserService) ctx.getAttribute("userService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath(""));
			ctx.setAttribute("userService", userService);
		}
		
		return userService;
	}
	
	private ApartmentService getApartmentService() {	
		
		ApartmentService apartmentService = (ApartmentService) ctx.getAttribute("apartmentService");
		if (apartmentService == null) {
			apartmentService = new ApartmentService(ctx.getRealPath(""));
			ctx.setAttribute("apartmentService", apartmentService);
		}
		
		return apartmentService;
	}
	
	private CommentService getCommentService() {	
		
		CommentService commentService = (CommentService) ctx.getAttribute("commentService");
		if (commentService == null) {
			commentService = new CommentService(ctx.getRealPath(""));
			ctx.setAttribute("commentService", commentService);
		}
		
		return commentService;
	}
	

}
