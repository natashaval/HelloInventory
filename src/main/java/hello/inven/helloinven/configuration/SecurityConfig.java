package hello.inven.helloinven.configuration;

import hello.inven.helloinven.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CustomAuthenticationHandler successHandler;

    // Configure allowed path based on Role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(myUserDetailsService);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**")
                .permitAll()
        // configure swagger
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html")
                .permitAll();

        http
                .authorizeRequests()
                .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                .antMatchers("/user/**")
                    .hasAnyAuthority("EMPLOYEE", "MANAGER")
                .antMatchers("/manager/**")
                    .hasAuthority("MANAGER")
                .antMatchers("/clerk/**")
                    .hasAuthority("CLERK")
                    .anyRequest()
                    .authenticated()
                .antMatchers("/","/home","/profile")
                    .permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                    .permitAll()

                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll();

        http.exceptionHandling().accessDeniedPage("/403");
    }

    // encode strong password by BCryptPassword
    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    // configure authBuilder with userDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
