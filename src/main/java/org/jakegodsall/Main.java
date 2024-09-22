package org.jakegodsall;

import org.apache.commons.cli.*;
import org.jakegodsall.view.cli.CommandLineInterface;
import org.jakegodsall.view.cli.CommandLineInterfaceFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("i", "input", true, "Choose the type of input to be provided");
        options.addOption("m", "mode", true, "Choose between sequential or concurrent flashcard generation");
        options.addOption("o", "output", true, "Choose the file format for the output");
        System.out.println("test");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("m")) {
            String mode = cmd.getOptionValue("m");
            System.out.println("Mode: " + mode);
        }

        CommandLineInterface cli = CommandLineInterfaceFactory.createGPTCommandLineInterface();
        cli.main();
    }
}