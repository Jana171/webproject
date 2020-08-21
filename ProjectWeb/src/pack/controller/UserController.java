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
		return "Hello World!";
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		System.out.println("USAO");
		return this.getUserService().getAllUsers();
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
