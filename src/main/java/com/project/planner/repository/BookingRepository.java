package com.project.planner.repository;

import com.project.planner.model.Booking;
import com.project.planner.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomAndDate(Room room, LocalDate date);

    List<Booking> findByDateAndTime(LocalDate date, int time);
}
