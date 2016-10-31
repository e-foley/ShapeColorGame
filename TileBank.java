import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class TileBank here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface TileBank
{
    public abstract void addPiece(GamePiece piece_adding);
    public abstract GamePiece drawPiece(int index);
    public abstract GamePiece drawRandom(Random randomizer);
    public abstract int numPieces();
    public abstract boolean isEmpty();
}
