package com.storactive.stg.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileUploadBase;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.http.HttpStatus.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@ControllerAdvice
public class MyExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MessageSource messageSource;
    private final String ERROR_VIEW = "error";

    @Autowired
    public MyExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler({
            FileUploadBase.SizeLimitExceededException.class,
            MaxUploadSizeExceededException.class
    })
    public ModelAndView SizeLimitExceededExceptionException(Model model,
                                                            FileUploadBase.SizeLimitExceededException ex) {
        HttpStatus status = BAD_REQUEST;

        ModelAndView view = new ModelAndView();

        model.addAllAttributes(getBody(status, ex, ex.getMessage()));
        view.setStatus(status);
        view.setViewName(ERROR_VIEW);
        return view;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(Model model,
                                                           ConstraintViolationException ex) {
        HttpStatus status = BAD_REQUEST;

        ModelAndView view = new ModelAndView();

        model.addAllAttributes(getBody(status, ex, ""));
        view.setStatus(status);
        view.setViewName(ERROR_VIEW);
        return view;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandlerFoundException(Model model,
                                                      ConstraintViolationException ex) {
        HttpStatus status = NOT_FOUND;

        ModelAndView view = new ModelAndView();

        model.addAllAttributes(getBody(status, ex, ex.getMessage()));
        view.setStatus(status);
        view.setViewName(ERROR_VIEW);
        return view;
    }


    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "Please enter a valid value"), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(getBody(FORBIDDEN, ex, ex.getMessage()), new HttpHeaders(), FORBIDDEN);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> exception(Exception ex) {
//        System.out.println("/***** global exception");
//        return new ResponseEntity<>(getBody(INTERNAL_SERVER_ERROR, ex, ex.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
//    }

    public Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {

        log.error(message, ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex);

        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }
        return body;
    }
}