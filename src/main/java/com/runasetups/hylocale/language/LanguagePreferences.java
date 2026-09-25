package com.runasetups.hylocale.language;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LanguagePreferences {

    private final Path file;
    private final Map<UUID, String> byPlayer = new ConcurrentHashMap<>();

    private LanguagePreferences(Path file) {
        this.file = file;
    }

    public static LanguagePreferences load(Path file) throws IOException {
        LanguagePreferences preferences = new LanguagePreferences(file);
        if (Files.exists(file)) {
            Properties properties = new Properties();
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
            for (String key : properties.stringPropertyNames()) {
                parsePlayerId(key).ifPresent(playerId -> preferences.byPlayer.put(playerId, properties.getProperty(key)));
            }
        }
        return preferences;
    }

    private static Optional<UUID> parsePlayerId(String key) {
        try {
            return Optional.of(UUID.fromString(key));
        } catch (IllegalArgumentException invalidEntry) {
            return Optional.empty();
        }
    }

    public Optional<String> get(UUID playerId) {
        return Optional.ofNullable(byPlayer.get(playerId));
    }

    public synchronized void set(UUID playerId, String language) throws IOException {
        byPlayer.put(playerId, language);
        save();
    }

    // Se escribe a un temporal y se reemplaza de una vez: un corte a mitad de escritura no deja el archivo roto.
    private void save() throws IOException {
        Properties properties = new Properties();
        byPlayer.forEach((playerId, language) -> properties.setProperty(playerId.toString(), language));
        Files.createDirectories(file.getParent());
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        try (Writer writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
            properties.store(writer, null);
        }
        Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}
