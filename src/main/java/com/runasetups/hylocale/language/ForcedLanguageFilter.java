package com.runasetups.hylocale.language;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.connection.Connect;
import com.hypixel.hytale.protocol.packets.interface_.UpdateLanguage;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;

import java.util.logging.Level;

public class ForcedLanguageFilter implements PacketFilter {

    private static final String CLIENT_DEFAULT_LANGUAGE = "en-US";

    private final String forcedLanguage;
    private final HytaleLogger logger;

    public ForcedLanguageFilter(String forcedLanguage, HytaleLogger logger) {
        this.forcedLanguage = forcedLanguage;
        this.logger = logger;
    }

    // Se reescribe antes de que el server lea el paquete: asi la carga inicial ya sale en el idioma forzado.
    @Override
    public boolean test(PacketHandler handler, Packet packet) {
        if (packet instanceof Connect connect) {
            connect.language = resolve(connect.language);
        } else if (packet instanceof UpdateLanguage update) {
            update.language = resolve(update.language);
        }
        return false;
    }

    private String resolve(String clientLanguage) {
        if (clientLanguage == null || CLIENT_DEFAULT_LANGUAGE.equals(clientLanguage)) {
            logger.at(Level.INFO).log("Idioma del cliente %s reemplazado por %s", clientLanguage, forcedLanguage);
            return forcedLanguage;
        }
        return clientLanguage;
    }
}
