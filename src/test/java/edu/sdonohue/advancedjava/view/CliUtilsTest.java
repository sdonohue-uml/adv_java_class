package edu.sdonohue.advancedjava.view;

import edu.sdonohue.advancedjava.view.CliUtils.BackgroundColor;
import edu.sdonohue.advancedjava.view.CliUtils.TextStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static edu.sdonohue.advancedjava.view.CliUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the CliUtils class.
 *
 * @author Sean Donohue
 */
public class CliUtilsTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream orignalIn = System.in;

    /**
     * Setup steps to run before each test.
     */
    @Before
    public void setUpStreams() {
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
        System.setIn(orignalIn);
    }

    /**
     * Test that the output method returns the correct text.
     */
    @Test
    public void testOutput(){
        output("Test");
        assertTrue("Output should equal 'Test'", outContent.toString().contains("Test"));
    }

    /**
     * Test that the output method returns the correct text when a TextStyle is applied.
     */
    @Test
    public void testOutputColoredText(){
        output("Test", TextStyle.BLUE);
        assertTrue("Output should equal 'Test'", outContent.toString().contains("Test"));
    }

    /**
     * Test that the output method returns the correct text when a TextStyle and BackgroundColor are applied.
     */
    @Test
    public void testOutputColoredBackground(){
        output("Test", TextStyle.BLUE, BackgroundColor.CYAN);
        assertTrue("Output should equal 'Test'", outContent.toString().contains("Test"));
    }

    /**
     * Test that the result method returns the correct text.
     */
    @Test
    public void testResult(){
        result("Test");
        assertTrue("Output should equal 'Test'", outContent.toString().contains("Test"));
    }

    /**
     * Test that the error method returns the correct text.
     */
    @Test
    public void testError(){
        error("Test");
        assertTrue("Output should equal 'Test'", outContent.toString().contains("Test"));
    }

    /**
     * Test the promptForText method outputs the correct prompt text and reads the input correctly.
     */
    @Test
    public void testPromptForText(){
        ByteArrayInputStream newIn = new ByteArrayInputStream("Test".getBytes());
        System.setIn(newIn);
        String input = promptForText("Test Prompt");
        assertTrue("Output should equal 'Test Prompt'", outContent.toString().contains("Test Prompt"));
        assertEquals("Input should equal 'Test'", "Test", input);
    }

    /**
     * Test the promptForInt method outputs the correct prompt text and reads the input correctly.
     */
    @Test
    public void testPromptForInt(){
        ByteArrayInputStream newIn = new ByteArrayInputStream("100".getBytes());
        System.setIn(newIn);
        int input = promptForInt("Test Prompt");
        assertTrue("Output should equal 'Test Prompt'", outContent.toString().contains("Test Prompt"));
        assertEquals("Input should equal 100", 100, input);
    }

    /**
     * Test the promptForDate method outputs the correct prompt text and reads the input correctly.
     */
    @Test
    public void testPromptForDate(){
        ByteArrayInputStream newIn = new ByteArrayInputStream("01/30/2020".getBytes());
        System.setIn(newIn);
        LocalDateTime input = promptForDate("Test Prompt");
        assertTrue("Output should equal 'Test Prompt'", outContent.toString().contains("Test Prompt"));
        assertEquals("Input should equal 01/30/2020",
                LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0, 0), input);
    }

    /**
     * Test the promptForSelection method outputs the correct prompt text and reads the input correctly.
     */
    @Test
    public void testPromptForSelection(){
        ByteArrayInputStream newIn = new ByteArrayInputStream("2".getBytes());
        System.setIn(newIn);
        List<Integer> list = Arrays.asList(100, 200, 300);
        Integer input = (Integer) promptForSelection("Test Prompt", list);
        assertTrue("Output should equal 'Test Prompt: '", outContent.toString().contains("Test Prompt: "));
        assertEquals("Input should equal 200", new Integer(200), input);
    }
}
