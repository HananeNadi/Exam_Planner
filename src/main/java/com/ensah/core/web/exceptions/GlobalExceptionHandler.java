package com.ensah.core.web.exceptions;


import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>("Entity not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>("Constraint violation: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistenceException(PersistenceException ex) {
        return new ResponseEntity<>("Persistence error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // Generic handler for any other exceptions
    @ExceptionHandler
    public ResponseEntity<ErrorPerson> handleExceptionPerson(Exception ex) {

        logger.error("error : ", ex);

        ErrorPerson err = new ErrorPerson();
        err.setCodeError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setMesssage("Erreur inconnue !");
        err.setDescription("Erreur inconnue, veuillez res....");

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler
    public ResponseEntity<ErroreElement> handleExceptionElement(Exception ex) {

        logger.error("error : ", ex);

        ErroreElement err = new ErroreElement();
        err.setCodeError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setMesssage("Erreur inconnue !");
        err.setDescription("Erreur inconnue, veuillez res....");
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}