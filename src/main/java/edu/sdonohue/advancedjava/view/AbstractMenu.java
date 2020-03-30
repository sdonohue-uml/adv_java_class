package edu.sdonohue.advancedjava.view;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class AbstractMenu implements Menu {
    protected String header;
    protected Menu parentMenu;
    protected Map<Integer, MenuCommand> commands = new LinkedHashMap<>();

    public abstract void initCommands();

    public abstract void initHeader();

    public void display(){
        // Print the header
        System.out.println("********************************************");
        System.out.println(header);
        System.out.println("********************************************");

        // Print the menu
        for (Map.Entry<Integer, MenuCommand> entry : commands.entrySet()){
            StringBuilder menuLine = new StringBuilder();
            menuLine.append(entry.getKey()).append(": ");
            menuLine.append(entry.getValue().getMenuText());
            System.out.println(menuLine.toString());
        }

        // Get the users selection
        System.out.println("Enter the number of your selection: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int selected = scanner.nextInt();
            // Run the selected command
            if (commands.containsKey(selected)){
                commands.get(selected).getCommand().run();
            } else {
                // error
            }
        }
    }

    public void returnToParent(){
        if (parentMenu != null){
            parentMenu.display();
        } else {
            System.out.println("Are you sure you want to quit? (Y/N)");
            try (Scanner scanner = new Scanner(System.in)) {
                String selected = scanner.next();
                if (selected.toLowerCase().startsWith("y")){
                    System.exit(0);
                } else {
                    display();
                }
            }
        }
    }
}
