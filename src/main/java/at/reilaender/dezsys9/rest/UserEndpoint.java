package at.reilaender.dezsys9.rest;

import at.reilaender.dezsys9.db.User;
import at.reilaender.dezsys9.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Mapping for path {@code /user}
 *
 * @author Paul Kalauner 5BHIT
 * @version 20160212.1
 */
@Named
@Path("/user")
@Produces({MediaType.APPLICATION_JSON})
public class UserEndpoint {

    @Inject
    private UserRepository userRepository;

    /**
     * Gets the user with the given id (email)
     *
     * @param id email of the user
     * @return response
     */
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        User user = this.userRepository.findOne(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for id: " + id).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }
}