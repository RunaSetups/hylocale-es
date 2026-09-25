package com.runasetups.hylocale.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HyLocaleCommand extends AbstractCommand {

    public HyLocaleCommand(String name, String description) {
        super(name, description);
        // Comando de diagnostico: abierto a todos, no requiere permisos de operador.
        this.requireNoPermission();
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        context.sendMessage(Message.raw("HyLocale ES activo. Todavia sin traducciones cargadas."));
        return CompletableFuture.completedFuture(null);
    }
}
