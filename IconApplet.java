import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

/**
   This applet lets the user move a rectangle by clicking
   the mouse.
*/
public class IconApplet extends Applet
{  
   public IconApplet()
   {  
      // the rectangle that the paint method draws
      // icon = new SquareIcon(Color.RED);
      icon = new DiamondIcon(Color.RED);
      
      // add mouse press listener         

      class MousePressListener implements MouseListener
      {  
         public void mousePressed(MouseEvent event)
         {  
            int x = event.getX();
            int y = event.getY();
            // box.setLocation(x, y);
            
            repaint();
         }

         // do-nothing methods
         public void mouseReleased(MouseEvent event) {}
         public void mouseClicked(MouseEvent event) {}
         public void mouseEntered(MouseEvent event) {}
         public void mouseExited(MouseEvent event) {}
      }
      

         
      MouseListener listener = new MousePressListener();
      addMouseListener(listener);
   }

   public void paint(Graphics g)
   {  
      Graphics2D g2 = (Graphics2D)g;
      g2.setColor(Color.RED);
      //g2.draw(box);
      icon.draw(g2, 100, 300, X_SCALE, Y_SCALE);
      CircleIcon circle = new CircleIcon(Color.GREEN);
      circle.draw(g2, 50.0, 50.0, 20.0, 20.0);
      
      TileGraphic tile = new TileGraphic(icon, 0.67);
      tile.draw(g2, 300.0, 300.0, 25.0, 25.0);
   }

   private TileIcon icon;
   private static final int BOX_X = 100;
   private static final int BOX_Y = 100;
   private static final double X_SCALE = 20.0f;
   private static final double Y_SCALE = 15.0f;
} 
