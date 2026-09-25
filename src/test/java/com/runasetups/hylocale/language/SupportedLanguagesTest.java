package com.runasetups.hylocale.language;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupportedLanguagesTest {

    @Test
    void shortSpanishCodeMapsToSpain() {
        assertEquals(Optional.of("es-ES"), SupportedLanguages.fromUserInput("es"));
    }

    @Test
    void shortEnglishCodeMapsToUnitedStates() {
        assertEquals(Optional.of("en-US"), SupportedLanguages.fromUserInput("en"));
    }

    @Test
    void inputIsCaseAndSpaceInsensitive() {
        assertEquals(Optional.of("es-ES"), SupportedLanguages.fromUserInput(" ES "));
    }

    @Test
    void fullLanguageCodeIsAccepted() {
        assertEquals(Optional.of("en-US"), SupportedLanguages.fromUserInput("EN-us"));
    }

    @Test
    void missingInputIsRejected() {
        assertEquals(Optional.empty(), SupportedLanguages.fromUserInput(null));
    }

    @Test
    void unsupportedLanguageIsRejected() {
        assertEquals(Optional.empty(), SupportedLanguages.fromUserInput("fr"));
    }
}
