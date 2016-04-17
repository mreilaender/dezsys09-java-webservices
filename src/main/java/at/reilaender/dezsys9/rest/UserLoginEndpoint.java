package at.reilaender.dezsys9.rest;

import at.reilaender.dezsys9.db.User;
import at.reilaender.dezsys9.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Mapping for path {@code /login}
 *
 * @author Paul Kalauner 5BHIT
 * @version 20160212.1
 */
@Named
@Path("/login")
@Produces({MediaType.APPLICATION_JSON})
public class UserLoginEndpoint {

    @Inject
    private UserRepository userRepository;

    /**
     * Checks if login data is valid for the given user
     *
     * @param user user
     * @return response
     */
    @POST
    public Response post(User user) {
        if (user.getEmail() == null)
            return Response.status(Response.Status.FORBIDDEN).entity("No email specified!").type(MediaType.TEXT_PLAIN).build();

        User userFromDb = userRepository.findOne(user.getEmail());
        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
            return Response.status(Response.Status.OK).entity("Welcome " + userFromDb.getName() + "!").type(MediaType.TEXT_PLAIN).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid account data!").type(MediaType.TEXT_PLAIN).build();
        }
    }
}