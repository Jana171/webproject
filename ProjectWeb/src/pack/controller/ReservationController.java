package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.model.User;
import pack.service.ReservationService;

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
		return this.getReservationService().getApartmentGuestHistory((User)request.getSession().getAttribute("user"));
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
