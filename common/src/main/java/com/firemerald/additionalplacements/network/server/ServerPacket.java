package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public interface ServerPacket extends APPacket {
    default void sendToServer() {
        APNetwork.sendToServer(this);
    }
}
