package com.runasetups.hylocale.language;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class SupportedLanguages {

    private static final Map<String, String> BY_INPUT = Map.of(
            "es", "es-ES",
            "es-es", "es-ES",
            "en", "en-US",
            "en-us", "en-US"
    );

    private SupportedLanguages() {
    }

    public static Optional<String> fromUserInput(String input) {
        if (input == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(BY_INPUT.get(input.trim().toLowerCase(Locale.ROOT)));
    }
}
