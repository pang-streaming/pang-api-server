package com.pangapiserver.domain.follow.service;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.entity.FollowEntity;
import com.pangapiserver.domain.user.repository.UserRepository;
import com.pangapiserver.application.follow.mapper.FollowMapper;
import com.pangapiserver.application.follow.data.FollowingResponse;
import com.pangapiserver.domain.follow.repository.FollowRepository;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowMapper followMapper;

    public List<FollowingResponse> getByFollowing(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followings = followRepository.findByUser(user);
        return followMapper.mapToFollowingResponse(
                followings,
                FollowEntity::getFollower,
                FollowingResponse::toFollowing
        );
    }

    public List<FollowingResponse> getByFollower(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followers = followRepository.findByFollower(user);
        return followMapper.mapToFollowingResponse(
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
}