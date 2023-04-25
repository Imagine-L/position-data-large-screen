package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.po.Equipment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IEquipmentService extends IService<Equipment> {

    /**
     * 指定设备码在设备表中是否存在，并且该设备码未被使用
     */
    boolean isCodeExistAndNotUse(String equipCode);

}
