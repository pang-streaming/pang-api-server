package com.pangapiserver.application.stream;

import com.pangapiserver.application.stream.data.response.StreamInfoResponse;
import com.pangapiserver.application.stream.data.response.StreamResponse;
import com.pangapiserver.application.stream.data.response.StreamUserResponse;
import com.pangapiserver.domain.follow.entity.FollowEntity;
import com.pangapiserver.domain.follow.service.FollowService;
import com.pangapiserver.domain.stream.entity.StreamEntity;
import com.pangapiserver.domain.stream.entity.StreamKeyEntity;
import com.pangapiserver.domain.stream.service.StreamKeyService;
import com.pangapiserver.domain.stream.service.StreamService;
import com.pangapiserver.infrastructure.common.dto.DataResponse;
import com.pangapiserver.infrastructure.encode.Sha512Encoder;
import com.pangapiserver.infrastructure.security.support.UserAuthenticationHolder;
import com.pangapiserver.infrastructure.stream.properties.StreamProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class StreamUseCase {
    private final StreamService service;
    private final StreamKeyService keyService;
    private final FollowService followService;
    private final UserAuthenticationHolder holder;
    private final StreamProperties properties;

    public DataResponse<StreamInfoResponse> getStreamById(UUID streamId) {
        StreamEntity stream = service.getByStreamId(streamId, holder.current());
        int followers = followService.getFollowersByUsername(stream.getUser().getUsername()).size();
        boolean isFollowed = followService.isFollowing(holder.current(), stream.getUser());
        return DataResponse.ok("스트리밍 정보 조회 성공", StreamInfoResponse.of(stream, followers, isFollowed));
    }

    public DataResponse<List<StreamResponse>> getLiveStreams() {
        return DataResponse.ok("생방송중 목록 조회 성공", service.getLiveStreams().stream().map(StreamResponse::of).toList());
    }

    public DataResponse<List<StreamResponse>> getFollowingLiveStreams() {
        List<StreamResponse> streams = service.getLiveStreams().stream().map(StreamResponse::of).toList();
        List<FollowEntity> follows = followService.getFollowingEntitiesByUsername(holder.current().getUsername());

        Set<String> followingUsernames = follows.stream()
                .map(f -> f.getFollower().getUsername())
                .collect(Collectors.toSet());

        List<StreamResponse> response = streams.stream()
                .filter(stream -> followingUsernames.contains(stream.username()))
                .toList();
        return DataResponse.ok("생방송중 목록 조회 성공", response);
    }

    /** 스트림 키로 스트림 생성 */
    public DataResponse<StreamUserResponse> createStreamByKey(String key) {
        StreamKeyEntity byStreamKey = keyService.getByStreamKey(Sha512Encoder.encode(key));
        StreamEntity stream = StreamEntity.builder()
                .user(byStreamKey.getUser())
                .title(byStreamKey.getUser().getNickname() + "님의 방송")
                .url(properties.getUrl() + byStreamKey.getUser().getNickname())
                .build();
        service.save(stream);
        return DataResponse.ok("스트림 생성 성공", StreamUserResponse.of(byStreamKey));
    }
}
