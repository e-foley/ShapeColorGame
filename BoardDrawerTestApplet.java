import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;

/**
   This applet lets the user move a rectangle by clicking
   the mouse.
*/
public class BoardDrawerTestApplet extends Applet
{  
   public BoardDrawerTestApplet()
   {  
       camera = new CameraPosition();
       board = new GameBoard();
       board.addPiece(0, 0, new GamePiece(1, 0));
       //board.addPiece(1, 0, new GamePiece(0, 1));
       //board.addPiece(2, 0, new GamePiece(1, 0));
       //board.addPiece(2, 1, new GamePiece(1, 1));

       counter_x = 0;
       counter_y = 0;
      // the rectangle that the paint method draws
      icon = new SquareIcon(Color.RED);
         
      // add mouse press listener         

      class MousePressListener implements MouseListener
      {  
         public void mousePressed(MouseEvent event)
         {  
            int x = event.getX();
            int y = event.getY();
            // box.setLocation(x, y);
            
            if (event.getButton() == MouseEvent.BUTTON1) {
                board.addPiece(++counter_x, counter_y, new GamePiece(0, 0));
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                board.addPiece(counter_x, ++counter_y, new GamePiece(0, 0));
            }
            //board.addPiece(x, y, new GamePiece(0, 0));
            
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
        Dimension applet_size = this.getSize();
        int applet_height = applet_size.height;
        int applet_width = applet_size.width;
        double estimated_zoom_x = (double)(applet_width) / BoardDrawer.getNormalBoardWidth(board);
        double estimated_zoom_y = (double)(applet_height) / BoardDrawer.getNormalBoardHeight(board);
        double min_zoom = Math.min(estimated_zoom_x, estimated_zoom_y);
        camera.setXZoom(min_zoom);
        camera.setYZoom(min_zoom);
        //camera.setXZoom(30.0);
        //camera.setYZoom(30.0);
        camera.setXCenter(BoardDrawer.getNormalBoardCenterX(board));
        camera.setYCenter(BoardDrawer.getNormalBoardCenterY(board));
        //camera.setXCenter(0.0);
        //camera.setYCenter(0.0);
        
        Graphics2D g2 = (Graphics2D)g;
        Rectangle2D.Double test = new Rectangle2D.Double(0, 0, applet_width - 1, applet_height - 1);
        g2.draw(test); 
        BoardDrawer.drawBoard(g2, new BoundingBox(0.0, applet_width - 1.0, 0.0, applet_height - 1.0), camera, board);
   }

   private GameBoard board;
   private TileIcon icon;
   private int counter_x;
   private int counter_y;
   private CameraPosition camera;
} 
