package top.liubaiblog.poh.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 留白
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    private static String[] schoolSplitLocations = null;
    private static List<EquipLocationMessage> schoolLocations = new ArrayList<>();

    static {
        String location = "119.161053,26.072909\n" +
                "119.160827,26.073304\n" +
                "119.160591,26.073182\n" +
                "119.160012,26.072859\n" +
                "119.159519,26.072806\n" +
                "119.158987,26.072801\n" +
                "119.158542,26.072811\n" +
                "119.158011,26.072801\n" +
                "119.157443,26.072786\n" +
                "119.156944,26.072801\n" +
                "119.156869,26.072941\n" +
                "119.156777,26.073114\n" +
                "119.156708,26.073264\n" +
                "119.156659,26.073384\n" +
                "119.156536,26.073596\n" +
                "119.15637,26.073895\n" +
                "119.156219,26.074121\n" +
                "119.15608,26.074304\n" +
                "119.155973,26.074473\n" +
                "119.155839,26.074791\n" +
                "119.155973,26.075022\n" +
                "119.15615,26.075369\n" +
                "119.156439,26.075403\n" +
                "119.156547,26.075427\n" +
                "119.15674,26.075446\n" +
                "119.156869,26.075533\n" +
                "119.157008,26.075663\n" +
                "119.157147,26.075817\n" +
                "119.157432,26.076053\n" +
                "119.157512,26.07615\n" +
                "119.157528,26.076169\n" +
                "119.157743,26.07613\n" +
                "119.157823,26.076111\n" +
                "119.158167,26.076106\n" +
                "119.158236,26.076087\n" +
                "119.158671,26.076087\n" +
                "119.1588,26.075909\n" +
                "119.158971,26.075663\n" +
                "119.159127,26.075461\n" +
                "119.159261,26.075239\n" +
                "119.159272,26.075234\n" +
                "119.159443,26.074969\n" +
                "119.15954,26.07507\n" +
                "119.159642,26.075167\n" +
                "119.160028,26.075239\n" +
                "119.160055,26.075186\n" +
                "119.159985,26.075085\n" +
                "119.15991,26.074984\n" +
                "119.159824,26.074979\n" +
                "119.15983,26.074979\n" +
                "119.159755,26.074892\n" +
                "119.159798,26.074796\n" +
                "119.15983,26.074738\n" +
                "119.160087,26.074786\n" +
                "119.160414,26.074834\n" +
                "119.160806,26.074911\n" +
                "119.1613,26.075042\n" +
                "119.161541,26.075104\n" +
                "119.16168,26.075133\n" +
                "119.161943,26.075032\n" +
                "119.162131,26.074911\n" +
                "119.162265,26.074748\n" +
                "119.162421,26.074589\n" +
                "119.162576,26.074377\n" +
                "119.162716,26.074222";
        schoolSplitLocations = location.split("\\n");
        for (String schoolSplitLocation : schoolSplitLocations) {
            String[] lineLocations = schoolSplitLocation.split(",");
            EquipLocationMessage locationMessage = new EquipLocationMessage();
            locationMessage.setEquipCode("460049167410300");
            locationMessage.setLongitude(lineLocations[0]);
            locationMessage.setLatitude(lineLocations[1]);
            locationMessage.setLongitudeHemisphere("E");
            locationMessage.setLatitudeHemisphere("N");
            schoolLocations.add(locationMessage);
        }
    }

    /**
     * 访问此接口，向主程序发起定位绕学校一圈的请求
     */
    @GetMapping("/school/mills/{time}")
    public ResponseData schoolLocation(@PathVariable("time") int time) {
        String data = "请求发送完毕";
        if (time < 0 || time >= 10000) {
            time = 20;
            data = "间隔时间异常，按默认20s发起一次请求处理";
        }
        final long sleepTime = time;
        schoolLocations.forEach(schoolLocationMessage -> {
            rabbitTemplate.convertAndSend(IotClientConstants.LOCATION_EXCHANGE,
                    "",
                    schoolLocationMessage,
                    message -> {
                        message.getMessageProperties().setExpiration(IotClientConstants.MESSAGE_EXPIRATION_MILLIS + "");
                        return message;
                    });
            log.debug("已发送测试定位，目标设备[{}]，经度[{}]，纬度[{}]",
                    schoolLocationMessage.getEquipCode(),
                    schoolLocationMessage.getLongitude(),
                    schoolLocationMessage.getLatitude());
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return ResponseData.success(data);
    }


}
