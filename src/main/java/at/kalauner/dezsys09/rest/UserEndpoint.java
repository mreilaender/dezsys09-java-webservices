package at.kalauner.dezsys09.rest;

import at.kalauner.dezsys09.db.User;
import at.kalauner.dezsys09.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Named
@Path("/user")
@Produces({MediaType.APPLICATION_JSON})
public class UserEndpoint {

    @Inject
    private UserRepository userRepository;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        User user = this.userRepository.findOne(id);

        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for id: " + id).build();
        }
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }
}