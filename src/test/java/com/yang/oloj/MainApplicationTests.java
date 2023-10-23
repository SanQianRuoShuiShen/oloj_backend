package com.yang.oloj;

import com.yang.oloj.config.WxOpenConfig;

import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/SanQianRuoShuiShen">三千弱水</a>
 * @from  
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;

    @Test
    void contextLoads() {
        System.out.println(wxOpenConfig);
    }

}
