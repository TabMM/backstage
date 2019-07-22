package com.laity.backstage.system.config;

import com.laity.backstage.system.entity.pojo.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName Redis
 * @Description TODO
 * @createTime 2019/6/7/17:58
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisPool redisPoolFactory() {

        logger.info("Redis配置-->JedisPool注入成功！");

        logger.info("Redis详细：" + redisProperties);

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxIdle(redisProperties.getMaxIdle());

        poolConfig.setMaxWaitMillis(redisProperties.getMaxWait());

        JedisPool jedisPool = new JedisPool(poolConfig, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeout());

        return jedisPool;
    }

}
