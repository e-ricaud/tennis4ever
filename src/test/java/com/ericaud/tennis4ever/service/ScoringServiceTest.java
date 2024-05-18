package com.ericaud.tennis4ever.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

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
    public void getScoringDetail_testPlayerAWinsFirstBall() {
        // Given
        String input = "A";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A : 15 / Player B : 0", result.trim());
    }

    @Test
    public void getScoringDetail_testPlayerBWinsFirstBall() {
        // Given
        String input = "B";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A : 0 / Player B : 15", result.trim());
    }

    @Nested
    class ScoringDetailExceptionsTests {
        private static Stream<Arguments> scoringInput_exceptions_provider() {
            return Stream.of(
                    Arguments.of("AABBC", "Scoring input cannot contain more than 2 players"),
                    Arguments.of("CC", "Scoring input should contain only 'A' or 'B' players")
            );
        }

        @ParameterizedTest
        @MethodSource("scoringInput_exceptions_provider")
        public void getScoringDetail_without1or2Players_shouldThrowException(String scoringInput, String exceptionMessage) {
            // When - Then
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> scoringService.getScoringDetail(scoringInput));
            Assertions.assertEquals(exceptionMessage, exception.getMessage());
        }

    }

}
