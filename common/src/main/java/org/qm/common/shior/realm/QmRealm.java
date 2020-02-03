package org.qm.common.shior.realm;

import org.qm.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

//公共的realm：获取安全数据，构造权限信息
public class QmRealm extends AuthorizingRealm {

    public void setName(String name) {
        super.setName("QmRealm");
    }

    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        ProfileResult result = (ProfileResult)principalCollection.getPrimaryPrincipal();
        //2.构造权限数据
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(result.getStrPerms());
        info.setRoles(result.getStrRoles());
        return info;
    }

    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
