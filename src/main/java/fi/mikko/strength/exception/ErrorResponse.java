package fi.mikko.strength.exception;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse {

  private final int statusCode;
  private final List<String> messages;

  public ErrorResponse(final int statusCode, final String message) {
    this.statusCode = statusCode;
    this.messages = Arrays.asList(message);
  }
}
