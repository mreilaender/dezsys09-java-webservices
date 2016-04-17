package at.reilaender.dezsys9;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.web.client.RestTemplate;

import java.io.File;

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
}
