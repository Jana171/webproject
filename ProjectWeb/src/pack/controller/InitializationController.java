package pack.controller;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.service.ApartmentService;
import pack.service.CommentService;
import pack.service.ReservationService;
import pack.service.UserService;

@Path("/init")
public class InitializationController {
	
	@Context
	ServletContext ctx;

	
	@GET
	@Path("/load")
	@Produces(MediaType.TEXT_PLAIN)
	public String load() {
		this.getReservationService();
		return "HAHA";
		
	}
	
	
	private UserService getUserService() {	
		
		UserService userService = (UserService) ctx.getAttribute("userService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath(""));
			ctx.setAttribute("userService", userService);
		}
		
		return userService;
	}
	
	private CommentService getCommentService() {	
		
		CommentService commentService = (CommentService) ctx.getAttribute("commentService");
		if (commentService == null) {
			commentService = new CommentService(ctx.getRealPath(""));
			ctx.setAttribute("commentService", commentService);
		}
		
		return commentService;
	}
	
	private ApartmentService getApartmentService() {	
		
		ApartmentService apartmentService = (ApartmentService) ctx.getAttribute("apartmentService");
		if (apartmentService == null) {
			apartmentService = new ApartmentService(ctx.getRealPath(""));
			ctx.setAttribute("apartmentService", apartmentService);
		}
		
		return apartmentService;
	}
	
	private ReservationService getReservationService() {	
		
		ReservationService reservationService = (ReservationService) ctx.getAttribute("reservationService");
		if (reservationService == null) {
			reservationService = new ReservationService(ctx.getRealPath(""));
			ctx.setAttribute("reservationService", reservationService);
		}
		
		return reservationService;
	}
}
