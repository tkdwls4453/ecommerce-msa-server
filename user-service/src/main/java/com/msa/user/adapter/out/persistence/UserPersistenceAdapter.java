package com.msa.user.adapter.out.persistence;

import com.msa.user.application.port.out.UserReadPort;
import com.msa.user.application.port.out.UserRegisterPort;
import com.msa.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRegisterPort, UserReadPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity savedEntity = userJpaRepository.save(UserEntity.builder()
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .build());


        return savedEntity.toDomain();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).orElse(null);

        return userEntity == null ? Optional.empty() : Optional.of(userEntity.toDomain());
    }
}
