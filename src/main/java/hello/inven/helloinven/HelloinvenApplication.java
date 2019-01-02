package hello.inven.helloinven;

import hello.inven.helloinven.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
@Import({ApplicationConfig.class})
public class HelloinvenApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloinvenApplication.class, args);
	}
}
