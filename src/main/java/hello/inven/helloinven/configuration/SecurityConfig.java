package hello.inven.helloinven.configuration;

import hello.inven.helloinven.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

// https://grokonez.com/spring-framework/spring-security/use-spring-security-jdbc-authentication-mysql-spring-boot#4_Configure_Database

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // https://stackoverflow.com/questions/3087548/can-spring-security-use-preauthorize-on-spring-controllers-methods
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .jdbcAuthentication()
//                .usersByUsernameQuery("select username, password, emp_active from user where username=?")
//                .authoritiesByUsernameQuery("select u.username, r.role from user u join role r on u.role_id = r.role_id where u.username=?")
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Autowired
//    @Qualifier("userDetailsService")
//    UserDetailsService userDetailsService;
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CustomAuthenticationHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(myUserDetailsService);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**")
                .permitAll()
        // configure swagger
//        https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut
//        https://stackoverflow.com/questions/47425048/why-does-springfox-swagger2-ui-tell-me-unable-to-infer-base-url
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html")
                .permitAll();



        http
                .authorizeRequests()
                .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
//                    .anyRequest()
//                    .authenticated()
//        https://stackoverflow.com/questions/43052745/how-to-fix-role-in-spring-security
                .antMatchers("/user/**")
                    .hasAnyAuthority("EMPLOYEE", "MANAGER")
                .antMatchers("/manager/**")
                    .hasAuthority("MANAGER")
                .antMatchers("/clerk/**")
                    .hasAuthority("CLERK")
//        https://stackoverflow.com/questions/43591834/spring-security-hasauthority-is-ignored-when-configured-globally-for-httpsecuri
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
                    //https://memorynotfound.com/spring-boot-spring-security-thymeleaf-form-login-example/
                    .permitAll();

        http.exceptionHandling().accessDeniedPage("/403");

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        https://stackoverflow.com/questions/49312301/encrypting-inmemoryauthentication-passwords-with-bcrypt
        auth.inMemoryAuthentication().withUser("helloadmin").password(passwordEncoder().encode("helloadmin")).authorities("ADMIN");
        auth.inMemoryAuthentication().withUser("helloclerk").password(passwordEncoder().encode("helloclerk")).authorities("CLERK");
        auth.inMemoryAuthentication().withUser("helloemp").password(passwordEncoder().encode("helloemp")).authorities("EMPLOYEE");



        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /*

//    https://www.jackrutorial.com/2018/04/spring-boot-security-rest-authentication.html
//    https://medium.com/@ziatheonlyone/spring-rest-api-part-3-spring-security-basic-authentication-3fd20342745b
    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.withUsername("helloadmin").password("helloadmin").passwordEncoder("helloadmin", passwordEncoder()).authorities("ADMIN").build();
        UserDetails clerk = User.withUsername("helloclerk").password("helloclerk").authorities("CLERK").build();
        UserDetails employee = User.withUsername("helloemployee").password("helloemployee").authorities("EMPLOYEE").build();
        return new InMemoryUserDetailsManager(admin,clerk, employee);
    }
    */

}
