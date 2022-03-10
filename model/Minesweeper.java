package model;

import view.MinesweeperView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

//TODO voor ineens verschillende lege vakjes te clearen gebruik:
// void floodFill( int x, int y ) {
//   if ( btn( x, y ) isFillable ) {
//       fillBtn(x,y);
//       floodFill( x+1, y );
//       floodFill( x-1, y );
//       floodFill( x, y-1 );
//       floodFill( x, y+1 );
//   } else {
//       return;
//   }

public class Minesweeper extends AbstractMineSweeper implements ActionListener {

    private int y;
    private int x;
    private boolean open;
    private boolean flaged;
    private boolean unflaged;

    private AbstractTile[][] playingField;
    //private TileView[][] tileViews;
    private int explosionCount; //hoeveelheid bommen er aanwezig moeten zijn
    private int countBombs; //effectief aanwizige bommen

    private MinesweeperView gui;

    public Minesweeper(){
        gui = new MinesweeperView();
    }

    @Override
    public int getWidth() {
        //return field.get(0).size();
        return x;
    }

    @java.lang.Override
    public int getHeight() {
        //return field.size();
        return y;
    }

    @java.lang.Override
    public void startNewGame(Difficulty level) {
        /**
         * Start a new game on the basis of the difficulty
         */

        if(level == Difficulty.EASY){
            startNewGame(8,8,10);
            gui.notifyNewGame(8,8);
            //tileViews = new TileView[8][8];
        }
        else if(level == Difficulty.MEDIUM){
            startNewGame(16,16,40);
            gui.notifyNewGame(16,16);
            //tileViews = new TileView[16][16];
        }
        else if(level == Difficulty.HARD){
            startNewGame(16, 30, 99);
            gui.notifyNewGame(16,30);
            //tileViews = new TileView[16][30];
        }
    }

    @java.lang.Override
    public void startNewGame(int row, int col, int explosionCount) {
        y = row;
        x = col;

        this.explosionCount = explosionCount;


        playingField = new AbstractTile[row][col];

        //tileViews = new TileView[row][col];
        setWorld(playingField);

        //printer om te checken in terminal
        for (int Nrow = 0; Nrow < y; Nrow++) {
            System.out.print('\n');
            for (int Ncol = 0; Ncol < x; Ncol++) {
                if(playingField[Nrow][Ncol].isExplosive()){
                    System.out.print(1 + " ");
                }else{
                    System.out.print(0 + " ");
                }
            }
        }
        System.out.print("\n");
        System.out.println("aantal bommen aanwezig: " + countBombs);

    }

    @java.lang.Override
    public void toggleFlag(int x, int y) {
        boolean isFlagged = getTile(x, y).isFlagged();
        if(isFlagged){
            unflag(x, y);
        }
        else {
            flag(x, y);
        }
    }

    @java.lang.Override
    public AbstractTile getTile(int x, int y) {
        return playingField[x][y];
    }

    @java.lang.Override
    public void setWorld(AbstractTile[][] world) {
        Random rand = new Random(); // maak een gigantisch random nummer aan
        countBombs = 0; //effectief aanwizige bommen

        //create the playing field
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                int randNum = rand.nextInt(64); // kies een nummer tussen 0-64 van rand
                if (randNum <= 10 && countBombs < explosionCount) {
                    world[row][col] = generateExplosiveTile();
                    //tileViews[row][col] = new TileView(row, col);
                    countBombs++;
                } else {
                    world[row][col] = generateEmptyTile();
                    //tileViews[row][col] = new TileView(row, col);
                }
            }
        }
        //if there are too few bombs create new bombs
        if(countBombs < explosionCount){
            while(countBombs < explosionCount) {
                int randNumForHeight = rand.nextInt(getHeight());
                int randNumForWidth = rand.nextInt(getWidth());
                if(!world[randNumForHeight][randNumForWidth].isExplosive()){
                    world[randNumForHeight][randNumForWidth] = generateExplosiveTile();
                    countBombs++;
                }
            }
        }

    }

    @java.lang.Override
    public void open(int x, int y) {
        getTile(x, y).open();
        gui.notifyOpened(x, y, getExplosionCountNeighbours(x, y)); // TODO make method getCountExplosiveNeighbours()
        //tileViews[x][y].notifyOpened(9);
        System.out.println("Tile (" + x + "," + y + ") opened.");
    }

    @java.lang.Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
        gui.notifyFlagged(x, y);
        //System.out.println("Tile (" + x + "," + y + ") flagged.");
    }

    @java.lang.Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
        gui.notifyUnflagged(x, y);
    }

    @java.lang.Override //TODO wat is dit? is dit dat wanneer je eerste tile aanklick een groot deel zo plots vrijkomt?
    public void deactivateFirstTileRule() {

    }

    @java.lang.Override
    public AbstractTile generateEmptyTile() {
        AbstractTile tile = new AbstractTile(){
            boolean flagVar = false;
            boolean openVar = false;

            @Override
            public boolean open() { //TODO wat moet hier gereturned worden?
                openVar = true;
                return true;
            }

            @Override
            public void flag() {
                flagVar = true;
            }

            @Override
            public void unflag() {
                flagVar = false;
            }

            @Override
            public boolean isFlagged() {
                return flagVar;
            }

            @Override
            public boolean isOpened() {
                return openVar;
            }

            @Override
            public boolean isExplosive() {
                return false;
            }
        };
        return tile;
    }

    @java.lang.Override
    public AbstractTile generateExplosiveTile() {
        AbstractTile tile = new AbstractTile() {
            boolean flagVar = false;
            boolean openVar = false;

            @Override
            public boolean open() { //TODO wat moet hier gereturned worden?
                openVar = true;
                return true;
            }

            @Override
            public void flag() {
                flagVar = true;
            }

            @Override
            public void unflag() {
                flagVar = false;
            }

            @Override
            public boolean isFlagged() {
                return flagVar;
            }

            @Override
            public boolean isOpened() {
                return openVar;
            }
            @Override
            public boolean isExplosive() {
                return true;
            }
        };
        return tile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if(e.getSource() == playingField[row][col]) {
                    if(!playingField[row][col].isOpened()){
                        System.out.println("buton (" + row + "," + col + ") is pressed");
                        open(row, col);

                    }
                }
            }
        }

    }

    public int getExplosionCountNeighbours(int x, int y) {
        int explosionCountNeighbours = 9; // temp hardcoded
        return explosionCountNeighbours;
    } //TODO afwerken

    public void checkWon(){

    } //TODO afwerken
}
