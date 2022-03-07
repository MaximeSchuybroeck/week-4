package model;

public class Minesweeper extends AbstractMineSweeper{


    public Minesweeper(){

    }

    @java.lang.Override
    public int getWidth() {
        return 0;
    }

    @java.lang.Override
    public int getHeight() {
        return 0;
    }

    @java.lang.Override
    public void startNewGame(Difficulty level) {

    }

    @java.lang.Override
    public void startNewGame(int row, int col, int explosionCount) {

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
