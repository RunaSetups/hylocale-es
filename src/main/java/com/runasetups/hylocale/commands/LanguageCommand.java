package com.runasetups.hylocale.commands;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.runasetups.hylocale.language.LanguagePreferences;
import com.runasetups.hylocale.language.SupportedLanguages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class LanguageCommand extends AbstractCommand {

    private final LanguagePreferences preferences;
    private final HytaleLogger logger;
    private final RequiredArg<String> languageArg;

    public LanguageCommand(LanguagePreferences preferences, HytaleLogger logger, String... aliases) {
        super("idioma", "hylocale.commands.idioma.desc");
        this.preferences = preferences;
        this.logger = logger;
        this.addAliases(aliases);
        this.requireNoPermission();
        this.languageArg = this.withRequiredArg("idioma", "hylocale.commands.idioma.arg", ArgTypes.STRING);
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        if (!context.isPlayer()) {
            context.sendMessage(Message.translation("hylocale.commands.idioma.playersOnly"));
            return CompletableFuture.completedFuture(null);
        }

        Optional<String> language = SupportedLanguages.fromUserInput(context.get(languageArg));
        if (language.isEmpty()) {
            context.sendMessage(Message.translation("hylocale.commands.idioma.invalid"));
            return CompletableFuture.completedFuture(null);
        }

        PlayerRef player = context.senderAs(PlayerRef.class);
        try {
            preferences.set(player.getUuid(), language.get());
        } catch (IOException e) {
            logger.at(Level.SEVERE).withCause(e).log("No se pudo guardar el idioma de %s", player.getUsername());
            context.sendMessage(Message.translation("hylocale.commands.idioma.saveError"));
            return CompletableFuture.completedFuture(null);
        }

        // Mismo camino que usa el juego cuando el jugador cambia el idioma desde su menu.
        player.setLanguage(language.get());
        I18nModule.get().sendTranslations(player.getPacketHandler(), language.get());
        context.sendMessage(Message.translation("hylocale.commands.idioma.changed"));
        return CompletableFuture.completedFuture(null);
    }
}
