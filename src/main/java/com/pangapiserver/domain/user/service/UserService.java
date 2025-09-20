package com.pangapiserver.domain.user.service;

import com.pangapiserver.application.follow.data.FollowerCountResponse;
import com.pangapiserver.domain.follow.repository.FollowCustomRepositoryImpl;
import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.domain.user.exception.UserAlreadyExistException;
import com.pangapiserver.domain.user.exception.UserNotFoundException;
import com.pangapiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final FollowCustomRepositoryImpl followCustomRepository;

    public void update(UserEntity user) {
        repository.save(user);
    }

    public void create(UserEntity user) {
        repository.save(user);
    }

    public void validateByUsernameAndEmail(String username, String email) {
        if (repository.findByUsername(username).isPresent() || getByEmail(email) != null) throw new UserAlreadyExistException();
    }

    public UserEntity getByUsername(String username) {
        return repository.findByUsername(username)
            .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<UserEntity> getUsers(UUID id) {
        return repository.findAllByIdNot(id);
    }

    public List<FollowerCountResponse> getFollowers(UUID id) {
        List<UUID> userIds = repository.findAllByIdNot(id).stream()
            .map(UserEntity::getId)
            .toList();
        return followCustomRepository.countByFollowerIds(userIds).entrySet().stream()
            .map(set -> new FollowerCountResponse(
                    set.getKey(),
                    set.getValue()
                )
            ).collect(Collectors.toList());
    }

    public void deleteByUser(UserEntity user) {
        followCustomRepository.deleteByUserOrFollower(user);
        repository.delete(user);
    }
}
