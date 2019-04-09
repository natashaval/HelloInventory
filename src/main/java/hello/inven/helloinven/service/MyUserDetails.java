package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {

    private final MyUser user;

    public MyUserDetails(MyUser user, Collection<? extends GrantedAuthority> authorities){
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public MyUserDetails(MyUser user, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities){
        super(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);

        this.user = user;
    }

    public MyUser getUser(){
        return this.user;
    }

}
