import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;

/**
 * Write a description of class TileGraphic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileGraphic
{
    /**
     * Constructor for objects of class TileGraphic
     */
    public TileGraphic(TileIcon icon, double icon_scale_ratio)
    {
        icon_ = icon;
        icon_scale_ratio_ = icon_scale_ratio;
    }
    
    public void draw(Graphics2D g, double x_center, double y_center, double x_scale, double y_scale) {
        // Background
        Rectangle2D.Double background = new Rectangle2D.Double(x_center - x_scale, y_center - y_scale, x_scale * 2.0f, y_scale * 2.0f);
        //g.setColor(new Color(0, 0, 100));
        g.setColor(Color.BLACK);
        //g.draw(background);  // Draws the outline
        g.fill(background);  // Fills the shape itself
        g.setColor(Color.GRAY);  // Draw border after fill for contrast when tiles are adjacent
        g.draw(background);
        icon_.draw(g, x_center, y_center, x_scale * icon_scale_ratio_, y_scale * icon_scale_ratio_);
        
        // DEBUG
        // g.setColor(Color.GRAY);
        // g.drawString(String.valueOf(y_center),(int)(x_center),(int)(y_center));
        // Highlight
        // Ellipse2D.Double highlight = new Ellipse2D.Double(x_center
        // g.setColor(Color.WHITE);
    }
    
    private TileIcon icon_;
    private double icon_scale_ratio_;
}
