package org.jakegodsall.view.cli;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.enums.Gender;
import org.jakegodsall.models.enums.Tense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageOptionsHandlerTest {

    private LanguageOptionsHandler languageOptionsHandler;

    @BeforeEach
    void setUp() {
        languageOptionsHandler = new LanguageOptionsHandler(new Language(
                "Spanish", false, List.of(Tense.PAST, Tense.PRESENT, Tense.FUTURE),
                List.of(Gender.MASCULINE, Gender.FEMININE)
        ));
    }

    @Test
    void getStress_correctInputLowerCase() throws IOException {
        String input = "y";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getStress(languageOptionsHandler.getBufferedReader()))
                .isTrue();
    }

    @Test
    void getStress_correctInputUpperCase() throws IOException {
        String input = "Y";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getStress(languageOptionsHandler.getBufferedReader()))
                .isTrue();
    }

    @Test
    void getTense_correctInputLowerCase() throws IOException {
        String input = "past";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getTense(languageOptionsHandler.getBufferedReader()))
                .isEqualTo(Tense.PAST);
    }

    @Test
    void getTense_correctInputUpperCase() throws IOException {
        String input = "FUTURE";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getTense(languageOptionsHandler.getBufferedReader()))
                .isEqualTo(Tense.FUTURE);
    }

    @Test
    void getGender_correctInputLowerCase() throws IOException {
        String input = "feminine";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getGender(languageOptionsHandler.getBufferedReader()))
                .isEqualTo(Gender.FEMININE);
    }

    @Test
    void getGender_correctInputUpperCase() throws IOException {
        String input = "FEMININE";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        languageOptionsHandler.setBufferedReader(new BufferedReader(new InputStreamReader(in)));

        assertThat(languageOptionsHandler.getGender(languageOptionsHandler.getBufferedReader()))
                .isEqualTo(Gender.FEMININE);
    }
}