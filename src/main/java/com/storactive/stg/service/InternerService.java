package com.storactive.stg.service;

import com.storactive.stg.exception.ValueAlreadyUsedException;
import com.storactive.stg.model.*;
import com.storactive.stg.repository.InternerRepo;
import com.storactive.stg.repository.UserRepo;
import com.storactive.stg.service.iService.IInternerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class InternerService implements IInternerService {

    final String OBJ = "Stagiaire";
    final InternerRepo internerRepo;
    final UserRepo userRepo;
    final HistoryService historySer;

    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public InternerService(InternerRepo internerRepo,
                           UserRepo userRepo,
                           HistoryService historySer, BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.internerRepo = internerRepo;
        this.historySer = historySer;
        this.pwdEncoder = pwdEncoder;
    }


    @Override
    public List<Interner> getAll() {
        return internerRepo.findAll();
    }

    public List<Task> getUserTasks(String username) {
        Interner interner = findByUsername(username);
        List<Task> tasks = new Vector<>();
        interner.getInternships().forEach(internship -> tasks.addAll(internship.getTasks()));
        return tasks;
    }


    public List<Absence> getUserAbsences(String username) {
        Interner interner = findByUsername(username);
        List<Absence> absences = new Vector<>();
        interner.getInternships().forEach(internship -> absences.addAll(internship.getAbsences()));
        return absences;
    }

    public List<StagePiece> getUserFiles(String username) {
        Interner interner = findByUsername(username);
        List<StagePiece> stagePieces = new Vector<>();
        interner.getInternships().forEach(internship -> stagePieces.addAll(internship.getStagePieces()));
        return stagePieces;
    }

    public Collection<Internship> getUserInternships(String username) {
        return findByUsername(username).getInternships();
    }

    @Override
    public Interner create(Interner interner) {
        Optional<User> user = userRepo
                .findByUsernameIgnoreCaseOrCinIgnoreCase
                        (interner.getUsername(), interner.getCin());
        if (user.isEmpty()) {
            interner.setPassword(pwdEncoder.encode(interner.getPassword()));
            Interner interner1 = internerRepo.save(interner);
            historySer.objetCreated(OBJ, interner1.getId());
            return interner1;
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
        Interner interner1 = internerRepo.save(interner);
        historySer.objetUpdated(OBJ, interner1.getId());
        return interner1;
    }


    @Override
    public void delete(Integer id) {
        if (!internerRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found");
        internerRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    @Override
    public Interner findById(int id) {
        return internerRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found"));
    }


    public Interner findByUsername(String username) {
        return internerRepo.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Interner Not Found"));
    }
}
