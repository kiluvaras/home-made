package ee.priit.pall.homemade.exception;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  private ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
    // TODO: add logging
    String message = e.getMessage() == null ? "" : e.getMessage();
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new RestError(message, ErrorCode.ENTITY_NOT_FOUND));
  }

  @ExceptionHandler(Exception.class)
  private ResponseEntity<Object> handleAllOtherExceptions(Exception e) {
    String message = e.getMessage() == null ? "" : e.getMessage();

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new RestError(message, ErrorCode.INTERNAL_SERVER_ERROR));
  }
}
