package com.runasetups.hylocale.language;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageResolverTest {

    private final LanguageResolver resolver = new LanguageResolver("es-ES");

    @Test
    void englishClientWithoutPreferenceGetsServerDefault() {
        assertEquals("es-ES", resolver.resolve(null, "en-US"));
    }

    @Test
    void clientWithoutDeclaredLanguageGetsServerDefault() {
        assertEquals("es-ES", resolver.resolve(null, null));
    }

    @Test
    void nonEnglishClientWithoutPreferenceKeepsItsLanguage() {
        assertEquals("pt-BR", resolver.resolve(null, "pt-BR"));
    }

    @Test
    void storedPreferenceWinsOverServerDefault() {
        assertEquals("en-US", resolver.resolve("en-US", "en-US"));
    }

    @Test
    void storedPreferenceWinsOverClientLanguage() {
        assertEquals("es-ES", resolver.resolve("es-ES", "pt-BR"));
    }
}
