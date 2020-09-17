package com.michealwang.mqmail.config.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 自定义JSON解析过程中规则定义
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-04-03
 */
@Configuration
public class FactJsonConfig implements WebMvcConfigurer {
    /**
     * @Description: 重写FastJson的转换类
     * @Param: []
     * @return: com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
     * @Author: Arnold.zhao
     * @Date: 2019/3/10
     */
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setSerializerFeatures(
                //数值字段为null则输出0，而非null,
                SerializerFeature.WriteNullNumberAsZero,
                //字符串类型如果为null，则输出“”，而非null
                SerializerFeature.WriteNullStringAsEmpty,
                //Boolean字段如果为null，则输出false，而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat
        );
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }


    /**
     * 此处实现WebMvcConfigurer接口，重写extendMessageConverters方法，
     * 使其StringHttpMessageConverter在fastJsonHttpMessageConverter之前进行执行，可以有效避免String字符的重复序列化导致的斜杠问题
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*
            需注意，converters中默认还包含很多的HttpMessageConverter，如：ByteArrayHttpMessageConverter等很多 <br/>
            此处使用converters.clear();将会导致所有的转换器全部清空，尽管在下面代码中有重新添加两个转换器，<br/>
            但如果后续程序中需要使用到其它转换器，请记得在此处重新add一下；arnold.zhao 2019/5/2
         */
        converters.clear();
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
        converters.add(fastJsonHttpMessageConverter());
    }
}