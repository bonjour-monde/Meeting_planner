package com.project.planner;

import com.project.planner.exception.NoPossibleRoomException;
import com.project.planner.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookingService bookingService() {
            return mock(BookingService.class);
        }
    }

    @Test
    void shouldReturnRoomNameWhenBookingSuccessful() throws Exception {
        when(bookingService.bookRoom(anyInt(), any(), anyInt(), any()))
                .thenReturn(Optional.of("Room A"));

        mockMvc.perform(post("/book")
                        .param("meetingType", "VC")
                        .param("participants", "5")
                        .param("date", "2025-02-10")
                        .param("time", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"Room A\""));
    }

    @Test
    void shouldReturnBadRequestWhenNoRoomPossible() throws Exception {
        when(bookingService.bookRoom(anyInt(), any(), anyInt(), any()))
                .thenThrow(NoPossibleRoomException.class);

        mockMvc.perform(post("/book")
                        .param("meetingType", "VC")
                        .param("participants", "5")
                        .param("date", "2025-02-10")
                        .param("time", "3"))
                .andExpect(status().isBadRequest());
    }
}
