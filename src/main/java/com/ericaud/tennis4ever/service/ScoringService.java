package com.ericaud.tennis4ever.service;

import org.apache.commons.lang3.StringUtils;

public class ScoringService {


    public String getScoringDetail(String scoringInput) {
        if (StringUtils.isEmpty(scoringInput)) {
            return "Player A : 0 / Player B : 0";
        }

        // Collect distinct players
        long distinctCharCount = scoringInput.chars().distinct().count();
        if (distinctCharCount >= 2) {
            throw new IllegalArgumentException("Scoring input cannot contain more than 2 players");
        }

        // Contain only A or B
        if (!scoringInput.matches("[AB]+")) {
            throw new IllegalArgumentException("Scoring input should contain only 'A' or 'B' players");
        }

        return "";
    }

}
