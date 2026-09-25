package com.runasetups.hylocale;

import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.runasetups.hylocale.commands.HyLocaleCommand;
import com.runasetups.hylocale.language.ForcedLanguageFilter;

import javax.annotation.Nonnull;

public class HyLocaleEsPlugin extends JavaPlugin {

    private ForcedLanguageFilter languageFilter;

    public HyLocaleEsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new HyLocaleCommand("hylocale", "hylocale.commands.hylocale.desc"));
        this.languageFilter = new ForcedLanguageFilter("es-ES", this.getLogger());
        PacketAdapters.registerInbound(this.languageFilter);
    }

    @Override
    protected void shutdown() {
        PacketAdapters.deregisterInbound(this.languageFilter);
    }
}
