package com.runasetups.hylocale.language;

import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.UpdateType;
import com.hypixel.hytale.protocol.packets.assets.UpdateTranslations;
import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;
import com.hypixel.hytale.server.core.modules.i18n.I18nModule;

import java.util.HashMap;
import java.util.Map;

public class PreferredTranslationsFilter implements PacketFilter {

    private final LanguagePreferences preferences;

    public PreferredTranslationsFilter(LanguagePreferences preferences) {
        this.preferences = preferences;
    }

    // Se mezcla sobre una copia en vez de reemplazar el mapa: un Init borra en el cliente todo lo que
    // no venga en el paquete, incluidas las claves que otros plugins le agregan.
    @Override
    public boolean test(PacketHandler handler, Packet packet) {
        if (packet instanceof UpdateTranslations translations
                && translations.type == UpdateType.Init
                && translations.translations != null) {
            PlayerAuthentication auth = handler.getAuth();
            if (auth != null) {
                preferences.get(auth.getUuid()).ifPresent(language -> {
                    Map<String, String> merged = new HashMap<>(translations.translations);
                    merged.putAll(I18nModule.get().getMessages(language));
                    translations.translations = merged;
                });
            }
        }
        return false;
    }
}
