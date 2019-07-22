package com.laity.backstage.system.entity.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName RedisProperties
 * @Description redis 配置信息
 * spring.redis.host=120.79.66.109
 * spring.redis.port=6397
 * spring.redis.jedis.pool.max-active=8
 * spring.redis.jedis.pool.max-idle=8
 * spring.redis.jedis.pool.min-idle=0
 * spring.redis.jedis.pool.max-wait=-1
 * spring.redis.timeout=0
 * @createTime 2019/6/7/14:24
 */
@Component
public class RedisProperties {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    @Override
    public String toString() {
        return "RedisProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", timeout=" + timeout +
                ", maxActive=" + maxActive +
                ", maxIdle=" + maxIdle +
                ", minIdle=" + minIdle +
                ", maxWait='" + maxWait + '\'' +
                '}';
    }
}
