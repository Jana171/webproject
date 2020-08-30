package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.model.Apartment;
import pack.model.User;
import pack.service.ApartmentService;

@Path("/apartments")
public class ApartmentController {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	
	//zavisno od uloge ulogovanog vratice razlicite apartmane
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Apartment> getAllApartments() {
		return this.getApartmentService().getAllApartmentsByUserRole((User) request.getSession().getAttribute("user"));
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
