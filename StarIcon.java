import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class StarIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarIcon implements TileIcon {
    private Color color_;
    private int num_points_;
    private double inner_radius_;
    private double rotation_;
    
    /**
     * Constructor for objects of class StarIcon
     */
    public StarIcon(Color color, int num_points, double inner_radius, double rotation)
    {
        color_ = color;
        num_points_ = num_points;
        inner_radius_ = inner_radius;
        rotation_ = rotation;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        Polygon poly = new Polygon();
        double angle = rotation_;
        for (int i = 0; i < num_points_; ++i) {
            poly.addPoint(round(x_center + x_scale * Math.cos(angle)), round(y_center + y_scale * Math.sin(angle)));
            angle += Math.PI / num_points_;  // (Only dividing pi because we have two vertices to draw per "point.")
            poly.addPoint(round(x_center + inner_radius_ * x_scale * Math.cos(angle)), round(y_center + inner_radius_ * y_scale * Math.sin(angle)));
            angle += Math.PI / num_points_;  // As before.
        }
        g.setColor(color_);
        g.fill(poly);
    }
    
    private static int round(double nominal)
    {
        return (int)(Math.round(nominal));
    }
}
