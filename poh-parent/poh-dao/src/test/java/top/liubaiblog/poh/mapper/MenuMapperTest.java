package top.liubaiblog.poh.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.poh.pojo.po.Menu;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 留白
 * @description
 */
@SpringBootTest
class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void test() {
        List<Menu> menus = menuMapper.selectListByUser(1L);
        menus.forEach(System.out::println);
    }

}