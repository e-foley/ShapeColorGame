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
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.concurrent.CopyOnWriteArrayList;

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
        bank = new StandardTileBank();
        randomizer = new Random();
        counter_x = 0;
        counter_y = 0;
        cursor_normal_x = 0.0;
        cursor_normal_y = 0.0;
        cursor_board_coords_x = 0;
        cursor_board_coords_y = 0;
        board_box = new BoundingBox();  // Make 
        viewport = new Viewport(board_box, camera);
        palette = new StandardPalette();
        icon_set = new StandardIconSet();
        text_color = Color.BLACK;
        placement_rules = new StandardPlacementRules();
        
        rack_one = new Rack();
        
        for (int i = 0; i < 6; ++i) {
            rack_one.addPiece(new GamePiece(bank.drawRandom(randomizer)));
        }
        
        // Just so we don't start at weird zoom!
        board.addPiece(cursor_board_coords_x, cursor_board_coords_y, new GamePiece(bank.drawRandom(randomizer)));
        
        piece_selected = bank.drawRandom(randomizer);
        
        repaint();

        class MousePressListener implements MouseListener
        {  
            public void mousePressed(MouseEvent event)
            {  
                int x = event.getX();
                int y = event.getY();

                recalculateNormalCoordinates(x, y);
                
                
                if (event.getButton() == MouseEvent.BUTTON1 /*&& piece_selected != null && !board.isPieceAt(cursor_board_coords_x, cursor_board_coords_y)*/) {
                    if (piece_selected == null) {
                        JOptionPane.showMessageDialog(null, "No piece selected!");;
                    } else {
                        // Check if the placement is valid
                        // TODO: Remove the "isPieceAt" check from the above conditional
                        CopyOnWriteArrayList<ProposedMove> additions = new CopyOnWriteArrayList<ProposedMove>();
                        additions.add(new ProposedMove(piece_selected, cursor_board_coords_x, cursor_board_coords_y));
                        if (placement_rules.isValidMove(board, additions)) {
                            //JOptionPane.showMessageDialog(null, "Valid!");
                            board.addPiece(cursor_board_coords_x, cursor_board_coords_y, new GamePiece(piece_selected));
                            repaint();
                            if (!bank.isEmpty()) {
                                piece_selected = bank.drawRandom(randomizer);
                            } else {
                                piece_selected = null;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Not valid!");
                        }
                    }
                } else if (event.getButton() == MouseEvent.BUTTON3 && board.isPieceAt(cursor_board_coords_x, cursor_board_coords_y)) {
                    // GamePiece piece_removing = board.getPiece(cursor_board_coords_x, cursor_board_coords_y);
                    GamePiece piece_removing = board.removePiece(cursor_board_coords_x, cursor_board_coords_y);
                    if (bank.isEmpty()) {
                        bank.addPiece(new GamePiece(piece_removing));
                        piece_selected = piece_removing;
                    } else {
                        bank.addPiece(new GamePiece(piece_removing));
                    }
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Debug.");
                }
//                 if (event.getButton() == MouseEvent.BUTTON1 && !bank.isEmpty()) {
//                     board.addPiece(++counter_x, counter_y, bank.drawRandom(randomizer));
//                     repaint();
//                 } else if (event.getButton() == MouseEvent.BUTTON3 && !bank.isEmpty()) {
//                     board.addPiece(counter_x, ++counter_y, bank.drawRandom(randomizer));
//                     repaint();
//                 }
                //board.addPiece(x, y, new GamePiece(0, 0));

            }

            // do-nothing methods
            public void mouseReleased(MouseEvent event) {}

            public void mouseClicked(MouseEvent event) {}

            public void mouseEntered(MouseEvent event) {}

            public void mouseExited(MouseEvent event) {}
        }

        class MouseMoveListener implements MouseMotionListener
        {  
            public void mouseMoved(MouseEvent event)
            {
                int x = event.getX();
                int y = event.getY();
                if(recalculateNormalCoordinates(x, y)) {
                    onNormalizedCoordinateChange();
                }
            }

            public void mouseDragged(MouseEvent event) {}
        }

        MouseListener listener = new MousePressListener();
        MouseMotionListener move_listener = new MouseMoveListener();
        addMouseListener(listener);
        addMouseMotionListener(move_listener);
    }

    // Returns true if the value is different than before
    public boolean recalculateNormalCoordinates(int x, int y) {
        // TODO: Consider whether the camera should hold this information.
        Dimension applet_size = this.getSize();
        int applet_height = applet_size.height;
        int applet_width = applet_size.width;
        cursor_normal_x = camera.inverseXPosition(viewport.toCameraX(x));
        cursor_normal_y = camera.inverseYPosition(viewport.toCameraY(y));
        DenormalizationResult result = BoardDrawer.denormalize(cursor_normal_x, cursor_normal_y, true);
        boolean new_value = false;
        // TODO: Consider whether we actually want to mirror these fields in the applet class or whether we should just store the denormalization result
        if (result.success) {
            valid_board_coords = true;
            if (cursor_board_coords_x != result.value_x) {
                cursor_board_coords_x = result.value_x;
                new_value = true;
            }
            if (cursor_board_coords_y != result.value_y) {
                cursor_board_coords_y = result.value_y;
                new_value = true;
            }
        } else {
            valid_board_coords = false;
        }
        
        return new_value;
    }
    
    // TODO: Remove zooming from paint function.  This should occur elsewhere.
    public void paint(Graphics g) {
        Dimension applet_size = this.getSize();
        int applet_height = applet_size.height;
        int applet_width = applet_size.width;
        // Populate BoundingBox fields individually because "new" BoundingBox wouldn't be recognized properly.
        board_box.x_min = 0.0;
        board_box.x_max = applet_width - 1.0;
        board_box.y_min = 0.0;
        board_box.y_max = applet_height - RACK_ALLOWANCE - 1.0;
        double estimated_zoom_x = (double)(board_box.width()) / (BoardDrawer.getNormalBoardWidth(board) + 2.0 * TILE_MARGIN);
        double estimated_zoom_y = (double)(board_box.height()) / (BoardDrawer.getNormalBoardHeight(board) + 2.0 * TILE_MARGIN);
        double min_zoom = Math.min(estimated_zoom_x, estimated_zoom_y);
        camera.setXZoom(min_zoom);
        camera.setYZoom(min_zoom);
        camera.setXCenter(BoardDrawer.getNormalBoardCenterX(board));
        camera.setYCenter(BoardDrawer.getNormalBoardCenterY(board));

        Graphics2D g2 = (Graphics2D)g;
        Rectangle2D.Double outer_border = new Rectangle2D.Double(0, 0, applet_width - 1, applet_height - 1);
        g2.draw(outer_border);
        g2.setColor(text_color);
        g2.drawString("(" + String.valueOf(cursor_board_coords_x) + ", " + String.valueOf(cursor_board_coords_y) + ")", 50, 50);
        // g2.drawString("(" + String.valueOf(cursor_normal_x) + ", " + String.valueOf(cursor_normal_y) + ")", 50, 80);
        BoardDrawer.drawBoard(g2, board_box, camera, board, palette, icon_set);
        RackDrawer.drawRack(g2, rack_one, 0.0, applet_height - RACK_ALLOWANCE, RACK_ALLOWANCE);
        
        
        // TODO: OFFLOAD LATER
        if (piece_selected != null) {
            final GamePiece piece = piece_selected;
            final Color color = palette.getColor(piece.getColor());
            final TileIcon shape = icon_set.getIcon(piece.getShape(), color);
            final TileGraphic tile = new TileGraphic(shape, 0.6);  // TODO: remove magic number
    
            // TODO: Have this calculation make more intuitive sense
//             tile.draw(g, box.width() / 2.0 + camera.getTransformedXPosition(getNormalTileCenterX(coords.getX())),
//                          box.height() / 2.0 + camera.getTransformedYPosition(getNormalTileCenterY(coords.getY())),
//                          camera.getTransformedXScale(0.5),
//                          camera.getTransformedYScale(0.5));
//           tile.draw(g, board_box.width() / 2.0 + camera.getTransformedXPosition(getNormalTileCenterX(coords.getX())),
//                        board_boxox.height() / 2.0 + camera.getTransformedYPosition(getNormalTileCenterY(coords.getY())),
//                        camera.getTransformedXScale(0.5),
//                        camera.getTransformedYScale(0.5));
            GameBoard temp_board = new GameBoard();
            temp_board.addPiece(cursor_board_coords_x, cursor_board_coords_y, new GamePiece(piece_selected));
            // BoardDrawer.drawBoard(g2, board_box, camera, temp_board, palette, icon_set);
            
            // draw the selected tile as a preview at top left
            tile.draw((Graphics2D)g, camera.getTransformedXScale(0.15), camera.getTransformedYScale(0.15), camera.getTransformedXScale(0.15), camera.getTransformedYScale(0.15));
        }
        
        
    }
    
    public void onNormalizedCoordinateChange() {
        // Draw "next" tile at location
        CopyOnWriteArrayList<ProposedMove> additions = new CopyOnWriteArrayList<ProposedMove>();
        additions.add(new ProposedMove(piece_selected, cursor_board_coords_x, cursor_board_coords_y));
        if (placement_rules.isValidMove(board, additions)) {
            text_color = Color.GREEN;
        } else {
            text_color = Color.RED;
        }
        repaint();
    }

    //    public void update(Graphics g)
    //    {
    // 
    //    }

    private Random randomizer;
    private TileBank bank;
    private GameBoard board;
    private TileIcon icon;
    private int counter_x;
    private int counter_y;
    private CameraPosition camera;
    private double cursor_normal_x;
    private double cursor_normal_y;
    private int cursor_board_coords_x;
    private int cursor_board_coords_y;
    private boolean valid_board_coords;
    private BoundingBox board_box;
    private Rack rack_one;
    private Viewport viewport;
    private Palette palette;
    private IconSet icon_set;
    private volatile Color text_color;
    private GamePiece piece_selected;  // Will we need to store index?
    private PlacementRules placement_rules;
    private static final double RACK_ALLOWANCE = 100.0;
    private boolean repaint_workaround = false;
    private static final double TILE_MARGIN = 1.0;  // TODO: Consider setting this based on "maximum play" size
} 
