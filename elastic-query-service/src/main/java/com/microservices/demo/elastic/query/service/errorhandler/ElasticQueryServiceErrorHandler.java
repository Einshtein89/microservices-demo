package com.microservices.demo.elastic.query.service.errorhandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ElasticQueryServiceErrorHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(ElasticQueryServiceErrorHandler.class);

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<String> handle(AccessDeniedException ex)
  {
    LOG.error("Access denied!", ex);

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<String> handle(IllegalArgumentException ex)
  {
    LOG.error("Illegal argument exception!", ex);

    return ResponseEntity.badRequest().body("Illegal argument: " + ex.getMessage());
  }

  @ExceptionHandler(value = {RuntimeException.class})
  public ResponseEntity<String> handle(RuntimeException ex)
  {
    LOG.error("Runtime exception!", ex);

    return ResponseEntity.badRequest().body("Runtime exception: " + ex.getMessage());
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<String> handle(Exception ex)
  {
    LOG.error("Internal server error!", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occured!");
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException ex)
  {
    LOG.error("Method Argument Validation Exception!", ex);
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors()
        .forEach((error) -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

}
