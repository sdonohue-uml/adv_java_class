package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for unit tests of the CurrentScoreKeeper class.
 *
 * @author Sean Donohue
 * @Version 1.0
 */
public class CurrentScoreKeeperTest {
    private CurrentScoreKeeper currentScoreKeeper;
    private Player john;
    private Player paul;
    private Player george;
    private Player ringo;

    /**
     * Set up to initialize each test. Creates a CurrentScoreKeeper with
     * four Players each with a starting score of zero.
     */
    @Before public void setup(){
        john = new BasicPlayer("John");
        paul = new BasicPlayer("Paul");
        george = new BasicPlayer("George");
        ringo = new BasicPlayer("Ringo");
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(john);
        playerList.add(paul);
        playerList.add(george);
        playerList.add(ringo);
        currentScoreKeeper = new CurrentScoreKeeper(playerList, 0);
    }

    /**
     * Tests the construction of the CurrentScoreKeeper.
     */
    @Test public void testInit(){
        assertEquals("Num Players should be 4", 4, currentScoreKeeper.getNumPlayers());
        assertEquals("John's score should be 0", 0, currentScoreKeeper.getPlayerScore(john));
        assertEquals("Paul's score should be 0", 0, currentScoreKeeper.getPlayerScore(paul));
        assertEquals("George's score should be 0", 0, currentScoreKeeper.getPlayerScore(george));
        assertEquals("Ringo's score should be 0", 0, currentScoreKeeper.getPlayerScore(ringo));
    }

    /**
     * Test modification of Player's scores.
     */
    @Test public void testModify(){
        currentScoreKeeper.addToPlayerScore(john, 100);
        currentScoreKeeper.addToPlayerScore(paul, 0);
        currentScoreKeeper.addToPlayerScore(george, -50);

        assertEquals("John's score should be 100", 100, currentScoreKeeper.getPlayerScore(john));
        assertEquals("Paul's score should be 0", 0, currentScoreKeeper.getPlayerScore(paul));
        assertEquals("George's score should be -50", -50, currentScoreKeeper.getPlayerScore(george));
        assertEquals("Ringo's score should be 0", 0, currentScoreKeeper.getPlayerScore(ringo));
    }

    /**
     * Tests the proper return of the correct Player with the single highest score.
     */
    @Test public void testSingleWinner(){
        currentScoreKeeper.addToPlayerScore(john, 100);
        List<Player> first = currentScoreKeeper.getFirstPlace();
        assertEquals("There should be a single winner", 1, first.size());
        assertTrue("John should be winning", first.contains(john));
    }

    /**
     * Tests the proper return of the list of all Players tied for the highest score.
     */
    @Test public void testTiedWinners(){
        currentScoreKeeper.addToPlayerScore(john, 100);
        currentScoreKeeper.addToPlayerScore(george, 100);
        List<Player> first = currentScoreKeeper.getFirstPlace();
        assertEquals("There should be 2 winners", 2, first.size());
        assertTrue("John should be winning", first.contains(john));
        assertTrue("George should be winning", first.contains(george));
    }

    /**
     * Tests that the exception is thrown if an unknown new Player is queried.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInvalidPlayer(){
        currentScoreKeeper.getPlayerScore(new BasicPlayer("Yoko"));
    }

    /**
     * Tests that the exception if thrown if a null Player is queried.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullPlayer(){
        currentScoreKeeper.getPlayerScore(null);
    }
}
