package com.project.planner;

import com.project.planner.exception.NoPossibleRoomException;
import com.project.planner.exception.UnboundTimeException;
import com.project.planner.model.EquipmentType;
import com.project.planner.model.MeetingType;
import com.project.planner.model.Room;
import com.project.planner.repository.BookingRepository;
import com.project.planner.repository.MovableEquipmentRepository;
import com.project.planner.repository.RoomRepository;
import com.project.planner.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MovableEquipmentRepository movableEquipmentRepository;

    @Test
    void shouldFindBestRoomWhenAvailable() {
        Room room1 = new Room("Room A", 10, Set.of(EquipmentType.SCREEN));
        Room room2 = new Room("Room B", 8, Set.of(EquipmentType.SCREEN, EquipmentType.WEBCAM, EquipmentType.SPEAKERPHONE));

        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(bookingRepository.findByRoomAndDate(any(), any())).thenReturn(List.of());

        Optional<Room> bestRoom = roomService.findBestRoom(MeetingType.VC, 5, LocalDate.of(2025, 2, 10), 3);

        assertTrue(bestRoom.isPresent());
        assertEquals("Room B", bestRoom.get().getName());
    }

    @Test
    void shouldThrowNoPossibleRoomExceptionEmptyWhenNoRoomAvailable() {
        assertThrows(NoPossibleRoomException.class,
                () -> roomService.findBestRoom(MeetingType.VC, 5, LocalDate.of(2025, 2, 10), 3));

    }

    @Test
    void shouldThrowExceptionForInvalidTime() {
        assertThrows(UnboundTimeException.class,
                () -> roomService.findBestRoom(MeetingType.VC, 5, LocalDate.of(2025, 2, 10), 15));
    }
}
