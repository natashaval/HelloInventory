package hello.inven.helloinven.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

//https://www.javainuse.com/spring/boot_form_authentication_handler
//https://www.devglan.com/spring-security/spring-boot-security-redirect-after-login
//https://www.baeldung.com/spring_redirect_after_login
@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        boolean hasUserRole = false;
        boolean hasAdminRole = false;
        boolean hasManagerRole = false;
        boolean hasClerkRole = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("auth --: " + authorities);
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().contains("EMPLOYEE")) {
                hasUserRole = true;
                break;
            }
            else if(grantedAuthority.getAuthority().contains("ADMIN")){
                hasAdminRole = true;
                break;
            }
            else if(grantedAuthority.getAuthority().contains("MANAGER")){
                hasManagerRole = true;
                break;
            }
            else if(grantedAuthority.getAuthority().contains("CLERK")){
                hasClerkRole = true;
                break;
            }
        }

        if (hasAdminRole) redirectStrategy.sendRedirect(request, response, "admin/dashboard");
        else if (hasManagerRole) redirectStrategy.sendRedirect(request, response, "manager/dashboard");
        else if(hasUserRole) redirectStrategy.sendRedirect(request, response, "user/dashboard");
        else if (hasClerkRole) redirectStrategy.sendRedirect(request, response, "clerk/dashboard");
        else throw new IllegalStateException();
    }
}
