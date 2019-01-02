package hello.inven.helloinven.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//https://stackoverflow.com/questions/41250177/getting-at-least-one-jpa-metamodel-must-be-present-with-webmvctest
@Configuration
//@EnableJpaRepositories(basePackages = "hello.inven.helloinven.repository")
@EnableJpaAuditing
public class ApplicationConfig {
}
