package edu.sdonohue.advancedjava;

import java.util.List;
import java.util.Set;

/**
 * Interface for a class that can be used to keep Player scores in a game.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public interface ScoreKeeper {

    /**
     * Used to modify the score of a single player that already exists
     * in the ScoreKeeper by a given amount. (i.e. currentScore += amount)
     *
     * @param player The Player whose score should be modified
     * @param amount A positive or negative integer to add to the player's current score
     */
    public void addToPlayerScore(Player player, int amount);

    /**
     * Gets the score for a Player.
     *
     * @param player The Player whose score will be returned
     * @return The Player's score
     */
    public int getPlayerScore(Player player);

    /**
     * Get a list containing the Player or Player(s) that have the highest score
     * value.
     *
     * @return A list of Player(s) with the highest score.
     */
    public List<Player> getFirstPlace();

    /**
     * Get the number of Players.
     *
     * @return The number of Players
     */
    public int getNumPlayers();

    /**
     * Get a set containing every Player
     *
     * @return The set of Players
     */
    public Set<Player> getPlayerSet();
}
