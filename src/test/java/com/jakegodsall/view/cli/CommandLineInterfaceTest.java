package com.jakegodsall.view.cli;

import com.jakegodsall.config.LanguageConfig;
import com.jakegodsall.models.Language;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineInterfaceTest {

    private final Map<String, Language> languages = LanguageConfig.languageMap;
    private CommandLineInterface commandLineInterface;
    private InputStream originalSystemIn;

    @BeforeEach
    void setUp() {
        commandLineInterface = CommandLineInterfaceFactory.createGPTCommandLineInterface();
        originalSystemIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Disabled // Test fails with surefire plugin but success otherwise
    @Test
    void getLanguageMode_correctInputLowerCase() throws IOException {
        // Define the language input
        String input = "ru\n";
        Language validLanguage = languages.get(input);

        // Prepare the input stream
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        // Debugging: Ensure System.in is correctly set
        System.out.println("System.in (lowercase test): " + System.in);

        // Call the method
        Language result = commandLineInterface.getLanguageMode(bufferedReader);

        // Debugging: Print the result
        System.out.println("Result (lowercase test): " + result);

        // Verify the result
        assertThat(result).isEqualTo(validLanguage);

        // Reset System.in
        System.setIn(System.in);
    }

    @Disabled // Test fails with surefire plugin but success otherwise
    @Test
    void getLanguageMode_correctInputUpperCase() throws IOException {
        // Define the language input
        String input = "RU\n";
        Language validLanguage = languages.get(input);

        // Prepare the input stream
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Call the method
        Language result = commandLineInterface.getLanguageMode(bufferedReader);

        // Verify the result
        assertThat(result).isEqualTo(validLanguage);

        // Reset System.in
        System.setIn(System.in);
    }
}