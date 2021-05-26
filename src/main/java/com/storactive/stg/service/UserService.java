package com.storactive.stg.service;


import com.storactive.stg.model.User;
import com.storactive.stg.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.pwdEncoder = pwdEncoder;
    }



    public User create(User user) {
        user.setId(null);
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }



}
