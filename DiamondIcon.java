import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class DiamondIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DiamondIcon implements TileIcon {
    private Color color_;
    
    /**
     * Constructor for objects of class DiamondIcon
     */
    public DiamondIcon(Color color)
    {
        // initialise instance variables
        color_ = color;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        Polygon poly = new Polygon();
        poly.addPoint(round(x_center - x_scale), round(y_center));
        poly.addPoint(round(x_center), round(y_center - y_scale));
        poly.addPoint(round(x_center + x_scale), round(y_center));
        poly.addPoint(round(x_center), round(y_center + y_scale));
        g.setColor(color_);
        g.fill(poly);
    }
    
    private static int round(double nominal)
    {
        return (int)(Math.round(nominal));
    }
}
