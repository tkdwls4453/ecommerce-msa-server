package com.msa.user.application.port.out;

import com.msa.user.domain.User;

public interface UserRegisterPort {
    User save(User user);
}
