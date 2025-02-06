package com.project.planner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Table(name = "Rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Room {
    @Id
    private String name;

    @Column(nullable = false)
    private int capacity;

    @ElementCollection(targetClass = EquipmentType.class)
    @CollectionTable(name = "Room_Equipments", joinColumns = @JoinColumn(name = "room_name"))
    @Enumerated(EnumType.STRING)
    private Set<EquipmentType> equipments;
}