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
public class FlowerIcon implements TileIcon {
    // instance variables - replace the example below with your own
    private Color color_;
    private int num_circles_;
    private double circle_size_;
    private double radius_;

    /**
     * Constructor for objects of class SquareIcon
     */
    public FlowerIcon(Color color, int num_circles, double circle_size, double radius)
    {
        // initialise instance variables
        color_ = color;
        num_circles_ = num_circles;
        circle_size_ = circle_size;
        radius_ = radius;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        g.setColor(color_);
        double angle = 0.0;
        for (int i = 0; i < num_circles_; ++i) {
            Ellipse2D.Double ellipse = new Ellipse2D.Double(x_center + x_scale * radius_ * Math.cos(angle) - x_scale * circle_size_,
                                                            y_center + y_scale * radius_ * Math.sin(angle) - y_scale * circle_size_,
                                                            2.0 * circle_size_ * x_scale, 2.0 * circle_size_ * y_scale);
            g.fill(ellipse);
            angle += 2.0 * Math.PI / num_circles_;
        }
    }
}
