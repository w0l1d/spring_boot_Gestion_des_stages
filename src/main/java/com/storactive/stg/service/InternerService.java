package com.storactive.stg.service;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import com.storactive.stg.repository.InternerRepo;
import com.storactive.stg.repository.UserRepo;
import com.storactive.stg.service.iService.IInternerService;
import com.storactive.stg.specs.InternerContainSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InternerService implements IInternerService {


    @Value("${spring.custom.select2.page-size}")
    private Integer MAX_PAGE_SIZE;
    @Value("${spring.custom.select2.limited-results-number}")
    private boolean LIMITED_NUMBER;

    final String OBJ = "Stagiaire";
    final InternerRepo internerRepo;
    final UserRepo userRepo;
    final HistoryService historySer;

    final BCryptPasswordEncoder pwdEncoder;


    public long countAll() {
        return internerRepo.count();
    }


    public long countAllActive() {
        return internerRepo.countInActiveInternship();
    }


    public Interner findByIdAndCredentialsUnchanged(User user) {
        return internerRepo.findByIdAndUsernameAndEnabledIsTrue(user.getId(), user.getUsername()).orElse(null);
    }

    public Page<Interner> findAllContains(String s) {
        Pageable pageable = (LIMITED_NUMBER) ?
                Pageable.ofSize(MAX_PAGE_SIZE)
                :
                Pageable.unpaged();
        return internerRepo.findAll(InternerContainSpec.getInternerSpec(s), pageable);

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cin '" + user1.getCin() + "' Already Exists");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username '" + user1.getUsername() + "' Already Exists");
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
