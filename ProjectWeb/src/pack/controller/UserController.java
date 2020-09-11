package pack.controller;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response;

import pack.dto.LoginDTO;
import pack.dto.RegisterDTO;
import pack.enums.Role;
import pack.model.Admin;
import pack.model.Amenity;
import pack.model.Host;
import pack.model.User;
import pack.service.AmenityService;
import pack.service.ApartmentService;
import pack.service.UserService;

@Path("/users")
public class UserController {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld() {
		if(request.getSession().getAttribute("user") == null) {
			return "Not logged";
			
		} else {
			User user = (User) request.getSession().getAttribute("user");
			return "Logged " + user.getUsername();
		}
	}
	
	@GET
	@Path("/role")
	@Produces(MediaType.TEXT_PLAIN)
	public String role() {
		if(request.getSession().getAttribute("user") == null) {
			return "";
			
		} else {
			User user = (User) request.getSession().getAttribute("user");
			return user.getRole().toString();
		}
	}
	
	

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		List<User> retVal = new ArrayList<User>();
		
		User user = (User) request.getSession().getAttribute("user");
		if(user.getRole() == Role.ADMIN) {
			retVal = this.getUserService().getAllUsers();
		} else if (user.getRole() == Role.HOST) {
			Host host = (Host) request.getSession().getAttribute("user");
			UserService userService = getUserService();
			retVal = userService.getMyApartmentsGuestHistory(host);
			
		}
		
		return retVal;
	}
	
	@GET
	@Path("/admins")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Admin> getAllAdmins() {
		return this.getUserService().getAllAdmins();
	}
	
	@GET
	@Path("/hosts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Host> getAllHosts() {
		return this.getUserService().getAllHosts();
	}
	


	
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(RegisterDTO registerDTO) {
		UserService userService = getUserService();
		if(!userService.checkIfUserExists(registerDTO.getUsername())) {
			System.out.println("BBB");
			userService.register(registerDTO);
			return Response.ok(registerDTO, MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println("CCC");
			return Response.status(Response.Status.NOT_FOUND).entity("Username is already taken!").build();
		}
			
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginDTO loginDTO) {
		
		UserService userService = getUserService();
		User user = userService.getUser(loginDTO.getUsername());
		
		if(user != null && user.getPassword().equals(loginDTO.getPassword())) {
			System.out.println(user.getUsername());
			request.getSession().setAttribute("user",user);
			return Response.ok(user, MediaType.APPLICATION_JSON).build();
//			return user;
		} else {
			System.out.println("AAA");
			return Response.status(Response.Status.NOT_FOUND).entity("Credentials are inncorect!").build();
//			return null;
		}
		
	}
	
	@GET
	@Path("/loggedHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Host getLoggedHost() {
		return (Host) request.getSession().getAttribute("user");
	}
	
	@GET
	@Path("/logged")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLoggedUser() {
		return (User) request.getSession().getAttribute("user");
	}
	
	@POST
	@Path("/logout")
	public Response logout() {
		if(request.getSession().getAttribute("user") != null) {
			request.getSession().setAttribute("user",null);
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("There are no currently logged in users!").build();
		}
	}
	
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User update(User user) {
		UserService userService = getUserService();
		User retVal = userService.updateUser(user);
		
		return retVal;
	}
	
	/*@GET
	@Path("/my-apartments-guest-history")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Guest> myApartmentsGuestHistory() {
		Host host = (Host) request.getSession().getAttribute("user");
		UserService userService = getUserService();
		List<Guest> retVal = userService.getMyApartmentsGuestHistory(host);
		
		return retVal;
	}*/

	
	private UserService getUserService() {	
		
		UserService userService = (UserService) ctx.getAttribute("userService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath(""));
			ctx.setAttribute("userService", userService);
		}
		
		return userService;
	}
	
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
	@Path("/amenities")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Amenity> getAllAmenities() {
		return this.getAmenityService().getAllAmenities();
	}
	
	@POST
	@Path("/amenities")
	public boolean addAmenity(Amenity amenity) {
		return this.getAmenityService().addAmenity(amenity);
	}
	
	@PUT
	@Path("/amenities")
	@Produces(MediaType.APPLICATION_JSON)
	public Amenity updateAmenity(Amenity amenity) {
		return this.getAmenityService().updateAmenity(amenity,getApartmentService().apartmentDAO);
	}
	
	@DELETE
	@Path("/amenities/{id}")
	public void deleteAmenity(@PathParam("id") int id) {
		this.getAmenityService().deleteAmenity(id,getApartmentService().apartmentDAO);
	}
	
}
