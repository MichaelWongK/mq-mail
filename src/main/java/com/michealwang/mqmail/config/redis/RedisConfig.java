package com.michealwang.mqmail.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/16 11:24
 * @Description redis
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisStringTemplate(LettuceConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Serializable> template = new RedisTemplate<>();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate redisByteTemplate(LettuceConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Serializable> template = new RedisTemplate<>();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisSerializer<byte[]>() {
            @Override
            public byte[] serialize(byte[] bytes) throws SerializationException {
                return bytes;
            }
            @Override
            public byte[] deserialize(byte[] bytes) throws SerializationException {
                return bytes;
            }
        });
//        template.setHashValueSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
