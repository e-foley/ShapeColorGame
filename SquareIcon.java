import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;

/**
 * Write a description of class SquareIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SquareIcon implements TileIcon {
    private Color color_;
    
    /**
     * Constructor for objects of class SquareIcon
     */
    public SquareIcon(Color color)
    {
        // initialise instance variables
        color_ = color;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        Rectangle2D.Double box = new Rectangle2D.Double(x_center - x_scale, y_center - y_scale, x_scale * 2.0f, y_scale * 2.0f);
        g.setColor(color_);
        g.fill(box);
    }
}
