package com.pangapiserver.presentation.user.document;

import com.pangapiserver.application.user.data.UpdateInfoRequest;
import com.pangapiserver.application.user.data.UserInfoResponse;
import com.pangapiserver.application.user.data.UserListResponse;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.infrastructure.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Tag(name = "유저 Api", description = "유저 api")
public interface UserDocuments {
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 목록 조회 API", description = "유저 목록을 조회합니다.")
    DataResponse<UserInfoResponse> getMyInfo();

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 정보 수정 API", description = "유저 정보를 수정합니다.")
    void updateUser(UpdateInfoRequest request);

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 목록 조회 API", description = "유저 목록을 조회합니다.")
    DataResponse<List<UserListResponse>> getUsers();

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴를 합니다.")
    Response deleteUser();
}
