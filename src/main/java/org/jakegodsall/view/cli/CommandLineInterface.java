package org.jakegodsall.view.cli;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.enums.InputMode;
import org.jakegodsall.models.enums.OutputMode;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.InputService;
import org.jakegodsall.services.OutputService;
import org.jakegodsall.services.impl.*;
import org.jakegodsall.utils.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CommandLineInterface {
    private final FlashcardService flashcardService;

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    private final ApiKeyHandler apiKeyHandler;
    private InputService inputService;
    private OutputService outputService;

    public void main() {
        try (
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            // API Key Handling
            apiKeyHandler.handle(consoleReader);

            // Options handling
            Language chosenLanguage = getLanguageFromUser(consoleReader);
            // LanguageOptionsHandler loh = new LanguageOptionsHandler(chosenLanguage, bufferedReader);
            // Options selectedOptions = loh.getOptions();
            Options selectedOptions = Options.builder().build();

            // Get flashcard type
            FlashcardType flashcardType = getFlashcardType(consoleReader);

            // Get input mode
            InputMode inputMode = getInputMode(consoleReader);

            switch (inputMode) {
                case InputMode.INTERACTIVE -> inputService = new InputServiceInteractiveMode(consoleReader, flashcardService);
                case InputMode.COMMA_SEPARATED_STRING -> inputService = new InputServiceCommaSeparatedStringMode(consoleReader, flashcardService);
                case InputMode.PLAIN_TEXT_FILE -> inputService = new InputServicePlainTextFileMode(consoleReader, flashcardService);
            }

            // Process the input and create flashcards
            List<Flashcard> flashcards = inputService.getInput(flashcardType, chosenLanguage, selectedOptions);

            // Get output mode
            OutputMode outputMode = getOutputMode(consoleReader);
            String fileExtension = "";

            switch (outputMode) {
                case OutputMode.CSV:
                    outputService = new OutputServiceCsvMode();
                    fileExtension = ".csv";
                    break;
                case OutputMode.JSON:
                    outputService = new OutputServiceJsonMode();
                    fileExtension = ".json";
                    break;
            }


            String fileName = FilenameUtils.generateFilename(chosenLanguage, fileExtension);
            outputService.writeToFile(flashcards, fileName);

        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void printLanguageOptions() {
        System.out.println("Languages:");
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            System.out.println("[" + entry.getKey() + "] - " + entry.getValue());
        }
    }

    public Language getLanguageFromUser(BufferedReader bufferedReader) throws IOException {
        printLanguageOptions();
        boolean validInput = false;
        String input = "";
        while (!validInput) {
            System.out.println("Choose the desired language from the following list (use codes):");
            input = bufferedReader.readLine();
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            if (languages.containsKey(input.toLowerCase().trim())) {
                validInput = true;
            }
        }
        Language chosenLanguage = LanguageConfig.getLanguage(input);
        System.out.println("Chosen language: " + chosenLanguage);
        return chosenLanguage;
    }

    public InputMode getInputMode(BufferedReader bufferedReader) throws IOException {
        System.out.println("Choose an input mode:");
        System.out.println("[1] Interactive Mode");
        System.out.println("[2] Comma-separated String Mode");
        System.out.println("[3] Plain Text File Mode");

        boolean validInput = false;
        String input = "";
        InputMode result = InputMode.INTERACTIVE;  // Default to Interactive Mode

        while (!validInput) {
            input = bufferedReader.readLine();
            if (input == null) {
                throw new IllegalArgumentException("Input cannot be null");
            }
            switch (input) {
                case "1":
                    result = InputMode.INTERACTIVE;
                    validInput = true;
                    break;
                case "2":
                    result = InputMode.COMMA_SEPARATED_STRING;
                    validInput = true;
                    break;
                case "3":
                    result = InputMode.PLAIN_TEXT_FILE;
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid option:");
                    System.out.println("[1] Interactive Mode");
                    System.out.println("[2] Comma-separated String Mode");
                    System.out.println("[3] Plain Text File Mode");
                    break;
            }
        }
        return result;
    }

    public OutputMode getOutputMode(BufferedReader bufferedReader) throws IOException {
        System.out.println("Choose an output mode:");
        System.out.println("[1] CSV");
        System.out.println("[2] JSON");

        boolean validInput = false;
        String input = "";
        OutputMode result = OutputMode.CSV;

        while (!validInput) {
            input = bufferedReader.readLine().trim();
            switch (input) {
                case "1":
                    result = OutputMode.CSV;
                    validInput = true;
                    break;
                case "2":
                    result = OutputMode.JSON;
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid option:");
                    System.out.println("[1] CSV");
                    System.out.println("[2] JSON");
                    break;
            }
        }
        return result;
    }

    public FlashcardType getFlashcardType(BufferedReader bufferedReader) throws IOException {
        System.out.println("Choose a flashcard type:");
        System.out.println("[1] Word Flashcard");
        System.out.println("[2] Sentence Flashcard");
        boolean validInput = false;
        String input = "";
        FlashcardType result = FlashcardType.WORD;
        while (!validInput) {
            input = bufferedReader.readLine();
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            if (input.equals("1")) {
                validInput = true;
            }
            if (input.equals("2")) {
                result = FlashcardType.SENTENCE;
                validInput = true;
            }
        }
        return result;
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word:");
        return bufferedReader.readLine();
    }
}
