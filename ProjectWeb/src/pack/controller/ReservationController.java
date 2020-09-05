package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.model.Reservation;
import pack.model.User;
import pack.service.ApartmentService;
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
	

}
