package pack.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import pack.model.Amenity;
import pack.service.AmenityService;
import pack.service.ApartmentService;
@Path("/amenities")
public class AmenitiesController {
	

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	

	
	
	private AmenityService getAmenityService() {	
		
		AmenityService amenityService = (AmenityService) ctx.getAttribute("amenityService");
		if (amenityService == null) {
			amenityService = new AmenityService(ctx.getRealPath(""));
			ctx.setAttribute("amenityService", amenityService);
		}
		
		return amenityService;
	}
		
	private ApartmentService getApartmentService() {	
		
		ApartmentService apartmentService = (ApartmentService) ctx.getAttribute("apartmentService");
		if (apartmentService == null) {
			apartmentService = new ApartmentService(ctx.getRealPath(""));
			ctx.setAttribute("apartmentService", apartmentService);
		}
		
		return apartmentService;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Amenity> getAllAmenities() {
		return this.getAmenityService().getAllAmenities();
	}
	
	@POST
	public boolean addAmenity(Amenity amenity) {
		return this.getAmenityService().addAmenity(amenity);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Amenity updateAmenity(Amenity amenity) {
		return this.getAmenityService().updateAmenity(amenity,getApartmentService().apartmentDAO);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean deleteAmenity(@PathParam("id") int id) {
		return this.getAmenityService().deleteAmenity(id,getApartmentService().apartmentDAO);
	}
	
}
