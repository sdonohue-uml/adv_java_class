package edu.sdonohue.advancedjava.view;


import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.validation.constraints.NotNull;

/**
 * Representation of a menu command, including the menu text and the
 * Runnable command to be executed when the item is selected.
 *
 * @author Sean Donohue
 */
@Immutable
public class MenuCommand {
    private String menuText;
    private Runnable command;

    /**
     * Constructor to create a MenuCommand
     *
     * @param menuText The menu item text
     * @param command The menu item command
     */
    public MenuCommand(@NotNull String menuText, @NotNull Runnable command){
        this.menuText = menuText;
        this.command = command;
    }

    /**
     * The text that is displayed for this menu item.
     *
     * @return The menu text
     */
    public String getMenuText() {
        return menuText;
    }

    /**
     * The command that is run when this menu item is selected.
     *
     * @return The menu command
     */
    public Runnable getCommand() {
        return command;
    }
}
