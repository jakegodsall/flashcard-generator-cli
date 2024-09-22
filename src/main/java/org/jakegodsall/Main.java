package org.jakegodsall;

import org.apache.commons.cli.*;
import org.jakegodsall.view.cli.CommandLineInterface;
import org.jakegodsall.view.cli.CommandLineInterfaceFactory;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Option INPUT = new Option("i", "input", true, "Choose the type of input to be provided");
    private static final Option MODE = new Option("m", "mode", true, "Choose between sequential or concurrent flashcard generation");
    private static final Option OUTPUT = new Option("o", "output", true, "Choose the file format for the output");

    public static void main(String[] args) {
        CommandLineInterface cli = CommandLineInterfaceFactory.createGPTCommandLineInterface();

        // Define the command line arguments
        Options options = new Options();
        options.addOption(INPUT);
        options.addOption(MODE);
        options.addOption(OUTPUT);

        // Instantiate the command line parser
        CommandLineParser parser = new DefaultParser();

        try {
            // Parse the command line arguments
            CommandLine cmd = parser.parse(options, args);

            // check if no options were provided
            if (cmd.getOptions().length == 0) {
                System.out.println("No options provided. Entering interactive mode:");
                cli.run();
            }

            if (cmd.hasOption("m")) {
                String mode = cmd.getOptionValue("n");
                System.out.println("Mode: " + mode);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }
}