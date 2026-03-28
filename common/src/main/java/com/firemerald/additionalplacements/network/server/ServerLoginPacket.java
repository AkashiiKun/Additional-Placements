package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ServerLoginPacket extends ServerPacket {
    @Nullable APPacket handleServer(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect);
}
