public class Viewport {
    private BoundingBox box_;
    private CameraPosition camera_;

    Viewport(BoundingBox box, CameraPosition camera) {
        box_ = box;
        camera_ = camera;
    }

    public double toCameraX(int x) {
        return x - (box_.x_min + box_.width() / 2.0);
    }
    
    public double toCameraY(int y) {
        return y - (box_.y_min + box_.height() / 2.0);
    }
}
