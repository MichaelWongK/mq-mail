package com.michealwang.mqmail.mp;

import com.michealwang.mqmail.platform.service.LoginLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 10:32
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Autowired
    private LoginLogService loginLogService;

    @Test
    public void testQueryWrapper() {
        System.out.println(loginLogService.selectByMsgId("21321321weedw"));;
    }
}
