package top.liubaiblog.poh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.poh.mapper.EquipmentViewMapper;
import top.liubaiblog.poh.pojo.param.EquipmentParams;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@SpringBootTest
public class MainTest {

    @Autowired
    private EquipmentViewMapper equipmentViewMapper;

    @Test
    public void test() {
        EquipmentParams equipmentParams = new EquipmentParams();
        equipmentParams.setEquipName("设备");
        equipmentParams.setGroupName("default");
        List<EquipmentVO> equipmentVOS = equipmentViewMapper.listByParams(equipmentParams);
        equipmentVOS.forEach(System.out::println);
    }

}
