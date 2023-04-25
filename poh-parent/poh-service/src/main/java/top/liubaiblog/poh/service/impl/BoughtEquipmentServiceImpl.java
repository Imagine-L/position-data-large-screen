package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.liubaiblog.poh.mapper.BoughtEquipmentMapper;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.NewEquipmentParams;
import top.liubaiblog.poh.pojo.po.BoughtEquipment;
import top.liubaiblog.poh.service.IBoughtEquipmentService;
import top.liubaiblog.poh.service.IEquipGroupService;
import top.liubaiblog.poh.service.IEquipmentService;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import java.util.Date;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
@Transactional
public class BoughtEquipmentServiceImpl extends ServiceImpl<BoughtEquipmentMapper, BoughtEquipment> implements IBoughtEquipmentService {

    @Autowired
    private IEquipmentService equipmentService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private IEquipGroupService groupService;

    @Override
    public boolean saveByParams(NewEquipmentParams equipmentParams) {
        // 参数校验
        if (equipmentParams == null || !StringUtils.hasLength(equipmentParams.getEquipCode())) {
            return false;
        }
        // 判断设备码在设备表中是否存在
        boolean codeExistAndNotUse = equipmentService.isCodeExistAndNotUse(equipmentParams.getEquipCode());
        // 判断分组是否存在
        if (!groupService.isGidExist(equipmentParams.getGroupId())) {
            return false;
        }
        // 若存在，则保存这个设备到已购买的设备表中
        if (codeExistAndNotUse) {
            // 封装参数并保存
            LoginUserDTO loginUser = UserThreadLocal.get();
            BoughtEquipment boughtEquipment = new BoughtEquipment();
            boughtEquipment.setEquipName(equipmentParams.getEquipName());
            boughtEquipment.setEquipCode(equipmentParams.getEquipCode());
            boughtEquipment.setEquipOwner(loginUser.getQueryUid());
            boughtEquipment.setGroupId(equipmentParams.getGroupId());
            boughtEquipment.setDanger(false);
            boughtEquipment.setShowed(equipmentParams.isShowed() || equipmentParams.isMain());
            boughtEquipment.setMain(equipmentParams.isMain());
            boughtEquipment.setLocked(equipmentParams.isLocked());
            boughtEquipment.setCreateBy(loginUser.getUid());
            boughtEquipment.setCreateTime(new Date());
            // 如果是主设备，异步将其他设备设置未非主设备
            if (equipmentParams.isMain()) {
                taskExecutor.execute(() -> setOtherNotMain(loginUser.getQueryUid(), equipmentParams.getEquipCode()));
            }
            return save(boughtEquipment);
        }
        return false;
    }

    @Override
    public boolean setOtherNotMain(Long uid, String equipCode) {
        // 将全部设备设置为false
        LambdaUpdateWrapper<BoughtEquipment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BoughtEquipment::getEquipOwner, uid);
        wrapper.ne(BoughtEquipment::getEquipCode, equipCode);
        wrapper.set(BoughtEquipment::getMain, false);
        return update(wrapper);
    }

    @Override
    public boolean updateByParams(NewEquipmentParams equipmentParams) {
        // 参数校验
        if (equipmentParams == null || !StringUtils.hasLength(equipmentParams.getEquipCode())) return false;
        // 判断分组是否存在
        Long groupId = equipmentParams.getGroupId();
        if (!groupService.isGidExist(groupId)) {
            return false;
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaUpdateWrapper<BoughtEquipment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BoughtEquipment::getEquipOwner, loginUser.getQueryUid());
        wrapper.eq(BoughtEquipment::getEquipCode, equipmentParams.getEquipCode());
        BoughtEquipment boughtEquipment = new BoughtEquipment();
        boughtEquipment.setEquipName(equipmentParams.getEquipName());
        if (equipmentParams.isMain()) {
            taskExecutor.execute(() -> setOtherNotMain(loginUser.getQueryUid(), equipmentParams.getEquipCode()));
        }
        boughtEquipment.setMain(equipmentParams.isMain());
        boughtEquipment.setGroupId(equipmentParams.getGroupId());
        boughtEquipment.setShowed(equipmentParams.isShowed() || equipmentParams.isMain());
        boughtEquipment.setLocked(equipmentParams.isLocked());
        boughtEquipment.setLastUpdateBy(loginUser.getUid());
        boughtEquipment.setLastUpdateTime(new Date());
        // 更新设备返回结果
        return update(boughtEquipment, wrapper);
    }

}
