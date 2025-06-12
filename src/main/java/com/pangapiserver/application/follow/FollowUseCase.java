package com.pangapiserver.application.follow;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.service.FollowService;
import com.pangapiserver.infrastructure.common.dto.BaseResponse;
import com.pangapiserver.application.follow.data.FollowingResponse;
import com.pangapiserver.infrastructure.security.support.UserAuthenticationHolder;

@Component
@RequiredArgsConstructor
public class FollowUseCase {
    private final FollowService service;
    private final UserAuthenticationHolder userAuthHolder;

    public BaseResponse<List<FollowingResponse>> getFollowings(String username) {
        return BaseResponse.ok(HttpStatus.OK, service.getByFollowing(isNull(username)));
    }

    public BaseResponse<List<FollowingResponse>> getFollowers(String username) {
        return BaseResponse.ok(HttpStatus.OK, service.getByFollower(isNull(username)));
    }

    public BaseResponse<?> followOrUnfollow(String username) {
        UserEntity user = userAuthHolder.current();
        service.followOrNot(user, username);
        return BaseResponse.ok(HttpStatus.OK, null);
    }

    private String isNull(String username) {
        if (username == null) {
            username = userAuthHolder.current().getUsername();
        }
        return username;
    }
}
