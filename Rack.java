import java.util.ArrayList;

public class Rack {
    private ArrayList<GamePiece> pieces;
    // private int capacity;  Should capacity be a property of the rack?

    public Rack() {
        pieces = new ArrayList<GamePiece>();
    }

    public void addPiece(GamePiece piece) {
        pieces.add(piece);
    }
    
    public GamePiece removePiece(int i) {
        return pieces.remove(i);
    }
    
    public GamePiece getPiece(int i) {
        return pieces.get(i);
    }
    
    public int numPieces() {
        return pieces.size();
    }
}
