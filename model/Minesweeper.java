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

public class Minesweeper extends AbstractMineSweeper {

    private int height;
    private int width;
    private boolean open;
    private boolean flaged;
    private boolean unflaged;

    private AbstractTile[][] playingField;
    private PlayableMinesweeper playableMinesweeper;
    private int explosionCount; //hoeveelheid bommen er aanwezig moeten zijn
    private int countBombs; //effectief aanwizige bommen

    public Minesweeper(){
    }

    @Override
    public int getWidth() {
        return width;
    }

    @java.lang.Override
    public int getHeight() {
        return height;
    }

    @java.lang.Override
    public void startNewGame(Difficulty level) {
        /**
         * Start a new game on the basis of the difficulty
         */
        switch (level){
            case EASY:
                startNewGame(8,8,10);
                break;
            case MEDIUM:
                startNewGame(16,16,40);

                break;
            case HARD:
                startNewGame(16, 30, 99);
                break;
        }


    }

    @java.lang.Override
    public void startNewGame(int row, int col, int explosionCount) {
        height = row;
        width = col;

        this.explosionCount = explosionCount;

        this.viewNotifier.notifyNewGame(row,col);
        playingField = new AbstractTile[row][col];

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
        return playingField[y][x];
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
                    countBombs++;
                } else {
                    world[row][col] = generateEmptyTile();
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
        if(getTile(x, y).open()){
            this.viewNotifier.notifyExploded(x, y);
            System.out.println("Tile (" + x + "," + y + ") exploded.");
        }else {
            getTile(x, y).open();
            this.viewNotifier.notifyOpened(x, y, getExplosionCountNeighbours(x, y)); // TODO make method getCountExplosiveNeighbours()
            System.out.println("Tile (" + x + "," + y + ") opened.");
        }
    }

    @java.lang.Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
        this.viewNotifier.notifyFlagged(x, y);
        System.out.println("Tile (" + x + "," + y + ") flagged.");
    }

    @java.lang.Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
        this.viewNotifier.notifyUnflagged(x, y);
    }

    @java.lang.Override //TODO wat is dit? is dit dat wanneer je eerste tile aanklick een groot deel zo plots vrijkomt?
    public void deactivateFirstTileRule() {

    }

    @java.lang.Override
    public Tile generateEmptyTile() {
        Tile tile = new Tile();
        return tile;
    }
    @java.lang.Override
    public Tile generateExplosiveTile() {
        Tile tile = new Tile();
        tile.setExplosive();
        return tile;
    }

    public void createPlayableMinesweeper(){

    }

    public int getExplosionCountNeighbours(int x, int y) {
        int TempExplosionCountNeighbours = 0;

        //if (x < 0 || y < 0 || x > height || y > width) {
        //}

        for(int a = x-1; a < x + 2; a++ ){
            for(int b = y-1; b < y + 2; b++){
                try {
                    if(getTile(a, b).isExplosive()){
                        TempExplosionCountNeighbours++;
                    }
                } catch (Exception ArrayIndexOutOfBoundsException) {
                    ArrayIndexOutOfBoundsException.printStackTrace();
                }
            }

        }


        /*try {
            if(getTile(x - 1, y).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x + 1, y).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x, y - 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x, y + 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x - 1, y - 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x + 1, y - 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x - 1, y + 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }
        try {
            if(getTile(x + 1, y + 1).isExplosive()){
                explosionCountNeighbours++;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            ArrayIndexOutOfBoundsException.printStackTrace();
        }*/
        //return TempExplosionCountNeighbours;
        return 9;
    }
    public void checkWon(){

    } //TODO afwerken
}
