package com.pangapiserver.domain.user.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import jakarta.persistence.*;
import com.pangapiserver.domain.user.enumeration.Role;
import com.pangapiserver.domain.user.enumeration.Gender;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private LocalDate age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String bannerImage;

    private boolean isAdult;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<UserRoleEntity> roles;

    private boolean isAlarm;

    public void updateInfo(String nickname, LocalDate age, Gender gender, String profileImage, String bannerImage) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (age != null) {
            this.age = age;
        }
        if (gender != null) {
            this.gender = gender;
        }
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
        if (bannerImage != null) {
            this.bannerImage = bannerImage;
        }
    }

    @Builder
    public UserEntity(String username, String email, String nickname, String password, LocalDate age, Gender gender, String profileImage, String bannerImage, boolean isAdult, List<Role> roles, boolean isAlarm) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.profileImage = profileImage;
        this.bannerImage = bannerImage;
        this.isAdult = isAdult;
        this.roles = roles.stream().map(UserRoleEntity::new).toList();
        this.isAlarm = isAlarm;
    }
}