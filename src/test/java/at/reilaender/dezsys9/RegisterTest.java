package at.reilaender.dezsys9;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import at.reilaender.dezsys9.db.User;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author mreilaender
 * @version 15.04.2016
 */
public class RegisterTest {

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
        if(new File(current + "\\" + embeddedDatabaseFilename).delete())
            Application.main(new String[]{"--spring.profiles.active=test", "--server.port=" + port});
        else
            System.out.println(String.format("Could not delete or find embedded database file %s", embeddedDatabaseFilename));
    }

    /**
     * Ends in an Exception on the spring server
        * Solved by setting content type to json
     */
    public void testWithApacheClient() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URI uri = new URIBuilder()
                    .setScheme(protocol)
                    .setHost(host)
                    .setPort(port)
                    .setPath("/register")
                    .build();
            HttpPost post = new HttpPost(uri);
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("name", "test"));
            nvps.add(new BasicNameValuePair("email", "test"));
            nvps.add(new BasicNameValuePair("password", "test"));
            post.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(post);
            response.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("successfully executed Post request");
    }

    @Test
    public void testUserAlreadyExists() {
        User user = new User("user1@test.at", "user1", "secret");
        this.restTemplate.postForEntity(url + "/register", user, String.class);
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("User " + user.getEmail() + " already exists!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testUserWithNullEmail() {
        User user = new User(null, "user1", "secret");
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testUserWithNullName() {
        User user = new User("user1@test.at", null, "secret");
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testUserWithNullPassword() {
        User user = new User("user1@test.at", "user1", null);
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testBlankUser() {
        User user = new User();
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testNullObject() {
        User user = null;
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void testUserEmptyEmailString() {
        User user = new User("", "user1", null);
        try {
            this.restTemplate.postForEntity(url + "/register", user, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals("Missing parameters!", e.getResponseBodyAsString());
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
