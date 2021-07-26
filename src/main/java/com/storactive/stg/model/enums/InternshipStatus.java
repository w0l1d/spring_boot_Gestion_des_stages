package com.storactive.stg.model.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

public enum InternshipStatus {
    IN_PROGRESS(1, "form.status.in_progress", "En cours"),
    SAVED(2, "form.status.saved", "Enregistré"),
    FINISHED(3, "form.status.finished", "Terminé"),
    CANCELED(4, "form.status.canceled", "Annulé");

    private final String description;
    private final String frValue;
    private int value;
    private MessageSource messageSource;

    InternshipStatus(int value, String description, String frValue) {
        this.value = value;
        this.description = description;
        this.frValue = frValue;
    }

    public static InternshipStatus valueOf(int s) {
        for (InternshipStatus status : InternshipStatus.values()) {
            if (status.value == s)
                return status;
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFrValue() {
        return frValue;
    }

    public String getDescription() {
        return messageSource.getMessage(description, null, description, null);
    }

    public InternshipStatus setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    @Component
    public static class EnumValuesInjectionService {

        @Autowired
        private MessageSource messageSource;

        //Inject into bean through static inner class and assign value to enum.
        @PostConstruct
        public void postConstruct() {

            for (InternshipStatus internshipStatus : EnumSet.allOf(InternshipStatus.class)) {
                internshipStatus.setMessageSource(messageSource);
            }
        }
    }
}
