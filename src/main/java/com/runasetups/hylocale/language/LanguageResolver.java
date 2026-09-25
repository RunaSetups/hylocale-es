package com.runasetups.hylocale.language;

public class LanguageResolver {

    private static final String CLIENT_DEFAULT_LANGUAGE = "en-US";

    private final String defaultLanguage;

    public LanguageResolver(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    // El cliente no ofrece espanol: en-US no es una eleccion real del jugador, es el unico idioma que tiene.
    public String resolve(String preferredLanguage, String clientLanguage) {
        if (preferredLanguage != null) {
            return preferredLanguage;
        }
        if (clientLanguage == null || CLIENT_DEFAULT_LANGUAGE.equals(clientLanguage)) {
            return defaultLanguage;
        }
        return clientLanguage;
    }
}
