package org.qm.common.shior;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.qm.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.qm.common.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//公共的realm：获取安全数据，构造权限信息
@Component
public class QmRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;

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

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ProfileResult profileResult = new ProfileResult(userDao.findByUsername(ShiroUtils.getCurUsername()));
        if (authenticationToken instanceof JWTUsernamePasswordToken) {  // 仅用于给非登录请求初始化subject
            JWTUsernamePasswordToken token = (JWTUsernamePasswordToken)authenticationToken;
            return new SimpleAuthenticationInfo(profileResult, token.getCredentials(), this.getName());
        } else {  // 登录请求走这里
            return new SimpleAuthenticationInfo(ShiroUtils.getPrincipal(), ShiroUtils.getCurUser().getPassword(), this.getName());
        }
    }
}
