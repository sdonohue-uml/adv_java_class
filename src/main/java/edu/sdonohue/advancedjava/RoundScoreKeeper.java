package edu.sdonohue.advancedjava;

import java.util.Map;

public class RoundScoreKeeper extends AbstractScoreKeeper{

    /**
     * Constructs a new RoundScoreKeeper which can be used to record
     * the amount scored for every player in any given round. Can be used
     * as a history of the round for a scorepad, so modification after
     * creation is disabled.
     *
     * @param scoresForTheRound A map of players and each amount
     *                          scored in this round.
     */
    public RoundScoreKeeper(Map<Player, Integer> scoresForTheRound){
        scores = scoresForTheRound;
    }

    /**
     * Override of the modifyPlayerScore method to disable modification
     *
     * @param player
     * @param amount
     */
    @Override
    public void addToPlayerScore(Player player, int amount) {
        throw new UnsupportedOperationException("Round scores cannot be modified after creation");
    }
}
