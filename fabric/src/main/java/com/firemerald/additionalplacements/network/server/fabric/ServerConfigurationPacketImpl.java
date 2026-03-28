package com.firemerald.additionalplacements.network.server.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.fabric.APPacketImpl;
import com.firemerald.additionalplacements.network.server.ServerConfigurationPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public interface ServerConfigurationPacketImpl extends APPacketImpl, ServerConfigurationPacket {
    default void handleServer(MinecraftServer server, ServerConfigurationPacketListenerImpl handler, PacketSender responseSender) {
        APPacket reply = handleServer(server::execute, handler::disconnect, handler::completeTask);
        if (reply instanceof APPacketImpl apPacket) apPacket.send(responseSender);
    }
}
