
/**
 * Write a description of class ProposedMoves here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProposedMove
{
    // instance variables - replace the example below with your own
    private GamePiece piece;
    private int x;
    private int y;

    /**
     * Constructor for objects of class ProposedMoves
     */
    public ProposedMove(GamePiece piece, int x, int y)
    {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public GamePiece getGamePiece() {
        return piece;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
