package top.liubaiblog.poh.common.threadlocal;


import top.liubaiblog.poh.pojo.dto.LoginUserDTO;

/**
 * @author 留白
 * @description 登录用户的本地线程副本
 */
public class UserThreadLocal {

    private static final ThreadLocal<LoginUserDTO> LOCAL = new ThreadLocal<>();

    public static void put(LoginUserDTO loginUser) {
        LOCAL.set(loginUser);
    }

    public static LoginUserDTO get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
