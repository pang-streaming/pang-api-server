package com.pangapiserver.infrastructure.redis.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaveType {
    STREAM_KEY("stream_key::"),
    WATCH_HISTORY("watch_history::"),
    ;
    private final String value;
}