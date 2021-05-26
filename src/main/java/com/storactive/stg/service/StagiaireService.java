package com.storactive.stg.service;

import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.repository.StagiaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StagiaireService {

    final StagiaireRepo stagiaireRepo;
    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public StagiaireService(StagiaireRepo stagiaireRepo, BCryptPasswordEncoder pwdEncoder) {
        this.stagiaireRepo = stagiaireRepo;
        this.pwdEncoder = pwdEncoder;
    }


    public List<Stagiaire> getAll() {
        return stagiaireRepo.findAll();
    }

    public Stagiaire create(Stagiaire stagiaire) {
        stagiaire.setPassword(pwdEncoder.encode(stagiaire.getPassword()));
        return stagiaireRepo.save(stagiaire);
    }


    public Stagiaire update(Stagiaire stagiaire) {
        if (!stagiaireRepo.existsById(stagiaire.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        stagiaire.setPassword(pwdEncoder.encode(stagiaire.getPassword()));
        return stagiaireRepo.save(stagiaire);
    }


    public void delete(Integer id) {
        if (!stagiaireRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        stagiaireRepo.deleteById(id);
    }


    public Stagiaire findById(int id) {
        return stagiaireRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
