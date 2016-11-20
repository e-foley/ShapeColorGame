import java.awt.Color;

public class StandardPalette implements Palette {
    public Color getColor(int i) {
        Color color;
        switch (i) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = new Color(0, 128, 255);
                break;
            case 2:
                color = new Color(0, 196, 0);
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = new Color(128, 64, 255);
                break;
            case 5:
                color = new Color(255, 128, 0);
                break;
            default:
                color = Color.GRAY;
                break;
        }
        return color;
    }
    
    public int numColorsDefined() {
        return 6;
    }
}
