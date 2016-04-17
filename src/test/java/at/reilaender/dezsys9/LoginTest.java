package at.reilaender.dezsys9;

import at.reilaender.dezsys9.db.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author mreilaender
 * @version 17.04.2016
 */
public class LoginTest {
    private RestTemplate restTemplate;
    private static String protocol = "http";
    private static String host = "localhost";
    private static int port = 12345;
    private static String url = String.format("%s://%s:%d", protocol, host, port);

    @Before
    public void setup() {
        this.restTemplate = new RestTemplate();
    }

    @BeforeClass
    public static void beforeClass() {
        String current = System.getProperty("user.dir");
        String embeddedDatabaseFilename = "UserDB_test.mv.db";
        if (new File(current + "\\" + embeddedDatabaseFilename).delete())
            Application.main(new String[]{"--spring.profiles.active=test", "--server.port=" + port});
        else
            System.out.println(String.format("Could not delete or find embedded database file %s", embeddedDatabaseFilename));
    }

    @Test
    public void testUserSuccessfullyLoggedIn() {
        User user = new User("user1@test.at", "user1", "secret");
        this.restTemplate.postForEntity(url + "/register", user, String.class);
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Welcome " + user.getName() + "!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.OK, e.getStatusCode());
        }
    }

    @Test
    public void testUserWrongEmail() {
        User user = new User("", "user1", "secret");
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Invalid account data!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }

    @Test
    public void testUserWrongPassword() {
        User user = new User("", "user1", "secret");
        this.restTemplate.postForEntity(url + "/register", user, String.class);
        user.setPassword("other_secret");
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Invalid account data!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }

    @Test
    public void testUserNullEmail() {
        User user = new User(null, "user1", "secret");
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("No email specified!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }

    @Test
    public void testUserWithNullPassword() {
        User user = new User("user1@test.at", "user1", null);
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Invalid account data!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }

    @Test
    public void testUserWithNullName() {
        User user = new User("user1@test.at", null, "secret");
        try {
            this.restTemplate.postForEntity(url + "/login", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Invalid account data!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }
}
