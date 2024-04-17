package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponseDto(
        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("pushed_at")
        OffsetDateTime updatedAt) {

}
