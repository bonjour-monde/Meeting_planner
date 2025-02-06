package com.project.planner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Movable_equipments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MovableEquipment {
    @Id
    @Enumerated(EnumType.STRING)
    private EquipmentType name;

    @Column(nullable = false)
    private int availableQuantity;
}