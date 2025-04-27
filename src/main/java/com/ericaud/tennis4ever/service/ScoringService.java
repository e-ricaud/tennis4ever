package com.ericaud.tennis4ever.service;

import com.ericaud.tennis4ever.service.model.Player;
import org.apache.commons.lang3.StringUtils;

public class ScoringService {

    private static final int SCORE_LOVE = 0;
    private static final int SCORE_FIFTEEN = 15;
    private static final int SCORE_THIRTY = 30;
    private static final int SCORE_FORTY = 40;

    public String getScoringDetail(String scoringSequence) {
        if (StringUtils.isEmpty(scoringSequence)) {
            return "Player A : " + SCORE_LOVE + " / Player B : " + SCORE_LOVE;
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
                playerWinBall(playerA);
            } else {
                playerWinBall(playerB);
            }
            result.append(getScore(playerA, playerB)).append("\n");
        }

        return result.toString();
    }

    private void playerWinBall(Player playerWinningBall) {
        switch (playerWinningBall.getScore()) {
            case SCORE_LOVE:
                playerWinningBall.setScore(SCORE_FIFTEEN);
                break;
            case SCORE_FIFTEEN:
                playerWinningBall.setScore(SCORE_THIRTY);
                break;
            case SCORE_THIRTY:
                playerWinningBall.setScore(SCORE_FORTY);
                break;
        }
    }

    private String getScore(Player playerA, Player playerB) {
        return "Player A : " + playerA.getScore() + " / Player B : " + playerB.getScore();
    }

}
