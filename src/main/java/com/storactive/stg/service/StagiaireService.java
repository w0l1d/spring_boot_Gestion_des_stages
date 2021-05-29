package com.storactive.stg.service;

import com.storactive.stg.exception.ValueAlreadyUsedException;
import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.model.User;
import com.storactive.stg.repository.StagiaireRepo;
import com.storactive.stg.repository.UserRepo;
import com.storactive.stg.service.iService.IStagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StagiaireService implements IStagiaireService {

    final StagiaireRepo stagiaireRepo;
    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public StagiaireService(StagiaireRepo stagiaireRepo,
                             UserRepo userRepo,
                             BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.stagiaireRepo = stagiaireRepo;
        this.pwdEncoder = pwdEncoder;
    }


    @Override
    public List<Stagiaire> getAll() {
        return stagiaireRepo.findAll();
    }

    @Override
    public Stagiaire create(Stagiaire stagiaire) {
        Optional<User> user = userRepo
                .findByUsernameIgnoreCaseOrCinIgnoreCase
                        (stagiaire.getUsername(), stagiaire.getCin());
        if (user.isEmpty()) {
            stagiaire.setPassword(pwdEncoder.encode(stagiaire.getPassword()));
            return stagiaireRepo.save(stagiaire);
        }
        User user1 = user.orElse(null);
        if (user1.getCin().equals(stagiaire.getCin()))
            throw new ValueAlreadyUsedException("cin '"+user1.getCin()+"' Already Exists");
        throw new ValueAlreadyUsedException("Username '"+user1.getUsername()+"' Already Exists");
    }


    @Override
    public Stagiaire update(Stagiaire stagiaire) {
        if (!stagiaireRepo.existsById(stagiaire.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        stagiaire.setPassword(pwdEncoder.encode(stagiaire.getPassword()));
        return stagiaireRepo.save(stagiaire);
    }


    @Override
    public void delete(Integer id) {
        if (!stagiaireRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        stagiaireRepo.deleteById(id);
    }


    @Override
    public Stagiaire findById(int id) {
        return stagiaireRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
