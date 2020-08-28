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

import pack.dto.LoginDTO;
import pack.model.User;
import pack.service.UserService;

@Path("/users")
public class UserController {

	//@Context
	//HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld() {
		return "Hello World!";
	}
	
	

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return this.getUserService().getAllUsers();
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(User user) {
		UserService userService = getUserService();
		if(!userService.checkIfUserExists(user))
			userService.addUser(user);
		
		return "Successfull!";
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User login(LoginDTO loginDTO, @Context HttpServletRequest request) {
		UserService userService = getUserService();
		User user = userService.getUser(loginDTO.getUsername());
		
		if(user != null && user.getPassword().equals(loginDTO.getPassword())) {
			request.getSession().setAttribute("user",user);
			return user;
		} else {
			return null;
		}
		
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public String logout(@Context HttpServletRequest request) {
		request.getSession().setAttribute("user",null);
		
		return "Successfull!";
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
