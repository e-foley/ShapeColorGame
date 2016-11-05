
/**
 * Write a description of class DenormalizationResult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DenormalizationResult
{
    public DenormalizationResult()
    {
        success = false;
        value_x = 0;
        value_y = 0;
    }
    
    public DenormalizationResult(boolean success, int value_x, int value_y)
    {
        this.success = success;
        this.value_x = value_x;
        this.value_y = value_y;
    }
    
    public boolean success;
    public int value_x;
    public int value_y;
}