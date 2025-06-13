package com.pangapiserver.application.follow.data;

import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.entity.FollowEntity;

public record FollowingResponse (
        String image,
        String nickname,
        Long follower
) {
    public static FollowingResponse toFollowing(FollowEntity follow, Long followerCount) {
        UserEntity follower = follow.getFollower();
        return new FollowingResponse(
                follower.getProfileImage(),
                follower.getNickname(),
                followerCount
        );
    }

    public static FollowingResponse toFollower(FollowEntity follow, Long followerCount) {
        UserEntity following = follow.getUser();
        return new FollowingResponse(
                following.getProfileImage(),
                following.getNickname(),
                followerCount
        );
    }
}
