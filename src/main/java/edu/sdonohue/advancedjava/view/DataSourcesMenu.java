package edu.sdonohue.advancedjava.view;

public class DataSourcesMenu extends AbstractMenu {

    public DataSourcesMenu(Menu parent){
        this.parentMenu = parent;
        initCommands();
        initHeader();
    }

    @Override
    public void initHeader() {
        header = "Data Sources Menu";
    }

    @Override
    public void initCommands() {
        commands.put(1, new MenuCommand("Change Data Source", new Runnable() {
            @Override
            public void run() {
                DataSourcesMenu.this.setDataSource();
            }
        }));
        commands.put(2, new MenuCommand("Upload XML Data", new Runnable() {
            @Override
            public void run() {
                DataSourcesMenu.this.uploadXml();
            }
        }));
        commands.put(3, new MenuCommand("Return to Main Menu", new Runnable() {
            @Override
            public void run() {
                DataSourcesMenu.this.returnToParent();
            }
        }));
    }

    protected void setDataSource(){
        System.out.println("Set Data Source Selected");
    }

    protected void uploadXml(){
        System.out.println("Upload XML Selected");
    }

}
