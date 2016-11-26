import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Write a description of class PlacementRules here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface PlacementRules
{
    public abstract boolean isValidMove(GameBoard board, CopyOnWriteArrayList<ProposedMove> play);
    public abstract int scoreMove(GameBoard board, CopyOnWriteArrayList<ProposedMove> play);
}
