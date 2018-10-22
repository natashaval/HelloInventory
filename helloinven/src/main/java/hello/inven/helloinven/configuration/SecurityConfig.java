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

// https://grokonez.com/spring-framework/spring-security/use-spring-security-jdbc-authentication-mysql-spring-boot#4_Configure_Database

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
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
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());

    }
}
