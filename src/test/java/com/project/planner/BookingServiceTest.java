package com.project.planner;

import com.project.planner.dto.BookingResponse;
import com.project.planner.exception.RoomNotFoundException;
import com.project.planner.exception.UnboundTimeException;
import com.project.planner.model.Booking;
import com.project.planner.model.EquipmentType;
import com.project.planner.model.MeetingType;
import com.project.planner.model.Room;
import com.project.planner.repository.BookingRepository;
import com.project.planner.service.BookingService;
import com.project.planner.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;


    @Test
    void shouldBookRoomWhenAvailable() {
        Room room = new Room("Room A", 10, Set.of(EquipmentType.SCREEN));
        LocalDate date = LocalDate.of(2025, 2, 10);
        int time = 3;
        Set<EquipmentType> missingEquipments = Set.of();

        when(roomService.findBestRoom(any(), anyInt(), any(), anyInt()))
                .thenReturn(Optional.of(room));

        Booking booking = new Booking(room, date, time, missingEquipments);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse result = bookingService.bookRoom(5, date, time, MeetingType.VC);

        assertNotNull(result);
        assertEquals("Room A", result.getRoomName());
        assertEquals(date, result.getDate());
        assertEquals(time, result.getTime());

        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(roomService, times(1)).findBestRoom(any(), anyInt(), any(), anyInt());
    }

    @Test
    void shouldThrowRoomNotFoundExceptionAndNotBookRoomWhenNoneAvailable() {
        when(roomService.findBestRoom(any(), anyInt(), any(), anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class,
                () -> bookingService.bookRoom(5, LocalDate.of(2025, 2, 10), 3, MeetingType.VC));

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionForInvalidTime() {
        assertThrows(UnboundTimeException.class,
                () -> bookingService.bookRoom(5, LocalDate.of(2025, 2, 10), 15, MeetingType.VC));
    }
}
