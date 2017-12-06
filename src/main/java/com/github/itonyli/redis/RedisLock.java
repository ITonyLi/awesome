package com.github.itonyli.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * No redlock, but i know Martin think the redlock is that "neither fish nor fowl".
 */

@Component
public class RedisLock {
    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private final StringRedisTemplate template;

    @Autowired
    public RedisLock(StringRedisTemplate template) {
        this.template = template;
        this.template.setEnableTransactionSupport(true);
    }

    /**
     * lock
     * @param key string
     * @param expireTime long mills
     */
    public Boolean setConcurrentLock(final String key, long expireTime) throws InterruptedException {
        logger.debug("Redis lock receive thread name:{} request, key:{}, expire time:{}.", Thread.currentThread().getName(), key, expireTime);
        ValueOperations<String, String> operations = template.opsForValue();
        while (!operations.setIfAbsent(key, String.valueOf(System.currentTimeMillis() + expireTime))) {

            TimeUnit.MILLISECONDS.sleep(3);
        }
        return Boolean.TRUE;
    }

    /**
     * unlock
     * @param key string
     */
    public void delConcurrentLock(String key) {

    }

    public void test() {
        ExecutorService service = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                try {
                    setConcurrentLock("KKK", (long) new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


    }

}


