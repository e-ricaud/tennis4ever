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

    public String getScoringDetail(String scoringSequence) {
        if (StringUtils.isEmpty(scoringSequence)) {
            return "Player A : " + LOVE.getValue() + " / Player B : " + LOVE.getValue();
        }

        // Collect distinct players
        long distinctCharCount = scoringSequence.chars().distinct().count();
        if (distinctCharCount > 2) {
            throw new IllegalArgumentException("Scoring input cannot contain more than 2 players");
        }

        // Contain only A or B
        if (!scoringSequence.matches("[AB]+")) {
            throw new IllegalArgumentException("Scoring input should contain only 'A' or 'B' players");
        }

        TennisGame tennisGame = new TennisGame(new Player("A"), new Player("B"));

        StringBuilder result = new StringBuilder();

        for (char point : scoringSequence.toCharArray()) {
            if(tennisGame.isGameFinishing()) {
                throw new IllegalArgumentException("Scoring input invalid because game finishing before end of sequence");
            }

            if (point == 'A') {
                result.append(playerWinBall(tennisGame, tennisGame.getPlayerA(), tennisGame.getPlayerB())).append("\n");
            } else {
                result.append(playerWinBall(tennisGame, tennisGame.getPlayerB(), tennisGame.getPlayerA())).append("\n");
            }
        }

        return result.toString();
    }

    private String playerWinBall(TennisGame tennisGame, Player playerWinningBall, Player playerLosingBall) {
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
                return handleWinningPoint(tennisGame, playerWinningBall, playerLosingBall);
        }
        return getScore(playerWinningBall, playerLosingBall);
    }

    private String handleWinningPoint(TennisGame tennisGame, Player playerWinningBall, Player playerLosingBall) {
        Score losingPlayerScore = playerLosingBall.getScore();

        if (losingPlayerScore.equals(FORTY)) {
            return handleAdvantage(tennisGame, playerWinningBall, playerLosingBall);
        }

        return declareWinner(tennisGame, playerWinningBall);
    }

    private String handleAdvantage(TennisGame tennisGame, Player playerWinningBall, Player playerLosingBall) {
        if (!playerWinningBall.isAdvantage() && !playerLosingBall.isAdvantage()) {
            playerWinningBall.setAdvantage(true);
            return "Player " + playerWinningBall.getName() + " has advantage";
        }

        if (!playerWinningBall.isAdvantage() && playerLosingBall.isAdvantage()) {
            playerLosingBall.setAdvantage(false);
            return getScore(playerWinningBall, playerLosingBall);
        }

        return declareWinner(tennisGame, playerWinningBall);
    }

    private String getScore(Player playerWinningBall, Player playerLosingBall) {
        int winningPlayerScore = playerWinningBall.getScore().getValue();
        int losingPlayerScore = playerLosingBall.getScore().getValue();

        if (winningPlayerScore == 40 && losingPlayerScore == 40) {
            return "Player A : 40 / Player B : 40 (Deuce)";
        } else {
            return playerWinningBall.getName().equals("A") ?
                    String.format("Player A : %d / Player B : %d", winningPlayerScore, losingPlayerScore) :
                    String.format("Player A : %d / Player B : %d", losingPlayerScore, winningPlayerScore);
        }
    }

    private String declareWinner(TennisGame tennisGame, Player playerWinningBall) {
        tennisGame.setGameFinishing(true);
        return "Player " + playerWinningBall.getName() + " wins the game";
    }

}
