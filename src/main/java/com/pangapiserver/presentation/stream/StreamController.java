package com.pangapiserver.presentation.stream;

import com.pangapiserver.infrastructure.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import com.pangapiserver.application.stream.StreamUseCase;
import org.springframework.web.bind.annotation.*;
import com.pangapiserver.application.stream.data.StreamListResponse;
import java.util.List;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamController {
    private final StreamUseCase useCase;

    @GetMapping("/items")
    public List<StreamListResponse> getItems() {
        return useCase.getItems();
    }

    @GetMapping("/items/popular")
    public List<StreamListResponse> getPopularItems() {
        return useCase.getPopularItems();
    }
}
