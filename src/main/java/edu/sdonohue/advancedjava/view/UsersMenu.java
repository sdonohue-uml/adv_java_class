package edu.sdonohue.advancedjava.view;

import edu.sdonohue.advancedjava.model.Person;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceException;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static edu.sdonohue.advancedjava.view.CliUtils.*;

/**
 * A menu that displays options related to users of the stock quote application.
 *
 * @author Sean Donohue
 */
public class UsersMenu extends AbstractMenu {

    /**
     * Constructor that creates a UsersMenu with a parent menu. The
     * parent menu is displayed when this menu is exited.
     *
     * @param parent The parent menu
     */
    public UsersMenu(Menu parent){
        super();
        this.parentMenu = parent;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initHeader() {
        header = "Manage Users Menu";
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initCommands() {
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

    // Menu command for displaying a list of users.
    private void listUsers(){
        try {
            List<Person> users = UserStockServiceFactory.getInstance().getPerson();
            if (users == null || users.size() == 0){
                result("No Users found");
                return;
            }
//            Person user = (Person) promptForSelection(
//                    "Enter the number of the User you'd like to view", users);
            for (Person user : users){
                result(user.toFriendlyString());
            }
        } catch (UserStockServiceException e) {
            error("Unable to retrieve the Users List");
        }
    }

    // Menu command for adding a new user.
    private void addUser(){
        String firstName = promptForText("Enter the new User's first name");
        if (firstName == null || firstName.length() == 0){
            error("Error reading first name");
            return;
        }

        String lastName = promptForText("Enter the new User's last name");
        if (lastName == null || lastName.length() == 0){
            error("Error reading last name");
            return;
        }

        LocalDateTime birthdate = promptForDate("Enter the User's birthdate");
        if (birthdate == null){
            error("Error reading birthdate");
            return;
        }

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(Timestamp.valueOf(birthdate));
        try {
            UserStockServiceFactory.getInstance().addOrUpdatePerson(person);
            result(person.toFriendlyString() + " added to database");
        } catch (UserStockServiceException e) {
            error("Error adding Person to database");
        }
    }
}
