package com.storactive.stg;

import com.storactive.stg.model.Employee;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;

public class Utils {


    public static void assertAuthorizedToResource(HttpServletRequest request, Interner owner) {
        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(owner))
            throw new AuthorizationServiceException(
                    "Not Authorized to this Resource"
            );
    }

    private static boolean isAuthorizedToInternship(Interner owner) {
        Interner loggedInterner = (Interner) Utils.getCurrUser();
        return owner.getId().equals(loggedInterner.getId());
    }


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

    public static int getPeriodInDaysBetween(Date d1, Date d2) {
        long date1 = d1.getTime();
        long date2 = d2.getTime();
        long diff = date2 - date1;

        return (int) (diff / (24 * 60 * 60 * 1000));
    }


    public static short getUserNature(User user) {
        if (user instanceof Employee)
            return 1;
        else if (user instanceof Interner)
            return 2;
        return 0;
    }

}
