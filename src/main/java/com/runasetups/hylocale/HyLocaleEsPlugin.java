package com.runasetups.hylocale;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.runasetups.hylocale.commands.HyLocaleCommand;

import javax.annotation.Nonnull;

public class HyLocaleEsPlugin extends JavaPlugin {

    public HyLocaleEsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new HyLocaleCommand("hylocale", "Muestra el estado de HyLocale ES"));
    }
}
