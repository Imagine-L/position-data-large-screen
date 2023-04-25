package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.FamilyParams;
import top.liubaiblog.poh.pojo.param.NewFamilyParams;
import top.liubaiblog.poh.pojo.param.UpdatePwdParams;
import top.liubaiblog.poh.pojo.param.UserParams;
import top.liubaiblog.poh.pojo.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.poh.pojo.vo.UserDetailVo;
import top.liubaiblog.poh.pojo.vo.UserVo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户
     */
    User getByUsername(String username);

    /**
     * 判断用户是否存在(true存在，false不存在)
     */
    boolean isUsernameExist(String username);

    /**
     * 更新当前登录的用户信息
     */
    LoginUserDTO updateLoginUser();

    /**
     * 根据参数更新用户数据表
     */
    boolean updateByParams(UserParams userParams);

    /**
     * 根据参数修改密码
     */
    boolean updatePassword(UpdatePwdParams updatePwdParams);

    /**
     * 根据参数返回家人用户列表
     */
    List<UserVo> listFamilyByParams(FamilyParams familyParams);

    /**
     * 根据id获取用户详细信息
     */
    UserDetailVo getFamilyDetailById(Long uid);

    /**
     * 根据参数更新家人信息
     */
    boolean updateFamilyByParams(NewFamilyParams familyParams);

    /**
     * 删除家人用户
     */
    boolean removeFamilyById(Long uid);

    /**
     * 添加一个家人
     */
    boolean saveFamilyByParams(NewFamilyParams familyParams);
}
