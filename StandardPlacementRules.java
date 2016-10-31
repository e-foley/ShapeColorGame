import java.util.ArrayList;
import java.lang.Math;

/**
 * Write a description of class StandardRules here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardPlacementRules implements PlacementRules
{
    // instance variables - replace the example below with your own
    private int x;

    private static final int NO_DIRECTION = 0;
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private static final int NO_MATCH = -1;
    
    /**
     * Constructor for objects of class StandardRules
     */
    public StandardPlacementRules()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
    
    
    // In order to be valid...
    // 1. All along one direction
    // 2. Can't overlap existing tile
    // 3. Forms contiguous line
    // 4. Every tile meets requirements of valid groupings in both vertical and horizontal directions
    public boolean isValidMove(GameBoard board, ArrayList<ProposedMove> additions) {
        assert (additions.size() >= 0);
        
        // No tiles played is the same as passing, pretty much, which is valid.
        if (additions.size() == 0) {
            return true;
        }
        
        for (int i = 0; i < additions.size(); i++) {
            // Make sure no proposed pieces overlap a piece already on the board
            if (board.isPieceAt(additions.get(i).getX(), additions.get(i).getY())) {
                return false;
            }
            
            for (int j = i + 1; j < additions.size(); j++) {
                // Make sure that no two pieces in `additions` share a location
                if (additions.get(i).getX() == additions.get(j).getX() && additions.get(i).getY() == additions.get(j).getY()) {
                    return false;
                }
                
                // Also make sure that the pieces are along one direction only
                // TODO: Consider whether this logic can be simplified.  Do we really need to test all pairs?
                if (additions.get(i).getX() != additions.get(j).getX() && additions.get(i).getY() != additions.get(j).getY()) {
                    return false;
                }
            }
        }
        
        // Calculate bounds of proposed play
        int x_min = additions.get(0).getX();
        int x_max = x_min;
        int y_min = additions.get(0).getY();
        int y_max = y_min;
        for (int i = 1; i < additions.size(); i++) {  // 1 and on because we've already checked 0
            int this_x = additions.get(i).getX();
            int this_y = additions.get(i).getY();
            if (this_x < x_min) {
                x_min = this_x;
            }
            if (this_x > x_max) {
                x_max = this_x;
            }
            if (this_y < y_min) {
                y_min = this_y;
            }
            if (this_y > y_max) {
                y_max = this_y;
            }
        }
        
        // Determine direction of play
        int direction = NO_DIRECTION;
        if (x_max - x_min == 0) {
            direction = VERTICAL;
        } else if (y_max - y_min == 0) {
            direction = HORIZONTAL;
        } else {
            // Shouldn't be possible
            assert(false);
        }
        
        // Make sure we can access all additions by traveling left, right, up, down without a blank tile in between
        // TODO: Consider just sorting the list by x and y and looking for gaps in the ascending orders of each
        boolean[] access_table = new boolean[additions.size()];  // Yes, this is a parallel array
        // Clear access mask
        for (int i = 0; i < access_table.length; i++) {
            access_table[i] = false;
        }
        
        // If horizontal, look left then right to ensure we hit all proposed tiles
        if (direction == HORIZONTAL) {
            access_table[0] = true;  // This is a gimme since we start at first tile
            int x = additions.get(0).getX();
            int y = additions.get(0).getY();
            int index = 0;  
            boolean left_end_reached = false;
            while (!left_end_reached) {
                x--;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                }
            }
            
            x = additions.get(0).getX();
            boolean right_end_reached = false;
            while (!right_end_reached) {
                x++;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                }
            }
        } else if (direction == VERTICAL) {
            access_table[0] = true;  // This is a gimme since we start at first tile
            int x = additions.get(0).getX();
            int y = additions.get(0).getY();
            int index = 0;  
            boolean top_end_reached = false;
            while (!top_end_reached) {
                y--;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                }
            }
            
            y = additions.get(0).getY();
            boolean bottom_end_reached = false;
            while (!bottom_end_reached) {
                y++;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                }
            }
        }
        
        // Make sure every element in access_table was accessed
        for (int i = 0; i < access_table.length; i++) {
            if (!access_table[i]) {
                return false;
            }
        }
        
        // Next, ensure all lines formed are valid
        // First, clone the board and add the additions, then check for horizontal and vertical successes for all tiles
        GameBoard proposed = (GameBoard)(board.clone());
        for (int i = 0; i < additions.size(); i++) {
            proposed.addPiece(additions.get(i).getX(), additions.get(i).getY(), additions.get(i).getGamePiece());
        }
        
        for (int i = 0; i < additions.size(); i++) {
            if (!isValidHorizontalAt(proposed, additions.get(i).getX(), additions.get(i).getY()) || !isValidVerticalAt(proposed, additions.get(i).getX(), additions.get(i).getY())) {
                return false;
            }
        }
        
        // If we made it this far, we have a valid move!  (Hopefully.)
        return true;
    }
    
    public boolean isValidGrouping(ArrayList<GamePiece> pieces) {
        assert(pieces.size() >= 0);
        
        // Check for empty sets.  We'll count "no pieces played" as valid, I suppose...
        if (pieces.isEmpty()) {
            return true;
        }
        
        // Check for exact duplicates, which aren't allowed...
        for (int i = 0; i < pieces.size(); i++) {
            for (int j = i + 1; j < pieces.size(); j++) {
                if (pieces.get(i).equals(pieces.get(j))) {
                    return false;
                }
            }
        }
        
        // By this point, we know there are no duplicates in the set, so it should be sufficient to see that they share shape or color.
        // We'll let the first piece in the set establish the color or shape (because it's arbitrary).
        final GamePiece first_piece = pieces.get(0);
        
        // Check whether all pieces match the first piece's shape (including the first piece, since this allows groups of size 1 to succeed)
        boolean shape_matched = true;  // temp
        for (int i = 0; i < pieces.size(); ++i) {
            if (pieces.get(i).getShape() != first_piece.getShape()) {
                shape_matched = false;
                break;
            }
        }
        
        // Check whether all pieces match the first piece's color (again including the first piece)
        // NOTE: Could skip this in the case shape_matched is true, but it makes the return statement logic a little less clear
        boolean color_matched = true;  // temp
        for (int i = 0; i < pieces.size(); ++i) {
            if (pieces.get(i).getColor() != first_piece.getColor()) {
                color_matched = false;
                break;
            }
        }
        
        return shape_matched || color_matched;
    }
    
    // TODO: Remove magic numbers
    public int scoreGrouping(ArrayList<GamePiece> pieces) {
        if (!isValidGrouping(pieces)) {
            return 0;
        } else if (pieces.size() >= 6) {
            return 12;
        } else {
            return pieces.size();
        }
    }
    
    public boolean isValidHorizontalAt(GameBoard board, int x, int y) {
        ArrayList<GamePiece> pieces_in_line = new ArrayList<GamePiece>();
        
        // Check whether there's actually a piece at this location.  If not, we'll deem line OK since empty spaces can exist on board.
        if (!board.isPieceAt(x, y)) {
            return true;
        } else {
            pieces_in_line.add(board.getPiece(x, y));
        }
        
        int x_check = x;
        
        // Add all pieces to the left
        while (board.isPieceAt(x_check--, y)) {
            pieces_in_line.add(board.getPiece(x_check, y));
        }
        
        x_check = x;
        
        // Add all pieces to the right
        while (board.isPieceAt(x_check++, y)) {
            pieces_in_line.add(board.getPiece(x_check, y));
        }
        
        // Evaluate the pieces in this set
        return isValidGrouping(pieces_in_line);
    }
    
    public boolean isValidVerticalAt(GameBoard board, int x, int y) {
        ArrayList<GamePiece> pieces_in_line = new ArrayList<GamePiece>();
        
        // Check whether there's actually a piece at this location.  If not, we'll deem line OK since empty spaces can exist on board.
        if (!board.isPieceAt(x, y)) {
            return true;
        } else {
            pieces_in_line.add(board.getPiece(x, y));
        }
        
        int y_check = y;
        
        // Add all pieces above
        while (board.isPieceAt(x, y_check--)) {
            pieces_in_line.add(board.getPiece(x, y_check));
        }
        
        y_check = y;
        
        // Add all pieces to the right
        while (board.isPieceAt(x, y_check++)) {
            pieces_in_line.add(board.getPiece(x, y_check));
        }
        
        // Evaluate the pieces in this set
        return isValidGrouping(pieces_in_line);
    }
    
    public int scoreMove(GameBoard board, ArrayList<ProposedMove> play) {
        return 0;
    }
    
    public int find(ArrayList<ProposedMove> additions, int x, int y) {
        for (int i = 0; i < additions.size(); i++) {
            if (additions.get(i).getX() == x && additions.get(i).getY() == y) {
                return i;
            }
        }
        return NO_MATCH;
    }
    
    public boolean contains(ArrayList<ProposedMove> additions, int x, int y) {
        return find(additions, x, y) != NO_MATCH;
    }
}
