package hello.inven.helloinven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HelloinvenApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloinvenApplication.class, args);
	}
}
