package edu.sdonohue.advancedjava.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the MainMenu class.
 *
 * @author Sean Donohue
 */
public class MainMenuTest {
    private MainMenu mainMenu;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    /**
     * Setup steps to run before each test.
     */
    @Before
    public void setup(){
        mainMenu = new MainMenu();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Tear down steps to run after each test.
     */
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Test the menu command sequence to Quit the application.
     */
    @Test
    public void testMenuQuit(){
        exit.expectSystemExitWithStatus(0);
        systemInMock.provideLines("3", "Y");
        mainMenu.display();
    }
}
