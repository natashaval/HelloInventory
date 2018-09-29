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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

//@Service("userDetailsService")
@Service
public class MyUserDetailsService  implements UserDetailsService {
//    https://www.baeldung.com/spring-security-authentication-with-a-database
    @Autowired
    private MyUserRepository userRepository;

//    https://stackoverflow.com/questions/30548391/org-springframework-security-core-userdetails-user-cannot-be-cast-to-myuserdetai/30642269

//    http://www.doubleh.ie/index.php/2016/09/09/how-to-save-db-user-entity-in-spring-security-authentication-object/
    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.print("USER GA KETEMUUUUU");
            throw new UsernameNotFoundException("Unknown user");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNonLocked = true;

        System.out.print("USER NUT NUT" + user);
        System.out.print("USER: " + user.getName() + "PASSWORD: " + user.getPassword());

        MyUserDetails myUserDetails = new MyUserDetails(user,
                enabled, accountNonExpired, credentialsNotExpired, accountNonLocked,
                getAuthorities(user.getRole()));
        return myUserDetails;
    }

    private List<GrantedAuthority> getAuthorities(Role userRoles){
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority(userRoles.getRole()));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
        System.out.print("RESULTapakahsudahsampaisini: " + Result);
        return Result;
    }



}
