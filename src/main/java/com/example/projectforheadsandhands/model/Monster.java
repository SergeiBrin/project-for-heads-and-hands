package com.example.projectforheadsandhands.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "monsters")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Monster extends Being {

}
