package at.reilaender.dezsys9;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry Point of the application
 *
 * @author Paul Kalauner 5BHIT
 * @version 20160212.1
 */
@SpringBootApplication
public class Application {
    /**
     * Main method
     * @param args none
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}