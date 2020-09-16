package com.michealwang.mqmail.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/15 18:00
 * @Description
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = false)
public class MyDruidDataSource {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("{spring.datasource.type}")
    private String type;
    //    @Value("${spring.datasource.initialSize}")
    private int initialSize = 3;
    //    @Value("${spring.datasource.minIdle}")
    private int minIdle = 5;
    //    @Value("${spring.datasource.maxActive}")
    private int maxActive = 200;
    //    @Value("${spring.datasource.maxWait}")
    private int maxWait = 60000;
    /*    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
        private int timeBetweenEvictionRunsMillis;
        @Value("${spring.datasource.minEvictableIdleTimeMillis}")
        private int minEvictableIdleTimeMillis;
        @Value("${spring.datasource.validationQuery}")*/
    private String validationQuery = "select 1 ";
    //    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle = true;
    //    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow = true;
    //    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn = false;
/*    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;
    @Value("${spring.datasource.useGlobalDataSourceStat}")
    private boolean useGlobalDataSourceStat;*/

    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setDbType(type);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
/*        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);*/
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);

/*        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);*/
        /*try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }*//*
        datasource.setConnectionProperties(connectionProperties);*/
        return datasource;
    }
}
