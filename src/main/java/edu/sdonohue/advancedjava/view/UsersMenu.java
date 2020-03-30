package edu.sdonohue.advancedjava.view;

public class UsersMenu extends AbstractMenu {

    public UsersMenu(Menu parent){
        this.parentMenu = parent;
        initCommands();
        initHeader();
    }

    @Override
    public void initHeader() {
        header = "Manage Users Menu";
    }

    @Override
    public void initCommands() {
        commands.put(1, new MenuCommand("Add a User", new Runnable() {
            @Override
            public void run() {
                UsersMenu.this.addUser();
            }
        }));
        commands.put(2, new MenuCommand("View Users List", new Runnable() {
            @Override
            public void run() {
                UsersMenu.this.listUsers();
            }
        }));
        commands.put(3, new MenuCommand("Return to Main Menu", new Runnable() {
            @Override
            public void run() {
                UsersMenu.this.returnToParent();
            }
        }));
    }

    public void listUsers(){
        System.out.println("Users List Selected");
        display();
    }

    public void addUser(){
        System.out.println("Add User Selected");
        display();
    }
}
