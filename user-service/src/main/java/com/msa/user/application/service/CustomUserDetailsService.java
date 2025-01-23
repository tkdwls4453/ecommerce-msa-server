package com.msa.user.application.service;

import com.msa.user.application.port.out.UserReadPort;
import com.msa.user.domain.User;
import com.msa.user.exception.EmailNotFoundException;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserReadPort userReadPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userReadPort.findByEmail(email)
            .orElseThrow(EmailNotFoundException::new);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(),
            true, true, true, true,
            new ArrayList<>());
    }

    public User getUserByEmail(String email){
        User user = userReadPort.findByEmail(email).orElse(null);

        if(user == null){
            throw new EmailNotFoundException();
        }

        return user;
    }
}
