package com.msa.user.application.port.out;

public interface UserReadPort {
    Boolean existsByEmail(String email);
}
