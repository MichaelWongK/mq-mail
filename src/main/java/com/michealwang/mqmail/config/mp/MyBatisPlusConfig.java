package com.michealwang.mqmail.config.mp;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/15 17:49
 * @Description MyBatis配置
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.michealwang.mqmail.platform.**.mapper")
public class MyBatisPlusConfig {



    /**
     * PaginationIntercepto分页插件。
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}