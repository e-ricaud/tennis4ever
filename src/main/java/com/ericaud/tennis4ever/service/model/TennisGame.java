package com.ericaud.tennis4ever.service.model;

import java.util.Objects;

public class TennisGame {

    private Player playerA;
    private Player playerB;
    private boolean gameFinishing;

    public TennisGame(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.gameFinishing = false;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }

    public boolean isGameFinishing() {
        return gameFinishing;
    }

    public void setGameFinishing(boolean gameFinishing) {
        this.gameFinishing = gameFinishing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisGame that = (TennisGame) o;
        return gameFinishing == that.gameFinishing && Objects.equals(playerA, that.playerA) && Objects.equals(playerB, that.playerB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerA, playerB, gameFinishing);
    }

}
