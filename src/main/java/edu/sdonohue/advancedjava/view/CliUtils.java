package edu.sdonohue.advancedjava.view;

import java.io.FilterInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CliUtils {

    private static Scanner getScanner(){
        return new Scanner(new FilterInputStream(System.in){
            @Override
            public void close() throws IOException {
                //do nothing
            }
        });
    }

    public static Object promptForSelection(String promptText, List list){
        int num = 1;
        for (Object item : list){
            StringBuilder menuLine = new StringBuilder();
            menuLine.append(num).append(") ");
            menuLine.append(item.toString());
            output(menuLine.toString());
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

    public static String promptForText(String promptText){
        return prompt(promptText);
    }

    public static int promptForInt(String promptText){
        String input = prompt(promptText);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            return -1;
        }
    }

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

    private static String prompt(String promptText){
        output(promptText, TextAttribute.RED_BOLD_BRIGHT);
        String input;
        try (Scanner scanner = getScanner()) {
            input = scanner.next();
        }

        return input;
    }

    public static void result(String resultText){
        output(resultText, TextAttribute.BLUE_BOLD_BRIGHT);
    }

    public static void error(String errorText){
        output(errorText, TextAttribute.BLUE_BOLD_BRIGHT);
    }

    public static void output(String line){
        output(line, TextAttribute.BLACK, BackgroundColor.WHITE);
    }

    public static void output(String line, TextAttribute textAttribute){
        output(line, textAttribute, BackgroundColor.WHITE);
    }

    public static void output(String line, TextAttribute textAttribute, BackgroundColor backgroundColor){
        final String RESET = "\u001B[0m";
        StringBuilder output = new StringBuilder();
        output.append(BackgroundColor.WHITE.equals(backgroundColor)?"":backgroundColor)
                .append(TextAttribute.BLACK.equals(textAttribute)?"": textAttribute)
                .append(line)
                .append(RESET);
        System.out.println(output.toString());
    }

    public enum TextAttribute {
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

        TextAttribute(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        @Override
        public String toString(){
            return ansiCode;
        }
    }

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

        @Override
        public String toString(){
            return ansiCode;
        }
    }
}
