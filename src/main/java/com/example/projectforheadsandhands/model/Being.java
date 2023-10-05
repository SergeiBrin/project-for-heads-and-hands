package com.example.projectforheadsandhands.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class Being {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Целое число от 1 до 30.
    @Min(1)
    @Max(30)
    private Integer attack;

    // Целое число от 1 до 30.
    @Min(1)
    @Max(30)
    private Integer defense;

    // Натуральное число от 0 до N. Если Здоровье становится равным 0, то Существо умирает.
    // Игрок может себя исцелить до 4-х раз на 30% от максимального Здоровья.
    @PositiveOrZero
    private Integer health;

    // Диапазон натуральных чисел M - N. Например, 1-6.
    @Min(1)
    @Column(name = "low_damage")
    private Integer lowDamage;

    @Max(6)
    @Column(name = "high_damage")
    private Integer highDamage;

}
