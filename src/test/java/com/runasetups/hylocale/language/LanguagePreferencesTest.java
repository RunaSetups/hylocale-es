package com.runasetups.hylocale.language;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguagePreferencesTest {

    private static final UUID PLAYER = UUID.fromString("9629f9f6-d136-41d6-85be-c44e501ec0b7");

    @TempDir
    Path dataDirectory;

    @Test
    void playerWithoutChoiceHasNoPreference() throws Exception {
        LanguagePreferences preferences = LanguagePreferences.load(dataDirectory.resolve("idiomas.properties"));

        assertEquals(Optional.empty(), preferences.get(PLAYER));
    }

    @Test
    void chosenLanguageIsRemembered() throws Exception {
        LanguagePreferences preferences = LanguagePreferences.load(dataDirectory.resolve("idiomas.properties"));

        preferences.set(PLAYER, "en-US");

        assertEquals(Optional.of("en-US"), preferences.get(PLAYER));
    }

    @Test
    void chosenLanguageSurvivesServerRestart() throws Exception {
        Path file = dataDirectory.resolve("idiomas.properties");
        LanguagePreferences.load(file).set(PLAYER, "en-US");

        LanguagePreferences afterRestart = LanguagePreferences.load(file);

        assertEquals(Optional.of("en-US"), afterRestart.get(PLAYER));
    }

    @Test
    void handEditedInvalidEntryDoesNotBreakLoading() throws Exception {
        Path file = dataDirectory.resolve("idiomas.properties");
        Files.writeString(file, "no-es-un-uuid=es-ES\n" + PLAYER + "=en-US\n");

        LanguagePreferences preferences = LanguagePreferences.load(file);

        assertEquals(Optional.of("en-US"), preferences.get(PLAYER));
    }
}
