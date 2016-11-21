import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;

/**
 * Write a description of class BoardDrawer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoardDrawer
{
    // instance variables - replace the example below with your own
    private int x;

    public static final double NORMAL_TILE_WIDTH = 1.0;
    public static final double NORMAL_TILE_HEIGHT = 1.0;
    public static final double NORMAL_TILE_GAP_X = 0.0;
    public static final double NORMAL_TILE_GAP_Y = 0.0;
    
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    
    /**
     * Constructor for objects of class BoardDrawer
     */
    public BoardDrawer()
    {
        // initialise instance variables
        x = 0;
    }
    
    static public double getNormalBoardEdge(GameBoard board, int edge)
    {
        switch (edge) {
            case TOP:
                return -NORMAL_TILE_HEIGHT / 2.0 + getNormalTileCenterY(board.getBoundaries().y_min);
            case BOTTOM:
                return  NORMAL_TILE_HEIGHT / 2.0 + getNormalTileCenterY(board.getBoundaries().y_max);
            case LEFT:
                return -NORMAL_TILE_WIDTH / 2.0 + getNormalTileCenterX(board.getBoundaries().x_min);
            case RIGHT:
                return  NORMAL_TILE_WIDTH / 2.0 + getNormalTileCenterX(board.getBoundaries().x_max);
            default:
                return  0.0;
        }
    }
    
    static public double getNormalTileCenterX(int x_pos)
    {
        return x_pos * (NORMAL_TILE_WIDTH + NORMAL_TILE_GAP_X);
    }
    
    static public double getNormalTileCenterY(int y_pos)
    {
        return y_pos * (NORMAL_TILE_HEIGHT + NORMAL_TILE_GAP_Y);
    }
    
    static public double getNormalBoardWidth(GameBoard board)
    {
        return getNormalBoardEdge(board, RIGHT) - getNormalBoardEdge(board, LEFT);
    }
    
    static public double getNormalBoardHeight(GameBoard board)
    {
        return getNormalBoardEdge(board, BOTTOM) - getNormalBoardEdge(board, TOP);
    }
    
    static public double getNormalBoardCenterX(GameBoard board)
    {
        return (getNormalBoardEdge(board, RIGHT) + getNormalBoardEdge(board, LEFT)) / 2.0;
    }
    
    static public double getNormalBoardCenterY(GameBoard board)
    {
        return (getNormalBoardEdge(board, BOTTOM) + getNormalBoardEdge(board, TOP)) / 2.0;
    }
    
    // TODO: Implement gap exclusion
    static public DenormalizationResult denormalize(double normal_x, double normal_y, boolean include_gaps)
    {
        return new DenormalizationResult(true, (int)(Math.round(normal_x / (NORMAL_TILE_WIDTH + NORMAL_TILE_GAP_X))), (int)(Math.round(normal_y / (NORMAL_TILE_HEIGHT + NORMAL_TILE_GAP_Y))));
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public static void drawBoard(Graphics2D g, BoundingBox box, CameraPosition camera, GameBoard board, Palette palette, IconSet icon_set)
    {
        // Draw each tile.  The tricky part is knowing how to scale things.
        
        // There also need to be rules about how to draw each tile.  Handle this later?
        
        // Get our list of coordinates at which tiles are placed
        final Coords all_coords[] = board.getAllCoords();
        for (int i = 0; i < all_coords.length; i++) {
            final Coords coords = all_coords[i];
            final GamePiece piece = board.getPiece(coords.getX(), coords.getY());
            final Color color = palette.getColor(piece.getColor());
            final TileIcon shape = icon_set.getIcon(piece.getShape(), color);
            final TileGraphic tile = new TileGraphic(shape, 0.6);  // TODO: remove magic number

            // TODO: Have this calculation make more intuitive sense
            tile.draw(g, box.width() / 2.0 + camera.getTransformedXPosition(getNormalTileCenterX(coords.getX())),
                         box.height() / 2.0 + camera.getTransformedYPosition(getNormalTileCenterY(coords.getY())),
                         camera.getTransformedXScale(0.5),
                         camera.getTransformedYScale(0.5));
        }
    }
}
