package edu.sdonohue.advancedjava.view;

import java.io.FilterInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Class for standardizing output to and input from the console for use in
 * a command line interface application.
 *
 * @author Sean Donohue
 */
public class CliUtils {

    /**
     * Outputs prompt text to the console and waits for input from the user that it
     * then returns as a String.
     *
     * @param promptText The prompt text
     * @return The user's response as a String
     */
    public static String promptForText(String promptText){
        return prompt(promptText);
    }

    /**
     * Outputs prompt text to the console and waits for input from the user that it
     * parses and then returns as an int.
     *
     * @param promptText The prompt text
     * @return The user's response as an int
     */
    public static int promptForInt(String promptText){
        String input = prompt(promptText);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            return -1;
        }
    }

    /**
     * Outputs prompt text to the console and waits for input from the user that it
     * parses and then returns as an instance of LocalDateTime. User input must be in
     * the format MM/dd/uuuu (e.g. 01/30/2020).
     *
     * @param promptText The prompt text
     * @return The user's response as a LocalDateTime
     */
    public static LocalDateTime promptForDate(String promptText){
        String input = prompt(promptText);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm:ss");
        try {
            String dateTimeString = input + " 00:00:00";
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e){
            return null;
        }
    }

    /**
     * Outputs a numbered list of options to the console and waits for the user to enter
     * the corresponding number. The Object selected from the list is returned.
     *
     * @param promptText The text instructions to the user
     * @param list The list of Objects to select from
     * @return The selected Object
     */
    public static Object promptForSelection(String promptText, List list){
        int num = 1;
        for (Object item : list){
            StringBuilder lineItem = new StringBuilder();
            lineItem.append(num).append(") ");
            lineItem.append(item.toString());
            output(lineItem.toString());
            num++;
        }

        int selected = promptForInt(promptText + ": ");
        int index = selected - 1;
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        } else {
            return null;
        }
    }

    // Outputs text to the console in a standard format and returns the user's response
    private static String prompt(String promptText){
        output(promptText, TextStyle.RED_BOLD_BRIGHT);
        String input;
        try (Scanner scanner = getScanner()) {
            input = scanner.next();
        }

        return input;
    }

    // Returns a Scanner that reads from System.in that doesn't close System.in when it is closed.
    private static Scanner getScanner(){
        return new Scanner(new FilterInputStream(System.in){
            @Override
            public void close() throws IOException {
                //do nothing
            }
        });
    }

    /**
     * Outputs a line of text to the console using blue text on a white background.
     * Designed to be used as a convenience method for outputting the results of operations
     * in the UI in a standard format.
     * Output is prepended with ansi code to set the color and style of the text, and the color
     * of the background, and appended with the ASCII reset code to return to default values.
     *
     * @param resultText The text to output
     */
    public static void result(String resultText){
        output(resultText, TextStyle.BLUE_BOLD_BRIGHT);
    }

    /**
     * Outputs a line of text to the console using blue text on a white background.
     * Designed to be used as a convenience method for outputting errors in a standard format.
     * Output is prepended with ansi code to set the color and style of the text, and the color
     * of the background, and appended with the ASCII reset code to return to default values.
     *
     * @param errorText The text to output
     */
    public static void error(String errorText){
        output(errorText, TextStyle.BLUE_BOLD_BRIGHT);
    }

    /**
     * Outputs a line of text to the console using black text on a white background.
     * Output is prepended with ansi code to set the color and style of the text, and the color
     * of the background, and appended with the ASCII reset code to return to default values.
     *
     * @param line The text to output
     */
    public static void output(String line){
        output(line, TextStyle.BLACK, BackgroundColor.WHITE);
    }

    /**
     * Outputs a line of text to the console using the selected TextStyle on a white background.
     * Output is prepended with ansi code to set the color and style of the text, and the color
     * of the background, and appended with the ASCII reset code to return to default values.
     *
     * @param line The text to output
     * @param textStyle The color and style of the text
     */
    public static void output(String line, TextStyle textStyle){
        output(line, textStyle, BackgroundColor.WHITE);
    }

    /**
     * Outputs a line of text to the console using the selected TextStyle on the selected BackgroundColor.
     * Output is prepended with ansi code to set the color and style of the text, and the color
     * of the background, and appended with the ASCII reset code to return to default values.
     *
     * @param line The text to output
     * @param textStyle The color and style of the text
     * @param backgroundColor The color of the background.
     */
    public static void output(String line, TextStyle textStyle, BackgroundColor backgroundColor){
        // The ansi code that resets the console output to default text styles and background
        final String RESET = "\u001B[0m";

        StringBuilder output = new StringBuilder();
        output.append(BackgroundColor.WHITE.equals(backgroundColor)?"":backgroundColor)
                .append(TextStyle.BLACK.equals(textStyle)?"": textStyle)
                .append(line)
                .append(RESET);
        System.out.println(output.toString());
    }

    /**
     * An enum of available text styles to apply to console output.
     */
    public enum TextStyle {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[34m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m"),

        BLACK_BOLD("\033[1;30m"),
        RED_BOLD("\033[1;31m"),
        GREEN_BOLD("\033[1;32m"),
        YELLOW_BOLD("\033[1;33m"),
        BLUE_BOLD("\033[1;34m"),
        PURPLE_BOLD("\033[1;35m"),
        CYAN_BOLD("\033[1;36m"),
        WHITE_BOLD("\033[1;37m"),

        BLACK_UNDERLINED("\033[4;30m"),
        RED_UNDERLINED("\033[4;31m"),
        GREEN_UNDERLINED("\033[4;32m"),
        YELLOW_UNDERLINED("\033[4;33m"),
        BLUE_UNDERLINED("\033[4;34m"),
        PURPLE_UNDERLINED("\033[4;35m"),
        CYAN_UNDERLINED("\033[4;36m"),
        WHITE_UNDERLINED("\033[4;37m"),

        BLACK_BOLD_BRIGHT ("\033[1;90m"),
        RED_BOLD_BRIGHT("\033[1;91m"),
        GREEN_BOLD_BRIGHT("\033[1;92m"),
        YELLOW_BOLD_BRIGHT("\033[1;93m"),
        BLUE_BOLD_BRIGHT("\033[1;94m"),
        PURPLE_BOLD_BRIGHT("\033[1;95m"),
        CYAN_BOLD_BRIGHT("\033[1;96m"),
        WHITE_BOLD_BRIGHT("\033[1;97m"),
        ;

        private String ansiCode;

        TextStyle(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        /**
         * The ansi code that applies the text style to text that comes after it.
         *
         * @return The ansi code.
         */
        @Override
        public String toString(){
            return ansiCode;
        }
    }

    /**
     * An enum of available background colors to apply to console output.
     */
    public enum BackgroundColor{
        BLACK("\u001B[40m"),
        RED("\u001B[41m"),
        GREEN("\u001B[42m"),
        YELLOW("\u001B[43m"),
        BLUE("\u001B[44m"),
        PURPLE("\u001B[45m"),
        CYAN("\u001B[46m"),
        WHITE("\u001B[47m"),

        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),
        RED_BACKGROUND_BRIGHT("\033[0;101m"),
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),
        PURPLE_BACKGROUND_BRIGHT("\033[0;105m"),
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),
        WHITE_BACKGROUND_BRIGHT("\033[0;107m"),
        ;

        private String ansiCode;

        BackgroundColor(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        /**
         * The ansi code that applies the background color to text that comes after it.
         *
         * @return The ansi code.
         */
        @Override
        public String toString(){
            return ansiCode;
        }
    }
}
