package org.qm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_permission")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert()
@DynamicUpdate()
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 权限名称
     */
    private String name;

    public Permission(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "perms") //删除一个权限
    private Set<Role> roles = new HashSet<>(0);

}