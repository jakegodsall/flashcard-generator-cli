package org.jakegodsall.view.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.Gender;
import org.jakegodsall.models.enums.Tense;
import org.jakegodsall.utils.TenseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LanguageOptionsHandler {
    private final Language selectedLanguage;
    private BufferedReader bufferedReader;

    public Options getOptions() throws IOException {
        Options options = new Options();
        if (selectedLanguage.isSupportsStress())
            options.setIsStress(getStress(bufferedReader));
        options.setTense(getTense(bufferedReader));
        options.setGender(getGender(bufferedReader));

        System.out.println(options);
        return options;
    }

    public boolean getStress(BufferedReader bufferedReader) throws IOException {
        boolean isStress = false;

        System.out.println("Do you want stress marks on the words?");
        System.out.println("[y] - Yes");
        System.out.println("[n] - No");

        String input = "";
        while (true) {
            input = bufferedReader.readLine();
            if (input != null) {
                if (input.equalsIgnoreCase("y")) {
                    isStress = true;
                    break;
                }
                if (input.equalsIgnoreCase("n")) {
                    break;
                }
            }
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return isStress;
    }

    public Tense getTense(BufferedReader bufferedReader) throws IOException {
        Map<String, Tense> tenseMap = TenseUtils.getTenseMap(selectedLanguage.getTenses());

        System.out.println("Which tense do you want to use?");
        TenseUtils.printTenses(selectedLanguage.getTenses());

        System.out.println(tenseMap);
        Tense selectedTense = Tense.PRESENT;

        String input;
        while (true) {
            input = bufferedReader.readLine().trim().toLowerCase();
            if (tenseMap.containsKey(input)) {
                selectedTense = tenseMap.get(input);
                break;
            } else {
                System.out.println("Invalid input. Please enter one of the following");
                TenseUtils.printTenses(selectedLanguage.getTenses());
            }
        }
        return selectedTense;
    }

    public Gender getGender(BufferedReader bufferedReader) throws IOException {
        System.out.println("Which gender do you want to use?");
        for (Gender gender : selectedLanguage.getGenders()) {
            System.out.println("[" + gender + "]");
        }

        Gender selectedGender = Gender.MASCULINE;
        String input;

        while(true) {
            input = bufferedReader.readLine().trim().toLowerCase();
            switch(input) {
                case "masculine":
                    break;
                case "feminine":
                    selectedGender = Gender.FEMININE;
                    break;
                case "neuter":
                    selectedGender = Gender.NEUTER;
                    break;
                default:
                    System.out.println("Invalid input");
                    continue;
            }
            break;
        }
        return selectedGender;
    }

}
