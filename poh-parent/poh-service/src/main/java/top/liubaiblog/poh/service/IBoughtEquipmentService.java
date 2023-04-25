package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.param.NewEquipmentParams;
import top.liubaiblog.poh.pojo.po.BoughtEquipment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IBoughtEquipmentService extends IService<BoughtEquipment> {

    /**
     * 根据设备参数保存新的设备
     */
    boolean saveByParams(NewEquipmentParams equipmentParams);

    /**
     * 将除了指定设备外的其他设备设置为非主设备(main=false)
     */
    boolean setOtherNotMain(Long uid, String equipCode);

    /**
     * 根据参数修改某个设备
     * 这里更新的是被用户购买的设备表，而不是去修改真正的设备表
     */
    boolean updateByParams(NewEquipmentParams equipmentParams);
}
