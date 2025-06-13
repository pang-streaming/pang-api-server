package com.pangapiserver.presentation.follow;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.pangapiserver.application.follow.FollowUseCase;
import com.pangapiserver.infrastructure.common.dto.BaseResponse;
import com.pangapiserver.application.follow.data.FollowingResponse;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowUseCase useCase;

    @GetMapping("/following")
    public BaseResponse<List<FollowingResponse>> getFollowings(@RequestParam(name = "username", required = false) String username) {
        return useCase.getFollowings(username);
    }

    @GetMapping("/follower")
    public BaseResponse<List<FollowingResponse>> getFollowers(@RequestParam(name = "username", required = false) String username) {
        return useCase.getFollowers(username);
    }

    @PostMapping
    public BaseResponse<?> followOrUnfollow(@RequestParam("username") String username) {
        return useCase.followOrUnfollow(username);
    }
}
