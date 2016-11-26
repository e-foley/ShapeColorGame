import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;

/**
 * Write a description of class TextIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextIcon implements TileIcon {
    private Color color_;
    private String text_;
    
    /**
     * Constructor for objects of class TextIcon
     */
    public TextIcon(Color color, String text)
    {
        color_ = color;
        text_ = text;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        // Rectangle2D.Double box = new Rectangle2D.Double(x_center - x_scale, y_center - y_scale, x_scale * 2.0f, y_scale * 2.0f);
        g.setColor(color_);
        // g.fill(box);
        g.drawString(text_, round(x_center), round(y_center));
    }
    
    private static int round(double nominal)
    {
        return (int)(Math.round(nominal));
    }
}
