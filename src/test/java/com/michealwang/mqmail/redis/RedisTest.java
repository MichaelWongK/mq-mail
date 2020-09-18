package com.michealwang.mqmail.redis;

import com.michealwang.mqmail.common.util.StringRedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/16 17:04
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void TestDel() {
        System.out.println("del token: " + stringRedisUtils.delete("11"));
    }

    @Test
    public void redissonTest() throws InterruptedException {
        String token = "token:u8uHgV75tugKSH8TpjRy2Y5C";
        RLock lock = redissonClient.getLock("token");
        boolean locked = lock.tryLock(0, 10, TimeUnit.SECONDS);
        if (locked) {
            Thread.sleep(60000);
        }
        lock.unlock();
    }

    @Test
    public void getExpire() {
        System.out.println(stringRedisUtils.getExpire("token:34HvEeYmLYWfe8a0OL4U6KSa"));;
    }

    @Test
    public void test() {
        // map
        Map valueMap = new HashMap();
        valueMap.put("valueMap1","map1");
        valueMap.put("valueMap2","map2");
        valueMap.put("valueMap3","map3");
//        stringRedisUtils.multiSet(valueMap);

//        stringRedisUtils.hPutAll("hashtest", valueMap);
        stringRedisUtils.hPut("hashset", "sss", "aacb");

        System.out.println(stringRedisUtils.hGetAll("hashset"));

    }
}
