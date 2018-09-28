package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails extends MyUser implements UserDetails{
//public class CustomUserDetails implements UserDetails {
//    https://stackoverflow.com/questions/7737602/example-of-custom-implementation-of-userdetails
//    http://krams915.blogspot.com/2012/01/spring-security-31-implement_3065.html

//    private MyUser user;
//    public CustomUserDetails(final MyUser _user) {
//        this.user = _user;
//
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.user.isEmp_active();
//    }
//
//    public MyUser getUser(){
//        return user;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomUserDetails [user=" + user + "]";
//    }

//    https://stackoverflow.com/questions/32276482/java-lang-classcastexception-org-springframework-security-core-userdetails-user
    private static final long serialVersionUID = 1L;
    private MyUser user;

    Set<GrantedAuthority> authorities = null;
    public MyUser getUser() {
        return user;
    }
    public void setUser(MyUser user) {
        this.user = user;
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
//        return user.isAccountNonExpired();
        return true;
    }

    public boolean isAccountNonLocked() {
//        return user.isAccountNonLocked();
        return true;
    }

    public boolean isCredentialsNonExpired() {
//        return user.isCredentialsNonExpired();
        return true;
    }

    public boolean isEnabled() {
//        return user.isAccountEnabled();
        return true;
    }
}
