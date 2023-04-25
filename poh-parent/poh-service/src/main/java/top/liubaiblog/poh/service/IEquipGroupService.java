package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.param.EquipGroupParams;
import top.liubaiblog.poh.pojo.param.NewEquipGroupParams;
import top.liubaiblog.poh.pojo.po.EquipGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.poh.pojo.vo.EquipGroupDetailVO;
import top.liubaiblog.poh.pojo.vo.EquipGroupVO;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 设备分组表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IEquipGroupService extends IService<EquipGroup> {

    /**
     * 根据用户id获取所有该用户设备分组的名字列表
     */
    List<EquipGroup> getNamesByUid();

    /**
     * 根据请求参数获取用户的分组列表
     */
    List<EquipGroupVO> listByParams(EquipGroupParams groupParams);

    /**
     * 获取某个分组的详细信息
     */
    EquipGroupDetailVO getDetailById(Long gid);

    /**
     * 根据请求参数更新分组信息
     */
    boolean updateByParams(NewEquipGroupParams groupParams);

    /**
     * 判断分组ID是否存在
     */
    boolean isGidExist(Long gid);

    /**
     * 根据请求参数保存设备分组
     */
    boolean saveByParams(NewEquipGroupParams groupParams);
}
