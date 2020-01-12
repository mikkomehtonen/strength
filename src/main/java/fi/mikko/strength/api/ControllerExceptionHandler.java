package fi.mikko.strength.api;

import fi.mikko.strength.exception.ErrorResponse;
import fi.mikko.strength.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(
      final RuntimeException ex, final WebRequest request) {
    log.info("BadRequest");
    return createResponse(ex, HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), request);
  }

  private ResponseEntity<Object> createResponse(
      final RuntimeException ex, final HttpStatus status,
      final String message, final WebRequest request) {

    return handleExceptionInternal(
        ex, new ErrorResponse(status.value(), message),
        new HttpHeaders(), status, request);
  }
}
