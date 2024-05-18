package com.ericaud.tennis4ever.api.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScoringResource.class)
public class ScoringResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetWinner_should_return_OK() throws Exception {
        String scoringInput = "AABBAB";

        mockMvc.perform(get("/api/scoring/winner")
                .param("scoringInput", scoringInput))
                .andExpect(status().isOk())
                .andExpect(content().string("Winner is: " + scoringInput));
    }

    @Test
    public void testGetWinner_withNoInput_should_OK() throws Exception {
        // Calling the endpoint without the required path variable should result in a 400 Bad Request
        mockMvc.perform(get("/api/scoring/winner")) // Notice the missing path variable
                .andExpect(status().isOk())
                .andExpect(content().string("Winner is: " + null));
    }

}
