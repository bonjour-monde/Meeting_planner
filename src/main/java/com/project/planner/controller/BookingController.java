package com.project.planner.controller;

import com.project.planner.dto.BookingRequest;
import com.project.planner.dto.BookingResponse;
import com.project.planner.model.MeetingType;
import com.project.planner.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.bookRoom(
                request.getParticipants(),
                request.getDate(),
                request.getTime(),
                request.getMeetingType()
        );
        return ResponseEntity.ok(response);
    }

}
