package at.kalauner.dezsys09.rest;

import at.kalauner.dezsys09.db.User;
import at.kalauner.dezsys09.db.UserRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Named
@Path("/register")
@Produces({MediaType.APPLICATION_JSON})
public class UserRegisterEndpoint {

    @Inject
    private UserRepository userRepository;

    @POST
    public Response post(User user) {
        try {
            if (!userRepository.exists(user.getEmail())) {
                User savedUser = this.userRepository.save(user);
                return Response.status(Response.Status.CREATED).entity("User " + savedUser.getEmail() + " saved!").type(MediaType.TEXT_PLAIN).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("User " + user.getEmail() + " already exists!").type(MediaType.TEXT_PLAIN).build();
            }
        } catch (TransactionSystemException | InvalidDataAccessApiUsageException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameters!").type(MediaType.TEXT_PLAIN).build();
        }
    }
}