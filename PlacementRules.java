import java.util.ArrayList;

/**
 * Write a description of class PlacementRules here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface PlacementRules
{
    public abstract boolean isValidMove(GameBoard board, ArrayList<ProposedMove> play);
    public abstract int scoreMove(GameBoard board, ArrayList<ProposedMove> play);
}
