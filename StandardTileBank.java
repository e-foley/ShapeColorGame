import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class StandardTileBank here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardTileBank implements TileBank
{
    // instance variables - replace the example below with your own
    private ArrayList<GamePiece> tiles;

    public static final int DEFAULT_NUM_COPIES = 3;
    public static final int DEFAULT_NUM_SHAPES_COLORS = 6;
    
    
        /**
     * Constructor for objects of class StandardTileBank
     */
    public StandardTileBank()
    {
        this(DEFAULT_NUM_SHAPES_COLORS, DEFAULT_NUM_COPIES);
    }
    
    public StandardTileBank(int num_copies)
    {
        this(DEFAULT_NUM_SHAPES_COLORS, num_copies);
    }
    
    /**
     * Constructor for objects of class StandardTileBank
     */
    public StandardTileBank(int num_shapes_colors, int num_copies)
    {
        tiles = new ArrayList<GamePiece>();
        for (int copy = 0; copy < num_copies; ++copy) {
            for (int shape = 0; shape < num_shapes_colors; ++shape) {
                for (int color = 0; color < num_shapes_colors; ++color) {
                    tiles.add(new GamePiece(shape, color));
                }
            }
        }
    }

    public void addPiece(GamePiece piece_adding)
    {
        tiles.add(piece_adding);
    }
    
    public GamePiece drawPiece(int index)
    {
        return tiles.remove(index);
    }
    
    public GamePiece drawRandom(Random randomizer)
    {
        return tiles.remove(randomizer.nextInt(tiles.size()));
    }
    
    public int numPieces()
    {
        return tiles.size();
    }
    
    public boolean isEmpty()
    {
        return tiles.isEmpty();
    }
}
