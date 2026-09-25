package com.runasetups.hylocale.language;

import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.connection.Connect;
import com.hypixel.hytale.protocol.packets.interface_.UpdateLanguage;
import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;

public class ClientLanguageFilter implements PacketFilter {

    private final LanguageResolver resolver;
    private final LanguagePreferences preferences;

    public ClientLanguageFilter(LanguageResolver resolver, LanguagePreferences preferences) {
        this.resolver = resolver;
        this.preferences = preferences;
    }

    // Se reescribe antes de que el server lea el paquete. En Connect todavia no se sabe quien es el jugador:
    // su preferencia guardada la aplica PreferredTranslationsFilter en la etapa de carga.
    @Override
    public boolean test(PacketHandler handler, Packet packet) {
        if (packet instanceof Connect connect) {
            connect.language = resolver.resolve(null, connect.language);
        } else if (packet instanceof UpdateLanguage update) {
            update.language = resolver.resolve(preferenceOf(handler), update.language);
        }
        return false;
    }

    private String preferenceOf(PacketHandler handler) {
        PlayerAuthentication auth = handler.getAuth();
        return auth == null ? null : preferences.get(auth.getUuid()).orElse(null);
    }
}
