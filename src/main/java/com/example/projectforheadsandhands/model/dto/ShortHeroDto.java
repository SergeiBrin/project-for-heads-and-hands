package com.example.projectforheadsandhands.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortHeroDto {
    private Long id;
    private String name;
    private Integer health;
    private Integer recovery;
}
