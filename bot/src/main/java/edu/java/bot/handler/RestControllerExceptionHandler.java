package edu.java.bot.handler;

import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice(basePackages = "edu.java.bot.controller")
public class RestControllerExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handle(HttpMessageNotReadableException e) {
        return badRequest()
            .body(new ApiErrorResponse(
                "Invalid request parameters",
                String.valueOf(BAD_REQUEST.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
            ));
    }
}
