package com.jakegodsall.view.cli;

import lombok.RequiredArgsConstructor;
import com.jakegodsall.config.LanguageConfig;
import com.jakegodsall.models.Language;
import com.jakegodsall.models.Options;
import com.jakegodsall.models.enums.InputMode;
import com.jakegodsall.models.enums.OutputMode;
import com.jakegodsall.models.flashcards.Flashcard;
import com.jakegodsall.models.flashcards.components.FlashcardComponent;
import com.jakegodsall.models.flashcards.components.SourceLanguageWord;
import com.jakegodsall.models.flashcards.components.TargetLanguageWord;
import com.jakegodsall.models.flashcards.components.SourceLanguageSentence;
import com.jakegodsall.models.flashcards.components.TargetLanguageSentence;
import com.jakegodsall.services.flashcard.FlashcardService;
import com.jakegodsall.services.input.InputService;
import com.jakegodsall.services.output.OutputService;
import com.jakegodsall.services.input.impl.InputServiceCommaSeparatedStringMode;
import com.jakegodsall.services.input.impl.InputServiceInteractiveMode;
import com.jakegodsall.services.input.impl.InputServicePlainTextFileMode;
import com.jakegodsall.services.output.impl.OutputServiceCsvMode;
import com.jakegodsall.services.output.impl.OutputServiceJsonMode;
import com.jakegodsall.utils.ConsoleUtils;
import com.jakegodsall.utils.FilenameUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RequiredArgsConstructor
public class CommandLineInterface {
    private final FlashcardService flashcardService;

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    private final ApiKeyHandler apiKeyHandler;
    private InputService inputService;
    private OutputService outputService;

    public void run() {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            // API Key Handling
            apiKeyHandler.handle(consoleReader);

            // LANGUAGE CHOICE
            Language targetLanguage = getLanguageMode(consoleReader);
            Language sourceLanguage = Language.builder().name("English").supportsStress(false).build();
            Options selectedOptions = Options.builder().build();

            // INPUT MODE CHOICE
            InputMode inputMode = getInputMode(consoleReader);
            switch (inputMode) {
                case InputMode.INTERACTIVE -> inputService = new InputServiceInteractiveMode(consoleReader);
                case InputMode.COMMA_SEPARATED_STRING -> inputService = new InputServiceCommaSeparatedStringMode(consoleReader);
                case InputMode.PLAIN_TEXT_FILE -> inputService = new InputServicePlainTextFileMode(consoleReader);
            }
            List<String> words = inputService.getInput();

            // FLASHCARD COMPONENT CHOICE
            List<FlashcardComponent> components = getFlashcardComponents(consoleReader);

            // Get number of cards per word
            int cardsPerWord = getCardsPerWord(consoleReader);

            List<Flashcard> flashcards = new ArrayList<>();
            if (inputMode == InputMode.INTERACTIVE) {
                flashcards = flashcardService.generateFlashcardsInteractively(components, sourceLanguage, targetLanguage, selectedOptions);
            } else {
                for (String word : words) {
                    List<Flashcard> wordFlashcards = flashcardService.generateMultipleFlashcardsForWord(
                        word,
                        components,
                        sourceLanguage,
                        targetLanguage,
                        selectedOptions,
                        cardsPerWord
                    );
                    flashcards.addAll(wordFlashcards);
                }
            }

            // Get output mode
            OutputMode outputMode = getOutputMode(consoleReader);
            String fileExtension = "";

            switch (outputMode) {
                case OutputMode.CSV:
                    outputService = new OutputServiceCsvMode();
                    fileExtension = ".csv";
                    break;
                case OutputMode.JSON:
                    outputService = new OutputServiceJsonMode(new ObjectMapper());
                    fileExtension = ".json";
                    break;
            }


            String fileName = FilenameUtils.generateFilename(targetLanguage, fileExtension);
            outputService.writeToFile(flashcards, fileName);

        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void run(String input, String language, String flashcardType, String mode, String output, String data) {
        System.out.println("NON INTERACTIVE MODE RUNNING");
        System.out.println(data);
    }

    public Language getLanguageMode(BufferedReader bufferedReader) throws IOException {
        ConsoleUtils.printLanguageOptions(languages);
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
        ConsoleUtils.printInputModes();

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
        ConsoleUtils.printOutputModes();

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

    public List<FlashcardComponent> getFlashcardComponents(BufferedReader bufferedReader) throws IOException {
        ConsoleUtils.printFlashcardModes();

        boolean validInput = false;
        String input = "";
        List<FlashcardComponent> result = List.of(new TargetLanguageWord(), new SourceLanguageWord());
        while (!validInput) {
            input = bufferedReader.readLine();
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            if (input.equals("1")) {
                validInput = true;
            }
            if (input.equals("2")) {
                result = List.of(new TargetLanguageWord(), new SourceLanguageWord(), new TargetLanguageSentence(), new SourceLanguageSentence());
                validInput = true;
            }
        }
        return result;
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word:");
        return bufferedReader.readLine();
    }

    private int getCardsPerWord(BufferedReader bufferedReader) throws IOException {
        System.out.println("How many flashcards would you like to generate per word? (1-5):");
        int cardsPerWord = 1;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                String input = bufferedReader.readLine().trim();
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= 5) {
                    cardsPerWord = number;
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between 1 and 5:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number between 1 and 5:");
            }
        }
        return cardsPerWord;
    }
}
