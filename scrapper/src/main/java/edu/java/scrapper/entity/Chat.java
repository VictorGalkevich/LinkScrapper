package edu.java.scrapper.entity;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    private Long id;
    private List<Link> links;
    private OffsetDateTime createdAt;
}
