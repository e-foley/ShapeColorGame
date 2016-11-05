import java.util.HashMap;
import java.util.Set;

/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameBoard implements Cloneable
{
    public class Boundaries {
        public int x_min;
        public int x_max;
        public int y_min;
        public int y_max;
        
        public Boundaries() {
            x_min = 0;
            x_max = 0;
            y_min = 0;
            y_max = 0;
        }
        
        public Boundaries(int x_min, int x_max, int y_min, int y_max) {
            this.x_min = x_min;
            this.x_max = x_max;
            this.y_min = y_min;
            this.y_max = y_max;
        }
        
        public int xSpan() {
            return x_max - x_min + 1;
        }
        
        public int ySpan() {
            return y_max - y_min + 1;
        }
    }
        
    // instance variables - replace the example below with your own
    private HashMap<Coords, GamePiece> board;

    /**
     * Constructor for objects of class Board
     */
    public GameBoard()
    {
        // initialise instance variables
        board = new HashMap<Coords, GamePiece>();
    }
    
    public void addPiece(int x, int y, GamePiece piece) {
        board.put(new Coords(x, y), piece);
    }

    public GamePiece removePiece(int x, int y) {
        return board.remove(new Coords(x, y));
    }
    
    public GamePiece getPiece(int x, int y) {
        return board.get(new Coords(x, y));
    }
    
    public boolean isPieceAt(int x, int y) {
        return board.containsKey(new Coords(x, y));
    }
    
    public void clear() {
        board.clear();
    }
    
    public GameBoard clone() {
        GameBoard new_board = new GameBoard();
        new_board.board = new HashMap<Coords, GamePiece>(board);
        return new_board;
    }
    
    public Boundaries getBoundaries() {
        if (board.isEmpty()) {
            return null;
        }
        
        Coords coords[] = board.keySet().toArray(new Coords[0]);
        
        // There should be at least one entry because we checked for empty earlier.
        assert(coords.length > 0);
        int x_min = coords[0].getX();
        int x_max = coords[0].getX();
        int y_min = coords[0].getY();
        int y_max = coords[0].getY();
        //Set<Coords> coords = board.keySet();
        
        // Check index 1 and beyond since we covered 0 above.
        for (int i = 1; i < coords.length; i++) {
            if (coords[i].getX() < x_min) {
                x_min = coords[i].getX();
            } else if (coords[i].getX() > x_max) {
                x_max = coords[i].getX();
            }
            
            if (coords[i].getY() < y_min) {
                y_min = coords[i].getY();
            } else if (coords[i].getY() > y_max) {
                y_max = coords[i].getY();
            }
        }
        
        return new Boundaries(x_min, x_max, y_min, y_max);
    }
    
    public Coords[] getAllCoords() {
        return board.keySet().toArray(new Coords[0]);
    }
}
