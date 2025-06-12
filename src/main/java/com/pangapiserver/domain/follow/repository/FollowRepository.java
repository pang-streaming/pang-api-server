package com.pangapiserver.domain.follow.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.follow.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Integer> {
    List<FollowEntity> findByUser(UserEntity user);

    List<FollowEntity> findByFollower(UserEntity user);

    Optional<FollowEntity> findByUserAndFollower(UserEntity user, UserEntity follower);

    @Query("SELECT f.follower.id, COUNT(f) " +
            "FROM FollowEntity f " +
            "WHERE f.follower.id IN :ids " +
            "GROUP BY f.follower.id")
    List<Object[]> countByFollowerIds(@Param("ids") List<UUID> ids);
}
