

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * The test class StandardPlacementRulesTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StandardPlacementRulesTest
{
    /**
     * Default constructor for test class StandardPlacementRulesTest
     */
    public StandardPlacementRulesTest()
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
    public void testGroupingNone()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        assertTrue(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testGroupingSolo()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        pieces.add(new GamePiece(0, 0));
        assertTrue(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testGroupingTwoTotallyDifferent()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        pieces.add(new GamePiece(0, 0));
        pieces.add(new GamePiece(1, 1));
        assertFalse(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testGroupingTwoShareShape()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        pieces.add(new GamePiece(0, 0));
        pieces.add(new GamePiece(0, 1));
        assertTrue(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testGroupingTwoShareSameColor()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        pieces.add(new GamePiece(0, 0));
        pieces.add(new GamePiece(1, 0));
        assertTrue(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testGroupingTwoIdentical()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        pieces.add(new GamePiece(0, 0));
        pieces.add(new GamePiece(0, 0));
        assertFalse(rules.isValidGrouping(pieces));
    }
    
    @Test
    public void testQuirkleBuildingSameColor()
    {
        StandardPlacementRules rules = new StandardPlacementRules();
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        assertEquals(0, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(0, 0));
        assertEquals(1, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(5, 0));
        assertEquals(2, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(1, 0));
        assertEquals(3, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(4, 0));
        assertEquals(4, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(2, 0));
        assertEquals(5, rules.scoreGrouping(pieces));
        pieces.add(new GamePiece(3, 0));
        assertEquals(12, rules.scoreGrouping(pieces));
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
