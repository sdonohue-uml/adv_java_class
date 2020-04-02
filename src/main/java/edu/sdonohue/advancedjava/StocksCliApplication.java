package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.view.MainMenu;

/**
 * A command line interface application for accessing stock data.
 *
 * @author Sean Donohue
 */
public class StocksCliApplication {

    /**
     * Main method for launching the application. No arguments are recognized.
     *
     * @param args none
     */
    public static void main(String[] args){
        MainMenu mainMenu = new MainMenu();
        mainMenu.display();
    }
}
