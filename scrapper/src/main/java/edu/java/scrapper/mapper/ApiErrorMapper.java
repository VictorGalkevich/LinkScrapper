package edu.java.scrapper.mapper;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.scrapper.exception.ScrapperException;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ApiErrorMapper {
    public ApiErrorResponse map(ScrapperException object, String description) {
        return new ApiErrorResponse(
                description,
                String.valueOf(object.getStatus().value()),
                object.getClass().getSimpleName(),
                object.getMessage(),
                Arrays.stream(object.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
