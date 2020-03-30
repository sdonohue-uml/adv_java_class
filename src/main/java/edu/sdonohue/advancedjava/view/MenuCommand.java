package edu.sdonohue.advancedjava.view;


import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.validation.constraints.NotNull;

@Immutable
public class MenuCommand {
    private String menuText;
    private Runnable command;

    public MenuCommand(@NotNull String menuText, @NotNull Runnable command){
        this.menuText = menuText;
        this.command = command;
    }

    public String getMenuText() {
        return menuText;
    }

    public Runnable getCommand() {
        return command;
    }
}
