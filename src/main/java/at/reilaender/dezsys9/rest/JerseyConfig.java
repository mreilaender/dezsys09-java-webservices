package at.reilaender.dezsys9.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Named;

/**
 * JerseyConfig
 *
 * @author Paul Kalauner 5BHIT
 * @version 20160212.1
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