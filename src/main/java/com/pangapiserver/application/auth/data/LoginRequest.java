package com.pangapiserver.application.auth.data;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    String id,
    @NotBlank
    String password
) { }