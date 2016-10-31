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
    // instance variables - replace the example below with your own
    private int x;
    
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
        //g.draw(box);
        g.fill(box);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
    
    Color color_;
}
