package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
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
//    @JsonIgnoreProperties(value = "role", allowSetters = true)
    @JsonIgnore
    private Set<MyUser> users;

    public Role(Integer roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }
}
