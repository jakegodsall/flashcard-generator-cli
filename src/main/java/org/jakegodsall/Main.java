package org.jakegodsall;

import org.apache.commons.cli.*;
import org.jakegodsall.view.cli.CommandLineInterface;
import org.jakegodsall.view.cli.CommandLineInterfaceFactory;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // Options and default values

    private static final Option INPUT_OPTION = new Option("i", "input", true, "Choose the type of input to be provided");
    private static final String INPUT_DEFAULT_VALUE = "str";
    private static final List<String> INPUT_VALID_VALUES = Arrays.asList("str", "file");

    private static final Option MODE_OPTION = new Option("m", "mode", true, "Choose between sequential or concurrent flashcard generation");
    private static final String MODE_DEFAULT_VALUE = "seq";
    private static final List<String> MODE_VALID_VALUES = Arrays.asList("seq", "con");

    private static final Option OUTPUT_OPTION = new Option("o", "output", true, "Choose the file format for the output");
    private static final String OUTPUT_DEFAULT_VALUE = "csv";
    private static final List<String> OUTPUT_VALID_VALUES = Arrays.asList("csv", "json");

    public static void main(String[] args) {
        CommandLineInterface cli = CommandLineInterfaceFactory.createGPTCommandLineInterface();

        // Define the command line arguments
        Options options = new Options();
        options.addOption(INPUT_OPTION);
        options.addOption(MODE_OPTION);
        options.addOption(OUTPUT_OPTION);

        // Instantiate the command line parser
        CommandLineParser parser = new DefaultParser();

        try {
            // Parse the command line arguments
            CommandLine cmd = parser.parse(options, args);

            // Process the input
            String input = getValidaValueForOption(cmd, "i", INPUT_VALID_VALUES, INPUT_DEFAULT_VALUE);
            String mode = getValidaValueForOption(cmd, "m", MODE_VALID_VALUES, MODE_DEFAULT_VALUE);
            String output = getValidaValueForOption(cmd, "o", OUTPUT_VALID_VALUES, OUTPUT_DEFAULT_VALUE);

            System.out.println("Running with input " + input);
            System.out.println("Running with mode " + mode);
            System.out.println("Running with output " + output);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

    private static String getValidaValueForOption(CommandLine cmd, String option, List<String> validValues, String defaultValue) {
        String value = cmd.getOptionValue(option, defaultValue);
        if (!validValues.contains(value)) {
            System.out.println("Invalid '" + option + "' type provided. Defaulting to '" + defaultValue + "'");
            value = defaultValue;
        }
        return value;
    }
}