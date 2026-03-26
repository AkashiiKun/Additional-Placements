package com.firemerald.additionalplacements.network.server.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.fabric.APPacketImpl;
import com.firemerald.additionalplacements.network.server.ServerLoginPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

public interface ServerLoginPacketImpl extends APPacketImpl, ServerLoginPacket {
    default void handleServer(MinecraftServer server, ServerLoginPacketListenerImpl handler, boolean understood, ServerLoginNetworking.LoginSynchronizer synchronizer, PacketSender responseSender) {
        APPacket reply = handleServer(server::execute, handler::disconnect);
        if (reply instanceof APPacketImpl apPacket) apPacket.send(responseSender);
    }
}
