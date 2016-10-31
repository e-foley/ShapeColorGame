public class BoundingBox {
    public double x_min;
    public double x_max;
    public double y_min;
    public double y_max;
    
    public BoundingBox() {
        x_min = 0;
        x_max = 0;
        y_min = 0;
        y_max = 0;
    }
    
    public BoundingBox(double x_min, double x_max, double y_min, double y_max) {
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
    }
    
    public double width() {
        return x_max - x_min;
    }
    
    public double height() {
        return y_max - y_min;
    }
}