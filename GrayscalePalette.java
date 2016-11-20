import java.awt.Color;

public class GrayscalePalette implements Palette {
    private int num_shades_;

    public GrayscalePalette(int num_shades) {
        num_shades_ = num_shades;
    }

    public Color getColor(int i) {
        final int shade = (int)((i + 1) * (255.0 / (num_shades_)));
        return new Color(shade, shade, shade);
    }
    
    public int numColorsDefined() {
        return num_shades_;
    }
}
