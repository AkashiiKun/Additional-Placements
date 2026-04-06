package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public interface ServerPlayPacket extends ServerPacket {
    APPacket handleServer(ServerPlayer player, Consumer<Runnable> enqueueWork);
}
