package hello.inven.helloinven.configuration;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import hello.inven.helloinven.service.CustomUserDetailsService;
import hello.inven.helloinven.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationNotSupportedException;
import javax.sql.DataSource;

// https://grokonez.com/spring-framework/spring-security/use-spring-security-jdbc-authentication-mysql-spring-boot#4_Configure_Database

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityConfiguration(UserDetailsService userDetailsService) {
//        super();
//        this.userDetailsService = userDetailsService;
//    }

//    @Bean
//    public UserDetailsService MyUserDetailsService(){}
//    return new MyUserDetailsService();

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(myUserDetailsService);
        http
                .authorizeRequests()
                .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
//                    .anyRequest()
//                    .authenticated()
//        https://stackoverflow.com/questions/43052745/how-to-fix-role-in-spring-security
                .antMatchers("/user")
                    .hasAnyAuthority("MANAGER","EMPLOYEE")
//        https://stackoverflow.com/questions/43591834/spring-security-hasauthority-is-ignored-when-configured-globally-for-httpsecuri
                    .anyRequest()
                    .authenticated()
                .antMatchers("/","/home")
                    .permitAll()
                .and()
                .formLogin().loginPage("/login")
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    //https://memorynotfound.com/spring-boot-spring-security-thymeleaf-form-login-example/
                    .permitAll();

        http.exceptionHandling().accessDeniedPage("/403");

    }

//    https://stackoverflow.com/questions/20349594/adding-additional-details-to-principal-object-stored-in-spring-security-context
//    @Autowired
//    @Qualifier("userDetailsService")
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userDetailsService);
//    }

//    https://www.concretepage.com/spring/spring-security/spring-mvc-security-jdbc-authentication-example-with-custom-userdetailsservice-and-database-tables-using-java-configuration
//    @Autowired
//    CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder);
//    }

    //    https://stackoverflow.com/questions/30548391/org-springframework-security-core-userdetails-user-cannot-be-cast-to-myuserdetai/30642269


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());

    }
}
