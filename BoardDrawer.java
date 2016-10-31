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
    
    // Finds the board x-ordinate of the piece overlapping the given normalized x-ordinate
    static public int denormalizeX(double normal_x, boolean include_gaps) 
    {
        return 0;  // placeholder
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    static public void drawBoard(Graphics2D g, BoundingBox box, CameraPosition camera, GameBoard board)
    {
        // Draw each tile.  The tricky part is knowing how to scale things.
        
        // There also need to be rules about how to draw each tile.  Handle this later?
        
        // Get our list of coordinates at which tiles are placed
        final Coords all_coords[] = board.getAllCoords();
        for (int i = 0; i < all_coords.length; i++) {
            final Coords coords = all_coords[i];
            final GamePiece piece = board.getPiece(coords.getX(), coords.getY());
            // Have to establish rules about how to draw the piece.
            Color color;
            switch (piece.getColor()) {
                case 0:
                    color = Color.RED;
                    break;
                case 1:
                    color = Color.BLUE;
                    break;
                default:
                    color = Color.GRAY;
                    break;
            }
            
            // TODO: Actually separate the color from the shape...
            
            TileIcon shape;
            switch (piece.getShape()) {
                case 0:
                    shape = new CircleIcon(color);
                    break;
                case 1:
                    shape = new SquareIcon(color);
                    break;
                default:
                    shape = new CircleIcon(color);  // Make dummy icon
                    break;
            }
            
            TileGraphic tile = new TileGraphic(shape, 0.6);  // TODO: remove magic number
            
//             double space_ratio_x = 0.0;  // how much space is placed between tiles relative to the width of a tile
//             double space_ratio_y = 0.0;  // how much space is placed between tiles relative to the height of a tile
            
//             // find normalized dimensions: how much space is needed in units of tiles
//             // TODO: Handle case where there are no tiles elegantly
//             // TODO: Handle case where tiles are not square
//             double normalized_width = board.getBoundaries().xSpan() * (1.0 + space_ratio_x) - space_ratio_x;
//             double normalized_height = board.getBoundaries().ySpan() * (1.0 + space_ratio_y) - space_ratio_y;
//             
//             double fit_to_width_ratio = box.width() / normalized_width;
//             double fit_to_height_ratio = box.height() / normalized_height;
//             double tile_size = Math.min(fit_to_width_ratio, fit_to_height_ratio);
//             // Here's where tricky coordinate stuff happens.
//             // Leftmost tile gets coordinates of tile_size / 2
//             double x_pos = (tile_size / 2) + (coords.getX() - board.getBoundaries().x_min) * (tile_size * (1.0 + space_ratio_x));
//             double y_pos = (tile_size / 2) + (coords.getY() - board.getBoundaries().y_min) * (tile_size * (1.0 + space_ratio_y));
//             tile.draw(g, x_pos, y_pos, tile_size / 2, tile_size / 2);
//             tile.draw(g, box.width() / 2 + camera.getTransformedXPosition(coords.getX()),
//                          box.height() / 2 + camera.getTransformedYPosition(coords.getY()),
//                          camera.getTransformedXScale(0.5),
//                          camera.getTransformedYScale(0.5));

            // TODO: Handle the width term more elegantly          
            tile.draw(g, box.width() / 2.0 + camera.getTransformedXPosition(getNormalTileCenterX(coords.getX())),
                         box.height() / 2.0 + camera.getTransformedYPosition(getNormalTileCenterY(coords.getY())),
                         camera.getTransformedXScale(0.5),
                         camera.getTransformedYScale(0.5));
            //System.out.println();
        }
    }
}
