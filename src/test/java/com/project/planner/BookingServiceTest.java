package com.project.planner;

import com.project.planner.exception.RoomNotFoundException;
import com.project.planner.exception.UnboundTimeException;
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
        when(roomService.findBestRoom(any(), anyInt(), any(), anyInt()))
                .thenReturn(Optional.of(room));

        Optional<String> result = bookingService.bookRoom(5, LocalDate.of(2025, 2, 10), 3, MeetingType.VC);

        assertTrue(result.isPresent());
        assertEquals("Room A", result.get());
        verify(bookingRepository, times(1)).save(any());
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
