package edu.sdonohue.advancedjava.view;

public class MainMenu extends AbstractMenu {

    public MainMenu(){
        initCommands();
        initHeader();
    }

    @Override
    public void initHeader() {
        header = "Main Menu";
    }

    @Override
    public void initCommands() {
        commands.put(1, new MenuCommand("Get Quotes", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.getQuotesMenu();
            }
        }));
        commands.put(2, new MenuCommand("Manage Data Sources", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.manageDataSourcesMenu();
            }
        }));
        commands.put(3, new MenuCommand("Add/View Users", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.manageUsersMenu();
            }
        }));
        commands.put(4, new MenuCommand("Quit", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.returnToParent();
            }
        }));
    }

    protected void getQuotesMenu(){
        Menu quotesMenu = new QuotesMenu(this);
        quotesMenu.display();
    }

    protected void manageDataSourcesMenu(){
        Menu dataMenu = new DataSourcesMenu(this);
        dataMenu.display();
    }

    protected void manageUsersMenu(){
        Menu usersMenu = new UsersMenu(this);
        usersMenu.display();
    }
}
