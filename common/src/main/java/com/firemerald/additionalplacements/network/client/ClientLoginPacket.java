package com.firemerald.additionalplacements.network.client;

import com.firemerald.additionalplacements.network.APPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ClientLoginPacket extends ClientPacket {
    @Environment(EnvType.CLIENT)
    @Nullable APPacket handleClient(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect);
}
