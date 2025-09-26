package com.pangapiserver.presentation.video;

import com.pangapiserver.application.stream.data.response.StreamResponse;
import com.pangapiserver.application.video.VideoUseCase;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.presentation.video.document.VideoDocuments;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController implements VideoDocuments {
    private final VideoUseCase useCase;

    @Override
    @GetMapping("/watched")
    public DataResponse<List<StreamResponse>> getWatchedVideos() {
        return useCase.getWatchedVideos();
    }
}
