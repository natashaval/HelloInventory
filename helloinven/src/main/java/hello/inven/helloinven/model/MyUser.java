package hello.inven.helloinven.model;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class MyUser { //extends User {

//    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
//                  boolean credentialsNonExpired, boolean accountNonLocked,
//                  Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }

    @Id
    @Column(name = "emp_id", unique = true)
    private long id;

//    KURANG EMPLOYEE PHOTO
    @Column(name = "emp_photo")
    private Blob photo;

    @Column(name = "emp_name", nullable = false)
    @NotBlank(message = "*Please provide a name")
    private String name;

    @Column(name = "emp_email",nullable = false)
    @NotBlank(message = "*Please provide an email")
    @Email(message = "*Please provide valid email")
    private String email;

    @Column(name = "emp_phone")
    private String phone;

    @Column(name = "emp_birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "username", nullable = false)
    private String username;
//     Login pakai Email saja (?)

    @Column(name = "password", nullable = false)
    private String password;

//     LOGIN pakai username & password dari table user_role

    @Column(name = "emp_manager_id", nullable = true)
    private Integer managerId;

    @Column(name = "emp_active")
    private boolean emp_active = true;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

//    https://stackoverflow.com/questions/30548391/org-springframework-security-core-userdetails-user-cannot-be-cast-to-myuserdetai/30642269
//    private boolean enabled;
//    private boolean accountNonExpired;
//    private boolean accountNonLocked;
//    private boolean credentialsNonExpired;
//
//    public MyUser(){}
//    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
//                  long id, String name, String email, String phone, Date birthday, Integer managerId, boolean emp_active) {
//        this.username = username;
//        this.password = password;
//        this.accountNonExpired = accountNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.id = id;
////        this.photo = photo;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.birthday = birthday;
//        this.managerId = managerId;
//        this.emp_active = emp_active;
//    }
//
//    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
//                  long id, String name, String email, String phone, Date birthday, Integer managerId, boolean emp_active, Role role) {
//        this.username = username;
//        this.password = password;
//        this.accountNonExpired = accountNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.id = id;
////        this.photo = photo;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.birthday = birthday;
//        this.managerId = managerId;
//        this.emp_active = emp_active;
//        this.role = role;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

//    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public boolean isEmp_active() {
        return emp_active;
    }

    public void setEmp_active(boolean emp_active) {
        this.emp_active = emp_active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
//
//    @Column(name = "enabled", nullable = false)
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
////        this.enabled = enabled;
//        this.enabled = true;
//    }
//
//    @Column(name = "account_non_expired", nullable = false)
//    public boolean isAccountNonExpired() {
//        return accountNonExpired;
//    }
//
//    public void setAccountNonExpired(boolean accountNonExpired) {
//        // this.accountNonExpired = accountNonExpired;
//        this.accountNonExpired = true;
//    }
//
//    @Column(name = "account_non_locked", nullable = false)
//    public boolean isAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    public void setAccountNonLocked(boolean accountNonLocked) {
////        this.accountNonLocked = accountNonLocked;
//        this.accountNonLocked = true;
//    }
//
//    @Column(name = "credentials_non_expired", nullable = false)
//    public boolean isCredentialsNonExpired() {
//        return credentialsNonExpired;
//    }
//
//    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
////        this.credentialsNonExpired = credentialsNonExpired;
//        this.credentialsNonExpired = true;
//    }
}
