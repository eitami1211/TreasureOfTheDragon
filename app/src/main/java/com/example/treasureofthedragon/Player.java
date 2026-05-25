package com.example.treasureofthedragon;

import java.util.ArrayList;

public class Player {
    private  ArrayList<Tile> playerTiles;
    private int cardsCollected;
    public Player ()
    {
        playerTiles = new ArrayList<>();
        cardsCollected = 0;
    }

    public ArrayList<Tile> getPlayerTiles() {
        return playerTiles;
    }

    public int getCardsCollected() {
        return cardsCollected;
    }

    public void setCardsCollected(int cardsCollected) {
        this.cardsCollected = cardsCollected;
    }
    public void addCards(int count)
    {
        cardsCollected += count;
    }
}

// comment for testing
