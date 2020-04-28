package org.qm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    int id;
    String username;
    String password;
    String salt;
    int is_superuser;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="auth_user_role",joinColumns={@JoinColumn(name="employee_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")}
    )
    private Set<Role> roles = new HashSet<Role>();//用户与角色   多对多
}
