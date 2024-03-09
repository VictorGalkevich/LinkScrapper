package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponseDto(List<Question> items) {
    public record Question(
        String title,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {
    }
}
