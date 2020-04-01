package edu.sdonohue.advancedjava.view;

/**
 * The Main Menu of the StocksCliApplication.
 *
 * @author Sean Donohue
 */
public class MainMenu extends AbstractMenu {

    /**
     * @inheritDoc
     */
    @Override
    protected void initHeader() {
        header = "Main Menu";
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initCommands() {
        commands.put(1, new MenuCommand("Get Quotes", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.getQuotesMenu();
            }
        }));
        commands.put(2, new MenuCommand("Add/View Users", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.manageUsersMenu();
            }
        }));
        commands.put(3, new MenuCommand("Quit", new Runnable() {
            @Override
            public void run() {
                MainMenu.this.returnToParent();
            }
        }));
    }

    //Menu command to open the Quotes menu
    private void getQuotesMenu(){
        Menu quotesMenu = new QuotesMenu(this);
        quotesMenu.display();
    }

    //Menu command to open the Users menu
    private void manageUsersMenu(){
        Menu usersMenu = new UsersMenu(this);
        usersMenu.display();
    }
}
