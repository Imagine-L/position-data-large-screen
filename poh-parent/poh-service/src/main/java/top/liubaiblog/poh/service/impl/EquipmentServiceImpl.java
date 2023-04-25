package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.liubaiblog.poh.mapper.EquipmentMapper;
import top.liubaiblog.poh.pojo.po.Equipment;
import top.liubaiblog.poh.service.IEquipmentService;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {

    @Override
    public boolean isCodeExistAndNotUse(String equipCode) {
        return getBaseMapper().isCodeExistAndNotUse(equipCode) > 0;
    }

}
