package com.project.planner.service;

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
    public Optional<String> bookRoom(int participants, LocalDate date, int time, MeetingType meetingType) {

        if (time < 0 || time > 11) {
            throw new UnboundTimeException("Time must be between 0 and 11");
        }

        var room = roomService.findBestRoom(meetingType, participants, date, time);

        if (room.isPresent()) {

            Set<EquipmentType> difference = new HashSet<>(meetingType.getNecessaryEquipments());
            difference.removeAll(room.get().getEquipments());

            bookingRepository.save(new Booking(room.get(), date, time, difference));

            return room.map(Room::getName);
        }

        throw new RoomNotFoundException("Room not found");
    }
}
