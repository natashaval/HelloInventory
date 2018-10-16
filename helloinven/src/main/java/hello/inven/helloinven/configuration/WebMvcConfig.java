package hello.inven.helloinven.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    //karena dibuat Singleton (why Bean?)
    // bedanya sama static method dan bean?
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    // Strength of BCrypt = 10 (default)
    // https://www.browserling.com/tools/bcrypt

    /*
//    https://www.baeldung.com/maven-webjars
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
//        https://stackoverflow.com/questions/32685819/spring-boot-webjars-unable-to-load-javascript-library-through-webjar
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    */

}
