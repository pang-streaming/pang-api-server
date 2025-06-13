package com.pangapiserver.domain.follow.service;

import java.util.*;
import java.util.function.Function;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.entity.FollowEntity;
import com.pangapiserver.domain.user.repository.UserRepository;
import com.pangapiserver.domain.follow.repository.FollowRepository;
import com.pangapiserver.application.follow.data.FollowingResponse;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<FollowingResponse> getByFollowing(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followings = followRepository.findByUser(user);
        return mapToFollowingResponse(
                followings,
                FollowEntity::getFollower,
                FollowingResponse::toFollowing
        );
    }

    public List<FollowingResponse> getByFollower(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followers = followRepository.findByFollower(user);
        return mapToFollowingResponse(
                followers,
                FollowEntity::getUser,
                FollowingResponse::toFollower
        );
    }

    public void followOrNot(UserEntity following, String username) {
        UserEntity follower = userRepository.findByUsername(username);
        Optional<FollowEntity> follow = followRepository.findByUserAndFollower(following, follower);
        if (follow.isPresent()) {
            followRepository.delete(follow.get());
        } else {
            followRepository.save(FollowEntity.builder()
                    .user(following)
                    .follower(follower)
                    .build());
        }
    }

    private List<FollowingResponse> mapToFollowingResponse(
            List<FollowEntity> followEntities,
            Function<FollowEntity, UserEntity> countTargetExtractor,
            BiFunction<FollowEntity, Long, FollowingResponse> responseMapper
    ) {
        List<UUID> targetIds = followEntities.stream()
                .map(countTargetExtractor)
                .map(UserEntity::getId)
                .distinct()
                .toList();
        List<Object[]> countList = followRepository.countByFollowerIds(targetIds);

        Map<UUID, Long> countMap = new HashMap<>();
        for (Object[] row : countList) {
            UUID id = (UUID) row[0];
            Long count = (Long) row[1];
            countMap.put(id, count);
        }
        return followEntities.stream()
                .map(follow -> {
                    UUID id = countTargetExtractor.apply(follow).getId();
                    long count = countMap.get(id);
                    return responseMapper.apply(follow, count);
                })
                .toList();
    }
}