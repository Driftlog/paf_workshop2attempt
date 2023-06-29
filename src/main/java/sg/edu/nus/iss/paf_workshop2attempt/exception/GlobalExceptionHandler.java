package sg.edu.nus.iss.paf_workshop2attempt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorMessage> recordNotFound(HttpServletRequest req, RecordNotFoundException ex) {
        ErrorMessage errMessage = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), req.getRequestURI());

        return new ResponseEntity<ErrorMessage>(errMessage, HttpStatus.NOT_FOUND);
}

}