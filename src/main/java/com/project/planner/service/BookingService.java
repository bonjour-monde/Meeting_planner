package com.project.planner.service;

import com.project.planner.dto.BookingResponse;
import com.project.planner.exception.RoomNotFoundException;
import com.project.planner.exception.UnboundTimeException;
import com.project.planner.model.Booking;
import com.project.planner.model.EquipmentType;
import com.project.planner.model.MeetingType;
import com.project.planner.model.Room;
import com.project.planner.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;


    @Transactional
    public BookingResponse bookRoom(int participants, LocalDate date, int time, MeetingType meetingType) {
        if (time < 0 || time > 11) {
            throw new UnboundTimeException("Time must be between 0 and 11");
        }

        var room = roomService.findBestRoom(meetingType, participants, date, time)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        Set<EquipmentType> missingEquipments = new HashSet<>(meetingType.getNecessaryEquipments());
        missingEquipments.removeAll(room.getEquipments());

        Booking booking = new Booking(room, date, time, missingEquipments);
        booking = bookingRepository.save(booking);

        return new BookingResponse(booking.getId(), room.getName(), date, time, missingEquipments);
    }
}
