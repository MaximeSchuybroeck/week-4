package model;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Minesweeper extends AbstractMineSweeper{

private List[][] field;
private Difficulty level;

    public Minesweeper(){

    }

    @Override
    public int getWidth() {
        return field[0].length;
    }

    @java.lang.Override
    public int getHeight() {
        return field.length;
    }

    @java.lang.Override
    public void startNewGame(Difficulty level) {
        this.level = level;

    }

    @java.lang.Override
    public void startNewGame(int row, int col, int explosionCount) {
        if(assertEquals(level, Difficulty.EASY))
        field = new List[][8];

    }

    @java.lang.Override
    public void toggleFlag(int x, int y) {

    }

    @java.lang.Override
    public AbstractTile getTile(int x, int y) {
        return null;
    }

    @java.lang.Override
    public void setWorld(AbstractTile[][] world) {

    }

    @java.lang.Override
    public void open(int x, int y) {

    }

    @java.lang.Override
    public void flag(int x, int y) {

    }

    @java.lang.Override
    public void unflag(int x, int y) {

    }

    @java.lang.Override
    public void deactivateFirstTileRule() {

    }

    @java.lang.Override
    public AbstractTile generateEmptyTile() {
        return null;
    }

    @java.lang.Override
    public AbstractTile generateExplosiveTile() {
        return null;
    }
}
