package org.qm.sys.shior.realm;

import org.apache.shiro.authc.*;
import org.qm.domain.system.User;
import org.qm.domain.system.response.ProfileResult;
import org.qm.common.shior.realm.QmRealm;
import org.qm.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends QmRealm {
    /**
     * 认证方法
     */
    @Autowired
    private UserService userService;

    public UserRealm() {
    }


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.根据控制层login传过来的UPToken拿到用户名和密码
        UsernamePasswordToken uPToken = (UsernamePasswordToken) authenticationToken;
        String username = uPToken.getUsername();
        String password = new String(uPToken.getPassword());
        //2.从数据库中查询该用户信息
        User curUser = userService.findByUsername(username);
        //3.验证密码
        if (curUser != null && curUser.getPassword().equals(password)) {
            //4.密码正确，则构造安全数据并返回。
            ProfileResult profileResult = new ProfileResult(curUser);
            return new SimpleAuthenticationInfo(profileResult, curUser.getPassword(), this.getName());
        }
        //5.密码错误或用户不存在，返回null表示验证失败。
        return null;
    }
}
