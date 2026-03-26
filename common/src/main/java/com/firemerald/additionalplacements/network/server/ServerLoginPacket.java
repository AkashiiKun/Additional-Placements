package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ServerLoginPacket extends ServerPacket {
    @Nullable APPacket handleServer(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect);
}
