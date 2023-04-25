package top.liubaiblog.poh.lisenter.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;
import top.liubaiblog.poh.endpoint.LocationEndPoint;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipLocationVO;
import top.liubaiblog.poh.service.IEquipmentViewService;
import top.liubaiblog.poh.service.IUserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 留白
 * @description 定位消息监听器
 */
@Slf4j
@Component
public class LocationListener {

    @Autowired
    private IEquipmentViewService equipmentViewService;

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    /**
     * 实时定位消息监听器
     */
    @RabbitListener(queues = IotClientConstants.REAL_TIME_POSITION_QUEUE)
    public void realTimeLocationListener(EquipLocationMessage locationMessage) {
        if (StringUtils.hasLength(locationMessage.getEquipCode())) {
            // 将定位信息封装成视图对象
            EquipLocationVO equipLocationVO = locationMessageToVO(locationMessage);
            if (equipLocationVO == null) {
                log.warn("设备[{}]未被购买或不存在，但获取到了定位信息!", locationMessage.getEquipCode());
                return;
            }
            // 向该用户的客户端发送消息
            LocationEndPoint.sendMessage(equipLocationVO.getQueryUid(), equipLocationVO);
            log.debug("来自设备[{}]的定位消息 -> 经度[{}], 纬度[{}]", locationMessage.getEquipCode(),
                    locationMessage.getLongitude(),
                    locationMessage.getLatitude());
        } else {
            log.debug("来自未知设备的定位消息 -> 经度[{}], 纬度[{}] ",
                    locationMessage.getLongitude(),
                    locationMessage.getLatitude());
        }
    }

    /**
     * 定位消息分发到用户交换机
     */
    @RabbitListener(queues = IotClientConstants.INTERVAL_LOCATION_RELEASE_QUEUE)
    public void intervalLocationReleaseListener(EquipLocationMessage locationMessage) {
        // 将定位信息封装成视图对象
        EquipLocationVO equipLocationVO = locationMessageToVO(locationMessage);
        if (equipLocationVO == null) {
            log.warn("设备[{}]未被购买或不存在，但获取到了定位信息!", locationMessage.getEquipCode());
            return;
        }
        // 分发消息
        rabbitTemplate.convertAndSend(IotClientConstants.USER_EXCHANGE,
                String.valueOf(equipLocationVO.getQueryUid()),
                equipLocationVO,
                message -> {
                    message.getMessageProperties().setExpiration(IotClientConstants.MESSAGE_EXPIRATION_MILLIS + "");
                    return message;
                });
    }

    private EquipLocationVO locationMessageToVO(EquipLocationMessage locationMessage) {
        // 根据定位信息获取具体的设备信息
        EquipmentView equipment = equipmentViewService.getByEquipCode(locationMessage.getEquipCode());
        // 封装视图对象
        if (equipment == null) {
            return null;
        }
        // 封装视图对象
        EquipLocationVO equipLocationVO = new EquipLocationVO();
        BeanUtils.copyProperties(locationMessage, equipLocationVO);
        Long equipOwner = equipment.getEquipOwner();
        if (equipOwner == null) {
            return null;
        }
        equipLocationVO.setQueryUid(equipOwner);
        equipLocationVO.setMain(equipment.getMain());
        return equipLocationVO;
    }

}
