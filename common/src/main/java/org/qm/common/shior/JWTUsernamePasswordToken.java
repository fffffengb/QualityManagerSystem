package org.qm.common.shior;

import org.apache.shiro.authc.UsernamePasswordToken;

public class JWTUsernamePasswordToken extends UsernamePasswordToken {
    private String token;

    public JWTUsernamePasswordToken(String token) {
        this.token = token;
    }
//    public String getToken() {
//        return this.token;
//    }
    @Override
    public String getCredentials() {
        return this.token;
    }
}
