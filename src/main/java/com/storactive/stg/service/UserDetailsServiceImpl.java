package com.storactive.stg.service;

import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.model.User;
import com.storactive.stg.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo, BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.pwdEncoder = pwdEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("username not found : " + username)
        );

        if (isStagiaire(user) && !((Stagiaire) user).isEnabled())
            throw new DisabledException("account is disabled");
        return org.springframework.security.core.userdetails
                .User.withUsername(username).password(user.getPassword())
                .roles(user instanceof Stagiaire?"STAGIAIRE":"ADMIN").build();
    }

    public boolean isStagiaire(User user) {
        return user instanceof Stagiaire;
    }

}
