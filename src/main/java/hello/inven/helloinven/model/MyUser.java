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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Blob;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class MyUser {

    @Id
    @Column(name = "emp_id", unique = true)
    private Long id;

    @Column(name = "emp_photo")
    private String photo;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank
    @JsonIgnore
    private String password;

//     LOGIN pakai username & password dari table user_role

    @Column(name = "emp_manager_id", nullable = true)
    private Long managerId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Role role;

    @OneToMany(mappedBy = "myUser", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<ItemSerial> userItemSerials;

    @OneToMany(mappedBy = "requestedBy")
    @JsonIgnore
    private List<ActionTransaction> actionTransactions;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
