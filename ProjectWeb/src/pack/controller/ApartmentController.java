package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.dto.ApartmentDTO;
import pack.model.Apartment;
import pack.model.Host;
import pack.model.User;
import pack.service.ApartmentService;
import pack.service.UserService;

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
	
	//zavisno od uloge ulogovanog vratice razlicite apartmane
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addApartment(ApartmentDTO dto) {
		UserService userService = (UserService) ctx.getAttribute("userService");
		ApartmentService apartmentService = getApartmentService();
		return apartmentService.addApartment(dto,(Host) request.getSession().getAttribute("user"), getUserService().amenityDAO);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment updateApartment(Apartment apartment) {
		ApartmentService apartmentService = getApartmentService();
		return apartmentService.updateApartment(apartment,getUserService().userDAO);
	}
	
	@GET
	@Path("/{id}")
	public Apartment getApartment(@PathParam("id") int id) {
		return this.getApartmentService().getApartment(id);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean deleteApartment(@PathParam("id") int id) {
		return this.getApartmentService().deleteApartment(id,getUserService().userDAO);
	}
	


	
	private ApartmentService getApartmentService() {	
		
		ApartmentService apartmentService = (ApartmentService) ctx.getAttribute("apartmentService");
		if (apartmentService == null) {
			apartmentService = new ApartmentService(ctx.getRealPath(""));
			ctx.setAttribute("apartmentService", apartmentService);
		}
		
		return apartmentService;
	}
	
	private UserService getUserService() {	
		
		UserService userService = (UserService) ctx.getAttribute("userService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath(""));
			ctx.setAttribute("userService", userService);
		}
		
		return userService;
	}
}
