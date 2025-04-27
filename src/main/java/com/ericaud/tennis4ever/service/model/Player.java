package com.ericaud.tennis4ever.service.model;

import com.ericaud.tennis4ever.service.enums.Score;

import java.util.Objects;

public class Player{

    private final String name;
    private Score score;
    private boolean advantage;

    public Player(String name) {
        this.name = name;
        this.score = Score.LOVE;
        this.advantage = false;
    }

    public String getName() {
        return name;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public boolean isAdvantage() {
        return advantage;
    }

    public void setAdvantage(boolean advantage) {
        this.advantage = advantage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score.equals(player.score) && advantage == player.advantage && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, advantage);
    }
}
