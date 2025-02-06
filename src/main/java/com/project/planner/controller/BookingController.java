package com.project.planner.controller;

import com.project.planner.model.MeetingType;
import com.project.planner.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class BookingController {


    @Autowired
    private BookingService bookingService;


    @PostMapping("/book")
    public Optional<String> book(@RequestParam MeetingType meetingType,
                                 @RequestParam int participants,
                                 @RequestParam LocalDate date,
                                 @RequestParam int time) {

        return bookingService.bookRoom(participants, date, time, meetingType);
    }

}
