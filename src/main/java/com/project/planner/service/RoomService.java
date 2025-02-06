package com.project.planner.service;

import com.project.planner.exception.NoPossibleRoomException;
import com.project.planner.exception.UnboundTimeException;
import com.project.planner.model.*;
import com.project.planner.repository.BookingRepository;
import com.project.planner.repository.MovableEquipmentRepository;
import com.project.planner.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovableEquipmentRepository movableEquipmentRepository;

    public Optional<Room> findBestRoom(MeetingType meetingType, int participants, LocalDate date, int time) {

        List<Room> possibleRooms = roomRepository.findAll().stream()
                .filter(room -> room.getCapacity() >= participants)
                .filter(room -> isRoomAvailable(room, date, time))

                .toList();

        if (possibleRooms.isEmpty()) {
            throw new NoPossibleRoomException("No room fit the capacity and availability requirement");
        }


        var bestRoom = possibleRooms.stream()
                .filter(room -> isRoomSuitable(room, meetingType))
                .min(Comparator.comparing(Room::getCapacity)
                        .thenComparing(room -> room.getEquipments().size()));

        if (bestRoom.isEmpty() && !meetingType.equals(MeetingType.SPEC)) {
            // impossible to find a room without moving equipments

            List<Booking> existingBookings = bookingRepository.findByDateAndTime(date, time);

            var necessaryEquipments = meetingType.getNecessaryEquipments();

            Set<EquipmentType> availableMoveableEquipments = movableEquipmentRepository.findAll().stream()
                    .filter(movableEquipment -> necessaryEquipments.contains(movableEquipment.getName()))
                    .filter(movableEquipment -> isMovableEquipmentAvailable(movableEquipment, existingBookings))
                    .map(MovableEquipment::getName)
                    .collect(Collectors.toSet());

            bestRoom = possibleRooms.stream()
                    .filter(room -> {
                        var intersection = new HashSet<>(availableMoveableEquipments);
                        intersection.addAll(room.getEquipments());

                        return meetingType.isMeetingPossible(intersection, 0);
                    }).findAny();
        }


        return bestRoom;
    }

    private boolean isMovableEquipmentAvailable(MovableEquipment equipment, List<Booking> existingBookings) {

        var countEquipment = existingBookings.stream()
                .flatMap(booking -> booking.getMovableEquipments().stream())
                .filter(eq -> eq.equals(equipment.getName()))
                .count();

        return equipment.getAvailableQuantity() > countEquipment;
    }


    private boolean isRoomAvailable(Room room, LocalDate date, int time) {
        List<Booking> existingBookings = bookingRepository.findByRoomAndDate(room, date);

        return existingBookings.stream().noneMatch(booking -> Math.abs(time - booking.getTime()) <= 1);
    }
    private boolean isRoomSuitable(Room room, MeetingType meetingType) {
        return meetingType.isMeetingPossible(room.getEquipments(), room.getCapacity());
    }
}