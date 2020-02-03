package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class for unit tests of the ScorePad class.
 *
 * @author Sean Donohue
 * @Version 1.0
 */
public class ScorePadTest {
    private ScorePad scorePad;
    private Player john;
    private Player paul;
    private Player george;
    private Player ringo;

    /**
     * Set up to initialize each test. Creates a ScorePad with a list of
     * Four Players each with a starting score of zero.
     */
    @Before
    public void setup(){
        john = new BasicPlayer("John");
        paul = new BasicPlayer("Paul");
        george = new BasicPlayer("George");
        ringo = new BasicPlayer("Ringo");
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(john);
        playerList.add(paul);
        playerList.add(george);
        playerList.add(ringo);
        ScoreKeeper scoreKeeper = new CurrentScoreKeeper(playerList, 0);
        scorePad = new ScorePad(scoreKeeper);
    }

    /**
     * Tests the scoring a round that contains changes for all four Players
     */
    @Test
    public void testFullRoundScoring(){
        Map<Player, Integer> scoreMap = new HashMap<Player, Integer>();
        scoreMap.put(john, 100);
        scoreMap.put(paul, 50);
        scoreMap.put(george, 0);
        scoreMap.put(ringo, -50);
        ScoreKeeper round = new RoundScoreKeeper(scoreMap);
        scorePad.scoreRound(round);
        assertEquals("John's score should be 100", 100, scorePad.getPlayerScore(john));
        assertEquals("Paul's score should be 50", 50, scorePad.getPlayerScore(paul));
        assertEquals("George's score should be 0", 0, scorePad.getPlayerScore(george));
        assertEquals("Ringo's score should be -50", -50, scorePad.getPlayerScore(ringo));
    }

    /**
     * Tests the scoring of a round that does not contain changes for all Players
     */
    @Test
    public void testPartialRoundScoring(){
        Map<Player, Integer> scoreMap = new HashMap<Player, Integer>();
        scoreMap.put(john, 150);
        ScoreKeeper round = new RoundScoreKeeper(scoreMap);
        scorePad.scoreRound(round);
        assertEquals("John's score should be 150", 150, scorePad.getPlayerScore(john));
        assertEquals("Paul's score should be 0", 0, scorePad.getPlayerScore(paul));
        assertEquals("George's score should be 0", 0, scorePad.getPlayerScore(george));
        assertEquals("Ringo's score should be 0", 0, scorePad.getPlayerScore(ringo));
    }

    /**
     * Tests the exception is thrown when a null round is passed
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullRound(){
        scorePad.scoreRound(null);
    }

    /**
     * Test the exception is thrown when the unimplemented display method is called
     */
    @Test (expected = UnsupportedOperationException.class)
    public void testDisplay(){
        scorePad.displayScorePad();
    }
}
