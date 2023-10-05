package com.example.projectforheadsandhands.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "heroes")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Hero extends Being {
    @Column(name = "original_health")
    private Integer originalHealth;

    private Integer recovery;
}
