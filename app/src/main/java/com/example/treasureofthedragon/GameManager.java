package com.example.treasureofthedragon;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private Tile boardTiles[][];
    private ArrayList<Tile> spiders;
    private ArrayList<Tile> dragons;
    private ArrayList<Tile> diamonds;
    private ArrayList<Tile> princesses;
    private ArrayList<Tile> trains;
    private ArrayList<Tile> balls;
    private ArrayList<Tile> robots;
    private ArrayList<Tile> chests;

    private ArrayList<Tile> cardsToFlip;

    private Tile firstTile;
    private int currTurn;
    private int playerCount;


    private ArrayList<Player> players;

    //private AppCompatActivity appCompatActivity;

    public GameManager (int numOfPlayers)
    {
        boardTiles = new Tile[7][7];
        ArrayList <Integer> list  = createCards();

        spiders = new ArrayList<>();
        dragons = new ArrayList<>();
        diamonds = new ArrayList<>();
        princesses = new ArrayList<>();
        trains = new ArrayList<>();
        balls = new ArrayList<>();
        robots = new ArrayList<>();
        chests = new ArrayList<>();

        players = new ArrayList<>();
        cardsToFlip = new ArrayList<>();

        currTurn = 0;
        playerCount = numOfPlayers;

        for (int i = 0; i < playerCount; i ++)
            players.add(new Player());



        firstTile = new Tile(-2,0,0);
        Random rnd = new Random();
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                int num = rnd.nextInt(list.size());
                boardTiles[i][j] = new Tile(list.get(num), i,j);
                list.remove(num);

            }
        }
    }

    public ArrayList<Integer> createCards()
    {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 3; i++)
        {
            list.add(0);
        }

        for (int i = 0; i < 4; i++)
        {
            list.add(3);
            list.add(4);
            list.add(5);
        }

        for (int i = 0; i < 6; i++)
        {
            list.add(6);
        }

        for (int i = 0; i < 8; i++)
        {
            list.add(7);
            list.add(2);
        }

        for (int i = 0; i < 12; i++)
        {
            list.add(1);
        }
        return list;
    }

    public Tile[][] getBoardTiles() {
        return boardTiles;
    }

    public void addTileToList(Tile tile)
    {
        if (tile.getType() == 0) {
            spiders.add(tile);
          //  Toast.makeText(appCompatActivity, "" + spiders.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 1)
        {
            dragons.add(tile);
          //  Toast.makeText(appCompatActivity, "" + dragons.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 2)
        {
            diamonds.add(tile);
            //Toast.makeText(appCompatActivity, "" + diamonds.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 3)
        {
            princesses.add(tile);
            //Toast.makeText(appCompatActivity, "" + princesses.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 4)
        {
            trains.add(tile);
            //Toast.makeText(appCompatActivity, "" + trains.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 5)
        {
            balls.add(tile);
            //Toast.makeText(appCompatActivity, "" + balls.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 6)
        {
            robots.add(tile);
            //Toast.makeText(appCompatActivity, "" + robots.size(), Toast.LENGTH_SHORT).show();
        }
        if (tile.getType() == 7)
        {
            chests.add(tile);
            //Toast.makeText(appCompatActivity, "" + chests.size(), Toast.LENGTH_SHORT).show();
        }


    }


    public void cutTurn()
    {
        spiders.clear();
        dragons.clear();
        diamonds.clear();
        princesses.clear();
        trains.clear();
        balls.clear();
        robots.clear();
        chests.clear();
        cardsToFlip.clear();

        firstTile = new Tile(-2,0,0);

        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if(boardTiles[i][j].isSelected() == true)
                    cardsToFlip.add(boardTiles[i][j]);

                boardTiles[i][j].setSelected(false);
            }
        }

        switchTurns();

    }

    public void endTurn(boolean isDragonTurn)
    {
        int count = 0;
        if (isDragonTurn == false) {
            for (int i = 0; i < diamonds.size(); i++) {
                players.get(currTurn).getPlayerTiles().add(diamonds.get(i));
                boardTiles[diamonds.get(i).getRow()][diamonds.get(i).getCol()].setType(-2);
                count ++;
            }

            for (int i = 0; i < princesses.size()/2; i++) {
                players.get(currTurn).getPlayerTiles().add(princesses.get( 2*i));
                boardTiles[princesses.get(2*i).getRow()][princesses.get(2*i).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(princesses.get(2*i+1));
                boardTiles[princesses.get(2*i+1).getRow()][princesses.get(2*i+1).getCol()].setType(-2);

                count += 2;
            }

            for (int i = 0; i < trains.size()/2; i++) {
                players.get(currTurn).getPlayerTiles().add(trains.get( 2*i));
                boardTiles[trains.get(2*i).getRow()][trains.get(2*i).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(trains.get(2*i+1));
                boardTiles[trains.get(2*i+1).getRow()][trains.get(2*i+1).getCol()].setType(-2);

                count += 2;
            }

            for (int i = 0; i < balls.size()/2; i++) {
                players.get(currTurn).getPlayerTiles().add(balls.get( 2*i));
                boardTiles[balls.get(2*i).getRow()][balls.get(2*i).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(balls.get(2*i+1));
                boardTiles[balls.get(2*i+1).getRow()][balls.get(2*i+1).getCol()].setType(-2);

                count += 2;
            }

            for (int i = 0; i < robots.size()/3; i++) {
                players.get(currTurn).getPlayerTiles().add(robots.get( 3*i));
                boardTiles[robots.get(3*i).getRow()][robots.get(3*i).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(robots.get(3*i+1));
                boardTiles[robots.get(3*i+1).getRow()][robots.get(3*i+1).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(robots.get(3*i+2));
                boardTiles[robots.get(3*i+2).getRow()][robots.get(3*i+2).getCol()].setType(-2);

                count += 3;
            }

            for (int i = 0; i < chests.size()/4; i++) {
                players.get(currTurn).getPlayerTiles().add(chests.get( 4*i));
                boardTiles[chests.get(4*i).getRow()][chests.get(4*i).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(chests.get(4*i+1));
                boardTiles[chests.get(4*i+1).getRow()][chests.get(4*i+1).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(chests.get(4*i+2));
                boardTiles[chests.get(4*i+2).getRow()][chests.get(4*i+2).getCol()].setType(-2);

                players.get(currTurn).getPlayerTiles().add(chests.get(4*i+3));
                boardTiles[chests.get(4*i+3).getRow()][chests.get(4*i+3).getCol()].setType(-2);

                count += 4;
            }
        }
        else
        {
            for (int i = 0; i < dragons.size(); i++) {
                players.get(currTurn).getPlayerTiles().add(dragons.get(i));
                boardTiles[dragons.get(i).getRow()][dragons.get(i).getCol()].setType(-2);

                count ++;
            }
        }

       // Toast.makeText(appCompatActivity, "player collected " + count + " cards", Toast.LENGTH_SHORT).show();
        players.get(currTurn).addCards(count);
    //    Toast.makeText(appCompatActivity, "player collected " + players.get(currTurn).getCardsCollected() + " cards totally", Toast.LENGTH_SHORT).show();
        cutTurn();


    }

    public Tile getFirstTile() {
        return firstTile;
    }

    public void setFirstTile(Tile firstTile) {
        this.firstTile = firstTile;
    }

    public void switchTurns()
    {
        if(currTurn == playerCount - 1)
            currTurn = 0;
        else
            currTurn ++;
    }

    public int getCurrTurn() {
        return currTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Tile> getCardsToFlip() {
        return cardsToFlip;
    }
}
