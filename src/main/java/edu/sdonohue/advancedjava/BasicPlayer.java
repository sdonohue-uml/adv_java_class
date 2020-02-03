package edu.sdonohue.advancedjava;

/**
 * Basic implementation of the Player interface
 *
 *  @author Sean Donohue
 *  @version 1.0
 */
public class BasicPlayer implements Player{
    //The name of the player
    private String name;

    /**
     * Constructs a new BasicPlayer with a name.
     *
     * @param name The name of the player
     */
    public BasicPlayer(String name){
        this.name = name;
    }

    /**
     * Get the name of the Player.
     *
     * @return Player name
     */
    @Override
    public String getName() {
        return name;
    }
}
