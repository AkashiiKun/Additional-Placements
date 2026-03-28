package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ConfigurationTask;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ServerConfigurationPacket extends ServerPacket {
    @Nullable APPacket handleServer(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect, Consumer<ConfigurationTask.Type> finishTask);
}
