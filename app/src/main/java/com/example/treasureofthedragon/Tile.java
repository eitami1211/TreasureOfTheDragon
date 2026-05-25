package com.example.treasureofthedragon;

public class Tile {
    enum CardType{
        REGULAR,
        DRAGON,
        SPIDER,
        NONE
    }
    private int type;
    private int row;
    private int col;
    private boolean isSelected;

    public Tile(int type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.isSelected = false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
