package at.kalauner.dezsys09.rest;

import at.kalauner.dezsys09.db.User;
import at.kalauner.dezsys09.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Named
@Path("/login")
@Produces({MediaType.APPLICATION_JSON})
public class UserLoginEndpoint {

    @Inject
    private UserRepository userRepository;

    @POST
    public Response post(User user) {
        User userFromDb = userRepository.findOne(user.getEmail());
        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
            return Response.status(Response.Status.OK).entity("Welcome " + userFromDb.getName() + "!").build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid account data!").build();
        }
    }
}