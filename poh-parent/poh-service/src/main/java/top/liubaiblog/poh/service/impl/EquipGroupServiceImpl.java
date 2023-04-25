package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.liubaiblog.poh.mapper.BoughtEquipmentMapper;
import top.liubaiblog.poh.mapper.EquipGroupMapper;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.EquipGroupParams;
import top.liubaiblog.poh.pojo.param.NewEquipGroupParams;
import top.liubaiblog.poh.pojo.po.EquipGroup;
import top.liubaiblog.poh.pojo.vo.EquipGroupDetailVO;
import top.liubaiblog.poh.pojo.vo.EquipGroupVO;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;
import top.liubaiblog.poh.service.IEquipGroupService;
import top.liubaiblog.poh.service.IEquipmentViewService;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备分组表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
@Transactional
public class EquipGroupServiceImpl extends ServiceImpl<EquipGroupMapper, EquipGroup> implements IEquipGroupService {

    @Autowired
    private IEquipmentViewService equipmentViewService;

    @Autowired
    @SuppressWarnings("all")
    private BoughtEquipmentMapper boughtEquipmentMapper;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public List<EquipGroup> getNamesByUid() {
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipGroup::getGroupOwner, loginUser.getQueryUid());
        wrapper.select(EquipGroup::getGid, EquipGroup::getGroupName);
        wrapper.orderByDesc(EquipGroup::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<EquipGroupVO> listByParams(EquipGroupParams groupParams) {
        if (groupParams == null) {
            groupParams = new EquipGroupParams();
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        groupParams.setUid(loginUser.getQueryUid());
        return getBaseMapper().listByParams(groupParams);
    }

    @Override
    public EquipGroupDetailVO getDetailById(Long gid) {
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 根据id获取分组并拷贝到视图层对象中
        LambdaQueryWrapper<EquipGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipGroup::getGid, gid);
        wrapper.eq(EquipGroup::getGroupOwner, loginUser.getQueryUid());
        EquipGroup equipGroup = getOne(wrapper);
        EquipGroupDetailVO equipGroupDetailVO = new EquipGroupDetailVO();
        BeanUtils.copyProperties(equipGroup, equipGroupDetailVO);
        // 获取该分组的所有设备
        List<EquipmentVO> equipments = equipmentViewService.listByGroupId(gid);
        // 确定该分组的设备是否全部展示或者全部禁用
        boolean isLocked = true;
        boolean isShowed = true;
        if (equipments == null || equipments.isEmpty()) {
            isLocked = false;
            isShowed = false;
        } else {
            for (EquipmentVO equipment : equipments) {
                if (!equipment.isLocked()) {
                    isLocked = false;
                }
                if (!equipment.isShowed()) {
                    isShowed = false;
                }
                if (!isLocked && isShowed) {
                    break;
                }
            }
        }
        equipGroupDetailVO.setEquipments(equipments);
        equipGroupDetailVO.setEquipmentsShowed(isShowed);
        equipGroupDetailVO.setEquipmentsLocked(isLocked);
        equipGroupDetailVO.setCount(equipments != null ? equipments.size() : 0);
        return equipGroupDetailVO;
    }

    @Override
    public boolean updateByParams(NewEquipGroupParams groupParams) {
        // 参数校验
        if (groupParams == null || groupParams.getGid() == null) return false;
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 封装参数修改分组信息
        LambdaQueryWrapper<EquipGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipGroup::getGid, groupParams.getGid());
        wrapper.eq(EquipGroup::getGroupOwner, loginUser.getQueryUid());
        EquipGroup equipGroup = new EquipGroup();
        equipGroup.setGroupName(groupParams.getGroupName());
        equipGroup.setDescription(groupParams.getDescription());
        equipGroup.setLastUpdateBy(loginUser.getUid());
        equipGroup.setLastUpdateTime(new Date());
        List<Long> equipmentIds = groupParams.getEquipmentIds();
        // 更新分组
        boolean result = update(equipGroup, wrapper);
        if (result) {
            // 异步执行对设备的操作
            taskExecutor.execute(() -> {
                // 将请求参数中的各个设备的划入此分组
                if (equipmentIds != null && !equipmentIds.isEmpty()) {
                    boughtEquipmentMapper.updateGroupIdById(equipmentIds, groupParams.getGid());
                }
                // 判断该组的所有设备是否需要展示或者锁定
                if (groupParams.isEquipmentsShowed()) {
                    boughtEquipmentMapper.updateShowedByGroupId(groupParams.getGid(), true);
                }
                if (groupParams.isEquipmentsLocked()) {
                    boughtEquipmentMapper.updateLockedByGroupId(groupParams.getGid(), true);
                }
            });
        }
        return result;
    }

    @Override
    public boolean isGidExist(Long gid) {
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipGroup::getGroupOwner, loginUser.getQueryUid());
        wrapper.eq(EquipGroup::getGid, gid);
        return count(wrapper) > 0;
    }

    @Override
    public boolean saveByParams(NewEquipGroupParams groupParams) {
        // 参数校验
        if (groupParams == null || !StringUtils.hasLength(groupParams.getGroupName())) {
            return false;
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 封装对象
        EquipGroup equipGroup = new EquipGroup();
        equipGroup.setGroupName(groupParams.getGroupName());
        equipGroup.setDescription(groupParams.getDescription());
        equipGroup.setGroupOwner(loginUser.getQueryUid());
        equipGroup.setCreateBy(loginUser.getUid());
        equipGroup.setCreateTime(new Date());
        List<Long> equipmentIds = groupParams.getEquipmentIds();
        // 保存到数据库
        boolean result = save(equipGroup);
        if (result) {
            // 异步执行对设备的操作
            taskExecutor.execute(() -> {
                // 将请求参数中的各个设备的划入此分组
                if (equipmentIds != null && !equipmentIds.isEmpty()) {
                    boughtEquipmentMapper.updateGroupIdById(equipmentIds, equipGroup.getGid());
                }
                // 判断该组的所有设备是否需要展示或者锁定
                if (groupParams.isEquipmentsShowed()) {
                    boughtEquipmentMapper.updateShowedByGroupId(equipGroup.getGid(), true);
                }
                if (groupParams.isEquipmentsLocked()) {
                    boughtEquipmentMapper.updateLockedByGroupId(equipGroup.getGid(), true);
                }
            });
        }
        return result;
    }
}
