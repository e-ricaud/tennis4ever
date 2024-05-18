package com.ericaud.tennis4ever.api.resource;

import com.ericaud.tennis4ever.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScoringResource.class)
public class ScoringResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoringService scoringService;

    @Test
    public void getScoringDetail_should_return_OK() throws Exception {
        String scoringInput = "AABBAB";

        when(scoringService.getScoringDetail(scoringInput)).thenReturn("Winner is: " + scoringInput);

        mockMvc.perform(get("/api/scoring/scoringDetail")
                .param("scoringInput", scoringInput))
                .andExpect(status().isOk())
                .andExpect(content().string("Winner is: " + scoringInput));
    }

    @Test
    public void getScoringDetail_scoringInput_shouldBeOptional() throws Exception {
        // Calling the endpoint without the required path variable should result in a 400 Bad Request
        when(scoringService.getScoringDetail(any())).thenReturn(null);

        mockMvc.perform(get("/api/scoring/scoringDetail")) // Notice the missing path variable
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void getScoringDetail_shouldCatchException_and_rethrowBy_GlobalExceptionHandler() throws Exception {
        // Calling the endpoint without the required path variable should result in a 400 Bad Request
        when(scoringService.getScoringDetail(any())).thenThrow(new IllegalArgumentException("Scoring input cannot contain more than 2 players"));

        mockMvc.perform(get("/api/scoring/scoringDetail"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Scoring input cannot contain more than 2 players"));
    }

}
