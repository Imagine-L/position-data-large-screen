package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipLocationVO;
import top.liubaiblog.poh.service.IEquipmentViewService;
import top.liubaiblog.poh.service.ILocationService;

/**
 * @author 留白
 * @description
 */
@Api(tags = "定位控制器(前台定位显示)")
@RestController
@Slf4j
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @ApiOperation("获取用户当前的经纬度位置")
    @GetMapping
    public ResponseData currentLocation() {
        EquipLocationVO equipLocationVO = locationService.currentLocation();
        if (equipLocationVO == null) {
            return ResponseData.build(ResponseDataEnum.CLIENT_EQUIPMENT_EXCEPTION, "用户设备不存在或没有设备的定位信息");
        }
        return ResponseData.success(equipLocationVO);
    }

}
