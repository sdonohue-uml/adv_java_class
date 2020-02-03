package edu.sdonohue.advancedjava;

import java.util.*;

/**
 * The CurrentScoreKeeper class is a basic scorekeeper that keeps track of
 * only the current score of each player in any game with points. History of
 * the changes to scores is not maintained. Extends AbstractScoreKeeper.
 *
 * @author Sean Donohue
 * @version 1.0
*/
public class CurrentScoreKeeper extends AbstractScoreKeeper{

    /**
     * Constructs a new CurrentScoreKeeper using the provided list of
     * Players and the common starting score.
     *
     * @param players A List of Players
     * @param startingScore The starting score for all players
     */
    public CurrentScoreKeeper(List<Player> players, int startingScore){
        scores = new LinkedHashMap<Player, Integer>(players.size());
        for (Player player : players){
            scores.put(player, startingScore);
        }
    }
}
