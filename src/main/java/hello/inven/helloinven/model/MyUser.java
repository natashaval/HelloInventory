package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Blob;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor

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
    private Long id;

//    KURANG EMPLOYEE PHOTO
    @Column(name = "emp_photo")
    private String photo;

    @Column(name = "emp_name", nullable = false)
//    @NotBlank
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank
    private String username;
//     Login pakai Email saja (?)

    @Column(name = "password", nullable = false)
    @NotBlank
    @JsonIgnore
    private String password;

//     LOGIN pakai username & password dari table user_role

    @Column(name = "emp_manager_id", nullable = true)
    private Long managerId;

//    @Column(name = "emp_active")
//    private boolean emp_active = true;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Role role;

//    @OneToMany(mappedBy = "primaryKey.myUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(mappedBy = "myUser", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JsonIgnoreProperties(value = "myUser")
    @JsonIgnore
    private List<ItemSerial> userItemSerials;

//    https://stackoverflow.com/questions/11718459/onetomany-mappedby-maps-to
//    https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    @OneToMany(mappedBy = "requestedBy")
    @JsonIgnore
    private List<ActionTransaction> actionTransactions;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ItemSerial> getUserItemSerials() {
        return userItemSerials;
    }

    public void setUserItemSerials(List<ItemSerial> userItemSerials) {
        this.userItemSerials = userItemSerials;
    }

    public List<ActionTransaction> getActionTransactions() {
        return actionTransactions;
    }

    public void setActionTransactions(List<ActionTransaction> actionTransactions) {
        this.actionTransactions = actionTransactions;
    }
}
