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
    String id;
    String password;
    int is_superuser;
    String username;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="auth_user_role",joinColumns={@JoinColumn(name="employee_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")}
    )
    private Set<Role> roles = new HashSet<Role>();//用户与角色   多对多
}
