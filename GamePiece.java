
/**
 * Write a description of class GamePiece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GamePiece
{
    // instance variables - replace the example below with your own
    private int color_id;
    private int shape_id;

    /**
     * Constructor for objects of class GamePiece
     */
    public GamePiece()
    {
        // initialise instance variables
    }
    
    public GamePiece(int shape_id, int color_id) {
        this.shape_id = shape_id;
        this.color_id = color_id;
    }
    
    public int getShape() {
        return shape_id;
    }
    
    public int getColor() {
        return color_id;
    }
    
    public boolean equals(final GamePiece other) {
        return this.getShape() == other.getShape() && this.getColor() == other.getColor();
    }
}
