package com.project.planner.dto;

import com.project.planner.model.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private MeetingType meetingType;
    private int participants;
    private LocalDate date;
    private int time;
}
