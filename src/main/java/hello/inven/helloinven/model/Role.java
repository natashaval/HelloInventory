package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="role")
public class Role {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role")
    private String role;

//    https://www.baeldung.com/hibernate-one-to-many
    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = "role", allowSetters = true)
    private Set<MyUser> users;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<MyUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MyUser> users) {
        this.users = users;
    }
}
