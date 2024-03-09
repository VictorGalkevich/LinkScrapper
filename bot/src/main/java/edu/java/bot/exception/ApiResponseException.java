package edu.java.bot.exception;

import edu.java.dto.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiResponseException extends RuntimeException {
    private final ApiErrorResponse response;
}
