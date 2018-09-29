package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

public class MyUserDetails extends User {
//    private final String email;
//    private final String phone;
//    private final Date birthday;
//    private final int managerId;
//
//    public MyUserDetails(String username, String password, boolean enabled,
//                         boolean accountNonExpired, boolean credentialsNonExpired,
//                         boolean accountNonLocked, String email, String phone, Date birthday, Integer managerId,
//                         Collection<? extends GrantedAuthority> authorities){
//        super(username, password, enabled, accountNonExpired,
//                credentialsNonExpired, accountNonLocked, authorities);
//        this.birthday = birthday;
//        this.email = email;
//        this.phone = phone;
//        this.managerId = managerId;
//
//    }
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public Integer getManagerId() {
//        return managerId;
//    }

//    http://www.doubleh.ie/index.php/2016/09/09/how-to-save-db-user-entity-in-spring-security-authentication-object/
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
