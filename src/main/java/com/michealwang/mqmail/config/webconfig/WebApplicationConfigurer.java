package com.michealwang.mqmail.config.webconfig;

import com.michealwang.mqmail.common.interceptor.ApiIdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 14:46
 * @Description
 * 其实以前都是继承WebMvcConfigurerAdapter类
 * 不过springBoot2.0以上 WebMvcConfigurerAdapter 方法过时，有两种替代方案：
 * 1、继承WebMvcConfigurationSupport
 * 2、实现WebMvcConfigurer
 * 但是继承WebMvcConfigurationSupport会让Spring-boot对mvc的自动配置失效。
 * 根据项目情况选择。现在大多数项目是前后端分离，
 * 并没有对静态资源有自动配置的需求所以继承WebMvcConfigurationSupport也未尝不可。
 */
@Configuration
public class WebApplicationConfigurer implements WebMvcConfigurer {

    /**
     * 跨域
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 对拦截器Bean处理，不处理在拦截器中无法注入其他Bean
     * @return
     */
    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(apiIdempotentInterceptor());
    }
}
