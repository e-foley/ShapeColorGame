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
public class CircleIcon implements TileIcon {
    // instance variables - replace the example below with your own
    private Color color_;

    /**
     * Constructor for objects of class SquareIcon
     */
    public CircleIcon(Color color)
    {
        // initialise instance variables
        color_ = color;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x_center - x_scale, y_center - y_scale, x_scale * 2.0f, y_scale * 2.0f);
        g.setColor(color_);
        // g.draw(ellipse);
        g.fill(ellipse);
    }
}
