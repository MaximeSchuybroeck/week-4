package model;

public class Tile extends AbstractTile {

    boolean flagVar = false;
    boolean openVar = false;

    @Override
    public boolean open() {
        openVar = true;
        return isExplosive();
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
}
