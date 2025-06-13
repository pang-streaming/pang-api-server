package com.pangapiserver.application.stream;

import com.pangapiserver.infrastructure.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.pangapiserver.domain.stream.entity.StreamEntity;
import com.pangapiserver.domain.stream.service.StreamService;
import com.pangapiserver.application.stream.data.StreamListResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StreamUseCase {
    private final StreamService service;

    public List<StreamListResponse> getItems() {
        List<StreamEntity> items = service.getAll();
        return StreamListResponse.of(items);
    }

    //TODO 바뀔 예정
    public List<StreamListResponse> getPopularItems() {
        List<StreamEntity> items = service.getAll();
        return StreamListResponse.of(items);
    }
}
