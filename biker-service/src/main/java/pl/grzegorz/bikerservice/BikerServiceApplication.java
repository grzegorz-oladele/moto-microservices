package pl.grzegorz.bikerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
public class BikerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikerServiceApplication.class, args);
	}

}
