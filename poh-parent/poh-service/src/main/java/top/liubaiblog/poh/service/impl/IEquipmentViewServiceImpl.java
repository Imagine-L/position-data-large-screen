package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.liubaiblog.poh.mapper.EquipmentViewMapper;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.EquipmentParams;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipmentDetailVO;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;
import top.liubaiblog.poh.service.IEquipmentViewService;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【poh_equipment_view】的数据库操作Service实现
 * @createDate 2022-06-21 19:59:22
 */
@Service
public class IEquipmentViewServiceImpl extends ServiceImpl<EquipmentViewMapper, EquipmentView>
        implements IEquipmentViewService {

    @Override
    public List<EquipmentVO> listByParams(EquipmentParams equipmentParams) {
        if (equipmentParams == null) {
            equipmentParams = new EquipmentParams();
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        equipmentParams.setUid(loginUser.getQueryUid());
        return getBaseMapper().listByParams(equipmentParams);
    }

    @Override
    public EquipmentDetailVO getDetailVOById(Long eid) {
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentView::getEquipOwner, loginUser.getQueryUid());
        wrapper.eq(EquipmentView::getEid, eid);
        EquipmentView equipmentView = getOne(wrapper);
        EquipmentDetailVO equipmentDetailVO = new EquipmentDetailVO();
        BeanUtils.copyProperties(equipmentView, equipmentDetailVO);
        return equipmentDetailVO;
    }

    @Override
    public List<EquipmentVO> listByGroupId(Long gid) {
        // 参数校验
        if (gid == null) {
            return Collections.emptyList();
        }
        // 封装条件并查询
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentView::getEquipOwner, loginUser.getQueryUid());
        wrapper.eq(EquipmentView::getGroupId, gid);
        wrapper.select(EquipmentView::getEid,
                EquipmentView::getEquipName,
                EquipmentView::getEquipCode,
                EquipmentView::getGroupId,
                EquipmentView::getShowed,
                EquipmentView::getLocked);
        List<EquipmentView> list = list(wrapper);
        // 将查询出的结果映射成视图层对象返回
        return list.stream().map(equipmentView -> {
            EquipmentVO equipmentVO = new EquipmentVO();
            BeanUtils.copyProperties(equipmentView, equipmentVO);
            return equipmentVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EquipmentView> getNamesByUid() {
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentView::getEquipOwner, loginUser.getQueryUid());
        wrapper.select(EquipmentView::getEid, EquipmentView::getEquipName, EquipmentView::getEquipCode);
        return list(wrapper);
    }

    @Override
    public boolean isGroupOwner(Long gid) {
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        LoginUserDTO loginUser = UserThreadLocal.get();
        wrapper.eq(EquipmentView::getEquipOwner, loginUser.getQueryUid());
        wrapper.eq(EquipmentView::getGroupId, gid);
        return count(wrapper) > 0;
    }

    @Override
    public List<EquipmentVO> listByShowed(Integer size) {
        if (size == null) {
            size = 4;
        }
        // 封装查询参数并查询
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentView::getEquipOwner, loginUser.getQueryUid());
        wrapper.eq(EquipmentView::getLocked, false).and(true, equipWrapper -> {
            equipWrapper.eq(EquipmentView::getShowed, true).or().eq(EquipmentView::getMain, true);
        });
        wrapper.select(EquipmentView::getEid, EquipmentView::getEquipName, EquipmentView::getEquipCode);
        wrapper.last("limit " + size);
        List<EquipmentView> equipments = list(wrapper);
        // 转换成视图层对象返回
        return equipments.stream().map(equipment -> {
            EquipmentVO equipmentVO = new EquipmentVO();
            equipmentVO.setEid(equipment.getEid());
            equipmentVO.setEquipName(equipment.getEquipName());
            equipmentVO.setEquipCode(equipment.getEquipCode());
            equipmentVO.setShowed(true);
            return equipmentVO;
        }).collect(Collectors.toList());
    }

    @Override
    public EquipmentView getByEquipCode(String equipCode) {
        LambdaQueryWrapper<EquipmentView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentView::getEquipCode, equipCode);
        wrapper.select(EquipmentView::getEquipOwner,
                EquipmentView::getMain,
                EquipmentView::getEid,
                EquipmentView::getShowed);
        return getOne(wrapper);
    }
}




