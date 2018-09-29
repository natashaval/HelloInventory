package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Service("useDetailsService")
public class CustomUserDetailsService { //implements UserDetailsService {
//    https://stackoverflow.com/questions/20349594/adding-additional-details-to-principal-object-stored-in-spring-security-context
//    https://stackoverflow.com/questions/41770156/spring-add-custom-user-details-to-spring-security-user

    @Autowired
    MyUserRepository myUserRepository;

//    @Override
//    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        //Try to find user and its roles, for example here we try to get it from database via a DAO object
//        //Do not confuse this foo.bar.User with CurrentUser or spring User, this is a temporary object which holds user info stored in database
//
//        MyUser user = myUserRepository.findByUsername(username);
//        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
//
//        // if you're implementing UserDetails you won't need to call this method
//        // and instead return the User as it is
//        // return user;
//
//        return buildUserForAuthentication(user, authorities);
//
//    }
//
//    private User buildUserForAuthentication(MyUser user, List<GrantedAuthority> authorities) {
//        String username = user.getUsername();
//        String password = user.getPassword();
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//
//        MyUser myUser = new MyUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
//                accountNonLocked, authorities);
//        myUser.setBirthday(myUser.getBirthday());
//        myUser.setEmail(myUser.getEmail());
//        return myUser;
//    }
//
//    private List<GrantedAuthority> buildUserAuthority(Role role) {
//        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
//
//        //Build user's authorities
////        for (Role role : roles) {
//            setAuths.add(new SimpleGrantedAuthority(role.getRole()));
////        }
//
//        return new ArrayList<GrantedAuthority>(setAuths);
//    }

//    https://stackoverflow.com/questions/32276482/java-lang-classcastexception-org-springframework-security-core-userdetails-user
//    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        MyUser myUser = myUserRepository.findByUsername(username);
//
//        Role roles = myUser.getRole();
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(roles.getRole()));
//
//        CustomUserDetails customUserDetails = new CustomUserDetails();
//        customUserDetails.setUser(myUser);
//        customUserDetails.setAuthorities(authorities);
//        return customUserDetails;
//    }
}
