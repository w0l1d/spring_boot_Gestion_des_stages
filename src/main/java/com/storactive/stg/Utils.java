package com.storactive.stg;

import com.storactive.stg.model.Employee;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Utils {

    @Value("#{user.nature.user}")
    private static String USER_USER;
    @Value("#{user.nature.interner}")
    private static String USER_INTERNER;
    @Value("#{user.nature.employee}")
    private static String USER_EMPLOYEE;


    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static User getCurrUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public static LocalDate ToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static int getPeriodInDaysBetween(Date date11, Date date22) {
        LocalDate date1 = Utils.ToLocalDate(date11);
        LocalDate date2 = Utils.ToLocalDate(date22);
        Period period = Period.between(date1, date2);
        return period.getDays();
    }


    public static short getUserNature(User user) {
        if (user instanceof Employee)
            return 1;
        else if (user instanceof Interner)
            return 2;
        return 0;
    }

}
