package edu.sdonohue.advancedjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The AbstractScoreKeeper class is an abstract scorekeeper that can keep track
 * of a list of players and a single score value associated with each player.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class AbstractScoreKeeper  implements ScoreKeeper{
    /**
     * A Map of Players and their Integer score values
     */
    protected Map<Player, Integer> scores;

    /**
     * @inheritDoc
     */
    @Override
    public void addToPlayerScore(Player player, int amount){
        Map.Entry<Player, Integer> entry = getPlayerEntry(player);
        int score = entry.getValue();
        score += amount;
        entry.setValue(score);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getPlayerScore(Player player){
        Map.Entry<Player, Integer> entry = getPlayerEntry(player);
        return entry.getValue();
    }

    /**
     * Internal method for finding the map entry for a Player, which can
     * then be modified or queried.
     *
     * @param player The Player whose entry should be returned
     * @return The Player's map entry
     */
    private Map.Entry<Player, Integer> getPlayerEntry(Player player){
        if (player == null){
            throw new IllegalArgumentException("Player was null");
        }

        for (Map.Entry<Player, Integer> entry : scores.entrySet()){
            if (entry.getKey().equals(player)){
                return entry;
            }
        }

        throw new IllegalArgumentException("Player not found: " + player.getName());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Player> getFirstPlace(){
        List<Player> first = new ArrayList<Player>();
        int highScore = Integer.MIN_VALUE;
        for (Map.Entry<Player, Integer> entry : scores.entrySet()){
            int score = entry.getValue();
            if (score > highScore || highScore == Integer.MIN_VALUE){
                first.clear();
                highScore = score;
            }
            if (score == highScore){
                first.add(entry.getKey());
            }
        }
        return first;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumPlayers(){
        return scores.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Player> getPlayerSet(){
        return scores.keySet();
    }
}
