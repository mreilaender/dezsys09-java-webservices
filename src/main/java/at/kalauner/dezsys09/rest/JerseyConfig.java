package at.kalauner.dezsys09.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Named;

/**
 * Created by Paul on 12.02.2016.
 */
@Named
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        this.register(UserEndpoint.class);
        this.register(UserRegisterEndpoint.class);
        this.register(UserLoginEndpoint.class);
        this.register(JacksonFeature.class);
    }
}