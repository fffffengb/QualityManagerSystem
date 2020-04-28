package org.qm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_role")
@Getter
@Setter
public class Role implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    @Id
    private String id;
    /**
     * 角色名
     */
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy="roles")  //避免生成中间表
    private Set<User> users = new HashSet<>(0);//角色与用户   多对多

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="auth_role_permission",
            joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="permission_id", referencedColumnName="id")})
    private Set<Permission> perms= new HashSet<>(0);//角色与模块  多对多
}