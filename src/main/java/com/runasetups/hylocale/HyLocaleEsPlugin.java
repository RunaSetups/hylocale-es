package com.runasetups.hylocale;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.util.Config;
import com.runasetups.hylocale.commands.HyLocaleCommand;
import com.runasetups.hylocale.commands.LanguageCommand;
import com.runasetups.hylocale.config.HyLocaleConfig;
import com.runasetups.hylocale.language.ClientLanguageFilter;
import com.runasetups.hylocale.language.LanguagePreferences;
import com.runasetups.hylocale.language.LanguageResolver;
import com.runasetups.hylocale.language.PreferredTranslationsFilter;
import com.runasetups.hylocale.language.SupportedLanguages;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class HyLocaleEsPlugin extends JavaPlugin {

    private static final String FALLBACK_DEFAULT_LANGUAGE = "es-ES";
    private static final String PREFERENCES_FILE = "idiomas.properties";

    private final Config<HyLocaleConfig> config;
    private ClientLanguageFilter clientLanguageFilter;
    private PreferredTranslationsFilter preferredTranslationsFilter;

    public HyLocaleEsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        this.config = this.withConfig(HyLocaleConfig.CODEC);
    }

    @Override
    protected void setup() {
        config.save();
        LanguagePreferences preferences = loadPreferences();
        LanguageResolver resolver = new LanguageResolver(defaultLanguage());

        this.getCommandRegistry().registerCommand(new HyLocaleCommand("hylocale", "hylocale.commands.hylocale.desc"));
        this.getCommandRegistry().registerCommand(new LanguageCommand(preferences, this.getLogger(), freeCommandNames("language")));

        this.clientLanguageFilter = new ClientLanguageFilter(resolver, preferences);
        this.preferredTranslationsFilter = new PreferredTranslationsFilter(preferences);
        PacketAdapters.registerInbound(clientLanguageFilter);
        PacketAdapters.registerOutbound(preferredTranslationsFilter);

        this.getEventRegistry().register(PlayerConnectEvent.class, event -> {
            PlayerRef player = event.getPlayerRef();
            preferences.get(player.getUuid()).ifPresent(player::setLanguage);
        });
    }

    @Override
    protected void shutdown() {
        PacketAdapters.deregisterInbound(clientLanguageFilter);
        PacketAdapters.deregisterOutbound(preferredTranslationsFilter);
    }

    private String defaultLanguage() {
        String configured = config.get().getDefaultLanguage();
        return SupportedLanguages.fromUserInput(configured).orElseGet(() -> {
            this.getLogger().at(Level.WARNING).log(
                    "DefaultLanguage '%s' no es valido (usar es-ES o en-US). Se usa %s.", configured, FALLBACK_DEFAULT_LANGUAGE);
            return FALLBACK_DEFAULT_LANGUAGE;
        });
    }

    // Hytale no avisa si dos comandos comparten nombre: el ultimo en registrarse pisa al otro en silencio.
    private String[] freeCommandNames(String... candidates) {
        Map<String, AbstractCommand> registered = CommandManager.get().getCommandRegistration();
        return Arrays.stream(candidates)
                .filter(name -> {
                    boolean taken = registered.containsKey(name);
                    if (taken) {
                        this.getLogger().at(Level.WARNING).log(
                                "El nombre /%s ya lo usa otro comando: no se registra como alias para no pisarlo.", name);
                    }
                    return !taken;
                })
                .toArray(String[]::new);
    }

    private LanguagePreferences loadPreferences() {
        try {
            return LanguagePreferences.load(this.getDataDirectory().resolve(PREFERENCES_FILE));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudieron leer las preferencias de idioma", e);
        }
    }
}
