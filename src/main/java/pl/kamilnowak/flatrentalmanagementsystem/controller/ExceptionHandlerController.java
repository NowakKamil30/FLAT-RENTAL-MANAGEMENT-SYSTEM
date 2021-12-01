package pl.kamilnowak.flatrentalmanagementsystem.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kamilnowak.flatrentalmanagementsystem.exception.CannotCreateLoginUserException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.Entity.JsonError;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.TokenIsTooOldException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class ExceptionHandlerController {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<JsonError> notFoundErrorHandler(NotFoundException notFoundException) {
        log.error("not found object id:" + notFoundException.getMessage());
        return new ResponseEntity<>(JsonError.builder()
                .message(notFoundException.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TokenIsTooOldException.class})
    public ResponseEntity<JsonError> tokenIsTooOldErrorHandler(TokenIsTooOldException tokenIsTooOldException) {
        log.error("token is too old:" + tokenIsTooOldException.getMessage());
        return new ResponseEntity<>(JsonError.builder()
                .message(tokenIsTooOldException.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CannotCreateLoginUserException.class})
    public ResponseEntity<JsonError> cannotCreateLoginUserErrorHandler(CannotCreateLoginUserException cannotCreateLoginUserException) {
        log.error("cannot create login user:" + cannotCreateLoginUserException.getMessage());
        return new ResponseEntity<>(JsonError.builder()
                .message(cannotCreateLoginUserException.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
