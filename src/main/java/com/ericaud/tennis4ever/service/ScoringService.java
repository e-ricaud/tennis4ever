package com.ericaud.tennis4ever.service;

import com.ericaud.tennis4ever.service.enums.Score;
import com.ericaud.tennis4ever.service.model.Player;
import com.ericaud.tennis4ever.service.model.TennisGame;
import org.apache.commons.lang3.StringUtils;

import static com.ericaud.tennis4ever.service.enums.Score.LOVE;
import static com.ericaud.tennis4ever.service.enums.Score.FIFTEEN;
import static com.ericaud.tennis4ever.service.enums.Score.THIRTY;
import static com.ericaud.tennis4ever.service.enums.Score.FORTY;

public class ScoringService {

    private static final String PLAYER_A_NAME = "A";
    private static final String PLAYER_B_NAME = "B";

    /**
     * Calculates and returns the detailed point-by-point score for a given sequence.
     *
     * @param scoringSequence A string containing only the characters 'A' or 'B',
     * representing the player who won each point.
     * @return A multi-line string detailing the score after each point.
     * @throws IllegalArgumentException If the sequence is invalid (contains other characters,
     * implies more than 2 players, or continues after the game is over).
     */
    public String getScoringDetail(String scoringSequence) {
        if (StringUtils.isEmpty(scoringSequence)) {
            return formatScore(LOVE.getValue(), LOVE.getValue(), false, false);
        }

        if (!scoringSequence.matches("[" + PLAYER_A_NAME + PLAYER_B_NAME + "]+")) {
            throw new IllegalArgumentException("Scoring input should contain only '" + PLAYER_A_NAME + "' or '" + PLAYER_B_NAME + "' players");
        }

        TennisGame tennisGame = new TennisGame(new Player(PLAYER_A_NAME), new Player(PLAYER_B_NAME));
        StringBuilder result = new StringBuilder();

        for (char pointWinnerChar : scoringSequence.toCharArray()) {
            if(tennisGame.isGameFinishing()) {
                throw new IllegalArgumentException("Scoring input invalid because game finishing before end of sequence");
            }

            Player winningPlayer = (pointWinnerChar == PLAYER_A_NAME.charAt(0)) ? tennisGame.getPlayerA() : tennisGame.getPlayerB();
            Player losingPlayer = (pointWinnerChar == PLAYER_A_NAME.charAt(0)) ? tennisGame.getPlayerB() : tennisGame.getPlayerA();

            String scoreAfterPoint = playerWinsPoint(tennisGame, winningPlayer, losingPlayer);
            result.append(scoreAfterPoint).append("\n");
        }

        return result.toString();
    }

    private String playerWinsPoint(TennisGame tennisGame, Player playerWinningPoint, Player playerLosingPoint) {
        switch (playerWinningPoint.getScore()) {
            case LOVE -> playerWinningPoint.setScore(FIFTEEN);
            case FIFTEEN -> playerWinningPoint.setScore(THIRTY);
            case THIRTY -> {
                playerWinningPoint.setScore(FORTY);
                if (playerLosingPoint.getScore() == FORTY) {
                    return formatScore(tennisGame);
                }
            }
            case FORTY -> {
                return handlePointWhenForty(tennisGame, playerWinningPoint, playerLosingPoint);
            }
        }
        return formatScore(tennisGame);
    }

    private String handlePointWhenForty(TennisGame tennisGame, Player playerWinningPoint, Player playerLosingPoint) {
        Score losingPlayerScore = playerLosingPoint.getScore();

        if (losingPlayerScore == FORTY) {
            return handleAdvantageScenarios(tennisGame, playerWinningPoint, playerLosingPoint);
        }
        return declareWinner(tennisGame, playerWinningPoint);
    }


    private String handleAdvantageScenarios(TennisGame tennisGame, Player playerWinningPoint, Player playerLosingPoint) {
        if (!playerWinningPoint.isAdvantage() && !playerLosingPoint.isAdvantage()) {
            playerWinningPoint.setAdvantage(true);
            return "Player " + playerWinningPoint.getName() + " has advantage";
        }

        if (playerLosingPoint.isAdvantage()) {
            playerLosingPoint.setAdvantage(false);
            return formatScore(tennisGame);
        }

        return declareWinner(tennisGame, playerWinningPoint);
    }

    private String formatScore(TennisGame tennisGame) {
        Player playerA = tennisGame.getPlayerA();
        Player playerB = tennisGame.getPlayerB();
        return formatScore(playerA.getScore().getValue(), playerB.getScore().getValue(), playerA.isAdvantage(), playerB.isAdvantage());
    }

    private String formatScore(int scoreA, int scoreB, boolean advantageA, boolean advantageB) {
        if (scoreA == FORTY.getValue() && scoreB == FORTY.getValue() && !advantageA && !advantageB) {
            return String.format("Player %s : %d / Player %s : %d (Deuce)", PLAYER_A_NAME, scoreA, PLAYER_B_NAME, scoreB);
        }

        return String.format("Player %s : %d / Player %s : %d", PLAYER_A_NAME, scoreA, PLAYER_B_NAME, scoreB);
    }

    private String declareWinner(TennisGame tennisGame, Player winner) {
        tennisGame.setGameFinishing(true);
        return "Player " + winner.getName() + " wins the game";
    }

}
