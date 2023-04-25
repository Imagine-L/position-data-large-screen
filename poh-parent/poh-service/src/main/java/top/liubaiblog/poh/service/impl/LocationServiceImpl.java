package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;
import top.liubaiblog.poh.mapper.LocationMapper;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.po.Location;
import top.liubaiblog.poh.pojo.vo.EquipLocationVO;
import top.liubaiblog.poh.service.IEquipmentViewService;
import top.liubaiblog.poh.service.ILocationService;

/**
 * <p>
 * 定位信息表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements ILocationService {

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    @Override
    public EquipLocationVO currentLocation() {
        // 获取当前登录的用户
        LoginUserDTO loginUser = UserThreadLocal.get();
        if (loginUser == null) {
            throw new AccessDeniedException("用户未登录");
        }
        Long queryUid = loginUser.getQueryUid();
        return rabbitTemplate.receiveAndConvert(IotClientConstants.USER_QUEUE_PREFIX + queryUid,
                200,
                new ParameterizedTypeReference<EquipLocationVO>() {});
    }
}
