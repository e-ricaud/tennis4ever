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

    @Test
    public void getScoringDetail_testPlayerAWinsGame() {
        // Given
        String input = "AAAA";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        String expected = """
                Player A : 15 / Player B : 0
                Player A : 30 / Player B : 0
                Player A : 40 / Player B : 0
                Player A wins the game
                """;
        assertEquals(expected, result);
    }

    @Test
    public void getScoringDetail_testPlayerBWinsGame() {
        // Given
        String input = "BBBB";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        String expected = """
                Player A : 0 / Player B : 15
                Player A : 0 / Player B : 30
                Player A : 0 / Player B : 40
                Player B wins the game
                """;
        assertEquals(expected, result);
    }

    @Test
    public void getScoringDetail_testDeuce() {
        // Given
        String input = "AAABBB";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testPlayerAAdvantage() {
        // Given
        String input = "AAABBBA";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A has advantage", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testPlayerBAdvantage() {
        // Given
        String input = "AAABBABB";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player B has advantage", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testPlayerAAdvantageWins() {
        // Given
        String input = "AAABBBAA";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A wins the game", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testPlayerBAdvantageWins() {
        // Given
        String input = "AAABBABBB";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player B wins the game", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testBackToDeuce() {
        // Given
        String input = "AAABBAB";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", result.split("\n")[result.split("\n").length - 1]);
    }

    @Test
    public void getScoringDetail_testComplexGameSequence() {
        // Given
        String input = "ABABAA";
        // When
        String result = scoringService.getScoringDetail(input);
        // Then

        String expected = """
                Player A : 15 / Player B : 0
                Player A : 15 / Player B : 15
                Player A : 30 / Player B : 15
                Player A : 30 / Player B : 30
                Player A : 40 / Player B : 30
                Player A wins the game
                """;

        assertEquals(expected, result);
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
