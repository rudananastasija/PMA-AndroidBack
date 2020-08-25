package rs.ftn.pma.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintException(ConstraintViolationException exception) {

        HashMap<String, String> message = new HashMap<>();

        if(exception.getSQLState().equals("23505")) {
            message.put("message", "duplicate value for entry");
        } else if (exception.getSQLState().equals("23502")) {
            message.put("message", "null value entered");
        }

        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
