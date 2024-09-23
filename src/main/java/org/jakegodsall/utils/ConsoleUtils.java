package org.jakegodsall.utils;

import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Map;

public class ConsoleUtils {

    public static void printLanguageOptions(Map<String, String> languages) {
        System.out.println("Languages:");
        ConsoleUtils.printListOfValues(languages);
    }

    public static void printInputModes() {
        List<String> inputModes = List.of("Interactive Mode", "Comma-separated String Mode", "Plain Text File Mode");
        System.out.println("Input Modes:");
        ConsoleUtils.printListOfValues(inputModes);
    }

    public static void printOutputModes() {
        List<String> outputModes = List.of("CSV", "JSON");
        System.out.println("Output Modes");
        ConsoleUtils.printListOfValues(outputModes);
    }

    public static void printFlashcardModes() {
        List<String> flashcardModes = List.of("Word Flashcard", "Sentence Flashcard");
        System.out.println("Flashcard Modes");
        ConsoleUtils.printListOfValues(flashcardModes);
    }

    public static void printListOfValues(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            printListItem(entry.getKey(), entry.getValue());
        }
    }

    public static void printListOfValues(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            printListItem(String.valueOf(i + 1), list.get(i));
        }
    }

    public static void printListItem(String key, String value) {
        System.out.println("[" + key + "] - " + value);
    }
}
