
/**
 * Write a description of class Coords here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coords
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;

    /**
     * Constructor for objects of class Coords
     */
    public Coords()
    {
        // initialise instance variables
        x = 0;
        y = 0;
    }
    
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    @Override
    public int hashCode() {
        return 65536 * x + y;
    }
    
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Coords))
            return false;
        if (obj == this)
            return true;

        Coords rhs = (Coords) obj;
        return (x == rhs.x && y == rhs.y);
    }
}
