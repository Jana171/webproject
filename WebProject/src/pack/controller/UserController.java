package pack.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserController {
	
	/*@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	*/
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		System.out.println("USAO");
		return "mmm";
	}
	
	/*@GET
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
*/
}
