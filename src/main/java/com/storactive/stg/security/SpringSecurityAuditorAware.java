package com.storactive.stg.security;

import com.storactive.stg.Utils;
import com.storactive.stg.model.User;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.of(Utils.getCurrUser());
    }

}