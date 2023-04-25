package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.LicenseParams;

/**
 * @author 留白
 * @description 登录服务
 */
public interface LicenseService {

    /**
     * 登录并返回登录的用户
     */
    LoginUserDTO loginGetLoginUser(LicenseParams licenseParams);

    /**
     * 登出用户
     */
    void logout();

    /**
     * 注册并返回注册后的用户
     */
    LoginUserDTO registerGetLoginUser(LicenseParams licenseParams);
}
