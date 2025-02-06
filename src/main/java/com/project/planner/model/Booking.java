package com.project.planner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Bookings")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
public class Booking {
    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    @JoinColumn(name = "name", nullable = false)
    @NonNull
    private Room room;

    @Column(nullable = false)
    @NonNull
    private LocalDate date;

    @Column(nullable = false)
    @NonNull
    private Integer time;

    @ElementCollection(targetClass = EquipmentType.class)
    @CollectionTable(name = "Booking_Movable_Equipments", joinColumns = @JoinColumn(name = "booking_id"))
    @Enumerated(EnumType.STRING)
    @NonNull
    private Set<EquipmentType> movableEquipments;

}