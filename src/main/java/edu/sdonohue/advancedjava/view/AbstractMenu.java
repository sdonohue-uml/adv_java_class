package edu.sdonohue.advancedjava.view;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.sdonohue.advancedjava.view.CliUtils.*;

/**
 * Abstract class for command line interface menus.
 *
 * @author Sean Donohue
 */
public abstract class AbstractMenu implements Menu {
    /**
     * Map of menu item numbers to MenuCommands. The MenuCommand will be
     * executed when the user enters the item number.
     */
    protected Map<Integer, MenuCommand> commands = new LinkedHashMap<>();

    /**
     * The parent Menu of this Menu. The parentMenu will be displayed when
     * this menu is exited.
     */
    protected Menu parentMenu;

    /**
     * The text header that will be displayed at the top of this menu.
     */
    protected String header;

    // Flag to tell if this menu is still active. Active menus are redisplayed
    // when executed menu commands are done.
    private boolean active = false;

    /**
     * Default constructor. Initializes the menu header and command list.
     */
    public AbstractMenu(){
        initCommands();
        initHeader();
    }

    /**
     * Abstract method for initializing the command list. Called by default
     * constructor.
     */
    protected abstract void initCommands();

    /**
     * Abstract method for initializing the menu header. Called by default
     * constructor.
     */
    protected abstract void initHeader();

    /**
     * Displays the Menu identified as the parent of this Menu, if there is one. If there is
     * no parent, the user is prompted to confirm that they want to exit, and the application
     * is closed.
     */
    protected void returnToParent(){
        if (parentMenu != null){
            active = false;
            parentMenu.display();
        } else {
            String selected = promptForText("Are you sure you want to quit? (Y/N)");
            if (selected.toLowerCase().startsWith("y")){
                System.exit(0);
            }
        }
    }

    /**
     * Displays the menu header and items and prompts for user selection. Redisplays the same
     * menu after the selected action is completed as long as the menu is still active.
     */
    public void display(){
        active = true;
        outputHeader();
        outputMenuItems();
        doSelection();
        if (active){
            display();
        }
    }

    // Output the menu header
    private void outputHeader() {
        // Print the header
        output("********************************************");
        output(header, TextStyle.BLACK_BOLD);
        output("********************************************");
    }

    // Output the menu items
    private void outputMenuItems() {
        // Print the menu
        for (Map.Entry<Integer, MenuCommand> entry : commands.entrySet()){
            StringBuilder menuLine = new StringBuilder();
            menuLine.append(entry.getKey()).append(": ");
            menuLine.append(entry.getValue().getMenuText());
            output(menuLine.toString());
        }
    }

    // Wait for user input, map the input to the selected menu item and then run the command
    private void doSelection() {
        // Get the users selection and run the matching command
        int selected = promptForInt("Enter the number of your selection: ");
        if (commands.containsKey(selected)){
            commands.get(selected).getCommand().run();
        } else {
            error("Error reading the selected menu item. Please try again.");
        }
    }
}
