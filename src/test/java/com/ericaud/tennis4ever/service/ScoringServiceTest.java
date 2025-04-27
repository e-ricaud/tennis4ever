package com.ericaud.tennis4ever.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ScoringServiceTest {

    @InjectMocks
    private ScoringService scoringService;

    @Test
    public void getScoringDetail_testInitialScore() {
        // Given
        String input = "";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A : 0 / Player B : 0", result);
    }

    @Test
    public void getScoringDetail_without1or2Players_shouldThrowException() {
        // Given
        String input = "AABBC";
        // When - Then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> scoringService.getScoringDetail(input));
        Assertions.assertEquals("Scoring input cannot contain more than 2 players", exception.getMessage());
    }

    @Test
    public void getScoringDetail_withOtherPlayerThanAorB_shouldThrowException() {
        // Given
        String input = "CC";
        // When - Then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> scoringService.getScoringDetail(input));
        Assertions.assertEquals("Scoring input should contain only 'A' or 'B' players", exception.getMessage());
    }

}
