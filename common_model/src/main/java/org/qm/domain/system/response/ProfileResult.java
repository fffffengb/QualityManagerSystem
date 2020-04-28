package org.qm.domain.system.response;

import org.qm.domain.system.Permission;
import org.qm.domain.system.Role;
import org.qm.domain.system.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
public class ProfileResult implements Serializable{
    private int id;
    private String username;
    private Set<String> strRoles = new HashSet<>();
    private Set<String> strPerms = new HashSet<>();

    public ProfileResult(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> perms = role.getPerms();
            strRoles.add(role.getName());
            for (Permission perm : perms) {
                String permName = perm.getName();
                strPerms.add(permName);
            }
        }
    }

}
