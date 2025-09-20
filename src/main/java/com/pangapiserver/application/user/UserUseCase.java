package com.pangapiserver.application.user;

import com.pangapiserver.application.follow.data.FollowerCountResponse;
import com.pangapiserver.application.user.data.UpdateInfoRequest;
import com.pangapiserver.application.user.data.UserInfoResponse;
import com.pangapiserver.application.user.data.UserListResponse;
import com.pangapiserver.domain.cash.service.CashService;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.user.service.UserService;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.infrastructure.common.dto.Response;
import com.pangapiserver.infrastructure.security.support.UserAuthenticationHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class UserUseCase {
    private final UserAuthenticationHolder holder;
    private final UserService service;
    private final CashService cashService;

    public DataResponse<UserInfoResponse> getMyInfo() {
        UserEntity user = holder.current();
        return DataResponse.ok("내 정보 조회 성공", UserInfoResponse.of(user, cashService.getBalance(user)));
    }

    public void updateInfo(UpdateInfoRequest request) {
        UserEntity user = holder.current();
        user.updateInfo(request.nickname(), request.age(), request.gender(), request.profileImage(), request.bannerImage(),
            request.description());
        service.update(user);
    }

    public DataResponse<List<UserListResponse>> getUsers() {
        UserEntity me = holder.current();
        List<UserEntity> users = service.getUsers(me.getId());
        List<FollowerCountResponse> followers = service.getFollowers(me.getId());
        return DataResponse.ok("유저 목록 조회 성공", UserListResponse.of(users, followers));
    }

    public Response deleteUser() {
        UserEntity user = holder.current();
        service.deleteByUser(user);
        return Response.ok("회원 탈퇴 성공");
    }
}
