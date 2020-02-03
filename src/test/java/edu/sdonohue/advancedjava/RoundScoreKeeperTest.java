package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class for unit tests of the RoundScoreKeeper class.
 *
 * @author Sean Donohue
 * @Version 1.0
 */
public class RoundScoreKeeperTest {
    RoundScoreKeeper round;
    private Player john;
    private Player paul;
    private Player george;
    private Player ringo;

    /**
     * Set up to initialize each test. Creates a RoundScoreKeeper with
     * four Players each with varied scores for the round.
     */
    @Before
    public void setup(){
        john = new BasicPlayer("John");
        paul = new BasicPlayer("Paul");
        george = new BasicPlayer("George");
        ringo = new BasicPlayer("Ringo");
        Map<Player, Integer> scoreMap = new HashMap<Player, Integer>();
        scoreMap.put(john, 100);
        scoreMap.put(paul, 50);
        scoreMap.put(george, 0);
        scoreMap.put(ringo, -50);
        round = new RoundScoreKeeper(scoreMap);
    }

    /**
     * Tests that the RoundScoreKeeper will not accept modifications to scores.
     */
    @Test (expected = UnsupportedOperationException.class)
    public void testNoModify(){
        round.addToPlayerScore(john, -1000);
    }

}
