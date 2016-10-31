
/**
 * Write a description of class CameraPosition here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CameraPosition
{
    // instance variables - replace the example below with your own
    private double x_center;
    private double y_center;
    private double x_zoom;
    private double y_zoom;

    /**
     * Constructor for objects of class CameraPosition
     */
    public CameraPosition()
    {
        // initialise instance variables
        x_center = 0.0;
        y_center = 0.0;
        x_zoom = 1.0;
        y_zoom = 1.0;
    }
    
    public CameraPosition(double x_center, double y_center, double x_zoom, double y_zoom) {
        this.x_center = x_center;
        this.y_center = y_center;
        this.x_zoom = x_zoom;
        this.y_zoom = y_zoom;
    }
    
    public void setXCenter(double new_x_center) {
        x_center = new_x_center;
    }
    
    public void setYCenter(double new_y_center) {
        y_center = new_y_center;
    }
    
    public void setXZoom(double new_x_zoom) {
        x_zoom = new_x_zoom;
    }
    
    public void setYZoom(double new_y_zoom) {
        y_zoom = new_y_zoom;
    }
    
    public double getXCenter() {
        return x_center;
    }
    
    public double getYCenter() {
        return y_center;
    }
    
    public double getXZoom() {
        return x_zoom;
    }
    
    public double getYZoom() {
        return y_zoom;
    }
    
    public double getTransformedXPosition(double original_x_position) {
        return x_zoom * (original_x_position - x_center); 
    }
    
    public double getTransformedYPosition(double original_y_position) {
        return y_zoom * (original_y_position - y_center);
    }
    
    public double getTransformedXScale(double original_x_scale) {
        return x_zoom * original_x_scale;
    }
    
    public double getTransformedYScale(double original_y_scale) {
        return y_zoom * original_y_scale;
    }
    
    // TODO: Bounds checking?
    public double inverseXPosition(double transformed_x_position) {
        return transformed_x_position / x_zoom + x_center;
    }
    
    // TODO: Bounds checking?
    public double inverseYPosition(double transformed_y_position) {
        return transformed_y_position / y_zoom + y_center;
    }
    
    // TODO: Bounds checking?
    public double inverseXScale(double transformed_x_scale) {
        return transformed_x_scale / x_zoom;
    }
    
    // TODO: Bounds checking?
    public double inverseYscale(double transformed_y_scale) {
        return transformed_y_scale / y_zoom;
    }
}
