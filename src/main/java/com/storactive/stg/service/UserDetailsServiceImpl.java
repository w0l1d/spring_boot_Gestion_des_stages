package com.storactive.stg.service;

import com.storactive.stg.model.User;
import com.storactive.stg.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("username not found : " + username)
        );

        if (!user.isEnabled())
            throw new DisabledException("account is disabled");
        return user;
    }

}
