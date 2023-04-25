package top.liubaiblog.poh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.poh.pojo.param.EquipmentParams;
import top.liubaiblog.poh.pojo.param.NewEquipmentParams;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipmentDetailVO;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【poh_equipment_view】的数据库操作Service
* @createDate 2022-06-21 19:59:22
*/
public interface IEquipmentViewService extends IService<EquipmentView> {

    /**
     * 根据参数查询设备列表
     */
    List<EquipmentVO> listByParams(EquipmentParams equipmentParams);

    /**
     * 根据id获取某个设备的详细信息
     */
    EquipmentDetailVO getDetailVOById(Long eid);

    /**
     * 获取某个分组的全部设备
     */
    List<EquipmentVO> listByGroupId(Long gid);

    /**
     * 获取所有设备的设备名
     */
    List<EquipmentView> getNamesByUid();

    /**
     * 是否还有设备在指定分组中
     */
    boolean isGroupOwner(Long gid);

    /**
     * 显示需要展示的设备列表
     */
    List<EquipmentVO> listByShowed(Integer size);

    /**
     * 根据设备码获取已被用户购买的设备，若返回为null，表示没有该设备或设备未被购买
     */
    EquipmentView getByEquipCode(String equipCode);
}
