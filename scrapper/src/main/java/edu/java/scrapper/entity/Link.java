package edu.java.scrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Link {
    private Long id;
    private String uri;
    private String host;
    private String protocol;
}
