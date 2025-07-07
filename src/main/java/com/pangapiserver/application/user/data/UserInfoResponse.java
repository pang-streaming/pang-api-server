package com.pangapiserver.application.user.data;

import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.user.enumeration.Gender;
import com.pangapiserver.domain.user.enumeration.Role;

import java.time.LocalDate;
import java.util.UUID;

public record UserInfoResponse(
    UUID id,
    String username,
    String email,
    String nickname,
    LocalDate age,
    Gender gender,
    String profileImage,
    String bannerImage,
    boolean isAdult,
    Role role,
    boolean isAlarm
) {
    public static UserInfoResponse of(UserEntity user) {
        return new UserInfoResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getNickname(),
            user.getAge(),
            user.getGender(),
            user.getProfileImage(),
            user.getBannerImage(),
            user.isAdult(),
            user.getRoles(),
            user.isAlarm()
        );
    }
}
