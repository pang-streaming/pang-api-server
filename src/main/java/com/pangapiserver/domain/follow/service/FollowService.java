package com.pangapiserver.domain.follow.service;

import java.util.List;
import java.util.Optional;

import com.pangapiserver.application.follow.data.FollowingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.entity.FollowEntity;
import com.pangapiserver.domain.user.repository.UserRepository;
import com.pangapiserver.domain.follow.repository.FollowRepository;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<FollowingResponse> getByFollowing(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followings = followRepository.findByUser(user);
        return streamGetFollow(followings);
    }

    public List<FollowingResponse> getByFollower(String username) {
        UserEntity user = userRepository.findByUsername(username);
        List<FollowEntity> followers = followRepository.findByFollower(user);
        return followers.stream()
                .map(follow ->
                        FollowingResponse.toFollower(
                                follow,
                                followRepository.countByFollower(follow.getUser())
                        )
                ).toList();
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

    private List<FollowingResponse> streamGetFollow(List<FollowEntity> follows) {
        return follows.stream()
                .map(follow ->
                        FollowingResponse.toFollowing(
                                follow,
                                followRepository.countByFollower(follow.getFollower())
                        )
                ).toList();
    }
}

