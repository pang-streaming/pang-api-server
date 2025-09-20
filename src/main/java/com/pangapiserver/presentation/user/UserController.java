package com.pangapiserver.presentation.user;

import com.pangapiserver.application.user.UserUseCase;
import com.pangapiserver.application.user.data.UpdateInfoRequest;
import com.pangapiserver.application.user.data.UserInfoResponse;
import com.pangapiserver.application.user.data.UserListResponse;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.infrastructure.common.dto.Response;
import com.pangapiserver.presentation.user.document.UserDocuments;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserDocuments {
    private final UserUseCase useCase;

    @Override
    @GetMapping("/me")
    public DataResponse<UserInfoResponse> getMyInfo() {
        return useCase.getMyInfo();
    }

    @Override
    @PatchMapping
    public void updateUser(@RequestBody UpdateInfoRequest request) {
        useCase.updateInfo(request);
    }

    @Override
    @GetMapping("/list")
    public DataResponse<List<UserListResponse>> getUsers() {
        return useCase.getUsers();
    }

    @Override
    @DeleteMapping
    public Response deleteUser() {
        return useCase.deleteUser();
    }
}
