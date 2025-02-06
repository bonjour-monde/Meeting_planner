package com.project.planner.dto;

import com.project.planner.model.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private String roomName;
    private LocalDate date;
    private int time;
    private Set<EquipmentType> missingEquipments;
}
