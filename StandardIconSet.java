import java.awt.Color;

public class StandardIconSet implements IconSet {
    // TODO: Get the concept of color out of here!
    public TileIcon getIcon(int i, Color color) {
        TileIcon shape;
        switch (i) {
            case 0:
                shape = new CircleIcon(color);
                break;
            case 1:
                shape = new RegularPolygonIcon(color, 4, 0);
                break;
            case 2:
                shape = new RegularPolygonIcon(color, 4, Math.PI/4.0);
                break;
            case 3:
                shape = new StarIcon(color, 4, 0.33, Math.PI/4.0);
                break;
            case 4:
                shape = new StarIcon(color, 8, 0.425, 0.0);
                break;
            case 5:
                shape = new FlowerIcon(color, 4, 0.35, 0.65);
                break;
            default:
                shape = new CircleIcon(color);  // Make dummy icon
                break;
        }
        return shape;
    }
    
    public int numIconsDefined() {
        return 6;
    }
}
