package model;

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

public class Minesweeper extends AbstractMineSweeper{

    private int height;
    private int width;
    private boolean open;
    private boolean flaged;
    private boolean unflaged;

    //private List<List<AbstractTile>> field; //stelt het speelveld voor
    private AbstractTile[][] playingField;
    private int explosionCount; //hoeveelheid bommen er aanwezig zijn

    public Minesweeper(){

    }

    @Override
    public int getWidth() {
        //return field.get(0).size();
        return width;
    }

    @java.lang.Override
    public int getHeight() {
        //return field.size();
        return height;
    }

    @java.lang.Override
    public void startNewGame(Difficulty level) {
        /**
         * Start a new game on the basis of the difficulty
         */

        if(level == Difficulty.EASY){
            startNewGame(8,8,10);
        }
        else if(level == Difficulty.MEDIUM){
            startNewGame(16,16,40);
        }
        else if(level == Difficulty.HARD){
            startNewGame(16, 30, 99);
        }
    }

    @java.lang.Override
    public void startNewGame(int row, int col, int explosionCount) {
        playingField = new AbstractTile[row][col];
        height = row;
        width = col;
        this.explosionCount = explosionCount;
        setWorld(playingField);
        //printer om te checken in terminal
        for (int Nrow = 0; Nrow < height; Nrow++) {
            System.out.print('\n');
            for (int Ncol = 0; Ncol < width; Ncol++) {
                if(playingField[Nrow][Ncol].isExplosive()){
                    System.out.print(1 + " ");
                }else{
                    System.out.print(0 + " ");
                }
                //System.out.print(playingField[Nrow][Ncol].isExplosive());
            }
        }
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
        int countBombs = 0;

        // TODO kan zijn dat er te weinig bommen aanwezig zijn, dit moet nog gecheckt worden en dan opnieuw uitgevoerd worden, gebruik while(countBombs < explosionCount) {}
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++) {
                int randNum = rand.nextInt(64); // kies een nummer tussen 0-64 van rand
                if (randNum <= 10 && countBombs < explosionCount) {
                    world[row][col] = generateExplosiveTile();
                    countBombs++;
                } else {
                    world[row][col] = generateEmptyTile();
                }
            }
        }
    }

    @java.lang.Override
    public void open(int x, int y) {
        getTile(x, y).open();
    }

    @java.lang.Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
    }

    @java.lang.Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
    }

    @java.lang.Override //TODO wat is dit? is dit dat wanneer je eerste tile aanklick een groot deel zo plots vrijkomt?
    public void deactivateFirstTileRule() {

    }

    @java.lang.Override
    public AbstractTile generateEmptyTile() {
        AbstractTile tile = new AbstractTile() {
            @Override
            public boolean open() {
                return false;
            }

            @Override
            public void flag() {

            }

            @Override
            public void unflag() {

            }

            @Override
            public boolean isFlagged() {
                return false;
            }

            @Override
            public boolean isOpened() {
                return false;
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
            @Override
            public boolean open() {
                if (!open){
                    open = true;
                }
                return true;
            }

            @Override
            public void flag() {
                flaged = true;
            }

            @Override
            public void unflag() {
                unflaged = true;
            }

            @Override
            public boolean isFlagged() {
                if(flaged){
                    return true;
                }else{
                    return false;
                }

            }

            @Override
            public boolean isOpened() {
                return open;
            }

            @Override
            public boolean isExplosive() {
                return true;
            }
        };
        return tile;
    }
}
