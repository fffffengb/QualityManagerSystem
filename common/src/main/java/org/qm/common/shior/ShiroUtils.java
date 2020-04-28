package org.qm.common.shior;


import org.qm.domain.system.User;

import java.util.HashMap;
import java.util.Map;

public class ShiroUtils {
    static private ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);
    public static User getCurUser() {
        return (User) threadLocal.get().get("curUser");
    }
    public static String getCurUserId() {
        Map<String, String> principal = (Map<String, String>) getPrincipal();
        return principal.get("userId");
    }
    public static String getCurUsername() {
        Map<String, String> principal = (Map<String, String>) getPrincipal();
        return principal.get("username");
    }
    public static Object getPrincipal() {
        return threadLocal.get().get("principal");
    }
    public static void setCurUser(User curUser) {
        threadLocal.get().put("curUser", curUser);
    }
    public static void setPrincipal(Map<String, Object> principal) {
        threadLocal.get().put("principal", principal);
    }

    public static void cleanUp() {
        threadLocal.remove();
    }
}
