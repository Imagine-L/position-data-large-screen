package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.po.Location;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.poh.pojo.vo.EquipLocationVO;

/**
 * <p>
 * 定位信息表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface ILocationService extends IService<Location> {

    /**
     * 获取用户当前的定位
     */
    EquipLocationVO currentLocation();
}
