package com.ericaud.tennis4ever.service;

import com.ericaud.tennis4ever.service.model.Player;
import org.apache.commons.lang3.StringUtils;

import static com.ericaud.tennis4ever.service.enums.Score.*;

public class ScoringService {

    public String getScoringDetail(String scoringSequence) {
        if (StringUtils.isEmpty(scoringSequence)) {
            return "Player A : " + LOVE.getValue() + " / Player B : " + LOVE.getValue();
        }

        // Collect distinct players
        long distinctCharCount = scoringSequence.chars().distinct().count();
        if (distinctCharCount >= 2) {
            throw new IllegalArgumentException("Scoring input cannot contain more than 2 players");
        }

        // Contain only A or B
        if (!scoringSequence.matches("[AB]+")) {
            throw new IllegalArgumentException("Scoring input should contain only 'A' or 'B' players");
        }

        Player playerA = new Player("A");
        Player playerB = new Player("B");

        StringBuilder result = new StringBuilder();

        for (char point : scoringSequence.toCharArray()) {
            if (point == 'A') {
                result.append(playerWinBall(playerA, playerB)).append("\n");
            } else {
                result.append(playerWinBall(playerB, playerA)).append("\n");
            }
        }

        return result.toString();
    }

    private String playerWinBall(Player playerWinningBall, Player playerLosingBall) {
        switch (playerWinningBall.getScore()) {
            case LOVE :
                playerWinningBall.setScore(FIFTEEN);
                break;
            case FIFTEEN:
                playerWinningBall.setScore(THIRTY);
                break;
            case THIRTY:
                playerWinningBall.setScore(FORTY);
                break;
            case FORTY:
                return "Player " + playerWinningBall.getName() + " wins the game";
        }
        return getScore(playerWinningBall, playerLosingBall);
    }

    private String getScore(Player playerWinningBall, Player playerLosingBall) {
        int winningPlayerScore = playerWinningBall.getScore().getValue();
        int losingPlayerScore = playerLosingBall.getScore().getValue();

        return playerWinningBall.getName().equals("A") ?
                String.format("Player A : %d / Player B : %d", winningPlayerScore, losingPlayerScore) :
                String.format("Player A : %d / Player B : %d", losingPlayerScore, winningPlayerScore);
    }

}
