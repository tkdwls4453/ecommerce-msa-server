package com.msa.user.application.port.out;

import com.msa.user.domain.User;
import java.util.Optional;

public interface UserReadPort {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
