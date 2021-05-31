package com.storactive.stg.service;

import com.storactive.stg.exception.ValueAlreadyUsedException;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import com.storactive.stg.repository.InternerRepo;
import com.storactive.stg.repository.UserRepo;
import com.storactive.stg.service.iService.IInternerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InternerService implements IInternerService {

    final InternerRepo internerRepo;
    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public InternerService(InternerRepo internerRepo,
                           UserRepo userRepo,
                           BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.internerRepo = internerRepo;
        this.pwdEncoder = pwdEncoder;
    }


    @Override
    public List<Interner> getAll() {
        return internerRepo.findAll();
    }

    @Override
    public Interner create(Interner interner) {
        Optional<User> user = userRepo
                .findByUsernameIgnoreCaseOrCinIgnoreCase
                        (interner.getUsername(), interner.getCin());
        if (user.isEmpty()) {
            interner.setPassword(pwdEncoder.encode(interner.getPassword()));
            return internerRepo.save(interner);
        }
        User user1 = user.orElse(null);
        if (user1.getCin().equals(interner.getCin()))
            throw new ValueAlreadyUsedException("cin '"+user1.getCin()+"' Already Exists");
        throw new ValueAlreadyUsedException("Username '"+user1.getUsername()+"' Already Exists");
    }


    @Override
    public Interner update(Interner interner) {
        if (!internerRepo.existsById(interner.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found");
        interner.setPassword(pwdEncoder.encode(interner.getPassword()));
        return internerRepo.save(interner);
    }


    @Override
    public void delete(Integer id) {
        if (!internerRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found");
        internerRepo.deleteById(id);
    }


    @Override
    public Interner findById(int id) {
        return internerRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found"));
    }

}
