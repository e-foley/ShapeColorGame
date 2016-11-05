import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class RegularPolygonIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RegularPolygonIcon implements TileIcon {
    private Color color_;
    private int num_points_;
    private double rotation_;
    
    /**
     * Constructor for objects of class RegularPolygonIcon
     */
    public RegularPolygonIcon(Color color, int num_points, double rotation)
    {
        color_ = color;
        num_points_ = num_points;
        rotation_ = rotation;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        Polygon poly = new Polygon();
        double angle = rotation_;
        for (int i = 0; i < num_points_; ++i) {
            poly.addPoint(round(x_center + x_scale * Math.cos(angle)), round(y_center + y_scale * Math.sin(angle)));
            angle += 2.0 * Math.PI / num_points_;
        }
        g.setColor(color_);
        g.fill(poly);
    }
    
    private static int round(double nominal)
    {
        return (int)(Math.round(nominal));
    }
}
