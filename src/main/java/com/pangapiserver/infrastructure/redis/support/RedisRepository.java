package com.pangapiserver.infrastructure.redis.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final StringRedisTemplate redisTemplate;

    public void save(SaveType type, String key, String value) {
        redisTemplate.opsForValue().set(type.getValue().concat(key), value);
    }

    public String get(SaveType type, String key) {
        return redisTemplate.opsForValue().get(type.getValue().concat(key));
    }

    public void delete(SaveType type, String key) {
        redisTemplate.delete(type.getValue().concat(key));
    }

    public void saveHistory(SaveType type, String userId, String value) {
        String key = type.getValue().concat(userId);
        redisTemplate.opsForList().remove(key, 0, value);
        redisTemplate.opsForList().leftPush(key, value);
        redisTemplate.opsForList().trim(key, 0, 9);
    }

    public List<String> getHistory(SaveType type, String userId) {
        return redisTemplate.opsForList().range(type.getValue().concat(userId), 0, 9);
    }
}
