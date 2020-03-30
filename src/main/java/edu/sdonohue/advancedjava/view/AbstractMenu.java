package edu.sdonohue.advancedjava.view;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.sdonohue.advancedjava.view.CliUtils.*;

public abstract class AbstractMenu implements Menu {
    protected Map<Integer, MenuCommand> commands = new LinkedHashMap<>();
    protected Menu parentMenu;
    protected String header;
    private boolean active = false;

    public abstract void initCommands();

    public abstract void initHeader();

    public void display(){
        active = true;
        // Print the header
        output("********************************************");
        output(header, TextAttribute.BLACK_BOLD);
        output("********************************************");

        // Print the menu
        for (Map.Entry<Integer, MenuCommand> entry : commands.entrySet()){
            StringBuilder menuLine = new StringBuilder();
            menuLine.append(entry.getKey()).append(": ");
            menuLine.append(entry.getValue().getMenuText());
            output(menuLine.toString());
        }

        // Get the users selection
        int selected = promptForInt("Enter the number of your selection: ");
        if (commands.containsKey(selected)){
            commands.get(selected).getCommand().run();
        } else {
            // todo: error
        }

        if (active){
            display();
        }
    }

    public void returnToParent(){
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


}
