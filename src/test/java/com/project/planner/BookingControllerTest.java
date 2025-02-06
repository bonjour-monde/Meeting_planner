package com.project.planner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.planner.controller.BookingController;
import com.project.planner.dto.BookingRequest;
import com.project.planner.dto.BookingResponse;
import com.project.planner.exception.NoPossibleRoomException;
import com.project.planner.model.EquipmentType;
import com.project.planner.model.MeetingType;
import com.project.planner.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;


    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());



    @Test
    void shouldReturnBookingResponseWhenBookingSuccessful() throws Exception {
        BookingResponse response = new BookingResponse(
                1L, "Room A", LocalDate.of(2025, 2, 10), 3, Set.of(EquipmentType.SCREEN));

        when(bookingService.bookRoom(anyInt(), any(), anyInt(), any()))
                .thenReturn(response);

        BookingRequest request = new BookingRequest(MeetingType.VC, 5, LocalDate.of(2025, 2, 10), 3);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.roomName").value("Room A"))
                .andExpect(jsonPath("$.date").value("2025-02-10"))
                .andExpect(jsonPath("$.time").value(3))
                .andExpect(jsonPath("$.missingEquipments[0]").value("SCREEN"));
    }

    @Test
    void shouldReturnBadRequestWhenNoRoomPossible() throws Exception {
        when(bookingService.bookRoom(anyInt(), any(), anyInt(), any()))
                .thenThrow(new NoPossibleRoomException("No room available"));

        BookingRequest request = new BookingRequest(MeetingType.VC, 5, LocalDate.of(2025, 2, 10), 3);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
