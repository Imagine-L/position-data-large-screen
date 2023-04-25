package top.liubaiblog.poh.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 留白
 * @description 安全相关常量
 */
public class SecureConstants {

    // redis中登录用户的token前缀
    public final static String REDIS_LOGIN_USER_TOKEN_PREFIX = "token:";
    // redis中用户权限的前缀
    public final static String REDIS_PERMISSION_PREFIX = "permission:";
    // redis中以上内容存储的时间(秒)
    public final static long REDIS_EXPIRATION_SECONDS = 60 * 60 * 24;
    // 用户登录后台所需要的权限集合
    public final static List<String> TO_BACKSTAGE_PERMISSIONS;
    // 用户的高权限家人角色id
    public final static Long SUPER_FAMILY_ID = 4L;
    // 系统用户角色id
    public final static Long SYSTEM_USER_ID = 2L;

    static {
        TO_BACKSTAGE_PERMISSIONS = new ArrayList<>();
        TO_BACKSTAGE_PERMISSIONS.add("equipment:operate");
        TO_BACKSTAGE_PERMISSIONS.add("equipmentGroup:operate");
    }

}
