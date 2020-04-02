package edu.sdonohue.advancedjava.view;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the MenuCommand class.
 *
 * @author Sean Donohue
 */
public class MenuCommandTest {

    private MenuCommand menuCommand;

    /**
     * Setup to run before each test.
     */
    @Before
    public void setup(){
        menuCommand = new MenuCommand("Test", new Runnable(){
            @Override
            public void run() {
                //do nothing
            }
        });
    }

    /**
     * Test that the constructor creates the object instance.
     */
    @Test
    public void testConstructor(){
        assertNotNull("MenuCommand should not be null", menuCommand);
    }

    /**
     * Test that the menu text is returned correctly.
     */
    @Test
    public void testGetMenuText(){
        assertEquals("MenuText should be 'Test'", "Test", menuCommand.getMenuText());
    }

    /**
     * Test that the menu command is returned correctly.
     */
    @Test
    public void testGetMenuCommand(){
        assertNotNull("MenuCommand should not be null", menuCommand.getCommand());
    }
}
