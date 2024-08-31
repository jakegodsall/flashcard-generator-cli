package org.jakegodsall;

import org.jakegodsall.view.cli.CommandLineInterface;
import org.jakegodsall.view.cli.CommandLineInterfaceFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CommandLineInterface cli = CommandLineInterfaceFactory.createGPTCommandLineInterface();
        cli.main();
    }
}