package com.pangapiserver.application.video;

import com.pangapiserver.application.stream.data.response.StreamResponse;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.video.service.VideoService;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.infrastructure.security.support.UserAuthenticationHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoUseCase {
    private final VideoService service;
    private final UserAuthenticationHolder holder;

    public DataResponse<List<StreamResponse>> getWatchedVideos() {
        UserEntity user = holder.current();
        List<StreamResponse> videos = service.getWatchedVideos(user.getId()).stream()
                .map(StreamResponse::of)
                .toList();
        return DataResponse.ok("최근 본 동영상 조회 성공", videos);
    }
}
