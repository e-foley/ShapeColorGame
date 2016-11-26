import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

// TODO: Make this class more sophisticated, including some sort of way of arranging tiles
// TODO: Add setters and getters
public class RackDrawer {
    public RackDrawer() {}

    public static void drawRack(Graphics2D g, Rack rack, double x_left, double y_top, double tile_size, Palette palette, IconSet icon_set) {
        for (int i = 0; i < rack.numPieces(); ++i) {
            final GamePiece piece = rack.getPiece(i);
            final Color color = palette.getColor(piece.getColor());
            final TileIcon shape = icon_set.getIcon(piece.getShape(), color);
            final TileGraphic tile = new TileGraphic(shape, 0.6);  // TODO: remove magic number
            tile.draw(g, x_left + (2*i + 1) * tile_size / 2.0, y_top + tile_size / 2.0, tile_size / 2.0, tile_size / 2.0);
        }
    }
}
