package com.pangapiserver.domain.video.service;

import com.pangapiserver.domain.stream.entity.StreamEntity;
import com.pangapiserver.domain.stream.repository.StreamRepository;
import com.pangapiserver.infrastructure.redis.support.RedisRepository;
import com.pangapiserver.infrastructure.redis.support.SaveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final StreamRepository repository;
    private final RedisRepository redisRepository;

    public List<StreamEntity> getWatchedVideos(UUID userId) {
        List<String> videoIds = redisRepository.getHistory(SaveType.WATCH_HISTORY, userId.toString());

        return videoIds.stream()
                .map(id -> repository.findById(UUID.fromString(id)))
                .flatMap(Optional::stream)
                .toList();
    }
}
