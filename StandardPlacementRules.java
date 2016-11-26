import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.Math;
import javax.swing.JOptionPane;

/**
 * Write a description of class StandardRules here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardPlacementRules implements PlacementRules
{

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
    }
    
    
    // In order to be valid...
    // 1. All along one direction
    // 2. Can't overlap existing tile
    // 3. Forms contiguous line
    // 4. Every tile meets requirements of valid groupings in both vertical and horizontal directions
    public boolean isValidMove(GameBoard board, CopyOnWriteArrayList<ProposedMove> additions) {
        //assert (additions.size() >= 0);
        
        // if (true) return true;  // debug
        
        // No tiles played is the same as passing, pretty much, which is valid.
        if (additions.size() == 0) {
            return true;
        }
        
        // JOptionPane.showMessageDialog(null, "Size of `additions` is " + Integer.valueOf(additions.size()) + ".");
        
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
        
        // JOptionPane.showMessageDialog(null, "Seems like no pieces share a location and that additions are along one direction only...");
        
        // Debug
        //if (true) return false;
        
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
        
        // JOptionPane.showMessageDialog(null, "Bounds of proposed play determined.");
        
        // Determine direction of play
        int direction = NO_DIRECTION;
        if (x_max - x_min == 0) {
            direction = VERTICAL;
            // JOptionPane.showMessageDialog(null, "Direction of play is vertical.");
        } else if (y_max - y_min == 0) {
            direction = HORIZONTAL;
            // JOptionPane.showMessageDialog(null, "Direction of play is horizontal.");
        } else {
            // Shouldn't be possible
            // JOptionPane.showMessageDialog(null, "\"Impossible\" direction determined.");
            assert(false);
        }
        
        
        // Make sure we can access all additions by traveling left, right, up, down without a blank tile in between
        // TODO: Consider just sorting the list by x and y and looking for gaps in the ascending orders of each
        boolean[] access_table = new boolean[additions.size()];  // Yes, this is a parallel array
        // Clear access mask
        for (int i = 0; i < access_table.length; i++) {
            access_table[i] = false;
        }
        
        // JOptionPane.showMessageDialog(null, "Access table created.");
        
        // If horizontal, look left then right to ensure we hit all proposed tiles
        if (direction == HORIZONTAL) {
            access_table[0] = true;  // This is a gimme since we start at first tile
            int x = additions.get(0).getX();
            int y = additions.get(0).getY();
            // int index = 0;  
            boolean left_end_reached = false;
            while (!left_end_reached) {
                x--;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                } else {
                    left_end_reached = true;
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
                } else {
                    right_end_reached = true;
                }
            }
        } else if (direction == VERTICAL) {
            access_table[0] = true;  // This is a gimme since we start at first tile
            int x = additions.get(0).getX();
            int y = additions.get(0).getY();
            // int index = 0;  
            boolean top_end_reached = false;
            while (!top_end_reached) {
                y--;
                if (board.isPieceAt(x, y)) {
                    continue;
                } else if (contains(additions, x, y)) {  // TODO: perform this search only once rather than twice
                    access_table[find(additions, x, y)] = true;
                } else {
                    top_end_reached = true;
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
                } else {
                    bottom_end_reached = true;
                }
            }
        }
        
        // JOptionPane.showMessageDialog(null, "Access table populated.");
        
        // Make sure every element in access_table was accessed
        for (int i = 0; i < access_table.length; i++) {
            if (!access_table[i]) {
                return false;
            }
        }
        
        // JOptionPane.showMessageDialog(null, "Every element in the access table was accessed successfully.");
        
        // Next, ensure all lines formed are valid
        // First, clone the board and add the additions, then check for horizontal and vertical successes for all tiles
        GameBoard proposed = (GameBoard)(board.clone());
        // JOptionPane.showMessageDialog(null, "Game board cloned.");
        for (int i = 0; i < additions.size(); i++) {
            proposed.addPiece(additions.get(i).getX(), additions.get(i).getY(), new GamePiece(additions.get(i).getGamePiece()));
        }
        
        // JOptionPane.showMessageDialog(null, "Pieces added to `proposed` list.");
        
        for (int i = 0; i < additions.size(); i++) {
            // JOptionPane.showMessageDialog(null, "Cycling through additions... Index " + Integer.valueOf(i) + ".");
            if (!isValidHorizontalAt(proposed, additions.get(i).getX(), additions.get(i).getY()) || !isValidVerticalAt(proposed, additions.get(i).getX(), additions.get(i).getY())) {
                return false;
            }
        }
        
        // JOptionPane.showMessageDialog(null, "All seems valid!");
        
        //if (true) return false;
        
        // If we made it this far, we have a valid move!  (Hopefully.)
        return true;
    }
    
    public void declareGrouping(CopyOnWriteArrayList<GamePiece> pieces) {
        String building = new String();
        for (int i = 0; i < pieces.size(); ++i) {
            // TODO: This should call a piece toString method instead.
            building += "S" + Integer.valueOf(pieces.get(i).getShape()) + "C" + Integer.valueOf(pieces.get(i).getColor()) + ", ";
        }
        JOptionPane.showMessageDialog(null, building);
    }
    
    public boolean isValidGrouping(CopyOnWriteArrayList<GamePiece> pieces) {
        
        // declareGrouping(pieces);
        
        //assert(pieces.size() >= 0);
        
        // JOptionPane.showMessageDialog(null, "Checking whether `pieces` is empty...");
        
        // Check for empty sets.  We'll count "no pieces played" as valid, I suppose...
        if (pieces.isEmpty()) {
            return true;
        }
        
        // JOptionPane.showMessageDialog(null, "`pieces` isn't empty.");
        
        //if (true) return true;
        
        // Check for exact duplicates, which aren't allowed...
        int limit = pieces.size();
        for (int i = 0; i < limit; i++) {
            for (int j = i + 1; j < limit; j++) {
                //GamePiece piece_one = pieces.get(i);
                //GamePiece piece_one = new GamePiece(3, 4);
                GamePiece piece_one = pieces.get(i);
                //GamePiece piece_two = new GamePiece(5, 6);
                GamePiece piece_two = pieces.get(j);
                if (piece_one.altEquals(piece_two)) {
                    return false;
                }
            }
        }
        
        // if (true) return true;
        //if (true) return true;
        
        // By this point, we know there are no duplicates in the set, so it should be sufficient to see that they share shape or color.
        // We'll let the first piece in the set establish the color or shape (because it's arbitrary).
        final GamePiece first_piece = pieces.get(0);
        
        //if (true) return true;
        
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
    public int scoreGrouping(CopyOnWriteArrayList<GamePiece> pieces) {
        if (!isValidGrouping(pieces)) {
            return 0;
        } else if (pieces.size() >= 6) {
            return 12;
        } else {
            return pieces.size();
        }
    }
    
    public boolean isValidHorizontalAt(GameBoard board, int x, int y) {
        // JOptionPane.showMessageDialog(null, "Checking horizontal...");
        
        CopyOnWriteArrayList<GamePiece> pieces_in_line = new CopyOnWriteArrayList<GamePiece>();
        
        // Check whether there's actually a piece at this location.  If not, we'll deem line OK since empty spaces can exist on board.
        if (!board.isPieceAt(x, y)) {
            return true;
        } else {
            // JOptionPane.showMessageDialog(null, "Piece exists at the designated location (good).");
            pieces_in_line.add(new GamePiece(board.getPiece(x, y)));
        }
        
        // Because we've already added the piece at x
        int x_check = x - 1;
        
        
        
        // Add all pieces to the left
        while (board.isPieceAt(x_check, y)) {
            pieces_in_line.add(new GamePiece(board.getPiece(x_check, y)));
            x_check--;
        }
        
        // JOptionPane.showMessageDialog(null, "Pieces added to line (left).");
        
        // Again, we've already added the piece at x
        x_check = x + 1;
        
        // Add all pieces to the right
        while (board.isPieceAt(x_check, y)) {
            pieces_in_line.add(new GamePiece(board.getPiece(x_check, y)));
            x_check++;
        }
        
        // JOptionPane.showMessageDialog(null, "Pieces added to line (right)...");
        
        // Evaluate the pieces in this set
        return isValidGrouping(pieces_in_line);
    }
    
    public boolean isValidVerticalAt(GameBoard board, int x, int y) {
        CopyOnWriteArrayList<GamePiece> pieces_in_line = new CopyOnWriteArrayList<GamePiece>();
        
        // Check whether there's actually a piece at this location.  If not, we'll deem line OK since empty spaces can exist on board.
        if (!board.isPieceAt(x, y)) {
            return true;
        } else {
            pieces_in_line.add(new GamePiece(board.getPiece(x, y)));
        }
        
        // Because we've already added the piece at y
        int y_check = y - 1;
        
        // Add all pieces above
        while (board.isPieceAt(x, y_check)) {
            pieces_in_line.add(new GamePiece(board.getPiece(x, y_check)));
            y_check--;
        }
        
        // Again, we've already added the piece at y
        y_check = y + 1;
        
        // Add all pieces below
        while (board.isPieceAt(x, y_check)) {
            pieces_in_line.add(new GamePiece(board.getPiece(x, y_check)));
            y_check++;
        }
        
        // Evaluate the pieces in this set
        return isValidGrouping(pieces_in_line);
    }
    
    public int scoreMove(GameBoard board, CopyOnWriteArrayList<ProposedMove> play) {
        return 0;
    }
    
    public int find(CopyOnWriteArrayList<ProposedMove> additions, int x, int y) {
        for (int i = 0; i < additions.size(); i++) {
            if (additions.get(i).getX() == x && additions.get(i).getY() == y) {
                return i;
            }
        }
        return NO_MATCH;
    }
    
    public boolean contains(CopyOnWriteArrayList<ProposedMove> additions, int x, int y) {
        return find(additions, x, y) != NO_MATCH;
    }
}
