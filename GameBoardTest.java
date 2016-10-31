

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GameBoardTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameBoardTest
{
    /**
     * Default constructor for test class GameBoardTest
     */
    public GameBoardTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    @Test
    public void testSpan() {
        GameBoard board = new GameBoard();
        board.addPiece(3, 4, new GamePiece());
        board.addPiece(3, 5, new GamePiece());
        board.addPiece(3, 6, new GamePiece());
        board.addPiece(4, 6, new GamePiece());
        assertEquals(2, board.getBoundaries().xSpan());
        assertEquals(3, board.getBoundaries().ySpan());
    }
    
    @Test
    public void testAddRemove() {
        GameBoard board = new GameBoard();
        assertFalse(board.isPieceAt(123, 123));
        board.addPiece(123, 123, new GamePiece());
        assertTrue(board.isPieceAt(123, 123));
        board.removePiece(123, 123);
        assertFalse(board.isPieceAt(123, 123));
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}

